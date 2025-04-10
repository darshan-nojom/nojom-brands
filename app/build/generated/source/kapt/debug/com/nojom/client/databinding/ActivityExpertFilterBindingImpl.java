package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityExpertFilterBindingImpl extends ActivityExpertFilterBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.img_close, 1);
        sViewsWithIds.put(R.id.tv_clear, 2);
        sViewsWithIds.put(R.id.tv_total_jobs, 3);
        sViewsWithIds.put(R.id.tv_top_rating, 4);
        sViewsWithIds.put(R.id.rv_expertise, 5);
        sViewsWithIds.put(R.id.tv_language, 6);
        sViewsWithIds.put(R.id.rl_hourly, 7);
        sViewsWithIds.put(R.id.img_check_hourly, 8);
        sViewsWithIds.put(R.id.rl_part_time, 9);
        sViewsWithIds.put(R.id.img_check_part_time, 10);
        sViewsWithIds.put(R.id.rl_full_time, 11);
        sViewsWithIds.put(R.id.img_check_full_time, 12);
        sViewsWithIds.put(R.id.rl_office_base, 13);
        sViewsWithIds.put(R.id.img_office_base, 14);
        sViewsWithIds.put(R.id.rl_home_base, 15);
        sViewsWithIds.put(R.id.img_check_home_base, 16);
        sViewsWithIds.put(R.id.et_skill, 17);
        sViewsWithIds.put(R.id.rv_skills, 18);
        sViewsWithIds.put(R.id.ll_bottom, 19);
        sViewsWithIds.put(R.id.tv_cancel, 20);
        sViewsWithIds.put(R.id.tv_apply, 21);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityExpertFilterBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }
    private ActivityExpertFilterBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.edittext.CustomEditText) bindings[17]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.ImageView) bindings[14]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.widget.RelativeLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[13]
            , (android.widget.RelativeLayout) bindings[9]
            , (androidx.recyclerview.widget.RecyclerView) bindings[5]
            , (androidx.recyclerview.widget.RecyclerView) bindings[18]
            , (android.textview.CustomTextView) bindings[21]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[3]
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