package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityOrderDetailsBindingImpl extends ActivityOrderDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.img_back, 1);
        sViewsWithIds.put(R.id.tv_close_project, 2);
        sViewsWithIds.put(R.id.lin_timeline, 3);
        sViewsWithIds.put(R.id.imgReq, 4);
        sViewsWithIds.put(R.id.progress1, 5);
        sViewsWithIds.put(R.id.txt_requested, 6);
        sViewsWithIds.put(R.id.progress2, 7);
        sViewsWithIds.put(R.id.imgApproved, 8);
        sViewsWithIds.put(R.id.progress3, 9);
        sViewsWithIds.put(R.id.txt_approved, 10);
        sViewsWithIds.put(R.id.progress4, 11);
        sViewsWithIds.put(R.id.imgDeliver, 12);
        sViewsWithIds.put(R.id.progress5, 13);
        sViewsWithIds.put(R.id.txt_delivered, 14);
        sViewsWithIds.put(R.id.progress6, 15);
        sViewsWithIds.put(R.id.imgComplete, 16);
        sViewsWithIds.put(R.id.txt_completed, 17);
        sViewsWithIds.put(R.id.txt_steps, 18);
        sViewsWithIds.put(R.id.rvStars, 19);
        sViewsWithIds.put(R.id.tv_job_title, 20);
        sViewsWithIds.put(R.id.tv_details, 21);
        sViewsWithIds.put(R.id.imgFile, 22);
        sViewsWithIds.put(R.id.txt_file_name, 23);
        sViewsWithIds.put(R.id.tv_deadline, 24);
        sViewsWithIds.put(R.id.tv_time, 25);
        sViewsWithIds.put(R.id.tv_job_id, 26);
        sViewsWithIds.put(R.id.tv_total, 27);
        sViewsWithIds.put(R.id.lbl_agency, 28);
        sViewsWithIds.put(R.id.tv_agencyFee, 29);
        sViewsWithIds.put(R.id.lbl_tax, 30);
        sViewsWithIds.put(R.id.tv_serviceTax, 31);
        sViewsWithIds.put(R.id.tv_totalPrice, 32);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityOrderDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 33, sIncludes, sViewsWithIds));
    }
    private ActivityOrderDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[8]
            , (android.widget.ImageView) bindings[1]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[16]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[12]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[22]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (android.textview.CustomTextView) bindings[28]
            , (android.textview.CustomTextView) bindings[30]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.ProgressBar) bindings[5]
            , (android.widget.ProgressBar) bindings[7]
            , (android.widget.ProgressBar) bindings[9]
            , (android.widget.ProgressBar) bindings[11]
            , (android.widget.ProgressBar) bindings[13]
            , (android.widget.ProgressBar) bindings[15]
            , (androidx.recyclerview.widget.RecyclerView) bindings[19]
            , (android.textview.CustomTextView) bindings[29]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[24]
            , (android.textview.CustomTextView) bindings[21]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[31]
            , (android.textview.CustomTextView) bindings[25]
            , (android.textview.CustomTextView) bindings[27]
            , (android.textview.CustomTextView) bindings[32]
            , (android.textview.CustomTextView) bindings[10]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[23]
            , (android.textview.CustomTextView) bindings[6]
            , (android.textview.CustomTextView) bindings[18]
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