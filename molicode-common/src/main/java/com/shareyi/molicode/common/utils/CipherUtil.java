package com.shareyi.molicode.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.tuple.Pair;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * rsa加解密
 * https://blog.csdn.net/qy20115549/article/details/83105736
 *
 * @author david
 * @date 2019/7/3
 */
public class CipherUtil {

    public static final String CHARSET_NAME = "UTF-8";

    public static RSAPublicKey loadPublicKey(String publicKey) throws Exception {
        try {
            byte[] buffer = Base64.decodeBase64(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }


    public static RSAPrivateKey loadPrivateKey(String privateKeyStr)
            throws Exception {
        try {
            byte[] buffer = Base64.decodeBase64(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(data.getBytes(CHARSET_NAME)));
        return outStr;
    }


    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(data.getBytes(CHARSET_NAME));
        String outStr = new String(cipher.doFinal(inputByte), CHARSET_NAME);
        return outStr;
    }


    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static Pair<String, String> genKeyPair(int size) throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为512-1024位
        keyPairGen.initialize(size, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        return Pair.of(publicKeyString, privateKeyString);
    }


    public static void main(String[] args) throws Exception {

        Pair<String, String> pair = genKeyPair(512);
        System.out.println("publicKey:\n" + pair.getLeft());
        System.out.println("privateKey:\n" + pair.getRight());


        RSAPublicKey rsaPublicKey = loadPublicKey(pair.getLeft());

        String en = encryptByPublicKey("a123456a", rsaPublicKey);
        System.out.println("加密值：" + en);

        RSAPrivateKey rsaPrivateKey = loadPrivateKey(pair.getRight());
        String origin = decryptByPrivateKey(en, rsaPrivateKey);
        System.out.println("解密值：" + origin);
    }

}
