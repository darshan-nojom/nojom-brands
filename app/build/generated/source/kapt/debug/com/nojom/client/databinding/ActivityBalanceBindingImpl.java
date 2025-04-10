package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityBalanceBindingImpl extends ActivityBalanceBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.img_back, 1);
        sViewsWithIds.put(R.id.tv_withdraw, 2);
        sViewsWithIds.put(R.id.txt_sign, 3);
        sViewsWithIds.put(R.id.tv_balance, 4);
        sViewsWithIds.put(R.id.tv_show_details, 5);
        sViewsWithIds.put(R.id.ll_show_details, 6);
        sViewsWithIds.put(R.id.tv_available_balance, 7);
        sViewsWithIds.put(R.id.tv_pending_balance, 8);
        sViewsWithIds.put(R.id.tv_total_balance, 9);
        sViewsWithIds.put(R.id.img_arrow_up, 10);
        sViewsWithIds.put(R.id.segmentedGroupTab, 11);
        sViewsWithIds.put(R.id.tab_history, 12);
        sViewsWithIds.put(R.id.tab_payment, 13);
        sViewsWithIds.put(R.id.viewpager, 14);
    }
    // views
    @NonNull
    private final androidx.core.widget.NestedScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityBalanceBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private ActivityBalanceBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[10]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.LinearLayout) bindings[6]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[11]
            , (com.nojom.client.segment.SegmentedButton) bindings[12]
            , (com.nojom.client.segment.SegmentedButton) bindings[13]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[3]
            , (androidx.viewpager.widget.ViewPager) bindings[14]
            );
        this.mboundView0 = (androidx.core.widget.NestedScrollView) bindings[0];
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