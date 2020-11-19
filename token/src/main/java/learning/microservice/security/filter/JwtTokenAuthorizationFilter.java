package learning.microservice.security.filter;

import com.nimbusds.jwt.SignedJWT;
import learning.microservice.core.property.JwtConfiguration;
import learning.microservice.security.token.converter.TokenConverter;
import learning.microservice.security.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Objetivo dessa classe é ser sempre executada, em todas as requisições
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {

    protected final JwtConfiguration jwtConfiguration;
    protected final TokenConverter tokenConverter;

    @Override
    @SuppressWarnings("Duplicates")
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(jwtConfiguration.getHeader().getName());

        if(header == null || !header.startsWith(jwtConfiguration.getHeader().getPrefix())){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return ;
        }

        String token = header.replace(jwtConfiguration.getHeader().getPrefix(),"").trim();
        SecurityContextUtil.setSecurityContext(StringUtils.equalsAnyIgnoreCase("signed",jwtConfiguration.getType())
                ? validate(token)
                : decryptValidating(token)
        );
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    @SneakyThrows
    private SignedJWT decryptValidating(String encryptedToken){
        String decryptToken = tokenConverter.decryptToken(encryptedToken);
        tokenConverter.validateTokenSignature(decryptToken);
        return SignedJWT.parse(decryptToken);
    }

    @SneakyThrows
    private SignedJWT validate(String signedToken){
        tokenConverter.validateTokenSignature(signedToken);
        return SignedJWT.parse(signedToken);
    }


}
