package com.nojom.client.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nojom.client.R;
import com.nojom.client.model.Portfolios;
import com.nojom.client.ui.BaseActivity;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_VERTICAL_IMAGE = 0;
    private static final int TYPE_SQUARE_IMAGE = 1;

    private List<Portfolios.Data> mDataset;
    private BaseActivity activity;
    private String filePath, companyPath;

    public CustomAdapter(BaseActivity context, List<Portfolios.Data> objects, String filePath, String cmpPath) {
        this.mDataset = objects;
        this.filePath = filePath;
        this.companyPath = cmpPath;
        activity = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_VERTICAL_IMAGE;
        } else {
            return TYPE_SQUARE_IMAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VERTICAL_IMAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical_image, parent, false);
            return new VerticalImageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_square_image, parent, false);
            return new SquareImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == TYPE_VERTICAL_IMAGE && position <= mDataset.size()) {
            Portfolios.Data item = mDataset.get(position);
            // Bind data for vertical image
            VerticalImageViewHolder verticalImageViewHolder = (VerticalImageViewHolder) holder;
            // verticalImageViewHolder.verticalImage.setImageResource(...);

            if (item != null && item.filename != null) {
                verticalImageViewHolder.txtCompany.setText(item.getCompany_name(activity.getLanguage()));

                Glide.with(activity).load(filePath + item.filename)
                        .placeholder(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(verticalImageViewHolder.verticalImage);

                Glide.with(activity).load(companyPath + item.company_filename)
                        .placeholder(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(verticalImageViewHolder.imgCompany);


                if (item.filename.endsWith(".mp4") || item.filename.endsWith(".MP4")
                        || item.filename.endsWith(".MOV") || item.filename.endsWith(".mov")
                        || item.filename.endsWith(".AVI") || item.filename.endsWith(".avi")
                ) {

                    verticalImageViewHolder.imgPlay.setVisibility(View.VISIBLE);
                    verticalImageViewHolder.view.setVisibility(View.VISIBLE);
                } else {
                    verticalImageViewHolder.imgPlay.setVisibility(View.GONE);
                    verticalImageViewHolder.view.setVisibility(View.GONE);
                }

                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(filePath + item.filename));
                    activity.startActivity(intent);
                });
            }
        } else {
            // Bind data for square image
            SquareImageViewHolder squareImageViewHolder = (SquareImageViewHolder) holder;
            // squareImageViewHolder.squareImage.setImageResource(...);

            if (mDataset.get(position).data.size() > 0) {

                Glide.with(activity).load(filePath + mDataset.get(position).data.get(0).filename)
                        .placeholder(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(squareImageViewHolder.squareImage);
                if (mDataset.get(position).data.get(0).filename.endsWith(".mp4") || mDataset.get(position).data.get(0).filename.endsWith(".MP4")
                        || mDataset.get(position).data.get(0).filename.endsWith(".MOV") || mDataset.get(position).data.get(0).filename.endsWith(".mov")
                        || mDataset.get(position).data.get(0).filename.endsWith(".AVI") || mDataset.get(position).data.get(0).filename.endsWith(".avi")
                ) {

                    squareImageViewHolder.imgPlay.setVisibility(View.VISIBLE);
                    squareImageViewHolder.view.setVisibility(View.VISIBLE);
                } else {
                    squareImageViewHolder.imgPlay.setVisibility(View.GONE);
                    squareImageViewHolder.view.setVisibility(View.GONE);
                }

                squareImageViewHolder.squareImage.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(filePath + mDataset.get(position).data.get(0).filename));
                    activity.startActivity(intent);
                });

                if (mDataset.get(position).data.size() > 1) {
                    squareImageViewHolder.squareImage2.setVisibility(View.VISIBLE);
                    Glide.with(activity).load(filePath + mDataset.get(position).data.get(1).filename)
                            .placeholder(R.mipmap.ic_launcher)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(squareImageViewHolder.squareImage2);

                    if (mDataset.get(position).data.get(1).filename.endsWith(".mp4") || mDataset.get(position).data.get(1).filename.endsWith(".MP4")
                            || mDataset.get(position).data.get(1).filename.endsWith(".MOV") || mDataset.get(position).data.get(1).filename.endsWith(".mov")
                            || mDataset.get(position).data.get(1).filename.endsWith(".AVI") || mDataset.get(position).data.get(1).filename.endsWith(".avi")
                    ) {
                        squareImageViewHolder.imgPlay1.setVisibility(View.VISIBLE);
                        squareImageViewHolder.view1.setVisibility(View.VISIBLE);
                    } else {
                        squareImageViewHolder.imgPlay1.setVisibility(View.GONE);
                        squareImageViewHolder.view1.setVisibility(View.GONE);
                    }

                    squareImageViewHolder.squareImage2.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(filePath + mDataset.get(position).data.get(1).filename));
                        activity.startActivity(intent);
                    });
                } else {
                    squareImageViewHolder.squareImage2.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
//        return (mDataset.size() / 3) * 2 + (mDataset.size() % 3 == 0 ? 0 : 1);
//        return (mDataset.size() / 2) * 3 + (mDataset.size() % 2 == 0 ? 0 : 2);
    }


    public class SquareImageViewHolder extends RecyclerView.ViewHolder {
        ImageView squareImage, squareImage2, imgPlay, imgPlay1, view, view1;

        public SquareImageViewHolder(View itemView) {
            super(itemView);
            squareImage = itemView.findViewById(R.id.square_image);
            squareImage2 = itemView.findViewById(R.id.square_image_2);
            imgPlay = itemView.findViewById(R.id.img_play);
            imgPlay1 = itemView.findViewById(R.id.img_play1);
            view = itemView.findViewById(R.id.view);
            view1 = itemView.findViewById(R.id.view1);
        }
    }

    public class VerticalImageViewHolder extends RecyclerView.ViewHolder {
        ImageView verticalImage, imgPlay, view;
        RoundedImageView imgCompany;
        TextView txtCompany;

        public VerticalImageViewHolder(View itemView) {
            super(itemView);
            verticalImage = itemView.findViewById(R.id.vertical_image);
            imgCompany = itemView.findViewById(R.id.img_company);
            imgPlay = itemView.findViewById(R.id.img_play);
            view = itemView.findViewById(R.id.view);
            txtCompany = itemView.findViewById(R.id.tv_chat);

            /*itemView.setOnClickListener(v -> {
                if (mDataset.get(getAdapterPosition()).data.get(0).filename.endsWith(".mp4") || mDataset.get(getAdapterPosition()).data.get(0).filename.endsWith(".MP4")
                        || mDataset.get(getAdapterPosition()).data.get(0).filename.endsWith(".MOV") || mDataset.get(getAdapterPosition()).data.get(0).filename.endsWith(".mov")
                        || mDataset.get(getAdapterPosition()).data.get(0).filename.endsWith(".AVI") || mDataset.get(getAdapterPosition()).data.get(0).filename.endsWith(".avi")
                ) {
                    //play video
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(filePath + mDataset.get(getBindingAdapterPosition()).filename));
                    activity.startActivity(intent);
                } else {
                    //view image
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(filePath + mDataset.get(getBindingAdapterPosition()).filename));
                    activity.startActivity(intent);
                }
            });*/
        }
    }
}
