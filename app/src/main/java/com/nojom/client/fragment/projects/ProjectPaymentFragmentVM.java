package com.nojom.client.fragment.projects;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentProjectPaymentNewBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.PaymentMethods;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.ui.clientprofile.ClientReviewActivity;
import com.nojom.client.ui.clientprofile.DepositFundsActivity;
import com.nojom.client.ui.projects.PaymentActivity;
import com.nojom.client.ui.projects.ProjectDetailsActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import io.intercom.android.sdk.Intercom;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.nojom.client.util.Constants.API_GET_PAYMENTMETHOD;
import static com.nojom.client.util.Constants.API_REFUND_JOB_POST;
import static com.nojom.client.util.Constants.API_RELEASE_INVOICE;

public class ProjectPaymentFragmentVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener {
    private FragmentProjectPaymentNewBinding binding;
    private BaseFragment fragment;
    private ProjectByID projectData;
    @SuppressLint("StaticFieldLeak")
    private CircularProgressBar progressBar;
    @SuppressLint("StaticFieldLeak")
    private TextView tvRelease;
    private Dialog releaseDialog;
    private CircularProgressBar progressBarRefund;
    private TextView tvConfirm;
    private Dialog refundDialog;

    ProjectPaymentFragmentVM(Application application, FragmentProjectPaymentNewBinding projectPaymentNewBinding, BaseFragment projectPaymentFragment) {
        super(application);
        binding = projectPaymentNewBinding;
        fragment = projectPaymentFragment;
        initData();
    }

    private void initData() {
        binding.tvDepositRelease.setOnClickListener(this);
        binding.txtRefund.setOnClickListener(this);

        if (fragment.activity != null) {
            projectData = ((ProjectDetailsActivity) fragment.activity).getProjectData();
        }

        if (projectData != null && projectData.jpstateId != null) {
            switch (projectData.jpstateId) {
                case Constants.BIDDING:
                case Constants.WAITING_FOR_AGENT_ACCEPTANCE:
                    binding.tvNoDeposit.setText(fragment.getString(R.string.no_payment_required));
                    setUiData();
                    binding.tvDepositDone.setText(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.sar__0_00) : fragment.getString(R.string.dollar__0_00));
                    binding.tvReleaseDone.setText(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.sar__0_00) : fragment.getString(R.string.dollar__0_00));
                    break;
                case Constants.WAITING_FOR_DEPOSIT:
                    binding.llPaymentStatus.setVisibility(VISIBLE);
                    binding.llBottom.setVisibility(VISIBLE);
                    binding.tvDepositRelease.setText(fragment.getString(R.string.deposit));
                    binding.tvDepositRelease.setBackground(ContextCompat.getDrawable(fragment.activity, R.drawable.blue_button_bg));
                    binding.tvNoDeposit.setText(fragment.getString(R.string.no_deposit_yet));
                    binding.tvDepositDone.setText(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.sar__0_00) : fragment.getString(R.string.dollar__0_00));
                    binding.tvReleaseDone.setText(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.sar__0_00) : fragment.getString(R.string.dollar__0_00));

                    if (projectData.bank_transfer_status != null && projectData.bank_transfer_status == 3) {//bank transfer reject case
                        binding.linDeposit.setBackgroundResource(R.drawable.pink_rounded_corner_10);
                        binding.imgChk.setImageResource(R.drawable.close_red);
                        binding.llDepositDone.setVisibility(VISIBLE);
                        binding.tvDepositRelease.setText(fragment.getString(R.string.deposit_again));
                        binding.tvConnect.setOnClickListener(v -> Intercom.client().displayMessageComposer());
                        binding.tvConnect.setVisibility(VISIBLE);
                        binding.tvBudget.setVisibility(GONE);
                        binding.txtRefund.setVisibility(VISIBLE);
                        binding.txtRefund.setText(fragment.getString(R.string.refused));
                        binding.txtRefund.setTextColor(fragment.getResources().getColor(R.color.C_FF3B30));
                    }


                    setUiData();
                    break;
                case Constants.IN_PROGRESS:
                    binding.llPaymentStatus.setVisibility(VISIBLE);
                    binding.llBottom.setVisibility(VISIBLE);
                    setUiData();
                    binding.tvDepositRelease.setText(fragment.getString(R.string.release));
                    binding.tvDepositRelease.setBackground(ContextCompat.getDrawable(fragment.activity, R.drawable.green_button_bg));
