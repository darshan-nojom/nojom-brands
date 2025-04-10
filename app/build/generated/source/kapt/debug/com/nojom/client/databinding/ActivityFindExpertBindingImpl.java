package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityFindExpertBindingImpl extends ActivityFindExpertBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lin_freelancer_tab, 1);
        sViewsWithIds.put(R.id.img_back, 2);
        sViewsWithIds.put(R.id.et_search, 3);
        sViewsWithIds.put(R.id.rl_filter, 4);
        sViewsWithIds.put(R.id.tv_filter_count, 5);
        sViewsWithIds.put(R.id.tv_filter, 6);
        sViewsWithIds.put(R.id.img_sort, 7);
        sViewsWithIds.put(R.id.img_search, 8);
        sViewsWithIds.put(R.id.tv_cancel, 9);
        sViewsWithIds.put(R.id.rl_check_all, 10);
        sViewsWithIds.put(R.id.img_all_pic, 11);
        sViewsWithIds.put(R.id.img_check_all, 12);
        sViewsWithIds.put(R.id.segmentedGroupTab, 13);
        sViewsWithIds.put(R.id.tab_all, 14);
        sViewsWithIds.put(R.id.tab_saved, 15);
        sViewsWithIds.put(R.id.tab_hired, 16);
        sViewsWithIds.put(R.id.tv_online_now, 17);
        sViewsWithIds.put(R.id.viewpager, 18);
        sViewsWithIds.put(R.id.tv_done, 19);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityFindExpertBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds));
    }
    private ActivityFindExpertBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.edittext.CustomEditText) bindings[3]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[10]
            , (android.widget.LinearLayout) bindings[4]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[13]
            , (com.nojom.client.segment.SegmentedButton) bindings[14]
            , (com.nojom.client.segment.SegmentedButton) bindings[16]
            , (com.nojom.client.segment.SegmentedButton) bindings[15]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[17]
            , (androidx.viewpager.widget.ViewPager) bindings[18]
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