package com.nojom.client.ui.home;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.viewpager.widget.ViewPager;

import com.nojom.client.R;

import com.nojom.client.databinding.ActivityFindExpertBinding;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.ExpertFilterActivity;
import com.nojom.client.ui.MainActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

class FindExpertActivityVM extends AndroidViewModel implements View.OnClickListener {
    private ActivityFindExpertBinding binding;
    private BaseActivity activity;
    private static final int REQ_FILTER = 101;
    private int pageNo = 1;

    public String sortType = "JOB";
    public int serviceCatId = 0;
    public int languageId = 0;
    public String workBase = "", availability = "";
    public String skillIds = "";
    private boolean loading;
    private AllLawyerFragment allLawyerFragment;
    private SavedLawyerFragment savedLawyerFragment;
    private HiredLawyerFragment hiredLawyerFragment;
    private int tempSelCatId = 0;
    private String selectedTab;
    private boolean isCallAPIForSearch, isFromPostJobScreen;

    public void setCallAPIForSearch(boolean callAPIForSearch) {
        isCallAPIForSearch = callAPIForSearch;
    }

    public boolean isFromPostJobScreen() {
        return isFromPostJobScreen;
    }

    FindExpertActivityVM(Application application, ActivityFindExpertBinding findExpertBinding, BaseActivity findExpertActivity) {
        super(application);
        binding = findExpertBinding;
        activity = findExpertActivity;
        initData();
    }

    public String getSearchKeyword() {
        return binding.etSearch.getText().toString().trim();
    }

    private void initData() {
        binding.rlFilter.setOnClickListener(this);
        binding.imgCheckAll.setOnClickListener(this);
        binding.imgBack.setOnClickListener(this);
        binding.rlCheckAll.setOnClickListener(this);
        binding.tvDone.setOnClickListener(this);
        binding.imgSearch.setOnClickListener(this);
        binding.tvCancel.setOnClickListener(this);

        if (activity.getIntent() != null) {
            tempSelCatId = activity.getIntent().getIntExtra("catid", 0);
            isFromPostJobScreen = activity.getIntent().getBooleanExtra("screen", false);
            serviceCatId = tempSelCatId;
        }

        if (isFromPostJobScreen) {
            binding.rlCheckAll.setVisibility(View.VISIBLE);
            binding.tvDone.setVisibility(View.VISIBLE);

            List<ExpertLawyers.Data> expertUsers = Preferences.getExpertUsers(activity);
            if (expertUsers == null || expertUsers.isEmpty()) {
                binding.rlCheckAll.performClick();
            }
        }

        binding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                resetFilter();
                if (!isCallAPIForSearch) {
                    if (binding.viewpager.getCurrentItem() == 0) {
                        if (allLawyerFragment != null) {
                            allLawyerFragment.getExperts(true, serviceCatId);
                        }
                    } else if (binding.viewpager.getCurrentItem() == 1) {
                        if (savedLawyerFragment != null) {
                            savedLawyerFragment.getExperts(true, serviceCatId);
                        }
                    } else if (binding.viewpager.getCurrentItem() == 2) {
                        if (hiredLawyerFragment != null) {
                            hiredLawyerFragment.getExperts(true, serviceCatId);
                        }
                    }
                    isCallAPIForSearch = true;
                }

                //hide keyboard
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.etSearch.getWindowToken(), 0);

