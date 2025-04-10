package com.nojom.client.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\r2\u0014\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u001f\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u001eJ$\u0010 \u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\r2\u0014\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u001f\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u001eJ\u000e\u0010!\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\rJ\b\u0010\"\u001a\u00020\u001bH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000f\"\u0004\b\u0015\u0010\u0011R \u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000f\"\u0004\b\u0019\u0010\u0011\u00a8\u0006#"}, d2 = {"Lcom/nojom/client/util/CardFormViewModelCopy;", "Landroidx/lifecycle/ViewModel;", "()V", "cardForm", "Lcom/moneyhash/sdk/android/vault/CardForm;", "getCardForm", "()Lcom/moneyhash/sdk/android/vault/CardForm;", "setCardForm", "(Lcom/moneyhash/sdk/android/vault/CardForm;)V", "moneyHash", "Lcom/moneyhash/sdk/android/core/MoneyHashSDK;", "mutableError", "Landroidx/lifecycle/MutableLiveData;", "", "getMutableError", "()Landroidx/lifecycle/MutableLiveData;", "setMutableError", "(Landroidx/lifecycle/MutableLiveData;)V", "mutableProgress", "", "getMutableProgress", "setMutableProgress", "mutableSuccess", "Lcom/moneyhash/sdk/android/model/IntentDetails;", "getMutableSuccess", "setMutableSuccess", "handleFormPayment", "", "intentId", "launcher", "Landroidx/activity/compose/ManagedActivityResultLauncher;", "Lcom/moneyhash/sdk/android/core/IntentCreationParams;", "handleIntentPayment", "handlePayment", "setupCardForm", "app_debug"})
public final class CardFormViewModelCopy extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.Nullable()
    private com.moneyhash.sdk.android.vault.CardForm cardForm;
    @org.jetbrains.annotations.NotNull()
    private final com.moneyhash.sdk.android.core.MoneyHashSDK moneyHash = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<com.moneyhash.sdk.android.model.IntentDetails> mutableSuccess;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.Boolean> mutableProgress;
    @org.jetbrains.annotations.NotNull()
    private androidx.lifecycle.MutableLiveData<java.lang.String> mutableError;
    
    public CardFormViewModelCopy() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.moneyhash.sdk.android.vault.CardForm getCardForm() {
        return null;
    }
    
    public final void setCardForm(@org.jetbrains.annotations.Nullable()
    com.moneyhash.sdk.android.vault.CardForm p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.moneyhash.sdk.android.model.IntentDetails> getMutableSuccess() {
        return null;
    }
    
    public final void setMutableSuccess(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<com.moneyhash.sdk.android.model.IntentDetails> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> getMutableProgress() {
        return null;
    }
    
    public final void setMutableProgress(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getMutableError() {
        return null;
    }
    
    public final void setMutableError(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.MutableLiveData<java.lang.String> p0) {
    }
    
    private final void setupCardForm() {
    }
    
    public final void handlePayment(@org.jetbrains.annotations.NotNull()
    java.lang.String intentId) {
    }
    
    public final void handleIntentPayment(@org.jetbrains.annotations.NotNull()
    java.lang.String intentId, @org.jetbrains.annotations.NotNull()
    androidx.activity.compose.ManagedActivityResultLauncher<com.moneyhash.sdk.android.core.IntentCreationParams, com.moneyhash.sdk.android.model.IntentDetails> launcher) {
    }
    
    public final void handleFormPayment(@org.jetbrains.annotations.NotNull()
    java.lang.String intentId, @org.jetbrains.annotations.NotNull()
    androidx.activity.compose.ManagedActivityResultLauncher<com.moneyhash.sdk.android.core.IntentCreationParams, com.moneyhash.sdk.android.model.IntentDetails> launcher) {
    }
}