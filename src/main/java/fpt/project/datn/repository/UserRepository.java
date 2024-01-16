package fpt.project.datn.repository;

import fpt.project.datn.object.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select t from User t where t.username = ?1")
    public Optional<User> findUserByUsername(String username);

    @Modifying
    @Transactional
    @Query("update User u set u.emailConfirmed = true where u.username = ?1")
    public void confirmEmail(String username);
}
