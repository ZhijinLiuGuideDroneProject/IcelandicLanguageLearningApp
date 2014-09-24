package com.gc02.wordsofafeather.service;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import com.gc02.wordsofafeather.activity.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service{
	private MediaPlayer music;
	
	public IBinder onBind(Intent intent)
	{
		return null;
	}
		
	@Override
	public void onCreate(){

		    music=MediaPlayer.create(this,R.raw.music);
		    music.setLooping(true);	
		    music.start();
		
	}	
	
	@Override
	public void onDestroy(){
		music.stop();
		music.reset();
		music.release();
		super.onDestroy();
	}
	

}
