package com.nojom.client.adapter;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.textview.CustomTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemCustomGigDetailsBinding;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.ReadMoreTextView;
import com.nojom.client.util.Utils;

import java.util.List;
import java.util.Objects;

public class CustomGigAdapter extends RecyclerView.Adapter<CustomGigAdapter.SimpleViewHolder> {
    LayoutInflater layoutInflater;
    private BaseActivity activity;
    private final List<ExpertGigDetail.CustomPackage> arrRequirementList;
    private final OnItemClick onItemClick;

    public CustomGigAdapter(BaseActivity activity, List<ExpertGigDetail.CustomPackage> arrRequirementList, OnItemClick onItemClick) {
        this.activity = activity;
        this.arrRequirementList = arrRequirementList;
        this.onItemClick = onItemClick;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomGigDetailsBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_custom_gig_details, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleViewHolder holder, int position) {
        try {
            ExpertGigDetail.CustomPackage item = arrRequirementList.get(position);

            holder.binding.tvGigName.setText(item.getName(activity));
            holder.binding.etCustomPrice.setText(item.requirmentDetails.get(0).featureName);

            switch (item.inputType) {
                case 1:
                    item.quantity = "0";
                    holder.binding.etQuantity.setText("0");
                    holder.binding.loutChecked.setVisibility(View.GONE);

                    if (item.requirmentDetails != null) {
                        if (item.gigRequirementType == 3) {
                            holder.binding.loutChecked.setVisibility(View.GONE);
                            holder.binding.imgChecked.setVisibility(View.GONE);
                            holder.binding.tvCustomPackagesPrice.setVisibility(View.GONE);
                            holder.binding.loutCustomOption.setVisibility(View.VISIBLE);
                            if (item.requirmentDetails.size() > 1) {
                                holder.binding.imgCustomNext.setVisibility(View.VISIBLE);
                            } else {
                                holder.binding.imgCustomNext.setVisibility(View.INVISIBLE);
                            }
                            item.quantity = "1";
                            item.price = item.requirmentDetails.get(0).price;
                            item.customPackageID = item.requirmentDetails.get(0).customPackageID;
                            onItemClick.onClickItem(item);
                        } else {
                            holder.binding.loutCustomOption.setVisibility(View.GONE);
                            holder.binding.loutNumber.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case 2:
                    item.quantity = "0";
                    holder.binding.loutNumber.setVisibility(View.GONE);
                    holder.binding.loutChecked.setVisibility(View.VISIBLE);
                    holder.binding.imgInfomChecked.setVisibility(View.GONE);
                    holder.binding.imgChecked.setVisibility(View.GONE);
                    holder.binding.tvCustomPackagesPrice.setVisibility(View.GONE);
                    holder.binding.tvCustomPackagesType2.setVisibility(View.VISIBLE);
                    holder.binding.tvCustomPackagesType2.setText(Utils.decimalFormat(String.valueOf(item.requirmentDetails.get(0).price)) + " " + activity.getString(R.string.days));
                    break;
                case 3:
                case 4:
                    holder.binding.loutNumber.setVisibility(View.GONE);
                    holder.binding.tvCustomPackagesType2.setVisibility(View.GONE);
                    if (item.gigRequirementType == 3) {
                        holder.binding.imgInfomChecked.setVisibility(View.GONE);
                        holder.binding.loutChecked.setVisibility(View.GONE);
                        holder.binding.imgChecked.setVisibility(View.GONE);
                        holder.binding.tvCustomPackagesPrice.setVisibility(View.GONE);
                        holder.binding.loutCustomOption.setVisibility(View.VISIBLE);
                        if (item.requirmentDetails.size() > 1) {
                            holder.binding.imgCustomNext.setVisibility(View.VISIBLE);
                        } else {
                            holder.binding.imgCustomNext.setVisibility(View.INVISIBLE);
                        }
                        item.quantity = "1";
                        item.price = item.requirmentDetails.get(0).price;
                        item.customPackageID = item.requirmentDetails.get(0).customPackageID;
                        onItemClick.onClickItem(item);
                    } else {
                        item.quantity = "0";
                        holder.binding.imgInfomChecked.setVisibility(View.VISIBLE);
                        holder.binding.loutCustomOption.setVisibility(View.GONE);
                        holder.binding.loutChecked.setVisibility(View.VISIBLE);
                        holder.binding.imgChecked.setVisibility(View.VISIBLE);
                        holder.binding.tvCustomPackagesPrice.setVisibility(View.VISIBLE);
                        holder.binding.tvCustomPackagesPrice.setText(
                                activity.getCurrency().equals("SAR") ?
                                        Utils.decimalFormat(String.valueOf(item.requirmentDetails.get(0).price)) + " "+activity.getString(R.string.sar)
                                        : activity.getString(R.string.dollar) + Utils.decimalFormat(String.valueOf(item.requirmentDetails.get(0).price)));
                    }
                    break;
            }

            holder.binding.imgPlus.setOnClickListener(v -> {
                if (onItemClick != null) {
                    String currentValue = holder.binding.etQuantity.getText().toString().trim();
                    currentValue = String.valueOf(Integer.parseInt(currentValue) + 1);
                    holder.binding.etQuantity.setText(currentValue);
                    item.quantity = currentValue;
                    item.price = item.requirmentDetails.get(0).price;
                    item.customPackageID = item.requirmentDetails.get(0).customPackageID;
                    onItemClick.onClickItem(item);
                }
            });

            holder.binding.imgMinus.setOnClickListener(v -> {
                if (onItemClick != null) {
                    String currentValue = holder.binding.etQuantity.getText().toString().trim();
                    try {
                        if (currentValue.equals("0")) {
                            holder.binding.etQuantity.setText("0");
                        } else {
                            currentValue = String.valueOf(Integer.parseInt(currentValue) - 1);
                            holder.binding.etQuantity.setText(currentValue);
                        }
                        item.quantity = currentValue;
                        item.price = item.requirmentDetails.get(0).price;
                        item.customPackageID = item.requirmentDetails.get(0).customPackageID;
                        onItemClick.onClickItem(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.binding.imgChecked.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (onItemClick != null) {
                    if (isChecked) {
                        item.quantity = "1";
                        item.price = item.requirmentDetails.get(0).price;
                        item.customPackageID = item.requirmentDetails.get(0).customPackageID;
                    } else {
                        item.quantity = "0";
                        item.price = item.requirmentDetails.get(0).price;
                    }
                    onItemClick.onClickItem(item);
                }
            });

            holder.binding.imgInfom.setOnClickListener(v -> priceDialog(activity, item.getName(activity), item.description, String.valueOf(item.requirmentDetails.get(0).price)));

            holder.binding.imgInfomChecked.setOnClickListener(v -> priceDialog(activity, item.getName(activity), item.description, String.valueOf(item.requirmentDetails.get(0).price)));

            final int[] posCustomPrice = {0};

            holder.binding.imgCustomBack.setOnClickListener(view1 -> {
                if (posCustomPrice[0] == 0) {
                    return;
                }

                posCustomPrice[0] = posCustomPrice[0] - 1;
                holder.binding.etCustomPrice.setText(item.requirmentDetails.get(posCustomPrice[0]).featureName);
                item.quantity = "1";
                item.price = item.requirmentDetails.get(posCustomPrice[0]).price;
                item.customPackageID = item.requirmentDetails.get(posCustomPrice[0]).customPackageID;
                onItemClick.onClickItem(item);

                if (posCustomPrice[0] == 0) {
                    holder.binding.imgCustomBack.setColorFilter(ContextCompat.getColor(activity, R.color._gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                    holder.binding.imgCustomNext.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                } else {
                    holder.binding.imgCustomNext.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                    holder.binding.imgCustomBack.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
            });


            holder.binding.imgCustomNext.setOnClickListener(view1 -> {
                if (posCustomPrice[0] != item.requirmentDetails.size() - 1) {
                    posCustomPrice[0] = posCustomPrice[0] + 1;
                }

                holder.binding.imgCustomBack.setVisibility(View.VISIBLE);
                holder.binding.etCustomPrice.setText(item.requirmentDetails.get(posCustomPrice[0]).featureName);
                item.quantity = "1";
                item.price = item.requirmentDetails.get(posCustomPrice[0]).price;
                item.customPackageID = item.requirmentDetails.get(posCustomPrice[0]).customPackageID;
                onItemClick.onClickItem(item);

                if (posCustomPrice[0] == item.requirmentDetails.size() - 1) {
                    holder.binding.imgCustomNext.setColorFilter(ContextCompat.getColor(activity, R.color._gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                } else {
                    holder.binding.imgCustomNext.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
                }

                holder.binding.imgCustomBack.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
            });

            holder.binding.imgCustomInfom.setOnClickListener(v -> priceDialogCustom(activity, item.getName(activity), item.description, item.requirmentDetails));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrRequirementList.size();
    }


    private void priceDialog(BaseActivity activity, String name, String description, String price) {
        this.activity = activity;

        final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_custom_price);
        dialog.setCancelable(true);


        CustomTextView txtName = dialog.findViewById(R.id.txt_name);
        CustomTextView txtPrice = dialog.findViewById(R.id.txt_price);
        ReadMoreTextView txtDescription = dialog.findViewById(R.id.txt_description);
        if (!TextUtils.isEmpty(description)) {
            txtDescription.setVisibility(View.VISIBLE);
            txtDescription.setText(description);
            txtDescription.setLinkTextColor(activity.getResources().getColor(R.color.colorPrimary));
            Linkify.addLinks(txtDescription, Linkify.WEB_URLS);
            Linkify.addLinks(txtDescription, Linkify.ALL);
        } else {
            txtDescription.setVisibility(View.GONE);
        }

        txtName.setText(name);
        txtPrice.setText(activity.getCurrency().equals("SAR") ?
                Utils.decimalFormat(price) + " "+activity.getString(R.string.sar) : activity.getString(R.string.dollar) + Utils.decimalFormat(price));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    private void priceDialogCustom(BaseActivity activity, String name, String description, List<ExpertGigDetail.RequirmentDetail> requirmentDetails) {
        this.activity = activity;

        final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_custom_price_option);
        dialog.setCancelable(true);

        CustomTextView txtName = dialog.findViewById(R.id.txt_name);
        RecyclerView rvCustom = dialog.findViewById(R.id.rv_custom);
        ReadMoreTextView txtDescription = dialog.findViewById(R.id.txt_description);

        LinearLayoutManager linearLayoutManagerCustomDesigner = new LinearLayoutManager(activity);
        rvCustom.setLayoutManager(linearLayoutManagerCustomDesigner);

        txtName.setText(name);
        if (!TextUtils.isEmpty(description)) {
            txtDescription.setVisibility(View.VISIBLE);
            txtDescription.setText(description);
        } else {
            txtDescription.setVisibility(View.GONE);
        }

        if (requirmentDetails.size() > 8) {
            ViewGroup.LayoutParams params = rvCustom.getLayoutParams();
            params.height = (int) activity.getResources().getDimension(R.dimen._250sdp);
            rvCustom.setLayoutParams(params);
        } else {
            rvCustom.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        CustomPriceAdapter customOptionGigAdapter = new CustomPriceAdapter(activity, requirmentDetails);
        rvCustom.setAdapter(customOptionGigAdapter);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    public interface OnItemClick {
        void onClickItem(ExpertGigDetail.CustomPackage customPackage);
    }


    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemCustomGigDetailsBinding binding;

        public SimpleViewHolder(ItemCustomGigDetailsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.imgCustomNext.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.imgCustomBack.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.imgPlus.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.imgMinus.setColorFilter(ContextCompat.getColor(activity, R.color.gray_), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }
}
