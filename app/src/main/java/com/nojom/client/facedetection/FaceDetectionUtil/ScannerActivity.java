package com.nojom.client.facedetection.FaceDetectionUtil;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.nojom.client.R;
import com.nojom.client.facedetection.FaceCenterCircleView.FaceCenterCrop;
import com.nojom.client.facedetection.FaceDetectionUtil.common.CameraSource;
import com.nojom.client.facedetection.FaceDetectionUtil.common.CameraSourcePreview;
import com.nojom.client.facedetection.FaceDetectionUtil.common.FrameMetadata;
import com.nojom.client.facedetection.FaceDetectionUtil.common.GraphicOverlay;
import com.nojom.client.facedetection.ProgressBarUtil.Imageutils;
import com.nojom.client.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.nojom.client.facedetection.ProgressBarUtil.FaceDetectionScanner.Constants.KEY_CAMERA_PERMISSION_GRANTED;
import static com.nojom.client.facedetection.ProgressBarUtil.FaceDetectionScanner.Constants.PERMISSION_REQUEST_CAMERA;


public class ScannerActivity extends AppCompatActivity {

    String TAG = "ScannerActivity";
    FaceDetectionProcessor faceDetectionProcessor;
    FaceDetectionResultListener faceDetectionResultListener = null;
    Bitmap bmpCapturedImage;
    List<FirebaseVisionFace> capturedFaces;
    FaceCenterCrop faceCenterCrop;
    FaceCenterCrop.FaceCenterCropListener faceCenterCropListener;
    boolean isComplete;
    GraphicOverlay barcodeOverlay;
    CameraSourcePreview preview;
    Button btnCapture;
    private CameraSource mCameraSource = null;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (preview != null)
                createCameraSource();

        }
    };
    private final Runnable mMessageSender = () -> {
        Log.d(TAG, "mMessageSender: ");
        Message msg = mHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_CAMERA_PERMISSION_GRANTED, false);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (getWindow() != null) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            Log.e(TAG, "Barcode scanner could not go into fullscreen mode!");
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        barcodeOverlay = findViewById(R.id.barcodeOverlay);
        preview = findViewById(R.id.preview);
        btnCapture = findViewById(R.id.btnCapture);

        faceCenterCrop = new FaceCenterCrop(this, 100, 100, 1);

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (faceCenterCrop != null)
                    faceCenterCrop.transform(bmpCapturedImage, faceCenterCrop.getCenterPoint(capturedFaces), getFaceCropResult());
            }
        });


        if (preview != null)
            if (preview.isPermissionGranted(true, mMessageSender))
                new Thread(mMessageSender).start();
    }

    private void createCameraSource() {

        // To initialise the detector

        FirebaseVisionFaceDetectorOptions options =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .enableTracking()
                        .build();

        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance().getVisionFaceDetector(options);

        // To connect the camera resource with the detector

        mCameraSource = new CameraSource(this, barcodeOverlay);
        mCameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);


        // FaceContourDetectorProcessor faceDetectionProcessor = new FaceContourDetectorProcessor(detector);

        faceDetectionProcessor = new FaceDetectionProcessor(detector);
        faceDetectionProcessor.setFaceDetectionResultListener(getFaceDetectionListener());

        mCameraSource.setMachineLearningFrameProcessor(faceDetectionProcessor);

        startCameraSource();
    }

    private FaceDetectionResultListener getFaceDetectionListener() {
        if (faceDetectionResultListener == null)
            faceDetectionResultListener = new FaceDetectionResultListener() {
                @Override
                public void onSuccess(@Nullable Bitmap originalCameraImage, @NonNull List<FirebaseVisionFace> faces, @NonNull FrameMetadata frameMetadata, @NonNull GraphicOverlay graphicOverlay) {
                    boolean isEnable;
                    isEnable = faces.size() > 0;

                    for (FirebaseVisionFace face : faces) {

                        // To get the results

                        Log.d(TAG, "Face bounds : " + face.getBoundingBox());

                        // To get this, we have to set the ClassificationMode attribute as ALL_CLASSIFICATIONS

                        Log.d(TAG, "Left eye open probability : " + face.getLeftEyeOpenProbability());
                        Log.d(TAG, "Right eye open probability : " + face.getRightEyeOpenProbability());
                        Log.d(TAG, "Smiling probability : " + face.getSmilingProbability());

                        // To get this, we have to enableTracking

                        Log.d(TAG, "Face ID : " + face.getTrackingId());

                    }

                    runOnUiThread(() -> {
                        Log.d(TAG, "button enable true ");
                        bmpCapturedImage = originalCameraImage;
                        capturedFaces = faces;
                        btnCapture.setEnabled(isEnable);
                    });
                }

                @Override
                public void onFailure(@NonNull Exception e) {

                }
            };

        return faceDetectionResultListener;
    }

    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());

        Log.d(TAG, "startCameraSource: " + code);

        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, PERMISSION_REQUEST_CAMERA);
            dlg.show();
        }

        if (mCameraSource != null && preview != null && barcodeOverlay != null) {
            try {
                Log.d(TAG, "startCameraSource: ");
                preview.start(mCameraSource, barcodeOverlay);
            } catch (IOException e) {
                Log.d(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        } else
            Log.d(TAG, "startCameraSource: not started");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: " + requestCode);
        preview.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (preview != null)
            preview.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraSource != null) {
            mCameraSource.release();
        }
    }

    private FaceCenterCrop.FaceCenterCropListener getFaceCropResult() {
        if (faceCenterCropListener == null)
            faceCenterCropListener = new FaceCenterCrop.FaceCenterCropListener() {
                @Override
                public void onTransform(Bitmap updatedBitmap) {


                    Log.d(TAG, "onTransform: ");

                    try {
                        File capturedFile = new File(getFilesDir(), "newImage.jpg");

                        Imageutils imageutils = new Imageutils(ScannerActivity.this);
                        imageutils.store_image(capturedFile, updatedBitmap);

                        Intent currentIntent = getIntent();
                        currentIntent.putExtra("image", capturedFile.getAbsolutePath());
                        setResult(RESULT_OK, currentIntent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure() {
                    Utils.toastMessage(ScannerActivity.this, "No face found");
                }
            };

        return faceCenterCropListener;
    }

}
