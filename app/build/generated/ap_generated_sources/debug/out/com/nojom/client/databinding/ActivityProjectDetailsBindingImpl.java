package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityProjectDetailsBindingImpl extends ActivityProjectDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.img_back, 1);
        sViewsWithIds.put(R.id.tv_close_project, 2);
        sViewsWithIds.put(R.id.segmentGroupBidding, 3);
        sViewsWithIds.put(R.id.segmentWaitingDeposit, 4);
        sViewsWithIds.put(R.id.segmentGroupProgress, 5);
        sViewsWithIds.put(R.id.segmentSubmitPayment, 6);
        sViewsWithIds.put(R.id.segmentGroupComplete, 7);
        sViewsWithIds.put(R.id.ll_tab, 8);
        sViewsWithIds.put(R.id.tabs, 9);
        sViewsWithIds.put(R.id.viewpager, 10);
        sViewsWithIds.put(R.id.tv_no_data, 11);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityProjectDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private ActivityProjectDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[1]
            , (android.widget.LinearLayout) bindings[8]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[3]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[7]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[5]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[6]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[4]
            , (com.google.android.material.tabs.TabLayout) bindings[9]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[11]
            , (androidx.viewpager.widget.ViewPager) bindings[10]
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