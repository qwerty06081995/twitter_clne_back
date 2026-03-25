package com.nd.twitter.controller;


import com.nd.twitter.config.JwtProvider;
import com.nd.twitter.exception.UserException;
import com.nd.twitter.model.User;
import com.nd.twitter.model.Verification;
import com.nd.twitter.repository.UserRepository;
import com.nd.twitter.response.AuthResponse;
import com.nd.twitter.service.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        System.out.println("User "+user);
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String birthDate = user.getBirthDate();

        User isEmailExists = userRepository.findByEmail(email);

        if (isEmailExists != null) {
            throw new UserException("Email already exists");
        }

        User createdUser = userRepository.save(user);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFullName(fullName);
        createdUser.setBirthDate(birthDate);
        createdUser.setEmail(email);
        createdUser.setVerification(new Verification());

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }
    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();

        Authentication authentication = authenticate(email, password);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String username, String password) throws UserException {
        UserDetails details = customUserDetailsServiceImpl.loadUserByUsername(username);
        if (details == null) {
            throw new BadCredentialsException("Invalid username");
        }

        if (!passwordEncoder.matches(password, details.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());

    }
}
