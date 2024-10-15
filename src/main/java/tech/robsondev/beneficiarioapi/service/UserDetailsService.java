package tech.robsondev.beneficiarioapi.service;

public interface UserDetailsService {
    org.springframework.security.core.userdetails
            .UserDetails loadUserByUsername(java.lang.String username) throws org.springframework
            .security.core.userdetails.UsernameNotFoundException;

}
