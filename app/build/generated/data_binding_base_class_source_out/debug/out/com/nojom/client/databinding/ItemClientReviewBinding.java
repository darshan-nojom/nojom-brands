// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.edittext.CustomEditText;
import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.nojom.client.R;
import com.nojom.client.segment.SegmentedButton;
import com.nojom.client.segment.SegmentedButtonGroup;
import com.willy.ratingbar.ScaleRatingBar;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemClientReviewBinding extends ViewDataBinding {
  @NonNull
  public final CustomEditText etComment;

  @NonNull
  public final ScaleRatingBar ratingbar;

  @NonNull
  public final RelativeLayout rlQuestion;

  @NonNull
  public final SegmentedButton sbNo;

  @NonNull
  public final SegmentedButton sbYes;

  @NonNull
  public final SegmentedButtonGroup segmentGroup;

  @NonNull
  public final CustomTextView tvQuestion;

  protected ItemClientReviewBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CustomEditText etComment, ScaleRatingBar ratingbar, RelativeLayout rlQuestion,
      SegmentedButton sbNo, SegmentedButton sbYes, SegmentedButtonGroup segmentGroup,
      CustomTextView tvQuestion) {
    super(_bindingComponent, _root, _localFieldCount);
    this.etComment = etComment;
    this.ratingbar = ratingbar;
    this.rlQuestion = rlQuestion;
    this.sbNo = sbNo;
    this.sbYes = sbYes;
    this.segmentGroup = segmentGroup;
    this.tvQuestion = tvQuestion;
  }

  @NonNull
  public static ItemClientReviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_client_review, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemClientReviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemClientReviewBinding>inflateInternal(inflater, R.layout.item_client_review, root, attachToRoot, component);
  }

  @NonNull
  public static ItemClientReviewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_client_review, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemClientReviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemClientReviewBinding>inflateInternal(inflater, R.layout.item_client_review, null, false, component);
  }

  public static ItemClientReviewBinding bind(@NonNull View view) {
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
  public static ItemClientReviewBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemClientReviewBinding)bind(component, view, R.layout.item_client_review);
  }
}
