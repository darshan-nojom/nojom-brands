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

public abstract class DialogFileOpenBinding extends ViewDataBinding {
  @NonNull
  public final CustomTextView btnCancel;

  @NonNull
  public final LinearLayout llCamera;

  @NonNull
  public final LinearLayout llDocument;

  @NonNull
  public final LinearLayout llGallery;

  protected DialogFileOpenBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CustomTextView btnCancel, LinearLayout llCamera, LinearLayout llDocument,
      LinearLayout llGallery) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnCancel = btnCancel;
    this.llCamera = llCamera;
    this.llDocument = llDocument;
    this.llGallery = llGallery;
  }

  @NonNull
  public static DialogFileOpenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_file_open, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogFileOpenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogFileOpenBinding>inflateInternal(inflater, R.layout.dialog_file_open, root, attachToRoot, component);
  }

  @NonNull
  public static DialogFileOpenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_file_open, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogFileOpenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogFileOpenBinding>inflateInternal(inflater, R.layout.dialog_file_open, null, false, component);
  }

  public static DialogFileOpenBinding bind(@NonNull View view) {
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
  public static DialogFileOpenBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogFileOpenBinding)bind(component, view, R.layout.dialog_file_open);
  }
}
