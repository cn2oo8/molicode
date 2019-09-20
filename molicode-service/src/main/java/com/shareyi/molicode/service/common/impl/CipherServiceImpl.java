package com.shareyi.molicode.service.common.impl;

import com.shareyi.molicode.common.utils.CipherUtil;
import com.shareyi.molicode.service.common.CipherService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 加解密服务
 *
 * @author david
 * @date 2019/7/3
 */
@Service
public class CipherServiceImpl implements CipherService, InitializingBean {
    /**
     * 公钥
     */
    private RSAPublicKey publicKey;
    /**
     * 私钥
     */
    private RSAPrivateKey privateKey;

    @Value("${rsa.public.key}")
    private String rsaPubicKeyStr;
    @Value("${rsa.private.key}")
    private String rsaPrivateKeyStr;

    @Override
    public String encryptByRSA(String str) throws Exception {
        return CipherUtil.encryptByPublicKey(str, publicKey);
    }

    @Override
    public String decryptByRSA(String str) throws Exception {
        return CipherUtil.decryptByPrivateKey(str, privateKey);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        publicKey = CipherUtil.loadPublicKey(rsaPubicKeyStr);
        privateKey = CipherUtil.loadPrivateKey(rsaPrivateKeyStr);
    }
}
