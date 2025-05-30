// Generated by data binding compiler. Do not edit!
package com.nojom.client.databinding;

import android.edittext.CustomEditText;
import android.textview.CustomTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.nojom.client.R;
import com.nojom.client.util.TagGroup;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentChooseSkillsBinding extends ViewDataBinding {
  @NonNull
  public final CustomEditText etSearch;

  @NonNull
  public final ToolbarProgressNextBinding progress;

  @NonNull
  public final TagGroup tagGroup;

  @NonNull
  public final CustomTextView tvSkip;

  protected FragmentChooseSkillsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      CustomEditText etSearch, ToolbarProgressNextBinding progress, TagGroup tagGroup,
      CustomTextView tvSkip) {
    super(_bindingComponent, _root, _localFieldCount);
    this.etSearch = etSearch;
    this.progress = progress;
    this.tagGroup = tagGroup;
    this.tvSkip = tvSkip;
  }

  @NonNull
  public static FragmentChooseSkillsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_choose_skills, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentChooseSkillsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentChooseSkillsBinding>inflateInternal(inflater, R.layout.fragment_choose_skills, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentChooseSkillsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_choose_skills, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentChooseSkillsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentChooseSkillsBinding>inflateInternal(inflater, R.layout.fragment_choose_skills, null, false, component);
  }

  public static FragmentChooseSkillsBinding bind(@NonNull View view) {
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
  public static FragmentChooseSkillsBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentChooseSkillsBinding)bind(component, view, R.layout.fragment_choose_skills);
  }
}
