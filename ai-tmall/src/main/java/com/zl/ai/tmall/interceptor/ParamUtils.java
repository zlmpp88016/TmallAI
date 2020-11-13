package com.zl.ai.tmall.interceptor;

import com.alibaba.da.coin.ide.spi.security.RSAUtil;
import com.alibaba.da.coin.ide.spi.standard.SecurityWrapperTaskQuery;
import com.alibaba.da.coin.ide.spi.standard.TaskQuery;
import com.alibaba.da.coin.ide.spi.trans.MetaFormat;
import com.alibaba.fastjson.JSONObject;
import com.zl.ai.tmall.config.RSAConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author:lei.zhu
 * @date:2020/11/13 11:02
 */
@Slf4j
public class ParamUtils {
    public static String decriptForTmall(InputStream inputStream){

        if(inputStream!=null) {
            String taskQuery="";
            try {
                StringBuffer sb = new StringBuffer();
                InputStreamReader reader = new InputStreamReader(inputStream);
                char[] chars = new char[1024];
                int len = 0;

                while ((len = reader.read(chars)) > 0) {
                    sb.append(chars, 0, len);
                }
                SecurityWrapperTaskQuery wrapperTaskQuery = MetaFormat
                        .parseToWrapperQuery(sb.toString());
                //然后利用RSA私钥解密语义理解对象，解密后为JSON格式的字符串
                taskQuery = RSAUtil.decryptByPrivateKey(wrapperTaskQuery.getSecurityQuery(),
                        RSAConfig.TMALL_PRIVATE_KEY);

                log.info(taskQuery);

            }catch (Exception e){
                e.printStackTrace();
            }
            return taskQuery;
        }else{
            return "";
        }
    }
}
