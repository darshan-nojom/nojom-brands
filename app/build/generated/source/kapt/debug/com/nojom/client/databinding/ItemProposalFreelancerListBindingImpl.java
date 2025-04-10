package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemProposalFreelancerListBindingImpl extends ItemProposalFreelancerListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rl_profile, 1);
        sViewsWithIds.put(R.id.img_user, 2);
        sViewsWithIds.put(R.id.tv_name, 3);
        sViewsWithIds.put(R.id.ratingbar, 4);
        sViewsWithIds.put(R.id.tv_rating, 5);
        sViewsWithIds.put(R.id.lin_price, 6);
        sViewsWithIds.put(R.id.tv_bid_price, 7);
        sViewsWithIds.put(R.id.tv_price_type, 8);
        sViewsWithIds.put(R.id.tv_type, 9);
        sViewsWithIds.put(R.id.ll_cover_letter, 10);
        sViewsWithIds.put(R.id.tv_proposal, 11);
        sViewsWithIds.put(R.id.tv_hire, 12);
        sViewsWithIds.put(R.id.tv_chat, 13);
        sViewsWithIds.put(R.id.progress_bar, 14);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemProposalFreelancerListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private ItemProposalFreelancerListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[2]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[10]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[14]
            , (com.willy.ratingbar.ScaleRatingBar) bindings[4]
            , (android.widget.RelativeLayout) bindings[0]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[8]
            , (com.nojom.client.util.ReadMoreTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[9]
            );
        this.relProposal.setTag(null);
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