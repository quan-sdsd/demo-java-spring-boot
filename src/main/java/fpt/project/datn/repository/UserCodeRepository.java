package fpt.project.datn.repository;

import fpt.project.datn.object.entity.UserCode;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserCodeRepository extends JpaRepository<UserCode, Integer> {
    @Modifying
    @Transactional
    @Query("delete from UserCode uc where uc.code = ?2 and uc.user.username = ?1 and uc.type = 'email-confirmation'")
    public int emailConfirmation(String username, String code);
}
