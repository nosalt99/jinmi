package com.cyl.musiclake.event

import com.cyl.musiclake.bean.Playlist
import com.cyl.musiclake.common.Constants


class PlaylistEvent(var type: String? = Constants.PLAYLIST_CUSTOM_ID, val playlist: Playlist? = null)
