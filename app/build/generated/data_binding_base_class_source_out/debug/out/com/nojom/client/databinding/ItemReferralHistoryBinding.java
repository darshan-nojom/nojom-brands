// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.nojom.client.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemReferralHistoryBinding extends ViewDataBinding {
  @NonNull
  public final CustomTextView txtDate;

  @NonNull
  public final CustomTextView txtName;

  @NonNull
  public final CustomTextView txtStatus;

  protected ItemReferralHistoryBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CustomTextView txtDate, CustomTextView txtName, CustomTextView txtStatus) {
    super(_bindingComponent, _root, _localFieldCount);
    this.txtDate = txtDate;
    this.txtName = txtName;
    this.txtStatus = txtStatus;
  }

  @NonNull
  public static ItemReferralHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_referral_history, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemReferralHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemReferralHistoryBinding>inflateInternal(inflater, R.layout.item_referral_history, root, attachToRoot, component);
  }

  @NonNull
  public static ItemReferralHistoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_referral_history, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemReferralHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemReferralHistoryBinding>inflateInternal(inflater, R.layout.item_referral_history, null, false, component);
  }

  public static ItemReferralHistoryBinding bind(@NonNull View view) {
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
  public static ItemReferralHistoryBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemReferralHistoryBinding)bind(component, view, R.layout.item_referral_history);
  }
}