//                    binding.tvDepositDone.setText(String.format(Locale.US, fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", fragment.activity.get2DecimalPlaces(projectData.fixedPrice)));
                    setTotal(projectData.fixedPrice, projectData.jobPostContracts.depositCharges, binding.tvDepositDone, null);
                    binding.tvReleaseDone.setText(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.sar__0_00) : fragment.getString(R.string.dollar__0_00));

                    if (projectData.jrId != null) {
                        binding.txtRefund.setVisibility(GONE);
                    } else {
                        if (projectData.clientRateId != null && projectData.clientRateId == -1 && projectData.fixedPrice != null && projectData.fixedPrice == 0.0) {
                            binding.txtRefund.setVisibility(GONE);
                        } else {
                            binding.txtRefund.setVisibility(VISIBLE);
                        }
                    }

                    binding.llDepositDone.setVisibility(VISIBLE);
                    binding.tvNoDeposit.setText(fragment.getString(R.string.check_job_release_payment));
                    break;
                case Constants.SUBMIT_WAITING_FOR_PAYMENT:
                    binding.llPaymentStatus.setVisibility(VISIBLE);
                    binding.llBottom.setVisibility(VISIBLE);
                    setUiData();
                    binding.tvDepositRelease.setText(fragment.getString(R.string.release));
                    binding.tvDepositRelease.setBackground(ContextCompat.getDrawable(fragment.activity, R.drawable.green_button_bg));
                    binding.tvNoDeposit.setText(fragment.getString(R.string.check_job_release_payment));
                    setTotal(projectData.fixedPrice, projectData.jobPostContracts.depositCharges, binding.tvDepositDone, null);
                    binding.llDepositDone.setVisibility(VISIBLE);
                    if (projectData.jrId != null) {
                        binding.txtRefund.setVisibility(GONE);
                    } else {
                        if (projectData.clientRateId != null && projectData.clientRateId == -1 && projectData.fixedPrice != null && projectData.fixedPrice == 0.0) {
                            binding.txtRefund.setVisibility(GONE);
                        } else {
                            binding.txtRefund.setVisibility(VISIBLE);
                        }
                    }
                    binding.tvReleaseDone.setText(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.sar__0_00) : fragment.getString(R.string.dollar__0_00));
                    break;
                case Constants.COMPLETED:
                    binding.llPaymentStatus.setVisibility(VISIBLE);
                    binding.llTnc.setVisibility(GONE);
                    setUiData();
                    binding.tvNoDeposit.setText(fragment.getString(R.string.job_is_closed));
                    binding.llReleaseDone.setVisibility(VISIBLE);
                    if (projectData.jrId != null) {
                        binding.txtRefund.setVisibility(GONE);
                    } else {
                        if (projectData.clientRateId != null && projectData.clientRateId == -1 && projectData.fixedPrice != null && projectData.fixedPrice == 0.0) {
                            binding.txtRefund.setVisibility(GONE);
                        } else {
                            binding.txtRefund.setVisibility(VISIBLE);
                        }
                    }
                    try {
//                        binding.tvReleaseDone.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", fragment.activity.get2DecimalPlaces(projectData.fixedPrice)));
                        setTotal(projectData.fixedPrice, projectData.jobPostContracts.depositCharges, binding.tvReleaseDone, null);
                        binding.tvDepositDone.setText(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.sar__0_00) : fragment.getString(R.string.dollar__0_00));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    binding.llDepositDone.setVisibility(VISIBLE);
                    break;
                case Constants.CANCELLED:
                    binding.tvNoDeposit.setText(fragment.getString(R.string.no_payment_cancel));
                    break;
                case Constants.REFUNDED:
                    binding.tvNoDeposit.setText(fragment.getString(R.string.no_payment_refunded));
                    break;
                case Constants.BANK_TRANSFER_REVIEW:
                    binding.llPaymentStatus.setVisibility(VISIBLE);
                    binding.llBottom.setVisibility(VISIBLE);
                    binding.tvDepositRelease.setText(fragment.getString(R.string.release));
                    binding.tvDepositRelease.setEnabled(false);
                    binding.relBtn.setBackground(ContextCompat.getDrawable(fragment.activity, R.drawable.darkgray_button_bg));
                    binding.tvNoDeposit.setText(fragment.getString(R.string.no_deposit_yet));
