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

public abstract class ItemTagViewBinding extends ViewDataBinding {
  @NonNull
  public final CustomTextView txtTagList;

  protected ItemTagViewBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CustomTextView txtTagList) {
    super(_bindingComponent, _root, _localFieldCount);
    this.txtTagList = txtTagList;
  }

  @NonNull
  public static ItemTagViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_tag_view, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemTagViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemTagViewBinding>inflateInternal(inflater, R.layout.item_tag_view, root, attachToRoot, component);
  }

  @NonNull
  public static ItemTagViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_tag_view, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemTagViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemTagViewBinding>inflateInternal(inflater, R.layout.item_tag_view, null, false, component);
  }

  public static ItemTagViewBinding bind(@NonNull View view) {
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
  public static ItemTagViewBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemTagViewBinding)bind(component, view, R.layout.item_tag_view);
  }
}
