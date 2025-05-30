package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentInfAgencyBindingImpl extends FragmentInfAgencyBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lout_gig, 1);
        sViewsWithIds.put(R.id.tv_gig_title, 2);
        sViewsWithIds.put(R.id.tv_agency_name, 3);
        sViewsWithIds.put(R.id.tv_agency_about, 4);
        sViewsWithIds.put(R.id.tv_agency_contact, 5);
        sViewsWithIds.put(R.id.tv_agency_email, 6);
        sViewsWithIds.put(R.id.tv_agency_website, 7);
        sViewsWithIds.put(R.id.tv_agency_add, 8);
        sViewsWithIds.put(R.id.tv_agency_note, 9);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentInfAgencyBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private FragmentInfAgencyBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[1]
            , (androidx.core.widget.NestedScrollView) bindings[0]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[2]
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