package learning.microservice.gateway.security.filter;

import com.netflix.zuul.context.RequestContext;
import com.nimbusds.jwt.SignedJWT;
import learning.microservice.core.property.JwtConfiguration;
import learning.microservice.security.filter.JwtTokenAuthorizationFilter;
import learning.microservice.security.token.converter.TokenConverter;
import learning.microservice.security.util.SecurityContextUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static learning.microservice.security.util.SecurityContextUtil.setSecurityContext;

@Slf4j
public class GatewayJwtTokenAuthorizationFilter extends JwtTokenAuthorizationFilter {

    public GatewayJwtTokenAuthorizationFilter(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration, tokenConverter);
    }

    @Override
    @SuppressWarnings("Duplicates")
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(jwtConfiguration.getHeader().getName());

        if(header == null || !header.startsWith(jwtConfiguration.getHeader().getPrefix())){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return ;
        }
        log.info("Recive : {}",header);
        String token = header.replace(jwtConfiguration.getHeader().getPrefix(),"").trim();
        log.info("token: {}",token);
        String decryptToken = tokenConverter.decryptToken(token);
        log.info("decryptToken: {}",token);
        tokenConverter.validateTokenSignature(decryptToken);

        setSecurityContext(SignedJWT.parse(decryptToken));

        if(jwtConfiguration.getType().equalsIgnoreCase("signed"))
            RequestContext.getCurrentContext().addZuulRequestHeader("Authorization",jwtConfiguration.getHeader().getPrefix() + decryptToken);

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }


}
