package com.nojom.client.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nojom.client.R;
import com.nojom.client.databinding.ItemWorkwithBinding;
import com.nojom.client.model.GetCompanies;
import com.nojom.client.ui.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WorkWithAdapter extends RecyclerView.Adapter<WorkWithAdapter.SimpleViewHolder> {

    private List<GetCompanies.Data> mDataset;
    private BaseActivity activity;
    private String filePath;

    public WorkWithAdapter(String path) {
        filePath = path;
    }

    public void doRefresh(List<GetCompanies.Data> objects, BaseActivity activity) {
        this.mDataset = objects;
        this.activity = activity;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemWorkwithBinding verifiedWithBinding = ItemWorkwithBinding.inflate(layoutInflater, parent, false);
        return new SimpleViewHolder(verifiedWithBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        GetCompanies.Data item = mDataset.get(position);
        holder.binding.txtName.setText(item.getName(activity.getLanguage()));

        String text = item.times + " " + activity.getString(R.string.cooperations);

        holder.binding.txtUname.setText(applyStyle(text));
        holder.binding.txtContract.setText(activity.getString(R.string.contract));

        if (item.contract_start_date != null) {
            holder.binding.txtContract.setVisibility(View.VISIBLE);
            holder.binding.linCDate.setVisibility(View.VISIBLE);
            holder.binding.txtDate.setVisibility(View.GONE);
            if (item.contract_start_date != null) {
                String campDate = item.contract_start_date;
                String campDateEnd = item.contract_end_date;

                SimpleDateFormat inputFormat = new SimpleDateFormat("M/yyyy"); // Single 'M' to handle months without leading zero
                SimpleDateFormat outputFormat = new SimpleDateFormat("MM-yyyy");

                try {
                    // Parse the input date string to a Date object
                    Date dateS = inputFormat.parse(campDate);
                    Date dateE = inputFormat.parse(campDateEnd);

                    // Format the Date object to the desired output format
                    String outputDateS = null;
                    if (dateS != null) {
                        outputDateS = outputFormat.format(dateS);
                    }
                    String outputDateE = null;
                    if (dateE != null) {
                        outputDateE = outputFormat.format(dateE);
                    }

                    // Print the result
                    holder.binding.txtDateC.setText(outputDateS);
                    holder.binding.txtDateEndC.setText(outputDateE);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (item.campaign_date != null && item.campaign_date.contains("T")) {
                String[] campDate = item.campaign_date.split("T");
                holder.binding.txtDate.setVisibility(View.VISIBLE);

                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd"); // Single 'M' to handle months without leading zero
                SimpleDateFormat outputFormat = new SimpleDateFormat("MM-yyyy");

                try {
                    // Parse the input date string to a Date object
                    Date dateS = inputFormat.parse(campDate[0]);

                    // Format the Date object to the desired output format
                    String outputDateS = null;
                    if (dateS != null) {
                        outputDateS = outputFormat.format(dateS);
                    }

                    holder.binding.txtDate.setText(outputDateS);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            holder.binding.txtContract.setVisibility(View.GONE);
            holder.binding.linCDate.setVisibility(View.GONE);
        }


        Glide.with(activity).load(path + item.filename).placeholder(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                    binding.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                    binding.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.binding.imgProfile);
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    String path;

    public void setPath(String path) {
        this.path = path;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemWorkwithBinding binding;

        SimpleViewHolder(ItemWorkwithBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.getRoot().setOnClickListener(v -> {

            });
        }
    }

    private SpannableString applyStyle(String text) {
        // Find the position of the first non-digit character
        int firstNonDigitIndex = 0;
        while (firstNonDigitIndex < text.length() && Character.isDigit(text.charAt(firstNonDigitIndex))) {
            firstNonDigitIndex++;
        }

        // Create a SpannableString
        SpannableString spannableString = new SpannableString(text);

        // Apply color to the digit part
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, firstNonDigitIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}
