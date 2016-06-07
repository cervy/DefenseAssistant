package com.szzaq.jointdefence.utils;

import java.io.IOException;
import java.io.InputStream;

public class StringUtils {
    public static String inputStream2String(InputStream in) throws IOException {
        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        if (in != null) {
            for (int n; (n = in.read(b)) != -1; ) {
                out.append(new String(b, 0, n));

            }
        }
        return out.toString();
    }

    public static String arrayToString(String[] arr) {
        String ret = "";
        for (String s : arr) {
            ret += s + ",";
        }

        return ret.length() == 0 ? ret : ret.substring(0, ret.length() - 1);

    }
}
