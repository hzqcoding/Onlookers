package com.example.hzq.onlookers.Login;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    //md5 加密算法
    public static String md5(String text) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuffer sb = new StringBuffer();//对象实例化
            // for 循环数组byte[] result;
            for (byte b : result){
                int number = b & 0xff;// 0xff 为16进制
                String hex = Integer.toHexString(number);// number值 转换 字符串
                if (hex.length() == 1){
                    sb.append("0"+hex);
                }else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";//发送异常return空字符串
        }
    }
}