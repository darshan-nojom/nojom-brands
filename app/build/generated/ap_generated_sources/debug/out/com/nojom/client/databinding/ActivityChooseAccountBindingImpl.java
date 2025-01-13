package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityChooseAccountBindingImpl extends ActivityChooseAccountBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(15);
        sIncludes.setIncludes(1, 
            new String[] {"no_data_layout"},
            new int[] {2},
            new int[] {com.nojom.client.R.layout.no_data_layout});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rl_header, 3);
        sViewsWithIds.put(R.id.img_back, 4);
        sViewsWithIds.put(R.id.rl_google_pay, 5);
        sViewsWithIds.put(R.id.google, 6);
        sViewsWithIds.put(R.id.img_check_uncheck, 7);
        sViewsWithIds.put(R.id.rl_venmo_pay, 8);
        sViewsWithIds.put(R.id.venmo, 9);
        sViewsWithIds.put(R.id.img_check_uncheck_venmo, 10);
        sViewsWithIds.put(R.id.rv_accounts, 11);
        sViewsWithIds.put(R.id.tv_add_account, 12);
        sViewsWithIds.put(R.id.ll_bottom, 13);
        sViewsWithIds.put(R.id.tv_save, 14);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityChooseAccountBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private ActivityChooseAccountBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.LinearLayout) bindings[13]
            , (com.nojom.client.databinding.NoDataLayoutBinding) bindings[2]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.RelativeLayout) bindings[8]
            , (androidx.recyclerview.widget.RecyclerView) bindings[11]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[14]
            , (android.widget.ImageView) bindings[9]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        setContainedBinding(this.noData);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        noData.invalidateAll();
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
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeNoData((com.nojom.client.databinding.NoDataLayoutBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeNoData(com.nojom.client.databinding.NoDataLayoutBinding NoData, int fieldId) {
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
        executeBindingsOn(noData);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): noData
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}