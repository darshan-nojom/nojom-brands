package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityCampDataBindingImpl extends ActivityCampDataBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.anim_toolbar, 1);
        sViewsWithIds.put(R.id.rl_header, 2);
        sViewsWithIds.put(R.id.img_back, 3);
        sViewsWithIds.put(R.id.toolbar_title, 4);
        sViewsWithIds.put(R.id.lin_view, 5);
        sViewsWithIds.put(R.id.relative, 6);
        sViewsWithIds.put(R.id.img_profile, 7);
        sViewsWithIds.put(R.id.rel_name, 8);
        sViewsWithIds.put(R.id.tv_name, 9);
        sViewsWithIds.put(R.id.img_verified, 10);
        sViewsWithIds.put(R.id.default_text_input_layout, 11);
        sViewsWithIds.put(R.id.et_camp, 12);
        sViewsWithIds.put(R.id.img_infoTitle, 13);
        sViewsWithIds.put(R.id.default_text_input_layout_dat, 14);
        sViewsWithIds.put(R.id.et_name, 15);
        sViewsWithIds.put(R.id.img_infoDate, 16);
        sViewsWithIds.put(R.id.default_text_input_layout_tim, 17);
        sViewsWithIds.put(R.id.et_time, 18);
        sViewsWithIds.put(R.id.img_infoTime, 19);
        sViewsWithIds.put(R.id.default_text_input_layout_brief, 20);
        sViewsWithIds.put(R.id.et_brief, 21);
        sViewsWithIds.put(R.id.img_infoBrief, 22);
        sViewsWithIds.put(R.id.tv_attach_file, 23);
        sViewsWithIds.put(R.id.tv_attach_file_desc, 24);
        sViewsWithIds.put(R.id.lin_file, 25);
        sViewsWithIds.put(R.id.imgFile, 26);
        sViewsWithIds.put(R.id.fileName, 27);
        sViewsWithIds.put(R.id.fileDate, 28);
        sViewsWithIds.put(R.id.imgDelete, 29);
        sViewsWithIds.put(R.id.rel_btn, 30);
        sViewsWithIds.put(R.id.btn_continue_price, 31);
        sViewsWithIds.put(R.id.progress_bar, 32);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityCampDataBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 33, sIncludes, sViewsWithIds));
    }
    private ActivityCampDataBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            , (android.button.CustomButton) bindings[31]
            , (com.google.android.material.textfield.TextInputLayout) bindings[11]
            , (com.google.android.material.textfield.TextInputLayout) bindings[20]
            , (com.google.android.material.textfield.TextInputLayout) bindings[14]
            , (com.google.android.material.textfield.TextInputLayout) bindings[17]
            , (com.google.android.material.textfield.TextInputEditText) bindings[21]
            , (com.google.android.material.textfield.TextInputEditText) bindings[12]
            , (com.google.android.material.textfield.TextInputEditText) bindings[15]
            , (com.google.android.material.textfield.TextInputEditText) bindings[18]
            , (android.textview.CustomTextView) bindings[28]
            , (android.textview.CustomTextView) bindings[27]
            , (android.widget.ImageView) bindings[3]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[29]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[26]
            , (android.widget.ImageView) bindings[22]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.ImageView) bindings[19]
            , (android.widget.ImageView) bindings[13]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[7]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.LinearLayout) bindings[25]
            , (android.widget.LinearLayout) bindings[5]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[32]
            , (android.widget.RelativeLayout) bindings[30]
            , (android.widget.RelativeLayout) bindings[8]
            , (android.widget.RelativeLayout) bindings[6]
            , (android.widget.RelativeLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[0]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[23]
            , (android.textview.CustomTextView) bindings[24]
            , (android.textview.CustomTextView) bindings[9]
            );
        this.scroll.setTag(null);
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