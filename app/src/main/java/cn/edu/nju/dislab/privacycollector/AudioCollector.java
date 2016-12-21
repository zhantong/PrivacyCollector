package cn.edu.nju.dislab.privacycollector;

import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhantong on 2016/12/21.
 */

public class AudioCollector {
    private static final String TAG = "AudioCollector";
    private static final int POLL_INTERVAL = 100;
    private static final int MAX_TICKS = 15;
    private Handler mHandler;
    private MediaRecorder mRecorder;
    private int mTickCount;
    private List<Double> results;
    private final Object LOCK = new Object();
    private Runnable mPollTask = new Runnable() {
        @Override
        public void run() {
            double amp = getAmplitude();
            results.add(amp);
            mTickCount++;
            if (mTickCount > MAX_TICKS) {
                stop();
            } else {
                mHandler.postDelayed(mPollTask, POLL_INTERVAL);
            }
        }
    };

    public AudioCollector() {
        mHandler = new Handler();
    }

    private void stop() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
        synchronized (LOCK) {
            LOCK.notify();
        }
    }

    public void collect() {
        try {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");
            mRecorder.prepare();
            mRecorder.start();
        } catch (Exception e) {
            Log.i(TAG, "unable to record audio");
            e.printStackTrace();
            return;
        }
        mTickCount = 0;
        results = new ArrayList<>();
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return (mRecorder.getMaxAmplitude() / 2700.0);
        else
            return 0;
    }

    public List<Double> getResult() {
        return results;
    }
}
