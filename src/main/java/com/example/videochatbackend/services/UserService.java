package com.example.videochatbackend.services;

import com.example.videochatbackend.domain.dtos.user.UserDto;
import com.example.videochatbackend.domain.entities.User;
import com.example.videochatbackend.domain.exceptions.NotFoundException;
import com.example.videochatbackend.domain.mappers.UserMapper;
import com.example.videochatbackend.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public UserDto findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(UserMapper.INSTANCE::userToUserDto).orElseThrow(() -> new NotFoundException("User not found."));
    }

    public List<UserDto> findUserContacts(String username) {
        Optional<User> user = userRepository.findUserContacts(username);
        if(user.isPresent()){
            return user.get().getContacts().stream().map(UserMapper.INSTANCE::userToUserDto).collect(Collectors.toList());
        }
        else {
            throw new NotFoundException("User not found.");
        }
    }

    public Page<UserDto> findUsersContainingUsername(String username, Integer page, Integer size) {
        List<String> contacts = filterContacts();
        Page<User> users = userRepository.findByUsernameContainingIgnoreCaseAndUsernameNotIn(username, contacts, PageRequest.of(page, size));

        return new PageImpl<>(
                users.stream().map(UserMapper.INSTANCE::userToUserDto).collect(Collectors.toList()),
                PageRequest.of(page, size),
                users.getTotalElements()
        );
    }

    private List<String> filterContacts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));

        List<String> contacts = new ArrayList<>();
        contacts.add(username);
        contacts.addAll(user.getContacts().stream().map(User::getUsername).toList());

        return contacts;
    }
}
