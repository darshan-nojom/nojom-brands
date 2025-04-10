package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityViewAllGigBindingImpl extends ActivityViewAllGigBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lin_freelancer_tab, 1);
        sViewsWithIds.put(R.id.img_back, 2);
        sViewsWithIds.put(R.id.et_search, 3);
        sViewsWithIds.put(R.id.lout_filter, 4);
        sViewsWithIds.put(R.id.txt_filter, 5);
        sViewsWithIds.put(R.id.img_cat_filter, 6);
        sViewsWithIds.put(R.id.txt_filter_count, 7);
        sViewsWithIds.put(R.id.img_search, 8);
        sViewsWithIds.put(R.id.txt_cancel, 9);
        sViewsWithIds.put(R.id.rv_skills, 10);
        sViewsWithIds.put(R.id.txt_ph, 11);
        sViewsWithIds.put(R.id.img_loader, 12);
        sViewsWithIds.put(R.id.lout_main, 13);
        sViewsWithIds.put(R.id.lout_1, 14);
        sViewsWithIds.put(R.id.txt_title_1, 15);
        sViewsWithIds.put(R.id.rv_all_1, 16);
        sViewsWithIds.put(R.id.lout_view_more_1, 17);
        sViewsWithIds.put(R.id.txt_view_more_1, 18);
        sViewsWithIds.put(R.id.progress_bar_1, 19);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityViewAllGigBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds));
    }
    private ActivityViewAllGigBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatAutoCompleteTextView) bindings[3]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[4]
            , (androidx.core.widget.NestedScrollView) bindings[13]
            , (android.widget.RelativeLayout) bindings[17]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[19]
            , (androidx.recyclerview.widget.RecyclerView) bindings[16]
            , (androidx.recyclerview.widget.RecyclerView) bindings[10]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[18]
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