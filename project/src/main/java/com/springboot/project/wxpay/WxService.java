package com.springboot.project.wxpay;

import java.util.Map;

public interface WxService {
    String payBack(String resXml);

    Map doUnifiedOrder() throws Exception;
}
