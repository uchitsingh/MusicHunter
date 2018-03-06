package com.codepath.musichunter;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.musichunter.model.data.network.model.ArtistModel;
import com.codepath.musichunter.model.data.network.service.IRequestInterface;
import com.codepath.musichunter.model.data.network.service.ServiceConnection;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchByArtistFragment extends Fragment {

    //   @BindView(R.id.sv_artist) SearchView m_Sv_Artist;
    //  @BindView(R.id.rv_artistList) RecyclerView m_Rv_ArtistList;
    private SearchView m_Sv_Artist;
    //  private RecyclerView m_Rv_ArtistList;
    private Unbinder mUbinder;
    private IRequestInterface iRequestInterface;
    private ArtistAdapter artistAdapter;
    //   private RelativeLayout rl_ArtistDetails;
    private CardView m_Cv_ArtistDetails;
    private TextView m_ArtistName, m_ArtistBio, m_ArtistGenre, m_ArtistStyle, m_ArtistMood, m_ArtistLabel, m_ArtistCountry, m_ArtistTimeline;
    private ImageView m_ArtistImageLogo, m_ArtistImageClearArt;

    public SearchByArtistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_by_artist, null);
        mUbinder = ButterKnife.bind(getActivity());
        iRequestInterface = ServiceConnection.getConnection();


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  initRecycleView();
        m_Sv_Artist = (SearchView) view.findViewById(R.id.sv_artist);

        initializeArtistDetalisView(view);
        artistViewBySearch();


    }

 /*   public void initRecycleView() {
        m_Rv_ArtistList = (RecyclerView) getActivity().findViewById(R.id.rv_artistList);
        m_Rv_ArtistList.setLayoutManager(new LinearLayoutManager(getActivity()));

    }*/


    public void initializeArtistDetalisView(View view) {
        //  rl_ArtistDetails = (RelativeLayout) view.findViewById(R.id.rl_ArtistDetails);
        m_Cv_ArtistDetails = (CardView) view.findViewById(R.id.cv_ArtistDetails);
   //     m_ArtistName = (TextView) view.findViewById(R.id.tv_ArtistName);
        m_ArtistBio = (TextView) view.findViewById(R.id.tv_ArtistBiography);
//        m_ArtistBio.setMovementMethod(ScrollingMovementMethod.getInstance());
        m_ArtistGenre = (TextView) view.findViewById(R.id.tv_ArtistGenre);
        m_ArtistStyle = (TextView) view.findViewById(R.id.tv_ArtistStyle);
        m_ArtistMood = (TextView) view.findViewById(R.id.tv_ArtistMood);
        m_ArtistLabel = (TextView) view.findViewById(R.id.tv_ArtistLabel);
        m_ArtistCountry = (TextView) view.findViewById(R.id.tv_ArtistCountry);
        m_ArtistTimeline = (TextView) view.findViewById(R.id.tv_ArtistTimeline);
        m_ArtistImageLogo= (ImageView) view.findViewById(R.id.iv_ArtistImageLogo);
        m_ArtistImageClearArt= (ImageView) view.findViewById(R.id.iv_ArtistImageCleanArt);

    }

    public void artistViewBySearch() {
        m_Sv_Artist.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                iRequestInterface.getArtists(s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ArtistModel>() {
                            @Override
                            public void accept(ArtistModel artistModel) throws Exception {
                                //         Toast.makeText(getContext(), artistModel.getArtists().get(0).getStrBiographyEN(), Toast.LENGTH_SHORT).show();
                                //        Log.i("bio", artistModel.getArtists().get(0).getStrBiographyEN());
                                //m_Rv_ArtistList.setAdapter(new ArtistAdapter(artistModel));
                                // rl_ArtistDetails.setVisibility(View.VISIBLE);
                                m_Cv_ArtistDetails.setVisibility(View.VISIBLE);
                        //        m_ArtistName.setText(artistModel.getArtists().get(0).getStrArtist());
                                m_ArtistBio.setText(artistModel.getArtists().get(0).getStrBiographyEN());
                                m_ArtistGenre.setText(artistModel.getArtists().get(0).getStrGenre());
                                m_ArtistStyle.setText(artistModel.getArtists().get(0).getStrStyle());
                                m_ArtistMood.setText(artistModel.getArtists().get(0).getStrMood());

                                String artist_Label = artistModel.getArtists().get(0).getStrLabel();
                                if (artist_Label != null) {
                                    m_ArtistLabel.setText(artistModel.getArtists().get(0).getStrLabel());
                                } else {
                                    m_ArtistLabel.setText("N/A");
                                }

                                m_ArtistCountry.setText(artistModel.getArtists().get(0).getStrCountry());

                                String formedYear = artistModel.getArtists().get(0).getIntFormedYear();

                                String diedYear = (String)artistModel.getArtists().get(0).getIntDiedYear();

                                if(formedYear!=null && diedYear !=null ){
                                    m_ArtistTimeline.setText(String.valueOf(formedYear + " - " + diedYear));
                                }else if(formedYear!=null){
                                    m_ArtistTimeline.setText(String.valueOf(formedYear));
                                }else if(diedYear!=null){
                                    m_ArtistTimeline.setText(String.valueOf(diedYear));
                                }

                                //    int disbandedYear = Integer.parseInt(artistModel.getArtists().get(0).getIntFormedYear());

                                Picasso.with(getContext()).load(artistModel.getArtists().get(0).getStrArtistLogo()).into(m_ArtistImageLogo);



                                String artistImageClearArt = artistModel.getArtists().get(0).getStrArtistClearart();

                                if(artistImageClearArt==null){
                                    artistImageClearArt = artistModel.getArtists().get(0).getStrArtistFanart();

                                }
                                    Picasso.with(getContext()).load(artistImageClearArt).into(m_ArtistImageClearArt);




                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.i("error", throwable.getMessage());
                            }
                        });
                return true;
            }


            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
     //   m_ArtistLabel.setText("");
    }


   /* public void rxArtistViewBySearch(){
        RxSearchView.queryTextChanges(m_Sv_Artist)
                .debounce(350, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        return !charSequence.toString().isEmpty();
                    }
                })
                //   .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .switchMap(new Function<CharSequence, ObservableSource<ArtistModel>>() {
                    @Override
                    public ObservableSource<ArtistModel> apply(CharSequence query) throws Exception {
                        return iRequestInterface.getArtists(query.toString());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArtistModel>() {
                               @Override
                               public void accept(ArtistModel artistModel) throws Exception {
                                   if(artistModel==null){
                                       Toast.makeText(getContext(),"Artist not found", Toast.LENGTH_SHORT).show();
                                   }else if(artistModel!=null) {
                                       m_Rv_ArtistList.setAdapter(artistAdapter = new ArtistAdapter(artistModel));
                                   }

                                   //  Toast.makeText(getContext(), artistModel.getArtists().get(0).getStrArtist(), Toast.LENGTH_SHORT).show();

                                   //   artistAdapter.addArtist(artistModel);


                                   // m_Rv_ArtistList.setAdapter(new ArtistAdapter(artistModel, R.layout.artist_row));
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
    }*/

/*   public void replaceFragment(ArtistModel artistModel){
       ArtistDetailsFragment artistDetailsFragment =  new ArtistDetailsFragment();
       Bundle bundle = new Bundle();
       bundle.putSerializable("artistModel",artistModel);
       artistDetailsFragment.setArguments(bundle);
       getFragmentManager().beginTransaction()
               .replace(R.id.fragment_container, artistDetailsFragment)
               .addToBackStack(null)
               .commit();

   }*/

}
