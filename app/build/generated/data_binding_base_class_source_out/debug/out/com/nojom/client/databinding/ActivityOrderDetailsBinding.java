// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.nojom.client.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityOrderDetailsBinding extends ViewDataBinding {
  @NonNull
  public final AppCompatImageView imgApproved;

  @NonNull
  public final ImageView imgBack;

  @NonNull
  public final AppCompatImageView imgComplete;

  @NonNull
  public final AppCompatImageView imgDeliver;

  @NonNull
  public final AppCompatImageView imgFile;

  @NonNull
  public final AppCompatImageView imgReq;

  @NonNull
  public final CustomTextView lblAgency;

  @NonNull
  public final CustomTextView lblTax;

  @NonNull
  public final LinearLayout linTimeline;

  @NonNull
  public final ProgressBar progress1;

  @NonNull
  public final ProgressBar progress2;

  @NonNull
  public final ProgressBar progress3;

  @NonNull
  public final ProgressBar progress4;

  @NonNull
  public final ProgressBar progress5;

  @NonNull
  public final ProgressBar progress6;

  @NonNull
  public final RecyclerView rvStars;

  @NonNull
  public final CustomTextView tvAgencyFee;

  @NonNull
  public final CustomTextView tvCloseProject;

  @NonNull
  public final CustomTextView tvDeadline;

  @NonNull
  public final CustomTextView tvDetails;

  @NonNull
  public final CustomTextView tvJobId;

  @NonNull
  public final CustomTextView tvJobTitle;

  @NonNull
  public final CustomTextView tvServiceTax;

  @NonNull
  public final CustomTextView tvTime;

  @NonNull
  public final CustomTextView tvTotal;

  @NonNull
  public final CustomTextView tvTotalPrice;

  @NonNull
  public final CustomTextView txtApproved;

  @NonNull
  public final CustomTextView txtCompleted;

  @NonNull
  public final CustomTextView txtDelivered;

  @NonNull
  public final CustomTextView txtFileName;

  @NonNull
  public final CustomTextView txtRequested;

  @NonNull
  public final CustomTextView txtSteps;

  protected ActivityOrderDetailsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppCompatImageView imgApproved, ImageView imgBack, AppCompatImageView imgComplete,
      AppCompatImageView imgDeliver, AppCompatImageView imgFile, AppCompatImageView imgReq,
      CustomTextView lblAgency, CustomTextView lblTax, LinearLayout linTimeline,
      ProgressBar progress1, ProgressBar progress2, ProgressBar progress3, ProgressBar progress4,
      ProgressBar progress5, ProgressBar progress6, RecyclerView rvStars,
      CustomTextView tvAgencyFee, CustomTextView tvCloseProject, CustomTextView tvDeadline,
      CustomTextView tvDetails, CustomTextView tvJobId, CustomTextView tvJobTitle,
      CustomTextView tvServiceTax, CustomTextView tvTime, CustomTextView tvTotal,
      CustomTextView tvTotalPrice, CustomTextView txtApproved, CustomTextView txtCompleted,
      CustomTextView txtDelivered, CustomTextView txtFileName, CustomTextView txtRequested,
      CustomTextView txtSteps) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imgApproved = imgApproved;
    this.imgBack = imgBack;
    this.imgComplete = imgComplete;
    this.imgDeliver = imgDeliver;
    this.imgFile = imgFile;
    this.imgReq = imgReq;
    this.lblAgency = lblAgency;
    this.lblTax = lblTax;
    this.linTimeline = linTimeline;
    this.progress1 = progress1;
    this.progress2 = progress2;
    this.progress3 = progress3;
    this.progress4 = progress4;
    this.progress5 = progress5;
    this.progress6 = progress6;
    this.rvStars = rvStars;
    this.tvAgencyFee = tvAgencyFee;
    this.tvCloseProject = tvCloseProject;
    this.tvDeadline = tvDeadline;
    this.tvDetails = tvDetails;
    this.tvJobId = tvJobId;
    this.tvJobTitle = tvJobTitle;
    this.tvServiceTax = tvServiceTax;
    this.tvTime = tvTime;
    this.tvTotal = tvTotal;
    this.tvTotalPrice = tvTotalPrice;
    this.txtApproved = txtApproved;
    this.txtCompleted = txtCompleted;
    this.txtDelivered = txtDelivered;
    this.txtFileName = txtFileName;
    this.txtRequested = txtRequested;
    this.txtSteps = txtSteps;
  }

  @NonNull
  public static ActivityOrderDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_order_details, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityOrderDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityOrderDetailsBinding>inflateInternal(inflater, R.layout.activity_order_details, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityOrderDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_order_details, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityOrderDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityOrderDetailsBinding>inflateInternal(inflater, R.layout.activity_order_details, null, false, component);
  }

  public static ActivityOrderDetailsBinding bind(@NonNull View view) {
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
  public static ActivityOrderDetailsBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityOrderDetailsBinding)bind(component, view, R.layout.activity_order_details);
  }
}
