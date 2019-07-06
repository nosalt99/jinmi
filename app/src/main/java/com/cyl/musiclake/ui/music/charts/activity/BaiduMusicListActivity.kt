package com.cyl.musiclake.ui.music.charts.activity

import android.os.Bundle
import com.cyl.musiclake.bean.Playlist
import com.cyl.musiclake.common.Extras
import kotlinx.android.synthetic.main.activity_chart_playlist.*


class BaiduMusicListActivity : BasePlaylistActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mPlaylist == null) {
            mPlaylist = intent.getParcelableExtra(Extras.PLAYLIST)
        }
        mPlaylist?.pid?.let { mPresenter?.loadOnlineMusicList(it, limit, mOffset) }
    }

    override fun setEnableMore(): Boolean {
        return true
    }

    override fun retryLoading() {
        super.retryLoading()
        mPlaylist?.pid?.let { mPresenter?.loadOnlineMusicList(it, limit, mOffset) }
    }

    override fun getToolBarTitle(): String? {
        if (mPlaylist == null) {
            mPlaylist = intent.getParcelableExtra(Extras.PLAYLIST)
        }
        return mPlaylist?.name
    }

    override fun getmPlaylist(): Playlist? {
        if (mPlaylist == null) {
            mPlaylist = intent.getParcelableExtra(Extras.PLAYLIST)
        }
        return mPlaylist
    }

    override fun listener() {
        super.listener()
        mAdapter?.setOnLoadMoreListener({
            recyclerView.postDelayed({
                if (mCurrentCounter < TOTAL_COUNTER) {
                    //数据全部加载完毕
                    mAdapter?.loadMoreEnd()
                } else {
                    //成功获取更多数据
                    mPlaylist?.pid?.let { mPresenter?.loadOnlineMusicList(it, limit, mOffset) }
                }
            }, 1000)
        }, recyclerView)
    }


    companion object {
        private val TAG = "NeteasePlaylistActivity"
    }
}
