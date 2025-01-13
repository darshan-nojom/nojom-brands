package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemHomeBindingImpl extends ItemHomeBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lout_item_view, 1);
        sViewsWithIds.put(R.id.img_gig, 2);
        sViewsWithIds.put(R.id.img_favourite, 3);
        sViewsWithIds.put(R.id.tv_name, 4);
        sViewsWithIds.put(R.id.tv_category, 5);
        sViewsWithIds.put(R.id.rv_platform, 6);
        sViewsWithIds.put(R.id.tv_price, 7);
        sViewsWithIds.put(R.id.tv_ads, 8);
        sViewsWithIds.put(R.id.progress_bar, 9);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemHomeBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private ItemHomeBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[3]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[2]
            , (androidx.cardview.widget.CardView) bindings[0]
            , (android.widget.RelativeLayout) bindings[1]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[9]
            , (androidx.recyclerview.widget.RecyclerView) bindings[6]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[7]
            );
        this.loutGig.setTag(null);
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