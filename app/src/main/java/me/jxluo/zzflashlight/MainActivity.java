package me.jxluo.zzflashlight;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.*;


public class MainActivity extends Activity implements SurfaceHolder.Callback {

    ToggleButton mToggleButton;
    Camera mCamera;
    SurfaceView mySurfaceView;
    SurfaceHolder mHolder;

    boolean myBulbOn = false;
    ImageView myImageView;

    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);


        //mToggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        mySurfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        mHolder = mySurfaceView.getHolder();
        mHolder.addCallback(this);

        myImageView = (ImageView) findViewById(R.id.imageView);
        refreshImage();
        myImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //displayInterstitial();
                System.out.println("Image is click!");
                myBulbOn = !myBulbOn;
                refreshImage();
                refreshBulb();

            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-5592155429252855/2152274929");

        // Create ad request.
        AdRequest adRequest1 = new AdRequest.Builder().build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequest1);


    }

    public void displayInterstitial() {
        System.out.println("jxluodebug displayInteresrserserse");
        if (interstitial.isLoaded()) {
            System.out.println("jxluodebug add loaded");
            interstitial.show();
        }
    }

    private void refreshImage() {
        if (myBulbOn) {
            myImageView.setAlpha(255);
        } else {
            myImageView.setAlpha(100);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCamera = Camera.open();
        displayInterstitial();
    }

    @Override
    protected void onStop() {

        mCamera.release();
        mCamera = null;
        displayInterstitial();
        super.onStop();
    }

    public void refreshBulb() {
        //System.out.println("On status click " + String.valueOf(mToggleButton.isChecked()));

        Boolean isChecked = myBulbOn;

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
