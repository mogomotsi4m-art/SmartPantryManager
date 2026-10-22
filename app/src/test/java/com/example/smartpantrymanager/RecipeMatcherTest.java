package com.example.smartpantrymanager;
import org.junit.Test;import java.util.*;import static org.junit.Assert.*;
public class RecipeMatcherTest{
 @Test public void fullMatch(){assertTrue(RecipeMatcher.qualifies(Arrays.asList(new PantryIngredient(1,"Flours",300,"g",""),new PantryIngredient(2,"milk",300,"ml","")),Arrays.asList(new RecipeIngredient("flour",200,"g"),new RecipeIngredient("milk",250,"ml"))));}
 @Test public void missingIngredient(){assertFalse(RecipeMatcher.qualifies(Collections.singletonList(new PantryIngredient(1,"flour",300,"g","")),Arrays.asList(new RecipeIngredient("flour",200,"g"),new RecipeIngredient("milk",100,"ml"))));}
 @Test public void insufficientQuantity(){assertFalse(RecipeMatcher.qualifies(Collections.singletonList(new PantryIngredient(1,"flour",100,"g","")),Collections.singletonList(new RecipeIngredient("flour",200,"g"))));}
 @Test public void pluralNormalization(){assertTrue(RecipeMatcher.qualifies(Collections.singletonList(new PantryIngredient(1,"tomatoes",3,"pcs","")),Collections.singletonList(new RecipeIngredient("tomato",2,"pcs"))));}
 @Test public void unitConversion(){assertTrue(RecipeMatcher.qualifies(Collections.singletonList(new PantryIngredient(1,"flour",1,"kg","")),Collections.singletonList(new RecipeIngredient("flour",500,"g"))));}
 @Test public void duplicatePantryRecordsAreSummed(){assertTrue(RecipeMatcher.qualifies(Arrays.asList(new PantryIngredient(1,"milk",100,"ml",""),new PantryIngredient(2," MILK ",150,"ml","")),Collections.singletonList(new RecipeIngredient("milk",250,"ml"))));}
 @Test public void incompatibleUnitsAreNotCombined(){assertFalse(RecipeMatcher.qualifies(Arrays.asList(new PantryIngredient(1,"flour",500,"g",""),new PantryIngredient(2,"flour",1,"pcs","")),Collections.singletonList(new RecipeIngredient("flour",600,"g"))));}
 @Test public void tomatoesNormalizeToTomato(){assertEquals("tomato",UnitConverter.normalizeName(" TOMATOES "));}
 @Test public void capitalisationAndWhitespaceDoNotMatter(){assertTrue(RecipeMatcher.qualifies(Collections.singletonList(new PantryIngredient(1,"  MILK   ",300,"ml","")),Collections.singletonList(new RecipeIngredient("milk",250,"ml"))));}
 @Test public void emptyPantry(){assertFalse(RecipeMatcher.qualifies(Collections.emptyList(),Collections.singletonList(new RecipeIngredient("flour",1,"g"))));}
 @Test public void unknownUnit(){assertFalse(RecipeMatcher.qualifies(Collections.singletonList(new PantryIngredient(1,"flour",100,"cups","")),Collections.singletonList(new RecipeIngredient("flour",20,"g"))));}
}