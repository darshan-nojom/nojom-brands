package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentSelectRateBindingImpl extends FragmentSelectRateBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ll_progress, 2);
        sViewsWithIds.put(R.id.progress_view, 3);
        sViewsWithIds.put(R.id.blank_view, 4);
        sViewsWithIds.put(R.id.header, 5);
        sViewsWithIds.put(R.id.img_back, 6);
        sViewsWithIds.put(R.id.txt_title, 7);
        sViewsWithIds.put(R.id.txt_learn_more, 8);
        sViewsWithIds.put(R.id.img_learn_more, 9);
        sViewsWithIds.put(R.id.txt_learn_more_desc, 10);
        sViewsWithIds.put(R.id.rv_rates, 11);
        sViewsWithIds.put(R.id.shimmer_layout, 12);
        sViewsWithIds.put(R.id.txt_or, 13);
        sViewsWithIds.put(R.id.tv_enter_price, 14);
        sViewsWithIds.put(R.id.txt_or_not_sure, 15);
        sViewsWithIds.put(R.id.rel_not_sure, 16);
        sViewsWithIds.put(R.id.tv_not_sure, 17);
        sViewsWithIds.put(R.id.view_not_sure, 18);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentSelectRateBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private FragmentSelectRateBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.view.View) bindings[4]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.LinearLayout) bindings[2]
            , (android.view.View) bindings[3]
            , (android.widget.RelativeLayout) bindings[16]
            , (androidx.recyclerview.widget.RecyclerView) bindings[11]
            , (com.facebook.shimmer.ShimmerFrameLayout) bindings[12]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[7]
            , (android.view.View) bindings[18]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
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