package com.codepath.musichunter.searchbyartist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.musichunter.MainActivity;
import com.codepath.musichunter.R;
import com.codepath.musichunter.model.data.network.model.searchbyartist.ArtistModel;
import com.codepath.musichunter.model.data.network.service.IRequestInterface;
import com.codepath.musichunter.model.data.network.service.ServiceConnection;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchByArtistFragment extends Fragment {

    //   @BindView(R.id.sv_artist) SearchView m_Sv_Artist;
    //  @BindView(R.id.rv_artistList) RecyclerView m_Rv_ArtistList;
//   private SearchView m_Sv_Artist;
    //  private RecyclerView m_Rv_ArtistList;
    private Unbinder mUbinder;
    private IRequestInterface iRequestInterface;
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
       // this.setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_search_by_artist, null);
        mUbinder = ButterKnife.bind(getActivity());

      /*  if(savedInstanceState!=null){
            CharSequence searchViewQuery = (CharSequence) savedInstanceState.get("savedSearchViewQuery");
            MainActivity.getM_Sv_Artist().setQuery(searchViewQuery, true);
        }*/
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iRequestInterface = ServiceConnection.getConnection();
        initializeArtistDetalisView(view);
        artistViewBySearch();
        //  initRecycleView();
     //   setRetainInstance(true);
  }


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
        //    MainActivity mainActivity  = new MainActivity();
      //  if(savedInstance!=null) {

              MainActivity.getM_Sv_Artist().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    iRequestInterface.getArtists(s)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<ArtistModel>() {
                                @Override
                                public void accept(ArtistModel artistModel) throws Exception {
                                   // try{
                                        if(artistModel!=null && artistModel.getArtists().size()>0){
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

                                            String diedYear = (String) artistModel.getArtists().get(0).getIntDiedYear();

                                            if (formedYear != null && diedYear != null) {
                                                m_ArtistTimeline.setText(String.valueOf(formedYear + " - " + diedYear));
                                            } else if (formedYear != null) {
                                                m_ArtistTimeline.setText(String.valueOf(formedYear));
                                            } else if (diedYear != null) {
                                                m_ArtistTimeline.setText(String.valueOf(diedYear));
                                            }

                                            //    int disbandedYear = Integer.parseInt(artistModel.getArtists().get(0).getIntFormedYear());

                                            Picasso.with(getContext()).load(artistModel.getArtists().get(0).getStrArtistLogo()).into(m_ArtistImageLogo);


                                            String artistImageClearArt = artistModel.getArtists().get(0).getStrArtistClearart();

                                            if (artistImageClearArt == null) {
                                                artistImageClearArt = artistModel.getArtists().get(0).getStrArtistFanart();

                                            }
                                            Picasso.with(getContext()).load(artistImageClearArt).into(m_ArtistImageClearArt);
                                        }
                                /*    }catch(Exception ex){
                                        Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                                    }*/




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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    //    Log.i("onSaveInstanceStateSArt", "onSaveInstanceState_SearchArti");
        CharSequence searchView = MainActivity.getM_Sv_Artist().getQuery();
        outState.putCharSequence("savedSearchViewQuery", searchView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
      //      Log.i("onRetainInstance", "onRetainInstance");
            CharSequence searchViewQuery = (CharSequence) savedInstanceState.get("savedSearchViewQuery");
            MainActivity.getM_Sv_Artist().setQuery(searchViewQuery, true);
        }
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
