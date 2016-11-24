package jtemp.bingossorder.utils;

import java.io.FileInputStream;

/**
 * Created by ZMS on 2016/11/24.
 */

public final class FileUtils {

    public static byte[] readBytes(String path) {
        try {
            FileInputStream in = new FileInputStream(path);
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
