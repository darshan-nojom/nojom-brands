package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentDescribeBindingImpl extends FragmentDescribeBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ll_progress, 1);
        sViewsWithIds.put(R.id.progress_view, 2);
        sViewsWithIds.put(R.id.blank_view, 3);
        sViewsWithIds.put(R.id.header, 4);
        sViewsWithIds.put(R.id.img_back, 5);
        sViewsWithIds.put(R.id.tv_title, 6);
        sViewsWithIds.put(R.id.txt_learn_more, 7);
        sViewsWithIds.put(R.id.img_learn_more, 8);
        sViewsWithIds.put(R.id.txt_learn_more_desc, 9);
        sViewsWithIds.put(R.id.et_describe, 10);
        sViewsWithIds.put(R.id.tv_word_perfect, 11);
        sViewsWithIds.put(R.id.tv_word_counter, 12);
        sViewsWithIds.put(R.id.rv_files, 13);
        sViewsWithIds.put(R.id.rl_attach_file, 14);
        sViewsWithIds.put(R.id.btn_attachment, 15);
        sViewsWithIds.put(R.id.btn_last_step, 16);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentDescribeBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private FragmentDescribeBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.view.View) bindings[3]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[16]
            , (android.edittext.CustomEditText) bindings[10]
            , (android.widget.RelativeLayout) bindings[4]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.LinearLayout) bindings[1]
            , (android.view.View) bindings[2]
            , (android.widget.LinearLayout) bindings[14]
            , (androidx.recyclerview.widget.RecyclerView) bindings[13]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[9]
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