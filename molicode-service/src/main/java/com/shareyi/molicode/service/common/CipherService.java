package com.shareyi.molicode.service.common;

/**
 * 加解密服务
 *
 * @author david
 * @date 2019/7/3
 */
public interface CipherService {

    /**
     * 通过rsa进行加密
     *
     * @param str
     * @return
     */
    String encryptByRSA(String str) throws Exception;

    /**
     * 通过rsa进行解密
     *
     * @param str
     * @return
     */
    String decryptByRSA(String str) throws Exception;
}
