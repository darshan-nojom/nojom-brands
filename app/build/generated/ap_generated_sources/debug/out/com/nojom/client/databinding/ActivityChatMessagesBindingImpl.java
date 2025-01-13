package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityChatMessagesBindingImpl extends ActivityChatMessagesBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ll_header, 2);
        sViewsWithIds.put(R.id.img_back, 3);
        sViewsWithIds.put(R.id.img_profile, 4);
        sViewsWithIds.put(R.id.ll_profile, 5);
        sViewsWithIds.put(R.id.tv_name, 6);
        sViewsWithIds.put(R.id.tv_online, 7);
        sViewsWithIds.put(R.id.img_setting, 8);
        sViewsWithIds.put(R.id.view, 9);
        sViewsWithIds.put(R.id.rl_hire, 10);
        sViewsWithIds.put(R.id.tv_project_title, 11);
        sViewsWithIds.put(R.id.view2, 12);
        sViewsWithIds.put(R.id.progress, 13);
        sViewsWithIds.put(R.id.rv_messages, 14);
        sViewsWithIds.put(R.id.rlScrollDown, 15);
        sViewsWithIds.put(R.id.rlScrollDownContent, 16);
        sViewsWithIds.put(R.id.ivScrollDown, 17);
        sViewsWithIds.put(R.id.tvNewMessageCount, 18);
        sViewsWithIds.put(R.id.shimmer_layout, 19);
        sViewsWithIds.put(R.id.view3, 20);
        sViewsWithIds.put(R.id.rl_bottom, 21);
        sViewsWithIds.put(R.id.img_attach, 22);
        sViewsWithIds.put(R.id.rl_message, 23);
        sViewsWithIds.put(R.id.et_message, 24);
        sViewsWithIds.put(R.id.img_send, 25);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityChatMessagesBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds));
    }
    private ActivityChatMessagesBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.edittext.CustomEditText) bindings[24]
            , (android.widget.ImageView) bindings[22]
            , (android.widget.ImageView) bindings[3]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[4]
            , (android.widget.ImageView) bindings[25]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[17]
            , (android.widget.RelativeLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.ProgressBar) bindings[13]
            , (android.widget.LinearLayout) bindings[21]
            , (android.widget.RelativeLayout) bindings[10]
            , (android.widget.RelativeLayout) bindings[23]
            , (android.widget.RelativeLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[16]
            , (androidx.recyclerview.widget.RecyclerView) bindings[14]
            , (com.facebook.shimmer.ShimmerFrameLayout) bindings[19]
            , (android.textview.CustomTextView) bindings[6]
            , (android.widget.TextView) bindings[18]
            , (android.textview.CustomTextView) bindings[7]
            , (android.textview.CustomTextView) bindings[11]
            , (android.view.View) bindings[9]
            , (android.view.View) bindings[12]
            , (android.view.View) bindings[20]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
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