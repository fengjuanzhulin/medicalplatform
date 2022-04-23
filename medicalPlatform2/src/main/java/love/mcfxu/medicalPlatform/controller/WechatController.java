package love.mcfxu.medicalPlatform.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import love.mcfxu.medicalPlatform.config.WeChatConfig;
import love.mcfxu.medicalPlatform.domain.entity.RegistrationSheet;
import love.mcfxu.medicalPlatform.domain.entity.UserEntity;
import love.mcfxu.medicalPlatform.service.RegistrationSheetService;
import love.mcfxu.medicalPlatform.service.UserEntityService;
import love.mcfxu.medicalPlatform.utils.JsonData;
import love.mcfxu.medicalPlatform.utils.JwtUtils;
import love.mcfxu.medicalPlatform.utils.WXPayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

@Api(value = "微信controller")
@Controller
@RequestMapping("/user/wechat")
public class WechatController {

    @Autowired
    WeChatConfig weChatConfig;

    @Autowired
    UserEntityService userService;


    @Autowired
    RegistrationSheetService registrationSheetService;

    /**
     * 拼装微信扫一扫登录url
     * @return
     */
    @ApiOperation(value = "拼装微信扫一扫登录url(可以无视)")
    @GetMapping("login_url")
    @ResponseBody
    public JsonData loginUrl(@RequestParam(value = "access_page",
            required = true)String accessPage) throws UnsupportedEncodingException {

        String redirectUrl = weChatConfig.getOpenRedirectUrl();

        String callbackUrl = URLEncoder.encode(redirectUrl,"GBK");

        String qrcodeUrl = String.format(WeChatConfig.getOpenQrcodeUrl(),weChatConfig.getOpenAppId(),callbackUrl,accessPage);

        return JsonData.buildSuccess(qrcodeUrl);
    }

    /**
     * 微信用户登陆回调
     * @param code
     * @param state
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "微信用户登陆回调")
    @GetMapping("/user/callback")
    public void wechatUserCallback(@RequestParam(value = "code",required = true) String code,
                                   String state, HttpServletResponse response) throws IOException {


        UserEntity user = userService.saveWeChatUser(code);
        if(user != null){

            String token = JwtUtils.geneJsonWebToken(user);

            response.sendRedirect(state+"?token="+token+"&head_img="+user.getHeadImg()+
                    "&name="+URLEncoder.encode(user.getUserName(),"UTF-8"));
        }

    }


    /**
     * 微信支付回调
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/order/callback")
    public void orderCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {

        InputStream inputStream =  request.getInputStream();

        BufferedReader in =  new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line ;
        while ((line = in.readLine()) != null){
            sb.append(line);
        }
        in.close();
        inputStream.close();
        Map<String,String> callbackMap = WXPayUtils.xmlToMap(sb.toString());
        System.out.println(callbackMap);

        SortedMap<String,String> sortedMap = WXPayUtils.getSortedMap(callbackMap);


        if(WXPayUtils.isCorrectSign(sortedMap,weChatConfig.getKey())){

            if("SUCCESS".equals(sortedMap.get("result_code"))){

                String outTradeNo = sortedMap.get("out_trade_no");

                RegistrationSheet registrationSheet = registrationSheetService.findByOutTradeNo(outTradeNo);

                if(registrationSheet != null && registrationSheet.getState()==0){
                    RegistrationSheet sheet = new RegistrationSheet();
                    sheet.setOpenId(sortedMap.get("openid"));
                    sheet.setOutTradeNo(outTradeNo);
                    sheet.setRegisterTime(new Date());
                    sheet.setState(1);
                    int rows = registrationSheetService.updateRegistrationSheetByOutTradeNo(sheet);
                    if(rows == 1){
                        response.setContentType("text/xml");
                        response.getWriter().println("success");
                        return;
                    }
                }
            }
        }
        response.setContentType("text/xml");
        response.getWriter().println("fail");

    }


}
