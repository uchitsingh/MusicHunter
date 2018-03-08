package com.codepath.musichunter.displaytracksbyAlbum;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.codepath.musichunter.MainActivity;
import com.codepath.musichunter.R;
import com.codepath.musichunter.model.data.network.model.displaytracksbyalbum.TracksModel;
import com.codepath.musichunter.model.data.network.model.searchbyartist.ArtistModel;
import com.codepath.musichunter.model.data.network.model.searhalbumsbyartist.AlbumsModel;
import com.codepath.musichunter.model.data.network.service.IRequestInterface;
import com.codepath.musichunter.model.data.network.service.ServiceConnection;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayTracksByAlbumFragment extends Fragment {

    private IRequestInterface iRequestInterface;
    private RecyclerView m_rv_tracksDetails;
    public DisplayTracksByAlbumFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_display_tracks_by_album, null);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iRequestInterface = ServiceConnection.getConnection();
        initRecycleView();
        trackViewBySearch();
    }

    private void initRecycleView() {
        m_rv_tracksDetails = (RecyclerView) getActivity().findViewById(R.id.rv_trackDetails);
        m_rv_tracksDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void trackViewBySearch() {
        String albumId = getActivity().getIntent().getExtras().getString("albumId");
        iRequestInterface.getTracksByAlbum(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TracksModel>() {
                    @Override
                    public void accept(TracksModel tracksModel) throws Exception {
                     //   String desc= tracksModel.getTrack().get(0).getStrDescriptionEN().toString();
                       // Toast.makeText(getContext(),desc,Toast.LENGTH_SHORT).show();
                        m_rv_tracksDetails.setAdapter(new TracksAdapter(tracksModel));

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
