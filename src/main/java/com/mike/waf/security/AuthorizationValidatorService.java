package com.mike.waf.security;

import com.mike.waf.exceptions.AuthorizationValidator;
import com.mike.waf.model.entities.User;
import com.mike.waf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationValidatorService {

    @Autowired
    @Lazy
    private UserService userService;


    public void validate(User user)  {

        if (userIsNotUserOwner(user)) {
            throw new AuthorizationValidator(
                    "User is not authorized"
            );

        }

    }

    public boolean userIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof CustomAuthentication customAuthentication) {
            return customAuthentication
                    .getAuthorities()
                    .stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
        }
        throw new AuthorizationValidator(
                "User is not authorized"
        );
    }

    public boolean userIsNotUserOwner(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var userAuth =  userService.findByUsername(authentication.getName());

        if (userIsAdmin()) { // if user is admin can delete any other user
            return false;
        }

        if(userAuth.isEmpty()) { // if user not found (never caught I think)
            return true;
        }
        if (!user.getId().equals(userAuth.get().getId())) { // if user authitcated is not the deleted
            return true;
        }
        return false;
    }

}
