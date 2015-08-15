package me.jxluo.zzflashlight;

import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity implements SurfaceHolder.Callback {

    ToggleButton mToggleButton;
    Camera mCamera;
    SurfaceView mySurfaceView;
    SurfaceHolder mHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        mySurfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        mHolder = mySurfaceView.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCamera = Camera.open();
    }

    @Override
    protected void onStop() {
        mCamera.release();
        mCamera = null;
        super.onStop();
    }

    public void onClick(View view) {
        System.out.println("On status click " + String.valueOf(mToggleButton.isChecked()));

        Boolean isChecked = mToggleButton.isChecked();

        if (isChecked) {
            try {
                mCamera.setPreviewDisplay(mHolder);
            } catch (Exception ex) {
            }
            Camera.Parameters p = mCamera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(p);
            mCamera.startPreview();
        } else {
            mCamera.stopPreview();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {}

    public void surfaceCreated(SurfaceHolder holder) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
