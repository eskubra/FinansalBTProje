package com.bestWallpapers.hamza.bestWallpaperss.activities.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bestWallpapers.hamza.bestWallpaperss.R;
import com.bestWallpapers.hamza.bestWallpaperss.activities.adapters.DuvarKagidiAdapter;
import com.bestWallpapers.hamza.bestWallpaperss.activities.models.DuvarKagidi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriFragment extends Fragment {

    List<DuvarKagidi> favoriList;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    DuvarKagidiAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favori, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressbar);
        adapter = new DuvarKagidiAdapter(getActivity(),favoriList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        DatabaseReference dbfavori;

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_area, new SettingsFragment())
                    .commit();
            return;
        }

        dbfavori = FirebaseDatabase.getInstance().getReference("kullanicilar")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("favoriler");
        progressBar.setVisibility(View.VISIBLE);
        dbfavori.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.GONE);

                for (DataSnapshot category: dataSnapshot.getChildren()){
                    for (DataSnapshot duvarkagidi: category.getChildren()){
                        String id = duvarkagidi.getKey();
                        String title = duvarkagidi.child("title").getValue(String.class);
                        String desc = duvarkagidi.child("desc").getValue(String.class);
                        String url = duvarkagidi.child("url").getValue(String.class);

                        DuvarKagidi d = new DuvarKagidi(id, title, desc, url, category.getKey());
                        d.isFavori=true;
                        favoriList.add(d);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
