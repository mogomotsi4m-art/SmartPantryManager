package com.example.smartpantrymanager;

import java.util.*;

public final class RecipeMatcher {
    private RecipeMatcher() {
    }

    public static boolean qualifies(List<PantryIngredient> pantry, List<RecipeIngredient> required) {
        Map<String, List<PantryIngredient>> map = new HashMap<>();
        for (PantryIngredient p : pantry)
            map.computeIfAbsent(UnitConverter.normalizeName(p.name), k -> new ArrayList<>()).add(p);
        for (RecipeIngredient r : required) {
            List<PantryIngredient> list = map.get(UnitConverter.normalizeName(r.name));
            if (list == null) return false;
            double need = UnitConverter.toBase(r.quantity, r.unit);
            if (Double.isNaN(need)) return false;
            double have = 0;
            for (PantryIngredient p : list)
                if (UnitConverter.compatible(p.unit, r.unit)) {
                    double v = UnitConverter.toBase(p.quantity, p.unit);
                    if (!Double.isNaN(v)) have += v;
                }
            if (have + 1e-9 < need) return false;
        }
        return true;
    }
}