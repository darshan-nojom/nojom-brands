package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityPrivateInfoBindingImpl extends ActivityPrivateInfoBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(13);
        sIncludes.setIncludes(1, 
            new String[] {"toolbar_save"},
            new int[] {2},
            new int[] {com.nojom.client.R.layout.toolbar_save});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.img_profile, 3);
        sViewsWithIds.put(R.id.et_firstname, 4);
        sViewsWithIds.put(R.id.et_lastname, 5);
        sViewsWithIds.put(R.id.et_username, 6);
        sViewsWithIds.put(R.id.et_email, 7);
        sViewsWithIds.put(R.id.tv_phone_prefix, 8);
        sViewsWithIds.put(R.id.et_mobile, 9);
        sViewsWithIds.put(R.id.ccp, 10);
        sViewsWithIds.put(R.id.txt_pass_title, 11);
        sViewsWithIds.put(R.id.tv_changepassword, 12);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final android.widget.RelativeLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPrivateInfoBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }
    private ActivityPrivateInfoBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.nojom.client.ccp.CountryCodePicker) bindings[10]
            , (android.edittext.CustomEditText) bindings[7]
            , (android.edittext.CustomEditText) bindings[4]
            , (android.edittext.CustomEditText) bindings[5]
            , (android.edittext.CustomEditText) bindings[9]
            , (android.textview.CustomTextView) bindings[6]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[3]
            , (com.nojom.client.databinding.ToolbarSaveBinding) bindings[2]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[11]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.RelativeLayout) bindings[1];
        this.mboundView1.setTag(null);
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
                return onChangeToolbar((com.nojom.client.databinding.ToolbarSaveBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeToolbar(com.nojom.client.databinding.ToolbarSaveBinding Toolbar, int fieldId) {
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