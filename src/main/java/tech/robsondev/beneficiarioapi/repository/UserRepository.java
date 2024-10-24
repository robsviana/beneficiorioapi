package tech.robsondev.beneficiarioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import tech.robsondev.beneficiarioapi.entity.UserApi;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserApi, UUID>{
    UserDetails findByEmail(String email);
}
