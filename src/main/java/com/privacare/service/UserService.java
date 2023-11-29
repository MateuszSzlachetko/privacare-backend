package com.privacare.service;

import com.privacare.model.dto.response.UserResponseDTO;
import com.privacare.model.entity.User;
import com.privacare.repository.UserRepository;
import com.privacare.utilities.exception.custom.not_found.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserBy(UUID id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id));
    }

    public UserResponseDTO getUserResponseDTOBy(UUID id) {
        return mapUserToUserResponse(this.userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)));
    }

    public List<UserResponseDTO> getUsersByPeselFragment(String peselFragment) {
        return this.userRepository.findByPeselContaining(peselFragment).stream()
                .map(UserService::mapUserToUserResponse).collect(Collectors.toList());
    }

    private static UserResponseDTO mapUserToUserResponse(User user) {
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

    public UserResponseDTO getUsersByAuthId(String authId) {
        return mapUserToUserResponse(this.userRepository.findByAuthId(authId).orElseThrow(
                () -> new UserNotFoundException(authId)));
    }
}
