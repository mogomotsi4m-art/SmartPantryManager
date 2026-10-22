package com.example.smartpantrymanager;
import android.content.*;import android.os.Bundle;import android.widget.*;import androidx.appcompat.app.*;import androidx.recyclerview.widget.*;
public class MainActivity extends AppCompatActivity{
 private DatabaseHelper db;private TextView summary;private RecyclerView recycler;
 @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_main);db=new DatabaseHelper(this);summary=findViewById(R.id.summaryText);recycler=findViewById(R.id.pantryRecycler);recycler.setLayoutManager(new LinearLayoutManager(this));
 findViewById(R.id.addButton).setOnClickListener(v->startActivity(new Intent(this,IngredientFormActivity.class)));
 findViewById(R.id.recipesButton).setOnClickListener(v->startActivity(new Intent(this,RecipesActivity.class)));
 findViewById(R.id.settingsButton).setOnClickListener(v->startActivity(new Intent(this,SettingsActivity.class)));}
 @Override protected void onResume(){super.onResume();if(db!=null)refresh();}
 private void refresh(){java.util.List<PantryIngredient> list=db.getPantry();summary.setText(list.size()+" pantry item(s) • "+db.recipeCount()+" recipes stored");
 recycler.setAdapter(new IngredientAdapter(list,new IngredientAdapter.Listener(){public void edit(PantryIngredient p){Intent i=new Intent(MainActivity.this,IngredientFormActivity.class);i.putExtra("id",p.id);i.putExtra("name",p.name);i.putExtra("quantity",p.quantity);i.putExtra("unit",p.unit);i.putExtra("expiry",p.expiryDate);startActivity(i);}
 public void delete(PantryIngredient p){new AlertDialog.Builder(MainActivity.this).setTitle("Delete ingredient?").setMessage("Remove "+p.name+"?").setNegativeButton("Cancel",null).setPositiveButton("Delete",(d,w)->{if(db.deleteIngredient(p.id)){Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_SHORT).show();refresh();}}).show();}}));}
}