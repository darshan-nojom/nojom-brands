package com.nojom.client.ui.projects;

import static com.nojom.client.multitypepicker.activity.VideoPickActivity.IS_NEED_CAMERA;
import static com.nojom.client.util.Constants.AGENT_PROFILE_DATA;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.databinding.ActivityCampDataBinding;
import com.nojom.client.model.AgentProfile;
import com.nojom.client.model.AgentService;
import com.nojom.client.model.Agents;
import com.nojom.client.model.Attachment;
import com.nojom.client.model.Campaign;
import com.nojom.client.model.CampaignPay;
import com.nojom.client.model.InfServices;
import com.nojom.client.model.Serv;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.multitypepicker.filter.entity.NormalFile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Utils;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CampDataActivity extends BaseActivity {

    private AgentProfile agentData;
    ActivityCampDataBinding binding;
    private InfServices influencerServices;
    //    ArrayList<SocialPlatformList.Data> connectedMediaList;
    private CampaignDataActivityVM campaignDataActivityVM;
    private List<Agents> agentList;
    private String notes;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camp_data);
        campaignDataActivityVM = new CampaignDataActivityVM(Task24Application.getInstance(), this);
        initData();
    }

    private void initData() {
        if (getIntent() != null) {
            agentData = (AgentProfile) getIntent().getSerializableExtra(AGENT_PROFILE_DATA);

            if (getIntent().hasExtra("data")) {
                influencerServices = (InfServices) getIntent().getSerializableExtra("data");
            }
            if (getIntent().hasExtra("selData")) {
                agentList = (List<Agents>) getIntent().getSerializableExtra("selData");
            }
            notes = getIntent().getStringExtra("notes");
            total = getIntent().getDoubleExtra("total", 0.0);
//            connectedMediaList = (ArrayList<SocialPlatformList.Data>) getIntent().getSerializableExtra("social");
        }

        binding.imgBack.setOnClickListener(view -> onBackPressed());

        if (agentData != null) {
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
            binding.tvName.setText(stringBuilder.toString());
//            binding.toolbarTitle.setText(stringBuilder.toString());

            setImage(binding.imgProfile, TextUtils.isEmpty(agentData.profilePic) ? "" : agentData.path + agentData.profilePic, 0, 0);

//            if (connectedMediaList != null && connectedMediaList.size() > 0) {
//                SocialMediaServAdapter adapter = new SocialMediaServAdapter();
//                adapter.doRefresh(connectedMediaList, this);
//                binding.rvMedia.setAdapter(adapter);
//            }
        }

        binding.etName.addTextChangedListener(watcher());
        binding.etBrief.addTextChangedListener(watcher());
        binding.etCamp.addTextChangedListener(watcher());

        binding.tvAttachFile.setOnClickListener(view -> {
            if (checkStoragePermission()) {
                checkPermission(false);
            } else {
                new StorageDisclosureDialog(this, () -> checkPermission(false));
            }
        });

        binding.btnContinuePrice.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.etCamp.getText().toString().trim())) {
                toastMessage(getString(R.string.campaign_title));
                return;
            }
            if (TextUtils.isEmpty(binding.etName.getText().toString().trim())) {
                toastMessage(getString(R.string.select_date));
                return;
            }
            if (TextUtils.isEmpty(binding.etBrief.getText().toString().trim())) {
                toastMessage(getString(R.string.brief));
                return;
            }
