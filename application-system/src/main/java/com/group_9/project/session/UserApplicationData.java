package com.group_9.project.session;

import java.util.HashMap;
import java.util.Map;

public class UserApplicationData {
    private static final Map<String, String> data = new HashMap<>();

    public static void set(String key, String value) {
        data.put(key, value);
    }

    public static String get(String key) {
        return data.getOrDefault(key, "");
    }

    public static void clear() {
        data.clear();
    }

    public static Map<String, String> getAll() {
        return new HashMap<>(data); // optional: for debugging or exporting
    }
}
