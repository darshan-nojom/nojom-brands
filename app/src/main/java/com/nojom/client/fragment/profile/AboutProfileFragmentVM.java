package com.nojom.client.fragment.profile;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nojom.client.R;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.databinding.FragmentAboutProfileBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.Skill;
import com.nojom.client.ui.clientprofile.FreelancerProfileActivity;
import com.nojom.client.util.EqualSpacingItemDecoration;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.Locale;

public class AboutProfileFragmentVM extends AndroidViewModel implements RecyclerviewAdapter.OnViewBindListner {
    private FragmentAboutProfileBinding binding;
    private BaseFragment fragment;
    private AgentProfile profileData;
    private ArrayList<Skill> languageList;

    AboutProfileFragmentVM(Application application, FragmentAboutProfileBinding aboutProfileBinding, BaseFragment aboutProfileFragment) {
        super(application);
        binding = aboutProfileBinding;
        fragment = aboutProfileFragment;
        initData();
    }

    private void initData() {

        try {
            profileData = ((FreelancerProfileActivity) fragment.activity).getAgentData();
            if (profileData != null) {
                if (profileData.profilePublicity != null) {
                    if (profileData.profilePublicity.address == 1) {//visible to user
                        binding.tvLocationTitle.setVisibility(View.VISIBLE);
                        binding.tvLocation.setVisibility(View.VISIBLE);
                        StringBuilder sbAdd = new StringBuilder();
                        if (!TextUtils.isEmpty(profileData.cityName)) {
                            sbAdd.append(profileData.cityName);
                            sbAdd.append(",");
                        }
                        if (!TextUtils.isEmpty(profileData.stateName)) {
                            sbAdd.append(profileData.stateName);
                            sbAdd.append(",");
                        }
                        if (!TextUtils.isEmpty(profileData.countryName)) {
                            sbAdd.append(profileData.countryName);
                        }

                        if (TextUtils.isEmpty(sbAdd.toString())) {
                            binding.tvLocation.setText("-");
                        } else {
                            binding.tvLocation.setText(String.format(Locale.US,"%s", sbAdd));
                        }
                    }
                }

                binding.txtAllService.setText("#" + profileData.generalRank + " "+fragment.activity.getString(R.string.in));
                binding.txtWritingService.setText("#" + profileData.categoryRank + " "+fragment.activity.getString(R.string.in));
                for (int i = 0; i < profileData.agentProfilePublicity.size(); i++) {
                    if (profileData.agentProfilePublicity.get(i).publicityType.equalsIgnoreCase("pro_address") && profileData.agentProfilePublicity.get(i).status.equalsIgnoreCase("1")) {//visible to user
                        binding.tvProfAddTitle.setVisibility(View.VISIBLE);
                        binding.tvProfAdd.setVisibility(View.VISIBLE);
                        binding.tvProfAdd.setText(profileData.proAddress != null ? profileData.proAddress : "-");
                    }

                    if (profileData.agentProfilePublicity.get(i).publicityType.equalsIgnoreCase("email") && profileData.agentProfilePublicity.get(i).status.equalsIgnoreCase("1")) {
                        binding.tvEmailTitle.setVisibility(View.VISIBLE);
                        binding.tvEmail.setVisibility(View.VISIBLE);
                        binding.tvEmail.setText(profileData.email != null ? profileData.email : "-");
                    }
                    if (profileData.agentProfilePublicity.get(i).publicityType.equalsIgnoreCase("phone") && profileData.agentProfilePublicity.get(i).status.equalsIgnoreCase("1")) {
                        binding.tvPhoneTitle.setVisibility(View.VISIBLE);
                        binding.tvPhone.setVisibility(View.VISIBLE);
                        binding.tvPhone.setText(profileData.contactNo != null ? profileData.contactNo : "-");
                    }
                    if (profileData.agentProfilePublicity.get(i).publicityType.equalsIgnoreCase("website") && profileData.agentProfilePublicity.get(i).status.equalsIgnoreCase("1")) {
                        binding.tvWebsiteTitle.setVisibility(View.VISIBLE);
                        binding.tvWebsite.setVisibility(View.VISIBLE);
                        binding.tvWebsite.setText(profileData.websites != null ? profileData.websites : "-");
                    }
                }

                binding.tvHeadlineTitle.setVisibility(View.VISIBLE);
                binding.tvHeadline.setVisibility(View.VISIBLE);
                binding.tvHeadline.setText(profileData.headlines != null ? profileData.headlines : "-");
                binding.tvAboutMeTitle.setVisibility(View.VISIBLE);
                binding.tvAboutMe.setVisibility(View.VISIBLE);
                binding.tvAboutMe.setText(profileData.summaries != null ? profileData.summaries : "-");
            }

            binding.rvLanguages.setLayoutManager(new LinearLayoutManager(fragment.activity));
            binding.rvLanguages.addItemDecoration(new EqualSpacingItemDecoration(20, EqualSpacingItemDecoration.VERTICAL));

            getLanguageData();

            RecyclerviewAdapter adapter = new RecyclerviewAdapter(languageList, R.layout.item_language_agents, this);
            binding.rvLanguages.setAdapter(adapter);
            binding.rvLanguages.setFocusable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLanguageData() {
        languageList = new ArrayList<>();
        if (profileData != null && profileData.language != null) {
            for (AgentProfile.Language data : profileData.language) {
                languageList.add(new Skill(data.name,
                        Utils.getLanguageLevel(data.level)));
            }
        }
    }

    @Override
    public void bindView(View view, int position) {
        TextView tvLanguage = view.findViewById(R.id.tv_language);
        TextView tvLevel = view.findViewById(R.id.tv_level);

        Skill item = languageList.get(position);
        tvLanguage.setText(String.format(Locale.US,"%s:", item.skillTitle));
        tvLevel.setText(item.skillValue);
    }
}
