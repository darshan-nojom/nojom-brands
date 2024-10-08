package com.nojom.client.ui.helper;

import android.app.Activity;
import android.content.Intent;

import com.nojom.client.multitypepicker.Constant;
import com.nojom.client.multitypepicker.filter.entity.ImageFile;
import com.nojom.client.multitypepicker.filter.entity.NormalFile;
import com.nojom.client.multitypepicker.filter.entity.VideoFile;
import com.nojom.client.ui.BaseActivity;

import java.util.ArrayList;

public abstract class ImagePickerActivity extends BaseActivity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<ImageFile> imgPaths = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    if (imgPaths != null && imgPaths.size() > 0) {
                        onImageSelect(imgPaths);
                    }
                }
                break;
            case Constant.REQUEST_CODE_PICK_VIDEO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<VideoFile> videoPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_VIDEO);
                    if (videoPath != null && videoPath.size() > 0) {
                        onVideoSelect(videoPath);
                    }
                }
                break;
            case Constant.REQUEST_CODE_PICK_FILE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<NormalFile> docPath = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    if (docPath != null && docPath.size() > 0) {
                        onDocumentSelect(docPath);
                    }
                }
                break;
        }
    }

    public abstract void onImageSelect(ArrayList<ImageFile> imgPaths);

    public abstract void onVideoSelect(ArrayList<VideoFile> videoPath);

    public abstract void onDocumentSelect(ArrayList<NormalFile> docPath);
}
