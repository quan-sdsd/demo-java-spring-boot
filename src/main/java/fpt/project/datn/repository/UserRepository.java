package fpt.project.datn.repository;

import fpt.project.datn.object.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select t from User t where t.username = ?1")
    public Optional<User> findUserByUsername(String username);
}
