package com.desafio.agendamentos.services;

import org.jasypt.util.text.StrongTextEncryptor;

/**
 * Using the JASYPT(Java Simplified Encryption)
 * library for encryption operations
 */
public class CryptoService {
    private static final StrongTextEncryptor encryptor;
    private static final String SECRET_KEY = "mySecretKeyToEncrypt";

    static {
        encryptor = new StrongTextEncryptor();
        encryptor.setPassword(SECRET_KEY);
    }

    public static String encrypt(String rawText) {
        return encryptor.encrypt(rawText);
    }

    public static String decrypt(String encryptedText) {
        return encryptor.decrypt(encryptedText);
    }
}