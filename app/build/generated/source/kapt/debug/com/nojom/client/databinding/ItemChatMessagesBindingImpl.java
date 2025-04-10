package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemChatMessagesBindingImpl extends ItemChatMessagesBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.day_date, 1);
        sViewsWithIds.put(R.id.frame_outgoing, 2);
        sViewsWithIds.put(R.id.rl_sender, 3);
        sViewsWithIds.put(R.id.tv_outgoing_message, 4);
        sViewsWithIds.put(R.id.rl_image_sender, 5);
        sViewsWithIds.put(R.id.img_outgoing, 6);
        sViewsWithIds.put(R.id.i_blur_sender, 7);
        sViewsWithIds.put(R.id.i_progress_sender, 8);
        sViewsWithIds.put(R.id.i_download_sender, 9);
        sViewsWithIds.put(R.id.rl_video_sender, 10);
        sViewsWithIds.put(R.id.video_outgoing, 11);
        sViewsWithIds.put(R.id.v_blur_sender, 12);
        sViewsWithIds.put(R.id.v_progress_sender, 13);
        sViewsWithIds.put(R.id.v_download_sender, 14);
        sViewsWithIds.put(R.id.rl_file_sender, 15);
        sViewsWithIds.put(R.id.f_progress_sender, 16);
        sViewsWithIds.put(R.id.f_download_sender, 17);
        sViewsWithIds.put(R.id.tv_file_outgoing, 18);
        sViewsWithIds.put(R.id.outgoing_date, 19);
        sViewsWithIds.put(R.id.img_seen, 20);
        sViewsWithIds.put(R.id.frame_incoming, 21);
        sViewsWithIds.put(R.id.rl_receiver, 22);
        sViewsWithIds.put(R.id.tv_incoming_message, 23);
        sViewsWithIds.put(R.id.rl_image_receiver, 24);
        sViewsWithIds.put(R.id.img_incoming, 25);
        sViewsWithIds.put(R.id.i_blur_receiver, 26);
        sViewsWithIds.put(R.id.i_progress_receiver, 27);
        sViewsWithIds.put(R.id.i_download_receiver, 28);
        sViewsWithIds.put(R.id.rl_video_receiver, 29);
        sViewsWithIds.put(R.id.video_incoming, 30);
        sViewsWithIds.put(R.id.v_blur_receiver, 31);
        sViewsWithIds.put(R.id.v_progress_receiver, 32);
        sViewsWithIds.put(R.id.v_download_receiver, 33);
        sViewsWithIds.put(R.id.rl_file_receiver, 34);
        sViewsWithIds.put(R.id.f_progress_receiver, 35);
        sViewsWithIds.put(R.id.f_download_receiver, 36);
        sViewsWithIds.put(R.id.tv_file_incoming, 37);
        sViewsWithIds.put(R.id.incoming_date, 38);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemChatMessagesBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 39, sIncludes, sViewsWithIds));
    }
    private ItemChatMessagesBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.textview.CustomTextView) bindings[1]
            , (android.widget.ImageView) bindings[36]
            , (android.widget.ImageView) bindings[17]
            , (android.widget.ProgressBar) bindings[35]
            , (android.widget.ProgressBar) bindings[16]
            , (android.widget.RelativeLayout) bindings[21]
            , (android.widget.RelativeLayout) bindings[2]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[26]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[7]
            , (android.widget.ImageView) bindings[28]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ProgressBar) bindings[27]
            , (android.widget.ProgressBar) bindings[8]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[25]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[6]
            , (android.widget.ImageView) bindings[20]
            , (android.textview.CustomTextView) bindings[38]
            , (android.textview.CustomTextView) bindings[19]
            , (android.widget.LinearLayout) bindings[34]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[24]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[22]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.RelativeLayout) bindings[29]
            , (android.widget.RelativeLayout) bindings[10]
            , (android.textview.CustomTextView) bindings[37]
            , (android.textview.CustomTextView) bindings[18]
            , (android.textview.CustomTextView) bindings[23]
            , (android.textview.CustomTextView) bindings[4]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[31]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[12]
            , (android.widget.ImageView) bindings[33]
            , (android.widget.ImageView) bindings[14]
            , (android.widget.ProgressBar) bindings[32]
            , (android.widget.ProgressBar) bindings[13]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[30]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[11]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
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