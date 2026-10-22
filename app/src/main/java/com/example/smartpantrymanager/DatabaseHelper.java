package com.example.smartpantrymanager;
import android.content.*; import android.database.*; import android.database.sqlite.*; import java.util.*;
public class DatabaseHelper extends SQLiteOpenHelper {
 public DatabaseHelper(Context c){super(c,"smart_pantry.db",null,1);}
 @Override public void onCreate(SQLiteDatabase db){
  db.execSQL("CREATE TABLE pantry(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,quantity REAL NOT NULL,unit TEXT NOT NULL,expiry_date TEXT)");
  db.execSQL("CREATE TABLE recipes(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL UNIQUE,description TEXT,instructions TEXT)");
  db.execSQL("CREATE TABLE recipe_ingredients(id INTEGER PRIMARY KEY AUTOINCREMENT,recipe_id INTEGER NOT NULL,name TEXT NOT NULL,quantity REAL NOT NULL,unit TEXT NOT NULL,FOREIGN KEY(recipe_id) REFERENCES recipes(id) ON DELETE CASCADE)");
  seed(db);
 }
 @Override public void onUpgrade(SQLiteDatabase db,int old,int now){db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");db.execSQL("DROP TABLE IF EXISTS recipes");db.execSQL("DROP TABLE IF EXISTS pantry");onCreate(db);}
 private void seed(SQLiteDatabase db){
  String[][] rs={
   {"Pancakes","Breakfast pancakes","Mix flour, milk, egg and sugar. Cook small portions in a lightly oiled pan.","flour|200|g;milk|250|ml;egg|2|pcs;sugar|20|g"},
   {"Tomato Pasta","Pasta with tomato sauce","Boil pasta. Cook chopped tomato and garlic, combine and serve.","pasta|200|g;tomato|2|pcs;garlic|1|pcs"},
   {"Omelette","Quick egg omelette","Beat eggs, add milk and cook gently.","egg|2|pcs;milk|30|ml;salt|2|g"},
   {"Banana Smoothie","Creamy banana smoothie","Blend banana and milk until smooth.","banana|1|pcs;milk|250|ml"},
   {"Cheese Toast","Toast with cheese","Top bread with cheese and toast until melted.","bread|2|pcs;cheese|40|g"},
   {"Garlic Rice","Flavoured rice","Cook rice and stir through lightly fried garlic.","rice|200|g;garlic|1|pcs"},
   {"Flour Flatbread","Simple pan flatbread","Mix flour, water and salt; knead, roll and cook.","flour|250|g;water|150|ml;salt|3|g"},
   {"Tomato Salad","Fresh tomato salad","Slice tomatoes and season.","tomato|3|pcs;salt|2|g"},
   {"Peanut Butter Toast","Toast with peanut butter","Spread peanut butter on bread.","bread|2|pcs;peanut butter|30|g"},
   {"Garlic Butter Pasta","Pasta with garlic butter","Boil pasta and toss with garlic and butter.","pasta|200|g;garlic|2|pcs;butter|30|g"},
   {"Rice Pudding","Milk rice pudding","Simmer rice in milk with sugar until tender.","rice|100|g;milk|500|ml;sugar|30|g"},
   {"Cheese Omelette","Omelette with cheese","Cook beaten eggs and fold in cheese.","egg|2|pcs;cheese|30|g"},
   {"Banana Pancakes","Banana breakfast pancakes","Mash banana, mix with egg and flour, cook pancakes.","banana|1|pcs;egg|1|pcs;flour|80|g"},
   {"Tomato Cheese Toast","Toast with tomato and cheese","Top bread with tomato and cheese, then toast.","bread|2|pcs;tomato|1|pcs;cheese|40|g"},
   {"Milk Tea","Tea with milk","Steep tea in hot water and add milk.","tea|1|pcs;water|200|ml;milk|50|ml"},
   {"Garlic Tomato Sauce","Tomato and garlic sauce","Cook tomato with garlic until soft.","tomato|3|pcs;garlic|2|pcs"},
   {"Sweet Porridge","Warm milk porridge","Whisk flour into milk and heat with sugar.","flour|50|g;milk|300|ml;sugar|15|g"},
   {"Peanut Banana Smoothie","Banana peanut smoothie","Blend banana, milk and peanut butter.","banana|1|pcs;milk|200|ml;peanut butter|20|g"},
   {"Buttered Rice","Rice with butter","Cook rice and stir through butter.","rice|200|g;butter|20|g"},
   {"Garlic Bread","Toasted garlic bread","Mix garlic into butter, spread on bread and toast.","bread|2|pcs;garlic|1|pcs;butter|20|g"}
  };
  db.beginTransaction();try{
   for(String[] r:rs){ContentValues v=new ContentValues();v.put("name",r[0]);v.put("description",r[1]);v.put("instructions",r[2]);long id=db.insert("recipes",null,v);
    for(String part:r[3].split(";")){String[] a=part.split("\\|");ContentValues iv=new ContentValues();iv.put("recipe_id",id);iv.put("name",a[0]);iv.put("quantity",Double.parseDouble(a[1]));iv.put("unit",a[2]);db.insert("recipe_ingredients",null,iv);}
   }db.setTransactionSuccessful();
  }finally{db.endTransaction();}
 }
 public List<PantryIngredient> getPantry(){List<PantryIngredient> out=new ArrayList<>();try(Cursor c=getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,expiry_date FROM pantry ORDER BY name COLLATE NOCASE",null)){while(c.moveToNext())out.add(new PantryIngredient(c.getLong(0),c.getString(1),c.getDouble(2),c.getString(3),c.getString(4)));}return out;}
 public long saveIngredient(PantryIngredient p){ContentValues v=new ContentValues();v.put("name",p.name.trim());v.put("quantity",p.quantity);v.put("unit",p.unit.trim());v.put("expiry_date",p.expiryDate);if(p.id>0)return getWritableDatabase().update("pantry",v,"id=?",new String[]{""+p.id})>0?p.id:-1;return getWritableDatabase().insert("pantry",null,v);}
 public boolean deleteIngredient(long id){return getWritableDatabase().delete("pantry","id=?",new String[]{""+id})>0;}
 public List<Recipe> getRecipes(){List<Recipe> out=new ArrayList<>();try(Cursor c=getReadableDatabase().rawQuery("SELECT id,name,description,instructions FROM recipes ORDER BY name",null)){while(c.moveToNext())out.add(new Recipe(c.getLong(0),c.getString(1),c.getString(2),c.getString(3)));}return out;}
 public List<RecipeIngredient> getRecipeIngredients(long id){List<RecipeIngredient> out=new ArrayList<>();try(Cursor c=getReadableDatabase().rawQuery("SELECT name,quantity,unit FROM recipe_ingredients WHERE recipe_id=?",new String[]{""+id})){while(c.moveToNext())out.add(new RecipeIngredient(c.getString(0),c.getDouble(1),c.getString(2)));}return out;}
 public List<Recipe> getSuggestedRecipes(){List<Recipe> out=new ArrayList<>();List<PantryIngredient> p=getPantry();for(Recipe r:getRecipes())if(RecipeMatcher.qualifies(p,getRecipeIngredients(r.id)))out.add(r);return out;}
 public Recipe getRecipe(long id){try(Cursor c=getReadableDatabase().rawQuery("SELECT id,name,description,instructions FROM recipes WHERE id=?",new String[]{""+id})){if(c.moveToFirst())return new Recipe(c.getLong(0),c.getString(1),c.getString(2),c.getString(3));}return null;}
 public int recipeCount(){try(Cursor c=getReadableDatabase().rawQuery("SELECT COUNT(*) FROM recipes",null)){return c.moveToFirst()?c.getInt(0):0;}}
}