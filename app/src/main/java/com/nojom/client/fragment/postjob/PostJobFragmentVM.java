package com.nojom.client.fragment.postjob;

import static android.app.Activity.RESULT_OK;
import static com.nojom.client.multitypepicker.activity.VideoPickActivity.IS_NEED_CAMERA;
import static com.nojom.client.util.Constants.API_ADD_JOB_POST;
import static com.nojom.client.util.Constants.API_EDIT_JOB_POST;
import static com.nojom.client.util.Constants.API_GET_CLIENT_PROFILE;
import static com.nojom.client.util.Constants.API_JOB_DETAILS;
import static com.nojom.client.util.Constants.API_SEND_EMAIL_VERIFICATION;
import static com.nojom.client.util.Constants.SYS_ID;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.button.CustomButton;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.edittext.CustomEditText;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.adapter.RecyclerviewAdapter;
import com.nojom.client.adapter.UploadFileAdapter;
import com.nojom.client.api.ApiRequest;
import com.nojom.client.api.RequestResponseListener;
import com.nojom.client.databinding.FragmentPostJobBinding;
import com.nojom.client.fragment.BaseFragment;
import com.nojom.client.model.Attachment;
import com.nojom.client.model.ExpertLawyers;
import com.nojom.client.model.Profile;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.model.SearchTagModel;
import com.nojom.client.model.ServicesModel;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.multitypepicker.filter.entity.NormalFile;
import com.nojom.client.segment.SegmentedButtonGroup;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.clientprofile.AutoPlaceLocationActivity;
import com.nojom.client.ui.clientprofile.PostJobActivity;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.ui.home.FindExpertActivity;
import com.nojom.client.ui.projects.SearchTagActivity;
import com.nojom.client.util.CompressFile;
import com.nojom.client.util.Constants;
import com.nojom.client.util.LocationAddress;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

class PostJobFragmentVM extends AndroidViewModel implements RecyclerviewAdapter.OnViewBindListner, UploadFileAdapter.OnFileDeleteListener/*, SelectFreelancerActivity.onSomeEventListener */, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, RequestResponseListener {
    private final static int PLACE_PICKER_REQUEST = 999;
    private final FragmentPostJobBinding binding;
    private final BaseFragment fragment;
    private boolean isEdit = false, isRepostJob;
    private boolean isDuplicateJob = false;
    private boolean isFixedPrice;
    private ArrayList<String> skillsList;
    private ArrayList<String> skillsIdList;
    private RecyclerviewAdapter mAdapter;
    private ArrayList<Attachment> fileList;
    private List<ExpertLawyers.Data> expertUserList;
    private String skillIds;
    private String skillNames;
    private String clientRate;
    private int clientRateId;
    private String budget;
    private String describe, moServiceID = "";
    private String attachFile;
    private String attachLocalFile;
    private String attachFileIds;
    private UploadFileAdapter uploadFileAdapter;
    private int projectId;
    private boolean isRefreshApi = true;
    private boolean isRefresh = true;
    private ProjectByID projectData;
    private Timer timer;
    private String deadline = "", payType, lawyerService;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private double latitude;
    private double longitude;
    private boolean isVerifyEmail = false, isFrom1TimeVerifyMail = false;
    private CircularProgressBar progressBarEmail;
    private CustomButton btnSendEmail;
    private Dialog dialogEmail;
    private ArrayList<SearchTagModel> searchTags;
    private ArrayList<ServicesModel.Data> servicesList;
    private StringBuilder sbOtherSearchTags = new StringBuilder();
    private StringBuilder sbJobSearchTags = new StringBuilder();

    PostJobFragmentVM(Application application, FragmentPostJobBinding postJobBinding, BaseFragment postJobFragment) {
        super(application);
        binding = postJobBinding;
        fragment = postJobFragment;
        initData();
    }

