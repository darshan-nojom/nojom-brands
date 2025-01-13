package com.nojom.client.ui.projects;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.nojom.client.R;
import com.nojom.client.adapter.NewSelectedServiceAdapter;
import com.nojom.client.adapter.SocialMediaServAdapter;
import com.nojom.client.databinding.ActivityInfServiceBinding;
import com.nojom.client.databinding.ViewServicesBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.Agents;
import com.nojom.client.model.CampListData;
import com.nojom.client.model.InfServices;
import com.nojom.client.model.Serv;
import com.nojom.client.model.SocialPlatformList;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.home.AddMoreStarActivity;
import com.nojom.client.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfluencerServActivity extends BaseActivity implements NewSelectedServiceAdapter.OnClickServiceListener {

    private AgentProfile agentData;
    ActivityInfServiceBinding binding;
    private InfServices influencerServices;
    ArrayList<SocialPlatformList.Data> connectedMediaList;
    private AddStarActivityVM addStarActivityVM;
    private CampListData campListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inf_service);
        initData();
    }

    private void initData() {
        addStarActivityVM = ViewModelProviders.of(this).get(AddStarActivityVM.class);
        addStarActivityVM.init(this);
        addStarActivityVM.getAgentService(1, 5);
        if (getIntent() != null) {
            agentData = (AgentProfile) getIntent().getSerializableExtra(AGENT_PROFILE_DATA);
            influencerServices = (InfServices) getIntent().getSerializableExtra("data");
            connectedMediaList = (ArrayList<SocialPlatformList.Data>) getIntent().getSerializableExtra("social");
        }

        if (agentData != null) {
            setPreview();
            StringBuilder stringBuilder = new StringBuilder();
            if (agentData.firstName != null) {
//                binding.tvName.setTextColor(getColor(R.color.black));
                stringBuilder.append(agentData.firstName);

            }
            if (agentData.lastName != null) {
//                binding.tvName.setTextColor(getColor(R.color.black));
                stringBuilder.append(" ");
                stringBuilder.append(agentData.lastName);
            }
            if (!TextUtils.isEmpty(agentData.about_me)) {
                binding.tvAboutme.setText(agentData.about_me);
                binding.tvAboutme.setVisibility(VISIBLE);
            } else {
                binding.tvAboutme.setVisibility(GONE);
            }
            if (!TextUtils.isEmpty(agentData.mawthooq_id)) {
                binding.txtMawNo.setText(agentData.mawthooq_id);
                binding.txtMawNo.setVisibility(VISIBLE);
            } else {
                binding.txtMawNo.setVisibility(GONE);
            }
            binding.tvName.setText(stringBuilder.toString());
            binding.toolbarTitle.setText(stringBuilder.toString());

            setImage(binding.imgProfile, TextUtils.isEmpty(agentData.profilePic) ? "" : agentData.path + agentData.profilePic, 0, 0);

            if (connectedMediaList != null && connectedMediaList.size() > 0) {
//                Collections.reverse(connectedMediaList);
                SocialMediaServAdapter adapter = new SocialMediaServAdapter();
                adapter.doRefresh(connectedMediaList, this);
                binding.rvMedia.setAdapter(adapter);
            }

            binding.linearCustom.removeAllViews();

            addInfluencerServiceLayout();
        }

        binding.btnContinuePrice.setOnClickListener(view -> {
            if (adapter.calculatePrice() > 0) {
                Intent intent = new Intent(this, CampDataActivity.class);
                intent.putExtra("data", influencerServices);
                intent.putExtra(AGENT_PROFILE_DATA, agentData);
                intent.putExtra("notes", binding.etNotes.getText().toString());
                intent.putExtra("total", adapter.calculatePrice());
//                intent.putExtra("social", connectedMediaList);
                startActivity(intent);
            } else {
                toastMessage(getString(R.string.please_select_one_platform));
            }
        });
        binding.imgBack.setOnClickListener(view -> onBackPressed());

//        AdapterClass adapterClass = new AdapterClass(null);
//        binding.rvProfile.setAdapter(adapterClass);

        binding.txtAddMore.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddMoreStarActivity.class);
