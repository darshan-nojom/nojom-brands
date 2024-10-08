package com.nojom.client.fragment.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentSkillProfileBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class SkillProfileFragment extends BaseFragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSkillProfileBinding skillProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_skill_profile, container, false);
        SkillProfileFragmentVM skillProfileFragmentVM = new SkillProfileFragmentVM(Task24Application.getInstance(), skillProfileBinding, this);
        skillProfileFragmentVM.agentSkillAPI();
        return skillProfileBinding.getRoot();
    }
}
