package com.nojom.client.ui.clientprofile;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityPostJobNewBinding;
import com.nojom.client.model.Attachment;
import com.nojom.client.model.ClientRate;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.home.FindExpertActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class PostJobNewActivityVM extends AndroidViewModel implements View.OnClickListener {
    private ActivityPostJobNewBinding binding;
    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;
    private ArrayList<Attachment> fileList;
    private boolean isFromLawyerScreen;
    private String payType, cr, budget, deadline, attachLocalFile;
    private int crId;

    PostJobNewActivityVM(Application application, ActivityPostJobNewBinding postJobBinding, BaseActivity postJobActivity) {
        super(application);
        binding = postJobBinding;
        activity = postJobActivity;
        initData();
    }

    private void initData() {

        binding.loutPlatform.setOnClickListener(this);
        binding.loutService.setOnClickListener(this);
        binding.loutBudget.setOnClickListener(this);
        binding.loutDeadline.setOnClickListener(this);
        binding.loutDescribe.setOnClickListener(this);
        binding.loutSendOffer.setOnClickListener(this);
        binding.btnPostJob.setOnClickListener(this);

        ClientRate clientRate = Preferences.getClientRate(activity);
        if (clientRate != null && clientRate.data != null && clientRate.data.size() > 1) {
            if (activity.getCurrency().equals("SAR")) {
                binding.txtBudget.setText(clientRate.data.get(1).rangeFrom + " - " + clientRate.data.get(1).rangeTo + " "+activity.getString(R.string.sar));
            } else {
                binding.txtBudget.setText(activity.getString(R.string.dollar) + clientRate.data.get(1).rangeFrom + " - $" + clientRate.data.get(1).rangeTo);
            }

            crId = clientRate.data.get(1).id;
            cr = binding.txtBudget.getText().toString();
        }
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int min = Calendar.getInstance().get(Calendar.MINUTE);
        deadline = getCalculatedDate("yyyy-MM-dd", 5) + " " + hour + ":" + min;
        binding.txtDeadline.setText(Utils.setDeadLine(deadline, activity));

        binding.txtPlatform.setTag("0");
        binding.txtCategory.setTag("0");

        setOfferData();
        checkStatus();
    }

    private void setOfferData() {
        List<ExpertLawyers.Data> expertUserList = Preferences.getExpertUsers(activity);
        if (expertUserList != null && expertUserList.size() > 0) {
            String expertUsers = null;
            for (ExpertLawyers.Data expertUser : expertUserList) {
                if (TextUtils.isEmpty(expertUsers)) {
                    expertUsers = expertUser.username;
                } else {
                    expertUsers = expertUser.username + ", " + expertUsers;
                }
            }
            binding.txtSendOffer.setText(expertUsers);
        } else {
            binding.txtSendOffer.setText(activity.getString(R.string.all_influencers));
        }
    }

    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    private void checkStatus() {
        boolean isValid = true;

        if (TextUtils.isEmpty(binding.txtDescribe.getText().toString()) || binding.txtDescribe.getText().toString().equals(activity.getString(R.string.enter))) {
            isValid = false;
        }

        if (isValid) {
            binding.btnPostJob.setEnabled(true);
            binding.btnPostJob.setBackground(activity.getResources().getDrawable(R.drawable.green_button_bg_20));
        } else {
            binding.btnPostJob.setEnabled(false);
            binding.btnPostJob.setBackground(activity.getResources().getDrawable(R.drawable.gray_button_bg_20));
        }
    }

    public void onBackPressed() {
        int backStackEntryCount = activity.getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            activity.onBackPressedEvent();
        } else {
            activity.getSupportFragmentManager().popBackStack();
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity, PostJobActivity.class);

        if (binding.txtPlatform.getTag() != null) {
            intent.putExtra(Constants.PLATFORM_ID, binding.txtPlatform.getTag().toString());
        }
        if (!TextUtils.isEmpty(binding.txtPlatform.getText().toString())) {
            intent.putExtra(Constants.PLATFORM_NAME, binding.txtPlatform.getText().toString());
        }
        intent.putExtra(Constants.SKILL_NAMES, binding.txtCategory.getText().toString());
        if (binding.txtCategory.getTag() != null) {
            intent.putExtra(Constants.SKILL_IDS, binding.txtCategory.getTag().toString());
        }
        if (!TextUtils.isEmpty(payType)) {
            intent.putExtra(Constants.PAY_TYPE, payType);
        }
        if (!TextUtils.isEmpty(cr)) {
            intent.putExtra(Constants.CLIENT_RATE, cr);
        }
        intent.putExtra(Constants.CLIENT_RATE_ID, crId);
        if (!TextUtils.isEmpty(budget)) {
            intent.putExtra(Constants.BUDGET, budget);
        }
        if (!TextUtils.isEmpty(deadline)) {
            intent.putExtra("deadline", deadline);
        }
        if (!TextUtils.isEmpty(binding.txtDescribe.getText().toString()) && !binding.txtDescribe.getText().toString().equals("Enter")) {
            intent.putExtra(Constants.DESCRIBE, binding.txtDescribe.getText().toString());
        }
//        intent.putExtra(Constants.ATTACH_LOCAL_FILE, TextUtils.isEmpty(attachLocalFile) ? "" : attachLocalFile);

        switch (view.getId()) {
            case R.id.lout_platform:
                activity.startActivityForResult(intent, 1010);
                break;
            case R.id.lout_service:
                intent.putExtra("tab", "serv");
                /*if (binding.txtPlatform.getTag() != null) {
                    intent.putExtra(Constants.PLATFORM_ID, binding.txtPlatform.getTag().toString());
                }
                if (!TextUtils.isEmpty(binding.txtPlatform.getText().toString())) {
                    intent.putExtra(Constants.PLATFORM_NAME, binding.txtPlatform.getText().toString());
                }

                intent.putExtra(Constants.SKILL_NAMES, binding.txtCategory.getText().toString());
                if (binding.txtCategory.getTag() != null) {
                    intent.putExtra(Constants.SKILL_IDS, binding.txtCategory.getTag().toString());
                }
                if (!TextUtils.isEmpty(binding.txtDescribe.getText().toString()) && !binding.txtDescribe.getText().toString().equals("Enter")) {
                    intent.putExtra(Constants.DESCRIBE, binding.txtDescribe.getText().toString());
                }
                if (!TextUtils.isEmpty(deadline)) {
                    intent.putExtra("deadline", deadline);
                }*/
                activity.startActivityForResult(intent, 1010);
                break;
            case R.id.lout_budget:
                intent.putExtra("tab", "rate");

                /*if (binding.txtPlatform.getTag() != null) {
                    intent.putExtra(Constants.PLATFORM_ID, binding.txtPlatform.getTag().toString());
                }
                if (!TextUtils.isEmpty(binding.txtPlatform.getText().toString())) {
                    intent.putExtra(Constants.PLATFORM_NAME, binding.txtPlatform.getText().toString());
                }

                if (!TextUtils.isEmpty(payType)) {
                    intent.putExtra(Constants.PAY_TYPE, payType);
                }

                intent.putExtra(Constants.SKILL_NAMES, binding.txtCategory.getText().toString());
                if (binding.txtCategory.getTag() != null) {
                    intent.putExtra(Constants.SKILL_IDS, binding.txtCategory.getTag().toString());
                }
                if (!TextUtils.isEmpty(binding.txtDescribe.getText().toString()) && !binding.txtDescribe.getText().toString().equals("Enter")) {
                    intent.putExtra(Constants.DESCRIBE, binding.txtDescribe.getText().toString());
                }
                if (!TextUtils.isEmpty(deadline)) {
                    intent.putExtra("deadline", deadline);
                }*/
                activity.startActivityForResult(intent, 1010);
                break;
            case R.id.lout_deadline:
                intent.putExtra("tab", "deadline");

                /*if (binding.txtPlatform.getTag() != null) {
                    intent.putExtra(Constants.PLATFORM_ID, binding.txtPlatform.getTag().toString());
                }
                if (!TextUtils.isEmpty(binding.txtPlatform.getText().toString())) {
                    intent.putExtra(Constants.PLATFORM_NAME, binding.txtPlatform.getText().toString());
                }

                if (!TextUtils.isEmpty(payType)) {
                    intent.putExtra(Constants.PAY_TYPE, payType);
                }
                if (!TextUtils.isEmpty(cr)) {
                    intent.putExtra(Constants.CLIENT_RATE, cr);
                }
                intent.putExtra(Constants.CLIENT_RATE_ID, crId);
                if (!TextUtils.isEmpty(budget)) {
                    intent.putExtra(Constants.BUDGET, budget);
                }
                if (!TextUtils.isEmpty(deadline)) {
                    intent.putExtra("deadline", deadline);
                }
                intent.putExtra(Constants.SKILL_NAMES, binding.txtCategory.getText().toString());
                if (binding.txtCategory.getTag() != null) {
                    intent.putExtra(Constants.SKILL_IDS, binding.txtCategory.getTag().toString());
                }
                if (!TextUtils.isEmpty(binding.txtDescribe.getText().toString()) && !binding.txtDescribe.getText().toString().equals("Enter")) {
                    intent.putExtra(Constants.DESCRIBE, binding.txtDescribe.getText().toString());
                }*/

                activity.startActivityForResult(intent, 1010);
                break;
            case R.id.lout_describe:
                intent.putExtra("tab", "desc");


                activity.startActivityForResult(intent, 1010);
                break;
            case R.id.lout_send_offer:
                Intent intento = new Intent(activity, FindExpertActivity.class);
                intento.putExtra("screen", true);
                activity.startActivityForResult(intento, 12121);
                break;
            case R.id.btn_post_job:

                if (!activity.isLogin()) {
                    activity.openLoginDialog();
                    return;
                }

                intent = new Intent(activity, PostJobTitleActivity.class);
                if (binding.txtPlatform.getTag() != null) {
                    intent.putExtra(Constants.PLATFORM_ID, binding.txtPlatform.getTag().toString());
                }
                if (!TextUtils.isEmpty(binding.txtPlatform.getText().toString())) {
                    intent.putExtra(Constants.PLATFORM_NAME, binding.txtPlatform.getText().toString());
                }
                intent.putExtra(Constants.SKILL_NAMES, binding.txtCategory.getText().toString());
                if (binding.txtCategory.getTag() != null) {
                    intent.putExtra(Constants.SKILL_IDS, binding.txtCategory.getTag().toString());
                }
                if (!TextUtils.isEmpty(payType)) {
                    intent.putExtra(Constants.PAY_TYPE, payType);
                }
                if (!TextUtils.isEmpty(cr)) {
                    intent.putExtra(Constants.CLIENT_RATE, cr);
                }
                intent.putExtra(Constants.CLIENT_RATE_ID, crId);
                if (!TextUtils.isEmpty(budget)) {
                    intent.putExtra(Constants.BUDGET, budget);
                }
                if (!TextUtils.isEmpty(deadline)) {
                    intent.putExtra("deadline", deadline);
                }
                intent.putExtra(Constants.DESCRIBE, binding.txtDescribe.getText().toString());
                intent.putExtra(Constants.ATTACH_LOCAL_FILE, TextUtils.isEmpty(attachLocalFile) ? "" : attachLocalFile);
                activity.startActivity(intent);
                break;
        }
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    fileList = new ArrayList<>();

                    if (data.hasExtra(Constants.PLATFORM_NAME)) {
                        binding.txtPlatform.setText("" + data.getStringExtra(Constants.PLATFORM_NAME));
                        binding.txtPlatform.setTag("" + data.getStringExtra(Constants.PLATFORM_ID));
                    }
                    if (data.hasExtra(Constants.SKILL_NAMES) && data.hasExtra(Constants.SKILL_IDS)) {
                        if (!data.getStringExtra(Constants.SKILL_NAMES).equalsIgnoreCase("null") && !TextUtils.isEmpty(data.getStringExtra(Constants.SKILL_NAMES))) {
                            binding.txtCategory.setText("" + data.getStringExtra(Constants.SKILL_NAMES));
                            binding.txtCategory.setTag("" + data.getStringExtra(Constants.SKILL_IDS));
                        }
                    }
                    if (data.hasExtra(Constants.BUDGET) && data.hasExtra(Constants.CLIENT_RATE)) {
                        String budget = data.getStringExtra(Constants.BUDGET);
                        String clientRate = data.getStringExtra(Constants.CLIENT_RATE);
                        if (data.hasExtra(Constants.CLIENT_RATE_ID)) {
                            crId = data.getIntExtra(Constants.CLIENT_RATE_ID, 0);
                        }
                        cr = clientRate;
                        this.budget = budget;

                        boolean isFixedPrice = Preferences.readBoolean(activity, Constants.IS_FIXED_PRICE, false);

                        if (TextUtils.isEmpty(clientRate)) {
                            if (TextUtils.isEmpty(budget) || budget.equals("null")) {
                                binding.txtBudget.setText(activity.getString(R.string.free).toUpperCase());
                            } else {
                                binding.txtBudget.setText(isFixedPrice ? (activity.getCurrency().equals("SAR") ? budget + " "+activity.getString(R.string.sar) : activity.getString(R.string.dollar) + budget) : budget + activity.getString(R.string.hr));
                            }
                        } else {
                            binding.txtBudget.setText(isFixedPrice ? clientRate : clientRate + activity.getString(R.string.hr));
                        }
                        binding.txtBudget.setTextColor(activity.getResources().getColor(R.color.black));
                    }
                    if (data.hasExtra("deadline")) {
                        String deadline = data.getStringExtra("deadline");
                        this.deadline = deadline;
                        binding.txtDeadline.setText(TextUtils.isEmpty(deadline) ? " " : Utils.setDeadLine(deadline, activity));
                    } else {
                        binding.txtDeadline.setText(TextUtils.isEmpty(deadline) ? " " : Utils.setDeadLine(deadline, activity));//lawyer case or when there is no need of deadline
                    }

                    if (data.hasExtra(Constants.DESCRIBE)) {
                        if (!TextUtils.isEmpty(data.getStringExtra(Constants.DESCRIBE))) {
                            binding.txtDescribe.setText("" + data.getStringExtra(Constants.DESCRIBE));
                            binding.txtDescribe.setTextColor(activity.getResources().getColor(R.color.black));
                        } else {
                            binding.txtDescribe.setText(activity.getString(R.string.enter));
                        }
                    }
                    setOfferData();
                    if (data.hasExtra(Constants.ATTACH_LOCAL_FILE)) {
                        attachLocalFile = data.getStringExtra(Constants.ATTACH_LOCAL_FILE);
                        if (!TextUtils.isEmpty(attachLocalFile)) {
                            String[] filesSplit = attachLocalFile.split(",");
                            for (String aFilesSplit : filesSplit) {
                                if (aFilesSplit.contains("png") || aFilesSplit.contains("jpg") || aFilesSplit.contains("jpeg")) {
                                    fileList.add(new Attachment(aFilesSplit, "", "", true));
                                } else {
                                    fileList.add(new Attachment(aFilesSplit, "", "", false));
                                }
                            }
                        }
                    }

                    if (data.hasExtra(Constants.PAY_TYPE)) {
                        payType = data.getStringExtra(Constants.PAY_TYPE);
                    }
                }
                checkStatus();
            } else {//in case of select expert
                if (requestCode == 12121) {
                    setOfferData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
