package com.search.blog.service;

import com.search.blog.entity.User;
import com.search.blog.exception.BlogServiceException;
import com.search.blog.exception.ErrorCode;
import com.search.blog.repository.UserRepository;
import com.search.blog.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BlogServiceException(ErrorCode.USER_NOT_EXIST,
                        HttpStatus.BAD_REQUEST, "user email info not found : " + email));
        return UserPrincipal.create(user);
    }
}