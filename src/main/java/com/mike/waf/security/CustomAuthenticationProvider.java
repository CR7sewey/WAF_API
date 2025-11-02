package com.mike.waf.security;

import com.mike.waf.model.entities.User;
import com.mike.waf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("CustomAuthenticationProvider.authenticate()");
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> userExists = userService.findByUsername(username);

        if (userExists.isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("User not found");
        }

        User user = userExists.get();
        // TODO - to be eliminated
        if (user.getRoles() == null) {
            user.setRoles(
                    List.of()
            );
        }

        boolean passwordMatch = passwordEncoder.matches(password, user.getPassword());
        if (passwordMatch) {
            CustomAuthentication customAuthentication = new CustomAuthentication(user);
            return customAuthentication;

        }
        else  {
            throw new AuthenticationCredentialsNotFoundException("Invalid username or password");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class); //basic redirect to authenticate
    }
}
