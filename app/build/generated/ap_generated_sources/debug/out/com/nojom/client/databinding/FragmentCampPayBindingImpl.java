package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentCampPayBindingImpl extends FragmentCampPayBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_total, 1);
        sViewsWithIds.put(R.id.lbl_agency, 2);
        sViewsWithIds.put(R.id.tv_agencyFee, 3);
        sViewsWithIds.put(R.id.lbl_tax, 4);
        sViewsWithIds.put(R.id.tv_serviceTax, 5);
        sViewsWithIds.put(R.id.tv_totalPrice, 6);
        sViewsWithIds.put(R.id.lbl_deposit, 7);
        sViewsWithIds.put(R.id.txt_deposit_amount, 8);
        sViewsWithIds.put(R.id.img_chk_deposit, 9);
        sViewsWithIds.put(R.id.lbl_release, 10);
        sViewsWithIds.put(R.id.txt_release_amount, 11);
        sViewsWithIds.put(R.id.img_chk_released, 12);
        sViewsWithIds.put(R.id.rel_release, 13);
        sViewsWithIds.put(R.id.btn_release, 14);
        sViewsWithIds.put(R.id.progress_bar_signup, 15);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentCampPayBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private FragmentCampPayBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.textview.CustomTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[9]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[12]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[4]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[15]
            , (android.widget.RelativeLayout) bindings[13]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[1]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[11]
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