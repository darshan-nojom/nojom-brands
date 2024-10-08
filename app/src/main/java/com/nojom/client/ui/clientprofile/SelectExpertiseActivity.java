package com.nojom.client.ui.clientprofile;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.databinding.ActivitySelectExpertiseBinding;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.EqualSpacingItemDecoration;
import com.nojom.client.util.Preferences;

import java.util.ArrayList;
import java.util.List;

public class SelectExpertiseActivity extends BaseActivity implements RecyclerviewAdapter.OnViewBindListner {
    private ActivitySelectExpertiseBinding binding;
    private RecyclerviewAdapter mAdapter;
    private Profile profileData;
    private int selectedAboutUsId = 0;
    private MyProfileActivityVM myProfileActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_expertise);
        myProfileActivityVM = new MyProfileActivityVM(Task24Application.getInstance(), this);
        initData();
    }

    private void initData() {
        binding.toolbar.imgBack.setOnClickListener(v -> onBackPressed());
        binding.toolbar.tvSave.setVisibility(View.VISIBLE);
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        binding.rvSkills.setLayoutManager(manager);
        binding.rvSkills.addItemDecoration(new EqualSpacingItemDecoration(26));

        profileData = Preferences.getProfileData(this);

        if (profileData.aboutus_id != 0) {
            selectedAboutUsId = profileData.aboutus_id;
            if (selectedAboutUsId == 6) {//other case
                binding.etName.setText(profileData.other_aboutus);
                binding.etName.setVisibility(View.VISIBLE);
            }
        }

        if (profileData.about != null) {
            mAdapter = new RecyclerviewAdapter((ArrayList<?>) profileData.about, R.layout.item_skills_edit, SelectExpertiseActivity.this);
            binding.rvSkills.setAdapter(mAdapter);
            binding.rvSkills.setFocusable(false);
        }

        myProfileActivityVM.getIsShowProgress().observe(this, aBoolean -> {
            if (aBoolean) {
                binding.toolbar.tvSave.setVisibility(View.INVISIBLE);
                binding.toolbar.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.toolbar.tvSave.setVisibility(View.VISIBLE);
                binding.toolbar.progressBar.setVisibility(View.GONE);
                onBackPressed();
            }
        });

        binding.toolbar.tvSave.setOnClickListener(v -> {
            if (selectedAboutUsId == 0) {
                toastMessage(getString(R.string.select_a_category));
                return;
            }

            if (selectedAboutUsId == 6) {//other
                if (TextUtils.isEmpty(binding.etName.getText().toString().trim())) {
                    toastMessage(getString(R.string.about_us));
                    return;
                }
            }

            myProfileActivityVM.updateProfile(null, profileData.company_name, profileData.brand_name, profileData.firstName,
                    profileData.lastName, profileData.email, profileData.contactNo, profileData.getMobilePrefix(profileData.contactNo),
                    selectedAboutUsId, binding.etName.getText().toString(),profileData.is_verified,profileData.username);
        });
    }

    @Override
    public void bindView(View view, final int position) {
        final TextView textView = view.findViewById(R.id.tv_skill);
        List<Profile.About> servicesList = profileData.about;
        if (servicesList == null || servicesList.size() == 0) {
            return;
        }
        textView.setText(servicesList.get(position).getName(getLanguage()));

        if (selectedAboutUsId == servicesList.get(position).id) {
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.black_button_bg));
            textView.setTextColor(Color.WHITE);
            textView.setTypeface(Typeface.createFromAsset(getAssets(), Constants.SFTEXT_BOLD));
        } else {
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.white_button_bg));
            textView.setTextColor(Color.BLACK);
            textView.setTypeface(Typeface.createFromAsset(getAssets(), Constants.SFTEXT_REGULAR));
        }

        textView.setOnClickListener(view1 -> {
            selectedAboutUsId = servicesList.get(position).id;
            mAdapter.notifyDataSetChanged();

            if (selectedAboutUsId == 6) {
                binding.etName.setVisibility(View.VISIBLE);
            } else {
                binding.etName.setVisibility(View.GONE);
                binding.etName.setText("");
            }
        });
    }
}
