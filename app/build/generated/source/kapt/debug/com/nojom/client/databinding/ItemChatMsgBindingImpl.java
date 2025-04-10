package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemChatMsgBindingImpl extends ItemChatMsgBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.day_date, 1);
        sViewsWithIds.put(R.id.frame_outgoing, 2);
        sViewsWithIds.put(R.id.tvMyMessage, 3);
        sViewsWithIds.put(R.id.rlImageSender, 4);
        sViewsWithIds.put(R.id.loutOutgoing, 5);
        sViewsWithIds.put(R.id.ivOutgoing, 6);
        sViewsWithIds.put(R.id.oProgressSender, 7);
        sViewsWithIds.put(R.id.llMyDoc, 8);
        sViewsWithIds.put(R.id.iv_my_doc, 9);
        sViewsWithIds.put(R.id.tvMyDocName, 10);
        sViewsWithIds.put(R.id.i_blur_sender, 11);
        sViewsWithIds.put(R.id.tvMyTimestamp, 12);
        sViewsWithIds.put(R.id.img_seen, 13);
        sViewsWithIds.put(R.id.frame_incoming, 14);
        sViewsWithIds.put(R.id.tvMessage, 15);
        sViewsWithIds.put(R.id.rlImageReceiver, 16);
        sViewsWithIds.put(R.id.ivIncoming, 17);
        sViewsWithIds.put(R.id.llDoc, 18);
        sViewsWithIds.put(R.id.iv_doc, 19);
        sViewsWithIds.put(R.id.tvDocName, 20);
        sViewsWithIds.put(R.id.i_blur_reciever, 21);
        sViewsWithIds.put(R.id.tvTimestamp, 22);
        sViewsWithIds.put(R.id.lout_offer, 23);
        sViewsWithIds.put(R.id.tv_title, 24);
        sViewsWithIds.put(R.id.tv_description, 25);
        sViewsWithIds.put(R.id.tv_price, 26);
        sViewsWithIds.put(R.id.lout_view, 27);
        sViewsWithIds.put(R.id.tv_view, 28);
        sViewsWithIds.put(R.id.progress_bar_view, 29);
        sViewsWithIds.put(R.id.progress_bar_view_accepted, 30);
        sViewsWithIds.put(R.id.tvTimestampOffer, 31);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemChatMsgBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 32, sIncludes, sViewsWithIds));
    }
    private ItemChatMsgBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.textview.CustomTextView) bindings[1]
            , (android.widget.RelativeLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[2]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[21]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[11]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.ImageView) bindings[19]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[17]
            , (android.widget.ImageView) bindings[9]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[6]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[23]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[27]
            , (android.widget.ProgressBar) bindings[7]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[29]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[30]
            , (android.widget.RelativeLayout) bindings[16]
            , (android.widget.RelativeLayout) bindings[4]
            , (android.textview.CustomTextView) bindings[25]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[31]
            , (android.textview.CustomTextView) bindings[24]
            , (android.textview.CustomTextView) bindings[28]
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