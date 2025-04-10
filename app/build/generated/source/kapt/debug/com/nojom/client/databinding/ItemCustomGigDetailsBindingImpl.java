package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemCustomGigDetailsBindingImpl extends ItemCustomGigDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_gig_name, 1);
        sViewsWithIds.put(R.id.loutChecked, 2);
        sViewsWithIds.put(R.id.img_infom_checked, 3);
        sViewsWithIds.put(R.id.tv_custom_packages_type2, 4);
        sViewsWithIds.put(R.id.tv_custom_packages_price, 5);
        sViewsWithIds.put(R.id.img_checked, 6);
        sViewsWithIds.put(R.id.loutNumber, 7);
        sViewsWithIds.put(R.id.img_infom, 8);
        sViewsWithIds.put(R.id.img_minus, 9);
        sViewsWithIds.put(R.id.et_quantity, 10);
        sViewsWithIds.put(R.id.img_plus, 11);
        sViewsWithIds.put(R.id.loutCustomOption, 12);
        sViewsWithIds.put(R.id.img_custom_infom, 13);
        sViewsWithIds.put(R.id.img_custom_back, 14);
        sViewsWithIds.put(R.id.et_custom_price, 15);
        sViewsWithIds.put(R.id.img_custom_next, 16);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemCustomGigDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private ItemCustomGigDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[10]
            , (android.widget.CheckBox) bindings[6]
            , (android.widget.ImageView) bindings[14]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[7]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[1]
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