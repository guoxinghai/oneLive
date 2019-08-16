package com.onlive.util;
import android.util.Log;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
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
    private static String accessKeyId = "LTAIxhDLF2lbQuTB";
    private static String accessSecret = "h37L0t0gAP3vd7ehiwNVt9bJ3Da3TA";
    public static CommonRequest getVerityCodeRequest(String phone, String code){//n为后去验证码的个数
        CommonRequest request = new CommonRequest();
        String json_code = "{'code':'"+code+"'}";
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.setConnectTimeout(3000);
        //request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("TemplateCode", TemplateCode);
        request.putQueryParameter("TemplateParam",json_code);
        return request;

    }
    public static IAcsClient getClient(){
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessSecret);
        return new DefaultAcsClient(profile);
    }
    public static String getVerifyCode(int n){
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i=0;i<n;i++){
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