                return true;
            }
            return false;
        });

        setupViewPager(binding.viewpager);

        Utils.trackAppsFlayerEvent(activity, "Experts_Screen");

        binding.segmentedGroupTab.setOnPositionChangedListener(position -> {
            if (position==0) {
                binding.viewpager.setCurrentItem(0);
            } else if (position==1) {
                binding.viewpager.setCurrentItem(1);
            } else if (position==2) {
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
            binding.segmentedGroupTab.setPosition(0,true);
        } else if (pos == 1) {
            binding.segmentedGroupTab.setPosition(1,true);
        } else if (pos == 2) {
            binding.segmentedGroupTab.setPosition(2,true);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager());
        adapter.addFrag(allLawyerFragment = new AllLawyerFragment(), activity.getString(R.string.all));
        adapter.addFrag(savedLawyerFragment = new SavedLawyerFragment(), activity.getString(R.string.save));
        adapter.addFrag(hiredLawyerFragment = new HiredLawyerFragment(), activity.getString(R.string.hire));
        viewPager.setPageMargin(20);
        viewPager.setAdapter(adapter);
    }


    static class ViewPagerAdapter extends FragmentStatePagerAdapter {
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

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_UNCHANGED;
        }
    }

    private void resetFilter() {
        pageNo = 1;
        sortType = "JOB";
        languageId = 0;
        serviceCatId = tempSelCatId;
        workBase = "";
        availability = "";
        skillIds = "";
        loading = false;
        binding.tvFilterCount.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
            case R.id.tv_done:
                activity.onBackPressed();
                break;
            case R.id.rl_post_job:
                activity.gotoMainActivity(Constants.TAB_POST_JOB);
                break;
            case R.id.img_check_all:
            case R.id.rl_check_all:
                binding.imgCheckAll.setImageResource(R.drawable.circle_check);

                if (binding.viewpager.getCurrentItem() == 0) {
                    if (allLawyerFragment != null) {
                        allLawyerFragment.unSelectAll();
                    }
                } else if (binding.viewpager.getCurrentItem() == 1) {
                    if (savedLawyerFragment != null) {
                        savedLawyerFragment.unSelectAll();
                    }
                } else if (binding.viewpager.getCurrentItem() == 2) {
                    if (hiredLawyerFragment != null) {
                        hiredLawyerFragment.unSelectAll();
                    }
                }

                break;
            case R.id.rl_filter:
                Intent i = new Intent(activity, ExpertFilterActivity.class);
                i.putExtra(Constants.SORT_BY, sortType);
                i.putExtra(Constants.SERVICE_CATEGORY_ID, serviceCatId);
                i.putExtra(Constants.LANGUAGE_ID, languageId);
                i.putExtra(Constants.WORKBASE, workBase);
                i.putExtra(Constants.AVAILABILITY, availability);
                i.putExtra(Constants.SKILL_ID, skillIds);
                activity.startActivityForResult(i, REQ_FILTER);
                break;
            case R.id.tv_cancel:
                Utils.hideSoftKeyboard(activity);
                binding.etSearch.setText("");
                serviceCatId = tempSelCatId;
                loading = false;
                if (binding.viewpager.getCurrentItem() == 0) {
                    if (allLawyerFragment != null) {
                        allLawyerFragment.setUserVisibleHint(true);
                    }
                } else if (binding.viewpager.getCurrentItem() == 1) {
                    if (savedLawyerFragment != null) {
                        savedLawyerFragment.setUserVisibleHint(true);
                    }
                } else if (binding.viewpager.getCurrentItem() == 2) {
                    if (hiredLawyerFragment != null) {
                        hiredLawyerFragment.setUserVisibleHint(true);
                    }
                }

//                binding.etSearch.setVisibility(View.INVISIBLE);
//                binding.imgSearch.setVisibility(View.VISIBLE);
                binding.tvCancel.setVisibility(View.VISIBLE);
//                binding.rlFilter.setVisibility(View.VISIBLE);

                break;
            case R.id.img_search:
                binding.etSearch.setVisibility(View.VISIBLE);
                binding.imgSearch.setVisibility(View.GONE);
                binding.tvCancel.setVisibility(View.VISIBLE);
                binding.etSearch.setFocusable(true);
                binding.etSearch.requestFocus();
                binding.etSearch.requestFocusFromTouch();
                binding.rlFilter.setVisibility(View.GONE);
                break;
        }
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQ_FILTER) {
            if (data != null) {
                resetFilter();

                sortType = data.getStringExtra(Constants.SORT_BY);
                serviceCatId = data.getIntExtra(Constants.SERVICE_CATEGORY_ID, 0);
                languageId = data.getIntExtra(Constants.LANGUAGE_ID, 0);
                workBase = data.getStringExtra(Constants.WORKBASE);
                availability = data.getStringExtra(Constants.AVAILABILITY);
                skillIds = data.getStringExtra(Constants.SKILL_ID);

                if (binding.viewpager.getCurrentItem() == 0) {
                    if (allLawyerFragment != null) {
                        allLawyerFragment.getExperts(true, serviceCatId);
                    }
                } else if (binding.viewpager.getCurrentItem() == 1) {
                    if (savedLawyerFragment != null) {
                        savedLawyerFragment.getExperts(true, serviceCatId);
                    }
                } else if (binding.viewpager.getCurrentItem() == 2) {
                    if (hiredLawyerFragment != null) {
                        hiredLawyerFragment.getExperts(true, serviceCatId);
                    }
                }

            }
        }
    }

    void onResumeMethod() {
        Preferences.writeString(activity, Constants.PLATFORM_ID, "");

        int filterCount = 0;
        if (!activity.isEmpty(sortType)) {
            filterCount = filterCount + 1;
        }

        if (serviceCatId != -1) {
            filterCount = filterCount + 1;
        }

        if (languageId != 0) {
            filterCount = filterCount + 1;
        }

        if (!activity.isEmpty(workBase)) {
            filterCount = filterCount + 1;
        }

        if (!activity.isEmpty(availability)) {
            filterCount = filterCount + 1;
        }

        if (!activity.isEmpty(skillIds)) {
            filterCount = filterCount + 1;
        }

        if (filterCount > 0/* && filterCount != 2*/) {
            binding.tvFilterCount.setVisibility(View.VISIBLE);
            binding.tvFilterCount.setText(String.format(Locale.US,"%d", filterCount));
        } else {
            binding.tvFilterCount.setVisibility(View.GONE);
        }
    }


    public void openPostJobFragment() {
        Intent i = new Intent(activity, MainActivity.class);
        i.putExtra(Constants.SCREEN_NAME, Constants.TAB_POST_JOB);
        i.putExtra("allcategory", true);
        activity.startActivity(i);
        activity.finish();
    }

    void unSelectAll() {
        binding.imgCheckAll.setImageResource(R.drawable.circle_uncheck);
        Preferences.setExpertUsers(activity, null);
    }
}
