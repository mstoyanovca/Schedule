package com.mstoyanov.schedule.domain;

import org.springframework.data.repository.CrudRepository;
import com.mstoyanov.schedule.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findDistinctByEmail(String email);
}
