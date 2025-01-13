package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMyProfileBindingImpl extends ActivityMyProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(36);
        sIncludes.setIncludes(1, 
            new String[] {"toolbar_back"},
            new int[] {2},
            new int[] {com.nojom.client.R.layout.toolbar_back});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.img_profile, 3);
        sViewsWithIds.put(R.id.progress_bar, 4);
        sViewsWithIds.put(R.id.txt_edit_profile, 5);
        sViewsWithIds.put(R.id.rel_com_name, 6);
        sViewsWithIds.put(R.id.tv_cmp_name, 7);
        sViewsWithIds.put(R.id.img_name, 8);
        sViewsWithIds.put(R.id.rel_brandName, 9);
        sViewsWithIds.put(R.id.tv_cmp_brand, 10);
        sViewsWithIds.put(R.id.img_username, 11);
        sViewsWithIds.put(R.id.rel_contName, 12);
        sViewsWithIds.put(R.id.tv_cmp_contact, 13);
        sViewsWithIds.put(R.id.img_website, 14);
        sViewsWithIds.put(R.id.rel_email, 15);
        sViewsWithIds.put(R.id.tv_email, 16);
        sViewsWithIds.put(R.id.img_gen, 17);
        sViewsWithIds.put(R.id.rel_uName, 18);
        sViewsWithIds.put(R.id.tv_uName, 19);
        sViewsWithIds.put(R.id.img_un, 20);
        sViewsWithIds.put(R.id.rel_mobile, 21);
        sViewsWithIds.put(R.id.tv_mobile, 22);
        sViewsWithIds.put(R.id.img_cou, 23);
        sViewsWithIds.put(R.id.rel_crn, 24);
        sViewsWithIds.put(R.id.tv_comNo, 25);
        sViewsWithIds.put(R.id.img_tc, 26);
        sViewsWithIds.put(R.id.lin_socialMedia, 27);
        sViewsWithIds.put(R.id.switchVat, 28);
        sViewsWithIds.put(R.id.rel_aboutUs, 29);
        sViewsWithIds.put(R.id.img_ab, 30);
        sViewsWithIds.put(R.id.lin_verification, 31);
        sViewsWithIds.put(R.id.img_so, 32);
        sViewsWithIds.put(R.id.rel_submit_verification, 33);
        sViewsWithIds.put(R.id.tv_editProfile, 34);
        sViewsWithIds.put(R.id.progress, 35);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMyProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 36, sIncludes, sViewsWithIds));
    }
    private ActivityMyProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ImageView) bindings[30]
            , (android.widget.ImageView) bindings[23]
            , (android.widget.ImageView) bindings[17]
            , (android.widget.ImageView) bindings[8]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[3]
            , (android.widget.ImageView) bindings[32]
            , (android.widget.ImageView) bindings[26]
            , (android.widget.ImageView) bindings[20]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.ImageView) bindings[14]
            , (android.widget.RelativeLayout) bindings[27]
            , (android.widget.LinearLayout) bindings[31]
            , (android.widget.ProgressBar) bindings[35]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[4]
            , (android.widget.RelativeLayout) bindings[29]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.RelativeLayout) bindings[6]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[24]
            , (android.widget.RelativeLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[21]
            , (android.widget.RelativeLayout) bindings[33]
            , (android.widget.RelativeLayout) bindings[18]
            , (android.widget.Switch) bindings[28]
            , (com.nojom.client.databinding.ToolbarBackBinding) bindings[2]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[25]
            , (android.textview.CustomTextView) bindings[34]
            , (android.textview.CustomTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[5]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        setContainedBinding(this.toolbar);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        toolbar.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (toolbar.hasPendingBindings()) {
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
        toolbar.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeToolbar((com.nojom.client.databinding.ToolbarBackBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeToolbar(com.nojom.client.databinding.ToolbarBackBinding Toolbar, int fieldId) {
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
        executeBindingsOn(toolbar);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): toolbar
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}