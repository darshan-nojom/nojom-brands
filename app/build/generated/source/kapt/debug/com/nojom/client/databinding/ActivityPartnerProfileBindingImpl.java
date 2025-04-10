package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityPartnerProfileBindingImpl extends ActivityPartnerProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(16);
        sIncludes.setIncludes(0, 
            new String[] {"toolbar_back"},
            new int[] {1},
            new int[] {com.nojom.client.R.layout.toolbar_back});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lin_submitted_survey, 2);
        sViewsWithIds.put(R.id.rel_application, 3);
        sViewsWithIds.put(R.id.txt_application_status, 4);
        sViewsWithIds.put(R.id.img_right_google, 5);
        sViewsWithIds.put(R.id.rel_about_me, 6);
        sViewsWithIds.put(R.id.txt_aboutme_status, 7);
        sViewsWithIds.put(R.id.img_aboutme, 8);
        sViewsWithIds.put(R.id.rel_my_profile, 9);
        sViewsWithIds.put(R.id.txt_myprofile_status, 10);
        sViewsWithIds.put(R.id.img_mp, 11);
        sViewsWithIds.put(R.id.tv_profile_complete, 12);
        sViewsWithIds.put(R.id.rel_complete_profile, 13);
        sViewsWithIds.put(R.id.tv_save, 14);
        sViewsWithIds.put(R.id.txt_laststep, 15);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPartnerProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private ActivityPartnerProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.RelativeLayout) bindings[6]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.RelativeLayout) bindings[13]
            , (android.widget.RelativeLayout) bindings[9]
            , (com.nojom.client.databinding.ToolbarBackBinding) bindings[1]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[10]
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