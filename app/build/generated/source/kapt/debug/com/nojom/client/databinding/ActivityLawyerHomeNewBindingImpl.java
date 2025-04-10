package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityLawyerHomeNewBindingImpl extends ActivityLawyerHomeNewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(15);
        sIncludes.setIncludes(1, 
            new String[] {"layout_home_how_work"},
            new int[] {2},
            new int[] {com.nojom.client.R.layout.layout_home_how_work});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.txt_hire_lawyer_lbl, 3);
        sViewsWithIds.put(R.id.lout_search, 4);
        sViewsWithIds.put(R.id.btn_filter, 5);
        sViewsWithIds.put(R.id.lout_best, 6);
        sViewsWithIds.put(R.id.rv_bestInf, 7);
        sViewsWithIds.put(R.id.tv_best_view, 8);
        sViewsWithIds.put(R.id.viewBestAll, 9);
        sViewsWithIds.put(R.id.tv_startNow, 10);
        sViewsWithIds.put(R.id.tv_get_help, 11);
        sViewsWithIds.put(R.id.txt_linkdin, 12);
        sViewsWithIds.put(R.id.txt_instagram, 13);
        sViewsWithIds.put(R.id.txt_facebook, 14);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityLawyerHomeNewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private ActivityLawyerHomeNewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ImageView) bindings[5]
            , (com.nojom.client.databinding.LayoutHomeHowWorkBinding) bindings[2]
            , (android.widget.LinearLayout) bindings[6]
            , (android.textview.CustomTextView) bindings[4]
            , (androidx.core.widget.NestedScrollView) bindings[0]
            , (androidx.recyclerview.widget.RecyclerView) bindings[7]
            , (android.widget.RelativeLayout) bindings[8]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[9]
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