package com.acer.cursos.platzigram.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acer.cursos.platzigram.R;
import com.acer.cursos.platzigram.adapter.AdapterImagen;
import com.acer.cursos.platzigram.modelo.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
RecyclerView recyclerView;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_perfil, container, false);
        showtoolbar("",false,view);

        recyclerView= view.findViewById(R.id.IdRecyclerPerfil);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        AdapterImagen adapter= new AdapterImagen(ImagenView(),R.layout.cardview_pictures,getActivity());

        recyclerView.setAdapter(adapter);

        return view;
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
