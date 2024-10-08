package com.nojom.client.ui.home;

import static com.nojom.client.util.Constants.API_GET_ACCEPT_OR_REJECT_OFFER;
import static com.nojom.client.util.Constants.API_GET_PAYMENTMETHOD;
import static com.nojom.client.util.Constants.API_GET_PROFILE_INFO;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.text.TextUtils;
import android.textview.CustomTextView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nojom.client.R;
import com.nojom.client.adapter.CustomDeadlineAdapter;
import com.nojom.client.adapter.CustomGigAdapter;
import com.nojom.client.adapter.GigAdapter;
import com.nojom.client.adapter.GigPagerAdapter;
import com.nojom.client.adapter.SocialGigAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.ActivityGigDetailsBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.CommonResponse;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.PaymentMethods;
import com.nojom.client.model.Requirement;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.MainActivity;
import com.nojom.client.ui.RateClickListener;
import com.nojom.client.ui.chat.ChatMessagesActivity;
import com.nojom.client.ui.clientprofile.DepositFundsActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.ui.projects.InfluencerProfileActivityCopy;
import com.nojom.client.ui.projects.PaymentActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.MyDownloadManager;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.ReadMoreTextView;
import com.nojom.client.util.SaveRemoveGigClickListener;
import com.nojom.client.util.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GigDetailActivityVM extends AndroidViewModel implements View.OnClickListener, RequestResponseListener, SaveRemoveGigClickListener, RateClickListener, CustomGigAdapter.OnItemClick, SocialGigAdapter.OnItemClick {
    private final ActivityGigDetailsBinding binding;
    @SuppressLint("StaticFieldLeak")
    private final BaseActivity activity;
    private final int[] posCustomDelivery = {0};
    private final ArrayList<TextView> tvList = new ArrayList<>();
    private GigPagerAdapter gigPagerAdapter;
    @SuppressLint("StaticFieldLeak")
    private TextView textViewName;
    private ExpertGigDetail expertGigDetail;
    private int selectedPackagePosition = 0;
    private double deadlinePrice = 0;

    public GigDetailActivityVM(@NonNull Application application, ActivityGigDetailsBinding activityGigDetailsBinding, BaseActivity findDesignersActivity) {
        super(application);
        this.binding = activityGigDetailsBinding;
        this.activity = findDesignersActivity;
        initData();
    }

    private void initData() {
        binding.imgBack.setOnClickListener(this);
        binding.loutContinue.setOnClickListener(this);
        binding.imgMinus.setOnClickListener(this);
        binding.imgPlus.setOnClickListener(this);
        binding.imgDownload.setOnClickListener(this);
        binding.imgFavourite.setOnClickListener(this);
        binding.tvViewAll.setOnClickListener(this);
        binding.loutChat.setOnClickListener(this);
        binding.imgCustomBack.setOnClickListener(this);
        binding.imgCustomNext.setOnClickListener(this);
        binding.loutReject.setOnClickListener(this);
        LinearLayoutManager linearLayoutManagerDesigner = new LinearLayoutManager(activity);
        binding.rvGigItem.setLayoutManager(linearLayoutManagerDesigner);
        binding.rvGigItem.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManagerCustomDesigner = new LinearLayoutManager(activity);
        binding.rvCustomGig.setLayoutManager(linearLayoutManagerCustomDesigner);
        binding.rvCustomGig.setNestedScrollingEnabled(false);

        LinearLayoutManager linearSocialLayoutManager = new LinearLayoutManager(activity);
        binding.rvSocialGig.setLayoutManager(linearSocialLayoutManager);
        binding.rvSocialGig.setNestedScrollingEnabled(false);

        binding.imgCustomNext.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
        binding.imgCustomBack.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);

        if (activity.getIntent() != null) {
            expertGigDetail = (ExpertGigDetail) activity.getIntent().getSerializableExtra(Constants.PROJECT_DETAIL);
        }

        getGigDetails();

        if (expertGigDetail.isFromOffer) {
            binding.loutChat.setVisibility(View.GONE);
            binding.loutReject.setVisibility(View.VISIBLE);
            binding.txtContinue.setText(activity.getResources().getString(R.string.accept));
        } else {
            binding.loutReject.setVisibility(View.GONE);
            binding.loutChat.setVisibility(View.VISIBLE);
            binding.txtContinue.setText(activity.getResources().getString(R.string.hire));
        }
    }

    private void changeSelection(int position, List<ExpertGigDetail.GigPackage> packages) {
        for (int listPosition = 0; listPosition < packages.size(); listPosition++) {
            if (listPosition == 0) { //left border black
                if (position == listPosition) {
                    tvList.get(listPosition).setBackground(activity.getResources().getDrawable(R.drawable.left_bottom_black));
                    tvList.get(listPosition).setTextColor(activity.getResources().getColor(R.color.white));
                } else {
                    tvList.get(listPosition).setBackground(activity.getResources().getDrawable(R.drawable.left_bottom_white));
                    tvList.get(listPosition).setTextColor(activity.getResources().getColor(R.color.tab_gray));
                }
            } else if (packages.size() - 1 == listPosition) {
                if (position == listPosition) {
                    tvList.get(listPosition).setBackground(activity.getResources().getDrawable(R.drawable.right_bottom_black));
                    tvList.get(listPosition).setTextColor(activity.getResources().getColor(R.color.white));
                } else {
                    tvList.get(listPosition).setBackground(activity.getResources().getDrawable(R.drawable.right_bottom_white));
                    tvList.get(listPosition).setTextColor(activity.getResources().getColor(R.color.tab_gray));
                }
            } else if (position == listPosition) {
                tvList.get(listPosition).setBackgroundColor(activity.getResources().getColor(R.color.black));
                tvList.get(listPosition).setTextColor(activity.getResources().getColor(R.color.white));
            } else {
                tvList.get(listPosition).setBackgroundColor(activity.getResources().getColor(R.color.white));
                tvList.get(listPosition).setTextColor(activity.getResources().getColor(R.color.tab_gray));
            }
        }
    }

    private void addTabs(List<ExpertGigDetail.GigPackage> packages) {
        try {
            for (int listPosition = 0; listPosition < packages.size(); listPosition++) {
                if (listPosition == 0) {
                    selectedPackagePosition = listPosition;
                }
                textViewName = new TextView(activity);
                Typeface face = Typeface.createFromAsset(activity.getAssets(),
                        "font/sf_pro_text_medium.otf");
                textViewName.setTypeface(face);
                textViewName.setTextSize(16);
                if (listPosition == 0) { //left border black
                    textViewName.setBackground(activity.getResources().getDrawable(R.drawable.left_bottom_black));
                    textViewName.setTextColor(activity.getResources().getColor(R.color.background_new));
                } else if (packages.size() - 1 == listPosition) {
                    textViewName.setBackground(activity.getResources().getDrawable(R.drawable.right_bottom_white));
                    textViewName.setTextColor(activity.getResources().getColor(R.color.tab_gray));
                } else {
                    textViewName.setBackgroundColor(activity.getResources().getColor(R.color.white));
                    textViewName.setTextColor(activity.getResources().getColor(R.color.tab_gray));
                }
                textViewName.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(packages.get(listPosition).price)) + " " + activity.getString(R.string.sar)
                        : activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(packages.get(listPosition).price)));
                binding.tvPackageTitle.setText(packages.get(0).name);
                binding.tvPackageDesc.setText(packages.get(0).description);
                setUpdateAmt(packages.get(0).price);
                binding.tvDeliveryDays.setText(packages.get(0).deliveryTitle);
                binding.tvRevisionsDays.setText(packages.get(0).revisions + "");
                textViewName.setTag(Utils.decimalFormat(String.valueOf(packages.get(0).price)));
                setGigAdapter(packages.get(0).requirements);

                if (packages.size() == 3 || packages.size() == 2) {
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1);
                    textViewName.setLayoutParams(param);
                }
                textViewName.setGravity(Gravity.CENTER);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        1,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                View view = new View(activity);
                view.setPadding(0,
                        (int) activity.getResources().getDimension(R.dimen._5sdp),
                        0,
                        (int) activity.getResources().getDimension(R.dimen._5sdp));
                view.setBackgroundColor(activity.getResources().getColor(R.color.gray));
                view.setLayoutParams(param);

                tvList.add(textViewName);
                binding.llSubMain.addView(textViewName);
                if (packages.size() - 1 != listPosition) {
                    binding.llSubMain.addView(view);
                }

                final int finalListPosition = listPosition;
                textViewName.setOnClickListener(v -> {
                    changeSelection(finalListPosition, packages);
                    selectedPackagePosition = finalListPosition;
                    textViewName.setTag(Utils.decimalFormat(String.valueOf(packages.get(finalListPosition).price)));
                    setUpdateAmt(packages.get(finalListPosition).price);
                    binding.tvDeliveryDays.setText(packages.get(finalListPosition).deliveryTitle);
                    binding.tvRevisionsDays.setText(packages.get(finalListPosition).revisions + "");
                    binding.tvPackageTitle.setText(packages.get(finalListPosition).name);
                    binding.tvPackageDesc.setText(packages.get(finalListPosition).description);
                    setGigAdapter(packages.get(finalListPosition).requirements);
                    if (activity.getCurrency().equals("SAR")) {
                        updateValue(binding.etQuantity.getText().toString().trim(), textViewName.getTag().toString().replace(activity.getString(R.string.sar), "").replace(",", ""));
                    } else {
                        updateValue(binding.etQuantity.getText().toString().trim(), textViewName.getTag().toString().replace(activity.getString(R.string.dollar), "").replace(",", ""));
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpdateAmt(Double price) {
        try {
            if (expertGigDetail.isFirstOrder == 0) {
                binding.tvView.setVisibility(View.GONE);
                binding.tvDisAmount.setVisibility(View.GONE);
                if (activity.getCurrency().equals("SAR")) {
                    binding.tvAmount.setText("(" + Utils.decimalFormat(String.valueOf(price + deadlinePrice)) + " " + activity.getString(R.string.sar) + ")");
                } else {
                    binding.tvAmount.setText("(" + activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(price + deadlinePrice)) + ")");
                }
            } else {
                double finalAmt = 0;
                if (expertGigDetail.couponData != null) {
                    if (expertGigDetail.couponData.type == 2) {
                        finalAmt = price - expertGigDetail.couponData.discount;
                    } else {
                        finalAmt = (price * expertGigDetail.couponData.discount) / 100;
                    }
                }

                if (finalAmt <= 0) {

                    if (activity.getCurrency().equals("SAR")) {
                        if (deadlinePrice != 0) {
                            binding.tvAmount.setText("(" + Utils.decimalFormat(String.valueOf(finalAmt + deadlinePrice)) + " " + activity.getString(R.string.sar) + ")");
                        } else {
                            binding.tvAmount.setText("(0 " + activity.getString(R.string.sar) + ")");
                        }
                    } else {
                        if (deadlinePrice != 0) {
                            binding.tvAmount.setText("(" + activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(finalAmt + deadlinePrice)) + ")");
                        } else {
                            binding.tvAmount.setText("(" + activity.getString(R.string.dollar) + "0)");
                        }
                    }

                    if (price != 0) {
                        binding.tvView.setVisibility(View.VISIBLE);
                        binding.tvDisAmount.setVisibility(View.VISIBLE);
                        binding.tvDisAmount.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(price + deadlinePrice)) + " " + activity.getString(R.string.sar)
                                : activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(price + deadlinePrice)));
                        binding.tvDisAmount.setPaintFlags(binding.tvDisAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        binding.tvView.setVisibility(View.GONE);
                        binding.tvDisAmount.setVisibility(View.GONE);
                    }
                } else {
                    binding.tvDisAmount.setVisibility(View.VISIBLE);
                    binding.tvView.setVisibility(View.VISIBLE);
                    if (activity.getCurrency().equals("SAR")) {
                        binding.tvAmount.setText("(" + Utils.decimalFormat(String.valueOf(finalAmt + deadlinePrice)) + " " + activity.getString(R.string.sar) + ")");
                        binding.tvDisAmount.setText(Utils.decimalFormat(String.valueOf(price + deadlinePrice)) + " " + activity.getString(R.string.sar));
                    } else {
                        binding.tvAmount.setText("(" + activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(finalAmt + deadlinePrice)) + ")");
                        binding.tvDisAmount.setText(activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(price + deadlinePrice)));
                    }

                    binding.tvDisAmount.setPaintFlags(binding.tvDisAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getGigDetails() {
        try {
            binding.tvName.setText(expertGigDetail.agentUserName);
            binding.tvTitle.setText(expertGigDetail.gigTitle);
            binding.tvDescription.setText(expertGigDetail.gigDescription);

            setGigImagesAdapter(expertGigDetail.gigImages, expertGigDetail.gigImagesPath);

            if (expertGigDetail.saved == 0) {
                binding.imgFavourite.setImageResource(R.drawable.ic_fav);
                binding.imgFavourite.setColorFilter(ContextCompat.getColor(activity, R.color.gray_light), android.graphics.PorterDuff.Mode.MULTIPLY);
            } else {
                binding.imgFavourite.setImageResource(R.drawable.ic_fav_fill);
                binding.imgFavourite.setColorFilter(ContextCompat.getColor(activity, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

            try {
                if (expertGigDetail.starpoints != null) {
                    binding.ratingbar.setVisibility(View.VISIBLE);
                    binding.ratingbar.setRating(expertGigDetail.starpoints);
                } else {
                    binding.ratingbar.setVisibility(View.VISIBLE);
                    binding.ratingbar.setRating(0);
                }
            } catch (NumberFormatException e) {
                binding.ratingbar.setVisibility(View.VISIBLE);
                binding.ratingbar.setRating(0);
                e.printStackTrace();
            }

            if (expertGigDetail.countRating != null) {
                binding.tvRating.setText("(" + Math.round(expertGigDetail.countRating) + ")");
            } else {
                binding.tvRating.setText("(0)");
            }

            if (!TextUtils.isEmpty(expertGigDetail.agentProfileImg)) {
                Glide.with(activity)
                        .load(expertGigDetail.agentProfilePath + expertGigDetail.agentProfileImg)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round)
                        .into(binding.imgProfile);

                Glide.with(activity)
                        .load(expertGigDetail.agentProfilePath + expertGigDetail.agentProfileImg)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round)
                        .into(binding.imgProfileChat);
            } else {
                binding.imgProfileChat.setImageResource(R.mipmap.ic_launcher_round);
            }

            if (expertGigDetail.gigType.equalsIgnoreCase("1")) {
                binding.loutNormalGig.setVisibility(View.GONE);
                binding.loutCustom.setVisibility(View.VISIBLE);
                binding.rvCustomGig.setVisibility(View.VISIBLE);
                binding.loutPrice.setVisibility(View.GONE);
                if (expertGigDetail.deadlines != null && expertGigDetail.deadlines.size() > 0) {
                    binding.loutCustomDelivery.setVisibility(View.VISIBLE);
                    if (expertGigDetail.deadlines.get(0).type == 1) {
                        binding.etCustomDelivery.setText(expertGigDetail.deadlines.get(0).value + " " + activity.getString(R.string.hours));
                    } else {
                        binding.etCustomDelivery.setText(expertGigDetail.deadlines.get(0).value + " " + activity.getString(R.string.days));
                    }
                    expertGigDetail.deadlineID = expertGigDetail.deadlines.get(0).id;
                    deadlinePrice = expertGigDetail.deadlines.get(0).price;

                    if (expertGigDetail.deadlines.size() > 1) {
                        binding.imgCustomNext.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.loutCustomDelivery.setVisibility(View.GONE);
                }

                if (expertGigDetail.minPrice == 0) {
                    binding.loutCustomPrice.setVisibility(View.GONE);
                    binding.tvCustomPrice.setText(activity.getString(R.string.free).toUpperCase());
                    binding.tvAmount.setText(activity.getCurrency().equals("SAR") ? "(0 " + activity.getString(R.string.sar) + ")" : "($0)");
                    setUpdateAmt(expertGigDetail.minPrice);
                } else {
                    binding.loutCustomPrice.setVisibility(View.VISIBLE);
                    binding.tvCustomPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(expertGigDetail.minPrice)) + " " + activity.getString(R.string.sar)
                            : activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(expertGigDetail.minPrice)));
                    setUpdateAmt(expertGigDetail.minPrice);
                }

                if (expertGigDetail.customPackages != null && expertGigDetail.customPackages.size() > 0) {
                    setCustomGigAdapter(expertGigDetail.customPackages);
                }

                binding.imgCustomInfom.setOnClickListener(v -> priceDialogCustom(activity.getResources().getString(R.string.delivery_time), expertGigDetail.deadlineDescription, expertGigDetail.deadlines));

            } else if (expertGigDetail.gigType.equalsIgnoreCase("2")) {
                binding.rvCustomGig.setVisibility(View.GONE);
                binding.loutCustomPrice.setVisibility(View.GONE);
                binding.loutCustom.setVisibility(View.GONE);
                binding.loutNormalGig.setVisibility(View.VISIBLE);
                binding.loutCustomPrice.setVisibility(View.GONE);
                binding.loutPrice.setVisibility(View.GONE);
                binding.txtAllService.setText("#" + expertGigDetail.generalRank + " " + activity.getString(R.string.in));
                binding.txtWritingService.setText("#" + expertGigDetail.categoryRank + " " + activity.getString(R.string.in));

                if (expertGigDetail.packages != null && expertGigDetail.packages.size() > 0) {
                    addTabs(expertGigDetail.packages);
                }
            } else if (expertGigDetail.gigType.equalsIgnoreCase("3")) {
                binding.loutNormalGig.setVisibility(View.GONE);
                binding.loutCustom.setVisibility(View.GONE);
                binding.loutPrice.setVisibility(View.VISIBLE);
                binding.tvPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(expertGigDetail.finalCalculatedPrice)) + " " + activity.getString(R.string.sar)
                        : activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(expertGigDetail.finalCalculatedPrice)) + "");
                setUpdateAmt(expertGigDetail.finalCalculatedPrice);
            }

            if (expertGigDetail.parentCategoryID == 4352) {
                binding.rvSocialGig.setVisibility(View.VISIBLE);
                if (expertGigDetail.socialPlatform != null && expertGigDetail.socialPlatform.size() > 0) {
                    setSocialGigAdapter(expertGigDetail.socialPlatform);
                }
            } else {
                binding.rvSocialGig.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void priceDialogCustom(String name, String deadlineDescription, List<ExpertGigDetail.Deadline> deadlineList) {
        final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_custom_price_option);
        dialog.setCancelable(true);

        CustomTextView txtName = dialog.findViewById(R.id.txt_name);
        ReadMoreTextView txtDescription = dialog.findViewById(R.id.txt_description);
        RecyclerView rvCustom = dialog.findViewById(R.id.rv_custom);

        LinearLayoutManager linearLayoutManagerCustomDesigner = new LinearLayoutManager(activity);
        rvCustom.setLayoutManager(linearLayoutManagerCustomDesigner);

        txtName.setText(name);

        if (!TextUtils.isEmpty(deadlineDescription)) {
            txtDescription.setVisibility(View.VISIBLE);
            txtDescription.setText(deadlineDescription);
        } else {
            txtDescription.setVisibility(View.GONE);
        }

        if (deadlineList.size() > 8) {
            ViewGroup.LayoutParams params = rvCustom.getLayoutParams();
            params.height = (int) activity.getResources().getDimension(R.dimen._250sdp);
            rvCustom.setLayoutParams(params);
        } else {
            rvCustom.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        CustomDeadlineAdapter customDeadlineAdapter = new CustomDeadlineAdapter(activity, deadlineList);
        rvCustom.setAdapter(customDeadlineAdapter);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void setGigImagesAdapter(List<ExpertGigDetail.GigImage> arrGigImages, String imagesPath) {
        if (gigPagerAdapter == null) {
            if (arrGigImages != null && arrGigImages.size() > 0) {
                gigPagerAdapter = new GigPagerAdapter(activity, arrGigImages, imagesPath);
                binding.vpDetailsImages.setAdapter(gigPagerAdapter);
                binding.indicatorImages.setViewPager(binding.vpDetailsImages);
            } else {
                binding.vpDetailsImages.setVisibility(View.GONE);
                binding.indicatorImages.setVisibility(View.GONE);
            }
        }
    }

    private void setGigAdapter(List<Requirement> arrGigPackages) {
        if (arrGigPackages.size() > 0) {
            binding.rvGigItem.setVisibility(View.VISIBLE);
            GigAdapter gigAdapter = new GigAdapter(activity, arrGigPackages, false);
            binding.rvGigItem.setAdapter(gigAdapter);
        } else {
            binding.rvGigItem.setVisibility(View.GONE);
        }
    }

    private void setCustomGigAdapter(List<ExpertGigDetail.CustomPackage> arrCustomGigPackages) {
        if (arrCustomGigPackages.size() > 0) {
            binding.rvCustomGig.setVisibility(View.VISIBLE);
            CustomGigAdapter customGigAdapter = new CustomGigAdapter(activity, arrCustomGigPackages, GigDetailActivityVM.this);
            binding.rvCustomGig.setAdapter(customGigAdapter);
        } else {
            binding.rvCustomGig.setVisibility(View.GONE);
        }
    }

    private void setSocialGigAdapter(List<ExpertGigDetail.SocialPlatform> arrSocialGigPackages) {
        if (arrSocialGigPackages.size() > 0) {
            binding.rvCustomGig.setVisibility(View.VISIBLE);
            SocialGigAdapter socialGigAdapter = new SocialGigAdapter(activity, arrSocialGigPackages, GigDetailActivityVM.this);
            binding.rvSocialGig.setAdapter(socialGigAdapter);
        } else {
            binding.rvSocialGig.setVisibility(View.GONE);
        }
    }

    private void getPaymentMethod() {
        if (!activity.isNetworkConnected())
            return;

        activity.isClickableView = true;
        binding.txtContinue.setVisibility(View.GONE);
        binding.tvAmount.setVisibility(View.GONE);
        binding.tvView.setVisibility(View.GONE);
        binding.tvDisAmount.setVisibility(View.GONE);
        binding.progressContinue.setVisibility(View.VISIBLE);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PAYMENTMETHOD, false, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                activity.onBackPressed();
                break;
            case R.id.lout_continue:
                getPaymentMethod();
                break;
            case R.id.img_plus:
                String currentValue = binding.etQuantity.getText().toString().trim();
                currentValue = String.valueOf(Integer.parseInt(currentValue) + 1);
                binding.etQuantity.setText(currentValue);
                if (activity.getCurrency().equals("SAR")) {
                    updateValue(currentValue, textViewName.getTag().toString().replace(activity.getString(R.string.sar), "").replace(",", ""));
                } else {
                    updateValue(currentValue, textViewName.getTag().toString().replace(activity.getString(R.string.dollar), "").replace(",", ""));
                }

                break;
            case R.id.img_minus:
                currentValue = binding.etQuantity.getText().toString().trim();
                try {
                    if (currentValue.equals("1")) {
                        binding.etQuantity.setText(currentValue);
                    } else {
                        currentValue = String.valueOf(Integer.parseInt(currentValue) - 1);
                        binding.etQuantity.setText(currentValue);
                    }
                    if (activity.getCurrency().equals("SAR")) {
                        updateValue(currentValue, textViewName.getTag().toString().replace(activity.getString(R.string.sar), "").replace(",", ""));
                    } else {
                        updateValue(currentValue, textViewName.getTag().toString().replace(activity.getString(R.string.dollar), "").replace(",", ""));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.img_custom_back:
                if (posCustomDelivery[0] == 0) {
                    return;
                }

                posCustomDelivery[0] = posCustomDelivery[0] - 1;
                binding.etCustomDelivery.setText(expertGigDetail.deadlines.get(posCustomDelivery[0]).value + (expertGigDetail.deadlines.get(posCustomDelivery[0]).type == 1 ? " "+activity.getString(R.string.hours) : " "+activity.getString(R.string.days)));
                expertGigDetail.deadlineID = expertGigDetail.deadlines.get(posCustomDelivery[0]).id;
                deadlinePrice = expertGigDetail.deadlines.get(posCustomDelivery[0]).price;
                setUpdateAmt(expertGigDetail.finalAmount);
                if (posCustomDelivery[0] == 0) {
                    binding.imgCustomBack.setColorFilter(ContextCompat.getColor(activity, R.color._gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                    binding.imgCustomNext.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                } else {
                    binding.imgCustomNext.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                    binding.imgCustomBack.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
                break;
            case R.id.img_custom_next:
                if (posCustomDelivery[0] != expertGigDetail.deadlines.size() - 1) {
                    posCustomDelivery[0] = posCustomDelivery[0] + 1;
                }

                binding.imgCustomBack.setVisibility(View.VISIBLE);
                binding.etCustomDelivery.setText(expertGigDetail.deadlines.get(posCustomDelivery[0]).value + (expertGigDetail.deadlines.get(posCustomDelivery[0]).type == 1 ? " "+activity.getString(R.string.hours) : " "+activity.getString(R.string.days)));
                expertGigDetail.deadlineID = expertGigDetail.deadlines.get(posCustomDelivery[0]).id;
                deadlinePrice = expertGigDetail.deadlines.get(posCustomDelivery[0]).price;
                setUpdateAmt(expertGigDetail.finalAmount);

                if (posCustomDelivery[0] == expertGigDetail.deadlines.size() - 1) {
                    binding.imgCustomNext.setColorFilter(ContextCompat.getColor(activity, R.color._gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                } else {
                    binding.imgCustomNext.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
                binding.imgCustomBack.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
            case R.id.img_download:
                if (activity.checkStoragePermission()) {
                    checkPermission(expertGigDetail);
                } else {
                    new StorageDisclosureDialog(activity, () -> checkPermission(expertGigDetail));
                }
                break;
            case R.id.img_favourite:
                binding.progressBarFav.setVisibility(View.VISIBLE);
                binding.imgFavourite.setVisibility(View.GONE);
                activity.saveRemoveGig(expertGigDetail.gigID, this, expertGigDetail.saved != 0);
                break;
            case R.id.tv_view_all:
                getAgentProfile(expertGigDetail.agentProfileID);
                break;
            case R.id.lout_chat:
                HashMap<String, String> chatMap = new HashMap<>();
                chatMap.put(Constants.RECEIVER_ID, expertGigDetail.agentProfileID + "");
                chatMap.put(Constants.RECEIVER_NAME, expertGigDetail.agentUserName);
                chatMap.put(Constants.RECEIVER_PIC, activity.getUserData().filePath.pathProfilePicAgent + expertGigDetail.agentProfileImg);
                chatMap.put(Constants.SENDER_ID, activity.getUserData().id + "");
                chatMap.put(Constants.SENDER_NAME, activity.getUserData().username);
                chatMap.put(Constants.SENDER_PIC, activity.getUserData().filePath.pathProfilePicClient + activity.getUserData().profilePic);
                chatMap.put("isProject", "1");//1 mean updated record
                if (expertGigDetail.gigType.equalsIgnoreCase("1")) {
                    chatMap.put("projectType", "3");//2=job & 1= gig
                } else {
                    chatMap.put("projectType", "1");//2=job & 1= gig
                }
                chatMap.put("isDetailScreen", "true");

                Intent chatIntent = new Intent(activity, ChatMessagesActivity.class);
                chatIntent.putExtra(Constants.CHAT_ID, activity.getUserData().id + "-" + expertGigDetail.agentProfileID);  // ClientId - AgentId
                chatIntent.putExtra(Constants.CHAT_DATA, chatMap);
                if (activity.getIsVerified() == 1) {
                    activity.startActivity(chatIntent);
                } else {
                    activity.toastMessage(activity.getString(R.string.verification_is_pending_please_complete_the_verification_first_before_chatting_with_them));
                }
                break;
            case R.id.lout_reject:
                getAcceptOrRejectOffer();
                break;
        }
    }

    private void updateValue(String strQuantity, String strPrice) {
        try {
            double finalAmt = Integer.parseInt(strQuantity) * Double.parseDouble(strPrice);
            setUpdateAmt(finalAmt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAgentProfile(int agentProfileId) {
        if (!activity.isNetworkConnected())
            return;
        activity.isClickableView = true;

        binding.viewAll.setVisibility(View.GONE);
        binding.progressBarProfile.setVisibility(View.VISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("agent_profile_id", agentProfileId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, activity, API_GET_PROFILE_INFO, true, map);
    }

    private void getAcceptOrRejectOffer() {
        if (!activity.isNetworkConnected())
            return;
        activity.isClickableView = true;

        binding.txtReject.setVisibility(View.GONE);
        binding.progressReject.setVisibility(View.VISIBLE);

        HashMap<String, RequestBody> map = new HashMap<>();
        RequestBody offerID = RequestBody.create(String.valueOf(expertGigDetail.offerID), MultipartBody.FORM);
        RequestBody status = RequestBody.create(String.valueOf(3), MultipartBody.FORM);
        RequestBody PK = RequestBody.create(String.valueOf(expertGigDetail.pk), MultipartBody.FORM);
        RequestBody SK = RequestBody.create(String.valueOf(expertGigDetail.sk), MultipartBody.FORM);
        map.put("offerID", offerID);
        map.put("offerStatus", status);
        map.put("PK", PK);
        map.put("SK", SK);

        Call<CommonResponse> call = activity.getService().acceptOrRejectOffer(API_GET_ACCEPT_OR_REJECT_OFFER, activity.getJWT(), map, "6");
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                activity.isClickableView = false;
                try {
                    JSONObject jsonData = new JSONObject();
                    jsonData.put("partitionKey", "#message#" + activity.getUserID() + "-" + expertGigDetail.receiverId);
                    jsonData.put("senderId", activity.getUserID());
                    jsonData.put("offerStatus", 3);
                    jsonData.put("receiverId", expertGigDetail.receiverId);
                    jsonData.put("messageId", expertGigDetail.messageId);
                    jsonData.put("price", expertGigDetail.price);
                    jsonData.put("contractID", 0);
                    Log.e("AAAAAA", "sendLiveOfferStatus..." + jsonData);
                    activity.mSocket.emit("sendLiveOfferStatus", jsonData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                binding.progressReject.setVisibility(View.GONE);
                binding.txtReject.setVisibility(View.VISIBLE);
                activity.toastMessage(response.body().getMessage(activity));
                Intent i = new Intent(activity, MainActivity.class);
                i.putExtra(Constants.SCREEN_NAME, Constants.TAB_CHAT);
                activity.startActivity(i);
                activity.finish();
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                activity.isClickableView = false;
                binding.progressReject.setVisibility(View.GONE);
                binding.txtReject.setVisibility(View.VISIBLE);
                activity.finish();
            }
        });
    }

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            AgentProfile profile = AgentProfile.getProfileInfo(responseBody);
            if (profile != null) {
//                Intent i = new Intent(activity, FreelancerProfileActivity.class);
                Intent i = new Intent(activity, InfluencerProfileActivityCopy.class);
                i.putExtra(Constants.AGENT_PROFILE_DATA, profile);
                activity.startActivity(i);
            }
            binding.viewAll.setVisibility(View.VISIBLE);
            binding.progressBarProfile.setVisibility(View.GONE);
        } else if (url.equalsIgnoreCase(API_GET_PAYMENTMETHOD)) {
            PaymentMethods paymentMethods = PaymentMethods.gePaymentMethodInfo(responseBody);
            binding.progressContinue.setVisibility(View.GONE);
            binding.txtContinue.setVisibility(View.VISIBLE);
            binding.tvAmount.setVisibility(View.VISIBLE);
            if (expertGigDetail.isFirstOrder != 0) {
                binding.tvView.setVisibility(View.VISIBLE);
                binding.tvDisAmount.setVisibility(View.VISIBLE);
            }
            Preferences.setPaymentMethod(activity, paymentMethods);
            try {
                if (expertGigDetail.gigType.equalsIgnoreCase("2")) {
                    expertGigDetail.fixedPrice = Integer.parseInt(binding.etQuantity.getText().toString()) * Double.parseDouble(textViewName.getTag().toString().replace(activity.getCurrency().equals("SAR") ? "SAR" : activity.getString(R.string.dollar), "")
                            .replace(",", "").replace("(", "").replace(")", ""));
                    expertGigDetail.quantity = Integer.valueOf(binding.etQuantity.getText().toString());

                } else {
                    if (expertGigDetail.isFirstOrder == 1) {
                        expertGigDetail.fixedPrice = Double.valueOf(binding.tvDisAmount.getText().toString().replace(activity.getCurrency().equals("SAR") ? activity.getString(R.string.sar) : activity.getString(R.string.dollar), "").replace(",", "")
                                .replace("(", "").replace(")", ""));
                    } else {
                        expertGigDetail.fixedPrice = Double.valueOf(binding.tvAmount.getText().toString().replace(activity.getCurrency().equals("SAR") ? activity.getString(R.string.sar) : activity.getString(R.string.dollar), "").replace(",", "")
                                .replace("(", "").replace(")", ""));
                    }
                }

//                Intent i = new Intent(activity, DepositFundsActivity.class);
                Intent i = new Intent(activity, PaymentActivity.class);
                i.putExtra(Constants.IS_FROM_GIG, true);
                i.putExtra(Constants.USER_DATA, expertGigDetail);
                i.putExtra(Constants.SELECTED_PACKAGE_POS, selectedPackagePosition);
                activity.startActivity(i);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        activity.isClickableView = false;
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        activity.isClickableView = false;
        if (url.equalsIgnoreCase(API_GET_PROFILE_INFO)) {
            binding.viewAll.setVisibility(View.VISIBLE);
            binding.progressBarProfile.setVisibility(View.GONE);
        }
    }

    private void checkPermission(final ExpertGigDetail userFiles) {
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        downloadFile(userFiles);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        activity.toastMessage(activity.getString(R.string.give_storage_permission_first));
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void downloadFile(final ExpertGigDetail attachments) {
        File folder = new File(Environment.getExternalStorageDirectory(), "/Download/" + activity.getString(R.string.app_name));
        if (!folder.exists())
            folder.mkdir();

        final File file = new File(folder, attachments.gigImages.get(binding.vpDetailsImages.getCurrentItem()).imageName);
        if (!file.exists()) {
            String url = attachments.gigImagesPath + attachments.gigImages.get(binding.vpDetailsImages.getCurrentItem()).imageName;
            if (!TextUtils.isEmpty(attachments.gigImages.get(binding.vpDetailsImages.getCurrentItem()).imageName) && (url.startsWith("http:") || url.startsWith("https:"))) {
                MyDownloadManager downloadManager = new MyDownloadManager(activity)
                        .setDownloadUrl(url)
                        .setTitle(attachments.gigImages.get(binding.vpDetailsImages.getCurrentItem()).imageName)
                        .setDestinationUri(file)
                        .setDownloadCompleteListener(new MyDownloadManager.DownloadCompleteListener() {
                            @Override
                            public void onDownloadComplete() {
                                activity.toastMessage(activity.getString(R.string.download_complete));
                            }

                            @Override
                            public void onDownloadFailure() {
                                activity.toastMessage(activity.getString(R.string.download_failed));
                            }
                        });
                downloadManager.startDownload();
            }

        } else {
            activity.toastMessage(activity.getString(R.string.already_downloaded));
        }
    }

    @Override
    public void savedGigSuccess(String gigId) {
        notifyFavProgressGig(1);
        activity.isClickableView = false;
    }

    @Override
    public void removeGigSuccess(String gigId) {
        notifyFavProgressGig(0);
    }

    private void notifyFavProgressGig(int state) {
        try {
            binding.imgFavourite.setVisibility(View.VISIBLE);
            binding.progressBarFav.setVisibility(View.GONE);
            expertGigDetail.saved = state;
            if (expertGigDetail.saved == 0) {
                binding.imgFavourite.setImageResource(R.drawable.ic_fav);
                binding.imgFavourite.setColorFilter(ContextCompat.getColor(activity, R.color.gray_light), android.graphics.PorterDuff.Mode.MULTIPLY);
            } else {
                binding.imgFavourite.setImageResource(R.drawable.ic_fav_fill);
                binding.imgFavourite.setColorFilter(ContextCompat.getColor(activity, R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickRateDialog(boolean isCancelled) {
        activity.gotoMainActivity(Constants.TAB_JOB_LIST);
    }

    @Override
    public void onClickItem(ExpertGigDetail.CustomPackage customPackage) {
        try {
            double finalAmt = 0;

            customPackage.finalAmount = Integer.parseInt(customPackage.quantity) * customPackage.price;

            for (int i = 0; i < expertGigDetail.customPackages.size(); i++) {
                finalAmt += expertGigDetail.customPackages.get(i).finalAmount;
            }

            double finalAmtPayable = expertGigDetail.minPrice + finalAmt;
            expertGigDetail.finalAmount = finalAmtPayable;
            setUpdateAmt(finalAmtPayable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
