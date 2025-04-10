package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityClientMoreBindingImpl extends ActivityClientMoreBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(29);
        sIncludes.setIncludes(1, 
            new String[] {"dialog_login_new"},
            new int[] {2},
            new int[] {com.nojom.client.R.layout.dialog_login_new});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lin_profile, 3);
        sViewsWithIds.put(R.id.ll_profile, 4);
        sViewsWithIds.put(R.id.tv_username, 5);
        sViewsWithIds.put(R.id.tv_email, 6);
        sViewsWithIds.put(R.id.img_profile, 7);
        sViewsWithIds.put(R.id.rl_profile, 8);
        sViewsWithIds.put(R.id.rl_discount, 9);
        sViewsWithIds.put(R.id.rl_balance, 10);
        sViewsWithIds.put(R.id.rl_invoice, 11);
        sViewsWithIds.put(R.id.rl_getDiscount, 12);
        sViewsWithIds.put(R.id.rl_expert, 13);
        sViewsWithIds.put(R.id.rl_feedback, 14);
        sViewsWithIds.put(R.id.rl_howitworks, 15);
        sViewsWithIds.put(R.id.rl_partner_with_us, 16);
        sViewsWithIds.put(R.id.img_partner, 17);
        sViewsWithIds.put(R.id.progress_partner, 18);
        sViewsWithIds.put(R.id.rl_what_we_do, 19);
        sViewsWithIds.put(R.id.rl_setting, 20);
        sViewsWithIds.put(R.id.rl_hire_freelancer, 21);
        sViewsWithIds.put(R.id.imgHireIcon, 22);
        sViewsWithIds.put(R.id.imgCloseHire, 23);
        sViewsWithIds.put(R.id.txtTitleHire, 24);
        sViewsWithIds.put(R.id.txtDesc, 25);
        sViewsWithIds.put(R.id.rl_playstore_agent, 26);
        sViewsWithIds.put(R.id.imgSmallIcon, 27);
        sViewsWithIds.put(R.id.txtGetApp, 28);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityClientMoreBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 29, sIncludes, sViewsWithIds));
    }
    private ActivityClientMoreBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ImageView) bindings[23]
            , (android.widget.ImageView) bindings[22]
            , (android.widget.ImageView) bindings[17]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[7]
            , (android.widget.ImageView) bindings[27]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.LinearLayout) bindings[4]
            , (com.nojom.client.databinding.DialogLoginNewBinding) bindings[2]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[18]
            , (android.widget.RelativeLayout) bindings[10]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.RelativeLayout) bindings[13]
            , (android.widget.RelativeLayout) bindings[14]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[21]
            , (android.widget.RelativeLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.widget.RelativeLayout) bindings[16]
            , (android.widget.RelativeLayout) bindings[26]
            , (android.widget.RelativeLayout) bindings[8]
            , (android.widget.RelativeLayout) bindings[20]
            , (android.widget.RelativeLayout) bindings[19]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[25]
            , (android.textview.CustomTextView) bindings[28]
            , (android.textview.CustomTextView) bindings[24]
            );
        setContainedBinding(this.login);
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        login.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (login.hasPendingBindings()) {
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
        login.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeLogin((com.nojom.client.databinding.DialogLoginNewBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeLogin(com.nojom.client.databinding.DialogLoginNewBinding Login, int fieldId) {
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
        executeBindingsOn(login);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): login
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}