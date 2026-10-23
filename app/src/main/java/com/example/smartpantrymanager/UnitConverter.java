package com.example.smartpantrymanager;

import java.util.Locale;

public final class UnitConverter {
    private UnitConverter() {
    }

    public static String normalizeName(String name) {
        if (name == null) return "";
        String n = name.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");
        // Handle common English plurals before the general trailing-s rule.
        if (n.endsWith("ies") && n.length() > 4) return n.substring(0, n.length() - 3) + "y";
        if (n.endsWith("oes") && n.length() > 4) return n.substring(0, n.length() - 2);
        if (n.endsWith("ses") && n.length() > 4) return n.substring(0, n.length() - 2);
        if (n.endsWith("s") && !n.endsWith("ss") && n.length() > 3)
            return n.substring(0, n.length() - 1);
        return n;
    }

    public static String dimension(String u) {
        if (u == null) return null;
        switch (u.trim().toLowerCase(Locale.ROOT)) {
            case "kg":
            case "g":
            case "gram":
            case "grams":
                return "mass";
            case "l":
            case "litre":
            case "litres":
            case "liter":
            case "liters":
            case "ml":
            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
            case "tsp":
            case "tbsp":
                return "volume";
            case "pcs":
            case "pc":
            case "piece":
            case "pieces":
            case "each":
            case "ea":
            case "unit":
            case "units":
                return "count";
            default:
                return null;
        }
    }

    public static double toBase(double x, String u) {
        if (u == null) return Double.NaN;
        switch (u.trim().toLowerCase(Locale.ROOT)) {
            case "kg":
                return x * 1000;
            case "g":
            case "gram":
            case "grams":
                return x;
            case "l":
            case "litre":
            case "litres":
            case "liter":
            case "liters":
                return x * 1000;
            case "ml":
            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
                return x;
            case "tsp":
                return x * 5;
            case "tbsp":
                return x * 15;
            case "pcs":
            case "pc":
            case "piece":
            case "pieces":
            case "each":
            case "ea":
            case "unit":
            case "units":
                return x;
            default:
                return Double.NaN;
        }
    }

    public static boolean compatible(String a, String b) {
        String da = dimension(a);
        return da != null && da.equals(dimension(b));
    }
}