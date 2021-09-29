package com.flyfish.common.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtil {

    private static final String DIR_PATH = "/com.flyfish";

    private static String getRootPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + DIR_PATH;
    }

    private static String getRealPath(String path) {
        if (path == null) {
            return getRootPath();
        }
        if (path.startsWith("/")) {
            return getRootPath() + path;
        }
        return getRootPath() + "/" + path;
    }

    private static boolean createDir(File file) {
        if (!file.exists()) {
            return file.mkdirs();
        }
        return false;
    }

    public static boolean createDir(String path) {
        String realPath = getRealPath(path);
        File file = new File(realPath);
        return createDir(file);
    }

    public static boolean createFile(String fileName) {
        File rootDir = new File(getRootPath());
        if (!rootDir.exists()) {
            if (!rootDir.mkdirs()) {
                return false;
            }
        }
        if (fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }
        int index = fileName.lastIndexOf("/");
        if (index != -1) {
            String dir = fileName.substring(0, index);
            if (!createDir(dir)) {
                return false;
            }
        }
        String realPath = getRealPath(fileName);
        File file = new File(realPath);
        return createFile(file);
    }

    private static boolean createFile(File file) {
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static File getFile(String path) {
        String realPath = getRealPath(path);
        File file =  new File(realPath);
        if (!file.exists()) {
            return null;
        }
        return file;
    }

    public static String readFromFile(String filePath) throws IOException{
        String realPath = getRealPath(filePath);
        File file = new File(realPath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static boolean writeToFile(String value, String filePath) {
        String realPath = getRealPath(filePath);
        File file = new File(realPath);
        if (!file.exists()) {
            if (!createFile(filePath)) {
                return false;
            }
        }
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            fos = new FileOutputStream(realPath);
            osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(value);
            osw.flush();
        } catch(Exception e) {
            return false;
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    return false;
                }
            } else if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }
}
