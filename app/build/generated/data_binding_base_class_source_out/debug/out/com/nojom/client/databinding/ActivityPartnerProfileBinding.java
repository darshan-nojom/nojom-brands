// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.nojom.client.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityPartnerProfileBinding extends ViewDataBinding {
  @NonNull
  public final ImageView imgAboutme;

  @NonNull
  public final ImageView imgMp;

  @NonNull
  public final ImageView imgRightGoogle;

  @NonNull
  public final LinearLayout linSubmittedSurvey;

  @NonNull
  public final RelativeLayout relAboutMe;

  @NonNull
  public final RelativeLayout relApplication;

  @NonNull
  public final RelativeLayout relCompleteProfile;

  @NonNull
  public final RelativeLayout relMyProfile;

  @NonNull
  public final ToolbarBackBinding toolbar;

  @NonNull
  public final CustomTextView tvProfileComplete;

  @NonNull
  public final CustomTextView tvSave;

  @NonNull
  public final CustomTextView txtAboutmeStatus;

  @NonNull
  public final CustomTextView txtApplicationStatus;

  @NonNull
  public final CustomTextView txtLaststep;

  @NonNull
  public final CustomTextView txtMyprofileStatus;

  protected ActivityPartnerProfileBinding(Object _bindingComponent, View _root,
      int _localFieldCount, ImageView imgAboutme, ImageView imgMp, ImageView imgRightGoogle,
      LinearLayout linSubmittedSurvey, RelativeLayout relAboutMe, RelativeLayout relApplication,
      RelativeLayout relCompleteProfile, RelativeLayout relMyProfile, ToolbarBackBinding toolbar,
      CustomTextView tvProfileComplete, CustomTextView tvSave, CustomTextView txtAboutmeStatus,
      CustomTextView txtApplicationStatus, CustomTextView txtLaststep,
      CustomTextView txtMyprofileStatus) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imgAboutme = imgAboutme;
    this.imgMp = imgMp;
    this.imgRightGoogle = imgRightGoogle;
    this.linSubmittedSurvey = linSubmittedSurvey;
    this.relAboutMe = relAboutMe;
    this.relApplication = relApplication;
    this.relCompleteProfile = relCompleteProfile;
    this.relMyProfile = relMyProfile;
    this.toolbar = toolbar;
    this.tvProfileComplete = tvProfileComplete;
    this.tvSave = tvSave;
    this.txtAboutmeStatus = txtAboutmeStatus;
    this.txtApplicationStatus = txtApplicationStatus;
    this.txtLaststep = txtLaststep;
    this.txtMyprofileStatus = txtMyprofileStatus;
  }

  @NonNull
  public static ActivityPartnerProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_partner_profile, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityPartnerProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityPartnerProfileBinding>inflateInternal(inflater, R.layout.activity_partner_profile, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityPartnerProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_partner_profile, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityPartnerProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityPartnerProfileBinding>inflateInternal(inflater, R.layout.activity_partner_profile, null, false, component);
  }

  public static ActivityPartnerProfileBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivityPartnerProfileBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityPartnerProfileBinding)bind(component, view, R.layout.activity_partner_profile);
  }
}
