package com.nojom.client.fragment.postjob;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.FragmentChooseSkillsBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.SkillsById;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ChooseSkillsFragmentVM extends AndroidViewModel implements View.OnClickListener {
    private FragmentChooseSkillsBinding binding;
    private BaseFragment fragment;
    private List<SkillsById.Data> skillsList;
    private boolean isEdit;
    private String serviceId;
    private String skillIds = "";
    private String skillNames = "";

    ChooseSkillsFragmentVM(Application application, FragmentChooseSkillsBinding chooseSkillsBinding, BaseFragment chooseSkillsFragment) {
        super(application);
        binding = chooseSkillsBinding;
        fragment = chooseSkillsFragment;
        initData();
    }

    private void initData() {
        binding.progress.imgBack.setOnClickListener(this);
        binding.progress.tvNext.setOnClickListener(this);
        binding.tvSkip.setOnClickListener(this);

        if (fragment.getArguments() != null) {
            isEdit = fragment.getArguments().getBoolean(Constants.IS_EDIT);
        }
        serviceId = Preferences.readString(fragment.activity, Constants.PLATFORM_ID, "");
        skillsList = new ArrayList<>();

        if (!isEdit) {
            binding.progress.progress.setProgress(20);
        } else {
            binding.progress.progress.setVisibility(View.GONE);
            binding.progress.tvNext.setText(fragment.getString(R.string.save));
            binding.tvSkip.setVisibility(View.GONE);
        }

        binding.tagGroup.setOnTagClickListener(tag -> {
            for (int i = 0; i < skillsList.size(); i++) {
                for (SkillsById.Data data : skillsList) {
                    if (data.skills.name.equals(tag.getText().toString())) {
                        data.isSelected = tag.isChecked;
                    }
                }
            }
        });

        getSkillsByServiceId();
    }

    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(fragment.activity);
        switch (view.getId()) {
            case R.id.img_back:
                fragment.goBackTo();
                break;
            case R.id.tv_next:
                getSkillIds();
                if (!TextUtils.isEmpty(skillIds)) {
                    Preferences.writeString(fragment.activity, Constants.SKILL_IDS, skillIds);
                    Preferences.writeString(fragment.activity, Constants.SKILL_NAMES, skillNames);
                    if (isEdit) {
                        fragment.goBackTo();
                    } else {
                        fragment.activity.replaceFragment(new PayTypeFragment());
                    }
                } else {
                    fragment.activity.validationError(fragment.activity.getString(R.string.please_select_skill));
                }
                break;
            case R.id.tv_skip:
                Preferences.writeString(fragment.activity, Constants.SKILL_IDS, "");
                Preferences.writeString(fragment.activity, Constants.SKILL_NAMES, "");
                fragment.activity.replaceFragment(new PayTypeFragment());
                break;
        }
    }

    private void getSkillIds() {
        skillIds = "";
        skillNames = "";
        for (SkillsById.Data data : skillsList) {
            if (data.isSelected) {
                if (TextUtils.isEmpty(skillIds)) {
                    skillIds = data.skills.id + "";
                    skillNames = data.skills.name;
                } else {
                    skillIds = skillIds + "," + data.skills.id;
                    skillNames = String.format(Locale.US,"%s,%s", skillNames, data.skills.name);
                }
            }
        }
    }

    private void getSkillsByServiceId() {
        if (!fragment.activity.isNetworkConnected())
            return;
    }
}
