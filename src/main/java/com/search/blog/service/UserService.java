package com.search.blog.service;

import com.search.blog.constant.Role;
import com.search.blog.dto.UserDto;
import com.search.blog.entity.User;
import com.search.blog.repository.UserRepository;
import com.search.blog.util.SecurityUtil;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Builder
@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signUp(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).orElse(null)
                != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        User users =
                User.builder()
                        .email(userDto.getEmail())
                        .name(userDto.getName())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .roles(EnumSet.of(Role.USER))
                        .activated(Boolean.TRUE)
                        .build();

        return userRepository.save(users);
    }

    // user 이름을 기준으로 정보를 가져온다.
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String email) {
        return userRepository.findByEmail(email);
    }

    // Security Context에 저장된 username의 정보만 가져온다.
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findByEmail);
    }
}
