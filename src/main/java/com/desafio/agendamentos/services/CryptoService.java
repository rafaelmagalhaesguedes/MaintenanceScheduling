package com.desafio.agendamentos.services;

import org.jasypt.util.text.StrongTextEncryptor;

/**
 * Crypto Service
 * *
 * Classe para Encriptação e Decriptação  dos dados.
 * Nesta classe utilizamos a biblioteca JASYPT para
 * simplificar o processo.
 **/
public class CryptoService {
    private static final StrongTextEncryptor encryptor;
    private static final String SECRET_KEY = "mySecretKeyToEncrypt";

    // Bloco estático para inicializar o encryptor com a chave secreta
    static {
        encryptor = new StrongTextEncryptor();
        encryptor.setPassword(SECRET_KEY);
    }

    /**
     * Método para criptografar um texto bruto.
     * @param rawText Texto a ser criptografado.
     * @return Texto criptografado.
     */
    public static String encrypt(String rawText) {
        return encryptor.encrypt(rawText);
    }

    /**
     * Método para descriptografar um texto criptografado.
     * @param encryptedText Texto criptografado a ser descriptografado.
     * @return Texto descriptografado.
     */
    public static String decrypt(String encryptedText) {
        return encryptor.decrypt(encryptedText);
    }
}