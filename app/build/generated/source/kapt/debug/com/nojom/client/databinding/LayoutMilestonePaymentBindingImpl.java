package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutMilestonePaymentBindingImpl extends LayoutMilestonePaymentBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_user_name, 1);
        sViewsWithIds.put(R.id.ll_budget, 2);
        sViewsWithIds.put(R.id.tv_bid_price, 3);
        sViewsWithIds.put(R.id.tv_price_type, 4);
        sViewsWithIds.put(R.id.relImg, 5);
        sViewsWithIds.put(R.id.img_profile, 6);
        sViewsWithIds.put(R.id.ll_project_status, 7);
        sViewsWithIds.put(R.id.lin_depAmnt, 8);
        sViewsWithIds.put(R.id.txt_depAmnt, 9);
        sViewsWithIds.put(R.id.lin_servAmnt, 10);
        sViewsWithIds.put(R.id.txt_servAmnt, 11);
        sViewsWithIds.put(R.id.view, 12);
        sViewsWithIds.put(R.id.lin_total, 13);
        sViewsWithIds.put(R.id.txt_totalAmnt, 14);
        sViewsWithIds.put(R.id.tv_milestone, 15);
        sViewsWithIds.put(R.id.ll_deposit_done, 16);
        sViewsWithIds.put(R.id.tv_deposit_amount, 17);
        sViewsWithIds.put(R.id.tv_release_amount, 18);
        sViewsWithIds.put(R.id.tv_satisfied, 19);
        sViewsWithIds.put(R.id.tv_tnc, 20);
    }
    // views
    @NonNull
    private final androidx.core.widget.NestedScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutMilestonePaymentBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 21, sIncludes, sViewsWithIds));
    }
    private LayoutMilestonePaymentBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[6]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[18]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[1]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[14]
            , (android.view.View) bindings[12]
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