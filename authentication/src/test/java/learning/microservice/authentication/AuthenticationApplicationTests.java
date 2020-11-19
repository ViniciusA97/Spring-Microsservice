package learning.microservice.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class AuthenticationApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void test(){
		System.out.println(new BCryptPasswordEncoder().encode("vinizin"));
	}

}
