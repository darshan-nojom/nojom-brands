package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentPostJobBindingImpl extends FragmentPostJobBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rl_header, 1);
        sViewsWithIds.put(R.id.img_back, 2);
        sViewsWithIds.put(R.id.tv_post_job, 3);
        sViewsWithIds.put(R.id.progress_bar, 4);
        sViewsWithIds.put(R.id.et_job_title, 5);
        sViewsWithIds.put(R.id.et_describe, 6);
        sViewsWithIds.put(R.id.rl_search_tag, 7);
        sViewsWithIds.put(R.id.tv_search_tag, 8);
        sViewsWithIds.put(R.id.img_search_tag, 9);
        sViewsWithIds.put(R.id.rv_files, 10);
        sViewsWithIds.put(R.id.tv_attach_file, 11);
        sViewsWithIds.put(R.id.tv_lbl_productType, 12);
        sViewsWithIds.put(R.id.lin_tabs, 13);
        sViewsWithIds.put(R.id.segmentedGroupTab, 14);
        sViewsWithIds.put(R.id.tab_fixedprice, 15);
        sViewsWithIds.put(R.id.tab_hourly, 16);
        sViewsWithIds.put(R.id.tab_free, 17);
        sViewsWithIds.put(R.id.rl_developer, 18);
        sViewsWithIds.put(R.id.tv_developer, 19);
        sViewsWithIds.put(R.id.tvDeadlineTitle, 20);
        sViewsWithIds.put(R.id.rl_deadline, 21);
        sViewsWithIds.put(R.id.tv_deadline, 22);
        sViewsWithIds.put(R.id.txtLblBudget, 23);
        sViewsWithIds.put(R.id.rl_budget, 24);
        sViewsWithIds.put(R.id.tv_rates, 25);
        sViewsWithIds.put(R.id.viewBudget, 26);
        sViewsWithIds.put(R.id.tv_law_catTitle, 27);
        sViewsWithIds.put(R.id.rl_law_cat, 28);
        sViewsWithIds.put(R.id.tv_sel_law_cat, 29);
        sViewsWithIds.put(R.id.view_law_cat, 30);
        sViewsWithIds.put(R.id.txt_loc_title, 31);
        sViewsWithIds.put(R.id.rl_location, 32);
        sViewsWithIds.put(R.id.et_location, 33);
        sViewsWithIds.put(R.id.img_location, 34);
        sViewsWithIds.put(R.id.ll_freelancer, 35);
        sViewsWithIds.put(R.id.tv_titleFreelancer, 36);
        sViewsWithIds.put(R.id.rl_freelancer, 37);
        sViewsWithIds.put(R.id.tv_freelancer, 38);
        sViewsWithIds.put(R.id.img_freelancer, 39);
        sViewsWithIds.put(R.id.rv_skills, 40);
        sViewsWithIds.put(R.id.tv_add_skill, 41);
        sViewsWithIds.put(R.id.ll_bottom, 42);
        sViewsWithIds.put(R.id.tv_cancel, 43);
        sViewsWithIds.put(R.id.relBottom, 44);
        sViewsWithIds.put(R.id.tv_save, 45);
        sViewsWithIds.put(R.id.progress_bar_edit, 46);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentPostJobBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 47, sIncludes, sViewsWithIds));
    }
    private FragmentPostJobBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.edittext.CustomEditText) bindings[6]
            , (android.edittext.CustomEditText) bindings[5]
            , (android.textview.CustomTextView) bindings[33]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.ImageView) bindings[39]
            , (android.widget.ImageView) bindings[34]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.LinearLayout) bindings[42]
            , (android.widget.LinearLayout) bindings[35]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[4]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[46]
            , (android.widget.RelativeLayout) bindings[44]
            , (android.widget.RelativeLayout) bindings[24]
            , (android.widget.RelativeLayout) bindings[21]
            , (android.widget.RelativeLayout) bindings[18]
            , (android.widget.RelativeLayout) bindings[37]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[28]
            , (android.widget.RelativeLayout) bindings[32]
            , (android.widget.RelativeLayout) bindings[7]
            , (androidx.recyclerview.widget.RecyclerView) bindings[10]
            , (androidx.recyclerview.widget.RecyclerView) bindings[40]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[14]
            , (com.nojom.client.segment.SegmentedButton) bindings[15]
            , (com.nojom.client.segment.SegmentedButton) bindings[17]
            , (com.nojom.client.segment.SegmentedButton) bindings[16]
            , (android.textview.CustomTextView) bindings[41]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[43]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[38]
            , (android.textview.CustomTextView) bindings[27]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[25]
            , (android.textview.CustomTextView) bindings[45]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[29]
            , (android.textview.CustomTextView) bindings[36]
            , (android.textview.CustomTextView) bindings[23]
            , (android.textview.CustomTextView) bindings[31]
            , (android.view.View) bindings[26]
            , (android.view.View) bindings[30]
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