package com.cyl.musiclake.event

import com.cyl.musiclake.bean.Playlist
import com.cyl.musiclake.common.Constants


class MyPlaylistEvent(var operate: Int, val playlist: Playlist? = null)
