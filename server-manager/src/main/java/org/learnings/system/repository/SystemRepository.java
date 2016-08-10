package org.learnings.system.repository;

import org.learnings.system.domain.SystemLinux;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemRepository extends JpaRepository<SystemLinux,String>{
	
	SystemLinux findOneByHostname(String hostname);
	SystemLinux findOneById(String id);
}
