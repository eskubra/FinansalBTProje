package com.bestWallpapers.hamza.bestWallpaperss.activities.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class DuvarKagidiActivity extends AppCompatActivity {

    List<DuvarKagidi> duvarKagidiList;
    List<DuvarKagidi> favoriList;
    RecyclerView recyclerView;
    DuvarKagidiAdapter adapter;

    DatabaseReference dbDuvarKagidi, dbFavori;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapers);

        Intent intent = getIntent();

        final String category = intent.getStringExtra("category");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(category);
        setSupportActionBar(toolbar);

        favoriList = new ArrayList<>();
        duvarKagidiList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DuvarKagidiAdapter(this, duvarKagidiList);

        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progressbar);


        dbDuvarKagidi = FirebaseDatabase.getInstance().getReference("images")
                .child(category);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            dbFavori = FirebaseDatabase.getInstance().getReference("kullanicilar")
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .child("favoriler")
            .child(category);
            FavoriDuvarKagitlari(category);
        } else {
            DuvarKagitlari(category);
        }
    }

    private void FavoriDuvarKagitlari(final String category) {
        progressBar.setVisibility(View.VISIBLE);
        dbFavori.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    for (DataSnapshot duvarkagidiSnapshot : dataSnapshot.getChildren()) {

                        String id = duvarkagidiSnapshot.getKey();
                        String title = duvarkagidiSnapshot.child("title").getValue(String.class);
                        String desc = duvarkagidiSnapshot.child("desc").getValue(String.class);
                        String url = duvarkagidiSnapshot.child("url").getValue(String.class);

                        DuvarKagidi d = new DuvarKagidi(id, title, desc, url, category);
                        favoriList.add(d);
                    }
                }
                DuvarKagitlari(category);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void DuvarKagitlari(final String category) {
        progressBar.setVisibility(View.VISIBLE);
        dbDuvarKagidi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    for (DataSnapshot duvarkagidiSnapshot : dataSnapshot.getChildren()) {

                        String id = duvarkagidiSnapshot.getKey();
                        String title = duvarkagidiSnapshot.child("title").getValue(String.class);
                        String desc = duvarkagidiSnapshot.child("desc").getValue(String.class);
                        String url = duvarkagidiSnapshot.child("url").getValue(String.class);

                        DuvarKagidi d = new DuvarKagidi(id, title, desc, url, category);
                        if (isFavori(d)){
                            d.isFavori = true;
                        }
                        duvarKagidiList.add(d);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private boolean isFavori(DuvarKagidi d){
        for (DuvarKagidi k: favoriList){
            if (k.id.equals(d.id)){
                return true;
            }
        }
        return false;
    }
}
