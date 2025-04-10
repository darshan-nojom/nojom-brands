package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ViewOverviewBindingImpl extends ViewOverviewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.txt_name, 1);
        sViewsWithIds.put(R.id.title_about, 2);
        sViewsWithIds.put(R.id.tv_aboutme, 3);
        sViewsWithIds.put(R.id.viewAbout, 4);
        sViewsWithIds.put(R.id.txt_cat_title, 5);
        sViewsWithIds.put(R.id.chipView, 6);
        sViewsWithIds.put(R.id.txt_tag_title, 7);
        sViewsWithIds.put(R.id.chipViewTags, 8);
        sViewsWithIds.put(R.id.txt_price_title, 9);
        sViewsWithIds.put(R.id.tv_priceRange, 10);
        sViewsWithIds.put(R.id.txt_maw_title, 11);
        sViewsWithIds.put(R.id.tv_mawId, 12);
        sViewsWithIds.put(R.id.linAge, 13);
        sViewsWithIds.put(R.id.txt_age_title, 14);
        sViewsWithIds.put(R.id.tv_age, 15);
        sViewsWithIds.put(R.id.txt_gen_title, 16);
        sViewsWithIds.put(R.id.tv_gender, 17);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ViewOverviewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }
    private ViewOverviewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.recyclerview.widget.RecyclerView) bindings[6]
            , (androidx.recyclerview.widget.RecyclerView) bindings[8]
            , (android.widget.LinearLayout) bindings[13]
            , (android.textview.CustomTextView) bindings[2]
            , (com.nojom.client.util.ReadMoreTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[1]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[7]
            , (android.view.View) bindings[4]
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