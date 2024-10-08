package com.nojom.client.adapter;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.nojom.client.R;
import com.nojom.client.databinding.ItemPortfolioListBinding;
import com.nojom.client.model.Portfolios;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.DepthTransformation;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PortfolioListAdapter extends RecyclerView.Adapter<PortfolioListAdapter.SimpleViewHolder> {
    private List<Portfolios.Data> mDataset;
    private BaseActivity activity;
    private String filePath;


    public PortfolioListAdapter(BaseActivity context, List<Portfolios.Data> objects, String filePath) {
        this.mDataset = objects;
        this.filePath = filePath;
        activity = context;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemPortfolioListBinding listFilesBinding =
                ItemPortfolioListBinding.inflate(layoutInflater, parent, false);
        return new SimpleViewHolder(listFilesBinding);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        Portfolios.Data item = mDataset.get(position);

        holder.binding.txtTitle.setText(item.title);
        /*if (item.portfolioFiles != null && item.portfolioFiles.size() > 0) {
            holder.binding.imgRight.setVisibility(View.VISIBLE);
            holder.binding.txtPage.setText(String.format(Locale.US,"1 / %d", item.portfolioFiles.size()));

            PortfolioPagerAdapter myCustomPagerAdapter = new PortfolioPagerAdapter(activity, item.portfolioFiles, filePath);
            holder.binding.viewpager.setAdapter(myCustomPagerAdapter);

            holder.binding.imgLeft.setOnClickListener(v -> {
                try {
                    holder.binding.imgRight.setVisibility(View.VISIBLE);

                    holder.binding.viewpager.setCurrentItem(holder.binding.viewpager.getCurrentItem() - 1);
                    holder.binding.txtPage.setText(String.format(holder.binding.viewpager.getCurrentItem() + 1 + " / %d", item.portfolioFiles.size()));

                    if (holder.binding.viewpager.getCurrentItem() == 0) {
                        holder.binding.imgLeft.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            holder.binding.imgRight.setOnClickListener(v -> {
                try {
                    holder.binding.imgLeft.setVisibility(View.VISIBLE);

                    holder.binding.viewpager.setCurrentItem(holder.binding.viewpager.getCurrentItem() + 1);
                    holder.binding.txtPage.setText(String.format(Locale.US,holder.binding.viewpager.getCurrentItem() + 1 + " / %d", item.portfolioFiles.size()));

                    if (holder.binding.viewpager.getCurrentItem() == myCustomPagerAdapter.getCount() - 1) {
                        holder.binding.imgRight.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            if (item.portfolioFiles.size() == 1) {
                holder.binding.imgLeft.setVisibility(View.GONE);
                holder.binding.imgRight.setVisibility(View.GONE);
            }

        } else {
            holder.binding.imgLeft.setVisibility(View.GONE);
            holder.binding.imgRight.setVisibility(View.GONE);
            holder.binding.txtPage.setText("0 / 0");
        }*/
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public List<Portfolios.Data> getData() {
        return mDataset;
    }

    private void showPortfolioDialog(List<Portfolios.PortfolioFiles> arrPortfolioImages, int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_zoom_portfolio_image);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        CustomTextView txtClose = dialog.findViewById(R.id.txtClose);
        ViewPager viewPager = dialog.findViewById(R.id.viewPager);
        CustomTextView txtPageCount = dialog.findViewById(R.id.txtPageCount);

        txtClose.setOnClickListener(v -> dialog.dismiss());

        PortfolioZoomPagerAdapter myCustomPagerAdapter = new PortfolioZoomPagerAdapter(activity, arrPortfolioImages, filePath);
        viewPager.setAdapter(myCustomPagerAdapter);
        viewPager.setCurrentItem(position);
        viewPager.setPageTransformer(true, new DepthTransformation());
        txtPageCount.setText(String.format(Locale.US,position + 1 + " / %d", arrPortfolioImages.size()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                txtPageCount.setText(String.format(position + 1 + " / %d", arrPortfolioImages.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ItemPortfolioListBinding binding;

        SimpleViewHolder(ItemPortfolioListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            itemView.getRoot().setOnClickListener(view -> {
//                showPortfolioDialog(mDataset.get(getAbsoluteAdapterPosition()).portfolioFiles, binding.viewpager.getCurrentItem());
            });

        }
    }

}
