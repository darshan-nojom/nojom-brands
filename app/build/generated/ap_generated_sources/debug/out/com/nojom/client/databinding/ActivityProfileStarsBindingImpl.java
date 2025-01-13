package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityProfileStarsBindingImpl extends ActivityProfileStarsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.appbar, 1);
        sViewsWithIds.put(R.id.collapsing_toolbar, 2);
        sViewsWithIds.put(R.id.relative, 3);
        sViewsWithIds.put(R.id.img_profile, 4);
        sViewsWithIds.put(R.id.rel_name, 5);
        sViewsWithIds.put(R.id.tv_name, 6);
        sViewsWithIds.put(R.id.img_verified, 7);
        sViewsWithIds.put(R.id.tv_userName, 8);
        sViewsWithIds.put(R.id.tv_link, 9);
        sViewsWithIds.put(R.id.txt_offer, 10);
        sViewsWithIds.put(R.id.lin_preview, 11);
        sViewsWithIds.put(R.id.anim_toolbar, 12);
        sViewsWithIds.put(R.id.rl_header, 13);
        sViewsWithIds.put(R.id.img_back, 14);
        sViewsWithIds.put(R.id.img_profile_toolbar, 15);
        sViewsWithIds.put(R.id.toolbar_title, 16);
        sViewsWithIds.put(R.id.img_save, 17);
        sViewsWithIds.put(R.id.img_share, 18);
        sViewsWithIds.put(R.id.lin_view, 19);
        sViewsWithIds.put(R.id.linear_custom, 20);
        sViewsWithIds.put(R.id.rel_continue, 21);
        sViewsWithIds.put(R.id.btn_continue_price, 22);
        sViewsWithIds.put(R.id.progress_bar, 23);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityProfileStarsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }
    private ActivityProfileStarsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.Toolbar) bindings[12]
            , (com.google.android.material.appbar.AppBarLayout) bindings[1]
            , (android.button.CustomButton) bindings[22]
            , (com.google.android.material.appbar.CollapsingToolbarLayout) bindings[2]
            , (android.widget.ImageView) bindings[14]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[4]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[15]
            , (android.widget.ImageView) bindings[17]
            , (android.widget.ImageView) bindings[18]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.LinearLayout) bindings[20]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[23]
            , (android.widget.RelativeLayout) bindings[21]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.RelativeLayout) bindings[13]
            , (android.widget.RelativeLayout) bindings[0]
            , (android.textview.CustomTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[10]
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