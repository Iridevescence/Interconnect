package com.iridevescence.interconnect.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.iridevescence.interconnect.api.skill.Restriction;

public class Registry {
    private static final HashMap<Restriction.Type, ArrayList<Restriction>> RESTRICTIONS = new HashMap<>();
    private static final HashMap<String, Restriction> S2R_MAPPING = new HashMap<>();

    public static void addRestriction(Restriction restriction) {
        ArrayList<Restriction> list = RESTRICTIONS.get(restriction.type());
        list.add(restriction);
        RESTRICTIONS.put(restriction.type(), list);
        S2R_MAPPING.put(restriction.name(), restriction);
    }

    public static ArrayList<Restriction> getRestrictionsByType(Restriction.Type type) {
        return RESTRICTIONS.get(type);
    }

    public static ArrayList<Restriction> getAllRestrictions() {
        ArrayList<Restriction> list = new ArrayList<>();
        RESTRICTIONS.forEach((type, restrictions) -> {
            list.addAll(restrictions);
        });
        return list;
    }

    public static Restriction getRestrictionByName(String name) {
        return S2R_MAPPING.get(name);
    }
}
