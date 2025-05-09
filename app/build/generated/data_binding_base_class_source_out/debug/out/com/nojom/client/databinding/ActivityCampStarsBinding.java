// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.button.CustomButton;
import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.nojom.client.R;
import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityCampStarsBinding extends ViewDataBinding {
  @NonNull
  public final CustomButton btnContinuePrice;

  @NonNull
  public final ImageView imgBack;

  @NonNull
  public final AppCompatImageView imgChkDeposit;

  @NonNull
  public final AppCompatImageView imgChkReleased;

  @NonNull
  public final AppCompatImageView imgFile;

  @NonNull
  public final CircleImageView imgProfile;

  @NonNull
  public final CustomTextView lblAgency;

  @NonNull
  public final CustomTextView lblDeposit;

  @NonNull
  public final CustomTextView lblRelease;

  @NonNull
  public final CustomTextView lblTax;

  @NonNull
  public final CircularProgressBar progressBar;

  @NonNull
  public final RelativeLayout relRelease;

  @NonNull
  public final RecyclerView rvPlatform;

  @NonNull
  public final CustomTextView tvAgencyFee;

  @NonNull
  public final CustomTextView tvBudget;

  @NonNull
  public final CustomTextView tvCloseProject;

  @NonNull
  public final CustomTextView tvReceiverName;

  @NonNull
  public final CustomTextView tvServiceTax;

  @NonNull
  public final CustomTextView tvStatus;

  @NonNull
  public final CustomTextView tvTotal;

  @NonNull
  public final CustomTextView tvTotalPrice;

  @NonNull
  public final CustomTextView txt1;

  @NonNull
  public final CustomTextView txtChat;

  @NonNull
  public final CustomTextView txtDate;

  @NonNull
  public final CustomTextView txtDepositAmount;

  @NonNull
  public final CustomTextView txtFileName;

  @NonNull
  public final CustomTextView txtFileSize;

  @NonNull
  public final CustomTextView txtNote;

  @NonNull
  public final CustomTextView txtPercent;

  @NonNull
  public final CustomTextView txtReleaseAmount;

  protected ActivityCampStarsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CustomButton btnContinuePrice, ImageView imgBack, AppCompatImageView imgChkDeposit,
      AppCompatImageView imgChkReleased, AppCompatImageView imgFile, CircleImageView imgProfile,
      CustomTextView lblAgency, CustomTextView lblDeposit, CustomTextView lblRelease,
      CustomTextView lblTax, CircularProgressBar progressBar, RelativeLayout relRelease,
      RecyclerView rvPlatform, CustomTextView tvAgencyFee, CustomTextView tvBudget,
      CustomTextView tvCloseProject, CustomTextView tvReceiverName, CustomTextView tvServiceTax,
      CustomTextView tvStatus, CustomTextView tvTotal, CustomTextView tvTotalPrice,
      CustomTextView txt1, CustomTextView txtChat, CustomTextView txtDate,
      CustomTextView txtDepositAmount, CustomTextView txtFileName, CustomTextView txtFileSize,
      CustomTextView txtNote, CustomTextView txtPercent, CustomTextView txtReleaseAmount) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnContinuePrice = btnContinuePrice;
    this.imgBack = imgBack;
    this.imgChkDeposit = imgChkDeposit;
    this.imgChkReleased = imgChkReleased;
    this.imgFile = imgFile;
    this.imgProfile = imgProfile;
    this.lblAgency = lblAgency;
    this.lblDeposit = lblDeposit;
    this.lblRelease = lblRelease;
    this.lblTax = lblTax;
    this.progressBar = progressBar;
    this.relRelease = relRelease;
    this.rvPlatform = rvPlatform;
    this.tvAgencyFee = tvAgencyFee;
    this.tvBudget = tvBudget;
    this.tvCloseProject = tvCloseProject;
    this.tvReceiverName = tvReceiverName;
    this.tvServiceTax = tvServiceTax;
    this.tvStatus = tvStatus;
    this.tvTotal = tvTotal;
    this.tvTotalPrice = tvTotalPrice;
    this.txt1 = txt1;
    this.txtChat = txtChat;
    this.txtDate = txtDate;
    this.txtDepositAmount = txtDepositAmount;
    this.txtFileName = txtFileName;
    this.txtFileSize = txtFileSize;
    this.txtNote = txtNote;
    this.txtPercent = txtPercent;
    this.txtReleaseAmount = txtReleaseAmount;
  }

  @NonNull
  public static ActivityCampStarsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_camp_stars, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityCampStarsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityCampStarsBinding>inflateInternal(inflater, R.layout.activity_camp_stars, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityCampStarsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_camp_stars, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityCampStarsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityCampStarsBinding>inflateInternal(inflater, R.layout.activity_camp_stars, null, false, component);
  }

  public static ActivityCampStarsBinding bind(@NonNull View view) {
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
  public static ActivityCampStarsBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityCampStarsBinding)bind(component, view, R.layout.activity_camp_stars);
  }
}
