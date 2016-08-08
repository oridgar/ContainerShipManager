package org.learnings.users.repository;

import org.learnings.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>{
	
	public User findOneByName(String name);
	public User findOneById(String id);
}