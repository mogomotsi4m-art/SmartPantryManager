package com.example.smartpantrymanager;

import android.view.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.Holder> {
    private final List<Recipe> items;
    private final Listener listener;

    public RecipeAdapter(List<Recipe> items, Listener l) {
        this.items = items;
        listener = l;
    }

    @NonNull
    public Holder onCreateViewHolder(@NonNull ViewGroup p, int t) {
        return new Holder(LayoutInflater.from(p.getContext()).inflate(R.layout.item_recipe, p, false));
    }

    public void onBindViewHolder(@NonNull Holder h, int pos) {
        Recipe r = items.get(pos);
        h.name.setText(r.name);
        h.desc.setText(r.description);
        h.itemView.setOnClickListener(v -> listener.open(r));
    }

    public int getItemCount() {
        return items.size();
    }

    public interface Listener {
        void open(Recipe r);
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView name, desc;

        Holder(View v) {
            super(v);
            name = v.findViewById(R.id.recipeName);
            desc = v.findViewById(R.id.recipeDescription);
        }
    }
}