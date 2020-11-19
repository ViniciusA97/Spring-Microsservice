package learning.microservice.gateway.security.config;

import learning.microservice.core.property.JwtConfiguration;
import learning.microservice.gateway.security.filter.GatewayJwtTokenAuthorizationFilter;
import learning.microservice.security.config.SecurityTokenConfig;
import learning.microservice.security.token.converter.TokenConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends SecurityTokenConfig {
    private final TokenConverter tokenConverter;

    public SecurityConfig(JwtConfiguration jwtConfiguration,TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.tokenConverter = tokenConverter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new GatewayJwtTokenAuthorizationFilter(jwtConfiguration,tokenConverter), UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }
}
