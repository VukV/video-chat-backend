package com.example.videochatbackend.services;

import com.example.videochatbackend.domain.dtos.user.UserDto;
import com.example.videochatbackend.domain.entities.User;
import com.example.videochatbackend.domain.exceptions.NotFoundExceptions;
import com.example.videochatbackend.domain.mappers.UserMapper;
import com.example.videochatbackend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO
        return null;
    }

    public UserDto findUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(UserMapper.INSTANCE::userToUserDto).orElseThrow(() -> new NotFoundExceptions("User not found."));
    }
}
