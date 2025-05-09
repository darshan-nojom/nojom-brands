// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.nojom.client.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ToolbarProgressNextBinding extends ViewDataBinding {
  @NonNull
  public final RelativeLayout header;

  @NonNull
  public final ImageView imgBack;

  @NonNull
  public final ProgressBar progress;

  @NonNull
  public final CustomTextView tvNext;

  protected ToolbarProgressNextBinding(Object _bindingComponent, View _root, int _localFieldCount,
      RelativeLayout header, ImageView imgBack, ProgressBar progress, CustomTextView tvNext) {
    super(_bindingComponent, _root, _localFieldCount);
    this.header = header;
    this.imgBack = imgBack;
    this.progress = progress;
    this.tvNext = tvNext;
  }

  @NonNull
  public static ToolbarProgressNextBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.toolbar_progress_next, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ToolbarProgressNextBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ToolbarProgressNextBinding>inflateInternal(inflater, R.layout.toolbar_progress_next, root, attachToRoot, component);
  }

  @NonNull
  public static ToolbarProgressNextBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.toolbar_progress_next, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ToolbarProgressNextBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ToolbarProgressNextBinding>inflateInternal(inflater, R.layout.toolbar_progress_next, null, false, component);
  }

  public static ToolbarProgressNextBinding bind(@NonNull View view) {
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
  public static ToolbarProgressNextBinding bind(@NonNull View view, @Nullable Object component) {
    return (ToolbarProgressNextBinding)bind(component, view, R.layout.toolbar_progress_next);
  }
}
