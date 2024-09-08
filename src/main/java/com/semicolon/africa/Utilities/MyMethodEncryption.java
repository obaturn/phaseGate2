package com.semicolon.africa.Utilities;

public class MyMethodEncryption {
    private  static  final int SHIFT = 10;
    public  static   String encrypt(String password) {
        StringBuilder encryptedPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            char shifted = (char) (ch + SHIFT);
            encryptedPassword.append(shifted);
        }
        return encryptedPassword.toString();
    }
    public  static String decrypt(String password) {
        StringBuilder decryptedPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            char shifted = (char) (ch - SHIFT);
            decryptedPassword.append(shifted);
        }
        return decryptedPassword.toString();
    }

}
