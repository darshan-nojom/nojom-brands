package com.nojom.client.fragment.projects;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.FragmentProjectRateBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ProjectGigByID;
import com.nojom.client.ui.chat.ChatMessagesActivity;
import com.nojom.client.ui.clientprofile.ClientGigReviewActivity;
import com.nojom.client.ui.projects.ProjectGigDetailsActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

class ProjectGigRateFragmentVM extends AndroidViewModel {
    private FragmentProjectRateBinding binding;
    private BaseFragment fragment;
    private ProjectGigByID projectData;
    private int RC_RATING = 1010;

    ProjectGigRateFragmentVM(Application application, FragmentProjectRateBinding projectRateBinding, BaseFragment projectRateFragment) {
        super(application);
        binding = projectRateBinding;
        fragment = projectRateFragment;
        initData();
    }

    private void initData() {
        if (fragment.activity != null) {
            projectData = ((ProjectGigDetailsActivity) fragment.activity).getProjectData();
        }

        binding.ratingAgent.setScrollable(false);
        binding.ratingUser.setScrollable(false);

        Profile profileData = Preferences.getProfileData(fragment.activity);
        if (profileData != null) {
            binding.tvUsername.setText(fragment.activity.getUserName());
            if (profileData.getCountryName(fragment.activity.getLanguage()) != null)
                binding.tvUserPlace.setText(profileData.getCountryName(fragment.activity.getLanguage()));

            fragment.activity.setImage(binding.imgUser, TextUtils.isEmpty(profileData.profilePic) ? "" : profileData.filePath.pathProfilePicClient + profileData.profilePic, 0, 0);

            if (projectData.clientReview != null) {
                if (projectData.clientReview.rate != null) {
                    binding.ratingUser.setRating((float) projectData.clientReview.rate);
                }
                if (projectData.clientReview.comment != null) {
                    binding.tvUserRate.setText(projectData.clientReview.comment);
                    binding.tvUserRate.setTextColor(ContextCompat.getColor(fragment.activity, R.color.black));
                }
            }
        }

        if (projectData != null) {
            try {
                if (!TextUtils.isEmpty(projectData.agentDetails.lastName) && !projectData.agentDetails.lastName.equals("null")) {
                    binding.tvAgentName.setText(projectData.agentDetails.firstName + " " + projectData.agentDetails.lastName);
                } else {
                    binding.tvAgentName.setText(projectData.agentDetails.firstName);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                binding.tvAgentPlace.setText(projectData.agentDetails.address.getCountry(fragment.activity.getLanguage()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                fragment.activity.setImage(binding.imgAgent, TextUtils.isEmpty(projectData.agentDetails.photo) ? "" : profileData.filePath.pathProfilePicAgent + projectData.agentDetails.photo, 0, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (projectData.agentReview != null) {
                if (projectData.agentReview.rate != null) {
                    binding.ratingAgent.setRating((float) projectData.agentReview.rate);
                }
                if (projectData.agentReview.comment != null) {
                    binding.tvAgentRate.setText(projectData.agentReview.comment);
                    binding.tvAgentRate.setTextColor(ContextCompat.getColor(fragment.activity, R.color.black));
                }
            }

            if (projectData.isAgentReview != null && projectData.isAgentReview == 1) {
                binding.ratingAgent.setIsIndicator(true);
            }
        }

        binding.ratingAgent.setOnRatingChangeListener((baseRatingBar, v) -> {
            if (projectData.isAgentReview != null && projectData.isAgentReview == 0) {
                Intent i = new Intent(fragment.activity, ClientGigReviewActivity.class);
                i.putExtra(Constants.USER_DATA, projectData);
                fragment.startActivityForResult(i, RC_RATING);
            }
        });

        binding.tvAgentChat.setOnClickListener(v -> {
            if (profileData != null && projectData.id != null) {
                HashMap<String, String> chatMap = new HashMap<>();
                chatMap.put(Constants.RECEIVER_ID, projectData.agentProfileID + "");
                if (!TextUtils.isEmpty(projectData.agentDetails.lastName) && !projectData.agentDetails.lastName.equals("null")) {
                    chatMap.put(Constants.RECEIVER_NAME, projectData.agentDetails.firstName + " " + projectData.agentDetails.lastName);
                } else {
                    chatMap.put(Constants.RECEIVER_NAME, projectData.agentDetails.firstName);
                }

                chatMap.put(Constants.RECEIVER_PIC, profileData.filePath.pathProfilePicAgent + projectData.agentDetails.photo);
                chatMap.put(Constants.SENDER_ID, profileData.id + "");
                chatMap.put(Constants.SENDER_NAME, profileData.username);
                chatMap.put(Constants.SENDER_PIC, profileData.filePath.pathProfilePicClient + profileData.profilePic);
                chatMap.put(Constants.PROJECT_ID, String.valueOf(projectData.id));
                chatMap.put("isProject", "1");//1 mean updated record
                chatMap.put("projectType", "1");//2=job & 1= gig

                Intent i = new Intent(fragment.activity, ChatMessagesActivity.class);
                i.putExtra(Constants.CHAT_ID, profileData.id + "-" + projectData.agentProfileID);  // ClientId - AgentId
                i.putExtra(Constants.CHAT_DATA, chatMap);
                if (fragment.activity.getIsVerified() == 1) {
                    fragment.activity.startActivity(i);
                } else {
                    fragment.activity.toastMessage(fragment.getString(R.string.verification_is_pending_please_complete_the_verification_first_before_chatting_with_them));
                }
            }
        });

        binding.ratingUser.setClickable(false);

    }

    void refreshPage() {
        initData();
    }


    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_RATING) {
                ((ProjectGigDetailsActivity) fragment.activity).getProjectGigById(true);
            }
        }
    }
}