//            intent.putExtra("data", campListData);
            intent.putExtra("service", influencerServices);
            intent.putExtra(AGENT_PROFILE_DATA, agentData);
            intent.putExtra("notes", binding.etNotes.getText().toString());
            intent.putExtra("total", adapter.calculatePrice());

            startActivity(intent);
        });

        addStarActivityVM.agentMutableData.observe(this, servData -> {
            campListData = servData;
            if (servData != null && servData.agents != null && servData.agents.size() > 0) {
                addOverlappingImages(binding.imageContainer, servData.agents);
            }
        });

    }

    private void addOverlappingImages(LinearLayout container, List<Agents> imageRes) {
        int overlapOffset = -5; // Adjust the overlap offset in dp
        int size = 40; // Circle size in dp

        // Convert dp to pixels
        float scale = getResources().getDisplayMetrics().density;
        int offsetPx = (int) (overlapOffset * scale);
        int sizePx = (int) (size * scale);

        for (int i = 0; i < imageRes.size(); i++) {
            CircleImageView imageView = new CircleImageView(this);
//            imageView.setImageResource(imageRes.get(i).image.path+imageRes.get(i).image.fileName);
            Glide.with(this).load(imageRes.get(i).image.path + imageRes.get(i).image.fileName)
                    .placeholder(R.drawable.dp).error(R.drawable.dp).into(imageView);
            // Set circular shape
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBorderColor(Color.WHITE);
            imageView.setBorderWidth(3);
            //imageView.setBackgroundResource(R.drawable.circle_round_gray); // Circular shape drawable


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizePx, sizePx);
            if (i != 0) {
                params.leftMargin = -30; // Overlap the images
            }
            imageView.setLayoutParams(params);

            container.addView(imageView);

            if (i == 4) {
                break;
            }
        }

        if (campListData.total > 5) {
            TextView textView = new TextView(this);
            textView.setText("+" + (campListData.total - 5));
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundResource(R.drawable.circle_round_gray);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizePx, sizePx);
            params.leftMargin = -30; // Position after the last image
            textView.setLayoutParams(params);

            container.addView(textView);
        }
    }

    private void setPreview() {
        if (getEmailStatus() == 3 && getWhatsappStatus() == 3 && getAcceptOfferStatus() == 3) {
            msg();
        } else if (getWhatsappStatus() != 3 && getAcceptOfferStatus() != 3 && getEmailStatus() != 3) {
            businessEmailWhatsapp(false);
        } else if (getEmailStatus() != 3 && getAcceptOfferStatus() != 3) {
            msgEmailOffer();
        } else if (getEmailStatus() == 3 && getAcceptOfferStatus() != 3 && getWhatsappStatus() != 3) {
            msgWhatsappOffer(true);
        } else if (getEmailStatus() != 3 && getWhatsappStatus() != 3) {
            businessEmailWhatsapp(true);
        } else if (getEmailStatus() == 3 && getAcceptOfferStatus() == 3) {
            msgWhatsapp(true);
        } else if (getAcceptOfferStatus() != 3) {
            msgOffer();
        }
    }

    private void businessEmailWhatsapp(boolean offerGone) {
        binding.linPreview.removeAllViews();
        binding.txtOffer.setVisibility(offerGone ? View.GONE : View.VISIBLE);
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_textview, binding.linPreview, false);
            TextView txtView = view.findViewById(R.id.txt_view1);
            switch (i) {
                case 0:
                    txtView.setText(getString(R.string.whatsapp));
                    break;
                case 1:
                    txtView.setText(getString(R.string.email));
                    break;
                case 2:
                    txtView.setText(getString(R.string.message));
                    break;
            }
            txtView.setBackground(getResources().getDrawable(R.drawable.gray_button_bg));
            txtView.setTextColor(getResources().getColor(R.color.C_020814));
            binding.linPreview.addView(view);
        }
    }

    private void msgOffer() {
        binding.linPreview.removeAllViews();
        binding.txtOffer.setVisibility(View.GONE);
        for (int i = 0; i < 2; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_textview, binding.linPreview, false);
            TextView txtView = view.findViewById(R.id.txt_view1);
            switch (i) {
                case 0:
                    txtView.setText(getString(R.string.message));
                    txtView.setBackground(getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(getResources().getColor(R.color.C_020814));
                    break;
                case 1:
                    txtView.setText(getString(R.string.send_offer));
                    txtView.setBackground(getResources().getDrawable(R.drawable.light_black_bg_7));
                    DrawableCompat.setTint(txtView.getBackground(), ContextCompat.getColor(this, R.color.black));
                    txtView.setTextColor(getResources().getColor(R.color.white));
                    break;
            }

            binding.linPreview.addView(view);
        }
    }

    private void msg() {
        binding.linPreview.removeAllViews();
        binding.txtOffer.setVisibility(View.GONE);

        View view = LayoutInflater.from(this).inflate(R.layout.item_textview, binding.linPreview, false);
        TextView txtView = view.findViewById(R.id.txt_view1);
        txtView.setText(getString(R.string.message));
        txtView.setBackground(getResources().getDrawable(R.drawable.gray_button_bg));
        txtView.setTextColor(getResources().getColor(R.color.C_020814));
        binding.linPreview.addView(view);
    }


    private void msgWhatsappOffer(boolean offerGone) {
        binding.linPreview.removeAllViews();
        binding.txtOffer.setVisibility(offerGone ? View.GONE : View.VISIBLE);
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_textview, binding.linPreview, false);
            TextView txtView = view.findViewById(R.id.txt_view1);
            switch (i) {
                case 0:
                    txtView.setText(getString(R.string.message));
                    txtView.setBackground(getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(getResources().getColor(R.color.C_020814));
                    break;
                case 1:
                    txtView.setText(getString(R.string.whatsapp));
                    txtView.setBackground(getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(getResources().getColor(R.color.C_020814));
                    break;
                case 2:
                    txtView.setText(getString(R.string.send_offer));
                    txtView.setBackground(getResources().getDrawable(R.drawable.light_black_bg_7));
                    DrawableCompat.setTint(txtView.getBackground(), ContextCompat.getColor(this, R.color.black));
                    txtView.setTextColor(getResources().getColor(R.color.white));
                    break;
            }

            binding.linPreview.addView(view);
        }
    }

    private void msgEmailOffer() {
        binding.linPreview.removeAllViews();
        binding.txtOffer.setVisibility(View.GONE);
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_textview, binding.linPreview, false);
            TextView txtView = view.findViewById(R.id.txt_view1);
            switch (i) {
                case 0:
                    txtView.setText(getString(R.string.message));
                    txtView.setBackground(getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(getResources().getColor(R.color.C_020814));
                    break;
                case 1:
                    txtView.setText(getString(R.string.email));
                    txtView.setBackground(getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(getResources().getColor(R.color.C_020814));
                    break;
                case 2:
                    txtView.setText(getString(R.string.send_offer));
                    txtView.setBackground(getResources().getDrawable(R.drawable.light_black_bg_7));
                    DrawableCompat.setTint(txtView.getBackground(), ContextCompat.getColor(this, R.color.black));
                    txtView.setTextColor(getResources().getColor(R.color.white));
                    break;
            }

            binding.linPreview.addView(view);
        }
    }

    private void msgWhatsapp(boolean offerGone) {
        binding.linPreview.removeAllViews();
        binding.txtOffer.setVisibility(offerGone ? View.GONE : View.VISIBLE);
        for (int i = 0; i < 2; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_textview, binding.linPreview, false);
            TextView txtView = view.findViewById(R.id.txt_view1);
            switch (i) {
                case 0:
                    txtView.setText(getString(R.string.message));
                    txtView.setBackground(getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(getResources().getColor(R.color.C_020814));
                    break;
                case 1:
                    txtView.setText(getString(R.string.whatsapp));
                    txtView.setBackground(getResources().getDrawable(R.drawable.gray_button_bg));
                    txtView.setTextColor(getResources().getColor(R.color.C_020814));
                    break;
            }

            binding.linPreview.addView(view);
        }
    }

    private int getAcceptOfferStatus() {
        return agentData.show_send_offer_button;
    }

    private int getEmailStatus() {
        return agentData.show_email;
    }

    private int getWhatsappStatus() {
        return agentData.show_whatsapp;
    }

    NewSelectedServiceAdapter adapter;

    private void addInfluencerServiceLayout() {
        ViewServicesBinding socialMediaBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_services, null, false);
