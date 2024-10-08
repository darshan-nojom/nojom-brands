package com.nojom.client.fragment.postjob;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentChooseSkillsBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;

public class ChooseSkillsFragment extends BaseFragment {

    public static ChooseSkillsFragment newInstance(boolean isEdit) {
        ChooseSkillsFragment fragment = new ChooseSkillsFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.IS_EDIT, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentChooseSkillsBinding chooseSkillsBinding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (chooseSkillsBinding != null) {
            return chooseSkillsBinding.getRoot();
        }
        chooseSkillsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose_skills, container, false);
        new ChooseSkillsFragmentVM(Task24Application.getInstance(), chooseSkillsBinding, this);
        return chooseSkillsBinding.getRoot();
    }
}
