package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentReviewsProfileBindingImpl extends FragmentReviewsProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(20);
        sIncludes.setIncludes(0, 
            new String[] {"no_data_layout"},
            new int[] {3},
            new int[] {com.nojom.client.R.layout.no_data_layout});
        sIncludes.setIncludes(1, 
            new String[] {"item_reviews_ph", "item_reviews_ph", "item_reviews_ph", "item_reviews_ph", "item_reviews_ph", "item_reviews_ph"},
            new int[] {4, 5, 6, 7, 8, 9},
            new int[] {com.nojom.client.R.layout.item_reviews_ph,
                com.nojom.client.R.layout.item_reviews_ph,
                com.nojom.client.R.layout.item_reviews_ph,
                com.nojom.client.R.layout.item_reviews_ph,
                com.nojom.client.R.layout.item_reviews_ph,
                com.nojom.client.R.layout.item_reviews_ph});
        sIncludes.setIncludes(2, 
            new String[] {"item_portfolio_list_ph", "item_portfolio_list_ph", "item_portfolio_list_ph", "item_portfolio_list_ph"},
            new int[] {10, 11, 12, 13},
            new int[] {com.nojom.client.R.layout.item_portfolio_list_ph,
                com.nojom.client.R.layout.item_portfolio_list_ph,
                com.nojom.client.R.layout.item_portfolio_list_ph,
                com.nojom.client.R.layout.item_portfolio_list_ph});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rel_title, 14);
        sViewsWithIds.put(R.id.txt_reviews, 15);
        sViewsWithIds.put(R.id.txt_review_count, 16);
        sViewsWithIds.put(R.id.rv_reviews, 17);
        sViewsWithIds.put(R.id.shimmer_layout_review, 18);
        sViewsWithIds.put(R.id.shimmer_layout, 19);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    @Nullable
    private final com.nojom.client.databinding.ItemReviewsPhBinding mboundView11;
    @Nullable
    private final com.nojom.client.databinding.ItemReviewsPhBinding mboundView12;
    @Nullable
    private final com.nojom.client.databinding.ItemReviewsPhBinding mboundView13;
    @Nullable
    private final com.nojom.client.databinding.ItemReviewsPhBinding mboundView14;
    @Nullable
    private final com.nojom.client.databinding.ItemReviewsPhBinding mboundView15;
    @Nullable
    private final com.nojom.client.databinding.ItemReviewsPhBinding mboundView16;
    @NonNull
    private final android.widget.LinearLayout mboundView2;
    @Nullable
    private final com.nojom.client.databinding.ItemPortfolioListPhBinding mboundView21;
    @Nullable
    private final com.nojom.client.databinding.ItemPortfolioListPhBinding mboundView22;
    @Nullable
    private final com.nojom.client.databinding.ItemPortfolioListPhBinding mboundView23;
    @Nullable
    private final com.nojom.client.databinding.ItemPortfolioListPhBinding mboundView24;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentReviewsProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds));
    }
    private FragmentReviewsProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.nojom.client.databinding.NoDataLayoutBinding) bindings[3]
            , (android.widget.RelativeLayout) bindings[14]
            , (androidx.recyclerview.widget.RecyclerView) bindings[17]
            , (com.facebook.shimmer.ShimmerFrameLayout) bindings[19]
            , (com.facebook.shimmer.ShimmerFrameLayout) bindings[18]
            , (android.textview.CustomTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[15]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView11 = (com.nojom.client.databinding.ItemReviewsPhBinding) bindings[4];
        setContainedBinding(this.mboundView11);
        this.mboundView12 = (com.nojom.client.databinding.ItemReviewsPhBinding) bindings[5];
        setContainedBinding(this.mboundView12);
        this.mboundView13 = (com.nojom.client.databinding.ItemReviewsPhBinding) bindings[6];
        setContainedBinding(this.mboundView13);
        this.mboundView14 = (com.nojom.client.databinding.ItemReviewsPhBinding) bindings[7];
        setContainedBinding(this.mboundView14);
        this.mboundView15 = (com.nojom.client.databinding.ItemReviewsPhBinding) bindings[8];
        setContainedBinding(this.mboundView15);
        this.mboundView16 = (com.nojom.client.databinding.ItemReviewsPhBinding) bindings[9];
        setContainedBinding(this.mboundView16);
        this.mboundView2 = (android.widget.LinearLayout) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView21 = (com.nojom.client.databinding.ItemPortfolioListPhBinding) bindings[10];
        setContainedBinding(this.mboundView21);
        this.mboundView22 = (com.nojom.client.databinding.ItemPortfolioListPhBinding) bindings[11];
        setContainedBinding(this.mboundView22);
        this.mboundView23 = (com.nojom.client.databinding.ItemPortfolioListPhBinding) bindings[12];
        setContainedBinding(this.mboundView23);
        this.mboundView24 = (com.nojom.client.databinding.ItemPortfolioListPhBinding) bindings[13];
        setContainedBinding(this.mboundView24);
        setContainedBinding(this.noData);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        noData.invalidateAll();
        mboundView11.invalidateAll();
        mboundView12.invalidateAll();
        mboundView13.invalidateAll();
        mboundView14.invalidateAll();
        mboundView15.invalidateAll();
        mboundView16.invalidateAll();
        mboundView21.invalidateAll();
        mboundView22.invalidateAll();
        mboundView23.invalidateAll();
        mboundView24.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (noData.hasPendingBindings()) {
            return true;
        }
        if (mboundView11.hasPendingBindings()) {
            return true;
        }
        if (mboundView12.hasPendingBindings()) {
            return true;
        }
        if (mboundView13.hasPendingBindings()) {
            return true;
        }
        if (mboundView14.hasPendingBindings()) {
            return true;
        }
        if (mboundView15.hasPendingBindings()) {
            return true;
        }
        if (mboundView16.hasPendingBindings()) {
            return true;
        }
        if (mboundView21.hasPendingBindings()) {
            return true;
        }
        if (mboundView22.hasPendingBindings()) {
            return true;
        }
        if (mboundView23.hasPendingBindings()) {
            return true;
        }
        if (mboundView24.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        noData.setLifecycleOwner(lifecycleOwner);
        mboundView11.setLifecycleOwner(lifecycleOwner);
        mboundView12.setLifecycleOwner(lifecycleOwner);
        mboundView13.setLifecycleOwner(lifecycleOwner);
        mboundView14.setLifecycleOwner(lifecycleOwner);
        mboundView15.setLifecycleOwner(lifecycleOwner);
        mboundView16.setLifecycleOwner(lifecycleOwner);
        mboundView21.setLifecycleOwner(lifecycleOwner);
        mboundView22.setLifecycleOwner(lifecycleOwner);
        mboundView23.setLifecycleOwner(lifecycleOwner);
        mboundView24.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeNoData((com.nojom.client.databinding.NoDataLayoutBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeNoData(com.nojom.client.databinding.NoDataLayoutBinding NoData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
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
        executeBindingsOn(noData);
        executeBindingsOn(mboundView11);
        executeBindingsOn(mboundView12);
        executeBindingsOn(mboundView13);
        executeBindingsOn(mboundView14);
        executeBindingsOn(mboundView15);
        executeBindingsOn(mboundView16);
        executeBindingsOn(mboundView21);
        executeBindingsOn(mboundView22);
        executeBindingsOn(mboundView23);
        executeBindingsOn(mboundView24);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): noData
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}