package com.nojom.client.multitypepicker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bm.library.PhotoView;
import com.nojom.client.R;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.ToastUtil;
import com.nojom.client.multitypepicker.filter.FileFilter;
import com.nojom.client.multitypepicker.filter.entity.Directory;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.Objects;

import static com.nojom.client.multitypepicker.activity.ImagePickActivity.DEFAULT_MAX_NUMBER;

public class ImageBrowserActivity extends BaseActivity {
    public static final String IMAGE_BROWSER_INIT_INDEX = "ImageBrowserInitIndex";
    public static final String IMAGE_BROWSER_SELECTED_LIST = "ImageBrowserSelectedList";
    private int mMaxNumber;
    private int mCurrentNumber = 0;
    private int initIndex = 0;
    private int mCurrentIndex = 0;

    private ViewPager mViewPager;
    private Toolbar mTbImagePick;
    private ArrayList<ImageFile> mList = new ArrayList<>();
    private ImageView mSelectView;
    private ArrayList<ImageFile> mSelectedFiles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.vw_activity_image_browser);

        mMaxNumber = getIntent().getIntExtra(Constant.MAX_NUMBER, DEFAULT_MAX_NUMBER);
        initIndex = getIntent().getIntExtra(IMAGE_BROWSER_INIT_INDEX, 0);
        mCurrentIndex = initIndex;
        mSelectedFiles = getIntent().getParcelableArrayListExtra(IMAGE_BROWSER_SELECTED_LIST);
        mCurrentNumber = Objects.requireNonNull(mSelectedFiles).size();
        loadData();
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        mTbImagePick = findViewById(R.id.tb_image_pick);
        mTbImagePick.setTitle(mCurrentNumber + "/" + mMaxNumber);
        setSupportActionBar(mTbImagePick);
        mTbImagePick.setNavigationOnClickListener(v -> finishThis());

        mSelectView = findViewById(R.id.cbx);
        mSelectView.setOnClickListener(v -> {
            if (!v.isSelected() && isUpToMax()) {
                ToastUtil.getInstance(ImageBrowserActivity.this).showToast(R.string.vw_up_to_max);
                return;
            }

            if (v.isSelected()) {
                mList.get(mCurrentIndex).setSelected(false);
                mCurrentNumber--;
                v.setSelected(false);
                mSelectedFiles.remove(mList.get(mCurrentIndex));
            } else {
                mList.get(mCurrentIndex).setSelected(true);
                mCurrentNumber++;
                v.setSelected(true);
                mSelectedFiles.add(mList.get(mCurrentIndex));
            }

            mTbImagePick.setTitle(mCurrentNumber + "/" + mMaxNumber);
        });

        mViewPager = findViewById(R.id.vp_image_pick);
        mViewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        mViewPager.setAdapter(new ImageBrowserAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                mSelectView.setSelected(mList.get(mCurrentIndex).isSelected());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(initIndex, false);
        mSelectView.setSelected(mList.get(mCurrentIndex).isSelected());
    }

    private void loadData() {
        FileFilter.getImages(this, directories -> {
            mList.clear();
            for (Directory<ImageFile> directory : directories) {
                mList.addAll(directory.getFiles());
            }

            for (ImageFile file : mList) {
                if (mSelectedFiles.contains(file)) {
                    file.setSelected(true);
                }
            }

            initView();
            Objects.requireNonNull(mViewPager.getAdapter()).notifyDataSetChanged();
        });
    }

    private class ImageBrowserAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView view = new PhotoView(ImageBrowserActivity.this);
            view.enable();
            view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            Utils.setImage(view, "file://" + mList.get(position).getPath(), 150, 150, false);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vw_menu_image_pick, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            finishThis();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isUpToMax() {
        return mCurrentNumber >= mMaxNumber;
    }

    private void finishThis() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constant.RESULT_BROWSER_IMAGE, mSelectedFiles);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishThis();
    }
}
