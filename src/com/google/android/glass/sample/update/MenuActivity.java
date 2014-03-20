/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.glass.sample.update;
import android.app.Activity;
import java.io.IOException;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.content.Intent;
import dk.dr.glass.update.R;
import android.media.MediaPlayer;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import android.view.View;
/**
 * Activity showing the options menu.
 */
public class MenuActivity extends Activity {
   // MediaPlayer mPlayer = new MediaPlayer();

    String updateClip = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        playUpdateClip();
    }

    public void playUpdateClip() {
        //Request update clip
        String updateClip;
        updateClip = new updateClip().requestLatest();
        try {


            //TEST this
            Intent i = new Intent();
            i.setAction("com.google.glass.action.VIDEOPLAYER");
            i.putExtra("video_url", updateClip);
            startActivity(i);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        } catch (SecurityException e) {
            e.printStackTrace();

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        openOptionsMenu();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.stop:
                return false;
            case R.id.play:
                playUpdateClip();
            default:
                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update, menu);
        return true;
    }



    @Override
    public void onOptionsMenuClosed(Menu menu) {
        // Nothing else to do, closing the activity.
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            openOptionsMenu();
            return true;
        }
        return false;
    }









}
