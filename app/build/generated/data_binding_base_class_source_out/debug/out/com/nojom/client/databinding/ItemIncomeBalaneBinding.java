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

public abstract class ItemIncomeBalaneBinding extends ViewDataBinding {
  @NonNull
  public final CustomTextView tvBalance;

  @NonNull
  public final CustomTextView tvDate;

  @NonNull
  public final CustomTextView tvJob;

  @NonNull
  public final CustomTextView tvJobId;

  @NonNull
  public final CustomTextView tvStatus;

  protected ItemIncomeBalaneBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CustomTextView tvBalance, CustomTextView tvDate, CustomTextView tvJob, CustomTextView tvJobId,
      CustomTextView tvStatus) {
    super(_bindingComponent, _root, _localFieldCount);
    this.tvBalance = tvBalance;
    this.tvDate = tvDate;
    this.tvJob = tvJob;
    this.tvJobId = tvJobId;
    this.tvStatus = tvStatus;
  }

  @NonNull
  public static ItemIncomeBalaneBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_income_balane, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemIncomeBalaneBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemIncomeBalaneBinding>inflateInternal(inflater, R.layout.item_income_balane, root, attachToRoot, component);
  }

  @NonNull
  public static ItemIncomeBalaneBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_income_balane, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemIncomeBalaneBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemIncomeBalaneBinding>inflateInternal(inflater, R.layout.item_income_balane, null, false, component);
  }

  public static ItemIncomeBalaneBinding bind(@NonNull View view) {
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
  public static ItemIncomeBalaneBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemIncomeBalaneBinding)bind(component, view, R.layout.item_income_balane);
  }
}
