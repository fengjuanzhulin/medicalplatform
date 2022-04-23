package love.mcfxu.medicalPlatform.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import love.mcfxu.medicalPlatform.domain.entity.UserEntity;
import love.mcfxu.medicalPlatform.domain.entity.UserEntity;

import java.util.Date;

public class JwtUtils {

    public static final String SUBJECT = "mcfxu";

    public static final long EXPIRE = 1000*60*60*24*7;

    public static final String PLATFORMSECRET = "ningyun10";

    /**
     * 用户登录信息加密
     * @param user
     * @return
     */
    public static String geneJsonWebToken(UserEntity user){

        if (user == null || user.getUserId() == null
                || user.getUserName() == null || user.getUserRole() == null){
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("user_account_number", user.getUserAccountNumber())
                .claim("user_pwd", user.getUserPwd())
                .claim("user_phone", user.getUserPhone())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                .signWith(SignatureAlgorithm.HS256, PLATFORMSECRET)
                .compact();
        return token;
    }

    /**
     * 用户登录信息解密
     * @param token
     * @return
     */
    public static Claims checkJwt(String token){
        try {
        final Claims claims = Jwts.parser().setSigningKey(PLATFORMSECRET)
                .parseClaimsJws(token).getBody();
        return claims;
        }catch (Exception e){}
        return null;
    }
}
