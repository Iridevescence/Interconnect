package com.iridevescence.interconnect.util;

import java.util.HashMap;

public class StringStorage {
    private static final HashMap<Integer, String> I2S = new HashMap<>();
    private static final HashMap<String, Integer> S2I = new HashMap<>();
    
    public static String getStringAt(int index) {
        return I2S.get(index);
    }

    public static int getIndexOf(String str) {
        return S2I.get(str);
    }
}
