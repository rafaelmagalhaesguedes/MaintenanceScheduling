package com.desafio.agendamentos.services;

import org.jasypt.util.text.StrongTextEncryptor;

/**
 * Serviço para criptografar e descriptografar textos.
 * Para facilitar o processo utilizamos a biblioteca JASYPT.
 */
public class CryptoService {
    private static final StrongTextEncryptor encryptor;
    private static final String SECRET_KEY = "mySecretKeyToEncrypt";

    static {
        encryptor = new StrongTextEncryptor();
        encryptor.setPassword(SECRET_KEY);
    }

    /**
     * Criptografa um texto em formato original.
     *
     * @param rawText Texto em formato original a ser criptografado.
     * @return Texto criptografado.
     */
    public static String encrypt(String rawText) {
        return encryptor.encrypt(rawText);
    }

    /**
     * Descriptografa um texto criptografado.
     *
     * @param encryptedText Texto criptografado a ser descriptografado.
     * @return Texto em formato original.
     */
    public static String decrypt(String encryptedText) {
        return encryptor.decrypt(encryptedText);
    }
}