//                    binding.tvDepositDone.setText(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.sar__0_00) : fragment.getString(R.string.dollar__0_00));
                    setTotal(projectData.fixedPrice, projectData.jobPostContracts.depositCharges, binding.tvDepositDone, null);
                    binding.tvReleaseDone.setText(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.sar__0_00) : fragment.getString(R.string.dollar__0_00));
                    binding.txtRefund.setVisibility(GONE);
                    binding.txtReview.setVisibility(VISIBLE);
                    setUiData();
                    break;
            }

            if (projectData.fixedPrice != null) {
                binding.linRange.setVisibility(GONE);
                binding.linTotal.setVisibility(VISIBLE);
                binding.linDepAmnt.setVisibility(VISIBLE);
                binding.linServAmnt.setVisibility(VISIBLE);
                binding.view.setVisibility(VISIBLE);
                if (fragment.activity != null) {
//                    binding.tvTotal.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", projectData.jobPostBudget.budget));
                    binding.txtDepAmnt.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", Utils.numberFormat(String.valueOf(projectData.fixedPrice))));
//                    double val = projectData.jobPostBudget.budget * projectData.jobPostContracts.depositCharges / 100;
//                    binding.txtServAmnt.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", Utils.numberFormat2Places(val)));
//                    double totalAmnt = val + projectData.jobPostBudget.budget;
//                    binding.txtTotalAmnt.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", Utils.numberFormat2Places(totalAmnt)));
                    setTotal(projectData.fixedPrice, projectData.jobPostContracts.depositCharges, binding.txtTotalAmnt, binding.txtServAmnt);
                }

            } else {
                binding.linRange.setVisibility(VISIBLE);
                binding.linTotal.setVisibility(GONE);
                binding.linDepAmnt.setVisibility(GONE);
                binding.linServAmnt.setVisibility(GONE);
                binding.view.setVisibility(GONE);
                if (!TextUtils.isEmpty(projectData.rangeTo)) {
                    binding.tvTotal.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar_s_sar) : "$%s - $%s", projectData.rangeFrom, projectData.rangeTo));
                } else if (!TextUtils.isEmpty(projectData.rangeFrom)) {
                    binding.tvTotal.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", projectData.rangeFrom));
                } else if (projectData.jobPostBudget != null && projectData.jobPostBudget.budget != null) {
                    binding.tvTotal.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", projectData.jobPostBudget.budget));
                } else {
                    binding.tvTotal.setText(fragment.getString(R.string.Free));
                }
            }


            Utils.MyClickableSpan reffCodeClick = new Utils.MyClickableSpan() {
                @Override
                public void onClick(@NotNull View view) {
                    fragment.activity.redirectUsingCustomTab(Constants.TERMS_USE);
                }
            };
            binding.tvTnc.setText(Utils.getColorString(fragment.activity, fragment.getString(R.string.refund_terms_and_conditions), fragment.getString(R.string.terms_and_conditions), R.color.colorPrimaryAlpha));
            Utils.makeLinks(binding.tvTnc, new String[]{fragment.getString(R.string.terms_and_conditions)}, new Utils.MyClickableSpan[]{reffCodeClick});
        }
        //in case of past project (Completed project) there is no option for refund, so hide that option
        if (fragment.activity != null && !((ProjectDetailsActivity) fragment.activity).isState()) {
            binding.txtRefund.setVisibility(GONE);
        }
        if (projectData != null && projectData.bank_transfer_status != null
                && projectData.bank_transfer_status == 3) {//bank transfer reject case
            binding.txtRefund.setVisibility(VISIBLE);
        }
    }

    private void setTotal(Double amount, Integer charge, TextView txtView, TextView txtService) {
        if (amount == null) {
            amount = 0.0;
        }
        double val = amount * charge / 100;
        if (txtService != null) {
            txtService.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", Utils.numberFormat(String.valueOf(val))));
        }
        double totalAmnt = val + amount;
        txtView.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", Utils.numberFormat(String.valueOf(totalAmnt))));
    }

    private void setUiData() {

//        binding.tvBudget.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", fragment.activity.get2DecimalPlaces(projectData.fixedPrice)));
        setTotal(projectData.fixedPrice, projectData.jobPostContracts.depositCharges, binding.tvBudget, null);
        if (projectData.agentDetails != null && projectData.agentDetails.firstName != null) {
            String txt = (fragment.getString(R.string.releasing_deposit_text, projectData.agentDetails.firstName));
//            binding.tvSatisfied.setText(fragment.getString(R.string.releasing_deposit_text, projectData.agentDetails.username));
            binding.tvSatisfied.setText(Utils.getColorString(fragment.activity, txt, projectData.agentDetails.firstName, R.color.colorPrimary));
        }

    }

    private void releasePayment(Double toalAmnt) {
        if (!fragment.activity.isNetworkConnected()) return;

        fragment.activity.isClickableView = true;
        tvRelease.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(VISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("job_post_id", projectData.jobPostId + "");
        map.put("job_post_bid_id", projectData.jpbId + "");
        map.put("amount", toalAmnt + "");
        map.put("payment_platform_id", "3");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_RELEASE_INVOICE, true, map);
    }

    private void refundPayment(String reason) {
        if (!fragment.activity.isNetworkConnected()) return;

        if (progressBarRefund != null) {
            progressBarRefund.setVisibility(VISIBLE);
            tvConfirm.setVisibility(View.INVISIBLE);
        }

        fragment.activity.isClickableView = true;

        HashMap<String, String> map = new HashMap<>();
        map.put("job_post_id", projectData.jobPostId + "");
        map.put("reason", reason);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_REFUND_JOB_POST, true, map);
    }

    private void releasePaymentDialog() {
        try {
            final Dialog dialog = new Dialog(fragment.activity);
            dialog.setTitle(null);
            dialog.setContentView(R.layout.dialog_release_payment);
            dialog.setCancelable(true);
            releaseDialog = dialog;
            TextView tvUsername = dialog.findViewById(R.id.tv_username);
            TextView tvTotal = dialog.findViewById(R.id.tv_total);
            RelativeLayout rlReleasePay = dialog.findViewById(R.id.rl_release_pay);
            TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
            tvRelease = dialog.findViewById(R.id.tv_release);
            progressBar = dialog.findViewById(R.id.progress_bar);

            String userName = projectData.agentDetails.firstName;
            tvUsername.setText(String.format(fragment.getString(R.string.release_payment_text), userName));
//            tvTotal.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", projectData.fixedPrice));
            double amount = projectData.fixedPrice;
            double val = amount * projectData.jobPostContracts.depositCharges / 100;

            double totalAmnt = val + amount;
            tvTotal.setText(String.format(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.s_sar) : "$%s", Utils.numberFormat(String.valueOf(totalAmnt))));

            rlReleasePay.setOnClickListener(v -> {
                releasePayment(totalAmnt);
            });

            tvCancel.setOnClickListener(v -> dialog.dismiss());

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.gravity = Gravity.TOP;
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refundPaymentDialog() {
        final Dialog dialog = new Dialog(fragment.activity);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_refund_payment);
        dialog.setCancelable(true);

        TextView tvConfirm = dialog.findViewById(R.id.tv_confirm);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);

        tvConfirm.setOnClickListener(v -> {
            dialog.dismiss();
            refundPaymentReasonDialog();
        });

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void refundPaymentReasonDialog() {
        final Dialog dialog = new Dialog(fragment.activity);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_refund_reason);
        dialog.setCancelable(true);
        refundDialog = dialog;
        tvConfirm = dialog.findViewById(R.id.tv_confirm);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView etReason = dialog.findViewById(R.id.edit_reason);
        TextView txt1 = dialog.findViewById(R.id.txt1);
        TextView txt2 = dialog.findViewById(R.id.txt2);
        progressBarRefund = dialog.findViewById(R.id.progress_bar);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
        radioGroup.clearCheck();


        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = group.findViewById(checkedId);
            if (null != rb) {
                if (rb.getText().equals(fragment.getString(R.string.other_1))) {
                    etReason.setVisibility(VISIBLE);
                } else {
                    etReason.setVisibility(GONE);
                }
            }
        });

        tvConfirm.setOnClickListener(v -> {
            RadioButton rb = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            String selectedReason = "";
            if (rb != null && !TextUtils.isEmpty(rb.getText())) {
                if (rb.getText().equals(fragment.getString(R.string.other))) {
                    selectedReason = etReason.getText().toString();
                } else if (rb.getText().equals(fragment.getString(R.string.did_not_meet_the_deadline))) {
                    selectedReason = "DEADLINE_NOT_MEET";
                } else if (rb.getText().equals(fragment.getString(R.string.not_satisfied_with_the_output))) {
                    selectedReason = "NOT_SATISFIED";
                }
            }
            if (TextUtils.isEmpty(selectedReason)) {
                fragment.activity.toastMessage("Please select reason");
                return;
            }

            refundPayment(selectedReason);
        });

        tvCancel.setOnClickListener(v -> {
            radioGroup.clearCheck();
            dialog.dismiss();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void thanksForPaymentDialog() {
        final Dialog dialog = new Dialog(fragment.activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_payment_done);
        dialog.setCancelable(true);

        LinearLayout llDeposit = dialog.findViewById(R.id.ll_deposit);

        llDeposit.setOnClickListener(v -> dialog.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

        dialog.setOnDismissListener(dialog1 -> {
            Intent i = new Intent(fragment.activity, ClientReviewActivity.class);
            i.putExtra(Constants.USER_DATA, projectData);
            fragment.startActivityForResult(i, 111);
        });
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111 && resultCode == RESULT_OK) {//after release payment and give rating to agent
            fragment.activity.gotoMainActivity(Constants.TAB_JOB_LIST);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_deposit_release) {
            if (projectData != null) {
                if (binding.tvDepositRelease.getText().toString().equals(fragment.getString(R.string.release))) {
                    releasePaymentDialog();
                } else if (projectData.jpstateId != Constants.BANK_TRANSFER_REVIEW) {
                    getPaymentMethod();
                }
            }
        } else if (view.getId() == R.id.txt_refund) {
            if (projectData != null && projectData.bank_transfer_status != null
                    && projectData.bank_transfer_status == 3) {//bank transfer reject case
                //Refused case
            } else {
                refundPaymentDialog();
            }
        }

    }

    private void getPaymentMethod() {
        if (!fragment.activity.isNetworkConnected()) return;

        fragment.activity.isClickableView = true;
        binding.tvDepositRelease.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_PAYMENTMETHOD, false, null);
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        fragment.activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_RELEASE_INVOICE)) {
            tvRelease.setVisibility(VISIBLE);
            progressBar.setVisibility(GONE);
            releaseDialog.dismiss();
            thanksForPaymentDialog();
        } else if (url.equalsIgnoreCase(API_GET_PAYMENTMETHOD)) {
            /*responseBody="{\n" +
                    "  \"client_balance\": 547.65,\n" +
                    "  \"payment_method\": [\n" +
                    "    {\n" +
                    "      \"id\": 6,\n" +
                    "      \"name\": \"Bank Card\",\n" +
                    "      \"payment_method\": \"Stripe\",\n" +
                    "      \"payment_method_id\": 2,\n" +
                    "      \"active\": \"1\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 7,\n" +
                    "      \"name\": \"Apple Pay\",\n" +
                    "      \"payment_method\": \"Stripe\",\n" +
                    "      \"payment_method_id\": 2,\n" +
                    "      \"active\": \"1\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 8,\n" +
                    "      \"name\": \"Bank Transfer\",\n" +
                    "      \"payment_method\": \"Stripe\",\n" +
                    "      \"payment_method_id\": 2,\n" +
                    "      \"active\": \"1\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";*/
            PaymentMethods paymentMethod = PaymentMethods.gePaymentMethodInfo(responseBody);
            binding.tvDepositRelease.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
            Preferences.setPaymentMethod(fragment.activity, paymentMethod);

//            Intent i = new Intent(fragment.activity, DepositFundsActivity.class);
            Intent i = new Intent(fragment.activity, PaymentActivity.class);
            i.putExtra(Constants.IS_FROM_GIG, false);
            i.putExtra(Constants.USER_DATA, projectData);
            fragment.startActivity(i);
        } else {
            if (progressBarRefund != null) {
                progressBarRefund.setVisibility(GONE);
                tvConfirm.setVisibility(VISIBLE);
            }

            refundDialog.dismiss();
            fragment.activity.gotoMainActivity(Constants.TAB_JOB_LIST);
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        fragment.activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_RELEASE_INVOICE)) {
            if (tvRelease != null && progressBar != null) {
                tvRelease.setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);
            }
        } else {
            if (progressBarRefund != null) {
                progressBarRefund.setVisibility(GONE);
                tvConfirm.setVisibility(VISIBLE);
            }
        }
    }
}
