package com.iridevescence.interconnect.api.skill;

import java.util.ArrayList;
import java.util.HashMap;

public class Registry {
    private static final HashMap<Restriction.Type, Restriction> RESTRICTIONS = new HashMap<>();

    public static void addRestriction(Restriction restriction) {
        RESTRICTIONS.put(restriction.type(), restriction);
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
}
