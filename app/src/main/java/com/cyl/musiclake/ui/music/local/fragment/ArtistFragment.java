package com.cyl.musiclake.ui.music.local.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.cyl.musiclake.R;
import com.cyl.musiclake.ui.base.BaseLazyFragment;
import com.cyl.musiclake.bean.Artist;
import com.cyl.musiclake.ui.music.local.adapter.ArtistAdapter;
import com.cyl.musiclake.ui.music.local.contract.ArtistContract;
import com.cyl.musiclake.ui.music.local.presenter.ArtistPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ArtistFragment extends BaseLazyFragment<ArtistPresenter> implements ArtistContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ArtistAdapter mAdapter;
    private List<Artist> artists = new ArrayList<>();

    public static ArtistFragment newInstance() {
        Bundle args = new Bundle();
        ArtistFragment fragment = new ArtistFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_recyclerview_notoolbar;
    }


    @Override
    public void initViews() {
        mAdapter = new ArtistAdapter(artists);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onLazyLoad() {
        if (mPresenter != null) {
            mPresenter.loadArtists("all");
        }
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
    public void showArtists(List<Artist> artists) {
        mAdapter.setNewData(artists);
        hideLoading();
    }

    @Override
    public void showEmptyView() {
        mAdapter.setEmptyView(R.layout.view_song_empty);
    }
}
