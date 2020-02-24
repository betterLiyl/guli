package com.guli.ucenter.controller;

import com.google.gson.Gson;
import com.guli.ucenter.entity.Member;
import com.guli.ucenter.service.MemberService;
import com.guli.ucenter.util.ConstantPropertiesUtil;
import com.guli.ucenter.util.HttpClientUtils;
import com.guli.ucenter.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private MemberService memberService;

    //扫描之后，调用这个方法
    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session) {
        //得到授权临时票据code
//        System.out.println("code = " + code);
//        System.out.println("state = " + state);
        //1 获取返回临时票据 code

        //2 拿着code请求微信提供的固定的地址
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //设置参数
        baseAccessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);
        try {
            //使用httpclient请求拼接之后的路径，获取openid和access_token
            String resultAccessToken = HttpClientUtils.get(baseAccessTokenUrl);
            //从返回字符串获取两个值
            Gson gson = new Gson();
            Map<String,Object> map = gson.fromJson(resultAccessToken, HashMap.class);
            String access_token = (String)map.get("access_token");//获取数据凭证
            String openid = (String)map.get("openid");//微信id
           // System.out.println("----------------------"+openid);
            //判断用户表是否已经存在添加用户信息，如果不存在，添加
            Member member = memberService.getOpenUserInfo(openid);
            if(member == null) {//不存在
                //3 拿着access_token和openid请求固定的地址
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                baseUserInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid);
                //httpclient请求这个地址
                String resultUserInfo = HttpClientUtils.get(baseUserInfoUrl);
                Map<String,Object> userMap = gson.fromJson(resultUserInfo, HashMap.class);
                String nickname = (String)userMap.get("nickname");//昵称
                String headimgurl = (String)userMap.get("headimgurl");//头像

                //添加
                member = new Member();
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                member.setOpenid(openid);

                memberService.save(member);
            }

            //根据member对象生成jwt字符串

          String token = JwtUtils.geneJsonWebToken(member);

            //回到首页面
            return "redirect:http://localhost:3000?token="+token;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * 生成二维码
     * @return
     */
    @GetMapping("login")
    public String genQrConnect() {
        try {
            //请求微信提供的固定的地址，向地址里面拼接参数，生成二维码
            //微信提供固定的地址
            // 1 微信开放平台授权baseUrl
            // %s表示？，占位符，传递参数
            String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                    "?appid=%s" +
                    "&redirect_uri=%s" +
                    "&response_type=code" +
                    "&scope=snsapi_login" +
                    "&state=%s" +
                    "#wechat_redirect";
            //2 对重定向地址进行urlEncode编码
            String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");

            String state = "lyl2";  //TODO  内网穿透前置域名，为了测试使用，实际开发中可以没有的
            //3 把参数设置到路径里面
            baseUrl = String.format(
                    baseUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    redirectUrl,
                    state);
            //重定向到地址中
            return "redirect:"+baseUrl;
        }catch(Exception e) {
            return null;
        }
    }
}