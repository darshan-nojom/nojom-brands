// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.nojom.client.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentBalanceDepositBinding extends ViewDataBinding {
  @NonNull
  public final NestedScrollView nestedScroll;

  @NonNull
  public final NoDataLayoutBinding noData;

  @NonNull
  public final RecyclerView rvIncome;

  @NonNull
  public final ShimmerFrameLayout shimmerLayout;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshLayout;

  @NonNull
  public final CustomTextView tvDepositMoney;

  protected FragmentBalanceDepositBinding(Object _bindingComponent, View _root,
      int _localFieldCount, NestedScrollView nestedScroll, NoDataLayoutBinding noData,
      RecyclerView rvIncome, ShimmerFrameLayout shimmerLayout,
      SwipeRefreshLayout swipeRefreshLayout, CustomTextView tvDepositMoney) {
    super(_bindingComponent, _root, _localFieldCount);
    this.nestedScroll = nestedScroll;
    this.noData = noData;
    this.rvIncome = rvIncome;
    this.shimmerLayout = shimmerLayout;
    this.swipeRefreshLayout = swipeRefreshLayout;
    this.tvDepositMoney = tvDepositMoney;
  }

  @NonNull
  public static FragmentBalanceDepositBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_balance_deposit, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBalanceDepositBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentBalanceDepositBinding>inflateInternal(inflater, R.layout.fragment_balance_deposit, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentBalanceDepositBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_balance_deposit, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentBalanceDepositBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentBalanceDepositBinding>inflateInternal(inflater, R.layout.fragment_balance_deposit, null, false, component);
  }

  public static FragmentBalanceDepositBinding bind(@NonNull View view) {
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
  public static FragmentBalanceDepositBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentBalanceDepositBinding)bind(component, view, R.layout.fragment_balance_deposit);
  }
}
