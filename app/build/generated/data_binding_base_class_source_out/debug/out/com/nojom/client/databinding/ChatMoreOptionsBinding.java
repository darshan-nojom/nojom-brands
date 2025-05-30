// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.nojom.client.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ChatMoreOptionsBinding extends ViewDataBinding {
  @NonNull
  public final CustomTextView lblCall;

  @NonNull
  public final CustomTextView lblEmail;

  @NonNull
  public final CustomTextView lblMessenger;

  @NonNull
  public final CustomTextView lblSms;

  @NonNull
  public final CustomTextView lblWhatsapp;

  @NonNull
  public final RelativeLayout rlCall;

  @NonNull
  public final RelativeLayout rlEmail;

  @NonNull
  public final RelativeLayout rlMessanger;

  @NonNull
  public final RelativeLayout rlSms;

  @NonNull
  public final RelativeLayout rlWhatsapp;

  protected ChatMoreOptionsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CustomTextView lblCall, CustomTextView lblEmail, CustomTextView lblMessenger,
      CustomTextView lblSms, CustomTextView lblWhatsapp, RelativeLayout rlCall,
      RelativeLayout rlEmail, RelativeLayout rlMessanger, RelativeLayout rlSms,
      RelativeLayout rlWhatsapp) {
    super(_bindingComponent, _root, _localFieldCount);
    this.lblCall = lblCall;
    this.lblEmail = lblEmail;
    this.lblMessenger = lblMessenger;
    this.lblSms = lblSms;
    this.lblWhatsapp = lblWhatsapp;
    this.rlCall = rlCall;
    this.rlEmail = rlEmail;
    this.rlMessanger = rlMessanger;
    this.rlSms = rlSms;
    this.rlWhatsapp = rlWhatsapp;
  }

  @NonNull
  public static ChatMoreOptionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.chat_more_options, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ChatMoreOptionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ChatMoreOptionsBinding>inflateInternal(inflater, R.layout.chat_more_options, root, attachToRoot, component);
  }

  @NonNull
  public static ChatMoreOptionsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.chat_more_options, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ChatMoreOptionsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ChatMoreOptionsBinding>inflateInternal(inflater, R.layout.chat_more_options, null, false, component);
  }

  public static ChatMoreOptionsBinding bind(@NonNull View view) {
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
  public static ChatMoreOptionsBinding bind(@NonNull View view, @Nullable Object component) {
    return (ChatMoreOptionsBinding)bind(component, view, R.layout.chat_more_options);
  }
}
