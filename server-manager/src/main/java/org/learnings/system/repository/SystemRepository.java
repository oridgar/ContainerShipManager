package org.learnings.system.repository;

import org.learnings.system.domain.SystemLinux;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemRepository extends JpaRepository<SystemLinux,Integer>{
	
	SystemLinux findOneByHostname(String hostname);
	SystemLinux findOneById(int id);
}
