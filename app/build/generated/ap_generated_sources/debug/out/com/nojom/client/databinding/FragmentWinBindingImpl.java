package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentWinBindingImpl extends FragmentWinBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lin_start_survey, 1);
        sViewsWithIds.put(R.id.tv_here_blue, 2);
        sViewsWithIds.put(R.id.rel_start_survey, 3);
        sViewsWithIds.put(R.id.txt_ref_link, 4);
        sViewsWithIds.put(R.id.txt_start, 5);
        sViewsWithIds.put(R.id.rl_survey_review, 6);
        sViewsWithIds.put(R.id.txt_survey, 7);
        sViewsWithIds.put(R.id.tv_profile_complete, 8);
        sViewsWithIds.put(R.id.img_right, 9);
        sViewsWithIds.put(R.id.viewDivider, 10);
        sViewsWithIds.put(R.id.tv_how_it_works, 11);
        sViewsWithIds.put(R.id.lin_survey, 12);
        sViewsWithIds.put(R.id.txt_title, 13);
        sViewsWithIds.put(R.id.rel_app_store, 14);
        sViewsWithIds.put(R.id.txt_appstore_status, 15);
        sViewsWithIds.put(R.id.img_right_appstore, 16);
        sViewsWithIds.put(R.id.rel_google_play, 17);
        sViewsWithIds.put(R.id.txt_gpay, 18);
        sViewsWithIds.put(R.id.txt_googlepay_status, 19);
        sViewsWithIds.put(R.id.img_right_googlepay, 20);
        sViewsWithIds.put(R.id.rel_google, 21);
        sViewsWithIds.put(R.id.txt_google, 22);
        sViewsWithIds.put(R.id.txt_google_status, 23);
        sViewsWithIds.put(R.id.img_right_google, 24);
        sViewsWithIds.put(R.id.rel_facebook, 25);
        sViewsWithIds.put(R.id.txt_fb, 26);
        sViewsWithIds.put(R.id.txt_facebook_status, 27);
        sViewsWithIds.put(R.id.img_right_facebook, 28);
        sViewsWithIds.put(R.id.rel_trustpilot, 29);
        sViewsWithIds.put(R.id.txt_tp, 30);
        sViewsWithIds.put(R.id.txt_trustpilot_status, 31);
        sViewsWithIds.put(R.id.img_right_trust, 32);
        sViewsWithIds.put(R.id.rel_sitejabber, 33);
        sViewsWithIds.put(R.id.txt_sj, 34);
        sViewsWithIds.put(R.id.txt_sitejabber_status, 35);
        sViewsWithIds.put(R.id.img_right_site, 36);
        sViewsWithIds.put(R.id.txt_blue_label, 37);
        sViewsWithIds.put(R.id.txt_silver_label, 38);
        sViewsWithIds.put(R.id.txt_t3, 39);
        sViewsWithIds.put(R.id.txt_t4, 40);
        sViewsWithIds.put(R.id.tv_terms_of_use, 41);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentWinBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 42, sIncludes, sViewsWithIds));
    }
    private FragmentWinBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.ImageView) bindings[28]
            , (android.widget.ImageView) bindings[24]
            , (android.widget.ImageView) bindings[20]
            , (android.widget.ImageView) bindings[36]
            , (android.widget.ImageView) bindings[32]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[14]
            , (android.widget.RelativeLayout) bindings[25]
            , (android.widget.RelativeLayout) bindings[21]
            , (android.widget.RelativeLayout) bindings[17]
            , (android.widget.RelativeLayout) bindings[33]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.RelativeLayout) bindings[29]
            , (android.widget.RelativeLayout) bindings[6]
            , (android.widget.ScrollView) bindings[0]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[41]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[37]
            , (android.textview.CustomTextView) bindings[27]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[23]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[18]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[38]
            , (android.textview.CustomTextView) bindings[35]
            , (android.textview.CustomTextView) bindings[34]
            , (android.textview.CustomTextView) bindings[5]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[39]
            , (android.textview.CustomTextView) bindings[40]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[30]
            , (android.textview.CustomTextView) bindings[31]
            , (android.view.View) bindings[10]
            );
        this.scrollview.setTag(null);
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