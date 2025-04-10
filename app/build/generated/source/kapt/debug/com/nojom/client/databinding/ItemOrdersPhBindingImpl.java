package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemOrdersPhBindingImpl extends ItemOrdersPhBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_title, 1);
        sViewsWithIds.put(R.id.txt_price, 2);
        sViewsWithIds.put(R.id.txt_status, 3);
        sViewsWithIds.put(R.id.progress1, 4);
        sViewsWithIds.put(R.id.progress2, 5);
        sViewsWithIds.put(R.id.progress3, 6);
        sViewsWithIds.put(R.id.progress4, 7);
        sViewsWithIds.put(R.id.image_container, 8);
        sViewsWithIds.put(R.id.txt_date, 9);
        sViewsWithIds.put(R.id.txt_camp_id, 10);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemOrdersPhBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private ItemOrdersPhBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.ProgressBar) bindings[4]
            , (android.widget.ProgressBar) bindings[5]
            , (android.widget.ProgressBar) bindings[6]
            , (android.widget.ProgressBar) bindings[7]
            , (android.textview.CustomTextView) bindings[1]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[3]
            );
        this.loutHeader.setTag(null);
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