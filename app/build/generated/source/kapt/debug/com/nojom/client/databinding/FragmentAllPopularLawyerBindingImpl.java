package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentAllPopularLawyerBindingImpl extends FragmentAllPopularLawyerBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(10);
        sIncludes.setIncludes(0, 
            new String[] {"no_data_layout"},
            new int[] {2},
            new int[] {com.nojom.client.R.layout.no_data_layout});
        sIncludes.setIncludes(1, 
            new String[] {"item_popular_placeholder", "item_popular_placeholder", "item_popular_placeholder", "item_popular_placeholder", "item_popular_placeholder"},
            new int[] {3, 4, 5, 6, 7},
            new int[] {com.nojom.client.R.layout.item_popular_placeholder,
                com.nojom.client.R.layout.item_popular_placeholder,
                com.nojom.client.R.layout.item_popular_placeholder,
                com.nojom.client.R.layout.item_popular_placeholder,
                com.nojom.client.R.layout.item_popular_placeholder});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rv_expert, 8);
        sViewsWithIds.put(R.id.shimmer_layout, 9);
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    @Nullable
    private final com.nojom.client.databinding.ItemPopularPlaceholderBinding mboundView11;
    @Nullable
    private final com.nojom.client.databinding.ItemPopularPlaceholderBinding mboundView12;
    @Nullable
    private final com.nojom.client.databinding.ItemPopularPlaceholderBinding mboundView13;
    @Nullable
    private final com.nojom.client.databinding.ItemPopularPlaceholderBinding mboundView14;
    @Nullable
    private final com.nojom.client.databinding.ItemPopularPlaceholderBinding mboundView15;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentAllPopularLawyerBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private FragmentAllPopularLawyerBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.nojom.client.databinding.NoDataLayoutBinding) bindings[2]
            , (androidx.recyclerview.widget.RecyclerView) bindings[8]
            , (com.facebook.shimmer.ShimmerFrameLayout) bindings[9]
            );
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView11 = (com.nojom.client.databinding.ItemPopularPlaceholderBinding) bindings[3];
        setContainedBinding(this.mboundView11);
        this.mboundView12 = (com.nojom.client.databinding.ItemPopularPlaceholderBinding) bindings[4];
        setContainedBinding(this.mboundView12);
        this.mboundView13 = (com.nojom.client.databinding.ItemPopularPlaceholderBinding) bindings[5];
        setContainedBinding(this.mboundView13);
        this.mboundView14 = (com.nojom.client.databinding.ItemPopularPlaceholderBinding) bindings[6];
        setContainedBinding(this.mboundView14);
        this.mboundView15 = (com.nojom.client.databinding.ItemPopularPlaceholderBinding) bindings[7];
        setContainedBinding(this.mboundView15);
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
        mboundView11.invalidateAll();
        mboundView12.invalidateAll();
        mboundView13.invalidateAll();
        mboundView14.invalidateAll();
        mboundView15.invalidateAll();
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
        if (mboundView11.hasPendingBindings()) {
            return true;
        }
        if (mboundView12.hasPendingBindings()) {
            return true;
        }
        if (mboundView13.hasPendingBindings()) {
            return true;
        }
        if (mboundView14.hasPendingBindings()) {
            return true;
        }
        if (mboundView15.hasPendingBindings()) {
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
        mboundView11.setLifecycleOwner(lifecycleOwner);
        mboundView12.setLifecycleOwner(lifecycleOwner);
        mboundView13.setLifecycleOwner(lifecycleOwner);
        mboundView14.setLifecycleOwner(lifecycleOwner);
        mboundView15.setLifecycleOwner(lifecycleOwner);
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
        executeBindingsOn(mboundView11);
        executeBindingsOn(mboundView12);
        executeBindingsOn(mboundView13);
        executeBindingsOn(mboundView14);
        executeBindingsOn(mboundView15);
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