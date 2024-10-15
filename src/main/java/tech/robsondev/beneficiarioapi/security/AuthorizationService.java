package tech.robsondev.beneficiarioapi.security;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import tech.robsondev.beneficiarioapi.dto.AuthetinticationDTO;
import tech.robsondev.beneficiarioapi.dto.LoginResponseDTO;
import tech.robsondev.beneficiarioapi.dto.RegisterDTO;
import tech.robsondev.beneficiarioapi.entity.UserApi;
import tech.robsondev.beneficiarioapi.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthetinticationDTO data) {
        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserApi) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDto) {
        if (this.userRepository.findByEmail(registerDto.email()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

        UserApi newUser = new UserApi(registerDto.email(), encryptedPassword, registerDto.role());
        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}