package fpt.project.datn.object.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Role extends GeneralEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "role")
    private List<User> users;

    @Override
    public String getAuthority() {
        return name;
    }
}
