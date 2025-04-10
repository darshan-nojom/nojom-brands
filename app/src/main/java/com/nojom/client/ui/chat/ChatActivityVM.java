package com.nojom.client.ui.chat;

import android.app.Application;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.viewpager.widget.ViewPager;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityChatBinding;
import com.nojom.client.fragment.chat.ChatListFragment;
import com.nojom.client.fragment.chat.LiveChatFragment;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class ChatActivityVM extends AndroidViewModel implements BaseActivity.OnProfileLoadListener {
    private final ActivityChatBinding binding;
    private final BaseActivity activity;

    ChatActivityVM(Application application, ActivityChatBinding chatBinding, BaseActivity chatActivity) {
        super(application);
        binding = chatBinding;
        activity = chatActivity;
        initData();
    }

    private void initData() {
        setupPager();
        activity.setOnProfileLoadListener(this);
        binding.imgChat.setOnClickListener(v -> activity.showContactUsDialog());
    }

    private void setupPager() {
        setupViewPager(binding.viewpager);

        binding.segmentedGroupTab.setOnPositionChangedListener(position -> {
            if (position == 0) {
                binding.viewpager.setCurrentItem(0);
            } else if (position == 1) {
                binding.viewpager.setCurrentItem(1);
            } else if (position == 2) {
                binding.viewpager.setCurrentItem(2);
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
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        binding.viewpager.setCurrentItem(0);
        setTab(0);
    }

    private void setTab(int pos) {
        if (pos == 0) {
            binding.segmentedGroupTab.setPosition(0, true);
        } else if (pos == 1) {
            binding.segmentedGroupTab.setPosition(1, true);
        } else if (pos == 2) {
            binding.segmentedGroupTab.setPosition(2, true);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager());
        adapter.addFrag(new ChatListFragment(), activity.getString(R.string.influencers));
//        adapter.addFrag(new LiveChatFragment(), activity.getString(R.string.live_chat));
//        adapter.addFrag(new ManagerFragment(), activity.getString(R.string.manager));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onProfileLoad(Profile data) {

    }

    public void onResume() {
        activity.getProfile();
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
