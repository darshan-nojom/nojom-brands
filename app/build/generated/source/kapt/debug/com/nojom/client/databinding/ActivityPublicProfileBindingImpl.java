package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityPublicProfileBindingImpl extends ActivityPublicProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(18);
        sIncludes.setIncludes(1, 
            new String[] {"no_data_layout"},
            new int[] {7},
            new int[] {com.nojom.client.R.layout.no_data_layout});
        sIncludes.setIncludes(2, 
            new String[] {"item_reviews_ph", "item_reviews_ph", "item_reviews_ph", "item_reviews_ph"},
            new int[] {3, 4, 5, 6},
            new int[] {com.nojom.client.R.layout.item_reviews_ph,
                com.nojom.client.R.layout.item_reviews_ph,
                com.nojom.client.R.layout.item_reviews_ph,
                com.nojom.client.R.layout.item_reviews_ph});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rl_header, 8);
        sViewsWithIds.put(R.id.img_back, 9);
        sViewsWithIds.put(R.id.tv_user_name, 10);
        sViewsWithIds.put(R.id.ratingbar, 11);
        sViewsWithIds.put(R.id.tv_reviews, 12);
        sViewsWithIds.put(R.id.img_profile, 13);
        sViewsWithIds.put(R.id.rv_verified, 14);
        sViewsWithIds.put(R.id.tv_no_verified, 15);
        sViewsWithIds.put(R.id.rv_reviews, 16);
        sViewsWithIds.put(R.id.shimmer_layout, 17);
    }
    // views
    @NonNull
    private final androidx.core.widget.NestedScrollView mboundView0;
    @NonNull
    private final android.widget.RelativeLayout mboundView1;
    @NonNull
    private final android.widget.LinearLayout mboundView2;
    @Nullable
    private final com.nojom.client.databinding.ItemReviewsPhBinding mboundView21;
    @Nullable
    private final com.nojom.client.databinding.ItemReviewsPhBinding mboundView22;
    @Nullable
    private final com.nojom.client.databinding.ItemReviewsPhBinding mboundView23;
    @Nullable
    private final com.nojom.client.databinding.ItemReviewsPhBinding mboundView24;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPublicProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }
    private ActivityPublicProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ImageView) bindings[9]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[13]
            , (com.nojom.client.databinding.NoDataLayoutBinding) bindings[7]
            , (com.willy.ratingbar.ScaleRatingBar) bindings[11]
            , (android.widget.RelativeLayout) bindings[8]
            , (androidx.recyclerview.widget.RecyclerView) bindings[16]
            , (androidx.recyclerview.widget.RecyclerView) bindings[14]
            , (com.facebook.shimmer.ShimmerFrameLayout) bindings[17]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[10]
            );
        this.mboundView0 = (androidx.core.widget.NestedScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.RelativeLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.LinearLayout) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView21 = (com.nojom.client.databinding.ItemReviewsPhBinding) bindings[3];
        setContainedBinding(this.mboundView21);
        this.mboundView22 = (com.nojom.client.databinding.ItemReviewsPhBinding) bindings[4];
        setContainedBinding(this.mboundView22);
        this.mboundView23 = (com.nojom.client.databinding.ItemReviewsPhBinding) bindings[5];
        setContainedBinding(this.mboundView23);
        this.mboundView24 = (com.nojom.client.databinding.ItemReviewsPhBinding) bindings[6];
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
        mboundView21.invalidateAll();
        mboundView22.invalidateAll();
        mboundView23.invalidateAll();
        mboundView24.invalidateAll();
        noData.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
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
        if (noData.hasPendingBindings()) {
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
        mboundView21.setLifecycleOwner(lifecycleOwner);
        mboundView22.setLifecycleOwner(lifecycleOwner);
        mboundView23.setLifecycleOwner(lifecycleOwner);
        mboundView24.setLifecycleOwner(lifecycleOwner);
        noData.setLifecycleOwner(lifecycleOwner);
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
        executeBindingsOn(mboundView21);
        executeBindingsOn(mboundView22);
        executeBindingsOn(mboundView23);
        executeBindingsOn(mboundView24);
        executeBindingsOn(noData);
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