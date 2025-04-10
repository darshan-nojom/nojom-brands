package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityInfluencerProfileBindingImpl extends ActivityInfluencerProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rl_header, 1);
        sViewsWithIds.put(R.id.img_back, 2);
        sViewsWithIds.put(R.id.img_save, 3);
        sViewsWithIds.put(R.id.img_share, 4);
        sViewsWithIds.put(R.id.relative, 5);
        sViewsWithIds.put(R.id.img_profile, 6);
        sViewsWithIds.put(R.id.rel_name, 7);
        sViewsWithIds.put(R.id.tv_user_name, 8);
        sViewsWithIds.put(R.id.tv_chat, 9);
        sViewsWithIds.put(R.id.linear_left, 10);
        sViewsWithIds.put(R.id.ratingbar, 11);
        sViewsWithIds.put(R.id.tv_reviews, 12);
        sViewsWithIds.put(R.id.rv_verified, 13);
        sViewsWithIds.put(R.id.tv_no_verified, 14);
        sViewsWithIds.put(R.id.rv_platform, 15);
        sViewsWithIds.put(R.id.segmentGroup, 16);
        sViewsWithIds.put(R.id.tab_about, 17);
        sViewsWithIds.put(R.id.tab_skills, 18);
        sViewsWithIds.put(R.id.tab_portfolio, 19);
        sViewsWithIds.put(R.id.tab_reviews, 20);
        sViewsWithIds.put(R.id.viewpager, 21);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityInfluencerProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }
    private ActivityInfluencerProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[2]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[6]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.LinearLayout) bindings[10]
            , (com.willy.ratingbar.ScaleRatingBar) bindings[11]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[1]
            , (androidx.recyclerview.widget.RecyclerView) bindings[15]
            , (androidx.recyclerview.widget.RecyclerView) bindings[13]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[16]
            , (com.nojom.client.segment.SegmentedButton) bindings[17]
            , (com.nojom.client.segment.SegmentedButton) bindings[19]
            , (com.nojom.client.segment.SegmentedButton) bindings[20]
            , (com.nojom.client.segment.SegmentedButton) bindings[18]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[8]
            , (androidx.viewpager.widget.ViewPager) bindings[21]
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