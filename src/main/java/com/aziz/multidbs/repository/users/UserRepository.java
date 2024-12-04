package com.aziz.multidbs.repository.users;

import com.aziz.multidbs.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}