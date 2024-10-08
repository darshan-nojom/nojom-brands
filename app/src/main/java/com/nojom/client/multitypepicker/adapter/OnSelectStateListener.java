package com.nojom.client.multitypepicker.adapter;

public interface OnSelectStateListener<T> {
    void OnSelectStateChanged(boolean state, T file);
}
