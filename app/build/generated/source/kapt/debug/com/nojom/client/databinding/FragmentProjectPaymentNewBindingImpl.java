package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentProjectPaymentNewBindingImpl extends FragmentProjectPaymentNewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ll_project_status, 1);
        sViewsWithIds.put(R.id.lin_range, 2);
        sViewsWithIds.put(R.id.tv_total, 3);
        sViewsWithIds.put(R.id.lin_depAmnt, 4);
        sViewsWithIds.put(R.id.txt_depAmnt, 5);
        sViewsWithIds.put(R.id.lin_servAmnt, 6);
        sViewsWithIds.put(R.id.txt_servAmnt, 7);
        sViewsWithIds.put(R.id.view, 8);
        sViewsWithIds.put(R.id.lin_total, 9);
        sViewsWithIds.put(R.id.txt_totalAmnt, 10);
        sViewsWithIds.put(R.id.tv_no_deposit, 11);
        sViewsWithIds.put(R.id.ll_payment_status, 12);
        sViewsWithIds.put(R.id.lin_deposit, 13);
        sViewsWithIds.put(R.id.ll_deposit_done, 14);
        sViewsWithIds.put(R.id.imgChk, 15);
        sViewsWithIds.put(R.id.tv_deposit_done, 16);
        sViewsWithIds.put(R.id.txt_refund, 17);
        sViewsWithIds.put(R.id.txt_review, 18);
        sViewsWithIds.put(R.id.ll_release_done, 19);
        sViewsWithIds.put(R.id.tv_release_done, 20);
        sViewsWithIds.put(R.id.ll_tnc, 21);
        sViewsWithIds.put(R.id.tv_satisfied, 22);
        sViewsWithIds.put(R.id.tv_tnc, 23);
        sViewsWithIds.put(R.id.ll_bottom, 24);
        sViewsWithIds.put(R.id.tv_budget, 25);
        sViewsWithIds.put(R.id.tv_connect, 26);
        sViewsWithIds.put(R.id.rel_btn, 27);
        sViewsWithIds.put(R.id.tv_deposit_release, 28);
        sViewsWithIds.put(R.id.progress_bar, 29);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentProjectPaymentNewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 30, sIncludes, sViewsWithIds));
    }
    private FragmentProjectPaymentNewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[15]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[24]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.LinearLayout) bindings[21]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[29]
            , (android.widget.RelativeLayout) bindings[27]
            , (android.textview.CustomTextView) bindings[25]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[28]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[23]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[18]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[10]
            , (android.view.View) bindings[8]
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