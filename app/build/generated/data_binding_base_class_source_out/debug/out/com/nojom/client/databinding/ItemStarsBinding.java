// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.nojom.client.R;
import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemStarsBinding extends ViewDataBinding {
  @NonNull
  public final AppCompatEditText etNotes;

  @NonNull
  public final CircleImageView imgGig;

  @NonNull
  public final CardView loutGig;

  @NonNull
  public final RelativeLayout loutItemView;

  @NonNull
  public final CircularProgressBar progressBar;

  @NonNull
  public final RecyclerView rvPlatform;

  @NonNull
  public final CustomTextView tvDesc;

  @NonNull
  public final CustomTextView tvName;

  @NonNull
  public final CustomTextView txtProfile;

  protected ItemStarsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppCompatEditText etNotes, CircleImageView imgGig, CardView loutGig,
      RelativeLayout loutItemView, CircularProgressBar progressBar, RecyclerView rvPlatform,
      CustomTextView tvDesc, CustomTextView tvName, CustomTextView txtProfile) {
    super(_bindingComponent, _root, _localFieldCount);
    this.etNotes = etNotes;
    this.imgGig = imgGig;
    this.loutGig = loutGig;
    this.loutItemView = loutItemView;
    this.progressBar = progressBar;
    this.rvPlatform = rvPlatform;
    this.tvDesc = tvDesc;
    this.tvName = tvName;
    this.txtProfile = txtProfile;
  }

  @NonNull
  public static ItemStarsBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_stars, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemStarsBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemStarsBinding>inflateInternal(inflater, R.layout.item_stars, root, attachToRoot, component);
  }

  @NonNull
  public static ItemStarsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_stars, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemStarsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemStarsBinding>inflateInternal(inflater, R.layout.item_stars, null, false, component);
  }

  public static ItemStarsBinding bind(@NonNull View view) {
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
  public static ItemStarsBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemStarsBinding)bind(component, view, R.layout.item_stars);
  }
}
