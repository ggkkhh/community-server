package com.roydon.common.utils;

import cn.hutool.core.util.RandomUtil;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {
    public static final String EMPTY = "";
    public static final String DEFAULT_CHARSET = "utf-8";
    public static final String DOT = ".";
    public static final String SLASH = "/";
    public static final String COMMA = ",";
    public final static String UNDERLINE = "_";
    public final static String t = "t";
    public final static String T = "T";
    public final static String NULL = "null";
    public final static String COLON = ":";
    protected final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    protected final static String HEX_CHARS = "0123456789abcdefABCDEF";
    protected final static byte[] HEX_VALUES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 10, 11, 12, 13, 14, 15};

    protected final static byte HEX_BYTES[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
            0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    protected final static byte NUM_BYTES[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
            0, 0, 0, 0};
    private static final String URL_PATTERN = "^https?:\\/\\/(([a-zA-Z0-9_-])+(\\.)?)*(:\\d+)?(\\/((\\.)?(\\?)?=?&?[a-zA-Z0-9_-](\\?)?)*)*$";

    public static String replaceNewLine(String str) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        return str.replaceAll("\r|\n", "");
    }

    public static void main(String[] args) {

        System.out.println("isFixedPhone===" + isMobileOrFixedPhone("0321-88889999"));
        System.out.println("isFixedPhone===" + isMobileOrFixedPhone("031-88889999"));
        System.out.println("isFixedPhone===" + isMobileOrFixedPhone("03188889"));
        boolean isUrl = isUrl("www.baidu.com");
        System.out.println("isUrl===" + isUrl);

        System.out.println("isWordOrNumberLimit818===1" + isWordOrNumberLimit818("03188889d."));
        System.out.println("isWordOrNumberLimit818===2" + isWordOrNumberLimit818("1234512345"));
        System.out.println("isWordOrNumberLimit818===3" + isWordOrNumberLimit818("12345123(45)2d"));
        System.out.println("isWordOrNumberLimit818===4" + isWordOrNumberLimit818("12345123451234512345"));
        System.out.println("isWordOrNumberLimit818===5" + isWordOrNumberLimit818("123451234512345123"));
        System.out.println("isWordOrNumberLimit818===6" + isWordOrNumberLimit818("12345123（45）2d"));
        System.out.println("isWordOrNumberLimit818===7" + isWordOrNumberLimit818("12345123（45,）2d"));

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.HOUR_OF_DAY, 48);
        long curReceiveDeadline = cal.getTimeInMillis();
        System.out.println("isWordOrNumberLimit818===" + curReceiveDeadline);

        String res = "ab\r\nc";
        System.out.println(res);
        res = res.replace("\r", "");
        res = res.replace("\n", "");
        System.out.println(res);

        System.out.println("isNumber===1" + isNumber("111"));
        System.out.println("isNumber===2" + isNumber("abc"));
        System.out.println("isNumber===3" + isNumber("aab2111"));
        System.out.println("isAlphabetOrNumber===4" + isAlphabetAndNumberAndUnderline("aab2d,111"));
        System.out.println("isAlphabetOrNumber===5" + isAlphabetAndNumberAndUnderline("a"));
        System.out.println("isAlphabetOrNumber===6" + isAlphabetAndNumberAndUnderline("a_"));
        System.out.println("isAlphabetOrNumber===7" + isAlphabetAndNumberAndUnderline("a_102"));
        System.out.println("isAlphabetOrNumber===8" + isAlphabetAndNumberAndUnderline("a__"));
        System.out.println("isAlphabetOrNumber===9" + isAlphabetAndNumberAndUnderline("a_1"));

        System.out.println(StandardCharsets.UTF_8.name());

        String genRandomChinese = genRandomChinese(5, 10);
        System.out.println("genRandomChinese = " + genRandomChinese);

        String randomString = genRandomLengthStr(10);
        System.out.println("Random string is: " + randomString);

    }

    public static String genRandomLengthStr(int length) {
        String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String allCharacters = alphabetsInLowerCase + alphabetsInUpperCase + numbers;
        StringBuffer randomString = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * allCharacters.length());
            randomString.append(allCharacters.charAt(randomIndex));
        }
        return randomString.toString();
    }

    /**
     * 将下划线命名方式的字段名改为驼峰式
     *
     * @param str
     * @return
     */
    public static String toFieldCamelCase(String str) {
        final String separate = "_";
        if (str.indexOf(separate) == -1) {
            return str;
        }

        String arr[] = str.split(separate);
        StringBuilder sb = new StringBuilder(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            sb.append(firstToUpper(arr[i]));
        }
        return sb.toString();
    }

    public static String formatNumber(int randomInt, int length) {
        String tmp = randomInt + "";
        if (tmp.length() > length) {
            return tmp.substring(0, length);
        }
        return String.format("%0" + length + "d", randomInt);
    }

    public static boolean isDecString(String string) {
        if (isNullEmpty(string)) {
            return false;
        }
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            if (ch > 63 || NUM_BYTES[ch] == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对象是否不为空(新增)
     *
     * @param o String,List,Map,Object[],int[],long[]
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 对象是否为空
     *
     * @param o String,List,Map,Object[],int[],long[]
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            if (o.toString().trim().equals("")) {
                return true;
            }
        } else if (o instanceof List) {
            if (((List) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Set) {
            if (((Set) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Object[]) {
            if (((Object[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof int[]) {
            if (((int[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof long[]) {
            if (((long[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof BlockingQueue) {
            if (((BlockingQueue) o).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将驼峰式命名的字符串转换为下划线方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->hello_world
     *
     * @param camelCaseStr 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String toUnderlineCase(String camelCaseStr) {
        if (camelCaseStr == null) {
            return null;
        }

        final int length = camelCaseStr.length();
        StringBuilder sb = new StringBuilder();
        char c;
        boolean isPreUpperCase = false;
        for (int i = 0; i < length; i++) {
            c = camelCaseStr.charAt(i);
            boolean isNextUpperCase = true;
            if (i < (length - 1)) {
                isNextUpperCase = Character.isUpperCase(camelCaseStr.charAt(i + 1));
            }
            if (Character.isUpperCase(c)) {
                if (!isPreUpperCase || !isNextUpperCase) {
                    if (i > 0) sb.append(UNDERLINE);
                }
                isPreUpperCase = true;
            } else {
                isPreUpperCase = false;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffix(String str, String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return str;
        }

        if (str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isHexString(String string) {
        if (isNullEmpty(string)) {
            return false;
        }
        int len = string.length();
        if (len % 2 == 0) {
            for (int i = 0; i < string.length(); i++) {
                char ch = string.charAt(i);
                if (ch > 127 || HEX_BYTES[ch] == 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static String simpleClassName(Class<?> clazz) {
        String className = ((Class<?>) clazz).getName();
        int lastDotIdx = className.lastIndexOf('.');
        if (lastDotIdx > -1) {
            return className.substring(lastDotIdx + 1);
        }
        return className;
    }

    public static String simpleClassName(Object o) {
        if (o == null) {
            return "null_object";
        }
        return simpleClassName(o.getClass());
    }

    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String LongToHexString(long value) {
        int len = 16;
        char[] hexChars = new char[len];
        for (int j = 0; j < 8; j++) {
            int v = (int) value & 0xFF;
            hexChars[(7 - j) << 1] = HEX_ARRAY[v >>> 4];
            hexChars[((7 - j) << 1) + 1] = HEX_ARRAY[v & 0x0F];
            value = (value >>> 8);
        }
        return new String(hexChars);
    }

    public static String LongToHexString(long high, long low) {
        int len = 32;
        char[] hexChars = new char[len];

        for (int j = 0; j < 8; j++) {
            int hv = (int) high & 0xFF;
            int lv = (int) low & 0xFF;
            hexChars[(7 - j) << 1] = HEX_ARRAY[hv >>> 4];
            hexChars[((7 - j) << 1) + 1] = HEX_ARRAY[hv & 0x0F];
            hexChars[((7 - j) << 1) + 16] = HEX_ARRAY[lv >>> 4];
            hexChars[((7 - j) << 1) + 1 + 16] = HEX_ARRAY[lv & 0x0F];
            high = (high >>> 8);
            low = (low >>> 8);
        }
        return new String(hexChars);
    }

    public static String LongToHexString(long high, long middle, long low) {
        int len = 48;
        char[] hexChars = new char[len];

        for (int j = 0; j < 8; j++) {
            int hv = (int) high & 0xFF;
            int mv = (int) middle & 0xFF;
            int lv = (int) low & 0xFF;
            hexChars[(7 - j) << 1] = HEX_ARRAY[hv >>> 4];
            hexChars[((7 - j) << 1) + 1] = HEX_ARRAY[hv & 0x0F];
            hexChars[((7 - j) << 1) + 16] = HEX_ARRAY[mv >>> 4];
            hexChars[((7 - j) << 1) + 1 + 16] = HEX_ARRAY[mv & 0x0F];
            hexChars[((7 - j) << 1) + 32] = HEX_ARRAY[lv >>> 4];
            hexChars[((7 - j) << 1) + 1 + 32] = HEX_ARRAY[lv & 0x0F];
            high = (high >>> 8);
            middle = (middle >>> 8);
            low = (low >>> 8);
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null) {
            return null;
        }

        if (hexString.length() % 2 != 0) {
            return null;
        }
        try {
            byte[] bytes = new byte[hexString.length() / 2];
            for (int i = 0; i < hexString.length() / 2; i++) {
                char ch = hexString.charAt(i * 2);
                bytes[i] = (byte) (HEX_VALUES[HEX_CHARS.indexOf(ch)] << 4);

                ch = hexString.charAt(i * 2 + 1);
                bytes[i] |= (byte) (HEX_VALUES[HEX_CHARS.indexOf(ch)]);
            }

            return bytes;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param str
     * @return 濡傛灉杞崲澶辫触锛岃繑鍥�0锛�
     * @Description 鎶奡tring绫诲瀷鐨勫�艰浆鍖栦负int绫诲瀷
     * @author Terry
     * @time 2016骞�5鏈�11鏃�
     */
    public static int valueOfInt(String str) {
        int result = 0;
        if (!isNullEmpty(str)) {
            try {
                result = Integer.parseInt(str);
            } catch (Exception e) {
                e.printStackTrace();
                result = 0;
            }
        }
        return result;
    }

    public static Integer valueOfInteger(String str) {
        Integer result = null;
        if (!isNullEmpty(str)) {
            result = Integer.parseInt(str);
        }
        return result;
    }

    /**
     * @param str
     * @return
     * @Description
     * @author Terry
     * @time
     */
    public static boolean isNullEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * @return
     * @author Terry
     * @time
     */
    public static long valueOfLong(String str) {
        long result = 0;
        if (!isNullEmpty(str)) {
            try {
                result = Long.parseLong(str);
            } catch (Exception e) {
                result = 0;
            }
        }
        return result;
    }

    public static Short valueOfShort(String str) {
        Short result = 0;
        if (!isNullEmpty(str)) {
            result = Short.parseShort(str);
        }
        return result;
    }

    /**
     * 姝ゆ柟娉曟槸鏍规嵁瀛楃涓插垽鏂槸false寮�杩樻槸true,浼犲叆鐨勫瓧绗︿覆濡傛灉鏄互t,T锛屽紑澶存垨绛変簬 1锛岄兘瑙嗕负true
     *
     * @param str
     * @return
     * @author Terry
     * @time 2016骞�5鏈�11鏃�
     */
    public static boolean valueOfBoolean(String str) {
        boolean flag = false;
        if (!isNullEmpty(str)) {
            if (str.startsWith(t) || str.startsWith(T) || str.equals("1")) {
                flag = true;
            }
        }
        return flag;
    }

    public static byte valueOfByte(String str) {
        byte result = 0;
        if (!isNullEmpty(str)) {
            result = Byte.parseByte(str);
        }
        return result;
    }

    public static double valueOfDouble(String str) {
        double result = 0;
        if (!isNullEmpty(str)) {
            result = Double.parseDouble(str);
        }
        return result;
    }

    /**
     * <p>
     * Title: firstToUpper
     * </p>
     * <p>
     * Description:鎶婂瓧绗︿覆杞寲涓洪瀛楁瘝澶у啓鐨勫瓧绗︿覆
     * </p>
     *
     * @param str
     * @return
     * @author guangshuai.wang
     */
    public static String firstToUpper(String str) {
        return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
    }

    public static String firstToLower(String str) {
        return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toLowerCase());
    }

    /**
     * 姝ゆ柟娉曟槸鑾峰彇涓�涓敮涓�鐨剈uid
     *
     * @return
     * @author Terry
     * @time 2016骞�6鏈�6鏃�
     */
    public static String getUUid() {
        return UUID.randomUUID().toString();
    }


    /**
     * 鎶婂浐瀹氭牸寮忕殑瀛楃鐨勬暟缁勪覆杞寲涓簃ap锛歬ey:value key:value銆傘�傘�傘��
     * 杩欎釜鏂规硶鎶婂弬鏁拌浆鍖栦负key-value瀵瑰簲鐨刴ap闆嗗悎
     */
    public static Map<String, String> splitStrToMap(String[] args) {
        Map<String, String> map = new HashMap<>();
        if (args != null && args.length > 0) {
            for (String arg : args) {
                String[] keyValues = arg.split(":");
                if (keyValues.length == 2) {
                    map.put(keyValues[0], keyValues[1]);
                }
            }
        }
        return map;
    }

    /**
     * 鎻忚堪:杩欎釜鏂规硶鏄皢涓�涓笅鍒掔嚎鍒嗛殧鐨勫瓧绗︿覆杞寲涓洪瀛楁瘝澶у啓鐨勯┘宄板懡鍚嶅瓧绗︿覆
     *
     * @param
     * @return
     * @author Terry
     * @time 2016骞�8鏈�6鏃�-涓嬪崍4:46:53
     */
    public static String toCamelCase(String field) {
        field = field.toLowerCase();
        String[] strs = field.split(UNDERLINE);
        StringBuilder className = new StringBuilder();
        if (strs.length >= 2) {

            for (String str : strs) {

                className.append(StringUtil.firstToUpper(str));
            }
        } else {

            className.append(StringUtil.firstToUpper(field));
        }
        return className.toString();
    }

    public static String toCamelCaseField(String field) {
        return StringUtil.firstToLower(toCamelCase(field));
    }

    public static byte[] toBytes(String str) throws IOException {
        try {
            return str.getBytes("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] strToBytes(String str) {
        try {
            return str.getBytes("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String fromBytes(byte[] str) throws IOException {
        return new String(str, "utf-8");
    }

    public static String bytesToUtf8(byte[] str) {
        try {
            return new String(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * @param value
     * @return
     * @Desc 描述：检测某个字符串是否都是数字
     * @Date 2017年6月8日 下午7:49:27
     */
    public static boolean checkNumber(String value) {
        return isDecString(value);
    }

    public static boolean isUrl(String value) {
        Pattern pattern = Pattern.compile(URL_PATTERN);
        return pattern.matcher(value).matches();
    }

    public static boolean isHttpUrl(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        return pattern.matcher(value).matches();
    }

    public static String byteToUnit(long value) {
        String result = null;
        int k = 1024;
        long temp = value / (k * k * k);
        if (temp != 0) {
            double d = (value * 1.0d) / (k * k * k);
            BigDecimal b = new BigDecimal(d);
            d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            result = String.valueOf(d);
            return result + "G";
        }
        temp = value / (k * k);
        if (temp != 0) {
            double d = (value * 1.0d) / (k * k);
            BigDecimal b = new BigDecimal(d);
            d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            result = String.valueOf(d);
            return result + "M";
        }
        temp = value / k;
        if (temp != 0) {
            double d = (value * 1.0d) / k;
            BigDecimal b = new BigDecimal(d);
            d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            result = String.valueOf(d);
            return result + "K";
        }
        return value + "B";
    }

    public static long getByteUnit(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        value = value.toLowerCase();
        long result = 0;
        result = getNumValue(value);
        if (value.endsWith("k")) {
            result = result * 1024;
        } else if (value.endsWith("m")) {
            result = result * 1024 * 1024;
        } else if (value.endsWith("g")) {
            result = result * 1024 * 1024 * 1024;
        }
        return result;
    }

    private static long getNumValue(String value) {
        if (value.length() == 1) {
            return StringUtil.valueOfLong(value);
        }
        String numbValue = value.substring(0, value.length() - 1);
        return StringUtil.valueOfLong(numbValue);
    }

    /**
     * 大写首字母<br>
     * 例如：str = name, return Name
     *
     * @param str 字符串
     * @return
     */
    public static String upperFirst(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 小写首字母<br>
     * 例如：str = Name, return name
     *
     * @param str 字符串
     * @return
     */
    public static String lowerFirst(String str) {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    public static String append(String... strs) {
        if (strs != null && strs.length > 0) {
            final StringBuilder builder = new StringBuilder();
            for (String str : strs) {
                builder.append(str);
            }
            final String result = builder.toString();
            builder.setLength(0);
            return result;
        } else {
            return null;
        }
    }

    public static StringBuilder append(StringBuilder... strBils) {
        if (strBils != null && strBils.length > 0) {
            final StringBuilder builder = new StringBuilder();
            for (StringBuilder str : strBils) {
                builder.append(str);
            }
            final StringBuilder result = builder;
            builder.setLength(0);
            return result;
        } else {
            return null;
        }
    }

    /**
     * 去掉指定前缀
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 preffix， 返回原字符串
     */
    public static String removePrefix(String str, String prefix) {
        if (str != null && str.startsWith(prefix)) {
            return str.substring(prefix.length());
        }
        return str;
    }

    private static final int BYTES_THRESHOLD = 50;

    public static String bytesToString(byte[] data) {
        if (data == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder(70);
        if (data.length > BYTES_THRESHOLD) {
            return "bytes <len=" + data.length + ">";
        } else {
            return sb.append('b').append('"').append(Base64.getEncoder().encodeToString(data)).append('"').toString();
        }
    }

    public static String stringToString(String str) {
        if (str == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder(str.length() + 2);
        return sb.append('"').append(str).append('"').toString();
    }

    /**
     * unicode 转字符串
     *
     * @param unicode 全为 Unicode 的字符串
     * @return
     */
    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            String s = hex[i].replaceAll("\\\\", "");
            int data = Integer.parseInt(s, 16);
            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

    /**
     * 含有unicode 的字符串转一般字符串
     *
     * @param unicodeStr 混有 Unicode 的字符串
     * @return
     */
    public static String unicodeStr2String(String unicodeStr) {
        int length = unicodeStr.length();
        int count = 0;
        //正则匹配条件，可匹配“\\u”1到4位，一般是4位可直接使用 String regex = "\\\\u[a-f0-9A-F]{4}";
        String regex = "\\\\u[a-f0-9A-F]{1,4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(unicodeStr);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String oldChar = matcher.group();//原本的Unicode字符
            String newChar = unicode2String(oldChar);//转换为普通字符
            int index = unicodeStr.indexOf(oldChar);

            sb.append(unicodeStr.substring(count, index));//添加前面不是unicode的字符
            sb.append(newChar);//添加转换后的字符
            count = index + oldChar.length();//统计下标移动的位置
        }
        sb.append(unicodeStr.substring(count, length));//添加末尾不是Unicode的字符
        return sb.toString();
    }

    /**
     * 字符串转换unicode
     *
     * @param string
     * @return
     */
    public static String string2Unicode(String string) {
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            unicode.append("\\u");
            // 取出每一个字符
            char c = string.charAt(i);
            String num = Integer.toHexString(c);
            if (num.length() < 4) {
                // 如果不够4位，补零
                int len = 4 - num.length();
                for (int i1 = 0; i1 < len; i1++) {
                    unicode.append("0");
                }
            }
            // 转换为unicode
            unicode.append(num);
        }
        return unicode.toString();
    }

    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     * not empty and not null and not whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !StringUtil.isBlank(cs);
    }

    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isEmpty(Collection cs) {
        return cs == null || cs.size() == 0;
    }

    /**
     * <p>Checks if a CharSequence is not empty ("") and not null.</p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !StringUtil.isEmpty(cs);
    }

    public static String findFirstClassName(String source) {
        final String regex = "class[\\s]*[\\S]*[\\s]*\\{";
        final Pattern patternEnd = Pattern.compile(regex);
        final Matcher matcherEnd = patternEnd.matcher(source);
        while (matcherEnd.find()) {
            String res = matcherEnd.group();
            if (StringUtil.isNotEmpty(res)) {
                res = res.replace("class", "");
                res = res.replace("{", "");
                res = res.replace(" ", "");
                res = res.replace("\r", "");
                res = res.replace("\n", "");
            }
            return res;
        }
        return null;
    }

    public static String findFirstClassNameWithExtendsNoStatic(String source) {
        final String regex = "public class[\\s]*[\\S]*[\\s]*extends";
        final Pattern patternEnd = Pattern.compile(regex);
        final Matcher matcherEnd = patternEnd.matcher(source);
        while (matcherEnd.find()) {
            String res = matcherEnd.group();
            if (StringUtil.isNotEmpty(res)) {
                res = res.replace("public class", "");
                res = res.replace("extends", "");
                res = res.replace(" ", "");
            }
            return res;
        }
        return null;
    }

    public static String findFirstClassNameWithExtends(String source) {
        final String regex = "class[\\s]*[\\S]*[\\s]*extends";
        final Pattern patternEnd = Pattern.compile(regex);
        final Matcher matcherEnd = patternEnd.matcher(source);
        while (matcherEnd.find()) {
            String res = matcherEnd.group();
            if (StringUtil.isNotEmpty(res)) {
                res = res.replace("class", "");
                res = res.replace("extends", "");
                res = res.replace(" ", "");
            }
            return res;
        }
        return null;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String urlEncode(String str) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, CharsetUtil.UTF_8.name());
        // 解决空格被转义成加号问题
        result = result.replaceAll("\\+", "%20");
        return result;
    }

    public static boolean isFixedPhone(String fixedPhone) {
        if (StringUtil.isEmpty(fixedPhone)) {
            return false;
        }

        String reg = "^[0][0-9]{2,4}-[0-9]{5,10}$";
        return Pattern.matches(reg, fixedPhone);
    }

    public static boolean isMobilePhone(String str) {
        if (StringUtil.isNotEmpty(str) && str.length() == 11) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumber(String input) {
        final String regex = "^\\d+$";
        final boolean match = isMatch(regex, input);
        return match;
    }

    private static final String REGEX_ALPHABET_NUMBER = "^[A-Za-z0-9]+$";

    // 是英文或数字
    public static boolean isAlphabetOrNumber(String input) {
        return isMatch(REGEX_ALPHABET_NUMBER, input);
    }

    private static final String REGEX_ALPHABET_AND_NUMBER = "^[A-Za-z_][A-Za-z0-9_]*$";

    // 是英文或英文数字下划线混合，且不能_或_数字结尾
    public static boolean isAlphabetAndNumberAndUnderline(String input) {
        final boolean match1 = isMatch(REGEX_ALPHABET_AND_NUMBER, input);
        final boolean match2 = isMatch("^.+_\\d+$", input);
        final boolean match3 = isMatch("^.+_+$", input);
        return match1 && !match2 && !match3;
    }

    public static boolean isMobileOrFixedPhone(String str) {
        return isMobilePhone(str) || isFixedPhone(str);
    }

    private static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    public static boolean isMail(final CharSequence input) {
        return isMatch(REGEX_EMAIL, input);
    }

    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    private static final String REGEX_8_18 = "[0-9a-zA-Z()（）]{8,18}";

    public static boolean isWordOrNumberLimit818(String input) {
        return isMatch(REGEX_8_18, input);
    }

    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    public static boolean equalsIgnoreNull(String str1, String str2) {
        str1 = Objects.isNull(str1) ? "" : str1;
        str2 = Objects.isNull(str2) ? "" : str2;
        return str1.equals(str2);
    }

    public static String genRandomChinese(int length) {
        return genRandomChinese(length, false);
    }

    public static String genRandomChinese(int length, boolean isMaxLength) {
        if (isMaxLength) {
            length = new Random().nextInt(length + 1);
            length = length < 1 ? 1 : length;
        }
        String[] chinese = new String[length];
        for (int i = 0; i < chinese.length; i++) {
            chinese[i] = String.valueOf((char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1))));
        }
        return StringUtils.join(chinese);
    }

    public static String join(List<String> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s + separator);
        }
        return sb.substring(0, sb.length() - separator.length());
    }

    public static String genRandomChinese(int minilength, int maxLength) {
        int length = RandomUtils.nextInt(minilength, maxLength + 1);
        length = length < 1 ? 1 : length;
        String[] chinese = new String[length];
        for (int i = 0; i < chinese.length; i++) {
            chinese[i] = String.valueOf((char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1))));
        }
        return StringUtils.join(chinese);
    }
//	public static void main(String[] args) throws Exception{
//		ByteOutputStream out = new ByteOutputStream();
//		int count = RandomUtil.randomInt(10, 2048);
////        int count = 10;
//		System.out.println(count);
//
//		for (int i = 0; i < count; i++) {
//			int v = RandomUtil.randomInt(0, 300);
////            int v = i;
//			out.write(v);
//		}
//
//		byte[] bytes = out.toByteArray();
//		System.out.println(bytes.length);
//		System.out.println(new String(bytes, "UTF-8"));
//
//		String encode = Base64Util.encode(bytes);
//		System.out.println(encode);
//
//	}

    //B右移位数
    private static final int B_MOVE_LENGTH = 0;
    //KB右移位数
    private static final int KB_MOVE_LENGTH = 10;
    //MB右移位数
    private static final int MB_MOVE_LENGTH = 20;
    //GB右移位数
    private static final int GB_MOVE_LENGTH = 30;
    //TB右移位数
    private static final int TB_MOVE_LENGTH = 40;
    //PB右移位数
    private static final int PB_MOVE_LENGTH = 50;
    //EB右移位数
    private static final int EB_MOVE_LENGTH = 60;
    //计算小数时用到
    private static final long BIT_UPPER_MASK = 1023L;
    //进制
    private static final BigDecimal BIT_UPPER = new BigDecimal("1024");

    /**
     * 格式化byte字节数
     *
     * @param byteSize byte字节长度
     * @return 易于理解的格式化字符串
     */
    public static String formatByteLength(long byteSize) {
        int round;
        if ((round = (int) (byteSize >> EB_MOVE_LENGTH)) > 0) {
            return concat(round, (int) ((byteSize >> PB_MOVE_LENGTH) & BIT_UPPER_MASK), "EB");
        } else if ((round = (int) (byteSize >> PB_MOVE_LENGTH)) > 0) {
            return concat(round, (int) ((byteSize >> TB_MOVE_LENGTH) & BIT_UPPER_MASK), "PB");
        } else if ((round = (int) (byteSize >> TB_MOVE_LENGTH)) > 0) {
            return concat(round, (int) ((byteSize >> GB_MOVE_LENGTH) & BIT_UPPER_MASK), "TB");
        } else if ((round = (int) (byteSize >> GB_MOVE_LENGTH)) > 0) {
            return concat(round, (int) ((byteSize >> MB_MOVE_LENGTH) & BIT_UPPER_MASK), "GB");
        } else if ((round = (int) (byteSize >> MB_MOVE_LENGTH)) > 0) {
            return concat(round, (int) ((byteSize >> KB_MOVE_LENGTH) & BIT_UPPER_MASK), "MB");
        } else if ((round = (int) (byteSize >> KB_MOVE_LENGTH)) > 0) {
            return concat(round, (int) ((byteSize >> B_MOVE_LENGTH) & BIT_UPPER_MASK), "KB");
        } else {
            return concat((int) (byteSize & BIT_UPPER_MASK), 0, "B");
        }
    }

    /**
     * 格式化byte速率
     *
     * @param byteSize byte字节长度
     * @param bitFmt   是否以bit单位格式化
     * @return 易于理解的格式化字符串
     */
    public static String formatByteRate(long byteSize, boolean bitFmt) {
        if (bitFmt) {
            byteSize = byteSize << 3;
        }
        int round;
        if ((round = (int) (byteSize >> EB_MOVE_LENGTH)) > 0) {
            return concat(round, (int) ((byteSize >> PB_MOVE_LENGTH) & BIT_UPPER_MASK), bitFmt ? "Ebps" : "EBps");
        } else if ((round = (int) (byteSize >> PB_MOVE_LENGTH)) > 0) {
            return concat(round, (int) ((byteSize >> TB_MOVE_LENGTH) & BIT_UPPER_MASK), bitFmt ? "Pbps" : "PBps");
        } else if ((round = (int) (byteSize >> TB_MOVE_LENGTH)) > 0) {
            return concat(round, (int) ((byteSize >> GB_MOVE_LENGTH) & BIT_UPPER_MASK), bitFmt ? "Tbps" : "TBps");
        } else if ((round = (int) (byteSize >> GB_MOVE_LENGTH)) > 0) {
            return concat(round, (int) ((byteSize >> MB_MOVE_LENGTH) & BIT_UPPER_MASK), bitFmt ? "Gbps" : "GBps");
        } else if ((round = (int) (byteSize >> MB_MOVE_LENGTH)) > 0) {
            return concat(round, (int) ((byteSize >> KB_MOVE_LENGTH) & BIT_UPPER_MASK), bitFmt ? "Mbps" : "MBps");
        } else if ((round = (int) (byteSize >> KB_MOVE_LENGTH)) > 0) {
            return concat(round, (int) ((byteSize >> B_MOVE_LENGTH) & BIT_UPPER_MASK), bitFmt ? "Kbps" : "KBps");
        } else {
            return concat((int) (byteSize & BIT_UPPER_MASK), 0, bitFmt ? "bps" : "Bps");
        }
    }

    /**
     * 拼接字符串
     *
     * @param round   整数部分
     * @param decimal 小数部分
     * @param unit    单位
     * @return
     */
    private static String concat(int round, int decimal, String unit) {
        if (decimal < 5) {
            //小数部分小于0.005时直接舍去
            return round + unit;
        } else {
            return new BigDecimal(round).add(new BigDecimal(decimal).divide(BIT_UPPER, 2, RoundingMode.HALF_UP)) + unit;
        }
    }

    // 替换\r\n
    public static String replaceTabAndEnterKey(String str) {
        final String newStr = str.replaceAll("(\r\n|\n\r|\r|\n)", "");
        return newStr;
    }

    /**
     * 随机生成一个长度的字段串
     *
     * @param length
     * @return
     */
    public static String randomAlphabet(int length) {
        return RandomUtil.randomString(length);
    }

}