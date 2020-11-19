package learning.microservice.core.repository.user;

import learning.microservice.core.model.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User getByEmail(String email);
    User getByUsername(String username);
}
