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

public abstract class DialogRefundPaymentBinding extends ViewDataBinding {
  @NonNull
  public final CustomTextView tvCancel;

  @NonNull
  public final CustomTextView tvConfirm;

  @NonNull
  public final CustomTextView tvUsername;

  protected DialogRefundPaymentBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CustomTextView tvCancel, CustomTextView tvConfirm, CustomTextView tvUsername) {
    super(_bindingComponent, _root, _localFieldCount);
    this.tvCancel = tvCancel;
    this.tvConfirm = tvConfirm;
    this.tvUsername = tvUsername;
  }

  @NonNull
  public static DialogRefundPaymentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_refund_payment, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogRefundPaymentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogRefundPaymentBinding>inflateInternal(inflater, R.layout.dialog_refund_payment, root, attachToRoot, component);
  }

  @NonNull
  public static DialogRefundPaymentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_refund_payment, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogRefundPaymentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogRefundPaymentBinding>inflateInternal(inflater, R.layout.dialog_refund_payment, null, false, component);
  }

  public static DialogRefundPaymentBinding bind(@NonNull View view) {
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
  public static DialogRefundPaymentBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogRefundPaymentBinding)bind(component, view, R.layout.dialog_refund_payment);
  }
}
