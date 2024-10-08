package com.nojom.client.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.nojom.client.model.Portfolios;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.TouchImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class PortfolioZoomPagerAdapter extends PagerAdapter implements Constants {
    private BaseActivity context;
    private List<Portfolios.PortfolioFiles> portfolioFiles;
    private LayoutInflater layoutInflater;
    private String filePath;

    public PortfolioZoomPagerAdapter(BaseActivity context, List<Portfolios.PortfolioFiles> images, String filePath) {
        this.context = context;
        this.portfolioFiles = images;
        this.filePath = filePath;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return portfolioFiles.size();
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
        return view == object;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_image_zoom_portfolio, container, false);

        TouchImageView imageView = itemView.findViewById(R.id.image);
        RelativeLayout loutMain = itemView.findViewById(R.id.loutMain);

        Glide.with(context).load(filePath + portfolioFiles.get(position).path)
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}