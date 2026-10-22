package com.example.smartpantrymanager;
import android.view.*;import android.widget.*;import androidx.annotation.NonNull;import androidx.recyclerview.widget.RecyclerView;import java.util.*;import java.text.DecimalFormat;
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.Holder>{
 public interface Listener{void edit(PantryIngredient p);void delete(PantryIngredient p);} private final List<PantryIngredient> items;private final Listener listener;
 public IngredientAdapter(List<PantryIngredient> items,Listener l){this.items=items;listener=l;}
 @NonNull public Holder onCreateViewHolder(@NonNull ViewGroup p,int t){return new Holder(LayoutInflater.from(p.getContext()).inflate(R.layout.item_ingredient,p,false));}
 public void onBindViewHolder(@NonNull Holder h,int pos){PantryIngredient p=items.get(pos);h.name.setText(p.name);h.quantity.setText(new DecimalFormat("0.##").format(p.quantity)+" "+p.unit+(p.expiryDate.isEmpty()?"":" • Expires "+p.expiryDate));h.edit.setOnClickListener(v->listener.edit(p));h.delete.setOnClickListener(v->listener.delete(p));}
 public int getItemCount(){return items.size();}
 static class Holder extends RecyclerView.ViewHolder{TextView name,quantity;Button edit,delete;Holder(View v){super(v);name=v.findViewById(R.id.itemName);quantity=v.findViewById(R.id.itemQuantity);edit=v.findViewById(R.id.editButton);delete=v.findViewById(R.id.deleteButton);}}
}