package com.ranosys.cryptographer;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Ranosys Technologies
 * It is class for AES and RSA encryption decryption
 * AES algo can use use for big data of string while RSA algo is use for short length of String
 * for AES in GCM mode kitkat version is required
 */


public class Cryptographer {

    //this is forth parameter specification use for CBC form in AES for Randomness
    private static IvParameterSpec ips = generateIV();
    //this is GCMParameterSpec use for AES in GCM mode
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static GCMParameterSpec gcmParamSpec = generateGCMParam();


    /**
     * for AES in GCM mode kitkat version is required and GCM mode also provide integrity
     * if AES/ECB/NoPadding or AES/CBC/NoPadding xform is used then input string must be multiple of block size (16 byte)
     *
     * @param inputString is String we want to encrypt
     * @param key         is symmetric key use for encryption (128,192,256)
     * @param xForm       is the transformation form in which form we want to transform
     *                    (AES/ECB/PKCS5Padding,AES/ECB/NoPadding,AES/CBC/PKCS5Padding,AES/CBC/NoPadding,AES/GCM/NoPadding)
     * @return it reurn encrypted string
     * @throws Exception NOSuchAlgorithmEXception,NoSuchPaddingEXception
     */
    private static String doEncryptAES(String inputString,
                                       SecretKey key, String xForm) throws Exception {
        byte inpBytes[] = inputString.getBytes("utf-8");
        Cipher cipher = Cipher.getInstance(xForm);
        switch (xForm) {
            case "AES/ECB/PKCS5Padding":
            case "AES/ECB/NoPadding":
                cipher.init(Cipher.ENCRYPT_MODE, key);
                break;
            case "AES/CBC/PKCS5Padding":
            case "AES/CBC/NoPadding":
                cipher.init(Cipher.ENCRYPT_MODE, key, ips);
                break;
            case "AES/GCM/NoPadding":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    cipher.init(Cipher.ENCRYPT_MODE, key, gcmParamSpec);
                } else {
                    throw new Exception("InvalidApiException: for AES GCM require min 19 sdk");
                }

                break;
        }
        return Base64.encodeToString(cipher.doFinal(inpBytes), Base64.NO_WRAP);
    }

    /**
     * for AES in GCM mode kitkat version is required
     *
     * @param inputString is String we want to decrypt
     * @param key         is symmetric key use for decryption and it similar to key used for encryption (128,192,256)
     * @param xForm       is the transformation form in which form we want to transform
     *                    (AES/ECB/PKCS5Padding,AES/ECB/NoPadding,AES/CBC/PKCS5Padding,AES/CBC/NoPadding,AES/GCM/NoPadding)
     * @return it reurn decrypted string
     * @throws Exception NOSuchAlgorithmEXception,NoSuchPaddingEXception
     */
    private static String doDecryptAES(String inputString,
                                       SecretKey key, String xForm) throws Exception {
        byte[] inpBytes = Base64.decode(inputString, Base64.NO_WRAP);
        Cipher cipher = Cipher.getInstance(xForm);
        switch (xForm) {
            case "AES/ECB/PKCS5Padding":
            case "AES/ECB/NoPadding":
                cipher.init(Cipher.DECRYPT_MODE, key);
                break;
            case "AES/CBC/PKCS5Padding":
            case "AES/CBC/NoPadding":
                cipher.init(Cipher.DECRYPT_MODE, key, ips);
                break;
            case "AES/GCM/NoPadding":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    cipher.init(Cipher.DECRYPT_MODE, key, gcmParamSpec);
                } else {
                    throw new Exception("InvalidApiException: for AES GCM require min 19 sdk");
                }

                break;
        }
        return new String(cipher.doFinal(inpBytes), "utf-8");
    }


    /**
     * @return it return the IVparameter specification used for AES/CBC/* form
     */
    private static IvParameterSpec generateIV() {
        SecureRandom r = new SecureRandom();
        byte[] newSeed = r.generateSeed(16);
        r.setSeed(newSeed);
        byte[] byteIV = new byte[16];
        r.nextBytes(byteIV);
        return new IvParameterSpec(byteIV);
    }

    /**
     * it is the method for generating GCmParamSpecification for AES in GCM mode
     *
     * @return it return GCMParameterSpec use in AES GCm mode
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static GCMParameterSpec generateGCMParam() {
        SecureRandom random = new SecureRandom();
        random.generateSeed(12);
        final byte[] nonce = new byte[12];
        random.nextBytes(nonce);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new GCMParameterSpec(128, nonce);
        } else
            return null;

    }


    /**
     * RSA algo use for short length of data
     * for RSA (input String +padding) must be less than to bit of key
     *
     * @param inputString is String we want to encrypt using RSA ago
     * @param key         is public key use for encryption (512,1024,2048)
     * @param xForm       is the transformation form that is use for encrypt a string
     * @return it reurn encrypted string
     * @throws Exception NOSuchAlgorithmEXception,NoSuchPaddingEXception
     */
    private static String doEncryptRSA(String inputString, PublicKey key,
                                       String xForm) throws Exception {
        byte[] inpBytes = inputString.getBytes("utf-8");
        Cipher cipher = Cipher.getInstance(xForm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeToString(cipher.doFinal(inpBytes), Base64.NO_WRAP);
    }


    /**
     * @param inputString is String we want to decrypt
     * @param key         is private key use for decryption and it is generated from public private pair (512,1024,2048)
     * @param xForm       is the transformation form same as use for encryption
     * @return it reurn decrypted string
     * @throws Exception NOSuchAlgorithmEXception,NoSuchPaddingEXception
     */
    private static String doDecryptRSA(String inputString, PrivateKey key,
                                       String xForm) throws Exception {
        Cipher cipher = Cipher.getInstance(xForm);
        byte[] inpBytes = Base64.decode(inputString, Base64.NO_WRAP);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(inpBytes), "utf-8");
    }


    /**
     * for AES in GCM mode kitkat version is required and GCM mode also provide integrity
     * if AES/ECB/NoPadding or AES/CBC/NoPadding xform is used then input string must be multiple of block size (16 byte)
     * @param inputString is the String we want to encrypt using AES Algo
     * @param key         is the common key that is use for encryption and decryption
     * @param xForm       is the transformation form that is use for encryption a string
     *                    (AES/ECB/PKCS5Padding,AES/ECB/NoPadding,AES/CBC/PKCS5Padding,AES/CBC/NoPadding,AES/GCM/NoPadding)
     * @return it return encrypted String
     * @throws Exception NOSuchAlgorithmEXception,NoSuchPaddingEXception
     */
    public static String encryptAES(String inputString, SecretKey key, String xForm) throws Exception {
        return doEncryptAES(inputString, key, xForm);
    }

    /**
     * for AES in GCM mode kitkat version is required and GCM mode also provide integrity
     * if AES/ECB/NoPadding or AES/CBC/NoPadding xform is used then input string must be multiple of block size (16 byte)
     * @param inputString is String we want to decrypt using AES algo
     * @param key         is symmetric key use for decryption and it similar to key used for encryption (128,192,256)
     * @param xForm       is the transformation form same as use for encryption
     * @return it reurn decrypted string
     * @throws Exception NOSuchAlgorithmEXception,NoSuchPaddingEXception
     */
    public static String decryptAES(String inputString, SecretKey key, String xForm) throws Exception {
        return doDecryptAES(inputString, key, xForm);
    }

    /**
     * RSA algo use for short length of data
     * for RSA (input String +padding) must be less than to bit of key
     *
     * @param inputString is String we want to encrypt using RSA algo
     * @param key         is public key use for encryption (512,1024,2048)
     * @param xForm       is the transformation form that is use for encryption a string
     *                    (RSA/NONE/PKCS1PADDING), (RSA/ECB/PKCS1PADDING), (RSA/ECB/OAEPWithSHA-1AndMGF1Padding), (RSA/ECB/OAEPWithSHA-256AndMGF1Padding)
     * @return it return encrypted string
     * @throws Exception NOSuchAlgorithmEXception,NoSuchPaddingEXception
     */
    public static String encryptRSA(String inputString, PublicKey key, String xForm) throws Exception {
        return doEncryptRSA(inputString, key, xForm);
    }

    /**
     * @param inputString is String we want to decrypt using RSA algo
     * @param key         is private key use for decryption and it is generated from public private pair (512,1024,2048)
     * @param xForm       is the transformation form same as use for encryption
     *                    (RSA/NONE/PKCS1PADDING), (RSA/ECB/PKCS1PADDING), (RSA/ECB/OAEPWithSHA-1AndMGF1Padding), (RSA/ECB/OAEPWithSHA-256AndMGF1Padding)
     * @return it reurn decrypted string
     * @throws Exception NOSuchAlgorithmEXception,NoSuchPaddingEXception
     */
    public static String decryptRSA(String inputString, PrivateKey key, String xForm) throws Exception {
        return doDecryptRSA(inputString, key, xForm);
    }

}
