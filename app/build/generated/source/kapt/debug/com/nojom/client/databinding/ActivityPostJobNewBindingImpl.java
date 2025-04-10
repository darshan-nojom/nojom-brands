package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityPostJobNewBindingImpl extends ActivityPostJobNewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lout_platform, 1);
        sViewsWithIds.put(R.id.txt_platform, 2);
        sViewsWithIds.put(R.id.lout_service, 3);
        sViewsWithIds.put(R.id.txt_category, 4);
        sViewsWithIds.put(R.id.lout_budget, 5);
        sViewsWithIds.put(R.id.txt_budget, 6);
        sViewsWithIds.put(R.id.lout_deadline, 7);
        sViewsWithIds.put(R.id.txt_deadline, 8);
        sViewsWithIds.put(R.id.lout_describe, 9);
        sViewsWithIds.put(R.id.txt_describe, 10);
        sViewsWithIds.put(R.id.lout_send_offer, 11);
        sViewsWithIds.put(R.id.txt_send_offer, 12);
        sViewsWithIds.put(R.id.btn_post_job, 13);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPostJobNewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private ActivityPostJobNewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.textview.CustomTextView) bindings[13]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.LinearLayout) bindings[3]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[12]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
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