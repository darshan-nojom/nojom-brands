// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.nojom.client.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemUploadedFilesBinding extends ViewDataBinding {
  @NonNull
  public final ImageView imgDelete;

  @NonNull
  public final AppCompatImageView imgFile;

  @NonNull
  public final CustomTextView tvFileName;

  protected ItemUploadedFilesBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView imgDelete, AppCompatImageView imgFile, CustomTextView tvFileName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imgDelete = imgDelete;
    this.imgFile = imgFile;
    this.tvFileName = tvFileName;
  }

  @NonNull
  public static ItemUploadedFilesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_uploaded_files, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemUploadedFilesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemUploadedFilesBinding>inflateInternal(inflater, R.layout.item_uploaded_files, root, attachToRoot, component);
  }

  @NonNull
  public static ItemUploadedFilesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_uploaded_files, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemUploadedFilesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemUploadedFilesBinding>inflateInternal(inflater, R.layout.item_uploaded_files, null, false, component);
  }

  public static ItemUploadedFilesBinding bind(@NonNull View view) {
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
  public static ItemUploadedFilesBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemUploadedFilesBinding)bind(component, view, R.layout.item_uploaded_files);
  }
}
