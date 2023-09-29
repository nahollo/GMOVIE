package com.gmovie.gmovie.repository;

import com.gmovie.gmovie.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
