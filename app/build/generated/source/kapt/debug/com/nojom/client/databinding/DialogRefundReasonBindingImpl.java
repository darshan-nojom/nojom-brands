package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DialogRefundReasonBindingImpl extends DialogRefundReasonBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.radioGroup, 1);
        sViewsWithIds.put(R.id.rb_not_satisfied, 2);
        sViewsWithIds.put(R.id.rb_deadline, 3);
        sViewsWithIds.put(R.id.rb_other, 4);
        sViewsWithIds.put(R.id.edit_reason, 5);
        sViewsWithIds.put(R.id.txt1, 6);
        sViewsWithIds.put(R.id.txt2, 7);
        sViewsWithIds.put(R.id.tv_cancel, 8);
        sViewsWithIds.put(R.id.tv_confirm, 9);
        sViewsWithIds.put(R.id.progress_bar, 10);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DialogRefundReasonBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private DialogRefundReasonBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.edittext.CustomEditText) bindings[5]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[10]
            , (android.widget.RadioGroup) bindings[1]
            , (android.widget.RadioButton) bindings[3]
            , (android.widget.RadioButton) bindings[2]
            , (android.widget.RadioButton) bindings[4]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[7]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}