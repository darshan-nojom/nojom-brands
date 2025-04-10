package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityDepositFundsCopyBindingImpl extends ActivityDepositFundsCopyBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.img_back, 1);
        sViewsWithIds.put(R.id.segmentGroup, 2);
        sViewsWithIds.put(R.id.sbCard, 3);
        sViewsWithIds.put(R.id.sbPayPal, 4);
        sViewsWithIds.put(R.id.sbGooglePay, 5);
        sViewsWithIds.put(R.id.sbVenmo, 6);
        sViewsWithIds.put(R.id.sbBt, 7);
        sViewsWithIds.put(R.id.viewpager, 8);
        sViewsWithIds.put(R.id.fragment_container, 9);
        sViewsWithIds.put(R.id.rlDepositBottom, 10);
        sViewsWithIds.put(R.id.tv_budget, 11);
        sViewsWithIds.put(R.id.tv_pay, 12);
        sViewsWithIds.put(R.id.progress_bar, 13);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDepositFundsCopyBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private ActivityDepositFundsCopyBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.FrameLayout) bindings[9]
            , (android.widget.ImageView) bindings[1]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[13]
            , (android.widget.LinearLayout) bindings[10]
            , (com.nojom.client.segment.SegmentedButton) bindings[7]
            , (com.nojom.client.segment.SegmentedButton) bindings[3]
            , (com.nojom.client.segment.SegmentedButton) bindings[5]
            , (com.nojom.client.segment.SegmentedButton) bindings[4]
            , (com.nojom.client.segment.SegmentedButton) bindings[6]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[2]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[12]
            , (androidx.viewpager.widget.ViewPager) bindings[8]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
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