package learning.microservice.security.token.converter;

import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import learning.microservice.core.property.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//Classe responsavel por converter o token, e pegar suas claims
public class TokenConverter {

    private final JwtConfiguration jwtConfiguration;

    @SneakyThrows
    public String decryptToken(String encryptToken){
        log.info("Decrypting token");

        JWEObject jweObject = JWEObject.parse(encryptToken);

        DirectDecrypter directDecrypter = new DirectDecrypter(jwtConfiguration.getPrivateKey().getBytes());

        jweObject.decrypt(directDecrypter);

        log.info("Token decrypted, returning signed token . . . ");

        return jweObject.getPayload().toSignedJWT().serialize();
    }

    @SneakyThrows
    public void validateTokenSignature(String signedToken){
        SignedJWT sign = SignedJWT.parse(signedToken);
        RSAKey publicKey = RSAKey.parse(sign.getHeader().getJWK().toJSONObject());
        if(!sign.verify(new RSASSAVerifier(publicKey))) throw new AccessDeniedException("Invalid token signature");


    }

}
