package com.iridevescence.interconnect.api.skill;

import java.util.ArrayList;
import java.util.HashMap;

public class Registry {
    private static final HashMap<Restriction.Type, Restriction> RESTRICTIONS = new HashMap<>();
    private static final HashMap<String, Restriction> S2R_MAPPING = new HashMap<>();

    public static void addRestriction(Restriction restriction) {
        RESTRICTIONS.put(restriction.type(), restriction);
        S2R_MAPPING.put(restriction.name(), restriction);
    }

    public static ArrayList<Restriction> getRestrictionsByType(Restriction.Type type) {
        ArrayList<Restriction> list = new ArrayList<>();
        RESTRICTIONS.forEach((mapType, restriction) -> {
            if (mapType == type) {
                list.add(restriction);
            }
        });
        return list;
    }

    public static Restriction getRestrictionByName(String name) {
        return S2R_MAPPING.get(name);
    }
}
