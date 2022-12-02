package com.iridevescence.interconnect.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Collections;
import java.util.List;

import com.iridevescence.interconnect.api.skill.Restriction;

public class Registry {
    private static final HashMap<Restriction.Type, List<Restriction>> RESTRICTIONS = new HashMap<>();
    private static final HashMap<String, Restriction> S2R_MAPPING = new HashMap<>();

    public static void addRestriction(Restriction restriction) {
        List<Restriction> list = RESTRICTIONS.getOrDefault(restriction.type(), new ArrayList<>());
        list.add(restriction);
        RESTRICTIONS.put(restriction.type(), list);
        S2R_MAPPING.put(restriction.name(), restriction);
    }

    public static List<Restriction> getRestrictionsByType(Restriction.Type v) {
        return Optional.ofNullable(RESTRICTIONS.get(v)).map((f) -> Collections.unmodifiableList(f)).orElse(Collections.emptyList());
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
