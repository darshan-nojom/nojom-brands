package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityBankTransferBindingImpl extends ActivityBankTransferBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(19);
        sIncludes.setIncludes(0, 
            new String[] {"toolbar_title"},
            new int[] {1},
            new int[] {com.nojom.client.R.layout.toolbar_title});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.et_senderName, 2);
        sViewsWithIds.put(R.id.et_bankName, 3);
        sViewsWithIds.put(R.id.etCardNumber, 4);
        sViewsWithIds.put(R.id.etTxnAmount, 5);
        sViewsWithIds.put(R.id.etTxnDate, 6);
        sViewsWithIds.put(R.id.etRefNo, 7);
        sViewsWithIds.put(R.id.etNote, 8);
        sViewsWithIds.put(R.id.txt_attach, 9);
        sViewsWithIds.put(R.id.rel_selected_crn, 10);
        sViewsWithIds.put(R.id.imgFile, 11);
        sViewsWithIds.put(R.id.txt_fileName, 12);
        sViewsWithIds.put(R.id.txt_date, 13);
        sViewsWithIds.put(R.id.imgDownload, 14);
        sViewsWithIds.put(R.id.imgDelete, 15);
        sViewsWithIds.put(R.id.rel_depositNow, 16);
        sViewsWithIds.put(R.id.txt_cont, 17);
        sViewsWithIds.put(R.id.progress_bar, 18);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityBankTransferBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private ActivityBankTransferBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.edittext.CustomEditText) bindings[3]
            , (android.edittext.CustomEditText) bindings[4]
            , (android.edittext.CustomEditText) bindings[8]
            , (android.edittext.CustomEditText) bindings[7]
            , (android.edittext.CustomEditText) bindings[2]
            , (android.edittext.CustomEditText) bindings[5]
            , (android.edittext.CustomEditText) bindings[6]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[15]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[14]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[11]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[18]
            , (android.widget.RelativeLayout) bindings[16]
            , (android.widget.LinearLayout) bindings[10]
            , (com.nojom.client.databinding.ToolbarTitleBinding) bindings[1]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[12]
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