package com.example.android.camera2basic;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;


public class FlashItemClickListener implements AdapterView.OnItemClickListener {
    private CaptureRequest.Builder mPreviewRequestBuilder;
    private CameraCaptureSession mCaptureSession;
    private Handler mBackgroundHandler;
    private PopupWindow mWindow;
    private ImageView mBtnFlash;
    private CameraCaptureSession.CaptureCallback mPreviewSessionCallback;
    private AnimationTextView mAnimationTextView;

    public FlashItemClickListener(CaptureRequest.Builder mPreviewRequestBuilder, CameraCaptureSession mCaptureSession,
                                  Handler mBackgroundHandler, PopupWindow mWindow, ImageView mBtnFlash, 
                                  CameraCaptureSession.CaptureCallback mPreviewSessionCallback,
                                  AnimationTextView mAnimationTextView) {
        this.mPreviewRequestBuilder = mPreviewRequestBuilder;
        this.mCaptureSession = mCaptureSession;
        this.mBackgroundHandler = mBackgroundHandler;
        this.mWindow = mWindow;
        this.mBtnFlash = mBtnFlash;
        this.mPreviewSessionCallback = mPreviewSessionCallback;
        this.mAnimationTextView = mAnimationTextView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        switch (position) {
            case 0:
                mBtnFlash.setImageResource(R.drawable.btn_flash_off);
                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON);
                mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
                mAnimationTextView.start("OFF", Camera2BasicFragment.WINDOW_TEXT_DISAPPEAR);
                break;
            case 1:
                mBtnFlash.setImageResource(R.drawable.btn_flash_on);
                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON);
                mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_SINGLE);
                mAnimationTextView.start("SINGLE", Camera2BasicFragment.WINDOW_TEXT_DISAPPEAR);

                break;
            case 2:
                mBtnFlash.setImageResource(R.drawable.btn_flash_all_on);
                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON);
                mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                mAnimationTextView.start("TORCH", Camera2BasicFragment.WINDOW_TEXT_DISAPPEAR);
                break;
            case 3:
                mBtnFlash.setImageResource(R.drawable.btn_flash_auto);
                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH);
                mAnimationTextView.start("AUTO_FLASH", Camera2BasicFragment.WINDOW_TEXT_DISAPPEAR);
                break;
            case 4:
                mBtnFlash.setImageResource(R.drawable.btn_flash_auto);
                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON_ALWAYS_FLASH);
                mAnimationTextView.start("ALWAYS_FLASH", Camera2BasicFragment.WINDOW_TEXT_DISAPPEAR);
                break;
        }
        updatePreview();
        mWindow.dismiss();
    }

    /**
     * 更新预览
     */
    private void updatePreview() {
        try {
            mCaptureSession.setRepeatingRequest(mPreviewRequestBuilder.build(), mPreviewSessionCallback, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("updatePreview", "ExceptionExceptionException");
        }
    }
}

