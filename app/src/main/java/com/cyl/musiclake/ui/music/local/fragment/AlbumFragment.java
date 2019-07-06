package com.cyl.musiclake.ui.music.local.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.cyl.musiclake.R;
import com.cyl.musiclake.ui.base.BaseLazyFragment;
import com.cyl.musiclake.bean.Album;
import com.cyl.musiclake.ui.music.local.adapter.AlbumAdapter;
import com.cyl.musiclake.ui.music.local.contract.AlbumsContract;
import com.cyl.musiclake.ui.music.local.presenter.AlbumPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class AlbumFragment extends BaseLazyFragment<AlbumPresenter> implements AlbumsContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private AlbumAdapter mAdapter;
    private List<Album> albumList = new ArrayList<>();

    public static AlbumFragment newInstance() {

        Bundle args = new Bundle();
        AlbumFragment fragment = new AlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_recyclerview_notoolbar;
    }


    @Override
    public void initViews() {
        mAdapter = new AlbumAdapter(albumList);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRecyclerView);
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void listener() {
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onLazyLoad() {
        mPresenter.loadAlbums("all");
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showAlbums(List<Album> albumList) {
        mAdapter.setNewData(albumList);
    }

    @Override
    public void showEmptyView() {
        mAdapter.setEmptyView(R.layout.view_song_empty);
    }
}
