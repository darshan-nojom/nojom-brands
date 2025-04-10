package com.nojom.client.fragment.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.nojom.client.R;
import com.nojom.client.adapter.CampaignStarAdapter;
import com.nojom.client.databinding.FragmentCampStarsBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.CampList;
import com.nojom.client.model.Profile;
import com.nojom.client.ui.projects.CampaignDetailActivity2;
import com.nojom.client.ui.projects.CampaignStarActivity;
import com.nojom.client.util.Constants;

import org.jetbrains.annotations.NotNull;
import org.ocpsoft.prettytime.PrettyTime;

public class CampStarsFragment extends BaseFragment implements CampaignStarAdapter.OnClickStarListener {
    private FragmentCampStarsBinding binding;
    private CampList campList;
    private final PrettyTime p = new PrettyTime();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camp_stars, container, false);
//        campList = ((CampaignDetailActivity2) activity).campList;
//        renderView();
        return binding.getRoot();
    }

    private void renderView() {
        if (campList.profiles != null && campList.profiles.size() > 0) {
            CampaignStarAdapter adapter = new CampaignStarAdapter(activity, campList.profiles, this);
            binding.rvStars.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        campList = ((CampaignDetailActivity2) activity).campList;
        renderView();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClickStar(int pos, Profile profile) {
        Intent intent = new Intent(activity, CampaignStarActivity.class);
        intent.putExtra(Constants.PROJECT, campList);
        intent.putExtra("profile", profile);
        activity.startActivity(intent);

    }

    @Override
    public void onClickChat(int pos, Profile profile) {

    }
}
