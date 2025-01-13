package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentAboutProfileBindingImpl extends FragmentAboutProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.txtAllService, 1);
        sViewsWithIds.put(R.id.txtWritingService, 2);
        sViewsWithIds.put(R.id.tv_headlineTitle, 3);
        sViewsWithIds.put(R.id.tv_headline, 4);
        sViewsWithIds.put(R.id.tv_about_meTitle, 5);
        sViewsWithIds.put(R.id.tv_about_me, 6);
        sViewsWithIds.put(R.id.tv_profAdd_title, 7);
        sViewsWithIds.put(R.id.tv_profAdd, 8);
        sViewsWithIds.put(R.id.tv_location_title, 9);
        sViewsWithIds.put(R.id.tv_location, 10);
        sViewsWithIds.put(R.id.tv_phone_title, 11);
        sViewsWithIds.put(R.id.tv_phone, 12);
        sViewsWithIds.put(R.id.tv_email_title, 13);
        sViewsWithIds.put(R.id.tv_email, 14);
        sViewsWithIds.put(R.id.tv_website_title, 15);
        sViewsWithIds.put(R.id.tv_website, 16);
        sViewsWithIds.put(R.id.rv_languages, 17);
    }
    // views
    @NonNull
    private final androidx.core.widget.NestedScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentAboutProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }
    private FragmentAboutProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.recyclerview.widget.RecyclerView) bindings[17]
            , (com.nojom.client.util.ReadMoreTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[1]
            , (android.textview.CustomTextView) bindings[2]
            );
        this.mboundView0 = (androidx.core.widget.NestedScrollView) bindings[0];
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