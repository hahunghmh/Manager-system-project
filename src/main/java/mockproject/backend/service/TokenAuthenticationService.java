/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/16/2023 - 3:10 PM
 */

package mockproject.backend.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Set;

import static mockproject.backend.utils.constant.Constant.*;

//author: DuongAM
public class TokenAuthenticationService {
    private static Set<GrantedAuthority> grantedAuthorities = null;

    public static void addAuthentication(HttpServletResponse res, String username,
                                         Set<GrantedAuthority> grantedAuthorities) {
        String JWT = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
        TokenAuthenticationService.grantedAuthorities = grantedAuthorities;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String username = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody().getSubject();
            return username != null
                    ? new UsernamePasswordAuthenticationToken(username, null, TokenAuthenticationService.grantedAuthorities)
                    : null;
        }
        return null;
    }

}
