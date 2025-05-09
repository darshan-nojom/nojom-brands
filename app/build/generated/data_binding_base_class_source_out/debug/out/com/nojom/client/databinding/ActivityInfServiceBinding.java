// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.button.CustomButton;
import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.nojom.client.R;
import com.nojom.client.util.ReadMoreTextView;
import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityInfServiceBinding extends ViewDataBinding {
  @NonNull
  public final Toolbar animToolbar;

  @NonNull
  public final CustomButton btnContinuePrice;

  @NonNull
  public final AppCompatEditText etNotes;

  @NonNull
  public final LinearLayout imageContainer;

  @NonNull
  public final ImageView imgBack;

  @NonNull
  public final AppCompatImageView imgMaw;

  @NonNull
  public final CircleImageView imgProfile;

  @NonNull
  public final ImageView imgVerified;

  @NonNull
  public final LinearLayout linPreview;

  @NonNull
  public final LinearLayout linView;

  @NonNull
  public final LinearLayout linearCustom;

  @NonNull
  public final LinearLayout loutHeader;

  @NonNull
  public final CircularProgressBar progressBar;

  @NonNull
  public final RelativeLayout relContinue;

  @NonNull
  public final RelativeLayout relName;

  @NonNull
  public final RelativeLayout relative;

  @NonNull
  public final RelativeLayout rlHeader;

  @NonNull
  public final RecyclerView rvMedia;

  @NonNull
  public final LinearLayout scroll;

  @NonNull
  public final CustomTextView toolbarTitle;

  @NonNull
  public final ReadMoreTextView tvAboutme;

  @NonNull
  public final CustomTextView tvName;

  @NonNull
  public final CustomTextView txtAddMore;

  @NonNull
  public final CustomTextView txtMawNo;

  @NonNull
  public final CustomTextView txtOffer;

  @NonNull
  public final CustomTextView txtPerc;

  @NonNull
  public final CustomTextView txtTax;

  @NonNull
  public final CustomTextView txtTitleMaw;

  protected ActivityInfServiceBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Toolbar animToolbar, CustomButton btnContinuePrice, AppCompatEditText etNotes,
      LinearLayout imageContainer, ImageView imgBack, AppCompatImageView imgMaw,
      CircleImageView imgProfile, ImageView imgVerified, LinearLayout linPreview,
      LinearLayout linView, LinearLayout linearCustom, LinearLayout loutHeader,
      CircularProgressBar progressBar, RelativeLayout relContinue, RelativeLayout relName,
      RelativeLayout relative, RelativeLayout rlHeader, RecyclerView rvMedia, LinearLayout scroll,
      CustomTextView toolbarTitle, ReadMoreTextView tvAboutme, CustomTextView tvName,
      CustomTextView txtAddMore, CustomTextView txtMawNo, CustomTextView txtOffer,
      CustomTextView txtPerc, CustomTextView txtTax, CustomTextView txtTitleMaw) {
    super(_bindingComponent, _root, _localFieldCount);
    this.animToolbar = animToolbar;
    this.btnContinuePrice = btnContinuePrice;
    this.etNotes = etNotes;
    this.imageContainer = imageContainer;
    this.imgBack = imgBack;
    this.imgMaw = imgMaw;
    this.imgProfile = imgProfile;
    this.imgVerified = imgVerified;
    this.linPreview = linPreview;
    this.linView = linView;
    this.linearCustom = linearCustom;
    this.loutHeader = loutHeader;
    this.progressBar = progressBar;
    this.relContinue = relContinue;
    this.relName = relName;
    this.relative = relative;
    this.rlHeader = rlHeader;
    this.rvMedia = rvMedia;
    this.scroll = scroll;
    this.toolbarTitle = toolbarTitle;
    this.tvAboutme = tvAboutme;
    this.tvName = tvName;
    this.txtAddMore = txtAddMore;
    this.txtMawNo = txtMawNo;
    this.txtOffer = txtOffer;
    this.txtPerc = txtPerc;
    this.txtTax = txtTax;
    this.txtTitleMaw = txtTitleMaw;
  }

  @NonNull
  public static ActivityInfServiceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_inf_service, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityInfServiceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityInfServiceBinding>inflateInternal(inflater, R.layout.activity_inf_service, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityInfServiceBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_inf_service, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityInfServiceBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityInfServiceBinding>inflateInternal(inflater, R.layout.activity_inf_service, null, false, component);
  }

  public static ActivityInfServiceBinding bind(@NonNull View view) {
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
  public static ActivityInfServiceBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityInfServiceBinding)bind(component, view, R.layout.activity_inf_service);
  }
}
