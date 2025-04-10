package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityInfluencerFilterBindingImpl extends ActivityInfluencerFilterBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rl_toolbar, 1);
        sViewsWithIds.put(R.id.img_close, 2);
        sViewsWithIds.put(R.id.tv_clear, 3);
        sViewsWithIds.put(R.id.txt_best_seller, 4);
        sViewsWithIds.put(R.id.txt_most_follower, 5);
        sViewsWithIds.put(R.id.txt_all, 6);
        sViewsWithIds.put(R.id.txt_saved, 7);
        sViewsWithIds.put(R.id.txt_hired, 8);
        sViewsWithIds.put(R.id.txt_m_no, 9);
        sViewsWithIds.put(R.id.txt_m_have, 10);
        sViewsWithIds.put(R.id.txt_m_all, 11);
        sViewsWithIds.put(R.id.txt_platform, 12);
        sViewsWithIds.put(R.id.rv_sortBy, 13);
        sViewsWithIds.put(R.id.et_search, 14);
        sViewsWithIds.put(R.id.rv_skills, 15);
        sViewsWithIds.put(R.id.et_search_skills, 16);
        sViewsWithIds.put(R.id.rv_skill_tags, 17);
        sViewsWithIds.put(R.id.rv_priceRange, 18);
        sViewsWithIds.put(R.id.lin_price, 19);
        sViewsWithIds.put(R.id.et_pr_from, 20);
        sViewsWithIds.put(R.id.et_pr_to, 21);
        sViewsWithIds.put(R.id.rv_follower, 22);
        sViewsWithIds.put(R.id.lin_follower, 23);
        sViewsWithIds.put(R.id.et_count_from, 24);
        sViewsWithIds.put(R.id.et_count_to, 25);
        sViewsWithIds.put(R.id.txt_female, 26);
        sViewsWithIds.put(R.id.txt_male, 27);
        sViewsWithIds.put(R.id.txt_other, 28);
        sViewsWithIds.put(R.id.rv_age, 29);
        sViewsWithIds.put(R.id.lin_age, 30);
        sViewsWithIds.put(R.id.et_age_from, 31);
        sViewsWithIds.put(R.id.et_age_to, 32);
        sViewsWithIds.put(R.id.txt_language, 33);
        sViewsWithIds.put(R.id.txt_location, 34);
        sViewsWithIds.put(R.id.txt_city, 35);
        sViewsWithIds.put(R.id.ll_bottom, 36);
        sViewsWithIds.put(R.id.tv_cancel, 37);
        sViewsWithIds.put(R.id.tv_apply, 38);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityInfluencerFilterBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 39, sIncludes, sViewsWithIds));
    }
    private ActivityInfluencerFilterBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.edittext.CustomEditText) bindings[31]
            , (android.edittext.CustomEditText) bindings[32]
            , (android.edittext.CustomEditText) bindings[24]
            , (android.edittext.CustomEditText) bindings[25]
            , (android.edittext.CustomEditText) bindings[20]
            , (android.edittext.CustomEditText) bindings[21]
            , (android.edittext.CustomEditText) bindings[14]
            , (android.edittext.CustomEditText) bindings[16]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.LinearLayout) bindings[30]
            , (android.widget.LinearLayout) bindings[23]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.LinearLayout) bindings[36]
            , (android.widget.RelativeLayout) bindings[1]
            , (androidx.recyclerview.widget.RecyclerView) bindings[29]
            , (androidx.recyclerview.widget.RecyclerView) bindings[22]
            , (androidx.recyclerview.widget.RecyclerView) bindings[18]
            , (androidx.recyclerview.widget.RecyclerView) bindings[17]
            , (androidx.recyclerview.widget.RecyclerView) bindings[15]
            , (androidx.recyclerview.widget.RecyclerView) bindings[13]
            , (android.textview.CustomTextView) bindings[38]
            , (android.textview.CustomTextView) bindings[37]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[35]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[33]
            , (android.textview.CustomTextView) bindings[34]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[27]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[28]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[7]
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