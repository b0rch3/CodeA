package atelier.manichino.responses;

import lombok.Data;
import java.util.Set;
import atelier.manichino.entity.Role;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String phoneNumber;
    private String postIndex;
//    private String provider;
//    private boolean active;
//    private String activationCode;
//    private String passwordResetCode;
    private Set<Role> roles;
}
