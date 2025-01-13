package com.nojom.client.databinding;
import com.nojom.client.R;
import com.nojom.client.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityPostJobBindingImpl extends ActivityPostJobBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(8);
        sIncludes.setIncludes(0, 
            new String[] {"toolbar_progress_next"},
            new int[] {2},
            new int[] {com.nojom.client.R.layout.toolbar_progress_next});
        sIncludes.setIncludes(1, 
            new String[] {"dialog_login_new"},
            new int[] {3},
            new int[] {com.nojom.client.R.layout.dialog_login_new});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ll_progress, 4);
        sViewsWithIds.put(R.id.progress_view, 5);
        sViewsWithIds.put(R.id.blank_view, 6);
        sViewsWithIds.put(R.id.container, 7);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPostJobBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private ActivityPostJobBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.view.View) bindings[6]
            , (android.widget.FrameLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[4]
            , (com.nojom.client.databinding.DialogLoginNewBinding) bindings[3]
            , (android.view.View) bindings[5]
            , (com.nojom.client.databinding.ToolbarProgressNextBinding) bindings[2]
            );
        this.linPlaceholderLogin.setTag(null);
        setContainedBinding(this.login);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        setContainedBinding(this.toolBack);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        toolBack.invalidateAll();
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
        if (toolBack.hasPendingBindings()) {
            return true;
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
        toolBack.setLifecycleOwner(lifecycleOwner);
        login.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeToolBack((com.nojom.client.databinding.ToolbarProgressNextBinding) object, fieldId);
            case 1 :
                return onChangeLogin((com.nojom.client.databinding.DialogLoginNewBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeToolBack(com.nojom.client.databinding.ToolbarProgressNextBinding ToolBack, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLogin(com.nojom.client.databinding.DialogLoginNewBinding Login, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
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
        executeBindingsOn(toolBack);
        executeBindingsOn(login);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): toolBack
        flag 1 (0x2L): login
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}