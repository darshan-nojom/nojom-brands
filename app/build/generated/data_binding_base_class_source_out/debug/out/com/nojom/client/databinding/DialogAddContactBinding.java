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

public abstract class DialogAddContactBinding extends ViewDataBinding {
  @NonNull
  public final CustomTextView tvCancel;

  @NonNull
  public final CustomTextView tvMessage;

  @NonNull
  public final CustomTextView tvTitle;

  @NonNull
  public final CustomTextView tvYes;

  protected DialogAddContactBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CustomTextView tvCancel, CustomTextView tvMessage, CustomTextView tvTitle,
      CustomTextView tvYes) {
    super(_bindingComponent, _root, _localFieldCount);
    this.tvCancel = tvCancel;
    this.tvMessage = tvMessage;
    this.tvTitle = tvTitle;
    this.tvYes = tvYes;
  }

  @NonNull
  public static DialogAddContactBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_add_contact, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogAddContactBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogAddContactBinding>inflateInternal(inflater, R.layout.dialog_add_contact, root, attachToRoot, component);
  }

  @NonNull
  public static DialogAddContactBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_add_contact, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogAddContactBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogAddContactBinding>inflateInternal(inflater, R.layout.dialog_add_contact, null, false, component);
  }

  public static DialogAddContactBinding bind(@NonNull View view) {
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
  public static DialogAddContactBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogAddContactBinding)bind(component, view, R.layout.dialog_add_contact);
  }
}
