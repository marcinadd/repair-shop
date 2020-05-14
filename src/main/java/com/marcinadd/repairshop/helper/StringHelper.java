package com.marcinadd.repairshop.helper;

import com.sun.istack.Nullable;

public class StringHelper {
    public static String replaceCharsWithAsterisks(@Nullable String string, int preserveFirst, int preserveLast) {
        if (string == null)
            return null;
        int n = string.length();
        if (preserveFirst + preserveLast >= n)
            return string;
        String first = string.substring(0, preserveFirst);
        String last = string.substring(n - preserveLast);
        return first + "*".repeat(n - preserveLast - preserveFirst) + last;
    }
}
