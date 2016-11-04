package com.triwahyuprasetyo.expandcolapserecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AnimalListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public static final int PERMANENT = 2;

    private List<Animal> data;

    public AnimalListAdapter(List<Animal> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        switch (type) {
            case PERMANENT:
                LayoutInflater inflaterP = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterP.inflate(R.layout.item_animal, parent, false);
                AnimalViewHolder statisticP = new AnimalViewHolder(view);
                return statisticP;
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.expand_collapse_animal, parent, false);
                ECStatisticViewHolder header = new ECStatisticViewHolder(view);
                return header;
            case CHILD:
                LayoutInflater inflaterC = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterC.inflate(R.layout.item_animal, parent, false);
                AnimalViewHolder statisticC = new AnimalViewHolder(view);
                return statisticC;
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Animal animal = data.get(position);
        switch (animal.type) {
            case HEADER:
                final ECStatisticViewHolder itemController = (ECStatisticViewHolder) holder;
                if (animal.invisibleChildren == null) {
                    itemController.iconUpDown.setImageResource(R.drawable.icon_expand);
                } else {
                    itemController.iconUpDown.setImageResource(R.drawable.icon_collapse);
                }
                itemController.iconUpDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (animal.invisibleChildren == null) {
                            animal.invisibleChildren = new ArrayList<Animal>();
                            int pos = data.indexOf(animal);
                            for (int i = (pos - 1); i >= 0; i--) {
                                if (data.get(i).type == AnimalListAdapter.PERMANENT) break;
                                animal.invisibleChildren.add(data.remove(i));
                            }
                            notifyItemRangeRemoved(pos- animal.invisibleChildren.size(), animal.invisibleChildren.size());
                            itemController.iconUpDown.setImageResource(R.drawable.icon_collapse);
                        } else {
                            int pos = data.indexOf(animal);
                            for (Animal st : animal.invisibleChildren) {
                                data.add(pos, st);
                            }
                            notifyItemRangeInserted(pos, animal.invisibleChildren.size());
                            itemController.iconUpDown.setImageResource(R.drawable.icon_expand);
                            animal.invisibleChildren = null;
                        }
                    }
                });
                break;
            case PERMANENT:
                final AnimalViewHolder permanentViewHolder = (AnimalViewHolder) holder;
                permanentViewHolder.animalName.setText(animal.animalName);
                permanentViewHolder.classification.setText(animal.classification);
                break;
            case CHILD:
                final AnimalViewHolder childViewHolder = (AnimalViewHolder) holder;
                childViewHolder.animalName.setText(animal.animalName);
                childViewHolder.classification.setText(animal.classification);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setCollapse(){
        Animal animal = data.get(data.size()-1);
        animal.invisibleChildren = new ArrayList<Animal>();
        int pos = data.indexOf(animal);
        for (int i = (pos - 1); i >= 0; i--) {
            if (data.get(i).type == AnimalListAdapter.PERMANENT) break;
            animal.invisibleChildren.add(data.remove(i));
        }
        notifyItemRangeRemoved(pos- animal.invisibleChildren.size(), animal.invisibleChildren.size());
    }

    private static class ECStatisticViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout relExpandCollapse;
        public ImageView iconUpDown;

        public ECStatisticViewHolder(View itemView) {
            super(itemView);
            relExpandCollapse = (RelativeLayout) itemView.findViewById(R.id.relExpandCollapse);
            iconUpDown = (ImageView) itemView.findViewById(R.id.iconUpDown);
        }
    }

    private static class AnimalViewHolder extends RecyclerView.ViewHolder {
        public TextView animalName;
        public TextView classification;

        public AnimalViewHolder(View itemView) {
            super(itemView);
            animalName = (TextView) itemView.findViewById(R.id.animalName);
            classification = (TextView) itemView.findViewById(R.id.classification);
        }
    }

    public static class Animal {
        private int type;
        private String animalName;
        private String classification;
        private List<Animal> invisibleChildren;

        public Animal(int type, String animalName, String classification) {
            this.type = type;
            this.animalName = animalName;
            this.classification = classification;
        }
    }
}
