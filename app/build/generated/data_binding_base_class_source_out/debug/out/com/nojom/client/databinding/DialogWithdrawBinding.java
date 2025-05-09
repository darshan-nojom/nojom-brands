// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.nojom.client.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogWithdrawBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout llDeposit;

  @NonNull
  public final CustomTextView tvCancelWithdraw;

  @NonNull
  public final CustomTextView tvDate;

  @NonNull
  public final CustomTextView tvEmail;

  @NonNull
  public final CustomTextView tvOk;

  @NonNull
  public final CustomTextView tvProvider;

  @NonNull
  public final CustomTextView tvStatus;

  @NonNull
  public final CustomTextView tvTotal;

  protected DialogWithdrawBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout llDeposit, CustomTextView tvCancelWithdraw, CustomTextView tvDate,
      CustomTextView tvEmail, CustomTextView tvOk, CustomTextView tvProvider,
      CustomTextView tvStatus, CustomTextView tvTotal) {
    super(_bindingComponent, _root, _localFieldCount);
    this.llDeposit = llDeposit;
    this.tvCancelWithdraw = tvCancelWithdraw;
    this.tvDate = tvDate;
    this.tvEmail = tvEmail;
    this.tvOk = tvOk;
    this.tvProvider = tvProvider;
    this.tvStatus = tvStatus;
    this.tvTotal = tvTotal;
  }

  @NonNull
  public static DialogWithdrawBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_withdraw, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogWithdrawBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogWithdrawBinding>inflateInternal(inflater, R.layout.dialog_withdraw, root, attachToRoot, component);
  }

  @NonNull
  public static DialogWithdrawBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_withdraw, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogWithdrawBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogWithdrawBinding>inflateInternal(inflater, R.layout.dialog_withdraw, null, false, component);
  }

  public static DialogWithdrawBinding bind(@NonNull View view) {
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
  public static DialogWithdrawBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogWithdrawBinding)bind(component, view, R.layout.dialog_withdraw);
  }
}
