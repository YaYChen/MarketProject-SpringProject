package com.springboot.project.wxpay;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.springboot.project.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WxServiceImpl implements WxService {

    @Value("${project.wxpay.spbill_create_ip}")
    public String SPBILL_CREATE_IP;//服务器ip地址
    @Value("${project.wxpay.notify_url}")
    public String NOTIFY_URL;//回调接口地址
    @Value("${project.wxpay.trade_type_app}")
    public String TRADE_TYPE_APP;//APP

    private WXConfigUtil wxConfigUtil;

    @Autowired
    public WxServiceImpl(WXConfigUtil wxConfigUtil){
        this.wxConfigUtil = wxConfigUtil;
    }

    @Override
    public Map<String,Object> payBack(Order order, int userId) throws Exception {
        this.wxConfigUtil.initialWxConfig(userId);
        WXPay wxpay = new WXPay(this.wxConfigUtil);
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String, String> notifyMap = new HashMap<String, String>();
        try {
            notifyMap.put("orderSerial", order.getSerial());
            notifyMap.put("price", order.getTotalPrice());
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {//验证签名是否有效，有效则进一步处理

                String return_code = notifyMap.get("return_code");//状态
                String out_trade_no = notifyMap.get("out_trade_no");//商户订单号
                if (return_code.equals("SUCCESS")) {
                    if (out_trade_no != null) {
                        //注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户的订单状态从退款改成支付成功
                        //注意特殊情况：微信服务端同样的通知可能会多次发送给商户系统，所以数据持久化之前需要检查是否已经处理过了，处理了直接返回成功标志
                        //业务数据持久化
                        result.put("return_code", "SUCCESS");
                        result.put("trade_no", out_trade_no);
                    } else {
                        result.put("return_code", "FAIL");
                        result.put("return_msg", "报文为空");
                    }
                }
                return result;
            } else {
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
                //失败的数据要不要存储？
                result.put("return_code", "FAIL");
                result.put("return_msg", "手机支付回调通知签名错误");
                return result;
            }
        } catch (Exception e) {
            result.put("return_code", "FAIL");
            result.put("return_msg", "手机支付回调通知失败,Error: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Map doUnifiedOrder(int userId) throws Exception {
        try {
            this.wxConfigUtil.initialWxConfig(userId);
            WXPay wxpay = new WXPay(this.wxConfigUtil);
            Map<String, String> data = new HashMap<>();
            //生成商户订单号，不可重复
            data.put("appid", this.wxConfigUtil.getAppID());
            data.put("mch_id", this.wxConfigUtil.getMchID());
            data.put("nonce_str", WXPayUtil.generateNonceStr());
            String body = "订单支付";
            data.put("body", body);
            data.put("out_trade_no", "订单号");
            data.put("total_fee", "1");
            //自己的服务器IP地址
            data.put("spbill_create_ip", SPBILL_CREATE_IP);
            //异步通知地址（请注意必须是外网）
            data.put("notify_url", NOTIFY_URL);
            //交易类型
            data.put("trade_type", TRADE_TYPE_APP);
            //附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
            data.put("attach", "");
            data.put("sign", WXPayUtil.generateSignature(data, this.wxConfigUtil.getKey(),
                    WXPayConstants.SignType.MD5));
            //使用官方API请求预付订单
            Map<String, String> response = wxpay.unifiedOrder(data);
            if ("SUCCESS".equals(response.get("return_code"))) {//主要返回以下5个参数
                Map<String, String> param = new HashMap<>();
                param.put("appid", this.wxConfigUtil.getAppID());
                param.put("partnerid",response.get("mch_id"));
                param.put("prepayid",response.get("prepay_id"));
                param.put("package","Sign=WXPay");
                param.put("noncestr",WXPayUtil.generateNonceStr());
                param.put("timestamp",System.currentTimeMillis()/1000+"");
                param.put("sign",WXPayUtil.generateSignature(param, this.wxConfigUtil.getKey(),
                        WXPayConstants.SignType.MD5));
                return param;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("下单失败");
        }
        throw new Exception("下单失败");
    }
}
