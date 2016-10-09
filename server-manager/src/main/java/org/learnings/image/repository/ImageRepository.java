package org.learnings.image.repository;

import org.learnings.container.domain.ContainerImpl;
import org.learnings.image.domain.ImageImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageImpl, Integer> {
	ImageImpl findOneById(int id);
	ImageImpl findOneByName(String name);
}
