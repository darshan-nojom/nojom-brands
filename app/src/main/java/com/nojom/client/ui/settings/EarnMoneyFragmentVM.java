package com.nojom.client.ui.settings;

import static com.nojom.client.util.Constants.API_GET_PROMO_CODE_HISTORY;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ScrollView;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.ReferralHistoryAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentEarnMoneyBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.ReferralHistory;
import com.nojom.client.util.Constants;
import com.nojom.client.util.EqualSpacingItemDecoration;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class EarnMoneyFragmentVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private final FragmentEarnMoneyBinding binding;
    private final BaseFragment fragment;
    private String shareText;

    EarnMoneyFragmentVM(Application application, FragmentEarnMoneyBinding earnMoneyBinding, BaseFragment earnMoneyFragment) {
        super(application);
        binding = earnMoneyBinding;
        fragment = earnMoneyFragment;
        initData();
    }

    private void initData() {
        binding.txtCopy.setOnClickListener(this);
        binding.linEmail.setOnClickListener(this);
        binding.linMore.setOnClickListener(this);
        binding.linMsg.setOnClickListener(this);

        if (fragment.activity.getCurrency().equals("SAR")) {
            binding.tvHereBlue.setText(fragment.getString(R.string.give_10_get_10_sar));
            binding.txtT1.setText(fragment.getString(R.string.anyone_who_use_your_coupon_or_follows_your_link_gets_10_off_the_first_order_sar));
            binding.txtT2.setText(fragment.getString(R.string.your_friend_s_order_must_be_completed_so_you_can_use_your_10_sar));
            binding.txtT3.setText(fragment.getString(R.string.get_10_for_every_friend_sar));
            binding.txtT4.setText(fragment.getString(R.string.for_example_if_you_invited_100_friends_then_you_will_get_1_000_sar));
            binding.tvTermsOfUse.setText(fragment.getString(R.string.earn_money_footer_sar));
        }

        binding.tvHowItWorks.setPaintFlags(binding.tvHowItWorks.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        binding.rvReferral.setLayoutManager(new LinearLayoutManager(fragment.activity));
        binding.rvReferral.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.VERTICAL));

        binding.tvHowItWorks.setOnClickListener(view -> binding.scrollview.post(() ->
                scrollToView(binding.scrollview, binding.txtBlueLabel)));


        ClickableSpan refCodeClick = new ClickableSpan() {
            @Override
            public void onClick(@NotNull View view) {
                copyMsg(fragment.activity.getReferralCode());
            }
        };

        String textLine = fragment.activity.getString(R.string.give_your_friends_10_off_their_first_order) + " (<u>" + fragment.activity.getReferralCode() + "</u>).";
        if (fragment.activity.getCurrency().equals("SAR")) {
            textLine = fragment.activity.getString(R.string.give_your_friends_10_off_their_first_order_sar) + " (<u>" + fragment.activity.getReferralCode() + "</u>).";
        }
        SpannableStringBuilder refCodeTxt = Utils.getColorString(fragment.activity, Html.fromHtml(textLine).toString(),
                fragment.activity.getReferralCode(), R.color.colorPrimary);
        binding.earnMoneyTitle.setText(refCodeTxt);
        Utils.makeLinks(binding.earnMoneyTitle, new String[]{fragment.activity.getReferralCode()}, new ClickableSpan[]{refCodeClick});

        ClickableSpan tncClick = new ClickableSpan() {
            @Override
            public void onClick(@NotNull View view) {
                fragment.activity.redirectUsingCustomTab(Constants.TERMS_USE);
            }
        };

        try {
            Utils.makeLinks(binding.tvTermsOfUse, new String[]{fragment.getString(R.string.terms_of_use_)}, new ClickableSpan[]{tncClick});
            setLink();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getReferralHistory();

    }

    void setLink() {
        try {
            if (fragment.activity != null) {
                String link = ((GetDiscountActivity) fragment.activity).getmInvitationUrl();
                binding.txtRefLink.setText(String.format(fragment.getString(R.string.task_promo_code) + " ", fragment.activity.getReferralCode()));
                if (fragment.activity.getCurrency().equals("SAR")) {
                    shareText = fragment.activity.getString(R.string.I_will_send_10_if_you_sign_up_using_below_sar) + ":\n\n1. "
                            + link + "\n2. " + fragment.activity.getString(R.string.use_my_invite_code) + fragment.activity.getReferralCode() + "\n3. " + fragment.activity.getString(R.string.get_10_discount_on_first_order_sar) + "\n\n" + fragment.activity.getString(R.string.let_s_start_placing_order_now);
                } else {
                    shareText = fragment.activity.getString(R.string.I_will_send_10_if_you_sign_up_using_below) + ":\n\n1. "
                            + link + "\n2. " + fragment.activity.getString(R.string.use_my_invite_code) + fragment.activity.getReferralCode() + "\n3. " + fragment.activity.getString(R.string.get_10_discount_on_first_order) + "\n\n" + fragment.activity.getString(R.string.let_s_start_placing_order_now);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        switch (view.getId()) {
            case R.id.txt_copy:
                copyMsg(fragment.activity.getReferralCode());
                break;
            case R.id.lin_msg:
                sendMsg();
                break;
            case R.id.lin_more:
                shareLink();
                break;
            case R.id.lin_email:
                sendEmail();
                break;
        }
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Referral link");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        fragment.startActivity(Intent.createChooser(intent, "Send Email"));
    }

    private void shareLink() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        fragment.startActivity(sendIntent);
    }

    private void sendMsg() {
        try {
            Uri uri = Uri.parse("smsto:");
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            it.putExtra("sms_body", shareText);
            fragment.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyMsg(String msg) {
        ClipboardManager clipboard = (ClipboardManager) fragment.activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(fragment.getString(R.string.copied), msg);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            fragment.activity.toastMessage(fragment.getString(R.string.copied));
        }
    }

    private void getReferralHistory() {
        if (!fragment.activity.isNetworkConnected())
            return;

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_PROMO_CODE_HISTORY, false, null);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data1) {
        if (url.equalsIgnoreCase(API_GET_PROMO_CODE_HISTORY)) {
            ReferralHistory model = ReferralHistory.getPromoCodeHistory(responseBody);
            List<ReferralHistory.Data> historyData = new ArrayList<>();
            ReferralHistory.Data data = new ReferralHistory.Data();
            data.username = fragment.activity.getString(R.string.name_of_friends);
            data.timestamp = fragment.activity.getString(R.string.date_of_first_order);
            historyData.add(data);

            if (model != null && model.data != null) {
                historyData.addAll(model.data);
            }
            if (historyData.size() > 1) {
                binding.relReferral.setVisibility(View.VISIBLE);
                binding.txtAmount.setText(fragment.activity.getCurrency().equals("SAR") ? Utils.priceWithSAR(fragment.activity, Utils.get2DecimalPlaces(Objects.requireNonNull(model).totalBalance)) : Utils.priceWith$(Utils.get2DecimalPlaces(Objects.requireNonNull(model).totalBalance),fragment.activity));
                binding.rvReferral.setVisibility(View.VISIBLE);
                ReferralHistoryAdapter adapter = new ReferralHistoryAdapter(fragment.activity, historyData);
                binding.rvReferral.setAdapter(adapter);
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
    }
}
