package com.nojom.client.ui.partner;

import static com.nojom.client.util.Constants.PREF_PARTNER_ABOUT;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityPartnerAboutBinding;
import com.nojom.client.model.PartnerWithUsResponse;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PartnerAboutActivity extends BaseActivity {
    private ActivityPartnerAboutBinding binding;
    private PartnerAboutActivityVM notificationActivityVM;
    private ArrayList<PartnerWithUsResponse.Data> responseList;
    private boolean isEdit, state;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_partner_about);

        if (getIntent() != null) {
            isEdit = getIntent().getBooleanExtra("isEdit", false);
            state = getIntent().getBooleanExtra("state", false);
            responseList = (ArrayList<PartnerWithUsResponse.Data>) getIntent().getSerializableExtra("list");
        }

        notificationActivityVM = ViewModelProviders.of(this).get(PartnerAboutActivityVM.class);
        addObserver();
        notificationActivityVM.init(this);
        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(view -> onBackPressed());
        binding.tvSave.setOnClickListener(v -> {
            if (checkValidation()) {
                notificationActivityVM.addAboutAppSurvey(jsonArray);
            }
        });

        if (responseList != null && responseList.size() > 0) {
            binding.linQuestions.removeAllViews();
            makeView();
        }

    }

    private boolean checkValidation() {
        jsonArray = new JSONArray();
        for (int i = 0; i < responseList.size(); i++) {
            JSONObject object = new JSONObject();
            if (responseList.get(i).type == 0) {
                if (TextUtils.isEmpty(responseList.get(i).aboutAnswer)) {
                    toastMessage(getString(R.string.please_enter) + " " + responseList.get(i).getQuestion(getLanguage()));
                    return false;
                }

                if (responseList.get(i).id == 8) {
                    if (!isValidUrl(responseList.get(i).aboutAnswer)) {
                        toastMessage(getString(R.string.please_enter_valid_url));
                        return false;
                    }
                }

                addValueToObject(object, responseList.get(i).id, responseList.get(i).aboutAnswer);

                if (object.length() > 0) {
                    jsonArray.put(object);
                }
            }

        }
        return true;
    }

    private void addValueToObject(JSONObject object, int partQueId, String answer) {
        try {
            object.put("partnershipQuestionID", partQueId);
            object.put("answer", answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addObserver() {
        notificationActivityVM.getIsSubmitAnswer().observe(this, isShow -> {
            disableEnableTouch(isShow);
            if (isShow) {
                binding.tvSave.setVisibility(View.INVISIBLE);
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.tvSave.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        notificationActivityVM.getIsSubmitAnswerSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                Preferences.writeBoolean(this, PREF_PARTNER_ABOUT, true);
                finish();
            }
        });
    }

    private void makeView() {
        for (int i = 0; i < responseList.size(); i++) {
            if (responseList.get(i).type == 0) {
                try {
                    View view = LayoutInflater.from(this).inflate(R.layout.custom_partner_about_view, null);
                    CustomTextView tvQuestion = view.findViewById(R.id.tv_title);
                    EditText etAbout = view.findViewById(R.id.et_about);

                    if (responseList.get(i).placeholder.length() > 40) {
                        etAbout.setMinLines(8);
                    }

                    tvQuestion.setText(responseList.get(i).getQuestion(getLanguage()));
                    etAbout.setHint(responseList.get(i).placeholder);

                    if (!TextUtils.isEmpty(responseList.get(i).getAnswers(getLanguage()))) {
                        etAbout.setText(responseList.get(i).getAnswers(getLanguage()));
                        responseList.get(i).aboutAnswer = responseList.get(i).getAnswers(getLanguage());
                    }

                    int finalI = i;
                    etAbout.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            responseList.get(finalI).aboutAnswer = s + "";
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    binding.linQuestions.addView(view);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
