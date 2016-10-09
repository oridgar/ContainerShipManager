package org.learnings.image.service;

import java.util.List;

import org.learnings.container.domain.Container;
import org.learnings.container.domain.ContainerImpl;
import org.learnings.image.domain.ImageImpl;
import org.learnings.libs.ContainerStatus;

public interface ImageService {
	public ImageImpl getImage(int id);
	public List<ImageImpl> getImageList();
	public void createImage(ImageImpl details);
	public void deleteImage(int id);
}
