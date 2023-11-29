package com.privacare.repository;

import com.privacare.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByPeselContaining(String peselFragment);

    Optional<User> findByAuthId(String authId);
}