package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityLawyerHomeBindingImpl extends ActivityLawyerHomeBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(49);
        sIncludes.setIncludes(1, 
            new String[] {"layout_home_how_work"},
            new int[] {2},
            new int[] {com.nojom.client.R.layout.layout_home_how_work});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rv_skills, 3);
        sViewsWithIds.put(R.id.txt_hire_lawyer_lbl, 4);
        sViewsWithIds.put(R.id.vp_hire, 5);
        sViewsWithIds.put(R.id.indicator_hire, 6);
        sViewsWithIds.put(R.id.lout_search, 7);
        sViewsWithIds.put(R.id.v_search, 8);
        sViewsWithIds.put(R.id.btn_search, 9);
        sViewsWithIds.put(R.id.rv_top_skills, 10);
        sViewsWithIds.put(R.id.lout_saved, 11);
        sViewsWithIds.put(R.id.rel_saved_view, 12);
        sViewsWithIds.put(R.id.viewSavedAll, 13);
        sViewsWithIds.put(R.id.rv_saved, 14);
        sViewsWithIds.put(R.id.lout_best, 15);
        sViewsWithIds.put(R.id.tv_best_view, 16);
        sViewsWithIds.put(R.id.viewBestAll, 17);
        sViewsWithIds.put(R.id.rv_bestInf, 18);
        sViewsWithIds.put(R.id.lout_gig, 19);
        sViewsWithIds.put(R.id.tv_gig_title, 20);
        sViewsWithIds.put(R.id.tv_gig_view, 21);
        sViewsWithIds.put(R.id.viewGigAll, 22);
        sViewsWithIds.put(R.id.rv_gig, 23);
        sViewsWithIds.put(R.id.tv_gig_view_all, 24);
        sViewsWithIds.put(R.id.view_all_gig, 25);
        sViewsWithIds.put(R.id.lout_linkedin, 26);
        sViewsWithIds.put(R.id.tv_linkedin_view, 27);
        sViewsWithIds.put(R.id.viewLinkdAll, 28);
        sViewsWithIds.put(R.id.rv_linkedin, 29);
        sViewsWithIds.put(R.id.tv_linkedin_view_all, 30);
        sViewsWithIds.put(R.id.view_all_linkedin, 31);
        sViewsWithIds.put(R.id.lout_youtube, 32);
        sViewsWithIds.put(R.id.tv_youtube_view, 33);
        sViewsWithIds.put(R.id.viewYoutubeAll, 34);
        sViewsWithIds.put(R.id.rv_youtube, 35);
        sViewsWithIds.put(R.id.tv_youtube_view_all, 36);
        sViewsWithIds.put(R.id.view_all_youtube, 37);
        sViewsWithIds.put(R.id.lout_insta, 38);
        sViewsWithIds.put(R.id.tv_insta_view, 39);
        sViewsWithIds.put(R.id.viewInstaAll, 40);
        sViewsWithIds.put(R.id.rv_insta, 41);
        sViewsWithIds.put(R.id.tv_insta_view_all, 42);
        sViewsWithIds.put(R.id.view_all_insta, 43);
        sViewsWithIds.put(R.id.tv_startNow, 44);
        sViewsWithIds.put(R.id.tv_get_help, 45);
        sViewsWithIds.put(R.id.txt_linkdin, 46);
        sViewsWithIds.put(R.id.txt_instagram, 47);
        sViewsWithIds.put(R.id.txt_facebook, 48);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityLawyerHomeBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 49, sIncludes, sViewsWithIds));
    }
    private ActivityLawyerHomeBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.textview.CustomTextView) bindings[9]
            , (com.nojom.client.databinding.LayoutHomeHowWorkBinding) bindings[2]
            , (com.nojom.client.ui.autoscrollviewpager.InkPageIndicator) bindings[6]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.LinearLayout) bindings[38]
            , (android.widget.LinearLayout) bindings[26]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[32]
            , (androidx.core.widget.NestedScrollView) bindings[0]
            , (android.widget.RelativeLayout) bindings[12]
            , (androidx.recyclerview.widget.RecyclerView) bindings[18]
            , (androidx.recyclerview.widget.RecyclerView) bindings[23]
            , (androidx.recyclerview.widget.RecyclerView) bindings[41]
            , (androidx.recyclerview.widget.RecyclerView) bindings[29]
            , (androidx.recyclerview.widget.RecyclerView) bindings[14]
            , (androidx.recyclerview.widget.RecyclerView) bindings[3]
            , (androidx.recyclerview.widget.RecyclerView) bindings[10]
            , (androidx.recyclerview.widget.RecyclerView) bindings[35]
            , (android.widget.RelativeLayout) bindings[16]
            , (android.textview.CustomTextView) bindings[45]
            , (android.textview.CustomTextView) bindings[20]
            , (android.widget.RelativeLayout) bindings[21]
            , (android.widget.RelativeLayout) bindings[24]
            , (android.widget.RelativeLayout) bindings[39]
            , (android.widget.RelativeLayout) bindings[42]
            , (android.widget.RelativeLayout) bindings[27]
            , (android.widget.RelativeLayout) bindings[30]
            , (android.textview.CustomTextView) bindings[44]
            , (android.widget.RelativeLayout) bindings[33]
            , (android.widget.RelativeLayout) bindings[36]
            , (android.textview.CustomTextView) bindings[48]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[47]
            , (android.textview.CustomTextView) bindings[46]
            , (android.view.View) bindings[8]
            , (android.textview.CustomTextView) bindings[25]
            , (android.textview.CustomTextView) bindings[43]
            , (android.textview.CustomTextView) bindings[31]
            , (android.textview.CustomTextView) bindings[37]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[40]
            , (android.textview.CustomTextView) bindings[28]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[34]
            , (com.nojom.client.ui.autoscrollviewpager.LoopingViewPager) bindings[5]
            );
        setContainedBinding(this.howWork);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.nestedScroll.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        howWork.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (howWork.hasPendingBindings()) {
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
        howWork.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeHowWork((com.nojom.client.databinding.LayoutHomeHowWorkBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeHowWork(com.nojom.client.databinding.LayoutHomeHowWorkBinding HowWork, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
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
        executeBindingsOn(howWork);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): howWork
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}