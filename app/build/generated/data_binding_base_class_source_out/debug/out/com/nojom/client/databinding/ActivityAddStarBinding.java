// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.button.CustomButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.nojom.client.R;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityAddStarBinding extends ViewDataBinding {
  @NonNull
  public final CustomButton btnContinuePrice;

  @NonNull
  public final ImageView btnFilter;

  @NonNull
  public final EditText etSearch;

  @NonNull
  public final AppCompatImageView imgBack;

  @NonNull
  public final AppCompatImageView imgPreview;

  @NonNull
  public final CircularProgressBar progressBar;

  @NonNull
  public final RelativeLayout relContinue;

  @NonNull
  public final RelativeLayout relHeaderLogin;

  @NonNull
  public final RecyclerView rvBestInf;

  protected ActivityAddStarBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CustomButton btnContinuePrice, ImageView btnFilter, EditText etSearch,
      AppCompatImageView imgBack, AppCompatImageView imgPreview, CircularProgressBar progressBar,
      RelativeLayout relContinue, RelativeLayout relHeaderLogin, RecyclerView rvBestInf) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnContinuePrice = btnContinuePrice;
    this.btnFilter = btnFilter;
    this.etSearch = etSearch;
    this.imgBack = imgBack;
    this.imgPreview = imgPreview;
    this.progressBar = progressBar;
    this.relContinue = relContinue;
    this.relHeaderLogin = relHeaderLogin;
    this.rvBestInf = rvBestInf;
  }

  @NonNull
  public static ActivityAddStarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_add_star, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityAddStarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityAddStarBinding>inflateInternal(inflater, R.layout.activity_add_star, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityAddStarBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_add_star, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityAddStarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityAddStarBinding>inflateInternal(inflater, R.layout.activity_add_star, null, false, component);
  }

  public static ActivityAddStarBinding bind(@NonNull View view) {
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
  public static ActivityAddStarBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityAddStarBinding)bind(component, view, R.layout.activity_add_star);
  }
}
