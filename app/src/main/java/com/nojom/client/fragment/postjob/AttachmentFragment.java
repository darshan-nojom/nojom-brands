package com.nojom.client.fragment.postjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.FragmentAttachmentBinding;
import com.nojom.client.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class AttachmentFragment extends BaseFragment {
    private AttachmentFragmentVM attachmentFragmentVM;
    private FragmentAttachmentBinding attachmentBinding;

    public static AttachmentFragment newInstance() {
        AttachmentFragment fragment = new AttachmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (attachmentBinding != null) {
            return attachmentBinding.getRoot();
        }
        attachmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_attachment, container, false);
        attachmentFragmentVM = new AttachmentFragmentVM(Task24Application.getInstance(), attachmentBinding, this);
        return attachmentBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        attachmentFragmentVM.onResumeMethod();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        attachmentFragmentVM.onActivityResult(requestCode, resultCode, data);
    }
}
