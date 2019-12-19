package com.bestWallpapers.hamza.bestWallpaperss.activities.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bestWallpapers.hamza.bestWallpaperss.BuildConfig;
import com.bestWallpapers.hamza.bestWallpaperss.R;
import com.bestWallpapers.hamza.bestWallpaperss.activities.models.DuvarKagidi;
import com.bestWallpapers.hamza.bestWallpaperss.activities.saveImageHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class DuvarKagidiAdapter extends RecyclerView.Adapter<DuvarKagidiAdapter.DuvarKagidiViewHolder> {

    private Context context;
    private List<DuvarKagidi> duvarKagidiList;


    public DuvarKagidiAdapter(Context context, List<DuvarKagidi> duvarKagidiList) {
        this.context = context;
        this.duvarKagidiList = duvarKagidiList;

    }

    @NonNull
    @Override
    public DuvarKagidiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_duvarkagidi, viewGroup, false);
        return new DuvarKagidiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DuvarKagidiViewHolder categoryViewHolder, int i) {
        DuvarKagidi d = duvarKagidiList.get(i);
        categoryViewHolder.textView.setText(d.title);
        Glide.with(context)
                .load(d.url)
                .into(categoryViewHolder.imageView);
        if (d.isFavori) {
            categoryViewHolder.checkBoxFav.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return duvarKagidiList.size();
    }

    public class DuvarKagidiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        TextView textView;
        ImageView imageView;

        CheckBox checkBoxFav;
        ImageButton buttonShare, buttonDownload;


        public DuvarKagidiViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view_title);
            imageView = itemView.findViewById(R.id.image_view);

            checkBoxFav = itemView.findViewById(R.id.checkbox_favourite);
            buttonShare = itemView.findViewById(R.id.button_share);
            buttonDownload = itemView.findViewById(R.id.button_download);

            checkBoxFav.setOnCheckedChangeListener(this);
            buttonShare.setOnClickListener(this);
            buttonDownload.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.button_share:
                    duvarKagidiPaylas(duvarKagidiList.get(getAdapterPosition()));
                    break;
                case R.id.button_download:
                    duvarKagigiKaydet(duvarKagidiList.get(getAdapterPosition()));
                    break;
            }

        }

        private void duvarKagidiPaylas(DuvarKagidi d) {
            ((Activity) context).findViewById(R.id.progressbar).setVisibility(View.VISIBLE);

            Glide.with(context)
                    .asBitmap()
                    .load(d.url)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            ((Activity) context).findViewById(R.id.progressbar).setVisibility(View.GONE);
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(resource));

                            context.startActivity(Intent.createChooser(intent, "DuvarKagidim"));
                        }
                    });
        }

        private Uri getLocalBitmapUri(Bitmap bmp) {
            Uri bmpUri = null;
            try {
                File file = new File(String.valueOf(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)),
                        "DuvarKagidim" + System.currentTimeMillis() + ".png");
                FileOutputStream out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
                bmpUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmpUri;
        }

       /* private void duvarKagidiIndir(final DuvarKagidi duvarKagidi) {
            ((Activity) context).findViewById(R.id.progressbar).setVisibility(View.VISIBLE);

            Glide.with(context)
                    .asBitmap()
                    .load(duvarKagidi.url)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            ((Activity) context).findViewById(R.id.progressbar).setVisibility(View.GONE);
                            Intent intent = new Intent(Intent.ACTION_VIEW);

                            Uri uri = duvarKagigiKaydet(resource, duvarKagidi.id);

                            if (uri != null){
                                intent.setDataAndType(uri, "image/*");
                                context.startActivity(Intent.createChooser(intent, "DuvarKagidim"));
                            }


                        }
                    });
        }*/

        private void duvarKagigiKaydet(final DuvarKagidi duvarKagidi) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "izin vermeniz gerekiyor",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions((Activity)context, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                return ;
            }
            else {
                AlertDialog dialog = new SpotsDialog(context);
                dialog.show();
                dialog.setMessage("indiriliyor...");

                String filename = UUID.randomUUID().toString()+"jpg";
                Picasso.with(context)
                        .load(duvarKagidi.url)
                        .into(new saveImageHelper(context,
                                dialog,
                                context.getApplicationContext().getContentResolver(),
                                filename,
                                "Image description"));
            }


        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Toast.makeText(context, "Lütfen giriş yapın...", Toast.LENGTH_LONG).show();
                buttonView.setChecked(false);
                return;
            }


            int position = getAdapterPosition();
            DuvarKagidi d = duvarKagidiList.get(position);

            DatabaseReference dbFavs = FirebaseDatabase.getInstance().getReference("kullanicilar")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("favoriler")
                    .child(d.category);

            if (isChecked) {
                dbFavs.child(d.id).setValue(d);

            } else {
                dbFavs.child(d.id).setValue(null);

            }
        }
    }
}
