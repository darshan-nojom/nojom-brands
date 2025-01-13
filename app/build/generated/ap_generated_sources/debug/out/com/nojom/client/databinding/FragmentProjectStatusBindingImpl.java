package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentProjectStatusBindingImpl extends FragmentProjectStatusBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(44);
        sIncludes.setIncludes(1, 
            new String[] {"no_data_layout"},
            new int[] {4},
            new int[] {com.nojom.client.R.layout.no_data_layout});
        sIncludes.setIncludes(2, 
            new String[] {"item_proposal_list"},
            new int[] {5},
            new int[] {com.nojom.client.R.layout.item_proposal_list});
        sIncludes.setIncludes(3, 
            new String[] {"item_user_rehire"},
            new int[] {6},
            new int[] {com.nojom.client.R.layout.item_user_rehire});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_title, 7);
        sViewsWithIds.put(R.id.swipeRefreshLayout, 8);
        sViewsWithIds.put(R.id.rv_hire, 9);
        sViewsWithIds.put(R.id.tv_or, 10);
        sViewsWithIds.put(R.id.tv_cancel_freelancer, 11);
        sViewsWithIds.put(R.id.rl_profile_progress, 12);
        sViewsWithIds.put(R.id.img_user1, 13);
        sViewsWithIds.put(R.id.tv_name1, 14);
        sViewsWithIds.put(R.id.tv_place1, 15);
        sViewsWithIds.put(R.id.tv_project, 16);
        sViewsWithIds.put(R.id.tv_chat1, 17);
        sViewsWithIds.put(R.id.ll_inprogress, 18);
        sViewsWithIds.put(R.id.tv_days, 19);
        sViewsWithIds.put(R.id.tv_hours, 20);
        sViewsWithIds.put(R.id.tv_minutes, 21);
        sViewsWithIds.put(R.id.tv_second, 22);
        sViewsWithIds.put(R.id.ll_job_status, 23);
        sViewsWithIds.put(R.id.tv_1, 24);
        sViewsWithIds.put(R.id.view1_right, 25);
        sViewsWithIds.put(R.id.txt_lbl_1, 26);
        sViewsWithIds.put(R.id.view2_left, 27);
        sViewsWithIds.put(R.id.tv_2, 28);
        sViewsWithIds.put(R.id.view2_right, 29);
        sViewsWithIds.put(R.id.txt_lbl_2, 30);
        sViewsWithIds.put(R.id.view3_left, 31);
        sViewsWithIds.put(R.id.tv_3, 32);
        sViewsWithIds.put(R.id.view3_right, 33);
        sViewsWithIds.put(R.id.txt_lbl_3, 34);
        sViewsWithIds.put(R.id.view4_left, 35);
        sViewsWithIds.put(R.id.tv_4, 36);
        sViewsWithIds.put(R.id.txt_lbl_4, 37);
        sViewsWithIds.put(R.id.tv_job_status_info, 38);
        sViewsWithIds.put(R.id.tv_live_support, 39);
        sViewsWithIds.put(R.id.rl_withdraw, 40);
        sViewsWithIds.put(R.id.tv_total, 41);
        sViewsWithIds.put(R.id.ll_close_project, 42);
        sViewsWithIds.put(R.id.tv_project_status, 43);
    }
    // views
    @NonNull
    private final androidx.core.widget.NestedScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentProjectStatusBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 44, sIncludes, sViewsWithIds));
    }
    private FragmentProjectStatusBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[13]
            , (android.widget.LinearLayout) bindings[42]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.LinearLayout) bindings[23]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.LinearLayout) bindings[2]
            , (com.nojom.client.databinding.NoDataLayoutBinding) bindings[4]
            , (com.nojom.client.databinding.ItemProposalListBinding) bindings[5]
            , (com.nojom.client.databinding.ItemUserRehireBinding) bindings[6]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[40]
            , (androidx.recyclerview.widget.RecyclerView) bindings[9]
            , (androidx.swiperefreshlayout.widget.SwipeRefreshLayout) bindings[8]
            , (android.textview.CustomTextView) bindings[24]
            , (android.textview.CustomTextView) bindings[28]
            , (android.textview.CustomTextView) bindings[32]
            , (android.textview.CustomTextView) bindings[36]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[38]
            , (android.textview.CustomTextView) bindings[39]
            , (android.textview.CustomTextView) bindings[21]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[43]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[41]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[30]
            , (android.textview.CustomTextView) bindings[34]
            , (android.textview.CustomTextView) bindings[37]
            , (android.view.View) bindings[25]
            , (android.view.View) bindings[27]
            , (android.view.View) bindings[29]
            , (android.view.View) bindings[31]
            , (android.view.View) bindings[33]
            , (android.view.View) bindings[35]
            );
        this.llHire.setTag(null);
        this.llUserRehire.setTag(null);
        this.llWaitingForDeposit.setTag(null);
        this.mboundView0 = (androidx.core.widget.NestedScrollView) bindings[0];
        this.mboundView0.setTag(null);
        setContainedBinding(this.noData);
        setContainedBinding(this.proposal);
        setContainedBinding(this.rehire);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        noData.invalidateAll();
        proposal.invalidateAll();
        rehire.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (noData.hasPendingBindings()) {
            return true;
        }
        if (proposal.hasPendingBindings()) {
            return true;
        }
        if (rehire.hasPendingBindings()) {
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
        noData.setLifecycleOwner(lifecycleOwner);
        proposal.setLifecycleOwner(lifecycleOwner);
        rehire.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeRehire((com.nojom.client.databinding.ItemUserRehireBinding) object, fieldId);
            case 1 :
                return onChangeProposal((com.nojom.client.databinding.ItemProposalListBinding) object, fieldId);
            case 2 :
                return onChangeNoData((com.nojom.client.databinding.NoDataLayoutBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeRehire(com.nojom.client.databinding.ItemUserRehireBinding Rehire, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeProposal(com.nojom.client.databinding.ItemProposalListBinding Proposal, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeNoData(com.nojom.client.databinding.NoDataLayoutBinding NoData, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
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
        executeBindingsOn(noData);
        executeBindingsOn(proposal);
        executeBindingsOn(rehire);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): rehire
        flag 1 (0x2L): proposal
        flag 2 (0x3L): noData
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}