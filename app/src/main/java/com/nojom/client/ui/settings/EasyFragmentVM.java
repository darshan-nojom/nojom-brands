package com.nojom.client.ui.settings;

import android.app.Application;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.style.ClickableSpan;
import android.textview.CustomTextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentWinBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.SocialSurveyListModel;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.util.Constants.API_GET_SOCIAL_SURVEY;

class EasyFragmentVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private FragmentWinBinding binding;
    private BaseFragment fragment;

    EasyFragmentVM(Application application, FragmentWinBinding winBinding, BaseFragment winFragment) {
        super(application);
        binding = winBinding;
        fragment = winFragment;
        initData();
    }

    private void initData() {
        getSurveyList();

        binding.tvHowItWorks.setPaintFlags(binding.tvHowItWorks.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if (fragment.activity.getCurrency().equals("SAR")) {
            binding.txtSurvey.setText(fragment.getString(R.string.survey_2_sar));
            binding.txtTitle.setText(fragment.getString(R.string.get_12_sar));
            binding.txtGpay.setText(fragment.getString(R.string.google_play_2_sar));
            binding.txtGoogle.setText(fragment.getString(R.string.google_2_sar));
            binding.txtFb.setText(fragment.getString(R.string.facebook_2_sar));
            binding.txtTp.setText(fragment.getString(R.string.trustpilot_2_sar));
            binding.txtSj.setText(fragment.getString(R.string.sitejabber_2_sar));
            binding.txtT3.setText(fragment.getString(R.string.get_2_for_every_review_sar));
            binding.txtT4.setText(fragment.getString(R.string.you_will_get_2_for_one_review_on_social_platform_sar));
        }

        binding.tvHowItWorks.setOnClickListener(this);
        binding.txtStart.setOnClickListener(this);
        binding.relAppStore.setOnClickListener(this);
        binding.relGooglePlay.setOnClickListener(this);
        binding.relGoogle.setOnClickListener(this);
        binding.relFacebook.setOnClickListener(this);
        binding.relTrustpilot.setOnClickListener(this);
        binding.relSitejabber.setOnClickListener(this);
        binding.linStartSurvey.setVisibility(View.GONE);
        binding.linSurvey.setVisibility(View.VISIBLE);

        ClickableSpan tncClick = new ClickableSpan() {
            @Override
            public void onClick(@NotNull View view) {
                fragment.activity.redirectUsingCustomTab(Constants.TERMS_USE);
            }
        };

        Utils.makeLinks(binding.tvTermsOfUse, new String[]{fragment.getString(R.string.terms_of_use_)}, new ClickableSpan[]{tncClick});

    }

    private void scrollToView(final ScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }

    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.txt_start:
                intent = new Intent(fragment.activity, AddSurveyActivity.class);
                fragment.startActivityForResult(intent, 125);
                break;
            case R.id.rel_app_store:
                if (binding.relAppStore.getTag() != null && !binding.relAppStore.getTag().equals("1")) {
                    openReSubmitSurveyActivity(1);
                }
                break;
            case R.id.rel_google_play:
                if (binding.relGooglePlay.getTag() != null && !binding.relGooglePlay.getTag().equals("1")) {
                    openReSubmitSurveyActivity(2);
                }
                break;
            case R.id.rel_google:
                if (binding.relGoogle.getTag() != null && !binding.relGoogle.getTag().equals("1")) {
                    openReSubmitSurveyActivity(3);
                }
                break;
            case R.id.rel_facebook:
                if (binding.relFacebook.getTag() != null && !binding.relFacebook.getTag().equals("1")) {
                    openReSubmitSurveyActivity(4);
                }
                break;
            case R.id.rel_trustpilot:
                if (binding.relTrustpilot.getTag() != null && !binding.relTrustpilot.getTag().equals("1")) {
                    openReSubmitSurveyActivity(5);
                }
                break;
            case R.id.rel_sitejabber:
                if (binding.relSitejabber.getTag() != null && !binding.relSitejabber.getTag().equals("1")) {
                    openReSubmitSurveyActivity(6);
                }
                break;
            case R.id.tv_how_it_works:
                binding.scrollview.post(() ->
                        scrollToView(binding.scrollview, binding.txtBlueLabel));
                break;
        }
    }

    private void openReSubmitSurveyActivity(int socialId) {
        Intent intent = new Intent(fragment.activity, ReSubmitSurveyActivity.class);
        intent.putExtra("social_id", socialId);
        switch (socialId) {
            case 1:
                intent.putExtra("note", "" + binding.txtAppstoreStatus.getTag());
                break;
            case 2:
                intent.putExtra("note", "" + binding.txtGooglepayStatus.getTag());
                break;
            case 3:
                intent.putExtra("note", "" + binding.txtGoogleStatus.getTag());
                break;
            case 4:
                intent.putExtra("note", "" + binding.txtFacebookStatus.getTag());
                break;
            case 5:
                intent.putExtra("note", "" + binding.txtTrustpilotStatus.getTag());
                break;
            case 6:
                intent.putExtra("note", "" + binding.txtSitejabberStatus.getTag());
                break;
        }
        fragment.startActivityForResult(intent, 125);
    }


    private void getSurveyList() {
        if (!fragment.activity.isNetworkConnected())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_SOCIAL_SURVEY, false, null);
    }

    private void setStatus(int surveyStatus, CustomTextView txtStatus, String note, RelativeLayout relativeLayout) {
        try {
            if (relativeLayout != null) {
                relativeLayout.setTag("" + surveyStatus);
            }
            txtStatus.setTag(note);
            switch (surveyStatus) {
                case 1:
                    txtStatus.setText(fragment.getString(R.string.deposited));
                    txtStatus.setBackground(fragment.getResources().getDrawable(R.drawable.green_rounded_corner_20));
                    txtStatus.setTextColor(fragment.getResources().getColor(R.color.white));
                    break;
                case 2:
                    txtStatus.setText(fragment.getString(R.string.under_review));
                    txtStatus.setBackground(fragment.getResources().getDrawable(R.drawable.orangelight_bg_20));
                    txtStatus.setTextColor(fragment.getResources().getColor(R.color.white));
                    break;
                case 3:
                    txtStatus.setText(fragment.getString(R.string.rejected));
                    txtStatus.setBackground(fragment.getResources().getDrawable(R.drawable.red_bg_20));
                    txtStatus.setTextColor(fragment.getResources().getColor(R.color.white));
                    break;
                default:
                    txtStatus.setText(fragment.getString(R.string.not_started));
                    txtStatus.setBackground(fragment.getResources().getDrawable(R.drawable.gray_rounded_corner_20));
                    txtStatus.setTextColor(fragment.getResources().getColor(R.color.tab_gray));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 125 && resultCode == RESULT_OK) {
            binding.linStartSurvey.setVisibility(View.GONE);
            binding.linSurvey.setVisibility(View.VISIBLE);
            getSurveyList();
            fragment.activity.getProfile();
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data1) {
        try {
            if (url.equalsIgnoreCase(API_GET_SOCIAL_SURVEY)) {
                setStatus(0, binding.tvProfileComplete, null, null);
                setStatus(0, binding.txtAppstoreStatus, null, binding.relAppStore);
                setStatus(0, binding.txtGooglepayStatus, null, binding.relGooglePlay);
                setStatus(0, binding.txtGoogleStatus, null, binding.relGoogle);
                setStatus(0, binding.txtFacebookStatus, null, binding.relFacebook);
                setStatus(0, binding.txtTrustpilotStatus, null, binding.relTrustpilot);
                setStatus(0, binding.txtSitejabberStatus, null, binding.relSitejabber);

                SocialSurveyListModel surveyListModel = SocialSurveyListModel.getSocialSurveys(responseBody);
                if (surveyListModel != null && surveyListModel.data.size() > 0) {
                    for (SocialSurveyListModel.Data data : surveyListModel.data) {
                        switch (data.id) {
                            case 1:
                                setStatus(data.surveyStatus, binding.txtAppstoreStatus, data.note, binding.relAppStore);
                                break;
                            case 2:
                                setStatus(data.surveyStatus, binding.txtGooglepayStatus, data.note, binding.relGooglePlay);
                                break;
                            case 3:
                                setStatus(data.surveyStatus, binding.txtGoogleStatus, data.note, binding.relGoogle);
                                break;
                            case 4:
                                setStatus(data.surveyStatus, binding.txtFacebookStatus, data.note, binding.relFacebook);
                                break;
                            case 5:
                                setStatus(data.surveyStatus, binding.txtTrustpilotStatus, data.note, binding.relTrustpilot);
                                break;
                            case 6:
                                setStatus(data.surveyStatus, binding.txtSitejabberStatus, data.note, binding.relSitejabber);
                                break;
                            case 7:
                                setStatus(data.surveyStatus, binding.tvProfileComplete, data.note, null);
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        setStatus(0, binding.tvProfileComplete, null, null);
        setStatus(0, binding.txtAppstoreStatus, null, binding.relAppStore);
        setStatus(0, binding.txtGooglepayStatus, null, binding.relGooglePlay);
        setStatus(0, binding.txtGoogleStatus, null, binding.relGoogle);
        setStatus(0, binding.txtFacebookStatus, null, binding.relFacebook);
        setStatus(0, binding.txtTrustpilotStatus, null, binding.relTrustpilot);
        setStatus(0, binding.txtSitejabberStatus, null, binding.relSitejabber);
    }
}
