package tech.robsondev.beneficiarioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import tech.robsondev.beneficiarioapi.entity.UserApi;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserApi, UUID>{
    UserDetails findByEmail(String email);
}
