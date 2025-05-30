// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nojom.client.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemStarsPlatformBinding extends ViewDataBinding {
  @NonNull
  public final AppCompatImageView imgChk;

  @NonNull
  public final RoundedImageView imgProfile;

  @NonNull
  public final RelativeLayout loutItemView;

  @NonNull
  public final CustomTextView tvCount;

  @NonNull
  public final CustomTextView tvCurr;

  @NonNull
  public final CustomTextView tvName;

  @NonNull
  public final CustomTextView tvPrice;

  protected ItemStarsPlatformBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppCompatImageView imgChk, RoundedImageView imgProfile, RelativeLayout loutItemView,
      CustomTextView tvCount, CustomTextView tvCurr, CustomTextView tvName,
      CustomTextView tvPrice) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imgChk = imgChk;
    this.imgProfile = imgProfile;
    this.loutItemView = loutItemView;
    this.tvCount = tvCount;
    this.tvCurr = tvCurr;
    this.tvName = tvName;
    this.tvPrice = tvPrice;
  }

  @NonNull
  public static ItemStarsPlatformBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_stars_platform, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemStarsPlatformBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemStarsPlatformBinding>inflateInternal(inflater, R.layout.item_stars_platform, root, attachToRoot, component);
  }

  @NonNull
  public static ItemStarsPlatformBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_stars_platform, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemStarsPlatformBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemStarsPlatformBinding>inflateInternal(inflater, R.layout.item_stars_platform, null, false, component);
  }

  public static ItemStarsPlatformBinding bind(@NonNull View view) {
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
  public static ItemStarsPlatformBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemStarsPlatformBinding)bind(component, view, R.layout.item_stars_platform);
  }
}
