package com.acer.cursos.platzigram.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acer.cursos.platzigram.R;
import com.acer.cursos.platzigram.modelo.Picture;
import com.acer.cursos.platzigram.post.view.DetallesImagenActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterImagen extends RecyclerView.Adapter<AdapterImagen.ViewHolderAdapter> {

      private ArrayList<Picture> pictures;
      private int recursos;
      private Activity activity;

    public AdapterImagen(ArrayList<Picture> pictures, int recursos, Activity activity) {
        this.pictures = pictures;
        this.recursos = recursos;
        this.activity = activity;

    }

    @NonNull
    @Override
    public ViewHolderAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(recursos,viewGroup,false);

        return new ViewHolderAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapter viewHolderAdapter, int i) {
        Picture picture= pictures.get(i);


        viewHolderAdapter.fechacard.setText(picture.getFechacard());
        viewHolderAdapter.usernamecard.setText(picture.getUsernamecard());
        viewHolderAdapter.likecard.setText(picture.getLikecard());
        Picasso.get()
               .load(picture.getImagen())
               .into(viewHolderAdapter.imagencard);

        viewHolderAdapter.imagencard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(activity,DetallesImagenActivity.class);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    Explode explode= new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent,ActivityOptionsCompat.makeSceneTransitionAnimation(activity,v,
                            activity.getString(R.string.transitionNameIumage)).toBundle());

                }else{
                    activity.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public class ViewHolderAdapter extends RecyclerView.ViewHolder {
        private ImageView  imagencard;
        private TextView   usernamecard;
        private TextView   fechacard;
        private TextView   likecard;

       public ViewHolderAdapter(@NonNull View itemView) {
            super(itemView);

            imagencard    =itemView.findViewById(R.id.IdImage);
            usernamecard  =itemView.findViewById(R.id.IdNombreCard);
            fechacard     =itemView.findViewById(R.id.IDnumeroFecha);
            likecard      =itemView.findViewById(R.id.IDlikeNumero);
        }
    }
}
