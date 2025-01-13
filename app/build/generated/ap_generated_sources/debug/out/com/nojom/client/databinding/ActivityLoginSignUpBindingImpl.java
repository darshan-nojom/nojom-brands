package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityLoginSignUpBindingImpl extends ActivityLoginSignUpBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.img_back, 1);
        sViewsWithIds.put(R.id.segmentLoginGroup, 2);
        sViewsWithIds.put(R.id.tab_signup, 3);
        sViewsWithIds.put(R.id.tab_login, 4);
        sViewsWithIds.put(R.id.ll_login, 5);
        sViewsWithIds.put(R.id.et_username, 6);
        sViewsWithIds.put(R.id.et_password, 7);
        sViewsWithIds.put(R.id.img_l_password, 8);
        sViewsWithIds.put(R.id.btn_login, 9);
        sViewsWithIds.put(R.id.progress_bar_login, 10);
        sViewsWithIds.put(R.id.tv_forgot_password, 11);
        sViewsWithIds.put(R.id.ll_signup, 12);
        sViewsWithIds.put(R.id.et_email, 13);
        sViewsWithIds.put(R.id.et_s_username, 14);
        sViewsWithIds.put(R.id.ccp, 15);
        sViewsWithIds.put(R.id.txt_prefix, 16);
        sViewsWithIds.put(R.id.et_mobile, 17);
        sViewsWithIds.put(R.id.et_s_password, 18);
        sViewsWithIds.put(R.id.img_password, 19);
        sViewsWithIds.put(R.id.btn_signup, 20);
        sViewsWithIds.put(R.id.progress_bar_signup, 21);
        sViewsWithIds.put(R.id.rl_login_with_facebook, 22);
        sViewsWithIds.put(R.id.txt_fb_title, 23);
        sViewsWithIds.put(R.id.progress_bar_fb, 24);
        sViewsWithIds.put(R.id.rl_login_with_google, 25);
        sViewsWithIds.put(R.id.txt_google_title, 26);
        sViewsWithIds.put(R.id.progress_bar_google, 27);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityLoginSignUpBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 28, sIncludes, sViewsWithIds));
    }
    private ActivityLoginSignUpBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.button.CustomButton) bindings[9]
            , (android.button.CustomButton) bindings[20]
            , (com.nojom.client.ccp.CountryCodePicker) bindings[15]
            , (android.edittext.CustomEditText) bindings[13]
            , (android.edittext.CustomEditText) bindings[17]
            , (android.edittext.CustomEditText) bindings[7]
            , (android.edittext.CustomEditText) bindings[18]
            , (android.edittext.CustomEditText) bindings[14]
            , (android.edittext.CustomEditText) bindings[6]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[19]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[12]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[24]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[27]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[10]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[21]
            , (android.widget.RelativeLayout) bindings[22]
            , (android.widget.RelativeLayout) bindings[25]
            , (com.nojom.client.segment.SegmentedButtonGroup) bindings[2]
            , (com.nojom.client.segment.SegmentedButton) bindings[4]
            , (com.nojom.client.segment.SegmentedButton) bindings[3]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[23]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[16]
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