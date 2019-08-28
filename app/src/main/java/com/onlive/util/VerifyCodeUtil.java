package com.onlive.util;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.onlive.R;

import java.util.Random;

/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.0.3</version>
</dependency>
*/
public class VerifyCodeUtil {
    private static String SignName = "万籁APP";
    private static String TemplateCode = "SMS_172525256";
    private static String accessKeyId = ValueUtil.getValue(R.string.accessKeyId);
    private static String accessSecret = ValueUtil.getValue(R.string.accessSecret);
    //获取发送验证码的request
    public static CommonRequest getVerityCodeRequest(String phone, String code){//n为后去验证码的个数
        CommonRequest request = new CommonRequest();
        String json_code = "{'code':'"+code+"'}";
//        String json_code = "9999";
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.setConnectTimeout(5000);
        //request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("TemplateCode", TemplateCode);
        request.putQueryParameter("TemplateParam",json_code);
        return request;

    }
    //获取client
    public static IAcsClient getClient(){
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessSecret);
        return new DefaultAcsClient(profile);
    }
    //获取指定位数的随机验证码
    public static String getVerifyCode(int n){
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i=0;i<n;i++){
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
    //获取查询某个手机号当日发送验证码次数的request
    public static CommonRequest queryVerifyCodeSentTimeRequest(String phone){

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setAction("QuerySendDetails");
        request.setVersion("2017-05-25");
        request.setConnectTimeout(5000);
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumber", phone);
        request.putQueryParameter("SendDate", TimeUtil.getCurrentDate());
        request.putQueryParameter("PageSize", "10");
        request.putQueryParameter("CurrentPage", "1");
        return  request;
    }
}
