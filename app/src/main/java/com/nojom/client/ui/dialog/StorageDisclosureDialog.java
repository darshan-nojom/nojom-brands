package com.nojom.client.ui.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.textview.CustomTextView;
import android.view.Gravity;
import android.view.WindowManager;

import com.nojom.client.R;
import com.nojom.client.ui.BaseActivity;

import java.util.Objects;

public class StorageDisclosureDialog {
    private BaseActivity activity;
    private OnClickListener onClickListener;

    public StorageDisclosureDialog(BaseActivity activity, OnClickListener onClickListener) {
        this.activity = activity;
        this.onClickListener = onClickListener;

        final Dialog dialog = new Dialog(activity, R.style.Theme_AppCompat_Dialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_storage_disclosure);
        dialog.setCancelable(true);

        CustomTextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        CustomTextView tvOk = dialog.findViewById(R.id.tv_ok);

        tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        tvOk.setOnClickListener(v -> {
            dialog.dismiss();
            if (onClickListener != null) {
                onClickListener.onClickOk();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
    }

    public interface OnClickListener {
        void onClickOk();
    }
}
