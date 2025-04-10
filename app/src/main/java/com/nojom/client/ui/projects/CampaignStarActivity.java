package com.nojom.client.ui.projects;

import static com.nojom.client.adapter.CampaignAdapter2.capitalizeWords;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.adapter.CampStarsPlatformAdapter;
import com.nojom.client.databinding.ActivityCampStarsBinding;
import com.nojom.client.model.CampList;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.chat.ChatMessagesActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CampaignStarActivity extends BaseActivity {

    private CampaignStarActivityVM campaignStarActivityVM;
    public CampList campList;
    private Profile profile;
    private ActivityCampStarsBinding binding;
    private final PrettyTime p = new PrettyTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camp_stars);
        campaignStarActivityVM = new CampaignStarActivityVM(Task24Application.getInstance(), this);

        if (getIntent() != null) {
            campList = (CampList) getIntent().getSerializableExtra(Constants.PROJECT);
            profile = (Profile) getIntent().getSerializableExtra("profile");
        }

        binding.tvReceiverName.setText(profile.firstName + " " + profile.lastName);

        Glide.with(this).load(profile.profile_picture).error(R.color.orange).into(binding.imgProfile);
        binding.tvStatus.setText(capitalizeWords(profile.req_status));
        binding.tvBudget.setText(Utils.decimalFormat(String.valueOf(profile.total_service_price)) + " " + getString(R.string.sar));

        if (!TextUtils.isEmpty(profile.client_note)) {
            binding.txtNote.setText(profile.client_note);
        }

        if (profile.req_status.equals("pending")) {
            binding.tvStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.yellow_bg_20));
            binding.tvStatus.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else if (profile.req_status.equals("approved") || profile.req_status.equals("completed")) {
            binding.tvStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.green_button_bg_20));
            binding.tvStatus.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            binding.tvStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.red_bg_20));
            binding.tvStatus.setTextColor(ContextCompat.getColor(this, R.color.white));
        }

        Date date1 = Utils.changeDateFormat("yyyy-MM-dd hh:mm:ss", profile.req_status_updated_at);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dfFinal2;
        if (getLanguage().equals("ar")) {
            dfFinal2 = new SimpleDateFormat("dd MMM,yyyy");
        } else {
            dfFinal2 = new SimpleDateFormat("MMM dd,yyyy");
        }


        if (date1 != null) {
            if (printDifference(date1, date).equalsIgnoreCase("0")) {
                String result = p.format(Utils.changeDateFormat("yyyy-MM-dd hh:mm:ss", profile.req_status_updated_at));
                binding.txtDate.setText(getString(R.string.due_date) + " " + result);
            } else {
                String finalDate = dfFinal2.format(date1);
                binding.txtDate.setText(getString(R.string.due_date) + " " + finalDate);
            }
        }

        binding.imgBack.setOnClickListener(view -> onBackPressed());

        double agencyFee = campList.totalPrice * campList.agency_fee_rate;
        double taxTotal = (campList.totalPrice + agencyFee) * campList.tax_rate;

        binding.lblAgency.setText(getString(R.string.agency_fee) + " (" + Math.round(campList.agency_fee_rate * 100) + "%)");
        binding.lblTax.setText(getString(R.string.service_fee_10_1) + " (" + Math.round(campList.tax_rate * 100) + "%)");

        binding.tvTotal.setText(Utils.decimalFormat(String.valueOf(campList.totalPrice)) + " " + getString(R.string.sar));
        binding.tvAgencyFee.setText(Utils.decimalFormat(String.valueOf(agencyFee)) + " " + getString(R.string.sar));
        binding.tvServiceTax.setText(Utils.decimalFormat(String.valueOf(taxTotal)) + " " + getString(R.string.sar));
        binding.tvTotalPrice.setText(Utils.decimalFormat(String.valueOf(campList.getActualPrice())) + " " + getString(R.string.sar));

        if (campList.profiles != null && campList.profiles.get(0).is_released) {
            binding.txtReleaseAmount.setText(Utils.decimalFormat(String.valueOf(campList.getActualPrice())) + " " + getString(R.string.sar));
            binding.txtDepositAmount.setText(0 + " " + getString(R.string.sar));
            binding.imgChkReleased.setVisibility(View.VISIBLE);
            binding.imgChkDeposit.setVisibility(View.GONE);
        } else {
            binding.txtDepositAmount.setText(Utils.decimalFormat(String.valueOf(campList.getActualPrice())) + " " + getString(R.string.sar));
            binding.txtReleaseAmount.setText(0 + " " + getString(R.string.sar));
            binding.imgChkReleased.setVisibility(View.GONE);
            binding.imgChkDeposit.setVisibility(View.VISIBLE);
        }

        CampStarsPlatformAdapter adapter = new CampStarsPlatformAdapter(this, campList.services, null);
        binding.rvPlatform.setAdapter(adapter);

        if (!TextUtils.isEmpty(campList.campaignAttachmentUrl)) {
            String fName = getFileNameFromUrl(campList.campaignAttachmentUrl);
            binding.txtFileName.setText("" + fName);
            binding.txtFileSize.setText(fName.length() + " " + getString(R.string.kb) + " - " + "100% uploaded");
        }

        boolean isShowReleaseButton = false, isEnableReleaseButton = true;
        if (campList.profiles != null && campList.profiles.size() > 0) {
            for (Profile profile : campList.profiles) {
                if (profile.id.equals(this.profile.id)) {
                    if (profile.req_status.equals("completed") && !profile.is_released) {
                        isShowReleaseButton = true;
                    } else {
                        isEnableReleaseButton = false;
                    }
                    break;
                }
            }
        }

        if (campList.campaignStatus.equals("in_progress")) {

        } else if (campList.campaignStatus.equals("completed")) {
            binding.relRelease.setVisibility(View.VISIBLE);
            if (isShowReleaseButton) {
                binding.relRelease.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            } else if (!isEnableReleaseButton) {
                binding.relRelease.setVisibility(View.GONE);
                binding.txt1.setVisibility(View.GONE);
            }
        }

        boolean finalIsShowReleaseButton = isShowReleaseButton;
        binding.btnContinuePrice.setOnClickListener(view -> {
            //release payment in case of in-progress state only
            if (campList.campaignStatus.equals("completed")) {
                if (finalIsShowReleaseButton) {
                    campaignStarActivityVM.paymentRelease(profile.id, campList.campaignId);
                }
            }
        });

        campaignStarActivityVM.mutableSuccess.observe(this, integer -> {
            if (integer == 1) {
                setResult(RESULT_OK);
                finish();
            }
        });

        campaignStarActivityVM.mutableProgress.observe(this, aBoolean -> {
            if (aBoolean) {
                binding.btnContinuePrice.setVisibility(View.INVISIBLE);
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.btnContinuePrice.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        binding.txtChat.setOnClickListener(view -> {
            HashMap<String, String> chatMap = new HashMap<>();
            chatMap.put(Constants.RECEIVER_ID, profile.id + "");
            chatMap.put(Constants.RECEIVER_NAME, profile.username);
            chatMap.put(Constants.RECEIVER_PIC, profile.profile_picture);
            chatMap.put(Constants.SENDER_ID, getUserData().id + "");
            chatMap.put(Constants.SENDER_NAME, getUserData().username);
            chatMap.put(Constants.SENDER_PIC, getUserData().filePath.pathProfilePicClient + getUserData().profilePic);
            chatMap.put("isProject", "1");//1 mean updated record
//                if (expertGigDetail.gigType.equalsIgnoreCase("1")) {
//                    chatMap.put("projectType", "3");//2=job & 1= gig
//                } else {
            chatMap.put("projectType", "1");//2=job & 1= gig
//                }
            chatMap.put("isDetailScreen", "true");

            Intent chatIntent = new Intent(this, ChatMessagesActivity.class);
            chatIntent.putExtra(Constants.CHAT_ID, getUserData().id + "-" + profile.id);  // ClientId - AgentId
            chatIntent.putExtra(Constants.CHAT_DATA, chatMap);
            if (getIsVerified() == 1) {
                startActivity(chatIntent);
            } else {
                toastMessage(getString(R.string.verification_is_pending_please_complete_the_verification_first_before_chatting_with_them));
            }
        });
    }

    private String getFileNameFromUrl(String url) {
        // Use Uri class to parse the URL
        Uri uri = Uri.parse(url);

        // Get the last segment of the path, which is the file name
        return uri.getLastPathSegment();
    }


}
