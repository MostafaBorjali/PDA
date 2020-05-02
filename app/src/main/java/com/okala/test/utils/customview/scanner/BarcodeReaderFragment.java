package com.okala.test.utils.customview.scanner;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.button.MaterialButton;
import com.okala.test.R;
import com.okala.test.databinding.FragmentUnderReviewBinding;
import com.okala.test.utils.customview.scanner.camera.CameraSource;
import com.okala.test.utils.customview.scanner.camera.CameraSourcePreview;
import com.okala.test.utils.customview.scanner.camera.GraphicOverlay;

import org.koin.core.KoinComponent;

import java.io.IOException;
import java.util.List;

public abstract class BarcodeReaderFragment extends Fragment implements View.OnTouchListener, BarcodeGraphicTracker.BarcodeGraphicTrackerListener, KoinComponent {

    public static final String BarcodeObject = "Barcode";
    protected static final String TAG = BarcodeReaderFragment.class.getSimpleName();
    protected static final String KEY_AUTO_FOCUS = "key_auto_focus";
    protected static final String KEY_USE_FLASH = "key_use_flash";
    private static final String KEY_SCAN_OVERLAY_VISIBILITY = "key_scan_overlay_visibility";
    private static final int RC_HANDLE_GMS = 9001;
    private static final int PERMISSION_CALLBACK_CONSTANT = 101;
    private static final int REQUEST_PERMISSION_SETTING = 102;
    protected boolean autoFocus = true;
    protected boolean useFlash = false;
    Handler h = new Handler();
    private String beepSoundFile;
    private boolean isPaused = false;
    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    Runnable runnablePause = new Runnable() {
        @Override
        public void run() {

            mPreview.stop();
            h.removeCallbacks(this);
        }
    };
    private ImageView flashLight;
    private ImageButton searchBarcode;
    private EditText edSearchBarcode;
    private TextView txtMessageEditTextSearchBarcode;
    private boolean isFlashOn;
    private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            startCameraSource();
            h.removeCallbacks(this);
        }
    };
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private BarcodeReaderListener mListener;
    private SharedPreferences permissionStatus;
    private LinearLayout codePayment;
    private MaterialButton cameraPermitionBtn;
    private FrameLayout cameraPermitionLay;
    private LinearLayout map;

    //  private MainViewModel mViewModel;
    private boolean sentToSettings = false;
    private ScannerOverlay mScanOverlay;
    private int scanOverlayVisibility;

    public BarcodeReaderFragment() {

    }

    protected abstract void barcode(Barcode barcode);

    protected abstract void onCreateView();

    protected abstract void searchBarcodeButton(String barcode);

    public void setListener(BarcodeReaderListener barcodeReaderListener) {
        mListener = barcodeReaderListener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = this.getArguments();
        if (arguments != null) {
            this.autoFocus = arguments.getBoolean(KEY_AUTO_FOCUS, false);
            this.useFlash = arguments.getBoolean(KEY_USE_FLASH, false);
            this.scanOverlayVisibility = arguments.getInt(KEY_SCAN_OVERLAY_VISIBILITY, View.VISIBLE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentUnderReviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_under_review, container, false);
        View view = binding.getRoot();
        //  mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        permissionStatus = getActivity().getSharedPreferences("permissionStatus", getActivity().MODE_PRIVATE);
        mPreview = view.findViewById(R.id.preview);
        mGraphicOverlay = view.findViewById(R.id.graphicOverlay);
        mScanOverlay = view.findViewById(R.id.scan_overlay);
        flashLight = view.findViewById(R.id.flashlight);
        cameraPermitionLay = view.findViewById(R.id.cameraPermition);
        cameraPermitionBtn = view.findViewById(R.id.cameraPermitionBtn);
        searchBarcode = view.findViewById(R.id.barcodeSearch);
        edSearchBarcode = view.findViewById(R.id.edBarcode);
        txtMessageEditTextSearchBarcode = view.findViewById(R.id.txtMessageEditTextSearchBarcode);
        mScanOverlay.setVisibility(scanOverlayVisibility);
        gestureDetector = new GestureDetector(getActivity(), new CaptureGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(getActivity(), new ScaleListener());
        view.setOnTouchListener(this);
        onCreateView();
        edSearchBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edSearchBarcode.setBackground(getResources().getDrawable(R.drawable.back_edittext_search));
                searchBarcode.setBackground(getResources().getDrawable(R.drawable.background_search));
                txtMessageEditTextSearchBarcode.setText(getString(R.string.searchBarcodeMessage));
                txtMessageEditTextSearchBarcode.setTextColor(getResources().getColor(R.color.textcolor));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        flashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFlashOn = !isFlashOn;
                setUseFlash(isFlashOn);

            }
        });

        searchBarcode.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (!edSearchBarcode.getText().toString().isEmpty()){
                    searchBarcodeButton(edSearchBarcode.getText().toString());
                }else {
                  edSearchBarcode.setBackground(getResources().getDrawable(R.drawable.back_edittext_search_error));
                    searchBarcode.setBackground(getResources().getDrawable(R.drawable.background_button_search_error));
                    txtMessageEditTextSearchBarcode.setText(getString(R.string.enterBarcodeMessage));
                    txtMessageEditTextSearchBarcode.setTextColor(getResources().getColor(R.color.colorerror));
                }


            }
        });

        return view;
    }


    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BarcodeReaderFragment);
        autoFocus = a.getBoolean(R.styleable.BarcodeReaderFragment_auto_focus, true);
        useFlash = a.getBoolean(R.styleable.BarcodeReaderFragment_use_flash, false);
        a.recycle();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BarcodeReaderListener) {
            mListener = (BarcodeReaderListener) context;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        permissionStatus = getActivity().getSharedPreferences("permissionStatus", getActivity().MODE_PRIVATE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                cameraPermitionLay.setVisibility(View.VISIBLE);
                cameraPermitionBtn.setText("دسترسی دوربین");
                cameraPermitionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CALLBACK_CONSTANT);
                    }
                });


            } else if (permissionStatus.getBoolean(Manifest.permission.CAMERA, false)) {
                cameraPermitionBtn.setText(getActivity().getString(R.string.camera_permitoin_setting));
                cameraPermitionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);

                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.grant_permission));
                builder.setMessage(getString(R.string.permission_camera));
                builder.setPositiveButton(R.string.grant, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        mListener.onCameraPermissionDenied();
                    }
                });
