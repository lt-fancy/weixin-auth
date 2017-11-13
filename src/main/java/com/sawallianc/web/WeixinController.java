package com.sawallianc.web;

import com.sawallianc.util.SHAUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.DigestException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class WeixinController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeixinController.class);
    @GetMapping("weixinAuth")
    public String weixin(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce,
                         @RequestParam String echostr){
        LOGGER.info("signature: {} timestamp: {} nonce: {} echostr: {}",signature,timestamp,nonce,echostr);
        List<String> list = new ArrayList<String>(3);
        list.add(timestamp);
        list.add(nonce);
        list.add("fingertap");
        Collections.sort(list);
        String result = null;
        try {
            result = SHAUtils.SHA1(list.get(0) + list.get(1) + list.get(2));
            if(StringUtils.equalsIgnoreCase(signature,result)){
                LOGGER.info("=================验证成功"+echostr);
                return echostr;
            }
        } catch (DigestException e) {
            LOGGER.error(e.getMessage());
        }
        return "";
    }
}
