package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityWithdrawMoneyBindingImpl extends ActivityWithdrawMoneyBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.img_back, 1);
        sViewsWithIds.put(R.id.tv_balance, 2);
        sViewsWithIds.put(R.id.tv_remaining_balance, 3);
        sViewsWithIds.put(R.id.lin_from, 4);
        sViewsWithIds.put(R.id.txt_from, 5);
        sViewsWithIds.put(R.id.tv_available_balance, 6);
        sViewsWithIds.put(R.id.rl_paypal, 7);
        sViewsWithIds.put(R.id.tv_provider, 8);
        sViewsWithIds.put(R.id.tv_paypal_email, 9);
        sViewsWithIds.put(R.id.btn_withdraw, 10);
        sViewsWithIds.put(R.id.progress_bar, 11);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityWithdrawMoneyBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private ActivityWithdrawMoneyBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.button.CustomButton) bindings[10]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.LinearLayout) bindings[4]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[11]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[5]
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