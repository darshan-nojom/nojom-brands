package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityAddCardBindingImpl extends ActivityAddCardBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(24);
        sIncludes.setIncludes(1, 
            new String[] {"toolbar_title"},
            new int[] {2},
            new int[] {com.nojom.client.R.layout.toolbar_title});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.et_firstname, 3);
        sViewsWithIds.put(R.id.et_lastname, 4);
        sViewsWithIds.put(R.id.etCardNumber, 5);
        sViewsWithIds.put(R.id.etExpiry, 6);
        sViewsWithIds.put(R.id.etSecureCode, 7);
        sViewsWithIds.put(R.id.llBillingAddress, 8);
        sViewsWithIds.put(R.id.etCountryName, 9);
        sViewsWithIds.put(R.id.ccp, 10);
        sViewsWithIds.put(R.id.etBillingAddress, 11);
        sViewsWithIds.put(R.id.etSelectCity, 12);
        sViewsWithIds.put(R.id.etSelectState, 13);
        sViewsWithIds.put(R.id.etZipcode, 14);
        sViewsWithIds.put(R.id.rel_is_primary, 15);
        sViewsWithIds.put(R.id.segmentGroup, 16);
        sViewsWithIds.put(R.id.tab_no, 17);
        sViewsWithIds.put(R.id.tab_yes, 18);
        sViewsWithIds.put(R.id.loutCard, 19);
        sViewsWithIds.put(R.id.tvAddCard, 20);
        sViewsWithIds.put(R.id.progress_bar_save, 21);
        sViewsWithIds.put(R.id.tvDeleteCard, 22);
        sViewsWithIds.put(R.id.progress_bar_delete, 23);
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

    public ActivityAddCardBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }
    private ActivityAddCardBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.nojom.client.ccp.CountryCodePicker) bindings[10]
            , (android.edittext.CustomEditText) bindings[11]
            , (android.edittext.CustomEditText) bindings[5]
            , (android.edittext.CustomEditText) bindings[9]
            , (android.edittext.CustomEditText) bindings[6]
            , (android.edittext.CustomEditText) bindings[3]
            , (android.edittext.CustomEditText) bindings[4]
            , (android.edittext.CustomEditText) bindings[7]
            , (android.edittext.CustomEditText) bindings[12]
            , (android.edittext.CustomEditText) bindings[13]
            , (android.edittext.CustomEditText) bindings[14]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.RelativeLayout) bindings[19]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[23]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[21]
            , (android.widget.RelativeLayout) bindings[15]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[16]
            , (com.nojom.client.segment.SegmentedButton) bindings[17]
            , (com.nojom.client.segment.SegmentedButton) bindings[18]
            , (com.nojom.client.databinding.ToolbarTitleBinding) bindings[2]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[22]
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