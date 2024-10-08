package com.nojom.client.ui.projects;

import android.app.Application;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.viewpager.widget.ViewPager;

import com.nojom.client.databinding.ActivityMyProjectsBinding;
import com.nojom.client.fragment.projects.ProjectsListFragment;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class MyProjectsActivityVM extends AndroidViewModel {
    private ActivityMyProjectsBinding binding;
    private BaseActivity activity;

    MyProjectsActivityVM(Application application, ActivityMyProjectsBinding myProjectsBinding, BaseActivity myProjectsActivity) {
        super(application);
        binding = myProjectsBinding;
        activity = myProjectsActivity;
        initData();
    }

    private void initData() {
        if (!activity.isLogin()) {
            binding.linPlaceholderLogin.setVisibility(View.VISIBLE);
            binding.viewpager.setVisibility(View.GONE);
            binding.btnLogin.setOnClickListener(v -> activity.openLoginDialog());
        }
        setupTabs();
    }

    public void showHideHorizontalProgress(int visibility) {
        binding.hProgressBar.setVisibility(visibility);
    }

    private void setupTabs() {
        setupViewPager(binding.viewpager);

        binding.segmentedGroupTab.setOnPositionChangedListener(position -> {
            if (position==0) {
                binding.viewpager.setCurrentItem(0);
            } else if (position==1) {
                binding.viewpager.setCurrentItem(1);
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
            binding.segmentedGroupTab.setPosition(0,true);
        } else if (pos == 1) {
            binding.segmentedGroupTab.setPosition(1,true);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager());
        adapter.addFrag(ProjectsListFragment.newInstance(true), Constants.WORK_IN_PROGRESS);
        adapter.addFrag(ProjectsListFragment.newInstance(false), Constants.PAST_PROJECTS);
        viewPager.setPageMargin(20);
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        final List<String> mFragmentTitleList = new ArrayList<>();

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
