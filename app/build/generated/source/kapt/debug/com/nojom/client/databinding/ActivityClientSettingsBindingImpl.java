package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityClientSettingsBindingImpl extends ActivityClientSettingsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(45);
        sIncludes.setIncludes(1, 
            new String[] {"dialog_login_new"},
            new int[] {2},
            new int[] {com.nojom.client.R.layout.dialog_login_new});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.txt_hire_lawyer_lbl, 3);
        sViewsWithIds.put(R.id.lin_profile, 4);
        sViewsWithIds.put(R.id.rel_header, 5);
        sViewsWithIds.put(R.id.ll_profile, 6);
        sViewsWithIds.put(R.id.tv_username, 7);
        sViewsWithIds.put(R.id.tv_email, 8);
        sViewsWithIds.put(R.id.img_profile, 9);
        sViewsWithIds.put(R.id.rl_profile, 10);
        sViewsWithIds.put(R.id.ic_prof, 11);
        sViewsWithIds.put(R.id.rl_balance, 12);
        sViewsWithIds.put(R.id.ic_wallet, 13);
        sViewsWithIds.put(R.id.txt_sel_wallet, 14);
        sViewsWithIds.put(R.id.img_arr_wallet, 15);
        sViewsWithIds.put(R.id.rl_invoice, 16);
        sViewsWithIds.put(R.id.ic_invo, 17);
        sViewsWithIds.put(R.id.rl_share, 18);
        sViewsWithIds.put(R.id.ic_ser, 19);
        sViewsWithIds.put(R.id.rl_rate, 20);
        sViewsWithIds.put(R.id.ic_ret, 21);
        sViewsWithIds.put(R.id.rl_trustVerification, 22);
        sViewsWithIds.put(R.id.ic_tv, 23);
        sViewsWithIds.put(R.id.txt_sel_verif, 24);
        sViewsWithIds.put(R.id.img_arr_verif, 25);
        sViewsWithIds.put(R.id.rl_notifications, 26);
        sViewsWithIds.put(R.id.ic_not, 27);
        sViewsWithIds.put(R.id.rl_language, 28);
        sViewsWithIds.put(R.id.ic_lang, 29);
        sViewsWithIds.put(R.id.txt_sel_lang, 30);
        sViewsWithIds.put(R.id.img_arr_lang, 31);
        sViewsWithIds.put(R.id.rl_contact, 32);
        sViewsWithIds.put(R.id.ic_cu, 33);
        sViewsWithIds.put(R.id.rl_terms, 34);
        sViewsWithIds.put(R.id.ic_tc, 35);
        sViewsWithIds.put(R.id.rl_privacy, 36);
        sViewsWithIds.put(R.id.ic_privacy, 37);
        sViewsWithIds.put(R.id.rl_faq, 38);
        sViewsWithIds.put(R.id.ic_faq, 39);
        sViewsWithIds.put(R.id.rl_setting, 40);
        sViewsWithIds.put(R.id.ic_set, 41);
        sViewsWithIds.put(R.id.lin_logout, 42);
        sViewsWithIds.put(R.id.btn_signout, 43);
        sViewsWithIds.put(R.id.progress_bar_logout, 44);
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

    public ActivityClientSettingsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 45, sIncludes, sViewsWithIds));
    }
    private ActivityClientSettingsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.textview.CustomTextView) bindings[43]
            , (android.widget.ImageView) bindings[33]
            , (android.widget.ImageView) bindings[39]
            , (android.widget.ImageView) bindings[17]
            , (android.widget.ImageView) bindings[29]
            , (android.widget.ImageView) bindings[27]
            , (android.widget.ImageView) bindings[37]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.ImageView) bindings[21]
            , (android.widget.ImageView) bindings[19]
            , (android.widget.ImageView) bindings[41]
            , (android.widget.ImageView) bindings[35]
            , (android.widget.ImageView) bindings[23]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.ImageView) bindings[31]
            , (android.widget.ImageView) bindings[25]
            , (android.widget.ImageView) bindings[15]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[9]
            , (android.widget.LinearLayout) bindings[42]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.LinearLayout) bindings[6]
            , (com.nojom.client.databinding.DialogLoginNewBinding) bindings[2]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[44]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[32]
            , (android.widget.RelativeLayout) bindings[38]
            , (android.widget.RelativeLayout) bindings[16]
            , (android.widget.RelativeLayout) bindings[28]
            , (android.widget.RelativeLayout) bindings[26]
            , (android.widget.RelativeLayout) bindings[36]
            , (android.widget.RelativeLayout) bindings[10]
            , (android.widget.RelativeLayout) bindings[20]
            , (android.widget.RelativeLayout) bindings[40]
            , (android.widget.RelativeLayout) bindings[18]
            , (android.widget.RelativeLayout) bindings[34]
            , (android.widget.RelativeLayout) bindings[22]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[30]
            , (android.textview.CustomTextView) bindings[24]
            , (android.textview.CustomTextView) bindings[14]
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