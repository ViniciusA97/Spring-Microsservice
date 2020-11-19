package learning.microservice.note.security.config;

import learning.microservice.core.property.JwtConfiguration;
import learning.microservice.security.config.SecurityTokenConfig;
import learning.microservice.security.filter.JwtTokenAuthorizationFilter;
import learning.microservice.security.token.converter.TokenConverter;
import learning.microservice.security.token.creator.TokenCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends SecurityTokenConfig {

        private final TokenConverter tokenConverter;

        public SecurityConfig(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
            super(jwtConfiguration);
            this.tokenConverter = tokenConverter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterAfter(new JwtTokenAuthorizationFilter(jwtConfiguration,tokenConverter), UsernamePasswordAuthenticationFilter.class);
            super.configure(http);
        }

}
