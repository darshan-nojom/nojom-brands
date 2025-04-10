package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DialogTimelineBindingImpl extends DialogTimelineBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.txtCancel, 1);
        sViewsWithIds.put(R.id.rel1, 2);
        sViewsWithIds.put(R.id.img_1, 3);
        sViewsWithIds.put(R.id.v1, 4);
        sViewsWithIds.put(R.id.lin1, 5);
        sViewsWithIds.put(R.id.txt_1, 6);
        sViewsWithIds.put(R.id.txt_1_desc, 7);
        sViewsWithIds.put(R.id.rel2, 8);
        sViewsWithIds.put(R.id.img_2, 9);
        sViewsWithIds.put(R.id.v2, 10);
        sViewsWithIds.put(R.id.lin2, 11);
        sViewsWithIds.put(R.id.txt_2, 12);
        sViewsWithIds.put(R.id.txt_2_desc, 13);
        sViewsWithIds.put(R.id.txt_2_time, 14);
        sViewsWithIds.put(R.id.rel3, 15);
        sViewsWithIds.put(R.id.img_3, 16);
        sViewsWithIds.put(R.id.v3, 17);
        sViewsWithIds.put(R.id.lin3, 18);
        sViewsWithIds.put(R.id.txt_3, 19);
        sViewsWithIds.put(R.id.txt_3_desc, 20);
        sViewsWithIds.put(R.id.txt_3_time, 21);
        sViewsWithIds.put(R.id.rel4, 22);
        sViewsWithIds.put(R.id.img_4, 23);
        sViewsWithIds.put(R.id.lin4, 24);
        sViewsWithIds.put(R.id.txt_4, 25);
        sViewsWithIds.put(R.id.txt_4_desc, 26);
        sViewsWithIds.put(R.id.txt_4_time, 27);
    }
    // views
    @NonNull
    private final androidx.core.widget.NestedScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DialogTimelineBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 28, sIncludes, sViewsWithIds));
    }
    private DialogTimelineBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[3]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[9]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[16]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[23]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.LinearLayout) bindings[24]
            , (android.widget.RelativeLayout) bindings[2]
            , (android.widget.RelativeLayout) bindings[8]
            , (android.widget.RelativeLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[22]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[21]
            , (android.textview.CustomTextView) bindings[25]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[27]
            , (android.textview.CustomTextView) bindings[1]
            , (android.view.View) bindings[4]
            , (android.view.View) bindings[10]
            , (android.view.View) bindings[17]
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