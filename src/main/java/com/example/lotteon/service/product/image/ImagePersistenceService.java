package com.example.lotteon.service.product.image;

import com.example.lotteon.dto.product.ProductImageDTO;
import com.example.lotteon.entity.product.ProductImage;
import com.example.lotteon.repository.jpa.product.image.ProductImageRepository;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImagePersistenceService {

  @Value("${lotteon.upload.product.path}")
  private String uploadPath;

  private final ProductImageRepository repo;
  private final ModelMapper mapper;

  private File getUploadPath(MultipartFile image) {
    String originalName = image.getOriginalFilename();
    int extensionIndex = originalName.lastIndexOf(".");
    String extension = originalName.substring(extensionIndex);
    String uuidName = UUID.randomUUID().toString();
    String destinationPath = uploadPath + "/" + uuidName + extension;
    return new File(destinationPath);
  }

  private ProductImage toEntity(int imageId, Map<String, String> typeUrlMap) {
    return ProductImage.builder()
        .id(imageId)
        .listThumbnailLocation(typeUrlMap.get("listThumbnail"))
        .mainThumbnailLocation(typeUrlMap.get("mainThumbnail"))
        .detailThumbnailLocation(typeUrlMap.get("detailThumbnail"))
        .detailImageLocation(typeUrlMap.get("detailImage"))
        .build();
  }

  private ProductImage toEntity(Map<String, String> typeUrlMap) {
    return ProductImage.builder()
        .listThumbnailLocation(typeUrlMap.get("listThumbnail"))
        .mainThumbnailLocation(typeUrlMap.get("mainThumbnail"))
        .detailThumbnailLocation(typeUrlMap.get("detailThumbnail"))
        .detailImageLocation(typeUrlMap.get("detailImage"))
        .build();
  }

  /**
   * Upload multipart files into upload path.
   *
   * @return Hash map of image type(list thumbnail, main thumbnail, ...) as a key and upload path as
   * a value
   */
  private Map<String, String> upload(Map<String, MultipartFile> multipartFileMap)
      throws IOException {
    HashMap<String, String> typeUrlMap = new HashMap<>();

    for (Map.Entry<String, MultipartFile> entry : multipartFileMap.entrySet()) {
      MultipartFile image = entry.getValue();
      if (!image.isEmpty()) {//업로드된 이미지 파일이 있을 때에만 저장 및 UPDATE 수행
        String key = entry.getKey();
        File dest = getUploadPath(image);
        image.transferTo(dest);
        String url = "/upload/product/" + dest.getName();
        typeUrlMap.put(key, url);
      }
    }

    return typeUrlMap;
  }

  /**
   * @param imageId          id, which serves as PK, of target row at product_image table
   * @param multipartFileMap A hash map that maps image type(list thumbnail, main thumbnail, ...) to
   *                         corresponding multipart image sent by client
   * @throws IOException
   */
  public void uploadAndUpdate(int imageId, Map<String, MultipartFile> multipartFileMap)
      throws IOException {
    Map<String, String> typeUrlMap = upload(multipartFileMap);
    ProductImage image = toEntity(imageId, typeUrlMap);
    for (Map.Entry<String, String> entry : typeUrlMap.entrySet()) {
      String key = entry.getKey();
      switch (key) {
        case "listThumbnail":
          repo.updateListThumbnail(image.getId(), image.getListThumbnailLocation());
          break;
        case "mainThumbnail":
          repo.updateMainThumbnail(image.getId(), image.getMainThumbnailLocation());
          break;
        case "detailThumbnail":
          repo.updateDetailThumbnail(image.getId(), image.getDetailThumbnailLocation());
          break;
        case "detailImage":
          repo.updateDetailImage(image.getId(), image.getDetailImageLocation());
      }
    }
  }

  /**
   * Save image into upload path specified at application.yml, and insert into product_image table.
   *
   * @return Auto-incremented id of inserted record
   */
  public ProductImageDTO uploadAndInsert(Map<String, MultipartFile> multipartFileMap)
      throws IOException {
    Map<String, String> typeUrlMap = upload(multipartFileMap);
    ProductImage image = toEntity(typeUrlMap);
    ProductImage insertedImage = repo.save(image);
    return mapper.map(insertedImage, ProductImageDTO.class);
  }
}