    private void initData() {
        mGoogleApiClient = new GoogleApiClient.Builder(fragment.activity).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        binding.imgBack.setOnClickListener(this);
        binding.tvPostJob.setOnClickListener(this);
        binding.tvAttachFile.setOnClickListener(this);
        binding.rlBudget.setOnClickListener(this);
        binding.rlDeveloper.setOnClickListener(this);
        binding.tvAddSkill.setOnClickListener(this);
        binding.tvCancel.setOnClickListener(this);
        binding.tvSave.setOnClickListener(this);
        binding.rlFreelancer.setOnClickListener(this);
        binding.rlDeadline.setOnClickListener(this);
        binding.rlLocation.setOnClickListener(this);
        binding.etLocation.setOnClickListener(this);
        binding.imgLocation.setOnClickListener(this);
        binding.rlLawCat.setOnClickListener(this);
        binding.rlSearchTag.setOnClickListener(this);
        Utils.trackAppsFlayerEvent(fragment.activity, "Post_Project_Screen");

        if (fragment.getArguments() != null) {
            isDuplicateJob = fragment.getArguments().getBoolean(Constants.DUPLICATE_PROJECT);
            isEdit = fragment.getArguments().getBoolean(Constants.IS_EDIT);
            isRepostJob = fragment.getArguments().getBoolean(Constants.REPOST_PROJECT, false);
            projectId = fragment.getArguments().getInt(Constants.PROJECT_ID);

            projectData = (ProjectByID) fragment.getArguments().getSerializable(Constants.PROJECT);//in case of edit job

            if (projectId == 0 && projectData != null) {
                projectId = projectData.id;
            }

            moServiceID = fragment.getArguments().getString(Constants.PLATFORM_ID);
            skillIds = fragment.getArguments().getString(Constants.SKILL_IDS);
            skillNames = fragment.getArguments().getString(Constants.SKILL_NAMES);
            clientRateId = fragment.getArguments().getInt(Constants.CLIENT_RATE_ID);
            clientRate = fragment.getArguments().getString(Constants.CLIENT_RATE);
            budget = fragment.getArguments().getString(Constants.BUDGET);
            describe = fragment.getArguments().getString(Constants.DESCRIBE);
            attachLocalFile = fragment.getArguments().getString(Constants.ATTACH_LOCAL_FILE);
            deadline = fragment.getArguments().getString("deadline");

            payType = fragment.getArguments().getString(Constants.PAY_TYPE);
            lawyerService = fragment.getArguments().getString(Constants.PLATFORM_NAME);

            if (TextUtils.isEmpty(moServiceID) && projectData != null) {
                moServiceID = String.valueOf(projectData.socialPlatformID);
            }
        }

        fileList = new ArrayList<>();

        binding.linTabs.setVisibility(View.GONE);
        binding.tvLblProductType.setVisibility(View.GONE);
        binding.rlDeadline.setVisibility(View.VISIBLE);
        binding.tvDeadlineTitle.setVisibility(View.VISIBLE);
        binding.tvFreelancer.setVisibility(View.VISIBLE);
        binding.rlFreelancer.setVisibility(View.VISIBLE);


        binding.etDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    binding.etDescribe.setGravity(Gravity.TOP);
                    binding.etDescribe.setHint(fragment.getResources().getString(R.string.describe_hint_text));
                } else {
                    binding.etDescribe.setHint(null);
                }
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        describe = s.toString();
                    }
                }, 300);
            }
        });

        binding.rvFiles.setLayoutManager(new LinearLayoutManager(fragment.activity));

        if (isEdit) {
            binding.llBottom.setVisibility(View.VISIBLE);
            binding.rlHeader.setVisibility(View.GONE);
        } else if (isDuplicateJob) {
            binding.llBottom.setVisibility(View.VISIBLE);
            binding.rlHeader.setVisibility(View.GONE);
            binding.tvSave.setText(fragment.getString(R.string.post_new_job));
            binding.relBottom.setBackground(ContextCompat.getDrawable(fragment.activity, R.drawable.green_button_bg));
            binding.tvSave.setTextColor(ContextCompat.getColor(fragment.activity, R.color.white));
        } else if (isRepostJob) {
            binding.llBottom.setVisibility(View.GONE);
            binding.rlHeader.setVisibility(View.VISIBLE);
        }
        locationUI();

        updateTabUI(payType);

        binding.segmentedGroupTab.setOnPositionChangedListener((SegmentedButtonGroup.OnPositionChangedListener) position -> {
            if (position == 0) {
                setTab(0);
                Preferences.writeBoolean(fragment.activity, Constants.IS_FIXED_PRICE, true);
                showHideBudget(View.VISIBLE);
            } else if (position == 2) {
                setTab(1);
                showHideBudget(View.GONE);
                if (projectData != null && projectData.clientRateId != null) {
                    projectData.clientRateId = -1;
                }
            }
        });
    }

    private void locationUI() {

        binding.rlLocation.setVisibility(View.GONE);
        binding.txtLocTitle.setVisibility(View.GONE);
    }

    private void updateTabUI(String payType) {

        binding.linTabs.setVisibility(View.GONE);
        binding.tvLblProductType.setVisibility(View.GONE);
        binding.rlDeadline.setVisibility(View.VISIBLE);
        binding.tvDeadlineTitle.setVisibility(View.VISIBLE);
        binding.tvFreelancer.setVisibility(View.VISIBLE);
        binding.rlFreelancer.setVisibility(View.VISIBLE);


        binding.rlLawCat.setVisibility(View.GONE);
        binding.tvLawCatTitle.setVisibility(View.GONE);
        binding.viewLawCat.setVisibility(View.GONE);
        if (isEdit || isDuplicateJob && moServiceID.equalsIgnoreCase("16")) {
            binding.rlLawCat.setVisibility(View.GONE);
            binding.tvLawCatTitle.setVisibility(View.GONE);
        } else {
            binding.rlLawCat.setVisibility(View.VISIBLE);
            binding.tvLawCatTitle.setVisibility(View.VISIBLE);
        }
        binding.viewLawCat.setVisibility(View.VISIBLE);
        binding.tvSelLawCat.setText(lawyerService);

        if (!TextUtils.isEmpty(payType)) {
            if (payType.equalsIgnoreCase("FREE")) {
                setTab(1);
                binding.tvRates.setText(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.sar) + " " + fragment.activity.getString(R.string.enter_rate) : "$ " + fragment.activity.getString(R.string.enter_rate));
                showHideBudget(View.GONE);
            } else {
                setTab(0);
            }
        }
    }

    private void showHideBudget(int gone) {
        binding.txtLblBudget.setVisibility(gone);
        binding.rlBudget.setVisibility(gone);
        binding.viewBudget.setVisibility(gone);
    }

    private void setTab(int pos) {
        if (pos == 0) {
            binding.segmentedGroupTab.setPosition(0, true);
        } else if (pos == 1) {
            binding.segmentedGroupTab.setPosition(1, true);
        }
    }

    void onResumeMethod() {
        ((PostJobActivity) fragment.activity).hideProgressView();
        if (isEdit || isDuplicateJob || isRepostJob) {
            if (projectData != null) {//in case of edit job post
                updateUI();
            }
        } else if (isRefresh) {
            setUi();
        }
    }

    private void getProjectById() {
        if (!fragment.activity.isNetworkConnected()) {
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("job_id", projectId + "");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_JOB_DETAILS, true, map);
    }

    private void setUi() {
        try {
            if (projectData != null && projectData.deadline != null && !TextUtils.isEmpty(projectData.deadline)) {
                if (!TextUtils.isEmpty(projectData.deadline)) {
                    deadline = projectData.deadline.replace("T", " ");
                    binding.tvDeadline.setText(Utils.setDeadLine(deadline, fragment.activity));
                } else {
                    binding.tvDeadline.setText(fragment.getString(R.string.select_deadline));
                }
            } else {
                binding.tvDeadline.setText(TextUtils.isEmpty(deadline) ? "" : Utils.setDeadLine(deadline, fragment.activity));
            }

            isFixedPrice = Preferences.readBoolean(fragment.activity, Constants.IS_FIXED_PRICE, false);
            if (isEdit || isDuplicateJob || isRepostJob) {
                searchTags = new ArrayList<>();
                if (projectData.jobSearchTags != null && projectData.jobSearchTags.size() > 0) {
                    searchTags = projectData.jobSearchTags;
                    StringBuilder sb = new StringBuilder();
                    sbJobSearchTags = new StringBuilder();
                    sbOtherSearchTags = new StringBuilder();
                    for (SearchTagModel searchTagModel : projectData.jobSearchTags) {
                        sb.append(searchTagModel.tagName);
                        sb.append(",");

                        if (searchTagModel.serviceCategoryID != 0) {
                            sbJobSearchTags.append(searchTagModel.serviceCategoryID);
                            sbJobSearchTags.append(",");
                        } else {
                            sbOtherSearchTags.append(searchTagModel.tagName);
                            sbOtherSearchTags.append(",");
                        }
                    }

                    if (sb.toString().length() > 1) {
                        binding.tvSearchTag.setText(sb.deleteCharAt(sb.length() - 1).toString());
                    } else {
                        binding.tvSearchTag.setText(sb.toString());
                    }

                }
            }

            binding.etDescribe.setText(describe);
            if (TextUtils.isEmpty(clientRate)) {
                if (TextUtils.isEmpty(budget) || budget == null || budget.equals("null")) {
                    binding.tvRates.setText(fragment.activity.getCurrency().equals("SAR") ? fragment.getString(R.string.sar) + " " + fragment.activity.getString(R.string.enter_rate) : "$ " + fragment.activity.getString(R.string.enter_rate));
                } else {
                    binding.tvRates.setText(isFixedPrice ? (fragment.activity.getCurrency().equals("SAR") ? budget + " " + fragment.getString(R.string.sar) : "$" + budget) : budget + "/hr");
                }
            } else {
                binding.tvRates.setText(isFixedPrice ? clientRate : clientRate + "/hr");
            }

            binding.tvDeveloper.setText(skillNames);
            expertUserList = Preferences.getExpertUsers(fragment.activity);

            if (expertUserList != null && expertUserList.size() > 0) {
                String expertUsers = null;
                for (ExpertLawyers.Data expertUser : expertUserList) {
                    if (TextUtils.isEmpty(expertUsers)) {
                        expertUsers = expertUser.username;
                    } else {
                        expertUsers = expertUser.username + ", " + expertUsers;
                    }
                }
                binding.tvFreelancer.setText(expertUsers);
            } else {
                binding.tvFreelancer.setText(fragment.getString(R.string.all_influencers));
            }

            fileList = new ArrayList<>();
            if (!TextUtils.isEmpty(attachLocalFile)) {
                String[] filesSplit = attachLocalFile.split(",");
                for (String aFilesSplit : filesSplit) {
                    if (aFilesSplit.contains("png") || aFilesSplit.contains("jpg") || aFilesSplit.contains("jpeg")) {
                        fileList.add(new Attachment(aFilesSplit, "", "", true));
                    } else {
                        fileList.add(new Attachment(aFilesSplit, "", "", false));
                    }
                }
                setFileAdapter(fileList);
            }

            if (!TextUtils.isEmpty(attachFile)) {
                String[] filesSplit = attachFile.split(",");
                String[] fileIds = attachFileIds.split(",");
                for (int i = 0; i < filesSplit.length; i++) {
                    if (filesSplit[i].contains("png") || filesSplit[i].contains("jpg") || filesSplit[i].contains("jpeg")) {
                        fileList.add(new Attachment("", fileIds[i], filesSplit[i], true));
                    } else {
                        fileList.add(new Attachment("", fileIds[i], filesSplit[i], false));
                    }
                }
                setFileAdapter(fileList);
            }
            isRefresh = false;

            locationUI();

            binding.tvTitleFreelancer.setVisibility(View.VISIBLE);
            binding.rlFreelancer.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDescription() {
        return binding.etDescribe.getText().toString().trim();
    }

    private String getSearchTag() {
        return binding.tvSearchTag.getText().toString().trim();
    }

    private String getJobTitle() {
        return binding.etJobTitle.getText().toString().trim();
    }

    @Override
    public void bindView(View view, final int position) {
        TextView tvSkillName = view.findViewById(R.id.tv_skill_name);
        ImageView imgRemove = view.findViewById(R.id.img_remove);

        tvSkillName.setText(skillsList.get(position));
        imgRemove.setOnClickListener(v -> {
            skillsList.remove(position);
            skillsIdList.remove(position);
            mAdapter.doRefresh(skillsList);

            if (skillsList != null && skillsList.size() > 0) {
                Preferences.writeString(fragment.activity, Constants.SKILL_NAMES, TextUtils.join(",", skillsList));
                Preferences.writeString(fragment.activity, Constants.SKILL_IDS, TextUtils.join(",", skillsIdList));
            } else {
                Preferences.writeString(fragment.activity, Constants.SKILL_NAMES, "");
                Preferences.writeString(fragment.activity, Constants.SKILL_IDS, "");
            }
        });
    }

    private void setFileAdapter(ArrayList<Attachment> uploadedfiles) {
        this.fileList = uploadedfiles;
        if (uploadedfiles != null && uploadedfiles.size() > 0) {
            if (uploadFileAdapter == null) {
                uploadFileAdapter = new UploadFileAdapter(fragment.activity, projectData != null ? projectData.attachmentPath : "");
                uploadFileAdapter.setOnFileDeleteListener(this);
            }

            uploadFileAdapter.doRefresh(uploadedfiles);
            String filesList = "";
            for (Attachment file : uploadedfiles) {
                if (TextUtils.isEmpty(filesList)) {
                    filesList = file.filepath;
                } else {
                    filesList = filesList + "," + file.filepath;
                }
            }
            Preferences.writeString(fragment.activity, Constants.ATTACH_LOCAL_FILE, filesList);
            if (binding.rvFiles.getAdapter() == null) {
                binding.rvFiles.setAdapter(uploadFileAdapter);
            }
        } else {
            Preferences.writeString(fragment.activity, Constants.ATTACH_LOCAL_FILE, "");
        }
    }

    @Override
    public void onFileDelete(ArrayList<Attachment> mDataset) {
        updateFileList(mDataset);
        setFileAdapter(mDataset);
    }

    private void updateFileList(ArrayList<Attachment> mDataset) {
        StringBuilder fileLocalList = new StringBuilder();
        StringBuilder fileListId = new StringBuilder();
        StringBuilder fileList = new StringBuilder();
        for (int i = 0; i < mDataset.size(); i++) {
            Attachment attachment = mDataset.get(i);
            if (!TextUtils.isEmpty(attachment.filepath)) {
                if (i == 0) {
                    fileLocalList = new StringBuilder(attachment.filepath);
                } else {
                    fileLocalList.append(",").append(attachment.filepath);
                }
            } else {
                if (i == 0) {
                    fileList = new StringBuilder(attachment.fileUrl);
                    fileListId = new StringBuilder(attachment.fileId);
                } else {
                    fileList.append(",").append(attachment.fileUrl);
                    fileListId.append(",").append(attachment.fileId);
                }
            }
        }

        attachFile = fileList.toString();
        attachFileIds = fileListId.toString();
        attachLocalFile = fileLocalList.toString();
    }

    @Override
    public void onClick(View view) {
        Utils.hideSoftKeyboard(fragment.activity);
        switch (view.getId()) {
            case R.id.img_back:
                fragment.goBackTo();
                break;
            case R.id.tv_cancel:
                fragment.activity.finish();
                break;
            case R.id.tv_save:
            case R.id.tv_post_job:
                isFrom1TimeVerifyMail = false;
                try {
                    if (isValid()) {
                        if (fragment.activity.isValidMobileWholeContent(binding.etDescribe.getText().toString().trim()) || fragment.activity.isValidMail(binding.etDescribe.getText().toString().trim())) {
                            fragment.activity.toastMessage(fragment.activity.getString(R.string.Please_remove_your_email_or_contact_number));
                        } else {
                            if (fragment.activity.isLogin()) {
                                if (isVerifyEmail) {
                                    getProfile();
                                } else {
                                    if (fragment.activity.getUserData().trustRate != null) {
                                        if (fragment.activity.getUserData().trustRate.email != null && fragment.activity.getUserData().trustRate.email <= 0) {
                                            new EmailVerificationDialog(fragment.activity);
                                        } else {
                                            addUpdateJobPost();
                                        }
                                    } else {
                                        getProfile();
                                    }
                                }
                            } else {
                                Task24Application.getInstance().isFromPostJobNGig = true;
                                fragment.activity.openLoginDialog();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_attach_file:
                selectFileDialog();
                break;
            case R.id.rl_budget:
                isRefresh = true;
                Fragment priceFragment = new PriceRateFragment();
                Bundle priceBundle = new Bundle();
                priceBundle.putBoolean(Constants.IS_EDIT, true);
                priceBundle.putString(Constants.PLATFORM_ID, moServiceID);
                priceBundle.putString(Constants.PLATFORM_NAME, lawyerService);
                priceFragment.setArguments(priceBundle);
                fragment.activity.replaceFragmentWithTarget(fragment, priceFragment, Constants.BUDGET_FRAGMENT_CODE);
                break;
            case R.id.rl_deadline:
                isRefresh = false;
                Fragment deadFragment = new DeadlineFragment();
                Bundle deadBundle = new Bundle();
                deadBundle.putBoolean(Constants.IS_EDIT, true);
                deadBundle.putString("deadline", deadline);
                deadBundle.putString(Constants.PLATFORM_ID, moServiceID);
                deadBundle.putString(Constants.PLATFORM_NAME, lawyerService);
                deadFragment.setArguments(deadBundle);
                fragment.activity.replaceFragmentWithTarget(fragment, deadFragment, Constants.DEADLINE_FRAGMENT_CODE);

                break;
            case R.id.rl_freelancer:
                isRefresh = true;
                Intent intent = new Intent(fragment.activity, FindExpertActivity.class);
                intent.putExtra("screen", true);
                fragment.startActivity(intent);
                break;
            case R.id.rl_developer:
                isRefresh = true;
                Fragment fragmentA;
                fragmentA = new ChooseDeveloperFragment();

                Bundle bundle = new Bundle();
                bundle.putString(Constants.PLATFORM_ID, moServiceID + "");
                bundle.putString(Constants.SKILL_IDS, skillIds + "");
                bundle.putBoolean(Constants.IS_EDIT, true);
                bundle.putString(Constants.PLATFORM_NAME, lawyerService);
                fragmentA.setArguments(bundle);
                fragment.activity.replaceFragmentWithTarget(fragment, fragmentA, Constants.CHOOSE_DEV_FRAGMENT_CODE);
                break;
            case R.id.tv_add_skill:
                isRefresh = true;
                fragment.activity.replaceFragment(ChooseSkillsFragment.newInstance(true));
                break;
            case R.id.rl_location:
            case R.id.et_location:
                if (mGoogleApiClient.isConnected()) getLocation();
                else mGoogleApiClient.connect();
                break;
            case R.id.img_location:
                fragment.startActivityForResult(new Intent(fragment.activity, AutoPlaceLocationActivity.class).putExtra("latitude", latitude).putExtra("longitude", longitude), 500);
                break;
            case R.id.rl_search_tag:
                if (servicesList == null || servicesList.size() == 0) {
                    getServiceByServiceId();
                } else {
                    Intent intentTag = new Intent(fragment.activity, SearchTagActivity.class);
                    intentTag.putExtra("data", searchTags);
                    intentTag.putExtra("list", servicesList);
                    fragment.startActivityForResult(intentTag, 7);
                }
                break;
        }
    }

    private void getServiceByServiceId() {
        try {
            servicesList = new ArrayList<>();
//            List<ServicesModel.Data> servicesSubList = new ArrayList<>(Preferences.getTopServices(fragment.activity));
            servicesList = new ArrayList<>(Preferences.getCategoryV2(fragment.activity));

            if (servicesList.size() > 0) {
//                for (ServicesModel.Data serviceData : servicesSubList) {
//                    if (serviceData.id == 4352) {
//                        servicesList.addAll(serviceData.services);
//                        break;
//                    }
//                }

                ServicesModel.Data otherData = servicesList.get(servicesList.size() - 1);
                servicesList.remove(servicesList.size() - 1);
                Collections.sort(servicesList, (s1, s2) -> s1.name.compareToIgnoreCase(s2.name));
                servicesList.add(otherData);

                Intent intentTag = new Intent(fragment.activity, SearchTagActivity.class);
                intentTag.putExtra("data", searchTags);
                intentTag.putExtra("list", servicesList);
                fragment.startActivityForResult(intentTag, 7);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void verifyEmail(String email) {
        if (!fragment.activity.isNetworkConnected()) return;

        btnSendEmail.setVisibility(View.INVISIBLE);
        progressBarEmail.setVisibility(View.VISIBLE);

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("platform", "4");

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_SEND_EMAIL_VERIFICATION, true, map);
    }

    private void getProfile() {
        if (!fragment.activity.isNetworkConnected()) return;

        binding.tvPostJob.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.apiRequest(this, fragment.activity, API_GET_CLIENT_PROFILE, false, null);
    }

    private boolean isValid() {
        if (TextUtils.isEmpty(getJobTitle())) {
            fragment.activity.validationError(fragment.activity.getString(R.string.Please_enter_job_title));
            return false;
        }

        String[] split = getJobTitle().split(" ");
        if (split.length < 2) {
            fragment.activity.validationError(fragment.activity.getString(R.string.title_should_be_more_than_2_words));
            return false;
        }

        for (String s : split) {
            if (s.length() > 50) {
                fragment.activity.validationError(fragment.activity.getString(R.string.Please_limit_length_of_words_to_less_than_50_characters_each));
                return false;
            }//handle if word is meaning full or not
        }


        if (getJobTitle().length() > 100) {
            fragment.activity.validationError(fragment.activity.getString(R.string.title_should_be_less_than_100_characters));
            return false;
        }

        if (TextUtils.isEmpty(getDescription())) {
            fragment.activity.validationError(fragment.activity.getString(R.string.please_enter_job_description));
            return false;
        }

        if (getDescription().length() < 50) {
            fragment.activity.validationError(fragment.activity.getString(R.string.please_add_your_project_description_more_than_50_characters));
            return false;
        }

//        if (TextUtils.isEmpty(getSearchTag())) {
//            fragment.activity.validationError(fragment.activity.getString(R.string.please_add_atleast_1_search_tag));
//            return false;
//        }

        if (TextUtils.isEmpty(skillNames)) {
            fragment.activity.validationError(fragment.activity.getString(R.string.please_select_what_do_you_need));
            return false;
        }

        return true;
    }

    private void addUpdateJobPost() {
        if (!fragment.activity.isNetworkConnected()) return;

        fragment.activity.isClickableView = true;
        if (isEdit || isDuplicateJob) {
            binding.tvSave.setVisibility(View.INVISIBLE);
            binding.progressBarEdit.setVisibility(View.VISIBLE);
        } else {
            binding.tvPostJob.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }

        MultipartBody.Part[] body = null;
        if (fileList != null && fileList.size() > 0) {
            body = new MultipartBody.Part[fileList.size()];
            for (int i = 0; i < fileList.size(); i++) {
                File file;
                if (!fragment.activity.isEmpty(fileList.get(i).filepath)) {
                    if (fileList.get(i).filepath.contains(".png") || fileList.get(i).filepath.contains(".jpg") || fileList.get(i).filepath.contains(".jpeg")) {
                        file = CompressFile.getCompressedImageFile(new File(fileList.get(i).filepath), fragment.activity);
                    } else {
                        file = new File(fileList.get(i).filepath);
                    }
                    Uri selectedUri = Uri.fromFile(file); // not orking in samsung device
                    String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());

                    RequestBody requestFile = null;
                    if (mimeType != null) {
                        requestFile = RequestBody.create(file, MediaType.parse(mimeType));
                    }

                    Headers.Builder headers = new Headers.Builder();
                    headers.addUnsafeNonAscii("Content-Disposition", "form-data; name=\"file\"; filename=\"" + file.getName() + "\"");

                    if (requestFile != null) {
                        body[i] = MultipartBody.Part.create(headers.build(), requestFile);
                    }
                }
            }
        }

        RequestBody descriptionBody = RequestBody.create(getDescription(), MultipartBody.FORM);
        RequestBody titleBody = RequestBody.create(getJobTitle(), MultipartBody.FORM);
        RequestBody payTypeIdBody = RequestBody.create(isFixedPrice ? "1" : "2", MultipartBody.FORM);

        if (!binding.txtLblBudget.isShown()) {//if select FREE then pass clientRate=-1 and budget=0
            clientRateId = -1;
            payTypeIdBody = RequestBody.create("5", MultipartBody.FORM);
        } else {
            payTypeIdBody = RequestBody.create("1", MultipartBody.FORM);
        }

        RequestBody rateIdBody = RequestBody.create(String.valueOf(clientRateId), MultipartBody.FORM);
        if (skillIds.equals("0")) {
            skillIds = "4352";
        }
        RequestBody skillidBody = RequestBody.create(skillIds, MultipartBody.FORM);

        if (clientRateId == 0) {
            if (TextUtils.isEmpty(budget) || budget.equalsIgnoreCase("null")) {
                fragment.activity.toastMessage(fragment.activity.getString(R.string.please_select_budget));
                fragment.activity.isClickableView = false;
                if (isEdit || isDuplicateJob) {
                    binding.tvSave.setVisibility(View.VISIBLE);
                    binding.progressBarEdit.setVisibility(View.GONE);
                } else {
                    binding.tvPostJob.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                }
                return;
            }
        }

        StringBuilder expertIds = new StringBuilder();
        if (expertUserList != null && expertUserList.size() > 0) {
            for (int i = 0; i < expertUserList.size(); i++) {
                if (i == 0) {
                    expertIds = new StringBuilder(expertUserList.get(i).id + "");
                } else {
                    expertIds.insert(0, expertUserList.get(i).id + ", ");
                }
            }
        }
        RequestBody offeredBody = RequestBody.create("1", MultipartBody.FORM);
        RequestBody expertsBody = RequestBody.create(expertIds.toString(), MultipartBody.FORM);
        RequestBody longiBody = RequestBody.create("" + longitude, MultipartBody.FORM);
        RequestBody latiBody = RequestBody.create("" + latitude, MultipartBody.FORM);
        RequestBody sysIdBody = RequestBody.create("6", MultipartBody.FORM);
        RequestBody legalHelpIdBody;

        String legalhelpid = "";

        legalHelpIdBody = RequestBody.create("" + legalhelpid, MultipartBody.FORM);

        RequestBody deadLineBody = null;
        String formattedDate = "";
        if (!TextUtils.isEmpty(deadline)) {
            String outputFormat = "yyyy-MM-dd HH:mm:ss";
            String inputFormat = "yyyy-MM-dd HH:mm";
            try {
                formattedDate = Utils.TimeStampConverterEnglish(inputFormat, deadline, outputFormat);
                deadLineBody = RequestBody.create(formattedDate, MultipartBody.FORM);
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
        }


        HashMap<String, RequestBody> map = new HashMap<>();
        if (expertUserList != null && expertUserList.size() > 0) {
            map.put("offered", offeredBody);
            map.put("experts", expertsBody);
        }
        map.put("sys_id", sysIdBody);
        map.put("title", titleBody);
        map.put("description", descriptionBody);
        map.put("pay_type_id", payTypeIdBody);
        RequestBody budgetBody;
        if (clientRateId == 0) {
            if (!TextUtils.isEmpty(budget) && !budget.equalsIgnoreCase("null")) {
                budgetBody = RequestBody.create(budget, MultipartBody.FORM);
                map.put("budget", budgetBody);
            }
        }
        map.put("client_rate_id", rateIdBody);
        map.put("service_id", skillidBody);
        map.put("longitude", longiBody);
        map.put("latitude", latiBody);

        if (isDuplicateJob) {
            map.put("is_duplicate", RequestBody.create("1", MultipartBody.FORM));
            map.put("duplicate_job_post_id", RequestBody.create(String.valueOf(projectData.id), MultipartBody.FORM));
        }

        map.put("legal_help_id", legalHelpIdBody);
        map.put("pages", RequestBody.create("0", MultipartBody.FORM));
        if (deadLineBody != null) map.put("deadline", deadLineBody);
        if (projectData != null && projectData.id != null && projectData.id != 0) {//in case of edit or duplicate
            map.put("job_post_id", RequestBody.create(String.valueOf(projectData.id), MultipartBody.FORM));
        }
        if (isEdit || isDuplicateJob) {
            String toDelete = "";
            if (uploadFileAdapter != null && !TextUtils.isEmpty(uploadFileAdapter.toDeleteId())) {
                toDelete = uploadFileAdapter.toDeleteId();
            }
            if (!TextUtils.isEmpty(toDelete)) {
                map.put("to_delete", RequestBody.create(toDelete, MultipartBody.FORM));
            }
        }

        RequestBody otherSearchTags, jobSearchTags;
        if (sbOtherSearchTags.toString().length() > 1) {
            otherSearchTags = RequestBody.create(sbOtherSearchTags.deleteCharAt(sbOtherSearchTags.length() - 1).toString(), MultipartBody.FORM);
        } else {
            otherSearchTags = RequestBody.create(sbOtherSearchTags.toString(), MultipartBody.FORM);
        }

        if (sbJobSearchTags.toString().length() > 1) {
            jobSearchTags = RequestBody.create(sbJobSearchTags.deleteCharAt(sbJobSearchTags.length() - 1).toString(), MultipartBody.FORM);
        } else {
            jobSearchTags = RequestBody.create(sbJobSearchTags.toString(), MultipartBody.FORM);
        }

        map.put("other_search_tags", otherSearchTags);
        map.put("job_search_tags", jobSearchTags);

        ApiRequest apiRequest = new ApiRequest();
        if (isEdit) {
            apiRequest.apiImageUploadRequestBody(this, fragment.activity, API_EDIT_JOB_POST, body, map);
        } else {
            apiRequest.apiImageUploadRequestBody(this, fragment.activity, API_ADD_JOB_POST, body, map);
        }
    }


    private void postDoneDialog() {
        final Dialog dialog = new Dialog(fragment.activity);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_posting_done);
        dialog.setCancelable(true);

        TextView tvInfo1 = dialog.findViewById(R.id.tv_info_1);
        TextView tvInfo2 = dialog.findViewById(R.id.tv_info_2);
        TextView tvInfo3 = dialog.findViewById(R.id.tv_info_3);
        TextView tvViewProposals = dialog.findViewById(R.id.tv_view_proposals);

        int[] colorList = {R.color.black};
        String[] fonts = {Constants.SFTEXT_BOLD};

        String[] words1 = {fragment.activity.getString(R.string.free_bids)};
        String[] words2 = {fragment.activity.getString(R.string.hire_the_best_fit)};
        String[] words3 = {fragment.activity.getString(R.string.satisfied_100)};

        tvInfo1.setText(Utils.getBoldString(fragment.activity, fragment.getString(R.string.job_post_done_1), fonts, colorList, words1));
        tvInfo2.setText(Utils.getBoldString(fragment.activity, fragment.getString(R.string.job_post_done_2), fonts, colorList, words2));
        tvInfo3.setText(Utils.getBoldString(fragment.activity, fragment.getString(R.string.job_post_done_3), fonts, colorList, words3));

        tvViewProposals.setOnClickListener(v -> dialog.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        DisplayMetrics displaymetrics = new DisplayMetrics();
        fragment.requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        lp.gravity = Gravity.CENTER;
        lp.width = (int) (displaymetrics.widthPixels * 0.9);
        lp.height = (int) (displaymetrics.heightPixels * 0.7);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialog.setOnDismissListener(dialog1 -> {
            fragment.activity.isClickableView = false;
            fragment.activity.gotoMainActivity(Constants.TAB_JOB_LIST);
        });
    }

    private void selectFileDialog() {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(fragment.activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_camera_document_select);
        dialog.setCancelable(true);
        TextView tvCancel = dialog.findViewById(R.id.btn_cancel);
        LinearLayout llCamera = dialog.findViewById(R.id.ll_camera);
        LinearLayout llDocument = dialog.findViewById(R.id.ll_document);

        llCamera.setOnClickListener(v -> {
            if (fragment.activity.checkStoragePermission()) {
                checkPermission(false);
            } else {
                new StorageDisclosureDialog(fragment.activity, () -> checkPermission(false));
            }
            dialog.dismiss();
        });

        llDocument.setOnClickListener(v -> {
            if (fragment.activity.checkStoragePermission()) {
                checkPermission(true);
            } else {
                new StorageDisclosureDialog(fragment.activity, () -> checkPermission(true));
            }
            dialog.dismiss();
        });

        tvCancel.setOnClickListener(v -> dialog.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    public void checkPermission(final boolean isDocument) {
        Dexter.withActivity(fragment.activity).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    isRefresh = false;
                    if (isDocument) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            fragment.activity.openDocuments(fragment, 1);
                        } else {
                            Intent intent = new Intent(fragment.activity, NormalFilePickActivity.class);
                            intent.putExtra(Constant.MAX_NUMBER, 5);
                            intent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "ppt", "pptx", "pdf"});
                            fragment.startActivityForResult(intent, Constant.REQUEST_CODE_PICK_FILE);
                        }
                    } else {
                        Intent intent = new Intent(fragment.activity, ImagePickActivity.class);
                        intent.putExtra(IS_NEED_CAMERA, true);
                        intent.putExtra(Constant.MAX_NUMBER, 5);
                        fragment.startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
                    }
                }

                if (report.isAnyPermissionPermanentlyDenied()) {
                    fragment.activity.toastMessage(fragment.activity.getString(R.string.please_give_permission));
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).onSameThread().check();
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_FILE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<NormalFile> docPaths = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    if (docPaths != null && docPaths.size() > 0) {
                        for (NormalFile file : docPaths) {
                            fileList.add(new Attachment(file.getPath(), "", "", false));
                        }
                        updateFileList(fileList);
                        setFileAdapter(fileList);
                    } else {
                        fragment.activity.toastMessage(fragment.activity.getString(R.string.file_not_selected));
                    }
                }
                break;
            case 4545://doc picker for android 10+
                String path = null;
                try {
                    if (data != null && data.getData() != null) {
                        path = Utils.getFilePath(fragment.activity, data.getData());
                        if (path != null) {
                            fileList.add(new Attachment(path, "", "", false));
                            updateFileList(fileList);
                            setFileAdapter(fileList);
                        } else {
                            fragment.activity.toastMessage(fragment.activity.getString(R.string.file_not_selected));
                        }
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    if (imgPath != null && imgPath.size() > 0) {
                        for (ImageFile file : imgPath) {
                            fileList.add(new Attachment(file.getPath(), "", "", true));
                        }
                        updateFileList(fileList);
                        setFileAdapter(fileList);
                    } else {
                        fragment.activity.toastMessage(fragment.activity.getString(R.string.file_not_selected));
                    }
                }
                break;
            case Constants.DEADLINE_FRAGMENT_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    deadline = data.getStringExtra("deadline");
                    if (deadline != null) {
                        binding.tvDeadline.setText(Utils.setDeadLine(deadline, fragment.activity));
                    }
                    if (projectData == null) {
                        projectData = new ProjectByID();
                    }
                    projectData.deadline = deadline;
                }
                break;
            case Constants.CHOOSE_DEV_FRAGMENT_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    skillIds = data.getStringExtra(Constants.SKILL_IDS);
                    skillNames = data.getStringExtra(Constants.SKILL_NAMES);
                    if (projectData == null) {
                        projectData = new ProjectByID();
                    }
                    if (projectData.sId != null && projectData.getName(fragment.activity.getLanguage()) != null) {
                        projectData.sId = Integer.valueOf(skillIds);
                    }
                    projectData.name = skillNames;
                    binding.tvDeveloper.setText(skillNames);
                }
                break;

            case Constants.BUDGET_FRAGMENT_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    budget = data.getStringExtra(Constants.BUDGET);
                    clientRate = data.getStringExtra(Constants.CLIENT_RATE);
                    clientRateId = data.getIntExtra(Constants.CLIENT_RATE_ID, 0);

                    if (projectData == null) {
                        projectData = new ProjectByID();
                    }

                    if (!TextUtils.isEmpty(budget)) {
                        if (projectData.jobPostBudget == null) {
                            projectData.jobPostBudget = new ProjectByID.JobPostBudget();
                        }
                        projectData.jobPostBudget.budget = Double.valueOf(budget);
                        projectData.rangeFrom = "";
                        projectData.rangeTo = "";
                    } else {
                        String[] rat = clientRate.split("-");
                        projectData.rangeFrom = rat[0].replace("$", "");
                        projectData.rangeTo = rat[1].replace("$", "");
                    }
                    projectData.clientRateId = clientRateId;
                    if (clientRateId >= 0) {
                        payType = "Fixed";
                    }
                }
                break;
            case Constants.CHOOSE_LAW_CAT_FRAGMENT_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    moServiceID = data.getStringExtra(Constants.SKILL_IDS);
                    if (isEdit) {
                        if (moServiceID != null) {
                            projectData.lhId = Integer.parseInt(moServiceID);
                        }
                    }
                    lawyerService = data.getStringExtra(Constants.PLATFORM_NAME);
                    binding.tvSelLawCat.setText(lawyerService);
                }
                break;
            case 1000:
                switch (resultCode) {
                    case RESULT_OK:
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        fragment.activity.toastMessage(fragment.activity.getString(R.string.location_service_not_enabled));
                        break;
                    default:
                        break;
                }
                break;
            case 121://edit job case
                getProjectById();
                break;
            case 500:
                if (resultCode == RESULT_OK && data != null) {
                    String locationAddress = data.getStringExtra(Constants.PICK_LOCATION_ADDRESS);
                    if (!TextUtils.isEmpty(locationAddress)) {
                        binding.etLocation.setText(locationAddress);
                    }
                    latitude = data.getDoubleExtra(Constants.PICK_LOCATION_LATITUDE, 0);

                    longitude = data.getDoubleExtra(Constants.PICK_LOCATION_LONGITUDE, 0);
                }
                break;
            case 7:
                try {
                    if (data != null) {
                        ArrayList<SearchTagModel> selectedTags = (ArrayList<SearchTagModel>) data.getSerializableExtra("tags");
                        searchTags = new ArrayList<>();
                        if (selectedTags != null && selectedTags.size() > 0) {
                            searchTags = selectedTags;
                            if (isEdit || isDuplicateJob || isRepostJob) {
                                if (projectData != null) {//in case of edit job post
                                    {
                                        projectData.jobSearchTags = selectedTags;
                                    }
                                }
                            }

                            StringBuilder sb = new StringBuilder();
                            for (SearchTagModel searchTagModel : selectedTags) {
                                sb.append(searchTagModel.tagName);
                                sb.append(",");

                                sbJobSearchTags = new StringBuilder();
                                sbOtherSearchTags = new StringBuilder();
                                if (searchTagModel.serviceCategoryID != 0) {
                                    sbJobSearchTags.append(searchTagModel.serviceCategoryID);
                                    sbJobSearchTags.append(",");
                                } else {
                                    sbOtherSearchTags.append(searchTagModel.tagName);
                                    sbOtherSearchTags.append(",");
                                }
                            }

                            if (sb.toString().length() > 1) {
                                binding.tvSearchTag.setText(sb.deleteCharAt(sb.length() - 1).toString());
                            } else {
                                binding.tvSearchTag.setText(sb.toString());
                            }

                        } else {
                            binding.tvSearchTag.setText("");
                            searchTags = null;
                            projectData.jobSearchTags = null;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    void onStopMethod() {
        if (mGoogleApiClient != null) mGoogleApiClient.disconnect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        fragment.activity.toastMessage(fragment.activity.getString(R.string.connection_suspended));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        fragment.activity.toastMessage(fragment.activity.getString(R.string.connection_failed));
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(fragment.activity, 90000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Current Location", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    private void settingRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);    // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(1000);   // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    getLocation();
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        status.startResolutionForResult(fragment.activity, 1000);
                    } catch (IntentSender.SendIntentException ignored) {
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    break;
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(fragment.activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fragment.activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
        } else {
            showLocationDisclosure();
        }
    }

    private void getLocationPermission() {
        try {
            Dexter.withActivity(fragment.activity).withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        if (ActivityCompat.checkSelfPermission(fragment.activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fragment.activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                        latitude = mLastLocation.getLatitude();
                        longitude = mLastLocation.getLongitude();
                        LocationAddress.getAddressFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), fragment.activity, new GeocoderHandler());
                    }

                    if (report.isAnyPermissionPermanentlyDenied()) {
                        fragment.activity.toastMessage(fragment.activity.getString(R.string.please_give_permission));
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

    @Override
    public void successResponse(String responseBody, String url, String message, String data) {
        if (url.equalsIgnoreCase(API_JOB_DETAILS)) {
            ProjectByID project = ProjectByID.getProjectById(responseBody);

            if (project != null) {
                projectData = project;
            }
            updateUI();
        } else if (url.equalsIgnoreCase(API_ADD_JOB_POST) || url.equalsIgnoreCase(API_EDIT_JOB_POST)) {
            Preferences.setExpertUsers(fragment.activity, null);
            if (isEdit || isDuplicateJob) {
                binding.tvSave.setVisibility(View.VISIBLE);
                binding.progressBarEdit.setVisibility(View.GONE);
            } else {
                binding.tvPostJob.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }
            if (isEdit) {
                fragment.activity.toastMessage(message);
                fragment.activity.isClickableView = false;
                fragment.activity.setResult(Activity.RESULT_OK);
                fragment.activity.finish();
                fragment.activity.finishToRight();
            } else {
                postDoneDialog();
            }
        } else if (url.equalsIgnoreCase(API_SEND_EMAIL_VERIFICATION)) {
            btnSendEmail.setVisibility(View.INVISIBLE);
            progressBarEmail.setVisibility(View.VISIBLE);
            fragment.activity.toastMessage(message);
            isVerifyEmail = true;
            isFrom1TimeVerifyMail = true;
            getProfile();
            dialogEmail.dismiss();
        } else if (url.equalsIgnoreCase(API_GET_CLIENT_PROFILE)) {
            Profile profile = Profile.getProfileInfo(responseBody);
            binding.tvPostJob.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
            if (!isFrom1TimeVerifyMail) {
                if (profile != null) {
                    Preferences.setProfileData(fragment.activity, profile);
                    if (profile.trustRate.email <= 0) {
                        new EmailVerificationDialog(fragment.activity);
                    } else {
                        addUpdateJobPost();
                    }
                }
            }
        }
    }

    @Override
    public void failureResponse(Throwable throwable, String url, String message) {
        fragment.activity.isClickableView = false;
        if (isEdit || isDuplicateJob) {
            binding.tvSave.setVisibility(View.VISIBLE);
            binding.progressBarEdit.setVisibility(View.GONE);
        } else if (url.equalsIgnoreCase(API_SEND_EMAIL_VERIFICATION)) {
            btnSendEmail.setVisibility(View.INVISIBLE);
            progressBarEmail.setVisibility(View.VISIBLE);
            dialogEmail.dismiss();
        } else {
            binding.tvPostJob.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
            fragment.activity.toastMessage(message);
        }
    }

    private void updateUI() {
        describe = projectData.description;
        moServiceID = projectData.socialPlatformID + "";
        skillIds = projectData.sId + "";
        skillNames = projectData.getName(fragment.activity.getLanguage());

        if (isEdit && projectData.getSocialPlatformName(fragment.activity.getLanguage()) != null) {
            lawyerService = projectData.getSocialPlatformName(fragment.activity.getLanguage());
        }

        if (projectData.clientRateId != null && projectData.clientRateId >= 0) {
            clientRateId = projectData.clientRateId;

            if (!TextUtils.isEmpty(projectData.rangeTo) && !TextUtils.isEmpty(projectData.rangeFrom)) {
                if (fragment.activity.getCurrency().equals("SAR")) {
                    clientRate = projectData.rangeFrom + " " + fragment.getString(R.string.sar) + " - " + projectData.rangeTo + " " + fragment.getString(R.string.sar);
                } else {
                    clientRate = "$" + projectData.rangeFrom + " - $" + projectData.rangeTo;
                }
            } else if (!TextUtils.isEmpty(projectData.rangeFrom)) {
                clientRate = fragment.activity.getCurrency().equals("SAR") ? projectData.rangeFrom + " " + fragment.getString(R.string.sar) : "$" + projectData.rangeFrom;
            } else if (projectData.jobPostBudget != null && projectData.jobPostBudget.budget != null) {
                budget = String.valueOf(projectData.jobPostBudget.budget);
                clientRate = "";
            } else {
                budget = "";
            }
        } else {
            if (projectData.jobPostBudget != null) budget = projectData.jobPostBudget.budget + "";
            clientRate = "";
        }
        if ((!isDuplicateJob || !isRepostJob) && projectData.attachments != null) {
            StringBuilder attachments = null;
            StringBuilder attachmentIds = null;
            for (int i = 0; i < projectData.attachments.size(); i++) {
                if (i == 0) {
                    attachments = new StringBuilder(projectData.attachments.get(i).filename);
                    attachmentIds = new StringBuilder(projectData.attachments.get(i).id + "");
                } else {
                    attachments.append(",").append(projectData.attachments.get(i).filename);
                    attachmentIds.append(",").append(projectData.attachments.get(i).id);
                }
            }

            attachLocalFile = "";
            if (attachments != null) {
                attachFile = attachments.toString();
            }
            if (attachmentIds != null) {
                attachFileIds = attachmentIds.toString();
            }
        }

        if (projectData.clientRateId != null && projectData.clientRateId >= 0) {
            updateTabUI("Fixed");
        } else {
            updateTabUI("FREE");
        }
        binding.etJobTitle.setText(projectData.title);
        setUi();
        isRefreshApi = false;
    }


    private void showLocationDisclosure() {
        final Dialog dialog = new Dialog(fragment.activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_location_disclosure);
        dialog.setCancelable(false);

        TextView tvOk = dialog.findViewById(R.id.tv_ok);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);

        tvOk.setOnClickListener(v -> {
            dialog.dismiss();
            getLocationPermission();
        });
        tvCancel.setOnClickListener(v -> dialog.dismiss());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

    }

    public class EmailVerificationDialog {
        private final BaseActivity activity;
        private final CustomEditText edtEmail;

        public EmailVerificationDialog(BaseActivity activity) {
            this.activity = activity;

            dialogEmail = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
            dialogEmail.setTitle(null);
            dialogEmail.setContentView(R.layout.dialog_email_verification);
            dialogEmail.setCancelable(true);

            edtEmail = dialogEmail.findViewById(R.id.edtEmail);
            btnSendEmail = dialogEmail.findViewById(R.id.btnSendEmail);
            progressBarEmail = dialogEmail.findViewById(R.id.progressBarEmail);

            edtEmail.setText(activity.getUserData().email);

            btnSendEmail.setOnClickListener(v -> {
                if (validData()) {
                    verifyEmail(edtEmail.getText().toString());
                }
            });

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(dialogEmail.getWindow()).getAttributes());
            lp.gravity = Gravity.CENTER;
            dialogEmail.show();
            dialogEmail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogEmail.getWindow().setAttributes(lp);
        }

        private boolean validData() {
            if (activity.isEmpty(edtEmail.getText().toString())) {
                activity.validationError(fragment.activity.getString(R.string.enter_emailid));
                return false;
            }

            if (!activity.isValidEmail(edtEmail.getText().toString())) {
                activity.validationError(fragment.activity.getString(R.string.enter_valid_emailid));
                return false;
            }
            return true;
        }

    }

    @SuppressLint("HandlerLeak")
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Address locationAddress;
            if (message.what == 1) {
                Bundle bundle = message.getData();
                locationAddress = bundle.getParcelable("address");
                if (locationAddress != null) {
                    binding.etLocation.setText(String.format(Locale.US, "%s", locationAddress.getAddressLine(0).replace("Unnamed Road,", "")));
                }
            }
        }
    }
}
