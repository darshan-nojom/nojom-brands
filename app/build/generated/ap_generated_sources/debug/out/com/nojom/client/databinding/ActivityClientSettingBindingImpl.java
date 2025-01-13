package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityClientSettingBindingImpl extends ActivityClientSettingBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(17);
        sIncludes.setIncludes(0, 
            new String[] {"toolbar_title"},
            new int[] {1},
            new int[] {com.nojom.client.R.layout.toolbar_title});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rl_notifications, 2);
        sViewsWithIds.put(R.id.rl_location, 3);
        sViewsWithIds.put(R.id.rl_language, 4);
        sViewsWithIds.put(R.id.rl_currency, 5);
        sViewsWithIds.put(R.id.rl_privacy_policy, 6);
        sViewsWithIds.put(R.id.rl_terms_of_use, 7);
        sViewsWithIds.put(R.id.rl_contact_us, 8);
        sViewsWithIds.put(R.id.rl_about_us, 9);
        sViewsWithIds.put(R.id.rl_faqs, 10);
        sViewsWithIds.put(R.id.rl_about, 11);
        sViewsWithIds.put(R.id.txt_version_name, 12);
        sViewsWithIds.put(R.id.rl_share_app, 13);
        sViewsWithIds.put(R.id.rl_data_privacy, 14);
        sViewsWithIds.put(R.id.btn_signout, 15);
        sViewsWithIds.put(R.id.progress_bar_logout, 16);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityClientSettingBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private ActivityClientSettingBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.textview.CustomTextView) bindings[15]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[16]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.RelativeLayout) bindings[8]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[14]
            , (android.widget.RelativeLayout) bindings[10]
            , (android.widget.RelativeLayout) bindings[4]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.RelativeLayout) bindings[2]
            , (android.widget.RelativeLayout) bindings[6]
            , (android.widget.RelativeLayout) bindings[13]
            , (android.widget.RelativeLayout) bindings[7]
            , (com.nojom.client.databinding.ToolbarTitleBinding) bindings[1]
            , (android.textview.CustomTextView) bindings[12]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
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
                return onChangeToolbar((com.nojom.client.databinding.ToolbarTitleBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeToolbar(com.nojom.client.databinding.ToolbarTitleBinding Toolbar, int fieldId) {
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