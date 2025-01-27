package com.github.JuanManuel.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * HashPass class is used to hash a password using the SHA-256 algorithm.
 */
public class HashPass {

    /**
     * Hashes a password using the SHA-256 algorithm.
     *
     * @param password the password to hash.
     * @return the hashed password.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}