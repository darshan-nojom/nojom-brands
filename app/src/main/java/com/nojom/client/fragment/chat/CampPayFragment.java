package com.nojom.client.fragment.chat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentCampPayBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.CampList;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.projects.CampByIdVM;
import com.nojom.client.ui.projects.CampaignDetailActivity2;
import com.nojom.client.ui.projects.CampaignStarActivityVM;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

public class CampPayFragment extends BaseFragment {
    private FragmentCampPayBinding binding;
    private CampList campList;
    private CampaignStarActivityVM campaignStarActivityVM;
    private CampByIdVM campByIdVM;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camp_pay, container, false);

        campaignStarActivityVM = new CampaignStarActivityVM(Task24Application.getInstance(), activity);
        campByIdVM = new CampByIdVM(Task24Application.getInstance(), activity);
        campList = ((CampaignDetailActivity2) activity).campList;
//        renderView();
        observer();
        return binding.getRoot();
    }

    private void observer() {
        campaignStarActivityVM.mutableProgress.observe(activity, aBoolean -> {
            if (aBoolean) {
                binding.btnRelease.setVisibility(View.INVISIBLE);
                binding.progressBarSignup.setVisibility(View.VISIBLE);
            } else {
                binding.btnRelease.setVisibility(View.VISIBLE);
                binding.progressBarSignup.setVisibility(View.GONE);
            }
        });


        campaignStarActivityVM.mutableSuccess.observe(activity, aBoolean -> {
            if (aBoolean == 1) {

                campByIdVM.campListData.observe(activity, campListData -> {
                    campList.campaignStatus = campListData.campaign_status;
                    if (campList.profiles != null && campList.profiles.size() > 0) {
                        for (Profile profile : campList.profiles) {
                            for (Profile agent : campListData.profiles) {
                                if (profile.id.equals(agent.id)) {
                                    profile.is_released = agent.is_released;
                                    profile.req_status = agent.req_status;
                                    break;
                                }
                            }
                        }
                    }
                    renderView();
                });

                campByIdVM.getCampaignById(campList.campaignId);
            }
        });
    }

    private void renderView() {
        try {
            double agencyFee = campList.totalPrice * campList.agency_fee_rate;
            double taxTotal = (campList.totalPrice + agencyFee) * campList.tax_rate;

            binding.lblAgency.setText(getString(R.string.agency_fee) + " (" + Math.round(campList.agency_fee_rate * 100) + "%)");
            binding.lblTax.setText(getString(R.string.service_fee_10_1) + " (" + Math.round(campList.tax_rate * 100) + "%)");

            binding.tvTotal.setText(Utils.decimalFormat(String.valueOf(campList.totalPrice)) + " " + activity.getString(R.string.sar));
            binding.tvAgencyFee.setText(Utils.decimalFormat(String.valueOf(agencyFee)) + " " + activity.getString(R.string.sar));
            binding.tvServiceTax.setText(Utils.decimalFormat(String.valueOf(taxTotal)) + " " + activity.getString(R.string.sar));
            binding.tvTotalPrice.setText(Utils.decimalFormat(String.valueOf(campList.getActualPrice())) + " " + activity.getString(R.string.sar));

            if (campList.profiles != null && campList.profiles.get(0).is_released) {
                binding.txtReleaseAmount.setText(Utils.decimalFormat(String.valueOf(campList.getActualPrice())) + " " + activity.getString(R.string.sar));
                binding.txtDepositAmount.setText(0 + " " + activity.getString(R.string.sar));
                binding.imgChkReleased.setVisibility(View.VISIBLE);
                binding.imgChkDeposit.setVisibility(View.GONE);
            } else {
                binding.txtDepositAmount.setText(Utils.decimalFormat(String.valueOf(campList.getActualPrice())) + " " + activity.getString(R.string.sar));
                binding.txtReleaseAmount.setText(0 + " " + activity.getString(R.string.sar));
                binding.imgChkReleased.setVisibility(View.GONE);
                binding.imgChkDeposit.setVisibility(View.VISIBLE);
            }
            boolean isShowReleaseButton = false, isEnableReleaseButton = true;
            if (campList.profiles != null && campList.profiles.size() > 0) {
                for (Profile profile : campList.profiles) {
                    if (profile.req_status.equals("completed") && !profile.is_released) {
                        isShowReleaseButton = true;
                    } else {
                        isEnableReleaseButton = false;
                    }
                }
            }

            if (campList.campaignStatus != null) {
                if (campList.campaignStatus.equals("in_progress")) {

                } else if (campList.campaignStatus.equals("completed")) {
                    binding.relRelease.setVisibility(View.VISIBLE);
                    if (isShowReleaseButton && isEnableReleaseButton) {
                        binding.relRelease.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    } else if (!isShowReleaseButton && !isEnableReleaseButton) {
                        binding.relRelease.setVisibility(View.GONE);
                        binding.txt1.setVisibility(View.GONE);
                        binding.txt2.setVisibility(View.GONE);
                        binding.txt3.setVisibility(View.GONE);
                        binding.txt4.setVisibility(View.GONE);
                        binding.txt5.setVisibility(View.GONE);
                    }
                }
            }
            boolean finalIsShowReleaseButton = isShowReleaseButton;
            boolean finalIsEnableReleaseButton = isEnableReleaseButton;
            binding.btnRelease.setOnClickListener(view -> {
                //release payment in case of in-progress state only
                if (campList.campaignStatus != null && campList.campaignStatus.equals("completed")) {
                    if (finalIsShowReleaseButton && finalIsEnableReleaseButton) {
                        campaignStarActivityVM.paymentRelease(campList.profiles.get(0).id, campList.campaignId);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        renderView();
    }

    public void getdata(CampList campList) {
        this.campList = campList;
        //renderView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
