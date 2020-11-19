package learning.microservice.security.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import learning.microservice.core.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
public class SecurityContextUtil {

    private SecurityContextUtil(){

    }

    public static void setSecurityContext(SignedJWT signedJWT){
        try{
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            String username = jwtClaimsSet.getSubject();
            if(username==null) throw new JOSEException("Username missing");
            List<String> authorities = jwtClaimsSet.getStringListClaim("authorities");
            log.info("USER DETAILS: {}",(jwtClaimsSet.getStringClaim("email")));
            User user = User.builder()
                    .id(jwtClaimsSet.getLongClaim("userId"))
                    .username(username)
                    .email(jwtClaimsSet.getStringClaim("email"))
                    .role(String.join(",",authorities))
                    .build();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, createAuthoritie(authorities));
            auth.setDetails(signedJWT.serialize());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }catch (Exception e){
            log.info("Exception on Util: {}",e.getMessage());
            SecurityContextHolder.clearContext();
        }
        
    }

    private static List<SimpleGrantedAuthority> createAuthoritie(List<String> authorities){
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }



}
