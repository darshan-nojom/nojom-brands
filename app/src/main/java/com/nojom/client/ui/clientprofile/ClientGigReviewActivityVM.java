package com.nojom.client.ui.clientprofile;

import android.annotation.SuppressLint;
import android.app.Application;
import android.edittext.CustomEditText;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityClientReviewBinding;
import com.nojom.client.model.ProjectGigByID;
import com.nojom.client.model.Questions;
import com.nojom.client.segment.SegmentedButton;
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
import static com.nojom.client.util.Constants.API_ADD_FEED_BACK;
import static com.nojom.client.util.Constants.API_GET_FEED_BACK_LISTS;

class ClientGigReviewActivityVM extends AndroidViewModel implements RecyclerviewAdapter.OnViewBindListner, View.OnClickListener, RequestResponseListener {
    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private ActivityClientReviewBinding binding;
    private RecyclerviewAdapter mAdapter;
    private ArrayList<Questions.Data> questionsList;
    private ProjectGigByID projectGigByID;

    ClientGigReviewActivityVM(Application application, ActivityClientReviewBinding clientReviewBinding, BaseActivity clientReviewActivity) {
        super(application);
        binding = clientReviewBinding;
        activity = clientReviewActivity;
        initData();
    }

    private void initData() {
        binding.tvCancel.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
        binding.etComment.setVisibility(View.VISIBLE);

        if (activity.getIntent() != null) {
            projectGigByID = (ProjectGigByID) activity.getIntent().getSerializableExtra(Constants.USER_DATA);
        }

        binding.rvQuestions.setLayoutManager(new LinearLayoutManager(activity));

        getQuestions();

       binding.etComment.setOnTouchListener((v, event) -> {
           if (binding.etComment.hasFocus()) {
               v.getParent().requestDisallowInterceptTouchEvent(true);
               switch (event.getAction() & MotionEvent.ACTION_MASK){
                   case MotionEvent.ACTION_SCROLL:
                       v.getParent().requestDisallowInterceptTouchEvent(false);
                       return true;
               }
           }
           return false;
       });

    }

    private void getQuestions() {
        if (!activity.isNetworkConnected())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_FEED_BACK_LISTS, false, null);
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
                if (!TextUtils.isEmpty(binding.etComment.getText().toString())) {
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
        map.put("gigID", projectGigByID.gigID + "");
        map.put("agentProfileID", projectGigByID.agentProfileID + "");
        map.put("contractID", projectGigByID.id + "");
        map.put("comment", binding.etComment.getText().toString());
        map.put("feedbackOption", getFeedbackOptionReviews() + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_ADD_FEED_BACK, true, map);
    }

    private String getReviews() {
        JSONArray main = new JSONArray();
        for (int i = 0; i < questionsList.size(); i++) {
            View view = binding.rvQuestions.getChildAt(i);
            ScaleRatingBar ratingBar = view.findViewById(R.id.ratingbar);

            try {
                if (questionsList.get(i).type == 2) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("reviewID", (i + 1) + "");
                    jsonObject.put("rate", ratingBar.getRating());
                    main.put(jsonObject);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return main.toString();
    }

    private String getFeedbackOptionReviews() {
        JSONArray main = new JSONArray();
        for (int i = 0; i < questionsList.size(); i++) {
            View view = binding.rvQuestions.getChildAt(i);
            SegmentedButtonGroup segmentedButtonGroup = view.findViewById(R.id.segmentGroup);
            try {
                if (questionsList.get(i).type == 1) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("reviewID", (i + 1) + "");
                    jsonObject.put("feedback", (segmentedButtonGroup.getPosition() == 0) ? "0" : "1");
                    main.put(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return main.toString();
    }


    @Override
    public void bindView(View view, int position) {
        RelativeLayout rlQuestions = view.findViewById(R.id.rl_question);
        TextView tvQuestions = view.findViewById(R.id.tv_question);
        ScaleRatingBar ratingBar = view.findViewById(R.id.ratingbar);
        SegmentedButtonGroup segmentedButtonGroup = view.findViewById(R.id.segmentGroup);
        SegmentedButton sgNo = view.findViewById(R.id.sb_no);
        SegmentedButton sgYes = view.findViewById(R.id.sb_yes);
        CustomEditText etComment = view.findViewById(R.id.et_comment);

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
        sgNo.setSelectedBackgroundColor(activity.getResources().getColor(R.color.red_dark));
        sgYes.setSelectedBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
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

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_ADD_FEED_BACK)) {
            activity.toastMessage(message);
            activity.setResult(RESULT_OK);
            activity.finish();
        } else if (url.equalsIgnoreCase(API_GET_FEED_BACK_LISTS)) {
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
