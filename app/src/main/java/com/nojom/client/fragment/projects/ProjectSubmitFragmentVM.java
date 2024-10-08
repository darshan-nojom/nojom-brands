package com.nojom.client.fragment.projects;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.adapter.SubmitFilesAdapter;
import com.nojom.client.databinding.FragmentProjectSubmitBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.FileList;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.ui.projects.ProjectDetailsActivity;

import java.util.ArrayList;
import java.util.List;

class ProjectSubmitFragmentVM extends AndroidViewModel implements RecyclerviewAdapter.OnViewBindListner {
    private FragmentProjectSubmitBinding binding;
    private BaseFragment fragment;
    private ProjectByID projectData;
    private List<FileList> filesList;
    private SubmitFilesAdapter fileAdapter;

    ProjectSubmitFragmentVM(Application application, FragmentProjectSubmitBinding projectSubmitBinding, BaseFragment projectSubmitFragment) {
        super(application);
        binding = projectSubmitBinding;
        fragment = projectSubmitFragment;
        initData();
    }

    private void initData() {

        if (fragment.activity != null) {
            projectData = ((ProjectDetailsActivity) fragment.activity).getProjectData();
        }

        binding.noData.tvNoTitle.setText(fragment.getString(R.string.no_files));
        binding.noData.tvNoDescription.setText(fragment.getString(R.string.no_submitted_file_desc));

        if (projectData != null) {
            filesList = projectData.submittedFiles;
        }
        binding.rvSubmitJobs.setLayoutManager(new LinearLayoutManager(fragment.activity));

        setAdapter();

    }

    private void setAdapter() {
        if (filesList != null && filesList.size() > 0) {
            binding.noData.llNoData.setVisibility(View.GONE);
            if (fileAdapter == null) {
                fileAdapter = new SubmitFilesAdapter(fragment.activity, (ArrayList<FileList>) filesList,projectData.submittedPath);
                binding.rvSubmitJobs.setAdapter(fileAdapter);
            }

            if (binding.rvSubmitJobs.getAdapter() == null) {
                binding.rvSubmitJobs.setAdapter(fileAdapter);
            }
        } else {
            binding.noData.llNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void bindView(View view, int position) {
        //TODO: check here (is code necesssory?)
    }
}
