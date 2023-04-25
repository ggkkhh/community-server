package com.roydon.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * @USER: roydon
 * @DATE: 2023/4/25 15:41
 * @Description RandomUtils
 **/
public class RandomUtils {

    /**
     * 在一个范围中随机一个整数(包括两端的值)
     *
     * @param min
     * @param max
     * @return
     */
    private static Random RAND = new Random();

    public static int randomInt(int min, int max) {
        if (min == max) {
            return min;
        }
        Random r = new Random();
        int intValue = max - min;
        intValue = intValue < 0 ? 1 :
                ((intValue == Integer.MAX_VALUE) ? intValue : intValue + 1);
        return min + r.nextInt(intValue);
    }

    public static long randomLong() {
        Random r = new Random();
        return r.nextLong();
    }

    public static Double randomDouble() {
        Random r = new Random();
        return r.nextDouble();
    }

    public static float randomFloat(float min, float max) {
        if (min == max) {
            return min;
        }
        Random r = new Random();
        return min + (max - min) * r.nextFloat();
    }

    public static boolean successOrFailure(int percent) {
        Random r = new Random();
        int rValue = r.nextInt(100);
        if (rValue < percent) {
            return true;
        }
        return false;
    }

    /**
     * n个整数随机一个<br>
     *
     * @param i 整数可变长参数
     * @return 随机出的数
     */
    public static int randomIntByParamList(int... i) {
        if (i != null && i.length > 0) {
            return i[randomInt(0, i.length - 1)];
        }
        return 0;
    }

    public static <T> T choice(Collection<T> items) {
        if (items != null && items.size() > 0) {
            return new ArrayList<>(items).get(randomInt(0, items.size() - 1));
        } else {
            return null;
        }
    }

    /**
     * 通过百分比随机，看是否中
     *
     * @param percent
     * @return 返回“中不中”
     */
    public static boolean randomByPercent(double percent) {
        return RAND.nextDouble() < percent;
    }

    public static String randomString(int length) {

        final char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = chars[randomInt(0, chars.length - 1)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(randomDouble());
        }
    }
}
