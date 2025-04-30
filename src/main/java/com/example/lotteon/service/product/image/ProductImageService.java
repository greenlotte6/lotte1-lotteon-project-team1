package com.example.lotteon.service.product.image;

import com.example.lotteon.repository.product.image.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductImageService {

  private final ProductImageRepository repo;
  private final ModelMapper mapper;

  public void updateListThumbnail(int imageId, String location) {
    repo.updateListThumbnail(imageId, location);
  }

  public void updateDetailThumbnail(int imageId, String location) {
    repo.updateDetailThumbnail(imageId, location);
  }

  public void updateDetailImage(int imageId, String location) {
    repo.updateDetailImage(imageId, location);
  }

  public void updateMainThumbnail(int imageId, String location) {
    repo.updateMainThumbnail(imageId, location);
  }
}
