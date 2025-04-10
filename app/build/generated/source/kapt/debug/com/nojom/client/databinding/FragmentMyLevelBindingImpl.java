package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentMyLevelBindingImpl extends FragmentMyLevelBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_here_blue, 1);
        sViewsWithIds.put(R.id.tv_here_silver, 2);
        sViewsWithIds.put(R.id.tv_here_gold, 3);
        sViewsWithIds.put(R.id.tv_here_vip, 4);
        sViewsWithIds.put(R.id.ll_medal_blue, 5);
        sViewsWithIds.put(R.id.img_medal_blue, 6);
        sViewsWithIds.put(R.id.ll_medal_silver, 7);
        sViewsWithIds.put(R.id.img_medal_silver, 8);
        sViewsWithIds.put(R.id.ll_medal_gold, 9);
        sViewsWithIds.put(R.id.img_medal_gold, 10);
        sViewsWithIds.put(R.id.ll_medal_vip, 11);
        sViewsWithIds.put(R.id.img_medal_vip, 12);
        sViewsWithIds.put(R.id.progress, 13);
        sViewsWithIds.put(R.id.tv_away_member, 14);
        sViewsWithIds.put(R.id.tv_how_it_works, 15);
        sViewsWithIds.put(R.id.txt_blue_label, 16);
        sViewsWithIds.put(R.id.txt_blue, 17);
        sViewsWithIds.put(R.id.txt_bronze_get, 18);
        sViewsWithIds.put(R.id.txt_pt_1, 19);
        sViewsWithIds.put(R.id.txt_silver_label, 20);
        sViewsWithIds.put(R.id.txt_silver, 21);
        sViewsWithIds.put(R.id.txt_silver_get, 22);
        sViewsWithIds.put(R.id.txt_pt_2, 23);
        sViewsWithIds.put(R.id.txt_gold, 24);
        sViewsWithIds.put(R.id.txt_gold_get, 25);
        sViewsWithIds.put(R.id.txt_pt_3, 26);
        sViewsWithIds.put(R.id.txt_vip, 27);
        sViewsWithIds.put(R.id.txt_vip_get, 28);
        sViewsWithIds.put(R.id.txt_pt_4, 29);
        sViewsWithIds.put(R.id.tv_terms_of_use, 30);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentMyLevelBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 31, sIncludes, sViewsWithIds));
    }
    private FragmentMyLevelBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.ProgressBar) bindings[13]
            , (android.widget.ScrollView) bindings[0]
            , (android.textview.CustomTextView) bindings[14]
            , (android.textview.CustomTextView) bindings[1]
            , (android.textview.CustomTextView) bindings[3]
            , (android.textview.CustomTextView) bindings[2]
            , (android.textview.CustomTextView) bindings[4]
            , (android.textview.CustomTextView) bindings[15]
            , (android.textview.CustomTextView) bindings[30]
            , (android.textview.CustomTextView) bindings[17]
            , (android.textview.CustomTextView) bindings[16]
            , (android.textview.CustomTextView) bindings[18]
            , (android.textview.CustomTextView) bindings[24]
            , (android.textview.CustomTextView) bindings[25]
            , (android.textview.CustomTextView) bindings[19]
            , (android.textview.CustomTextView) bindings[23]
            , (android.textview.CustomTextView) bindings[26]
            , (android.textview.CustomTextView) bindings[29]
            , (android.textview.CustomTextView) bindings[21]
            , (android.textview.CustomTextView) bindings[22]
            , (android.textview.CustomTextView) bindings[20]
            , (android.textview.CustomTextView) bindings[27]
            , (android.textview.CustomTextView) bindings[28]
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