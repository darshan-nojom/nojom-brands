package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityOfferSummaryBindingImpl extends ActivityOfferSummaryBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(15);
        sIncludes.setIncludes(0, 
            new String[] {"toolbar_back"},
            new int[] {1},
            new int[] {com.nojom.client.R.layout.toolbar_back});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.img_profile, 2);
        sViewsWithIds.put(R.id.txt_name, 3);
        sViewsWithIds.put(R.id.img_verified, 4);
        sViewsWithIds.put(R.id.lout_describe, 5);
        sViewsWithIds.put(R.id.txt_describe, 6);
        sViewsWithIds.put(R.id.lout_budget, 7);
        sViewsWithIds.put(R.id.txt_budget, 8);
        sViewsWithIds.put(R.id.lout_deadline, 9);
        sViewsWithIds.put(R.id.txt_deadline, 10);
        sViewsWithIds.put(R.id.lout_title, 11);
        sViewsWithIds.put(R.id.txt_title, 12);
        sViewsWithIds.put(R.id.btn_last_step, 13);
        sViewsWithIds.put(R.id.progress_bar, 14);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityOfferSummaryBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private ActivityOfferSummaryBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.textview.CustomTextView) bindings[13]
            , (com.nojom.client.databinding.ToolbarBackBinding) bindings[1]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[2]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[4]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[11]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[14]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[12]
            );
        setContainedBinding(this.header);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        header.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (header.hasPendingBindings()) {
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
        header.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeHeader((com.nojom.client.databinding.ToolbarBackBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeHeader(com.nojom.client.databinding.ToolbarBackBinding Header, int fieldId) {
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
        executeBindingsOn(header);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): header
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}