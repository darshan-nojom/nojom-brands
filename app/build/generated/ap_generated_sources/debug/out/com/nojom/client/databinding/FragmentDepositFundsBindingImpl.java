package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentDepositFundsBindingImpl extends FragmentDepositFundsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ll_checkout, 1);
        sViewsWithIds.put(R.id.rel_redeemBalanceView, 2);
        sViewsWithIds.put(R.id.tv_balance, 3);
        sViewsWithIds.put(R.id.ll_redeem_view, 4);
        sViewsWithIds.put(R.id.et_redeem_amount, 5);
        sViewsWithIds.put(R.id.tv_apply_redeem, 6);
        sViewsWithIds.put(R.id.rlAvailableBalance, 7);
        sViewsWithIds.put(R.id.rlAddCard, 8);
        sViewsWithIds.put(R.id.tvAddCard, 9);
        sViewsWithIds.put(R.id.tvNext, 10);
        sViewsWithIds.put(R.id.rlAddPaypal, 11);
        sViewsWithIds.put(R.id.tvAddPaypal, 12);
        sViewsWithIds.put(R.id.tvPaypalNext, 13);
        sViewsWithIds.put(R.id.lin_ban_transfer, 14);
        sViewsWithIds.put(R.id.et_senderName, 15);
        sViewsWithIds.put(R.id.et_bankName, 16);
        sViewsWithIds.put(R.id.etCardNumber, 17);
        sViewsWithIds.put(R.id.etTxnAmount, 18);
        sViewsWithIds.put(R.id.etTxnDate, 19);
        sViewsWithIds.put(R.id.etRefNo, 20);
        sViewsWithIds.put(R.id.etNote, 21);
        sViewsWithIds.put(R.id.txt_attach, 22);
        sViewsWithIds.put(R.id.rel_selected_crn, 23);
        sViewsWithIds.put(R.id.imgFile, 24);
        sViewsWithIds.put(R.id.txt_fileName, 25);
        sViewsWithIds.put(R.id.txt_date, 26);
        sViewsWithIds.put(R.id.imgDownload, 27);
        sViewsWithIds.put(R.id.imgDelete, 28);
        sViewsWithIds.put(R.id.tv_job_id, 29);
        sViewsWithIds.put(R.id.linear, 30);
        sViewsWithIds.put(R.id.tv_deposit_amount, 31);
        sViewsWithIds.put(R.id.tv_deposit_fee, 32);
        sViewsWithIds.put(R.id.rl_redeem, 33);
        sViewsWithIds.put(R.id.tv_return_redeem, 34);
        sViewsWithIds.put(R.id.tv_redeem, 35);
        sViewsWithIds.put(R.id.rl_promocode, 36);
        sViewsWithIds.put(R.id.tv_promocode_title, 37);
        sViewsWithIds.put(R.id.tv_edit_promo, 38);
        sViewsWithIds.put(R.id.tv_promoAmount, 39);
        sViewsWithIds.put(R.id.tv_total, 40);
        sViewsWithIds.put(R.id.tv_promocode, 41);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentDepositFundsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 42, sIncludes, sViewsWithIds));
    }
    private FragmentDepositFundsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.edittext.CustomEditText) bindings[16]
            , (android.edittext.CustomEditText) bindings[17]
            , (android.edittext.CustomEditText) bindings[21]
            , (android.edittext.CustomEditText) bindings[5]
            , (android.edittext.CustomEditText) bindings[20]
            , (android.edittext.CustomEditText) bindings[15]
            , (android.edittext.CustomEditText) bindings[18]
            , (android.edittext.CustomEditText) bindings[19]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[28]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[27]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[24]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[30]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.RelativeLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[23]
            , (android.widget.RelativeLayout) bindings[8]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[36]
            , (android.widget.RelativeLayout) bindings[33]
            , (android.textview.CustomTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[31]
            , (android.textview.CustomTextView) bindings[32]
            , (android.textview.CustomTextView) bindings[38]
            , (android.textview.CustomTextView) bindings[29]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.ImageView) bindings[13]
            , (android.textview.CustomTextView) bindings[39]
            , (android.textview.CustomTextView) bindings[41]
            , (android.textview.CustomTextView) bindings[37]
            , (android.textview.CustomTextView) bindings[35]
            , (android.textview.CustomTextView) bindings[34]
            , (android.textview.CustomTextView) bindings[40]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[25]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
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