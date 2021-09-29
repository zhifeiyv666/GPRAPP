package com.flyfish.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.flyfish.bean.Password;
import com.flyfish.common.utils.FileUtil;
import com.flyfish.common.utils.JsonUtil;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GlobalApp {

    private static final String DATA_FILE = "data.json";

    private static GlobalApp instance = new GlobalApp();
    private boolean hasPermission = false;

    private Map<String, List<Password>> passwords = null;

    public static GlobalApp getInstance() {
        return instance;
    }

    private GlobalApp() {
        // load passwords;
    }

    public List<Password> searchPasswords(String key) {
        if (key != null) {
            return passwords.get(key);
        }
        return null;
    }

    public boolean isHasPermission() {
        return hasPermission;
    }

    public boolean init(boolean hasPermission) {
        this.hasPermission = hasPermission;
        return loadPasswords();
    }

    public boolean updatePassword(boolean hasPermission) {
        this.hasPermission = hasPermission;
        return writePasswords();
    }

    public boolean updatePassword() {
        return writePasswords();
    }

    public boolean addPassword(String appName, Password password) {
        if (appName != null && appName.length() > 0 && password != null) {
            if (passwords == null) {
                passwords = new HashMap<>();
            }
            if (passwords.containsKey(appName)) {
                passwords.get(appName).add(password);
            } else {
                passwords.put(appName, Arrays.asList(password));
            }
            return writePasswords();
        }
        return false;
    }

    public Password genPassword(int passwordLength) {
        char[] digital = new char[passwordLength];
        char[] lowerLetter = new char[passwordLength];
        char[] upperLetter = new char[passwordLength];
        char[] extra = new char[passwordLength];
        long timestamp = System.currentTimeMillis();
        Random random = new Random(timestamp);
        int i = 0;
        while ( i < passwordLength) {
            int x = random.nextInt(10);
            char c = (char)('0' + x);
            digital[i++] = c;
        }
        i = 0;
        while ( i < passwordLength) {
            int x = random.nextInt(26);
            char c = (char) ('a' + x);
            lowerLetter[i++] = c;
        }
        i = 0;
        while (i < passwordLength) {
            int x = random.nextInt(26);
            char c = (char) ('A' + x);
            upperLetter[i++] = c;
        }
        i = 0;
        while (i < passwordLength) {
            int x = random.nextInt(94);
            char c = (char) (33 + x);
            if ((c >= '0' && c <= '9') ||
                    (c >= 'a' && c <= 'z') ||
                    (c >= 'A' && c <= 'Z')
            ) {
                continue;
            }
            extra[i++] = c;
        }
        char[] result = new char[passwordLength];
        int a = 0, b = 0, c = 0, d = 0;
        for (i = 0; i < passwordLength; i ++) {
            int x = random.nextInt(4);
            switch (x) {
                case 0:
                    result[i] = digital[i];
                    a = 1;
                    break;
                case 1:
                    result[i] = lowerLetter[i];
                    b = 1;
                    break;
                case 2:
                    result[i] = upperLetter[i];
                    c = 1;
                    break;
                case 3 :
                    result[i] = extra[i];
                    d = 1;
                    break;
            }
        }
        if (passwordLength >= 4 && a * b * c * d == 0) {
            result[0] = digital[0];
            result[1] = lowerLetter[1];
            result[2] = upperLetter[2];
            result[3] = extra[3];
        }
        String passwordValue = String.valueOf(result);
        return new Password(passwordValue, timestamp);
    }

    private boolean writePasswords() {
        if (hasPermission) {
            String json = JsonUtil.getInstance().serialozeToString(passwords);
            return FileUtil.writeToFile(json, DATA_FILE);
        }
        return false;
    }

    private boolean loadPasswords() {
        if (hasPermission) {
            try {
                String json = FileUtil.readFromFile(DATA_FILE);
                TypeReference<Map<String, List<Password>>> typeReference = new TypeReference<Map<String, List<Password>>>() {};
                passwords = (Map<String, List<Password>>) JsonUtil.getInstance().deserialize(typeReference, json);
                return true;
            } catch (FileNotFoundException e) {
                boolean r = FileUtil.createDir(null);
                passwords = new HashMap<>();
                return false;
            } catch (Exception e) {
                passwords = new HashMap<>();
                return false;
            }
        }
        return false;
    }
}
