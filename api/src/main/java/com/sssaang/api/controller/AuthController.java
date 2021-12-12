package com.sssaang.api.controller;

import com.sssaang.api.auth.JwtTokenProvider;
import com.sssaang.api.entity.Role;
import com.sssaang.api.entity.User;
import com.sssaang.api.entity.constants.RoleName;
import com.sssaang.api.exception.AppException;
import com.sssaang.api.payload.ApiResponse;
import com.sssaang.api.payload.JwtAuthenticationResponse;
import com.sssaang.api.payload.LoginRequest;
import com.sssaang.api.payload.SignUpRequest;
import com.sssaang.api.repository.RoleRepository;
import com.sssaang.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.usernameOrEmail,
                        loginRequest.password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = this.tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(this.userRepository.existsByUsername(signUpRequest.username)) {
            return new ResponseEntity(
                    new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST
            );
        }

        if(this.userRepository.existsByEmail(signUpRequest.email)) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.name, signUpRequest.username,
                signUpRequest.email, signUpRequest.password);

        user.password = this.passwordEncoder.encode(user.password);

        Role userRole = this.roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.roles = Collections.singleton(userRole);

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/{username}")
                .buildAndExpand(result.username)
                .toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

}