//                builder.show();
            } else {
                cameraPermitionLay.setVisibility(View.VISIBLE);
                cameraPermitionBtn.setText("دسترسی دوربین");
                cameraPermitionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                //just request the permission
                //  requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CALLBACK_CONSTANT);
            }


            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.CAMERA, true);
            editor.apply();
        } else {
            cameraPermitionLay.setVisibility(View.GONE);
            proceedAfterPermission();
        }
    }

    private void proceedAfterPermission() {
        createCameraSource(autoFocus, useFlash);
    }

    @SuppressLint("InlinedApi")
    private void createCameraSource(final boolean autoFocus, final boolean useFlash) {
        Context context = getActivity();

        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay, this);
        barcodeDetector.setProcessor(
                new MultiProcessor.Builder<>(barcodeFactory).build());

        if (!barcodeDetector.isOperational()) {

            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = getActivity().registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(getActivity(), R.string.low_storage_error, Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }


        CameraSource.Builder builder = new CameraSource.Builder(getActivity(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(1.0f);

        builder = builder.setFocusMode(
                autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);

        mCameraSource = builder
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();
    }


    public void setUseFlash(boolean use) {
        useFlash = use;
        mCameraSource.setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : Camera.Parameters.FLASH_MODE_OFF);
    }

    public void setAutoFocus(boolean continuous) {
        autoFocus = continuous;
        mCameraSource.setFocusMode(autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : Camera.Parameters.FOCUS_MODE_AUTO);
    }

    public boolean deviceSupportsFlash() {
        if (getActivity().getPackageManager() == null)
            return false;
        return getActivity().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onResume() {
        super.onResume();
        // MainActivity.nearmeVisibility.setHideNearMeView(true);
        h.removeCallbacks(runnablePause);
        h.postDelayed(runnable, 1000);


        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                cameraPermitionLay.setVisibility(View.GONE);
                proceedAfterPermission();
            } else {
                //mListener.onCameraPermissionDenied();
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
//        MainActivity.nearmeVisibility.setHideNearMeView(false);
        //mViewModel.getMainModel().setHideNearMeView(false);
        //
        if (mPreview != null) {
            h.removeCallbacks(runnable);

            h.postDelayed(runnablePause, 1000);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                    allgranted = true;
                } else {

                    allgranted = false;
                    break;
                }
            }
            if (allgranted) {
                cameraPermitionLay.setVisibility(View.GONE);
                proceedAfterPermission();
                cameraPermitionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CALLBACK_CONSTANT);

                    }
                });

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                cameraPermitionLay.setVisibility(View.VISIBLE);
            } else {
                cameraPermitionBtn.setText(getActivity().getString(R.string.camera_permitoin_setting));
                cameraPermitionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);

                    }
                });
                mListener.onCameraPermissionDenied();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();

            }
        }
    }

    private void startCameraSource() throws SecurityException {


        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getActivity());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    private boolean onTap(float rawX, float rawY) {
        int[] location = new int[2];
        mGraphicOverlay.getLocationOnScreen(location);
        float x = (rawX - location[0]) / mGraphicOverlay.getWidthScaleFactor();
        float y = (rawY - location[1]) / mGraphicOverlay.getHeightScaleFactor();

        // Find the barcode whose center is closest to the tapped point.
        Barcode best = null;
        float bestDistance = Float.MAX_VALUE;
        for (BarcodeGraphic graphic : mGraphicOverlay.getGraphics()) {
            Barcode barcode = graphic.getBarcode();
            if (barcode.getBoundingBox().contains((int) x, (int) y)) {
                // Exact hit, no need to keep looking.
                best = barcode;
                break;
            }
            float dx = x - barcode.getBoundingBox().centerX();
            float dy = y - barcode.getBoundingBox().centerY();
            float distance = (dx * dx) + (dy * dy);  // actually squared distance
            if (distance < bestDistance) {
                best = barcode;
                bestDistance = distance;
            }
        }

        if (best != null) {
            Intent data = new Intent();
            data.putExtra(BarcodeObject, best);

            // TODO - pass the scanned value
            getActivity().setResult(CommonStatusCodes.SUCCESS, data);
            getActivity().finish();
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean b = scaleGestureDetector.onTouchEvent(motionEvent);

        boolean c = gestureDetector.onTouchEvent(motionEvent);

        return b || c || view.onTouchEvent(motionEvent);
    }

    @Override
    public void onScanned(final Barcode barcode) {
        if (!isPaused) {
            if (getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    barcode(barcode);
                    // mListener.onScanned(barcode);
                }
            });
        }
    }

    @Override
    public void onScannedMultiple(final List<Barcode> barcodes) {
        if (!isPaused) {
            if (getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //   barcode(barcodes);
                    //mListener.onScannedMultiple(barcodes);
                }
            });

        }
    }

    @Override
    public void onBitmapScanned(final SparseArray<Barcode> sparseArray) {
        if (mListener != null) {
            if (getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mListener.onBitmapScanned(sparseArray);
                }
            });

        }
    }

    @Override
    public void onScanError(final String errorMessage) {
        if (mListener != null) {
            if (getActivity() == null) {
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mListener.onScanError(errorMessage);
                }
            });

        }
    }

    public void playBeep() {
        MediaPlayer m = new MediaPlayer();
        try {
            if (m.isPlaying()) {
                m.stop();
                m.release();
                m = new MediaPlayer();
            }

            AssetFileDescriptor descriptor = getActivity().getAssets().openFd(beepSoundFile != null ? beepSoundFile : "beep.mp3");
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            m.prepare();
            m.setVolume(1f, 1f);
            m.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface BarcodeReaderListener {
        void onScanned(Barcode barcode);

        void onScannedMultiple(List<Barcode> barcodes);

        void onBitmapScanned(SparseArray<Barcode> sparseArray);

        void onScanError(String errorMessage);

        void onCameraPermissionDenied();
    }

    private class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
        }
    }

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            mCameraSource.doZoom(detector.getScaleFactor());
        }
    }


}
