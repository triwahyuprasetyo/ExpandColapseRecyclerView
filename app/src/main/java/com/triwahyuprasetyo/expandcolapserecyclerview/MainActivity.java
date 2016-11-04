package com.triwahyuprasetyo.expandcolapserecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.triwahyuprasetyo.expandcolapserecyclerview.AnimalListAdapter.Animal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recyclerviewRC);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final List<Animal> data = new ArrayList<>();

        data.add(new Animal(AnimalListAdapter.PERMANENT, "Tiger", "Mammals"));
        data.add(new Animal(AnimalListAdapter.PERMANENT, "Panda", "Mammals"));
        data.add(new Animal(AnimalListAdapter.PERMANENT, "Kakatua", "Birds"));
        data.add(new Animal(AnimalListAdapter.PERMANENT, "Peacock", "Birds"));
        data.add(new Animal(AnimalListAdapter.CHILD, "Shark", "Fishes"));
        data.add(new Animal(AnimalListAdapter.CHILD, "Piranha", "Fishes"));
        data.add(new Animal(AnimalListAdapter.CHILD, "Crocodile", "Reptiles"));
        data.add(new Animal(AnimalListAdapter.HEADER, "-", "-"));

        AnimalListAdapter sla = new AnimalListAdapter(data);
        recyclerview.setAdapter(sla);
        sla.setCollapse();
    }
}
