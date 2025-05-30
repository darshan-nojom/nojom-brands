package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityPartnerWithUsBindingImpl extends ActivityPartnerWithUsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(21);
        sIncludes.setIncludes(0, 
            new String[] {"toolbar_back"},
            new int[] {1},
            new int[] {com.nojom.client.R.layout.toolbar_back});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_form_msg, 2);
        sViewsWithIds.put(R.id.lin_submitted_survey, 3);
        sViewsWithIds.put(R.id.rel_application, 4);
        sViewsWithIds.put(R.id.txt_application_status, 5);
        sViewsWithIds.put(R.id.img_right_google, 6);
        sViewsWithIds.put(R.id.rel_about_me, 7);
        sViewsWithIds.put(R.id.txt_aboutme_status, 8);
        sViewsWithIds.put(R.id.img_aboutme, 9);
        sViewsWithIds.put(R.id.rel_my_profile, 10);
        sViewsWithIds.put(R.id.txt_myprofile_status, 11);
        sViewsWithIds.put(R.id.img_mp, 12);
        sViewsWithIds.put(R.id.img_linkedin, 13);
        sViewsWithIds.put(R.id.lin_survey_form, 14);
        sViewsWithIds.put(R.id.lin_questions, 15);
        sViewsWithIds.put(R.id.et_website, 16);
        sViewsWithIds.put(R.id.tv_country, 17);
        sViewsWithIds.put(R.id.tv_ages, 18);
        sViewsWithIds.put(R.id.tv_save, 19);
        sViewsWithIds.put(R.id.progress_bar, 20);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPartnerWithUsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 21, sIncludes, sViewsWithIds));
    }
    private ActivityPartnerWithUsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.edittext.CustomEditText) bindings[16]
            , (android.widget.ImageView) bindings[9]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[13]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.LinearLayout) bindings[14]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[20]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[4]
            , (android.widget.RelativeLayout) bindings[10]
            , (com.nojom.client.databinding.ToolbarBackBinding) bindings[1]
            , (android.textview.CustomTextView) bindings[18]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[11]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        setContainedBinding(this.toolbar);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        toolbar.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (toolbar.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        toolbar.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeToolbar((com.nojom.client.databinding.ToolbarBackBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeToolbar(com.nojom.client.databinding.ToolbarBackBinding Toolbar, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
        executeBindingsOn(toolbar);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): toolbar
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}