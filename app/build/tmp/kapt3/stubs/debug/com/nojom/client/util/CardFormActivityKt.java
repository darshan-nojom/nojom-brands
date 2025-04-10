package com.nojom.client.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a.\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007\u001a&\u0010\u0012\u001a\u00020\t2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007\u001aJ\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0014\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001\"(\u0010\u0000\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0017"}, d2 = {"gLauncher", "Landroidx/activity/compose/ManagedActivityResultLauncher;", "Lcom/moneyhash/sdk/android/core/IntentCreationParams;", "Lcom/moneyhash/sdk/android/model/IntentDetails;", "getGLauncher", "()Landroidx/activity/compose/ManagedActivityResultLauncher;", "setGLauncher", "(Landroidx/activity/compose/ManagedActivityResultLauncher;)V", "PaymentFormDialog", "", "viewModel", "Lcom/nojom/client/util/CardFormViewModelCopy;", "intentId", "", "onDismissRequest", "Lkotlin/Function0;", "cardFormActivity", "Lcom/nojom/client/util/CardFormActivity;", "TransparentDialogScreen", "onClose", "handleIntentCase", "it", "launcher", "app_debug"})
public final class CardFormActivityKt {
    public static androidx.activity.compose.ManagedActivityResultLauncher<com.moneyhash.sdk.android.core.IntentCreationParams, com.moneyhash.sdk.android.model.IntentDetails> gLauncher;
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.activity.compose.ManagedActivityResultLauncher<com.moneyhash.sdk.android.core.IntentCreationParams, com.moneyhash.sdk.android.model.IntentDetails> getGLauncher() {
        return null;
    }
    
    public static final void setGLauncher(@org.jetbrains.annotations.NotNull()
    androidx.activity.compose.ManagedActivityResultLauncher<com.moneyhash.sdk.android.core.IntentCreationParams, com.moneyhash.sdk.android.model.IntentDetails> p0) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void TransparentDialogScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClose, @org.jetbrains.annotations.NotNull()
    java.lang.String intentId, @org.jetbrains.annotations.NotNull()
    com.nojom.client.util.CardFormActivity cardFormActivity) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PaymentFormDialog(@org.jetbrains.annotations.NotNull()
    com.nojom.client.util.CardFormViewModelCopy viewModel, @org.jetbrains.annotations.NotNull()
    java.lang.String intentId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismissRequest, @org.jetbrains.annotations.NotNull()
    com.nojom.client.util.CardFormActivity cardFormActivity) {
    }
    
    public static final void handleIntentCase(@org.jetbrains.annotations.NotNull()
    com.moneyhash.sdk.android.model.IntentDetails it, @org.jetbrains.annotations.NotNull()
    com.nojom.client.util.CardFormActivity cardFormActivity, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismissRequest, @org.jetbrains.annotations.NotNull()
    com.nojom.client.util.CardFormViewModelCopy viewModel, @org.jetbrains.annotations.NotNull()
    java.lang.String intentId, @org.jetbrains.annotations.NotNull()
    androidx.activity.compose.ManagedActivityResultLauncher<com.moneyhash.sdk.android.core.IntentCreationParams, com.moneyhash.sdk.android.model.IntentDetails> launcher) {
    }
}