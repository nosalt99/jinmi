package com.cyl.musiclake.ui.music.playlist.love;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cyl.musiclake.R;
import com.cyl.musiclake.ui.base.BaseFragment;
import com.cyl.musiclake.common.Constants;
import com.cyl.musiclake.bean.Music;
import com.cyl.musiclake.player.PlayManager;
import com.cyl.musiclake.ui.music.local.adapter.SongAdapter;
import com.cyl.musiclake.ui.music.dialog.AddPlaylistDialog;
import com.cyl.musiclake.ui.music.dialog.ShowDetailDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class LoveFragment extends BaseFragment<LovePresenter> implements LoveContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private SongAdapter mAdapter;
    private List<Music> musicInfos = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    public static LoveFragment newInstance() {
        Bundle args = new Bundle();
        LoveFragment fragment = new LoveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews() {
        mToolbar.setTitle(getString(R.string.item_favorite));
        setHasOptionsMenu(true);
        if (getActivity() != null) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            appCompatActivity.setSupportActionBar(mToolbar);
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mAdapter = new SongAdapter(musicInfos);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRecyclerView);

    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void loadData() {
        mPresenter.loadSongs();
    }


    @Override
    public void showSongs(List<Music> songs) {
        musicInfos = songs;
        mAdapter.setNewData(songs);
    }

    @Override
    protected void listener() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (view.getId() != R.id.iv_more) {
                PlayManager.play(position, musicInfos, Constants.PLAYLIST_LOVE_ID);
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), view);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.popup_song_play:
                        PlayManager.play(position, musicInfos, Constants.PLAYLIST_LOVE_ID);
                        break;
                    case R.id.popup_song_detail:
                        ShowDetailDialog.newInstance((Music) adapter.getItem(position))
                                .show(getChildFragmentManager(), getTag());
                        break;
                    case R.id.popup_song_addto_queue:
                        AddPlaylistDialog.newInstance(musicInfos.get(position))
                                .show(getChildFragmentManager(), "ADD_PLAYLIST");
                        break;

                }
                return false;
            });
            popupMenu.inflate(R.menu.popup_album);
            popupMenu.show();
        });
    }

    @Override
    public void showEmptyView() {
        mAdapter.setEmptyView(R.layout.view_song_empty);
    }

}
