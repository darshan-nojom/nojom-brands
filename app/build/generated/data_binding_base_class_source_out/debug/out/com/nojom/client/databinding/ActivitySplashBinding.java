// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.nojom.client.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivitySplashBinding extends ViewDataBinding {
  @NonNull
  public final AppCompatImageView imgLogo;

  @NonNull
  public final AppCompatImageView imgText;

  protected ActivitySplashBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppCompatImageView imgLogo, AppCompatImageView imgText) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imgLogo = imgLogo;
    this.imgText = imgText;
  }

  @NonNull
  public static ActivitySplashBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_splash, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivitySplashBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivitySplashBinding>inflateInternal(inflater, R.layout.activity_splash, root, attachToRoot, component);
  }

  @NonNull
  public static ActivitySplashBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_splash, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivitySplashBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivitySplashBinding>inflateInternal(inflater, R.layout.activity_splash, null, false, component);
  }

  public static ActivitySplashBinding bind(@NonNull View view) {
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
  public static ActivitySplashBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivitySplashBinding)bind(component, view, R.layout.activity_splash);
  }
}
