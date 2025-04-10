package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityInfServiceBindingImpl extends ActivityInfServiceBinding  {

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
        sViewsWithIds.put(R.id.relative, 5);
        sViewsWithIds.put(R.id.img_profile, 6);
        sViewsWithIds.put(R.id.rel_name, 7);
        sViewsWithIds.put(R.id.tv_name, 8);
        sViewsWithIds.put(R.id.img_verified, 9);
        sViewsWithIds.put(R.id.rvMedia, 10);
        sViewsWithIds.put(R.id.imgMaw, 11);
        sViewsWithIds.put(R.id.txt_title_maw, 12);
        sViewsWithIds.put(R.id.txt_maw_no, 13);
        sViewsWithIds.put(R.id.txt_offer, 14);
        sViewsWithIds.put(R.id.lin_preview, 15);
        sViewsWithIds.put(R.id.tv_aboutme, 16);
        sViewsWithIds.put(R.id.lin_view, 17);
        sViewsWithIds.put(R.id.linear_custom, 18);
        sViewsWithIds.put(R.id.txt_perc, 19);
        sViewsWithIds.put(R.id.txt_tax, 20);
        sViewsWithIds.put(R.id.et_notes, 21);
        sViewsWithIds.put(R.id.lout_header, 22);
        sViewsWithIds.put(R.id.image_container, 23);
        sViewsWithIds.put(R.id.txt_AddMore, 24);
        sViewsWithIds.put(R.id.rel_continue, 25);
        sViewsWithIds.put(R.id.btn_continue_price, 26);
        sViewsWithIds.put(R.id.progress_bar, 27);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityInfServiceBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 28, sIncludes, sViewsWithIds));
    }
    private ActivityInfServiceBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            , (android.button.CustomButton) bindings[26]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[21]
            , (android.widget.LinearLayout) bindings[23]
            , (android.widget.ImageView) bindings[3]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[11]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[6]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[17]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.LinearLayout) bindings[22]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[27]
            , (android.widget.RelativeLayout) bindings[25]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[2]
            , (androidx.recyclerview.widget.RecyclerView) bindings[10]
            , (android.widget.LinearLayout) bindings[0]
            , (android.textview.CustomTextView) bindings[4]
            , (com.nojom.client.util.ReadMoreTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[24]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[12]
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