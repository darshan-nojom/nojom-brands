package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentProjectDetailsBindingImpl extends FragmentProjectDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lout_project_details, 1);
        sViewsWithIds.put(R.id.tv_edit, 2);
        sViewsWithIds.put(R.id.progress_bar, 3);
        sViewsWithIds.put(R.id.tv_job_title, 4);
        sViewsWithIds.put(R.id.tv_project_budget, 5);
        sViewsWithIds.put(R.id.tv_paytype, 6);
        sViewsWithIds.put(R.id.tv_service, 7);
        sViewsWithIds.put(R.id.tv_skills, 8);
        sViewsWithIds.put(R.id.skills_tag, 9);
        sViewsWithIds.put(R.id.tv_deadline, 10);
        sViewsWithIds.put(R.id.tv_details, 11);
        sViewsWithIds.put(R.id.lout_job_describe, 12);
        sViewsWithIds.put(R.id.txt_detail, 13);
        sViewsWithIds.put(R.id.lout_describe, 14);
        sViewsWithIds.put(R.id.tv_describe, 15);
        sViewsWithIds.put(R.id.btn_add_describe, 16);
        sViewsWithIds.put(R.id.tv_cf, 17);
        sViewsWithIds.put(R.id.tv_upload_file, 18);
        sViewsWithIds.put(R.id.rv_my_files, 19);
        sViewsWithIds.put(R.id.noData, 20);
        sViewsWithIds.put(R.id.tv_no_title, 21);
        sViewsWithIds.put(R.id.tv_no_description, 22);
        sViewsWithIds.put(R.id.btn_post_job, 23);
        sViewsWithIds.put(R.id.lout_gig_details, 24);
        sViewsWithIds.put(R.id.tv_job_title_gig, 25);
        sViewsWithIds.put(R.id.tv_project_price_gig, 26);
        sViewsWithIds.put(R.id.tv_paytype_gig, 27);
        sViewsWithIds.put(R.id.tv_details_gig, 28);
        sViewsWithIds.put(R.id.loutPackageDetails, 29);
        sViewsWithIds.put(R.id.tv_package_title, 30);
        sViewsWithIds.put(R.id.tv_package_desc, 31);
        sViewsWithIds.put(R.id.tv_quantity, 32);
        sViewsWithIds.put(R.id.tv_delivery_days, 33);
        sViewsWithIds.put(R.id.tv_revisions_days, 34);
        sViewsWithIds.put(R.id.rv_gig_item, 35);
        sViewsWithIds.put(R.id.loutCustomPackageDetails, 36);
        sViewsWithIds.put(R.id.lout_custom_packages_price, 37);
        sViewsWithIds.put(R.id.tv_custom_packages_price, 38);
        sViewsWithIds.put(R.id.tv_custom_delivery_days, 39);
        sViewsWithIds.put(R.id.rv_custom_gig_item, 40);
        sViewsWithIds.put(R.id.rv_gig_social, 41);
        sViewsWithIds.put(R.id.tv_job_id, 42);
    }
    // views
    @NonNull
    private final androidx.core.widget.NestedScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentProjectDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 43, sIncludes, sViewsWithIds));
    }
    private FragmentProjectDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.textview.CustomTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[23]
            , (android.widget.LinearLayout) bindings[36]
            , (android.widget.LinearLayout) bindings[37]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[24]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[29]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[20]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[3]
            , (androidx.recyclerview.widget.RecyclerView) bindings[40]
            , (androidx.recyclerview.widget.RecyclerView) bindings[35]
            , (androidx.recyclerview.widget.RecyclerView) bindings[41]
            , (androidx.recyclerview.widget.RecyclerView) bindings[19]
            , (com.cunoraz.tagview.TagView) bindings[9]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[39]
            , (android.textview.CustomTextView) bindings[38]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[33]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[11]
            , (com.nojom.client.util.ReadMoreTextView) bindings[28]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[42]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[25]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[21]
            , (android.textview.CustomTextView) bindings[31]
            , (android.textview.CustomTextView) bindings[30]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[27]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[32]
            , (android.textview.CustomTextView) bindings[34]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[8]
            , (android.widget.LinearLayout) bindings[18]
            , (android.textview.CustomTextView) bindings[13]
            );
        this.mboundView0 = (androidx.core.widget.NestedScrollView) bindings[0];
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