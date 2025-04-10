package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityHireBindingImpl extends ActivityHireBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_user_name, 1);
        sViewsWithIds.put(R.id.tv_bid_price, 2);
        sViewsWithIds.put(R.id.tv_price_type, 3);
        sViewsWithIds.put(R.id.relImg, 4);
        sViewsWithIds.put(R.id.img_profile, 5);
        sViewsWithIds.put(R.id.et_amount, 6);
        sViewsWithIds.put(R.id.tv_currency, 7);
        sViewsWithIds.put(R.id.tv_bid_amount_fee, 8);
        sViewsWithIds.put(R.id.et_recAmount, 9);
        sViewsWithIds.put(R.id.tv_currency_rec, 10);
        sViewsWithIds.put(R.id.tv_hiw, 11);
        sViewsWithIds.put(R.id.ll_bottom, 12);
        sViewsWithIds.put(R.id.tv_cancel, 13);
        sViewsWithIds.put(R.id.tv_confirm_hire, 14);
        sViewsWithIds.put(R.id.progress_bar, 15);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityHireBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private ActivityHireBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.edittext.CustomEditText) bindings[6]
            , (android.edittext.CustomEditText) bindings[9]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[5]
            , (android.widget.LinearLayout) bindings[12]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[15]
            , (android.widget.RelativeLayout) bindings[4]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[1]
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