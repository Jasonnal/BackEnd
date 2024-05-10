package com.bishe.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.util.StringUtils;

public class PasswordUtil {
    //加密
    public static String encrypt(String password) { //加密
        String salt = IdUtil.simpleUUID();
        String finalPassword = SecureUtil.md5(salt + password);
        return salt + '$' + finalPassword;
    }

    public static boolean decrypt(String password, String securePassword) { //解密
        boolean result = false;
        if (StringUtils.hasLength(password) && StringUtils.hasLength(securePassword)) {
            if (securePassword.length() == 65 && securePassword.contains("$")) {
                String[] securePasswordArr = securePassword.split("\\$");
                // 盐值
                String slat = securePasswordArr[0];
                String finalPassword = securePasswordArr[1];
                // 使用同样的加密算法和随机盐值生成最终加密的密码
                password = SecureUtil.md5(slat + password);
                if (finalPassword.equals(password)) {
                    result = true;
                }
            }
        }
        return result;
    }

}

