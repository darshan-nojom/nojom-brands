package com.nojom.client.ui.settings;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.nojom.client.R;
import com.nojom.client.databinding.ActivityGetDiscountBinding;
import com.nojom.client.segment.SegmentedButtonGroup;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class GetDiscountActivityVM extends AndroidViewModel {
    private ActivityGetDiscountBinding binding;
    private BaseActivity activity;
    private int tabPosition = 0;
    private String mInvitationUrl;
    private EarnMoneyFragment earnMoneyFragment;

    GetDiscountActivityVM(Application application, ActivityGetDiscountBinding getDiscountBinding, BaseActivity getDiscountActivity) {
        super(application);
        binding = getDiscountBinding;
        activity = getDiscountActivity;
        initData();
    }

    String getmInvitationUrl() {
        return mInvitationUrl;
    }

    private void initData() {
        binding.imgBack.setOnClickListener(v -> activity.onBackPressed());
        if (activity.getIntent() != null) {
            tabPosition = activity.getIntent().getIntExtra(Constants.TAB_BALANCE, 0);
        }

        if (activity.getCurrency().equals("SAR")) {
            binding.tabEarnMoney.setText(activity.getString(R.string.get_200_sar));
            binding.tabEasy.setText(activity.getString(R.string.easy_12_sar));
            binding.tabWin.setText(activity.getString(R.string.win_100_sar));
        }

        generateDynamicLink();
        setupPager();
    }

    private void setupPager() {
        setupViewPager(binding.viewpager);

        binding.segmentedGroupTab.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(int position) {
                if (position == 0) {
                    binding.viewpager.setCurrentItem(0);
                } else if (position == 1) {
                    binding.viewpager.setCurrentItem(1);
                } else if (position == 2) {
                    binding.viewpager.setCurrentItem(2);
                } else if (position == 3) {
                    binding.viewpager.setCurrentItem(3);
                }
            }
        });

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                binding.viewpager.setCurrentItem(i);
                setTab(i);
                if (i == 0 && earnMoneyFragment != null) {
                    earnMoneyFragment.setLink();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        binding.viewpager.setCurrentItem(tabPosition);
        setTab(tabPosition);
    }

    private void setTab(int pos) {
        if (pos == 0) {
            binding.segmentedGroupTab.setPosition(0, true);
        } else if (pos == 1) {
            binding.segmentedGroupTab.setPosition(1, true);
        } else if (pos == 2) {
            binding.segmentedGroupTab.setPosition(2, true);
        } else if (pos == 3) {
            binding.segmentedGroupTab.setPosition(3, true);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager());
        adapter.addFrag(earnMoneyFragment = new EarnMoneyFragment(), activity.getString(R.string.earn_money));
        adapter.addFrag(new MyLevelFragment(), activity.getString(R.string.my_level));
        adapter.addFrag(new EasyFragment(), activity.getString(R.string.easy_12));
        adapter.addFrag(new WinFragment(), activity.getString(R.string.get_discount));
        viewPager.setPageMargin(20);
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(4);
    }

    private void generateDynamicLink() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String uid = Objects.requireNonNull(user).getUid();
        if (TextUtils.isEmpty(uid)) {
            return;
        }
        String link = "https://24task.com/invite/?invitedby=" + uid + "&code=" + activity.getReferralCode();
        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(link))
                .setDomainUriPrefix("https://24taskpromo.page.link")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.nojom.client")
                                .setMinimumVersion(1)
                                .build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder("Task24.Task24")
                                .setAppStoreId("1397804027")
                                .setMinimumVersion("1.0")
                                .build())
                .buildShortDynamicLink()
                .addOnFailureListener(Throwable::printStackTrace)
                .addOnSuccessListener(shortDynamicLink -> {
                    mInvitationUrl = Objects.requireNonNull(shortDynamicLink.getShortLink()).toString();
                    if (earnMoneyFragment != null) {
                        earnMoneyFragment.setLink();
                    }
                });
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
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
}
