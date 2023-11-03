package com.privacare.service;

import com.privacare.model.dto.response.UserResponseDTO;
import com.privacare.model.entity.User;
import com.privacare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserBy(UUID id) throws NoSuchElementException {
        return this.userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User with id: " + id + " not found"));
    }

    public List<UserResponseDTO> getUsersByPeselFragment(String peselFragment) {
        return this.userRepository.findByPeselContaining(peselFragment).stream()
                .map(UserService::mapUserToUserResponse).collect(Collectors.toList());
    }

    private static UserResponseDTO mapUserToUserResponse(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .authId(user.getAuthId())
                .createdAt(user.getCreatedAt())
                .name(user.getName())
                .surname(user.getSurname())
                .pesel(user.getPesel())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
