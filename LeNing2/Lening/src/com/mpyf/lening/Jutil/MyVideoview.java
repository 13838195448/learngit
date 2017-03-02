package com.mpyf.lening.Jutil;

import io.vov.vitamio.widget.VideoView;
import android.content.Context;
import android.util.AttributeSet;

public class MyVideoview extends VideoView{

	private PlayPauseListener mListener;
	
	public MyVideoview(Context context) {
		super(context);
	}

	public MyVideoview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPlayPauseListener(PlayPauseListener listener) {
        mListener = listener;
    }

    @Override
    public void pause() {
        super.pause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    public void start() {
        super.start();
        if (mListener != null) {
            mListener.onPlay();
        }
    }

    public interface PlayPauseListener {
        void onPlay();
        void onPause();
    }
}
