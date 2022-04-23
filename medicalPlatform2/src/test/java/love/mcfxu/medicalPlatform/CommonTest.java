package love.mcfxu.medicalPlatform;

import io.jsonwebtoken.Claims;
import love.mcfxu.medicalPlatform.domain.entity.UserEntity;
import love.mcfxu.medicalPlatform.utils.JwtUtils;
import org.junit.jupiter.api.Test;

public class CommonTest {

    @Test
    public void geneJwtTest(){
        UserEntity user=new UserEntity();
        user.setUserId(876);
        user.setUserName("mancheng");
        user.setUserRole(2);


        String token = JwtUtils.geneJsonWebToken(user);
        System.out.println(token);

    }

    @Test
    public void checkJwtTest(){
        String token = "eyJzdWIiOiJtY2Z4dSIsInVzZXJf" +
                "aWQiOjg3NiwidXNlcl9uYW1lIjoibWFuY2hlbmciLCJ1c2VyX3JvbG" +
                "UiOjIsImlhdCI6MTY0NjU2NDc3OSwiZXhwIjoxNjQ3MTY5NTc5fQ";

        String token5="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtY2Z4dSIsInVzZ" +
                "XJfYWNjb3VudF9udW1iZXIiOiJmeSIsInVzZXJfcHdkIjoiMjAy" +
                "Q0I5NjJBQzU5MDc1Qjk2NEIwNzE1MkQyMzRCNzAiLCJ1c2VyX3Bob25" +
                "lIjoiMTI0MTQzIiwiaWF0IjoxNjQ3MjQyMTIyLCJleHAiOjE2NDc" +
                "4NDY5MjJ9.9ILVjEU776Lzaw3UfrtdkeAzrS5hnSfoZDoAiRXxKOI";
        Claims claims = JwtUtils.checkJwt(token5);
        if(claims!=null){
            System.out.println(claims.get("user_phone"));
            System.out.println(claims.get(("user_pwd")));
            System.out.println(claims.get("user_account_number"));
        }
        else {
            System.out.println("非法token");
        }

    }
}
