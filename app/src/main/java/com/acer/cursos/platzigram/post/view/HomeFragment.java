package com.acer.cursos.platzigram.post.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acer.cursos.platzigram.R;
import com.acer.cursos.platzigram.adapter.AdapterImagen;
import com.acer.cursos.platzigram.modelo.Picture;
import com.crashlytics.android.Crashlytics;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    private static final int REQUEST_CAMARE = 1;
    private static final String TAG = "HomeFragment";
    private FloatingActionButton FabCamara;
    RecyclerView recyclerCardView;
    private String photopathTem="";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Crashlytics.logException(new Exception("Reporte desde HomeFragment"));
        // Inflate the layout for this fragment
     View view= inflater.inflate(R.layout.fragment_home, container, false);

        showtoolbar(getResources().getString(R.string.inicio),false,view);

        recyclerCardView= view.findViewById(R.id.IdRecyc);
        FabCamara=(FloatingActionButton)view.findViewById(R.id.IDFabCamare);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerCardView.setLayoutManager(linearLayoutManager);

        AdapterImagen adapter= new AdapterImagen(ImagenView(),R.layout.cardview_pictures,getActivity());

        recyclerCardView.setAdapter(adapter);

        FabCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePicture();
            }
        });
        return view;
    }

    private void TakePicture() {

        Intent IntentTakeCamara= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(IntentTakeCamara.resolveActivity(getActivity().getPackageManager())!=null){

            File photoFile=null;

            try {
                  photoFile= CrearImageFile();

            }catch (Exception e){
                Crashlytics.logException(e);
                e.printStackTrace();
            }

        if(photoFile != null) {

                    Uri photoUri= FileProvider.getUriForFile(getActivity(),"com.acer.cursos.platzigram",photoFile);
                    IntentTakeCamara.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                    startActivityForResult(IntentTakeCamara, REQUEST_CAMARE);

           }
        }
    }

    private File CrearImageFile() throws IOException {

      String timeStamp= new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());
      String imageFileName="JPEG_" + timeStamp + "_";
      File stogeDir= getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

      File PHOTO= File.createTempFile(imageFileName,".jpg",stogeDir);

      photopathTem= "file:" + PHOTO.getAbsolutePath();

      return PHOTO;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      if(requestCode== REQUEST_CAMARE && resultCode== getActivity().RESULT_OK){
          Log.d("HomeFragment","CAMARA.. OK!!!");

          Intent intent= new Intent(getActivity(),NewPostActivity.class);
          intent.putExtra("PHOTO_PATH_TEMP",photopathTem);
          startActivity(intent);
      }
    }


    private ArrayList<Picture> ImagenView() {
        ArrayList<Picture> ImagenView= new ArrayList<>();

        ImagenView.add(new Picture("https://imagenmix.net/wp-content/uploads/2016/10/frases-cortas.jpg","4 Dias","Jose guerra","3 Me Gusta"));
        ImagenView.add(new Picture("https://imagenmix.net/wp-content/uploads/2016/10/frases-cortas.jpg","10 Dias","Rafael pinto","25 Me Gusta"));
        ImagenView.add(new Picture("https://imagenmix.net/wp-content/uploads/2016/10/frases-cortas.jpg","5 Dias","Yaneth Gonzalez","30 Me Gusta"));
        return ImagenView;
    }


    public void showtoolbar(String titulo, boolean upbutton, View view){
        Toolbar toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(titulo);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upbutton);

    }

}