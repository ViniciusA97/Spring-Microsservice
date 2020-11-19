package learning.microservice.security.token.creator;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import learning.microservice.core.model.user.User;
import learning.microservice.core.property.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

//Classe responsavel por criar o token com suas claims
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenCreator {

    private final JwtConfiguration jwtConfiguration;

    //Assina o token depois criptografa
    @SneakyThrows
    public SignedJWT createSignedJWT(Authentication auth){
        log.info("Starting create signed JWT");
        User user = (User) auth.getPrincipal();
        log.info("User on signed JWT : {}", user.toString());
        JWTClaimsSet claimSet = createClaimSet(auth, user);

        KeyPair keyPair = generateKeyPair();

        JWK jwk = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).keyID(UUID.randomUUID().toString()).build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).jwk(jwk).type(JOSEObjectType.JWT).build(), claimSet);

        RSASSASigner rsassaSigner = new RSASSASigner(keyPair.getPrivate());

        signedJWT.sign(rsassaSigner);

        return signedJWT;
    }

    public JWTClaimsSet createClaimSet(Authentication auth, User user){
        log.info("Creating JWTClaimSet user: {}", user.toString() );
        return new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .claim("authorities", auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(toList()))
                .claim("userId", user.getId())
                .claim("email",user.getEmail())
                .issuer("http://learning.microservice")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (jwtConfiguration.getExpiration() * 1000)))
                .build();
    }

    @SneakyThrows
    public KeyPair generateKeyPair(){
        log.info("generating keys");

        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
        rsa.initialize(2048);
        return  rsa.genKeyPair();

    }

    @SneakyThrows
    public String encryptToken(SignedJWT signedJWT){
        DirectEncrypter directEncrypter = new DirectEncrypter(jwtConfiguration.getPrivateKey().getBytes());
        JWEObject jweObject = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256).contentType("JWT").build(), new Payload(signedJWT));
        jweObject.encrypt(directEncrypter);

        return jweObject.serialize();
    }


}
