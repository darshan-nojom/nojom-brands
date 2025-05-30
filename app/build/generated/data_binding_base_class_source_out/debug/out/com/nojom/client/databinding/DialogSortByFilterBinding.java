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
import androidx.recyclerview.widget.RecyclerView;
import com.nojom.client.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DialogSortByFilterBinding extends ViewDataBinding {
  @NonNull
  public final RecyclerView rvSortByFilter;

  @NonNull
  public final CustomTextView txtDone;

  protected DialogSortByFilterBinding(Object _bindingComponent, View _root, int _localFieldCount,
      RecyclerView rvSortByFilter, CustomTextView txtDone) {
    super(_bindingComponent, _root, _localFieldCount);
    this.rvSortByFilter = rvSortByFilter;
    this.txtDone = txtDone;
  }

  @NonNull
  public static DialogSortByFilterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_sort_by_filter, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DialogSortByFilterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DialogSortByFilterBinding>inflateInternal(inflater, R.layout.dialog_sort_by_filter, root, attachToRoot, component);
  }

  @NonNull
  public static DialogSortByFilterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.dialog_sort_by_filter, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DialogSortByFilterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DialogSortByFilterBinding>inflateInternal(inflater, R.layout.dialog_sort_by_filter, null, false, component);
  }

  public static DialogSortByFilterBinding bind(@NonNull View view) {
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
  public static DialogSortByFilterBinding bind(@NonNull View view, @Nullable Object component) {
    return (DialogSortByFilterBinding)bind(component, view, R.layout.dialog_sort_by_filter);
  }
}
