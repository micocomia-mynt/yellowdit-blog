package ph.apper.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class User {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private boolean isVerified = false;
    private boolean isActive = false;

    private LocalDateTime dateRegistered;
    private LocalDateTime dateVerified;
    private LocalDateTime lastLogin;

    public User(String id) {
        this.id = id;
    }
}
