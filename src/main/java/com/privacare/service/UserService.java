package com.privacare.service;

import com.privacare.model.dto.request.UserRequestDTO;
import com.privacare.model.dto.response.UserResponseDTO;
import com.privacare.model.entity.User;
import com.privacare.repository.UserRepository;
import com.privacare.utilities.exception.custom.not_found.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.privacare.utilities.security.UserAccessGuard.checkUserAccess;

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
        checkUserAccess(authId);
        return mapUserToUserResponse(this.userRepository.findByAuthId(authId).orElseThrow(
                () -> new UserNotFoundException(authId)));
    }

    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {
        checkUserAccess(userRequestDTO.getAuthId());
        Optional<User> existingUser = this.userRepository.findByAuthId(userRequestDTO.getAuthId());

        if (existingUser.isPresent())
            throw new RuntimeException("User with authId: " + userRequestDTO.getAuthId() + " already exists");

        User user = User.builder()
                .authId(userRequestDTO.getAuthId())
                .createdAt(LocalDateTime.now())
                .name(userRequestDTO.getName())
                .surname(userRequestDTO.getSurname())
                .pesel(userRequestDTO.getPesel())
                .phoneNumber(userRequestDTO.getPhoneNumber())
                .build();

        this.userRepository.save(user);
        return mapUserToUserResponse(user);
    }
}
