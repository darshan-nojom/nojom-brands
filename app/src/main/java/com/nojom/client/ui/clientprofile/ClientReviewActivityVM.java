package com.nojom.client.ui.clientprofile;

import android.annotation.SuppressLint;
import android.app.Application;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityClientReviewBinding;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.model.Questions;
import com.nojom.client.segment.SegmentedButtonGroup;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_ADD_AGENT_REVIEW;
import static com.nojom.client.util.Constants.API_GET_REVIEW_QUESTION_LIST;

class ClientReviewActivityVM extends AndroidViewModel implements RecyclerviewAdapter.OnViewBindListner, View.OnClickListener, RequestResponseListener {
    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private ActivityClientReviewBinding binding;
    private RecyclerviewAdapter mAdapter;
    private ArrayList<Questions.Data> questionsList;
    private ProjectByID jobPostBids;

    ClientReviewActivityVM(Application application, ActivityClientReviewBinding clientReviewBinding, BaseActivity clientReviewActivity) {
        super(application);
        binding = clientReviewBinding;
        activity = clientReviewActivity;
        initData();
    }

    private void initData() {
        binding.tvCancel.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);

        if (activity.getIntent() != null) {
            jobPostBids = (ProjectByID) activity.getIntent().getSerializableExtra(Constants.USER_DATA);
        }

        if (jobPostBids == null) {
            activity.finish();
            return;
        }

        binding.rvQuestions.setLayoutManager(new LinearLayoutManager(activity));

        getQuestions();
    }

    private void getQuestions() {
        if (!activity.isNetworkConnected())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_REVIEW_QUESTION_LIST, false, null);
    }

    private void setAdapter() {
        if (questionsList != null && questionsList.size() > 0) {
            if (mAdapter == null) {
                mAdapter = new RecyclerviewAdapter(questionsList, R.layout.item_client_review, this);
            }

            mAdapter.doRefresh(questionsList);

            if (binding.rvQuestions.getAdapter() == null) {
                binding.rvQuestions.setAdapter(mAdapter);
            }
        } else {
            if (mAdapter != null)
                mAdapter.doRefresh(questionsList);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                activity.onBackPressed();
                break;
            case R.id.tv_submit:
                if (isComment()) {
                    submitReview();
                } else {
                    activity.toastMessage(activity.getString(R.string.please_enter_your_comment));
                }
                break;
        }

    }

    private void submitReview() {
        if (!activity.isNetworkConnected())
            return;

        HashMap<String, String> map = new HashMap<>();
        map.put("review", getReviews() + "");
        map.put("job_post_id", jobPostBids.id + "");
        map.put("agent_id", jobPostBids.agentProfileId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_ADD_AGENT_REVIEW, true, map);
    }

    private String getReviews() {
        JSONArray main = new JSONArray();
        for (int i = 0; i < questionsList.size(); i++) {
            View view = binding.rvQuestions.getChildAt(i);
            ScaleRatingBar ratingBar = view.findViewById(R.id.ratingbar);
            SegmentedButtonGroup segmentedButtonGroup = view.findViewById(R.id.segmentGroup);
            EditText etComment = view.findViewById(R.id.et_comment);

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("review_id", (i + 1) + "");
                if (questionsList.get(i).type == 1) {
                    jsonObject.put("rate", (segmentedButtonGroup.getPosition() == 0) ? activity.getString(R.string.no).toUpperCase() : activity.getString(R.string.yes).toUpperCase());
                } else if (questionsList.get(i).type == 2) {
                    jsonObject.put("rate", ratingBar.getRating());
                } else if (questionsList.get(i).type == 3) {
                    jsonObject.put("rate", etComment.getText().toString());
                }
                main.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return main.toString();
    }

    private boolean isComment() {
        for (int i = 0; i < questionsList.size(); i++) {
            View view = binding.rvQuestions.getChildAt(i);
            EditText etComment = view.findViewById(R.id.et_comment);

            if (questionsList.get(i).type == 3) {
                if (!TextUtils.isEmpty(etComment.getText().toString().trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void bindView(View view, int position) {
        RelativeLayout rlQuestions = view.findViewById(R.id.rl_question);
        TextView tvQuestions = view.findViewById(R.id.tv_question);
        ScaleRatingBar ratingBar = view.findViewById(R.id.ratingbar);
        SegmentedButtonGroup segmentedButtonGroup = view.findViewById(R.id.segmentGroup);
        EditText etComment = view.findViewById(R.id.et_comment);


        etComment.setOnTouchListener((v, event) -> {
            if (etComment.hasFocus()) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                }
            }
            return false;
        });

        Questions.Data item = questionsList.get(position);
        if (item.type == 3) {
            rlQuestions.setVisibility(View.GONE);
            etComment.setVisibility(View.VISIBLE);
        } else {
            rlQuestions.setVisibility(View.VISIBLE);
            etComment.setVisibility(View.GONE);
            if (item.type == 1) {
                ratingBar.setVisibility(View.GONE);
                segmentedButtonGroup.setVisibility(View.VISIBLE);
            } else {
                ratingBar.setVisibility(View.VISIBLE);
                segmentedButtonGroup.setVisibility(View.GONE);
            }
            tvQuestions.setText(item.getQuestion(activity));
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_ADD_AGENT_REVIEW)) {
            activity.toastMessage(message);
            activity.setResult(RESULT_OK);
            activity.finish();
        } else if (url.equalsIgnoreCase(API_GET_REVIEW_QUESTION_LIST)) {
            Questions questions = Questions.getQuestions(responseBody);
            if (questions != null && questions.data != null) {
                questionsList = new ArrayList<>();
                questionsList = (ArrayList<Questions.Data>) questions.data;

                setAdapter();
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
    }
}
