package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentInfAllBindingImpl extends FragmentInfAllBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rel_send_offer, 1);
        sViewsWithIds.put(R.id.lout_best, 2);
        sViewsWithIds.put(R.id.rl_best_view, 3);
        sViewsWithIds.put(R.id.viewBestAll, 4);
        sViewsWithIds.put(R.id.rv_services, 5);
        sViewsWithIds.put(R.id.tv_ph_service, 6);
        sViewsWithIds.put(R.id.lout_gig, 7);
        sViewsWithIds.put(R.id.tv_gig_title, 8);
        sViewsWithIds.put(R.id.rl_agency_view, 9);
        sViewsWithIds.put(R.id.viewGigAll, 10);
        sViewsWithIds.put(R.id.tv_agency_name, 11);
        sViewsWithIds.put(R.id.tv_agency_contact, 12);
        sViewsWithIds.put(R.id.tv_agency_website, 13);
        sViewsWithIds.put(R.id.lout_linkedin, 14);
        sViewsWithIds.put(R.id.txt_reviews, 15);
        sViewsWithIds.put(R.id.txt_review_count, 16);
        sViewsWithIds.put(R.id.tv_linkedin_view, 17);
        sViewsWithIds.put(R.id.viewLinkdAll, 18);
        sViewsWithIds.put(R.id.rv_linkedin, 19);
        sViewsWithIds.put(R.id.tv_ph_review, 20);
        sViewsWithIds.put(R.id.tv_linkedin_view_all, 21);
        sViewsWithIds.put(R.id.view_all_linkedin, 22);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentInfAllBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds));
    }
    private FragmentInfAllBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[14]
            , (androidx.core.widget.NestedScrollView) bindings[0]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.RelativeLayout) bindings[3]
            , (androidx.recyclerview.widget.RecyclerView) bindings[19]
            , (androidx.recyclerview.widget.RecyclerView) bindings[5]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[8]
            , (android.widget.RelativeLayout) bindings[17]
            , (android.widget.RelativeLayout) bindings[21]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[18]
            );
        this.nestedScroll.setTag(null);
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