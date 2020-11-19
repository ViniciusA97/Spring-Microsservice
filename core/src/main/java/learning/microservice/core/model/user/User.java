package learning.microservice.core.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

    @Id @GeneratedValue
    private long id;

    @Column @NonNull
    private String email;

    @Column @NonNull
    private String username;

    @Column
    private String password;

    @Column @NonNull
    private String role = "ADMIN";

    public User(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role= user.getRole();
    }

}
