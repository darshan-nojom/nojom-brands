package com.nojom.client.ui.projects;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nojom.client.R;
import com.nojom.client.adapter.PlatformDetailAdapter;
import com.nojom.client.databinding.ActivityCampaignDetailsBinding;
import com.nojom.client.model.CampList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

public class CampaignDetailActivity extends BaseActivity {

    private ProjectDetailsActivityVM projectDetailsActivityVM;
    private CampList campList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCampaignDetailsBinding detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_campaign_details);
//        projectDetailsActivityVM = new ProjectDetailsActivityVM(Task24Application.getInstance(), detailsBinding, this);

        if (getIntent() != null) {
            campList = (CampList) getIntent().getSerializableExtra(Constants.PROJECT);
        }

        if (campList != null) {
            detailsBinding.tvJobTitle.setText(campList.campaignTitle);
            detailsBinding.tvProjectBudget.setText(campList.totalPrice + "");
            detailsBinding.tvPaytype.setText(campList.currency + "");
            if (!TextUtils.isEmpty(campList.campaignCreatedAt)) {
                String deadline = campList.campaignCreatedAt.replace("T", " ");
                detailsBinding.tvDeadline.setText(Utils.setDeadLine(deadline, this));
            }

            detailsBinding.tvDetails.setText(campList.campaignBrief + "");
            detailsBinding.tvJobId.setText(campList.campaignId + "");

            PlatformDetailAdapter adapter = new PlatformDetailAdapter(this, campList.socialPlatforms);
            detailsBinding.rvPlatform.setAdapter(adapter);

            Glide.with(this).load(campList.campaignAttachmentUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(detailsBinding.imgCampaign);
        }

        detailsBinding.imgBack.setOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
