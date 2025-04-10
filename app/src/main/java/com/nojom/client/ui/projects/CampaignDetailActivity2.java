package com.nojom.client.ui.projects;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityCampDetailsBinding;
import com.nojom.client.fragment.chat.CampDetailFragment;
import com.nojom.client.fragment.chat.CampPayFragment;
import com.nojom.client.fragment.chat.CampStarsFragment;
import com.nojom.client.model.CampList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CampaignDetailActivity2 extends BaseActivity implements View.OnClickListener {

    private ProjectDetailsActivityVM projectDetailsActivityVM;
    public CampList campList;
    private ActivityCampDetailsBinding binding;
    private int selTab;
    private String campId;
    private CampByIdVM campByIdVM;
    private CampDetailFragment campDetailFragment;
    private CampStarsFragment campStarsFragment;
    private CampPayFragment campPayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camp_details);
//        projectDetailsActivityVM = new ProjectDetailsActivityVM(Task24Application.getInstance(), detailsBinding, this);
        campByIdVM = new CampByIdVM(Task24Application.getInstance(), this);

        binding.txtTabDetail.setOnClickListener(this);
        binding.txtTabStars.setOnClickListener(this);
        binding.txtTabPay.setOnClickListener(this);
        binding.imgMenu.setOnClickListener(this);

        setupViewPager(binding.viewpager);

        if (getIntent() != null) {
            campList = (CampList) getIntent().getSerializableExtra(Constants.PROJECT);
            selTab = getIntent().getIntExtra("state", 0);
            campId = getIntent().getStringExtra("camp_id");

            if (campList == null && !TextUtils.isEmpty(campId)) {
                campList = new CampList();
                campList.campaignId = Integer.valueOf(campId);
            }
        }

        if (selTab == 1) {
            binding.imgMenu.setVisibility(View.VISIBLE);
        } else {
            binding.imgMenu.setVisibility(View.GONE);
        }


        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        binding.viewpager.setCurrentItem(0);

        binding.imgBack.setOnClickListener(view -> onBackPressed());

        campByIdVM.campListData.observe(this, campListData -> {
            campList.campaignTitle = campListData.campaign_title;
            campList.campaignBrief = campListData.campaign_brief;
            campList.campaignStatus = campListData.campaign_status;
            campList.tax_rate = campListData.tax_rate;
            campList.agency_fee_rate = campListData.agency_fee_rate;
            campList.subtotal = campListData.subtotal;
            campList.totalPrice = campListData.subtotal;
            if (campListData.campaign_attachment_url != null) {
                campList.campaignAttachmentUrl = campListData.campaign_attachment_url;
            }
            campList.campaignLaunchDate = campListData.campaign_launch_date;
            campList.timestamp = campListData.campaign_created_at;
            if (campListData.profiles != null) {
                campList.profiles = campListData.profiles;
            }
            if (campListData.socialPlatforms != null) {
                campList.socialPlatforms = campListData.socialPlatforms;
            }
//            if (campListData.profiles != null) {
//                campList.socialPlatforms = campListData.profiles;
//            }
            if (campDetailFragment != null) {
                campDetailFragment.onResume();
            }
            if (campStarsFragment != null) {
                campStarsFragment.onResume();
            }
            if (campPayFragment != null) {
                campPayFragment.getdata(campList);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        campDetailFragment = new CampDetailFragment();
        campStarsFragment = new CampStarsFragment();
        campPayFragment = new CampPayFragment();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(campDetailFragment, getString(R.string.details));
        adapter.addFrag(campStarsFragment, getString(R.string.stars));
        adapter.addFrag(campPayFragment, getString(R.string.pay));
//        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        campByIdVM.getCampaignById(campList.campaignId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_tab_detail -> {
                binding.viewpager.setCurrentItem(0);
                setTab(0);
            }
            case R.id.txt_tab_pay -> {
                binding.viewpager.setCurrentItem(2);
                setTab(2);
            }
            case R.id.txt_tab_stars -> {
                binding.viewpager.setCurrentItem(1);
                setTab(1);
            }
            case R.id.img_menu -> openPopup(binding.imgMenu);
        }
    }

    private void setTab(int selTab) {
        switch (selTab) {
            case 0 -> {
                //binding.viewpager.setCurrentItem(0);
                binding.txtViewDetail.setVisibility(View.VISIBLE);
                binding.txtViewPay.setVisibility(View.GONE);
                binding.txtViewStars.setVisibility(View.GONE);
            }
            case 1 -> {
                //binding.viewpager.setCurrentItem(1);
                binding.txtViewDetail.setVisibility(View.GONE);
                binding.txtViewPay.setVisibility(View.GONE);
                binding.txtViewStars.setVisibility(View.VISIBLE);
            }
            case 2 -> {
                //binding.viewpager.setCurrentItem(2);
                binding.txtViewDetail.setVisibility(View.GONE);
                binding.txtViewPay.setVisibility(View.VISIBLE);
                binding.txtViewStars.setVisibility(View.GONE);
            }
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Fragment> mFragmentList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    private PopupWindow mPopupWindow;
    private View mContentView;

    private void openPopup(View anchor) {
        if (mPopupWindow == null) {
            mContentView = LayoutInflater.from(this).inflate(R.layout.camp_menu, null);
            TextView txtEdit = (TextView) mContentView.findViewById(R.id.tv_edit);
            TextView txtCancel = (TextView) mContentView.findViewById(R.id.tv_cancel);

            mContentView.setFocusable(true);
            mContentView.setFocusableInTouchMode(true);

            mPopupWindow = new PopupWindow(mContentView);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setTouchable(true);

            txtCancel.setOnClickListener(view -> {
                mPopupWindow.dismiss();
            });
            txtEdit.setOnClickListener(view -> {
                mPopupWindow.dismiss();
                Intent intent = new Intent(this, EditCampDataActivity.class);
                intent.putExtra("data", campList);
                startActivity(intent);
            });
        }

        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mContentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mPopupWindow.showAsDropDown(anchor, (anchor.getMeasuredWidth() - mContentView.getMeasuredWidth()) / 2, 0);
            mPopupWindow.update(anchor, mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());
        }

    }
}
