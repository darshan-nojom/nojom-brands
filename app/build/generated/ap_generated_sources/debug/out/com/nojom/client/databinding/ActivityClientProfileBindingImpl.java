package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityClientProfileBindingImpl extends ActivityClientProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(25);
        sIncludes.setIncludes(1, 
            new String[] {"toolbar_save"},
            new int[] {2},
            new int[] {com.nojom.client.R.layout.toolbar_save});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.et_CompName, 3);
        sViewsWithIds.put(R.id.et_brandName, 4);
        sViewsWithIds.put(R.id.et_contName, 5);
        sViewsWithIds.put(R.id.et_firstname, 6);
        sViewsWithIds.put(R.id.et_lastname, 7);
        sViewsWithIds.put(R.id.et_email, 8);
        sViewsWithIds.put(R.id.tv_phone_prefix, 9);
        sViewsWithIds.put(R.id.et_mobile, 10);
        sViewsWithIds.put(R.id.ccp, 11);
        sViewsWithIds.put(R.id.et_crNo, 12);
        sViewsWithIds.put(R.id.rel_selected_crn, 13);
        sViewsWithIds.put(R.id.imgFile, 14);
        sViewsWithIds.put(R.id.txt_fileNameCrn, 15);
        sViewsWithIds.put(R.id.txt_date, 16);
        sViewsWithIds.put(R.id.imgView, 17);
        sViewsWithIds.put(R.id.et_vatNo, 18);
        sViewsWithIds.put(R.id.rel_selected_vat, 19);
        sViewsWithIds.put(R.id.imgFileVat, 20);
        sViewsWithIds.put(R.id.txt_fileNameVat, 21);
        sViewsWithIds.put(R.id.txt_dateVat, 22);
        sViewsWithIds.put(R.id.imgViewVat, 23);
        sViewsWithIds.put(R.id.tv_changepassword, 24);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityClientProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 25, sIncludes, sViewsWithIds));
    }
    private ActivityClientProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.nojom.client.ccp.CountryCodePicker) bindings[11]
            , (android.edittext.CustomEditText) bindings[4]
            , (android.edittext.CustomEditText) bindings[3]
            , (android.edittext.CustomEditText) bindings[5]
            , (android.edittext.CustomEditText) bindings[12]
            , (android.edittext.CustomEditText) bindings[8]
            , (android.edittext.CustomEditText) bindings[6]
            , (android.edittext.CustomEditText) bindings[7]
            , (android.edittext.CustomEditText) bindings[10]
            , (android.edittext.CustomEditText) bindings[18]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[14]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[20]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[17]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[23]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.LinearLayout) bindings[19]
            , (com.nojom.client.databinding.ToolbarSaveBinding) bindings[2]
            , (android.textview.CustomTextView) bindings[24]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[21]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
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