package com.nojom.client.multitypepicker.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.nojom.client.R;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.ToastUtil;
import com.nojom.client.multitypepicker.Util;
import com.nojom.client.multitypepicker.activity.ImageBrowserActivity;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.util.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.os.Environment.DIRECTORY_DCIM;
import static com.nojom.client.multitypepicker.Constant.REQUEST_CODE_TAKE_IMAGE;
import static com.nojom.client.multitypepicker.activity.ImageBrowserActivity.IMAGE_BROWSER_INIT_INDEX;
import static com.nojom.client.multitypepicker.activity.ImageBrowserActivity.IMAGE_BROWSER_SELECTED_LIST;

public class ImagePickAdapter extends BaseAdapter<ImageFile, ImagePickAdapter.ImagePickViewHolder> {
    private boolean isNeedImagePager;
    private boolean isNeedCamera;
    private int mMaxNumber;
    private int mCurrentNumber = 0;
    public String mImagePath, filePath;
    public Uri mImageUri;

    public ImagePickAdapter(Context ctx, boolean needCamera, boolean isNeedImagePager, int max) {
        this(ctx, new ArrayList<ImageFile>(), needCamera, isNeedImagePager, max);
    }

    private ImagePickAdapter(Context ctx, ArrayList<ImageFile> list, boolean needCamera, boolean needImagePager, int max) {
        super(ctx, list);
        isNeedCamera = needCamera;
        mMaxNumber = max;
        isNeedImagePager = needImagePager;
    }

    @Override
    public ImagePickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.vw_layout_item_image_pick, parent, false);
        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        if (params != null) {
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            int width = Objects.requireNonNull(wm).getDefaultDisplay().getWidth();
            params.height = width / ImagePickActivity.COLUMN_NUMBER;
        }
        return new ImagePickViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ImagePickViewHolder holder, int position) {
        if (isNeedCamera && position == 0) {
            holder.mIvCamera.setVisibility(View.VISIBLE);
            holder.mIvThumbnail.setVisibility(View.INVISIBLE);
            holder.mCbx.setVisibility(View.INVISIBLE);
            holder.mShadow.setVisibility(View.INVISIBLE);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
                File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM).getAbsolutePath()
                        + "/IMG_" + timeStamp + ".jpg");
                mImagePath = file.getAbsolutePath();
                filePath = "file://" + Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM).getAbsolutePath()
                        + "/IMG_" + timeStamp + ".jpg";

                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, mImagePath);
                contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, file.getName());
                mImageUri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                if (Util.detectIntent(mContext, intent)) {
                    ((Activity) mContext).startActivityForResult(intent, REQUEST_CODE_TAKE_IMAGE);
                } else {
                    ToastUtil.getInstance(mContext).showToast(mContext.getString(R.string.vw_no_photo_app));
                }
            });
        } else {
            holder.mIvCamera.setVisibility(View.INVISIBLE);
            holder.mIvThumbnail.setVisibility(View.VISIBLE);
            holder.mCbx.setVisibility(View.VISIBLE);

            ImageFile file;
            if (isNeedCamera) {
                file = mList.get(position - 1);
            } else {
                file = mList.get(position);
            }

            Utils.setImage(holder.mIvThumbnail, "file://" + file.getPath(), 0, 0, false);

            if (file.isSelected()) {
                holder.mCbx.setSelected(true);
                holder.mShadow.setVisibility(View.VISIBLE);
            } else {
                holder.mCbx.setSelected(false);
                holder.mShadow.setVisibility(View.INVISIBLE);
            }

            holder.mCbx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!v.isSelected() && isUpToMax()) {
                        ToastUtil.getInstance(mContext).showToast(R.string.vw_up_to_max);
                        return;
                    }

                    int index = isNeedCamera ? holder.getAbsoluteAdapterPosition() - 1 : holder.getAbsoluteAdapterPosition();
                    if (v.isSelected()) {
                        holder.mShadow.setVisibility(View.INVISIBLE);
                        holder.mCbx.setSelected(false);
                        mCurrentNumber--;
                        mList.get(index).setSelected(false);
                    } else {
                        holder.mShadow.setVisibility(View.VISIBLE);
                        holder.mCbx.setSelected(true);
                        mCurrentNumber++;
                        mList.get(index).setSelected(true);
                    }

                    if (mListener != null) {
                        mListener.OnSelectStateChanged(holder.mCbx.isSelected(), mList.get(index));
                    }
                }
            });

            if (isNeedImagePager) {
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, ImageBrowserActivity.class);
                    intent.putExtra(Constant.MAX_NUMBER, mMaxNumber);
                    intent.putExtra(IMAGE_BROWSER_INIT_INDEX,
                            isNeedCamera ? holder.getAbsoluteAdapterPosition() - 1 : holder.getAbsoluteAdapterPosition());
                    intent.putParcelableArrayListExtra(IMAGE_BROWSER_SELECTED_LIST, ((ImagePickActivity) mContext).mSelectedList);
                    ((Activity) mContext).startActivityForResult(intent, Constant.REQUEST_CODE_BROWSER_IMAGE);
                });
            } else {
                holder.mIvThumbnail.setOnClickListener(view -> {
                    if (!holder.mCbx.isSelected() && isUpToMax()) {
                        ToastUtil.getInstance(mContext).showToast(R.string.vw_up_to_max);
                        return;
                    }

                    int index = isNeedCamera ? holder.getAbsoluteAdapterPosition() - 1 : holder.getAbsoluteAdapterPosition();
                    if (holder.mCbx.isSelected()) {
                        holder.mShadow.setVisibility(View.INVISIBLE);
                        holder.mCbx.setSelected(false);
                        mCurrentNumber--;
                        mList.get(index).setSelected(false);
                    } else {
                        holder.mShadow.setVisibility(View.VISIBLE);
                        holder.mCbx.setSelected(true);
                        mCurrentNumber++;
                        mList.get(index).setSelected(true);
                    }

                    if (mListener != null) {
                        mListener.OnSelectStateChanged(holder.mCbx.isSelected(), mList.get(index));
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return isNeedCamera ? mList.size() + 1 : mList.size();
    }

    class ImagePickViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvCamera;
        private ImageView mIvThumbnail;
        private View mShadow;
        private ImageView mCbx;

        public ImagePickViewHolder(View itemView) {
            super(itemView);
            mIvCamera = itemView.findViewById(R.id.iv_camera);
            mIvThumbnail = itemView.findViewById(R.id.iv_thumbnail);
            mShadow = itemView.findViewById(R.id.shadow);
            mCbx = itemView.findViewById(R.id.cbx);
        }
    }

    public boolean isUpToMax() {
        return mCurrentNumber >= mMaxNumber;
    }

    public void setCurrentNumber(int number) {
        mCurrentNumber = number;
    }
}
