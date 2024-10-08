package com.nojom.client.adapter;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.textview.CustomTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemCustomGigProjectDetailsBinding;
import com.nojom.client.model.ProjectGigByID;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Utils;

import java.util.List;
import java.util.Objects;

public class CustomGigDetailAdapter extends RecyclerView.Adapter<CustomGigDetailAdapter.SimpleViewHolder> {
    LayoutInflater layoutInflater;
    private BaseActivity activity;
    private List<ProjectGigByID.CustomPackage> arrRequirementList;

    public CustomGigDetailAdapter(BaseActivity activity, List<ProjectGigByID.CustomPackage> arrRequirementList) {
        this.activity = activity;
        this.arrRequirementList = arrRequirementList;
        layoutInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomGigProjectDetailsBinding popularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_custom_gig_project_details, parent, false);
        return new SimpleViewHolder(popularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        try {
            ProjectGigByID.CustomPackage item = arrRequirementList.get(position);

            holder.binding.tvGigName.setText(item.name + (!TextUtils.isEmpty(item.featureName) ? " (" + item.featureName.trim() + ")" : ""));

            if (item.inputType == 1) {
                holder.binding.imgInfom.setVisibility(View.VISIBLE);
                holder.binding.tvCustomPackages.setText(item.quantity + " ");
            } else {
                holder.binding.imgInfom.setVisibility(View.GONE);
                holder.binding.tvCustomPackages.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(String.valueOf(item.price)) + " "+activity.getString(R.string.sar)
                        : "$" + Utils.decimalFormat(String.valueOf(item.price)));
            }

            holder.binding.imgInfom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    priceDialog(activity, item.name, String.valueOf(item.price));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return arrRequirementList.size();
    }


    public void priceDialog(BaseActivity activity, String name, String price) {
        this.activity = activity;

        final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_custom_price);
        dialog.setCancelable(true);


        CustomTextView txtName = dialog.findViewById(R.id.txt_name);
        CustomTextView txtPrice = dialog.findViewById(R.id.txt_price);

        txtName.setText(name);
        txtPrice.setText(activity.getCurrency().equals("SAR") ? Utils.decimalFormat(price) + " "+activity.getString(R.string.sar) : "$" + Utils.decimalFormat(price));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

    }


    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ItemCustomGigProjectDetailsBinding binding;

        public SimpleViewHolder(ItemCustomGigProjectDetailsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
