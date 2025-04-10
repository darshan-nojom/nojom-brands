package com.nojom.client.ui.projects;

import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityPaymentNewBinding;
import com.nojom.client.databinding.DialogPayDoneBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.AgentService;
import com.nojom.client.model.Agents;
import com.nojom.client.model.Attachment;
import com.nojom.client.model.Campaign;
import com.nojom.client.model.InfServices;
import com.nojom.client.model.Serv;
import com.nojom.client.model.WalletData;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.AddCardActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentMethodActivity extends BaseActivity {

    ActivityPaymentNewBinding binding;
    private InfServices influencerServices;
    //    ArrayList<SocialPlatformList.Data> connectedMediaList;
    private CampaignDataActivityVM campaignDataActivityVM;
    private List<Agents> agentList;
    private String notes, title, date, time, brief;
    private ArrayList<Attachment> fileList = new ArrayList<>();
    private double total;
    private boolean isWallet = false;
    private AgentProfile agentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_new);
        campaignDataActivityVM = new CampaignDataActivityVM(Task24Application.getInstance(), this);
        initData();
    }

    private void initData() {
        if (getIntent() != null) {

            agentData = (AgentProfile) getIntent().getSerializableExtra(AGENT_PROFILE_DATA);

            if (getIntent().hasExtra("data")) {
                influencerServices = (InfServices) getIntent().getSerializableExtra("data");
            }
            if (getIntent().hasExtra("selData")) {
                agentList = (List<Agents>) getIntent().getSerializableExtra("selData");
            }
            if (getIntent().hasExtra("files")) {
                fileList = (ArrayList<Attachment>) getIntent().getSerializableExtra("files");
            }
            notes = getIntent().getStringExtra("notes");
            title = getIntent().getStringExtra("title");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            brief = getIntent().getStringExtra("brief");
            total = getIntent().getDoubleExtra("total", 0.0);
//            connectedMediaList = (ArrayList<SocialPlatformList.Data>) getIntent().getSerializableExtra("social");
        }

        List<WalletData> ratesData = Preferences.getRates(this);
        double agencyFeeRate = 0;
        double servTaxFeeRate = 0;
        for (WalletData data : ratesData) {
            if (Objects.equals(data.rate_type, "tax") && data.is_active == 1) {
                servTaxFeeRate = data.rate_value * 100;
            } else if (Objects.equals(data.rate_type, "agency_fee") && data.is_active == 1) {
                agencyFeeRate = data.rate_value * 100;
            }
        }

        double agencyFee = calculatePrice() * agencyFeeRate / 100;
        double servTax = (calculatePrice() + agencyFee) * servTaxFeeRate / 100;
        total = calculatePrice() + agencyFee + servTax;

        binding.txtLblTax.setText(String.format(getString(R.string.service_tax_s), Utils.numberFormat(servTaxFeeRate)) + "%)");
        binding.txtLblAgency.setText(String.format(getString(R.string.agency_fee_s), Utils.numberFormat(agencyFeeRate)) + "%)");
        binding.txtJobId.setText(agentData.id + " ");
        binding.txtBal.setText("0 SAR");
        binding.txtDepAmount.setText(Utils.numberFormat(calculatePrice()) + " " + getString(R.string.sar));
//        double fee = calculatePrice() * 5 / 100;
//        double adjustedTotal = calculatePrice() + fee;
        binding.txtAgencyFee.setText(Utils.numberFormat(agencyFee) + " " + getString(R.string.sar));
        binding.txtServAmount.setText(Utils.numberFormat(servTax) + " " + getString(R.string.sar));
        binding.txtTotal.setText(Utils.numberFormat(total) + " " + getString(R.string.sar));

        binding.txtPayTitle.setText(getString(R.string.how_would_you_like_to_pay) + " " + Utils.numberFormat(total) + " " + getString(R.string.sar) + "?");
        binding.btnContinuePrice.setText(getString(R.string.pay) + " " + Utils.numberFormat(total) + " " + getString(R.string.sar));

        binding.imgBack.setOnClickListener(view -> onBackPressed());

        binding.relVisa.setOnClickListener(view -> {
            binding.relVisa.setBackground(getResources().getDrawable(R.drawable.blue_border_8));
            binding.relWallet.setBackground(getResources().getDrawable(R.drawable.black_border_8));
            binding.imgChkVisa.setVisibility(View.VISIBLE);
            binding.imgChkWallet.setVisibility(View.GONE);
            isWallet = false;
        });
        binding.relWallet.setOnClickListener(view -> {
            binding.relWallet.setBackground(getResources().getDrawable(R.drawable.blue_border_8));
            binding.relVisa.setBackground(getResources().getDrawable(R.drawable.black_border_8));
            binding.imgChkWallet.setVisibility(View.VISIBLE);
            binding.imgChkVisa.setVisibility(View.GONE);
            isWallet = true;
        });
        binding.btnContinuePrice.setOnClickListener(view -> {
//            if (isWallet) {

//            } else {


            if (fileList != null && fileList.size() > 0) {
                campaignDataActivityVM.uploadAttachment(fileList.get(0).filepath);
            } else {
                createCampaign(null);
            }

        });

        campaignDataActivityVM.mutableUploadedFileUrl.observe(this, this::createCampaign);

//        campaignDataActivityVM.mutableCampId.observe(this, campId -> {
//            CampaignPay campaignPay = new CampaignPay(total, "sar");
//            campaignDataActivityVM.createCampaignPayment(campaignPay, campId);
//        });


        campaignDataActivityVM.mutableProgress.observe(this, aBoolean -> {
            if (aBoolean) {
                binding.btnContinuePrice.setVisibility(View.INVISIBLE);
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.btnContinuePrice.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });

        campaignDataActivityVM.mutableWalletSuccess.observe(this, integer -> {
            if (integer == 1) {
                postDoneDialog(true);
            }
        });
//        CardFormViewModel moneyHashHelper = new CardFormViewModel();

        campaignDataActivityVM.mutableIntentId.observe(this, intentId -> {
            if (!isWallet) {
//                Intent intent = CardFormActivity.createIntent(intentId);
//                startActivity(intent);
                Intent intent = new Intent(this, AddCardActivity.class);
                intent.putExtra("intentId", intentId);
                startActivityForResult(intent, 121);
            }
//            moneyHashHelper.processPayment(intentId, false, null, null, null);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 121) {
            postDoneDialog(true);
        } else if (resultCode == RESULT_CANCELED && requestCode == 121) {
            postDoneDialog(false);
        }
    }

    private void createCampaign(String attachUrl) {
        List<Campaign.Stars> campStars = new ArrayList<>();
        if (influencerServices != null) {//when coming direct from detail screen (without open add more star screen)
            List<Campaign.Service> campServ = new ArrayList<>();
            for (Serv infS : influencerServices.services) {
                if (infS.isChecked && infS.id != -1) {//if all platform not checked that time pass specific services
                    campServ.add(new Campaign.Service(infS.service_id));
                } else if (infS.isChecked) {//if all platform checked then pass only 1 Ids
                    campServ.clear();
                    campServ.add(new Campaign.Service(infS.service_id));
                    break;
                }
            }
            if (campServ.size() > 0) {
                campStars.add(new Campaign.Stars(agentData.id, campServ, notes));
            }
        } else {//when coming from add more star screen
            for (Agents infS : agentList) {
                List<Campaign.Service> campServ = new ArrayList<>();
                for (AgentService serv : infS.services) {
                    if (serv.isChecked && serv.socialPlatformId != -1) {//if all platform not checked that time pass specific services
                        campServ.add(new Campaign.Service(serv.serviceId));
                    } else if (serv.isChecked) {//if all platform checked then pass only 1 Ids
                        campServ.clear();
                        campServ.add(new Campaign.Service(serv.serviceId));
                        break;
                    }
                }
                if (campServ.size() > 0) {
                    campStars.add(new Campaign.Stars(infS.id, campServ, infS.notes));
                }
            }
        }
        Campaign campaign = new Campaign(title, date + " " + time, brief, attachUrl, campStars/*Arrays.asList(service1, service2, service3)*/, isWallet);

        campaignDataActivityVM.createCampaign(campaign);
    }

    public double calculatePrice() {
        double finalPrice = 0;
        if (influencerServices != null) {//when coming direct from detail screen (without open add more star screen)
            for (Serv data : influencerServices.services) {
                if (data.price > 0 && data.isChecked && data.id == -1) {
                    finalPrice = data.price;
                    break;
                } else if (data.price > 0 && data.isChecked) {
                    finalPrice = finalPrice + data.price;
                }
            }
        } else {//when coming from add more star screen
            double subTotalPrice = 0;
            for (Agents agents : agentList) {
                if (agents.getPrice(agents.services) > 0) {
                    subTotalPrice = subTotalPrice + agents.getPrice(agents.services);
                }
            }
            finalPrice = subTotalPrice;
        }

        return finalPrice;

    }

    private void postDoneDialog(boolean isSuccess) {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle(null);
        DialogPayDoneBinding bindingDialog = DialogPayDoneBinding.inflate(LayoutInflater.from(this));
        dialog.setContentView(bindingDialog.getRoot());
        dialog.setCancelable(true);

        if (!isSuccess) {
            bindingDialog.imgDone.setImageResource(R.drawable.ic_pay_fail);
            bindingDialog.txtTitle.setText(getString(R.string.payment_failed));
            bindingDialog.txtDesc.setText(getString(R.string.unfortunately_we_were_unable_to_process_your_payment_please_try_again_or_use_a_different_payment_method) + "\n");
            bindingDialog.txtDesc1.setText(getString(R.string.if_you_continue_to_experience_issues_our_support_team_is_here_to_help));
            bindingDialog.btnContinuePrice.setText(getString(R.string.try_again));
        }

        bindingDialog.btnContinuePrice.setOnClickListener(view -> {
            if (isSuccess) {
                gotoMainActivity(Constants.TAB_JOB_LIST);
            } else {
                dialog.dismiss();
                //finish();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        lp.gravity = Gravity.CENTER;
        lp.width = (int) (displaymetrics.widthPixels * 0.9);
        lp.height = (int) (displaymetrics.heightPixels * 0.7);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialog.setOnDismissListener(dialog1 -> {
            isClickableView = false;
            if (isSuccess) {
                gotoMainActivity(Constants.TAB_JOB_LIST);
            } else {
                dialog.dismiss();
                //finish();
            }
        });
    }
}
