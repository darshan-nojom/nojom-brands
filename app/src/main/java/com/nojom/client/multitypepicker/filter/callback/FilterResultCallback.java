package com.nojom.client.multitypepicker.filter.callback;

import com.nojom.client.multitypepicker.filter.entity.BaseFile;
import com.nojom.client.multitypepicker.filter.entity.Directory;

import java.util.List;

public interface FilterResultCallback<T extends BaseFile> {
    void onResult(List<Directory<T>> directories);
}
