// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.edittext.CustomEditText;
import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.nojom.client.R;
import com.nojom.client.ccp.CountryCodePicker;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityPrivateInfoBinding extends ViewDataBinding {
  @NonNull
  public final CountryCodePicker ccp;

  @NonNull
  public final CustomEditText etEmail;

  @NonNull
  public final CustomEditText etFirstname;

  @NonNull
  public final CustomEditText etLastname;

  @NonNull
  public final CustomEditText etMobile;

  @NonNull
  public final CustomTextView etUsername;

  @NonNull
  public final CircleImageView imgProfile;

  @NonNull
  public final ToolbarSaveBinding toolbar;

  @NonNull
  public final CustomTextView tvChangepassword;

  @NonNull
  public final CustomTextView tvPhonePrefix;

  @NonNull
  public final CustomTextView txtPassTitle;

  protected ActivityPrivateInfoBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CountryCodePicker ccp, CustomEditText etEmail, CustomEditText etFirstname,
      CustomEditText etLastname, CustomEditText etMobile, CustomTextView etUsername,
      CircleImageView imgProfile, ToolbarSaveBinding toolbar, CustomTextView tvChangepassword,
      CustomTextView tvPhonePrefix, CustomTextView txtPassTitle) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ccp = ccp;
    this.etEmail = etEmail;
    this.etFirstname = etFirstname;
    this.etLastname = etLastname;
    this.etMobile = etMobile;
    this.etUsername = etUsername;
    this.imgProfile = imgProfile;
    this.toolbar = toolbar;
    this.tvChangepassword = tvChangepassword;
    this.tvPhonePrefix = tvPhonePrefix;
    this.txtPassTitle = txtPassTitle;
  }

  @NonNull
  public static ActivityPrivateInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_private_info, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityPrivateInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityPrivateInfoBinding>inflateInternal(inflater, R.layout.activity_private_info, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityPrivateInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_private_info, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityPrivateInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityPrivateInfoBinding>inflateInternal(inflater, R.layout.activity_private_info, null, false, component);
  }

  public static ActivityPrivateInfoBinding bind(@NonNull View view) {
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
  public static ActivityPrivateInfoBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityPrivateInfoBinding)bind(component, view, R.layout.activity_private_info);
  }
}
