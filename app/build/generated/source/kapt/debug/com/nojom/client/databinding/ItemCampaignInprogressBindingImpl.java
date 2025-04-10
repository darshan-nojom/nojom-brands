package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemCampaignInprogressBindingImpl extends ItemCampaignInprogressBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.image_container, 1);
        sViewsWithIds.put(R.id.tv_date, 2);
        sViewsWithIds.put(R.id.tv_receiver_name, 3);
        sViewsWithIds.put(R.id.txt_price, 4);
        sViewsWithIds.put(R.id.txt_paid, 5);
        sViewsWithIds.put(R.id.txt_agents, 6);
        sViewsWithIds.put(R.id.linDate, 7);
        sViewsWithIds.put(R.id.txt_date, 8);
        sViewsWithIds.put(R.id.txt_percent, 9);
        sViewsWithIds.put(R.id.txt_status, 10);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemCampaignInprogressBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private ItemCampaignInprogressBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[0]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[10]
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