package com.padawan.desafio.util;

public class StringUtil {
    
    public static String getOnlyDigits(final String value) {
        if (value != null && !value.trim().isEmpty()) {
            String returN = new String();
            for (char charTemp : value.toCharArray()) {
                if (Character.isDigit(charTemp)) {
                    returN += charTemp;
                }
            }
            if (!returN.isEmpty()) {
                return returN;
            }
        }
        return "";
    }
}
