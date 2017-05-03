package com.mstoyanov.schedule.domain;

import org.springframework.data.repository.CrudRepository;
import com.mstoyanov.schedule.domain.Token;

public interface TokenRepository extends CrudRepository<Token, Long> {

	Token findDistinctByToken(String token);
}
