package com.nojom.client.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nojom.client.R;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class GigPagerAdapter extends PagerAdapter implements Constants {
    private BaseActivity context;
    private List<ExpertGigDetail.GigImage> arrGigImageList;
    private String imagePath;
    private LayoutInflater layoutInflater;

    public GigPagerAdapter(BaseActivity context, List<ExpertGigDetail.GigImage> arrGigImageList, String imagePath) {
        this.context = context;
        this.arrGigImageList = arrGigImageList;
        this.imagePath = imagePath;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrGigImageList.size();
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
        return view == ((RelativeLayout) object);
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_gig_detail_pager, container, false);

        ImageView imageView = itemView.findViewById(R.id.iv_gig_image_list);
        ProgressBar progressBar = itemView.findViewById(R.id.progressBar);

        if (!TextUtils.isEmpty(arrGigImageList.get(position).imageName)) {
            Glide.with(context).load(imagePath + arrGigImageList.get(position).imageName)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.ic_launcher);
        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}