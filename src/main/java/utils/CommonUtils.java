package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class CommonUtils {

    private static final String CYRILLIC_CHARACTERS = "àáâãäå¸æçèéêëìíîïğñòóôõö÷øùúûüışÿÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÙÚÛÜİŞß";
    private static final String LATIN_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHARACTERS = "1234567890";

    public static String getCyrillicUniqueValueChar(int length) {
        return RandomStringUtils.random(length, CYRILLIC_CHARACTERS);
    }

    public static String getLatinUniqueValueChar(int length) {
        return RandomStringUtils.random(length, LATIN_CHARACTERS);
    }

    public static String getNumberUniqueValue(int length) {
        return RandomStringUtils.random(length, NUMBER_CHARACTERS);
    }

    public static String getCyrillicAndLatinCharacters() {
        return CYRILLIC_CHARACTERS + LATIN_CHARACTERS;
    }

    public static Long getRandomLong(Long min, Long max) {
        Random r = new Random();
        return r.nextLong((max - min) + 1) + min;
    }
}
