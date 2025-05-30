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
import com.willy.ratingbar.ScaleRatingBar;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemInfReviewsBinding extends ViewDataBinding {
  @NonNull
  public final ScaleRatingBar ratingbar;

  @NonNull
  public final CustomTextView tvDate;

  @NonNull
  public final CustomTextView tvProjectName;

  @NonNull
  public final CustomTextView tvRating;

  @NonNull
  public final CustomTextView tvReview;

  protected ItemInfReviewsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ScaleRatingBar ratingbar, CustomTextView tvDate, CustomTextView tvProjectName,
      CustomTextView tvRating, CustomTextView tvReview) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ratingbar = ratingbar;
    this.tvDate = tvDate;
    this.tvProjectName = tvProjectName;
    this.tvRating = tvRating;
    this.tvReview = tvReview;
  }

  @NonNull
  public static ItemInfReviewsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_inf_reviews, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemInfReviewsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemInfReviewsBinding>inflateInternal(inflater, R.layout.item_inf_reviews, root, attachToRoot, component);
  }

  @NonNull
  public static ItemInfReviewsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_inf_reviews, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemInfReviewsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemInfReviewsBinding>inflateInternal(inflater, R.layout.item_inf_reviews, null, false, component);
  }

  public static ItemInfReviewsBinding bind(@NonNull View view) {
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
  public static ItemInfReviewsBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemInfReviewsBinding)bind(component, view, R.layout.item_inf_reviews);
  }
}
