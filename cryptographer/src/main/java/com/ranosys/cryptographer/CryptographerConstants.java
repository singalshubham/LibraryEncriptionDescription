package com.ranosys.cryptographer;

/**
 * Created by shubham on 17/2/17.
 */

public class CryptographerConstants {

    public class AESxForm {
        //for AES/ECB/NoPadding input string must be multiple of block size (16 byte)
        public static final String AES_ECB_NOPADDING = "AES/ECB/NoPadding";
        public static final String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";
        ////for AES/CBC/NoPadding input string must be multiple of block size (16 byte)
        public static final String AES_CBC_NOPADDING = "AES/CBC/NoPadding";
        public static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
        //For AES/GCM/NoPadding kitkat version is required and it provide both integrity and confidentiality
        public static final String AES_GCM_NOPADING = "AES/GCM/NoPadding";

    }

    public class RSAxForm {
        public static final String RSA_NONE_PKCS1PADDING = "RSA/NONE/PKCS1PADDING";
        public static final String RSA_ECB_PKCS1PADDING = "RSA/ECB/PKCS1PADDING";
        public static final String RSA_ECB_OAEPWithSHA_1AndMGF1Padding = " RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
        public static final String RSA_ECB_OAEPWithSHA_256AndMGF1Padding = "  RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    }
}







