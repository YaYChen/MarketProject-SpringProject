package com.springboot.project.wxpay;

import com.github.wxpay.sdk.WXPayConfig;
import com.springboot.project.entity.WxPay;
import com.springboot.project.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class WXConfigUtil implements WXPayConfig {

    private byte[] certData;

    private WxPayService service;
    private WxPay wxPay;

    @Autowired
    public WXConfigUtil(WxPayService service) {
        this.service = service;
    }

    public void initialWxConfig(int user_id) throws Exception{
        this.wxPay = this.service.getWxPayByUserID(user_id);
        this.loadCertFile(this.wxPay.getCertFile());
    }

    private void loadCertFile(String path) throws Exception {
        File file = new File(path);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    @Override
    public String getAppID() {
        return this.wxPay.getAppId();
    }

    //parnerid，商户号
    @Override
    public String getMchID() {
        return this.wxPay.getMchId();
    }

    @Override
    public String getKey() {
        return this.wxPay.getKey();
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}

