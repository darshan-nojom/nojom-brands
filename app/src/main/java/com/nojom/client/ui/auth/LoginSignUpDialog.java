package com.nojom.client.ui.auth;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.Html;
import android.textview.CustomTextView;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nojom.client.R;
import com.nojom.client.ui.BaseActivity;
import com.nojom.client.util.Constants;

import java.util.Objects;

public class LoginSignUpDialog implements Constants {
    private final BaseActivity activity;

    public LoginSignUpDialog(BaseActivity activity) {
        this.activity = activity;

        final Dialog dialog = new Dialog(activity, R.style.Theme_Design_Light_BottomSheetDialog);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.dialog_login_sign_up);
        dialog.setCancelable(true);

        ImageView imgClose = dialog.findViewById(R.id.img_close);
        LinearLayout imgSingUp = dialog.findViewById(R.id.lout_signup);
        LinearLayout imgLogin = dialog.findViewById(R.id.lout_login);
//        CustomTextView tvSignUp = dialog.findViewById(R.id.tv_sign_up);

//        String txt1 = activity.getString(R.string.get) + " ";
//        String txt2 = "<font color='#07B825'>$10 " + activity.getString(R.string.off) + " </font>";
//        if (activity.getCurrency().equals("SAR")) {
//            txt2 = "<font color='#07B825'>10 "+activity.getString(R.string.sar) + activity.getString(R.string.off) + " </font>";
//        }
//        String txt3 = activity.getString(R.string.on_your_first_order);
//        tvSignUp.setText(Html.fromHtml(txt1 + txt2 + txt3));

        imgSingUp.setOnClickListener(v -> {
            dialog.dismiss();
            activity.openLoginScreen(false);
        });

        imgLogin.setOnClickListener(v -> {
            dialog.dismiss();
            String agentApp = "https://play.google.com/store/apps/details?id=com.nojom.stars";
            try {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(agentApp)));
            } catch (android.content.ActivityNotFoundException anfe) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(agentApp)));
            }
        });

        imgClose.setOnClickListener(v -> {
            dialog.dismiss();
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

    }
}
