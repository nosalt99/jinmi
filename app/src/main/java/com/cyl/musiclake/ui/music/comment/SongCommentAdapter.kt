package com.cyl.musiclake.ui.music.comment

import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cyl.musicapi.bean.SongComment
import com.cyl.musiclake.R
import com.cyl.musiclake.utils.CoverLoader
import com.cyl.musiclake.utils.FormatUtil


class SongCommentAdapter(list: List<SongComment>) : BaseQuickAdapter<SongComment, BaseViewHolder>(R.layout.item_comment, list) {

    override fun convert(helper: BaseViewHolder, item: SongComment) {
        helper.setText(R.id.tv_comment_user, item.nick)
        helper.setText(R.id.tv_comment_time, FormatUtil.formatDate(item.time))
        helper.setText(R.id.tv_comment_content, item.content)
        CoverLoader.loadImageView(mContext, item.avatarUrl, helper.getView(R.id.civ_cover))
    }

}