//        if (getLanguage().equals("ar")) {
//            setArFont(socialMediaBinding.txtName, Constants.FONT_AR_MEDIUM);
//            setArFont(socialMediaBinding.txtShow, Constants.FONT_AR_MEDIUM);
//            setArFont(socialMediaBinding.txtAdd, Constants.FONT_AR_MEDIUM);
//        }

        socialMediaBinding.txtName.setVisibility(GONE);

        if (influencerServices != null && influencerServices.services != null && influencerServices.services.size() > 0) {
            ArrayList<Serv> servicesData = new ArrayList<>(influencerServices.services);

//            if (influencerServices.all_platforms_price != null && influencerServices.all_platforms_price > 0) {
//                servicesData.add(new Serv(getString(R.string.all_social_media), influencerServices.all_platforms_price));
//            }
            Collections.reverse(servicesData);
            adapter = new NewSelectedServiceAdapter(servicesData, this);
            adapter.setOnclickListener(this);
            socialMediaBinding.rvService.setAdapter(adapter);
            socialMediaBinding.rvService.setVisibility(VISIBLE);

//            int[] colorList = {R.color.red};
//            String[] fonts = {Constants.SFTEXT_BOLD};
//            String[] words = {adapter.calculatePercentage() + "%"};

//            binding.txtPerc.setText(Utils.getBoldString(this, String.format(getString(R.string.you_qualify_for_a_s_discount_for_choosing_all_platforms), adapter.calculatePercentage()), fonts, colorList, words));
//            binding.txtPerc.setText(String.format(getString(R.string.you_qualify_for_a_s_discount_for_choosing_all_platforms), adapter.calculatePercentage()));

            if (adapter.calculatePrice() > 0) {
                String formattedNumber = Utils.getDecimalFormat(Utils.formatValue(adapter.calculatePrice()));
                binding.btnContinuePrice.setText(getString(R.string.continue_) + " (" + formattedNumber + " " + getString(R.string.sar) + ")");
            }
        } else {
            socialMediaBinding.rvService.setVisibility(GONE);
        }


        binding.linearCustom.addView(socialMediaBinding.getRoot());
    }

    @Override
    public void onClickService(int pos, Serv serv) {

    }

    @Override
    public void onClickServiceChecked() {
        if (adapter != null) {
            String formattedNumber = Utils.getDecimalFormat(Utils.formatValue(adapter.calculatePrice()));
            binding.btnContinuePrice.setText(getString(R.string.continue_) + " (" + formattedNumber + " " + getString(R.string.sar) + ")");
        }
    }
}
