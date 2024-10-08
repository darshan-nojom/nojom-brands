package com.nojom.client.ui.projects;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nojom.client.R;
import com.nojom.client.Task24Application;
import com.nojom.client.adapter.PaymentAdapter;
import com.nojom.client.databinding.ActivityBankTransferBinding;
import com.nojom.client.model.Banks;
import com.nojom.client.model.ExpertGigDetail;
import com.nojom.client.model.ProjectByID;
import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.activity.ImagePickActivity;
import com.nojom.client.multitypepicker.activity.NormalFilePickActivity;
import com.nojom.client.multitypepicker.activity.VideoPickActivity;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.ui.balance.BalanceActivity;
import com.nojom.client.ui.clientprofile.PaymentActivityVM;
import com.nojom.client.ui.dialog.StorageDisclosureDialog;
import com.nojom.client.util.Constants;
import com.nojom.client.util.Preferences;
import com.nojom.client.util.Utils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BankTransferActivity extends BaseActivity implements View.OnClickListener, PaymentAdapter.OnClickPaymentListener {
    ActivityBankTransferBinding binding;
    String selectedFilePath;
    List<Banks.Data> bankList;
    private String wallet, id, code, redeem, total, depAmnt, fees, appliedPromoCode;
    private boolean isFromGig;
    private PaymentActivityVM paymentActivityVM;
    private ProjectByID projectData;
    private ExpertGigDetail gigData;
    private double promoDiscountAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank_transfer);
        paymentActivityVM = new PaymentActivityVM(Task24Application.getInstance(), this);
        paymentActivityVM.getBanks();
        initData();
    }

    private void initData() {
        binding.toolbar.tvTitle.setText(getString(R.string.bank_transfer));
        binding.toolbar.imgBack.setOnClickListener(this);
        binding.relDepositNow.setOnClickListener(this);
        binding.txtAttach.setOnClickListener(this);
        binding.imgDelete.setOnClickListener(this);
        binding.etBankName.setOnClickListener(this);
        bankList = Preferences.getBanks(this);

        paymentActivityVM.getBankMutableLiveData().observe(this, banks -> {
            if (banks != null && banks.data != null && banks.data.size() > 0) {
                bankList = banks.data;
            }
        });

        if (getIntent() != null) {
            isFromGig = getIntent().getBooleanExtra("isFromGig", false);
            wallet = getIntent().getStringExtra("wallet");
            id = getIntent().getStringExtra("id");
            depAmnt = getIntent().getStringExtra("depAmnt");
            fees = getIntent().getStringExtra("fees");
            total = getIntent().getStringExtra("total");
            code = getIntent().getStringExtra("code");
            redeem = getIntent().getStringExtra("redeem");
            projectData = (ProjectByID) getIntent().getSerializableExtra("project");
            gigData = (ExpertGigDetail) getIntent().getSerializableExtra("gig");
            appliedPromoCode = getIntent().getStringExtra("promocode");
            promoDiscountAmount = getIntent().getDoubleExtra("promoamount", 0.0);

            binding.etTxnAmount.setText(total);
        }

        paymentActivityVM.getPurchaseMutableLiveData().observe(this, purchaseModel -> {
            thanksForPaymentDialog();
        });

        paymentActivityVM.getIsShowProgress().observe(this, aBoolean -> {
            if (aBoolean) {
                binding.txtCont.setVisibility(View.INVISIBLE);
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.txtCont.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_attach:
                selectFileDialog(1234);
                break;
            case R.id.imgDelete:
                binding.relSelectedCrn.setVisibility(View.GONE);
                binding.txtFileName.setText("");
                selectedFilePath = null;
                break;
            case R.id.et_bankName:
                if (bankList != null && bankList.size() > 0) {
                    showBankNameDialog();
                } else {
                    paymentActivityVM.getBanks();
                }
                break;
            case R.id.rel_depositNow:
                onClickBankTransferPay();
//                thanksForPaymentDialog();
                break;
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }

    private void selectFileDialog(int code) {
        @SuppressLint("PrivateResource") final Dialog dialog = new Dialog(this, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_camera_document_select);
        dialog.setCancelable(true);
        TextView tvCancel = dialog.findViewById(R.id.btn_cancel);
        LinearLayout llCamera = dialog.findViewById(R.id.ll_camera);
        LinearLayout llDocument = dialog.findViewById(R.id.ll_document);

        llCamera.setOnClickListener(v -> {
            dialog.dismiss();
            if (checkStoragePermission()) {
                checkPermission(false, code);
            } else {
                new StorageDisclosureDialog(this, () -> checkPermission(false, code));
            }
        });

        llDocument.setOnClickListener(v -> {
            dialog.dismiss();
            if (checkStoragePermission()) {
                checkPermission(true, code);
            } else {
                new StorageDisclosureDialog(this, () -> checkPermission(true, code));
            }
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

    public void checkPermission(final boolean isDocument, int code) {
        try {
            Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        if (isDocument) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                openDocuments(BankTransferActivity.this, 1, code);
                            } else {
                                Intent intent = new Intent(BankTransferActivity.this, NormalFilePickActivity.class);
                                intent.putExtra(Constant.MAX_NUMBER, 1);
                                intent.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "ppt", "pptx", "pdf"});
                                startActivityForResult(intent, code);
                            }
                        } else {
                            Intent intent = new Intent(BankTransferActivity.this, ImagePickActivity.class);
                            intent.putExtra(VideoPickActivity.IS_NEED_CAMERA, true);
                            intent.putExtra(Constant.MAX_NUMBER, 1);
                            intent.putExtra("rCode", Constant.REQUEST_CODE_PICK_IMAGE);
                            startActivityForResult(intent, code);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == Activity.RESULT_OK && data != null) {

            ArrayList<ImageFile> imgPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            if (imgPath != null && imgPath.size() > 0) {
                selectedFilePath = imgPath.get(0).getPath();
                binding.relSelectedCrn.setVisibility(View.VISIBLE);
                binding.txtFileName.setText(imgPath.get(0).getName());
                return;
            }

            String path = null;
            try {
                if (data.getData() != null) {
                    path = Utils.getFilePath(this, data.getData());
                    if (path != null) {
                        selectedFilePath = path;
                        binding.relSelectedCrn.setVisibility(View.VISIBLE);
                        binding.txtFileName.setText(path);
                    } else {
                        toastMessage(getString(R.string.please_select_file));
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }

    Dialog dialogBankName;
    PaymentAdapter paymentAdapter;

    void showBankNameDialog() {
        dialogBankName = new Dialog(this, R.style.Theme_Design_Light_BottomSheetDialog);
        dialogBankName.setTitle(null);
        dialogBankName.setContentView(R.layout.dialog_item_select_black);
        dialogBankName.setCancelable(true);

        LinearLayout llButton = dialogBankName.findViewById(R.id.ll_bottom);
        TextView tvCancel = dialogBankName.findViewById(R.id.tv_cancel);
        TextView tvApply = dialogBankName.findViewById(R.id.tv_apply);
        final EditText etSearch = dialogBankName.findViewById(R.id.et_search);
        RecyclerView rvTypes = dialogBankName.findViewById(R.id.rv_items);

        etSearch.setVisibility(View.GONE);
        llButton.setVisibility(View.GONE);

        rvTypes.setLayoutManager(new LinearLayoutManager(this));
        try {
            paymentAdapter = new PaymentAdapter(this, bankList, this);
            paymentAdapter.setSelectedLanguageList(binding.etBankName.getText().toString());
            rvTypes.setAdapter(paymentAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvCancel.setOnClickListener(v -> {
            dialogBankName.dismiss();
        });

        tvApply.setOnClickListener(v -> {
            dialogBankName.dismiss();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialogBankName.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialogBankName.show();
        dialogBankName.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBankName.getWindow().setAttributes(lp);
        etSearch.setOnFocusChangeListener((v, hasFocus) -> etSearch.post(() -> Utils.openSoftKeyboard(this, etSearch)));
        etSearch.requestFocus();
    }

    @Override
    public void onClickBank(Banks.Data bankName, int adapterPos) {
        binding.etBankName.setText(bankName.getName(getLanguage()));
        binding.etBankName.setTag("" + bankName.id);
        if (dialogBankName != null) {
            dialogBankName.dismiss();
        }
    }

    public void onClickBankTransferPay() {
        if (TextUtils.isEmpty(binding.etSenderName.getText().toString().trim())) {
            toastMessage(getString(R.string.please_enter_sender_name));
            return;
        }
        if (TextUtils.isEmpty(binding.etBankName.getText().toString().trim())) {
            toastMessage(getString(R.string.please_select_bank_name));
            return;
        }
        if (TextUtils.isEmpty(binding.etCardNumber.getText().toString().trim())) {
            toastMessage(getString(R.string.please_enter_account_number));
            return;
        }
        if (TextUtils.isEmpty(binding.etTxnAmount.getText().toString().trim())) {
            toastMessage(getString(R.string.please_enter_valid_transaction_amount));
            return;
        }
        if (TextUtils.isEmpty(binding.etTxnDate.getText().toString().trim())) {
            toastMessage(getString(R.string.please_enter_transaction_date));
            return;
        }
        if (TextUtils.isEmpty(binding.etRefNo.getText().toString().trim())) {
            toastMessage(getString(R.string.please_enter_reference_number));
            return;
        }

        try {
            double redeem = Math.abs(Double.parseDouble(getRedeemVal().replaceAll("- ", "")));

            paymentActivityVM.doPaymentWithBraintreeBankTransfer(isFromGig, selectedFilePath, projectData, gigData, fees, binding.etSenderName.getText().toString(), Integer.valueOf(binding.etBankName.getTag().toString()), binding.etCardNumber.getText().toString(), binding.etTxnDate.getText().toString(), binding.etRefNo.getText().toString(), binding.etNote.getText().toString(), total, redeem, appliedPromoCode, promoDiscountAmount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRedeemVal() {
        if (getCurrency().equals("SAR")) {
            return Utils.priceWithoutSAR(this, redeem);
        } else {
            return Utils.priceWithout$(redeem);
        }
    }

    private void thanksForPaymentDialog() {
        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_bank_transfer_done);
        dialog.setCancelable(true);

        TextView txtGoto = dialog.findViewById(R.id.txt_goto_wallet);
        TextView txtService = dialog.findViewById(R.id.txt_customerService);

        txtGoto.setOnClickListener(v -> {
            gotoMainActivity(4);
            startActivity(new Intent(this, BalanceActivity.class));
        });
        txtService.setOnClickListener(v -> {
            dialog.dismiss();
            gotoMainActivity(Constants.TAB_JOB_LIST);
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

        dialog.setOnDismissListener(dialog1 -> {
            dialog.dismiss();
        });
    }
}
