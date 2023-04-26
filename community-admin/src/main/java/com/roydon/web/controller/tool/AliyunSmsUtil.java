package com.roydon.web.controller.tool;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 阿里云短信发送工具类
 *
 * @author zql
 * @createTime 2020-11-29 21:56:40
 */
public class AliyunSmsUtil {

    /**
     * 屏蔽构造函数，避免被实例化
     */
    private AliyunSmsUtil() {
    }

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(AliyunSmsUtil.class);
    /**
     * 短信发送返回信息
     */
    private static final String MESSAGE_OK = "OK";
    /**
     * 阿里云短信服务提供的标识用户
     */
    private final static String ACCESS_KEY_ID = "LTAI5tSpJYrm2fawRxgfWncf";
    /**
     * 阿里云短信服务提供的密钥，用来验证用户的密钥。AccessKeySecret必须保密。
     */
    private final static String ACCESS_SECRET = "Plo6TQpTC6YzThyI1QHcu53EtKvroX";

    /**
     * 短信发送方法
     *
     * @param mobile 手机号
     * @param type   短信模板类型  1身份验证，2登录确认，3登录异常，4用户注册，5修改密码，6信息变更
     * @param code   验证码
     * @return
     * @author zql
     * @createTime 2020-11-29 21:57:08
     */
    public static String sendSms(String mobile, int type, String code) {
        if (!isMobile(mobile)) {
            logger.error("短信发送失败，手机号码不正确！");
            return null;
        }
        DefaultProfile profile = DefaultProfile.getProfile("default", AliyunSmsUtil.ACCESS_KEY_ID, AliyunSmsUtil.ACCESS_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        String signName = "郭意诚的社区后台";
        // 阿里云短信服务提供的模板代码，此代码是由自己创建的模板得到的
        String templateCode = TemplateEnum.getTemplate(type);
        if (Objects.isNull(templateCode)) {
            return null;
        }
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        // 要发送到的手机号
        request.putQueryParameter("PhoneNumbers", mobile);
        // 短信签名名称。请在控制台签名管理页面签名名称一列查看。 说明 必须是已添加、并通过审核的短信签名。 
        request.putQueryParameter("SignName", signName);
        // 短信模板ID。请在控制台模板管理页面模板CODE一列查看。 说明 必须是已添加、并通过审核的短信签名；且发送国际/港澳台消息时，请使用国际/港澳台短信模版。
        request.putQueryParameter("TemplateCode", templateCode);
        // 短信模板变量对应的实际值，JSON格式。 说明 如果JSON中需要带换行符，请参照标准的JSON协议处理。
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");

        String data = null;
        try {
            CommonResponse response = client.getCommonResponse(request);
            data = response.getData();
        } catch (ServerException e) {
            logger.error("短信发送失败：" + e.getMessage(), e);
        } catch (ClientException e) {
            logger.error("短信发送失败：" + e.getMessage(), e);
        }
        return data;
    }

    /**
     * 查询发送阿里云短信是否成功
     *
     * @param mobile     手机号
     * @param type       短信模板类型 1身份验证，2登录确认，3登录异常，4用户注册，5修改密码，6信息变更
     * @param randomCode 验证码
     * @return
     * @author zql
     * @createTime 2020-11-29 22:13:20
     */
    public static boolean isSuccessSendAliyunSms(String mobile, int type, String randomCode) {
        boolean bl = false;
        String str = sendSms(mobile, type, randomCode);
        if (Objects.isNull(str)) {
            logger.error("短信发送失败，返回信息为空");
            return false;
        }
        try {
            // 解析JSON字符串
            JSONObject jsonObject = JSONObject.fromObject(str);
            String message = jsonObject.getString("Message");
            String requestId = jsonObject.getString("RequestId");
            String bizId = "";
            // 当发送失败时不会有回执id，防止json解析报错，如返回结果{"Message":"触发分钟级流控Permits:1","RequestId":"FE686A34-6021-44A4-B034-A5F7792D7B39","Code":"isv.BUSINESS_LIMIT_CONTROL"}
            if (AliyunSmsUtil.MESSAGE_OK.equals(message)) {
                bizId = jsonObject.getString("BizId");
            }
            String backCode = jsonObject.getString("Code");
            boolean condition = false;
            // 如果需要添加回传的短信消息到数据库，则把isSuccessSendAliyunSms方法独立出来
            if (condition) {
                logger.error("添加短信回传数据" + bizId + requestId + "失败！");
            }
            if (AliyunSmsUtil.MESSAGE_OK.equals(backCode)) {
                bl = true;
            } else {
                logger.error("短信发送失败!");
            }
        } catch (Exception e) {
            logger.error("解析JSON字符串失败!" + e.getMessage(), e);
        }
        return bl;
    }

    /**
     * 验证是否是正确的手机
     *
     * @param mobile 手机号码
     * @return
     * @author zql
     * @createTime 2020-11-29 22:13:04
     */
    private static boolean isMobile(String mobile) {
        boolean bl = false;
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        // 手机号码长度
        int length = 11;
        if (mobile.length() != length) {
            bl = false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobile);
            boolean isMatch = m.matches();
            bl = isMatch;
        }
        return bl;
    }

    /**
     * 阿里云提供的模板代码枚举
     *
     * @author zql
     * @createTime 2020-11-29 22:07:59
     * @version:1.0
     */
    enum TemplateEnum {
        /**
         * 身份验证
         */
        SMS_000000001(1),
        /**
         * 登录确认
         */
        SMS_000000002(2),
        /**
         * 登录异常
         */
        SMS_000000003(3),
        /**
         * 用户注册
         */
        SMS_000000004(4),
        /**
         * 修改密码
         */
        SMS_000000005(5),
        /**
         * 信息变更
         */
        SMS_000000006(6),
        SMS_273815229(7);

        private int type;

        private TemplateEnum(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public static String getTemplate(int type) {
            TemplateEnum[] tes = TemplateEnum.values();
            for (TemplateEnum te : tes) {
                if (te.getType() == type) {
                    return te.toString();
                }
            }
            throw new IllegalArgumentException("Invalid type code: " + type);
        }
    }

    public static void main(String[] args) {
        AliyunSmsUtil.sendSms("18203707837", 7, "776655");
    }
}
