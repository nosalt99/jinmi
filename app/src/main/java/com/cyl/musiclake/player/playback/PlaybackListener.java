package com.cyl.musiclake.player.playback;

import android.media.MediaPlayer;



public interface PlaybackListener {

    void onCompletionNext();


    void onCompletionEnd();

    void onBufferingUpdate(MediaPlayer mp, int percent);

    void onPrepared();

    void onError();
}
