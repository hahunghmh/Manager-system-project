/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/21/2023 - 4:35 PM
 */

package mockproject.backend.utils.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Constant {
    public static long FORGOT_EXPIRATION_TIME=60;
//    10 min
    public static long TOKEN_EXPIRATION_TIME=6_000_000;
    public static String SECRET="ThisIsASecret";
    public static String TOKEN_PREFIX="Bearer";
    public static String HEADER_STRING="Authorization";
//
//    @Value("${secret.key}")
//    public void setSECRET(String secret) {
//        Constant.SECRET = secret;
//    }
//
//    @Value("${header.string}")
//    public void setHeaderString(String headerString) {
//        Constant.HEADER_STRING = headerString;
//    }
//
//    @Value("${token.expired.time}")
//    public void setExpirationTime(long expirationTime) {
//        Constant.TOKEN_EXPIRATION_TIME = expirationTime;
//    }
//
//    @Value("${forgot.expired.time}")
//    public static void setTimeExpired(long forgotExperiedTime) {
//        Constant.FORGOT_EXPIRATION_TIME = forgotExperiedTime;
//        System.out.println(FORGOT_EXPIRATION_TIME);
//    }
}
