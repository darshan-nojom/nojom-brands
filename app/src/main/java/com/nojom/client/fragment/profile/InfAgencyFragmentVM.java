package com.nojom.client.fragment.profile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.databinding.FragmentInfAgencyBinding;
import com.nojom.client.databinding.FragmentInfAllBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.ClientReviews;
import com.nojom.client.ui.projects.InfluencerProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class InfAgencyFragmentVM extends AndroidViewModel {

    FragmentInfAgencyBinding binding;
    BaseFragment fragment;
    private List<ClientReviews.Data> reviewsList;

    InfAgencyFragmentVM(Application application, FragmentInfAgencyBinding reviewsProfileBinding, BaseFragment reviewsProfileFragment) {
        super(application);
        binding = reviewsProfileBinding;
        fragment = reviewsProfileFragment;
        reviewsList = new ArrayList<>();
        initData();
    }

    private void initData() {
        fragment.activity.runOnUiThread(() -> {
            if (((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency != null && ((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.size() > 0) {
                binding.tvAgencyName.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).name);
                binding.tvAgencyContact.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).phone);
                binding.tvAgencyWebsite.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).website);
                binding.tvAgencyEmail.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).email);
                binding.tvAgencyAdd.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).address);
                binding.tvAgencyNote.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).note);
                binding.tvAgencyAbout.setText(((InfluencerProfileActivity) fragment.activity).getAgentData().agent_agency.get(0).about);
            }
        });

    }
}
