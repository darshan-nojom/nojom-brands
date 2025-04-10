package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemOrderStarsBindingImpl extends ItemOrderStarsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.img_profile, 1);
        sViewsWithIds.put(R.id.tv_title, 2);
        sViewsWithIds.put(R.id.txt_price, 3);
        sViewsWithIds.put(R.id.txt_status, 4);
        sViewsWithIds.put(R.id.progress1, 5);
        sViewsWithIds.put(R.id.progress2, 6);
        sViewsWithIds.put(R.id.progress3, 7);
        sViewsWithIds.put(R.id.progress4, 8);
        sViewsWithIds.put(R.id.image_container, 9);
        sViewsWithIds.put(R.id.txt_orderDetail, 10);
        sViewsWithIds.put(R.id.rel_chat, 11);
        sViewsWithIds.put(R.id.txt_profile, 12);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemOrderStarsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }
    private ItemOrderStarsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[9]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.ProgressBar) bindings[5]
            , (android.widget.ProgressBar) bindings[6]
            , (android.widget.ProgressBar) bindings[7]
            , (android.widget.ProgressBar) bindings[8]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[4]
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