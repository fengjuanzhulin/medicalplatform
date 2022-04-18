//package love.mcfxu.medicalPlatform.interceptor;
//
//import com.google.gson.Gson;
//import io.jsonwebtoken.Claims;
//import love.mcfxu.medicalPlatform.utils.JsonData;
//import love.mcfxu.medicalPlatform.utils.JwtUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.servlet.HandlerInterceptor;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//public class LoginIntercepter implements HandlerInterceptor {
//
//    /**
//     * 重写preHandler
//     * @param request
//     * @param response
//     * @param handler
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//            String accessToken = request.getHeader("token");
//            if (accessToken == null){
//                accessToken  = request.getParameter("token");
//            }
//            if (StringUtils.isNotBlank(accessToken)){
//                Claims claims = JwtUtils.checkJwt(accessToken);
//                if (claims!=null) {
//                    String phone = (String)claims.get("user_phone");
//                    String account_number= (String)claims.get("user_account_number");
//                    request.setAttribute("user_phone", phone);
//                    request.setAttribute("user_account_number", account_number);
//                    return true;
//                }
//            }
//        sendJsonMessage(response, JsonData.buildError("请登录"));
//        return false;
//    }
//
//    /**
//     * 响应前端数据
//     * @param response
//     * @param obj
//     * @throws IOException
//     */
//    public static void sendJsonMessage(HttpServletResponse response,Object obj) throws IOException {
//        Gson gson = new Gson();
//        response.setContentType("application/json; charset=utf-8");
//        PrintWriter writer = response.getWriter();
//        writer.print(gson.toJson(obj));
//        writer.close();
//        response.flushBuffer();
//    }
//}
