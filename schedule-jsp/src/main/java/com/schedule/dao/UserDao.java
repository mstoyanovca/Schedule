package com.schedule.dao;

import java.util.List;
import com.schedule.model.User;

public interface UserDao {

	public User findById(int Id);

	public User findByEmail(String email);
	
	public User findByToken(String token);

	public List<User> findAll();

	public void save(User user);

	public void update(User user);
}