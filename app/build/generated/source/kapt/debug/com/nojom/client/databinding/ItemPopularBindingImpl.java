package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemPopularBindingImpl extends ItemPopularBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(19);
        sIncludes.setIncludes(0, 
            new String[] {"item_banner_lawyer"},
            new int[] {1},
            new int[] {com.nojom.client.R.layout.item_banner_lawyer});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.relview, 2);
        sViewsWithIds.put(R.id.img_profile, 3);
        sViewsWithIds.put(R.id.img_favourite, 4);
        sViewsWithIds.put(R.id.img_check, 5);
        sViewsWithIds.put(R.id.progress_bar_fav, 6);
        sViewsWithIds.put(R.id.lin_title, 7);
        sViewsWithIds.put(R.id.tv_name, 8);
        sViewsWithIds.put(R.id.tv_description, 9);
        sViewsWithIds.put(R.id.linRatingBar, 10);
        sViewsWithIds.put(R.id.ratingbar, 11);
        sViewsWithIds.put(R.id.tv_rating, 12);
        sViewsWithIds.put(R.id.tv_amount, 13);
        sViewsWithIds.put(R.id.tv_city, 14);
        sViewsWithIds.put(R.id.text_chip_attrs, 15);
        sViewsWithIds.put(R.id.tv_view_profile, 16);
        sViewsWithIds.put(R.id.progress_bar_profile, 17);
        sViewsWithIds.put(R.id.tv_chat, 18);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemPopularBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private ItemPopularBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (com.nojom.client.databinding.ItemBannerLawyerBinding) bindings[1]
            , (android.widget.ImageView) bindings[5]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[4]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[3]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.LinearLayout) bindings[7]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[6]
            , (fr.castorflex.android.circularprogressbar.CircularProgressBar) bindings[17]
            , (com.willy.ratingbar.ScaleRatingBar) bindings[11]
            , (android.widget.RelativeLayout) bindings[2]
            , (com.nojom.client.chipview.ChipView) bindings[15]
            , (android.textview.CustomTextView) bindings[13]
            , (android.textview.CustomTextView) bindings[18]
            , (android.textview.CustomTextView) bindings[14]
            , (com.nojom.client.util.ReadMoreTextView) bindings[9]
            , (android.textview.CustomTextView) bindings[8]
            , (android.textview.CustomTextView) bindings[12]
            , (android.textview.CustomTextView) bindings[16]
            );
        setContainedBinding(this.banner);
        this.linParent.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        banner.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (banner.hasPendingBindings()) {
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
        banner.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeBanner((com.nojom.client.databinding.ItemBannerLawyerBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeBanner(com.nojom.client.databinding.ItemBannerLawyerBinding Banner, int fieldId) {
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
        executeBindingsOn(banner);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): banner
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}