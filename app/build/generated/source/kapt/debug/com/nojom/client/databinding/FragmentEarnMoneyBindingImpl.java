package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentEarnMoneyBindingImpl extends FragmentEarnMoneyBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_here_blue, 1);
        sViewsWithIds.put(R.id.earn_money_title, 2);
        sViewsWithIds.put(R.id.txt_ref_link, 3);
        sViewsWithIds.put(R.id.txt_copy, 4);
        sViewsWithIds.put(R.id.lin_msg, 5);
        sViewsWithIds.put(R.id.txt_text, 6);
        sViewsWithIds.put(R.id.lin_email, 7);
        sViewsWithIds.put(R.id.txt_email, 8);
        sViewsWithIds.put(R.id.lin_more, 9);
        sViewsWithIds.put(R.id.txt_more, 10);
        sViewsWithIds.put(R.id.tv_how_it_works, 11);
        sViewsWithIds.put(R.id.rel_referral, 12);
        sViewsWithIds.put(R.id.txt_history, 13);
        sViewsWithIds.put(R.id.txt_amount, 14);
        sViewsWithIds.put(R.id.rv_referral, 15);
        sViewsWithIds.put(R.id.txt_blue_label, 16);
        sViewsWithIds.put(R.id.txt_t1, 17);
        sViewsWithIds.put(R.id.txt_silver_label, 18);
        sViewsWithIds.put(R.id.txt_t2, 19);
        sViewsWithIds.put(R.id.txt_t3, 20);
        sViewsWithIds.put(R.id.txt_t4, 21);
        sViewsWithIds.put(R.id.tv_terms_of_use, 22);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentEarnMoneyBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds));
    }
    private FragmentEarnMoneyBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.textview.CustomTextView) bindings[2]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[12]
            , (androidx.recyclerview.widget.RecyclerView) bindings[15]
            , (android.widget.ScrollView) bindings[0]
            , (android.textview.CustomTextView) bindings[1]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[18]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[21]
            , (android.textview.CustomTextView) bindings[6]
            );
        this.scrollview.setTag(null);
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