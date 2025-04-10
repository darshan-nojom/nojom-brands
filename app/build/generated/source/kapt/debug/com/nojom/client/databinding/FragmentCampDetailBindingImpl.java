package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentCampDetailBindingImpl extends FragmentCampDetailBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_status, 1);
        sViewsWithIds.put(R.id.txt_campaign_title, 2);
        sViewsWithIds.put(R.id.txt_stars, 3);
        sViewsWithIds.put(R.id.txt_percent, 4);
        sViewsWithIds.put(R.id.txt_date, 5);
        sViewsWithIds.put(R.id.txt_time, 6);
        sViewsWithIds.put(R.id.txt_details, 7);
        sViewsWithIds.put(R.id.rvPlatform, 8);
        sViewsWithIds.put(R.id.txt_camp_id, 9);
        sViewsWithIds.put(R.id.imgFile, 10);
        sViewsWithIds.put(R.id.txt_file_name, 11);
        sViewsWithIds.put(R.id.rvTracks, 12);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentCampDetailBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }
    private FragmentCampDetailBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[10]
            , (androidx.recyclerview.widget.RecyclerView) bindings[8]
            , (androidx.recyclerview.widget.RecyclerView) bindings[12]
            , (android.textview.CustomTextView) bindings[1]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[6]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
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