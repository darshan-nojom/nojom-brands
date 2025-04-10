package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityFreelancerProfileBindingImpl extends ActivityFreelancerProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rl_header, 1);
        sViewsWithIds.put(R.id.img_back, 2);
        sViewsWithIds.put(R.id.linear_left, 3);
        sViewsWithIds.put(R.id.tv_user_name, 4);
        sViewsWithIds.put(R.id.ratingbar, 5);
        sViewsWithIds.put(R.id.tv_reviews, 6);
        sViewsWithIds.put(R.id.relative, 7);
        sViewsWithIds.put(R.id.img_profile, 8);
        sViewsWithIds.put(R.id.rv_verified, 9);
        sViewsWithIds.put(R.id.tv_no_verified, 10);
        sViewsWithIds.put(R.id.segmentGroup, 11);
        sViewsWithIds.put(R.id.tab_about, 12);
        sViewsWithIds.put(R.id.tab_skills, 13);
        sViewsWithIds.put(R.id.tab_portfolio, 14);
        sViewsWithIds.put(R.id.tab_reviews, 15);
        sViewsWithIds.put(R.id.viewpager, 16);
        sViewsWithIds.put(R.id.tv_report_block, 17);
        sViewsWithIds.put(R.id.ll_bottom, 18);
        sViewsWithIds.put(R.id.tv_chat, 19);
        sViewsWithIds.put(R.id.tv_hire, 20);
        sViewsWithIds.put(R.id.progress_bar, 21);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityFreelancerProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }
    private ActivityFreelancerProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[2]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[8]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.LinearLayout) bindings[18]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[21]
            , (com.willy.ratingbar.ScaleRatingBar) bindings[5]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[1]
            , (androidx.recyclerview.widget.RecyclerView) bindings[9]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[11]
            , (com.nojom.client.segment.SegmentedButton) bindings[12]
            , (com.nojom.client.segment.SegmentedButton) bindings[14]
            , (com.nojom.client.segment.SegmentedButton) bindings[15]
            , (com.nojom.client.segment.SegmentedButton) bindings[13]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[4]
            , (androidx.viewpager.widget.ViewPager) bindings[16]
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