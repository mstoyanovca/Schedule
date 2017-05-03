package com.schedule.service;

import java.util.List;
import com.schedule.model.User;

public interface UserService {

	public List<User> findAll();

	public User findById(int Id);

	public User findByEmail(String email);

	public User findByToken(String token);

	public void save(User user);

	public void update(User user);
}