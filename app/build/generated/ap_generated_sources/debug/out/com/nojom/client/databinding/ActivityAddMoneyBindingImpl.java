package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityAddMoneyBindingImpl extends ActivityAddMoneyBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(28);
        sIncludes.setIncludes(0, 
            new String[] {"toolbar_title"},
            new int[] {1},
            new int[] {com.nojom.client.R.layout.toolbar_title});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rel_google, 2);
        sViewsWithIds.put(R.id.imgGoogle, 3);
        sViewsWithIds.put(R.id.txt_wallet, 4);
        sViewsWithIds.put(R.id.txt_walletAmount, 5);
        sViewsWithIds.put(R.id.txt_job_id, 6);
        sViewsWithIds.put(R.id.txt_depAmount, 7);
        sViewsWithIds.put(R.id.txt_lbl_serv_amnt, 8);
        sViewsWithIds.put(R.id.txt_servAmount, 9);
        sViewsWithIds.put(R.id.rel_promoCode, 10);
        sViewsWithIds.put(R.id.txt_promoCode, 11);
        sViewsWithIds.put(R.id.rel_redeem, 12);
        sViewsWithIds.put(R.id.txt_redeem, 13);
        sViewsWithIds.put(R.id.txt_total, 14);
        sViewsWithIds.put(R.id.rel_title, 15);
        sViewsWithIds.put(R.id.rel_name, 16);
        sViewsWithIds.put(R.id.txt_copy_name, 17);
        sViewsWithIds.put(R.id.txt_lbl_name, 18);
        sViewsWithIds.put(R.id.txt_recName, 19);
        sViewsWithIds.put(R.id.rel_accNo, 20);
        sViewsWithIds.put(R.id.txt_copy_acc, 21);
        sViewsWithIds.put(R.id.txt_lbl_acc, 22);
        sViewsWithIds.put(R.id.txt_accNo, 23);
        sViewsWithIds.put(R.id.txt_copy_iban, 24);
        sViewsWithIds.put(R.id.txt_lbl_iban, 25);
        sViewsWithIds.put(R.id.txt_ibanNo, 26);
        sViewsWithIds.put(R.id.rel_depositNow, 27);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAddMoneyBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 28, sIncludes, sViewsWithIds));
    }
    private ActivityAddMoneyBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (androidx.appcompat.widget.AppCompatImageView) bindings[3]
            , (android.widget.RelativeLayout) bindings[20]
            , (android.widget.RelativeLayout) bindings[27]
            , (android.widget.RelativeLayout) bindings[2]
            , (android.widget.RelativeLayout) bindings[16]
            , (android.widget.RelativeLayout) bindings[10]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[15]
            , (com.nojom.client.databinding.ToolbarTitleBinding) bindings[1]
            , (android.textview.CustomTextView) bindings[23]
            , (android.textview.CustomTextView) bindings[21]
            , (android.textview.CustomTextView) bindings[24]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[25]
            , (android.textview.CustomTextView) bindings[18]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[5]
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