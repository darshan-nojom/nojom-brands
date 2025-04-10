package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityGigDetailsBindingImpl extends ActivityGigDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.vp_details_images, 1);
        sViewsWithIds.put(R.id.img_back, 2);
        sViewsWithIds.put(R.id.img_favourite, 3);
        sViewsWithIds.put(R.id.img_check, 4);
        sViewsWithIds.put(R.id.progress_bar_fav, 5);
        sViewsWithIds.put(R.id.img_download, 6);
        sViewsWithIds.put(R.id.indicator_images, 7);
        sViewsWithIds.put(R.id.lout_gig_details, 8);
        sViewsWithIds.put(R.id.img_profile, 9);
        sViewsWithIds.put(R.id.img_status, 10);
        sViewsWithIds.put(R.id.tv_name, 11);
        sViewsWithIds.put(R.id.linRatingBar, 12);
        sViewsWithIds.put(R.id.ratingbar, 13);
        sViewsWithIds.put(R.id.tv_rating, 14);
        sViewsWithIds.put(R.id.tv_view_all, 15);
        sViewsWithIds.put(R.id.viewAll, 16);
        sViewsWithIds.put(R.id.progress_bar_profile, 17);
        sViewsWithIds.put(R.id.tv_title, 18);
        sViewsWithIds.put(R.id.tv_description, 19);
        sViewsWithIds.put(R.id.loutNormalGig, 20);
        sViewsWithIds.put(R.id.llMain, 21);
        sViewsWithIds.put(R.id.llSubMain, 22);
        sViewsWithIds.put(R.id.tv_package_title, 23);
        sViewsWithIds.put(R.id.tv_package_desc, 24);
        sViewsWithIds.put(R.id.tv_delivery_days, 25);
        sViewsWithIds.put(R.id.tv_revisions_days, 26);
        sViewsWithIds.put(R.id.rv_gig_item, 27);
        sViewsWithIds.put(R.id.img_minus, 28);
        sViewsWithIds.put(R.id.et_quantity, 29);
        sViewsWithIds.put(R.id.img_plus, 30);
        sViewsWithIds.put(R.id.tv_delivery_total_days, 31);
        sViewsWithIds.put(R.id.txtAllService, 32);
        sViewsWithIds.put(R.id.txtWritingService, 33);
        sViewsWithIds.put(R.id.lout_custom, 34);
        sViewsWithIds.put(R.id.rv_social_gig, 35);
        sViewsWithIds.put(R.id.loutCustomPrice, 36);
        sViewsWithIds.put(R.id.tv_custom_price, 37);
        sViewsWithIds.put(R.id.loutCustomDelivery, 38);
        sViewsWithIds.put(R.id.img_custom_infom, 39);
        sViewsWithIds.put(R.id.img_custom_back, 40);
        sViewsWithIds.put(R.id.et_custom_delivery, 41);
        sViewsWithIds.put(R.id.img_custom_next, 42);
        sViewsWithIds.put(R.id.rv_custom_gig, 43);
        sViewsWithIds.put(R.id.lout_price, 44);
        sViewsWithIds.put(R.id.tv_price, 45);
        sViewsWithIds.put(R.id.lout_bottom, 46);
        sViewsWithIds.put(R.id.lout_chat, 47);
        sViewsWithIds.put(R.id.img_profile_chat, 48);
        sViewsWithIds.put(R.id.lout_reject, 49);
        sViewsWithIds.put(R.id.txt_reject, 50);
        sViewsWithIds.put(R.id.progress_reject, 51);
        sViewsWithIds.put(R.id.lout_continue, 52);
        sViewsWithIds.put(R.id.txt_continue, 53);
        sViewsWithIds.put(R.id.tv_amount, 54);
        sViewsWithIds.put(R.id.tv_view, 55);
        sViewsWithIds.put(R.id.tv_dis_amount, 56);
        sViewsWithIds.put(R.id.progress_continue, 57);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityGigDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 58, sIncludes, sViewsWithIds));
    }
    private ActivityGigDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.textview.CustomTextView) bindings[41]
            , (android.textview.CustomTextView) bindings[29]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.ImageView) bindings[40]
            , (android.widget.ImageView) bindings[39]
            , (android.widget.ImageView) bindings[42]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[28]
            , (android.widget.ImageView) bindings[30]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[9]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[48]
            , (android.widget.ImageView) bindings[10]
            , (com.nojom.client.ui.autoscrollviewpager.InkPageIndicator) bindings[7]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[21]
            , (android.widget.LinearLayout) bindings[22]
            , (android.widget.LinearLayout) bindings[46]
            , (android.widget.LinearLayout) bindings[47]
            , (android.widget.LinearLayout) bindings[52]
            , (android.widget.LinearLayout) bindings[34]
            , (android.widget.LinearLayout) bindings[38]
            , (android.widget.LinearLayout) bindings[36]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[20]
            , (android.widget.LinearLayout) bindings[44]
            , (android.widget.RelativeLayout) bindings[49]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[5]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[17]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[57]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[51]
            , (com.willy.ratingbar.ScaleRatingBar) bindings[13]
            , (androidx.recyclerview.widget.RecyclerView) bindings[43]
            , (androidx.recyclerview.widget.RecyclerView) bindings[27]
            , (androidx.recyclerview.widget.RecyclerView) bindings[35]
            , (android.textview.CustomTextView) bindings[54]
            , (android.textview.CustomTextView) bindings[37]
            , (android.textview.CustomTextView) bindings[25]
            , (android.textview.CustomTextView) bindings[31]
            , (com.nojom.client.util.ReadMoreTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[56]
            , (android.textview.CustomTextView) bindings[11]
            , (android.textview.CustomTextView) bindings[24]
            , (android.textview.CustomTextView) bindings[23]
            , (android.textview.CustomTextView) bindings[45]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[18]
            , (android.textview.CustomTextView) bindings[55]
            , (android.widget.RelativeLayout) bindings[15]
            , (android.textview.CustomTextView) bindings[32]
            , (android.textview.CustomTextView) bindings[53]
            , (android.textview.CustomTextView) bindings[50]
            , (android.textview.CustomTextView) bindings[33]
            , (android.textview.CustomTextView) bindings[16]
            , (com.nojom.client.ui.autoscrollviewpager.LoopingViewPager) bindings[1]
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