package org.learnings.container.repository;

import org.learnings.container.domain.ContainerImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerRepository extends JpaRepository<ContainerImpl, String> {
	ContainerImpl findOneById(String id);
	ContainerImpl findOneByName(String name);
}
