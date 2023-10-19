package com.privacare.service;

import com.privacare.model.entity.User;
import com.privacare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserBy(UUID id) throws NoSuchElementException {
        return this.userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User with id: " + id +" not found"));
    }
}
