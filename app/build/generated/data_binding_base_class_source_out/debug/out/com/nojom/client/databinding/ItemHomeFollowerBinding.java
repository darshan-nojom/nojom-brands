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
import com.makeramen.roundedimageview.RoundedImageView;
import com.nojom.client.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemHomeFollowerBinding extends ViewDataBinding {
  @NonNull
  public final RoundedImageView imgLogo;

  @NonNull
  public final CustomTextView txtName;

  protected ItemHomeFollowerBinding(Object _bindingComponent, View _root, int _localFieldCount,
      RoundedImageView imgLogo, CustomTextView txtName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imgLogo = imgLogo;
    this.txtName = txtName;
  }

  @NonNull
  public static ItemHomeFollowerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_home_follower, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemHomeFollowerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemHomeFollowerBinding>inflateInternal(inflater, R.layout.item_home_follower, root, attachToRoot, component);
  }

  @NonNull
  public static ItemHomeFollowerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_home_follower, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemHomeFollowerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemHomeFollowerBinding>inflateInternal(inflater, R.layout.item_home_follower, null, false, component);
  }

  public static ItemHomeFollowerBinding bind(@NonNull View view) {
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
  public static ItemHomeFollowerBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemHomeFollowerBinding)bind(component, view, R.layout.item_home_follower);
  }
}
