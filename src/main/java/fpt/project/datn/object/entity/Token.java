package fpt.project.datn.object.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    private String username;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String accessToken;
    private String refreshToken;

    @PrePersist
    public void prePersist() {
        this.username = this.user.getUsername();
    }
}
