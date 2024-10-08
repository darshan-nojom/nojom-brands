package com.nojom.client.ui.settings;

import static com.nojom.client.util.Constants.API_GET_USER_LEVEL;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.textview.CustomTextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentMyLevelBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.GetUserLevel;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

class MyLevelFragmentVM extends AndroidViewModel implements RequestResponseListener {
    private final FragmentMyLevelBinding binding;
    private final BaseFragment fragment;

    MyLevelFragmentVM(Application application, FragmentMyLevelBinding myLevelBinding, BaseFragment myLevelFragment) {
        super(application);
        binding = myLevelBinding;
        fragment = myLevelFragment;
        initData();
    }

    private void initData() {
        binding.tvHowItWorks.setPaintFlags(binding.tvHowItWorks.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.tvTermsOfUse.setPaintFlags(binding.tvTermsOfUse.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        binding.imgMedalBlue.setImageResource(R.drawable.medal_blue);
        binding.imgMedalSilver.setImageResource(R.drawable.medal_silver);
        binding.imgMedalGold.setImageResource(R.drawable.medal_gold);
        binding.imgMedalVip.setImageResource(R.drawable.medal_vip);

        getUserLevel();

        binding.tvHowItWorks.setOnClickListener(view -> binding.scrollview.post(() ->
                scrollToView(binding.scrollview, binding.txtBlueLabel)));

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

    private void getUserLevel() {
        if (!fragment.activity.isNetworkConnected())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_USER_LEVEL, false, null);
    }

    @SuppressLint("StringFormatInvalid")
    private void setProgressUi(double fromNumber, int toNumber, String medalName, CustomTextView tvHereText, LinearLayout llMedalView, String totalAmount, int level) {
        if (tvHereText != null && level != 0)
            tvHereText.setVisibility(View.VISIBLE);
        if (llMedalView != null && level != 0)
            llMedalView.setBackground(ContextCompat.getDrawable(fragment.activity, R.drawable.shadow_bg_10));
        if (toNumber == 0 && fromNumber == 0) {
            binding.tvAwayMember.setText(fragment.getString(R.string.you_are_vip_member));
        } else {
            double totalPayment = Double.parseDouble(fragment.activity.getCurrency().equals("SAR") ? Utils.priceWithoutSAR(fragment.activity, totalAmount) : Utils.priceWithout$(totalAmount));
            double awayAmount = toNumber - totalPayment;
            String away = fragment.activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(fragment.activity, Utils.numberFormat2Places(Math.abs(awayAmount))) : Utils.priceWith$(Utils.numberFormat2Places(Math.abs(awayAmount)),fragment.activity);

            binding.tvAwayMember.setText(fragment.getString(R.string.away_member, away, medalName));

            int percentage = (int) ((fromNumber * 100) / toNumber);
            binding.progress.setProgress(percentage);
        }
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_USER_LEVEL)) {

            GetUserLevel model = GetUserLevel.getUserLevel(responseBody);
            double fromNum = 0;
            if (model.totalAmount != null && (!TextUtils.isEmpty(model.totalAmount) || !model.totalAmount.equalsIgnoreCase("null"))) {
                fromNum = Double.parseDouble(model.totalAmount);
            }

            if (model.levelData.size() > 0) {
                if (fragment.activity.getCurrency().equals("SAR")) {
                    binding.txtBronzeGet.setText(fragment.activity.getString(R.string.spend) + " " + model.levelData.get(0).amount + " " + fragment.getString(R.string.sar) + " " + fragment.activity.getString(R.string.and_get));
                    binding.txtSilverGet.setText(fragment.activity.getString(R.string.spend) + " " + model.levelData.get(1).amount + " " + fragment.getString(R.string.sar) + " " + fragment.activity.getString(R.string.and_get));
                    binding.txtGoldGet.setText(fragment.activity.getString(R.string.spend) + " " + model.levelData.get(2).amount + " " + fragment.getString(R.string.sar) + " " + fragment.activity.getString(R.string.and_get));
                    binding.txtVipGet.setText(fragment.activity.getString(R.string.spend) + " " + model.levelData.get(3).amount + " " + fragment.getString(R.string.sar) + " " + fragment.activity.getString(R.string.and_get));

                    binding.txtPt1.setText(fragment.getString(R.string._4_points_with_every_sar_you_spend));
                    binding.txtPt2.setText(fragment.getString(R.string._4_points_with_every_sar_you_spend));
                    binding.txtPt3.setText(fragment.getString(R.string._4_points_with_every_sar_you_spend));
                    binding.txtPt4.setText(fragment.getString(R.string._4_points_with_every_sar_you_spend));
                } else {
                    binding.txtBronzeGet.setText(fragment.activity.getString(R.string.spend) + " " + fragment.getString(R.string.dollar) + model.levelData.get(0).amount + " " + fragment.activity.getString(R.string.and_get));
                    binding.txtSilverGet.setText(fragment.activity.getString(R.string.spend) + " " + fragment.getString(R.string.dollar) + model.levelData.get(1).amount + " " + fragment.activity.getString(R.string.and_get));
                    binding.txtGoldGet.setText(fragment.activity.getString(R.string.spend) + " " + fragment.getString(R.string.dollar) + model.levelData.get(2).amount + " " + fragment.activity.getString(R.string.and_get));
                    binding.txtVipGet.setText(fragment.activity.getString(R.string.spend) + " " + fragment.getString(R.string.dollar) + model.levelData.get(3).amount + " " + fragment.activity.getString(R.string.and_get));

                    binding.txtPt1.setText(fragment.getString(R.string._4_points_with_every_dollar_you_spend));
                    binding.txtPt2.setText(fragment.getString(R.string._4_points_with_every_dollar_you_spend));
                    binding.txtPt3.setText(fragment.getString(R.string._4_points_with_every_dollar_you_spend));
                    binding.txtPt4.setText(fragment.getString(R.string._4_points_with_every_dollar_you_spend));
                }

            }
            if (model.level != 0) {
                if (model.level == 1) {
                    setProgressUi(fromNum, 200, fragment.getString(R.string.silver), binding.tvHereBlue, binding.llMedalBlue, model.totalAmount, model.level);
                    binding.imgMedalBlue.setImageResource(R.drawable.medal_blue_fill);
                    binding.txtBlue.setVisibility(View.VISIBLE);
                } else if (model.level == 2) {
                    binding.imgMedalBlue.setImageResource(R.drawable.medal_blue_fill);
                    binding.imgMedalSilver.setImageResource(R.drawable.medal_silver_fill);
                    setProgressUi(fromNum, 400, fragment.getString(R.string.gold), binding.tvHereSilver, binding.llMedalSilver, model.totalAmount, model.level);
                    binding.txtSilver.setVisibility(View.VISIBLE);
                } else if (model.level == 3) {
                    binding.imgMedalBlue.setImageResource(R.drawable.medal_blue_fill);
                    binding.imgMedalSilver.setImageResource(R.drawable.medal_silver_fill);
                    binding.imgMedalGold.setImageResource(R.drawable.medal_gold_fill);
                    setProgressUi(fromNum, 1250, fragment.getString(R.string.vip), binding.tvHereGold, binding.llMedalGold, model.totalAmount, model.level);
                    binding.txtGold.setVisibility(View.VISIBLE);
                } else if (model.level == 4) {
                    binding.imgMedalBlue.setImageResource(R.drawable.medal_blue_fill);
                    binding.imgMedalSilver.setImageResource(R.drawable.medal_silver_fill);
                    binding.imgMedalGold.setImageResource(R.drawable.medal_gold_fill);
                    binding.imgMedalVip.setImageResource(R.drawable.medal_vip_fill);
                    binding.progress.setProgress(100);
                    binding.txtVip.setVisibility(View.VISIBLE);
                    setProgressUi(0, 0, "", binding.tvHereVip, binding.llMedalVip, model.totalAmount, model.level);
                } else {
                    setProgressUi(fromNum, 100, fragment.getString(R.string.blue), binding.tvHereBlue, binding.llMedalBlue, model.totalAmount, model.level);
                }
            } else {
                setProgressUi(fromNum, 100, fragment.getString(R.string.blue), binding.tvHereBlue, binding.llMedalBlue, model.totalAmount, model.level);
            }
        }

    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
    }
}