//            if (fileList != null && fileList.size() > 0) {
//                campaignDataActivityVM.uploadAttachment(fileList.get(0).filepath);
//            } else {
//                createCampaign(null);
//            }

            Intent intent = new Intent(this, PaymentMethodActivity.class);
            intent.putExtra("files", fileList);
            intent.putExtra("data", influencerServices);
            intent.putExtra(Constants.AGENT_PROFILE_DATA, agentData);
            intent.putExtra("selData", (Serializable) agentList);
            intent.putExtra("title", binding.etCamp.getText().toString().trim());
            intent.putExtra("date", binding.etName.getText().toString().trim());
            intent.putExtra("time", binding.etTime.getText().toString().trim());
            intent.putExtra("brief", binding.etBrief.getText().toString().trim());
            intent.putExtra("total", total);
            startActivity(intent);

        });

        binding.etName.setOnClickListener(view -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {

//                int mHour = c.get(Calendar.HOUR_OF_DAY);
//                int mMinute = c.get(Calendar.MINUTE);
//                String _hour = mHour < 10 ? "0" + mHour : mHour + "";
//                String _minute = mMinute < 10 ? "0" + mMinute : mMinute + "";

                String _month = (month + 1) < 10 ? "0" + (month + 1) : String.valueOf(month + 1);
                String _date = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                String _pickedDate = year + "-" + _month + "-" + _date;

                binding.etName.setText(_pickedDate);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dialog.show();
        });

        binding.etTime.setOnClickListener(view -> {
            showTimePickerDialog();
        });

        //create campaign
        campaignDataActivityVM.mutableUploadedFileUrl.observe(this, this::createCampaign);
        campaignDataActivityVM.mutableCampId.observe(this, campId -> {
            CampaignPay campaignPay = new CampaignPay(calculatePrice(), "sar");
            campaignDataActivityVM.createCampaignPayment(campaignPay, campId);
        });


        campaignDataActivityVM.mutableProgress.observe(this, aBoolean -> {
            if (aBoolean) {
                binding.btnContinuePrice.setVisibility(View.INVISIBLE);
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.btnContinuePrice.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });

        binding.imgDelete.setOnClickListener(view -> {
            binding.linFile.setVisibility(View.GONE);
            binding.tvAttachFile.setVisibility(View.VISIBLE);
            binding.tvAttachFileDesc.setVisibility(View.VISIBLE);
            fileList = new ArrayList<>();
        });
    }

    private void showTimePickerDialog() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Create a TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(CampDataActivity.this, (view, hourOfDay, minute) -> {
            // Format and display the selected time
            String formattedTime = String.format("%02d:%02d:00", hourOfDay, minute);
            binding.etTime.setText(formattedTime);
        }, currentHour, currentMinute, true // Use 24-hour format
        );

        // Show the dialog
        timePickerDialog.show();
    }

    private void createCampaign(String attachUrl) {
        List<Campaign.Stars> campStars = new ArrayList<>();
        if (influencerServices != null) {//when coming direct from detail screen (without open add more star screen)
            List<Campaign.Service> campServ = new ArrayList<>();
            for (Serv infS : influencerServices.services) {
                if (infS.isChecked && infS.id != -1) {//if all platform not checked that time pass specific services
                    campServ.add(new Campaign.Service(infS.service_id));
                } else if (infS.isChecked) {//if all platform checked then pass only 1 Ids
                    campServ.clear();
                    campServ.add(new Campaign.Service(infS.service_id));
                    break;
                }
            }
            if (campServ.size() > 0) {
                campStars.add(new Campaign.Stars(agentData.id, campServ, notes));
            }
        } else {//when coming from add more star screen
            for (Agents infS : agentList) {
                List<Campaign.Service> campServ = new ArrayList<>();
                for (AgentService serv : infS.services) {
                    if (serv.isChecked && serv.socialPlatformId != -1) {//if all platform not checked that time pass specific services
                        campServ.add(new Campaign.Service(serv.serviceId));
                    } else if (serv.isChecked) {//if all platform checked then pass only 1 Ids
                        campServ.clear();
                        campServ.add(new Campaign.Service(serv.serviceId));
                        break;
                    }
                }
                if (campServ.size() > 0) {
                    campStars.add(new Campaign.Stars(infS.id, campServ, infS.notes));
                }
            }
        }
        Campaign campaign = new Campaign(binding.etCamp.getText().toString(), binding.etName.getText().toString() + " " + binding.etTime.getText().toString(), binding.etBrief.getText().toString(), attachUrl, campStars/*Arrays.asList(service1, service2, service3)*/, false);

        campaignDataActivityVM.createCampaign(campaign);
    }

    private TextWatcher watcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isValid()) {
                    binding.relBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    binding.btnContinuePrice.setTextColor(getResources().getColor(R.color.white));
                } else {
                    binding.relBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.C_E5E5EA)));
                    binding.btnContinuePrice.setTextColor(getResources().getColor(R.color.C_8E8E93));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    private boolean isValid() {
        if (TextUtils.isEmpty(binding.etName.getText().toString().trim())) {
            return false;
        }
        if (TextUtils.isEmpty(binding.etCamp.getText().toString().trim())) {
            return false;
        }
        return !TextUtils.isEmpty(binding.etBrief.getText().toString().trim());
    }

    public void checkPermission(final boolean isDocument) {
        try {
            Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        if (isDocument) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                openDocuments(CampDataActivity.this, 1);
                            } else {
                                Intent intent = new Intent(CampDataActivity.this, NormalFilePickActivity.class);
                                intent.putExtra(Constant.MAX_NUMBER, 1);
                                intent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "ppt", "pptx", "pdf"});
                                startActivityForResult(intent, Constant.REQUEST_CODE_PICK_FILE);
                            }
                        } else {
                            Intent intent = new Intent(CampDataActivity.this, ImagePickActivity.class);
                            intent.putExtra(IS_NEED_CAMERA, true);
                            intent.putExtra(Constant.MAX_NUMBER, 1);
                            intent.putExtra("rCode", Constant.REQUEST_CODE_PICK_IMAGE);
                            startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
                        }
                    }

                    if (report.isAnyPermissionPermanentlyDenied()) {
                        toastMessage(getString(R.string.please_give_permission));
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).onSameThread().check();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Attachment> fileList = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_FILE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<NormalFile> docPaths = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    if (docPaths != null && docPaths.size() > 0) {
                        for (NormalFile file : docPaths) {
                            fileList.add(new Attachment(file.getPath(), "", "", false));
                        }
                        setFileAdapter(fileList);
                    } else {
                        toastMessage(getString(R.string.please_select_file));
                    }
                }
                break;
            case 4545://doc picker for android 10+
                String path = null;
                try {
                    if (data != null && data.getData() != null) {
                        path = Utils.getFilePath(this, data.getData());
                        if (path != null) {
                            fileList.add(new Attachment(path, "", "", false));
                            setFileAdapter(fileList);
                        } else {
                            toastMessage(getString(R.string.please_select_file));
                        }
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    if (imgPath != null && imgPath.size() > 0) {
                        for (ImageFile file : imgPath) {
                            fileList.add(new Attachment(file.getPath(), "", "", true));
                        }
                        setFileAdapter(fileList);
                    } else {
                        toastMessage(getString(R.string.please_select_file));
                    }
                }
                break;
        }
    }

    private void setFileAdapter(ArrayList<Attachment> fileList) {

        if (fileList != null && fileList.size() > 0) {
            binding.linFile.setVisibility(View.VISIBLE);
            binding.tvAttachFile.setVisibility(View.GONE);
            binding.tvAttachFileDesc.setVisibility(View.GONE);
            setImage(binding.imgFile, fileList.get(0).filepath, 0, 0);
            String filename = fileList.get(0).filepath.substring(fileList.get(0).filepath.lastIndexOf("/") + 1);
            binding.fileName.setText(filename + "");
        } else {
            fileList = new ArrayList<>();
            binding.linFile.setVisibility(View.GONE);
            binding.tvAttachFile.setVisibility(View.VISIBLE);
            binding.tvAttachFileDesc.setVisibility(View.VISIBLE);
        }
    }

    public double calculatePrice() {
        double finalPrice = 0;
        if (influencerServices != null) {//when coming direct from detail screen (without open add more star screen)
            for (Serv data : influencerServices.services) {
                if (data.price > 0 && data.isChecked && data.id == -1) {
                    finalPrice = data.price;
                    break;
                } else if (data.price > 0 && data.isChecked) {
                    finalPrice = finalPrice + data.price;
                }
            }
        } else {//when coming from add more star screen
            double subTotalPrice = 0;
            for (Agents agents : agentList) {
                if (agents.getPrice(agents.services) > 0) {
                    subTotalPrice = subTotalPrice + agents.getPrice(agents.services);
                }
            }
            finalPrice = subTotalPrice;
        }

        if (finalPrice == 0) {
            return 0;
        }

//        List<WalletData> ratesData = Preferences.getRates(this);
//        double agencyFeeRate = 0;
//        double servTaxFeeRate = 0;
//        for (WalletData data : ratesData) {
//            if (Objects.equals(data.rate_type, "tax") && data.is_active == 1) {
//                servTaxFeeRate = data.rate_value * 100;
//            } else if (Objects.equals(data.rate_type, "agency_fee") && data.is_active == 1) {
//                agencyFeeRate = data.rate_value * 100;
//            }
//        }
//
//        return finalPrice + ((finalPrice * agencyFeeRate) / 100);//5% service tax
        return finalPrice;

    }
}
