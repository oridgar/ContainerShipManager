package org.learnings.container.repository;

import org.learnings.container.domain.ContainerImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerRepository extends JpaRepository<ContainerImpl, Integer> {
	ContainerImpl findOneById(int id);
	ContainerImpl findOneByName(String name);
}
