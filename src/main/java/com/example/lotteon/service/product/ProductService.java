package com.example.lotteon.service.product;

import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.dto.product.ProductOptionsDTO;
import com.example.lotteon.dto.seller.SellerDTO;
import com.example.lotteon.dto.seller.SellerIdDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.entity.product.ProductOptions;
import com.example.lotteon.repository.jpa.product.ProductRepository;
import com.example.lotteon.repository.jpa.seller.SellerRepository;
import com.example.lotteon.service.es.ProductSyncService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final SellerRepository sellerRepository;
  private final ProductRepository repo;
  private final ModelMapper modelMapper;
  private final ProductRepository productRepository;
  private final ProductSyncService syncService;

  public List<ProductDTO> proList() {
    List<Product> products = repo.findAll();
    List<ProductDTO> productDTOS = new ArrayList<>();
    for (Product product : products) {
      ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
      productDTOS.add(productDTO);
    }
    return productDTOS;

  }

  public List<ProductDTO> proListBySubCategoryIdSortedBySales(String subcategoryId) {
    List<Product> products = productRepository.findBySubCategoryIdOrderBySalesDesc(subcategoryId);
    return products.stream()
            .map(product -> modelMapper.map(product, ProductDTO.class))
            .toList();
  }

  public List<ProductDTO> proListBySubCategoryIdSortedByPrice(String subcategoryId, String sort) {
    Sort.Direction direction = "desc".equalsIgnoreCase(sort) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort priceSort = Sort.by(direction, "price");

    List<Product> products = productRepository.findBySubCategoryId(subcategoryId, priceSort);
    return products.stream()
            .map(product -> modelMapper.map(product, ProductDTO.class))
            .toList();
  }

  public Page<Product> getAll(Pageable pageable) {
    return repo.findAll(pageable);
  }

  public Page<Product> getByName(String name, Pageable pageable) {
    return repo.findByName(name, pageable);
  }

  public Page<Product> getPagesById(int id, Pageable pageable) {
    return repo.findById(id, pageable);
  }

  public Product getById(int id) {
    return repo.findById(id).orElse(null);
  }

  public Page<Product> getByCompany(String company, Pageable pageable) {
    return repo.findByCompany(company, pageable);
  }

  public void updateStatusById(int id, String status) {
    repo.updateStatusById(id, status);
  }

  public void edit(int id, ProductDTO productDTO) {
    Product product = modelMapper.map(productDTO, Product.class);
    repo.updateById(id, product);
    syncService.sync(product);
  }

  public List<Product> getBestProducts() {
    Pageable topFive = PageRequest.of(0, 5); // 상위 5개
    return productRepository.findTop5ByOrderBySalesDesc(topFive);
  }

  public int getLatestIdAndIncrement() {
    return repo.getLatestIdAndIncrement();
  }

  public void register(String sellerId, ProductDTO product) {
    UserDTO user = UserDTO.builder()
        .id(sellerId)
        .build();
    SellerIdDTO sellerIdDTO = SellerIdDTO.builder()
        .businessNumber("112-12-12345")
        .user(user)
        .build();
    SellerDTO sellerDTO = SellerDTO.builder()
        .sellerId(sellerIdDTO)
        .build();

    product.setSeller(sellerDTO);
    Product entity = modelMapper.map(product, Product.class);
    repo.save(entity);
    syncService.sync(entity);
  }

  public List<ProductOptions> getOptions(int productId) {
    return repo.findOptionsByProductId(productId);
  }

  public ProductOptionsDTO getOption(int productId) {
    ProductOptions option = repo.findOptionByProductId(productId);
    return modelMapper.map(option, ProductOptionsDTO.class);
  }

  public List<ProductDTO> proListSortedByPrice(String sort) {
    Sort.Direction direction =
        "desc".equalsIgnoreCase(sort) ? Sort.Direction.DESC : Sort.Direction.ASC;
    List<Product> products = repo.findAll(Sort.by(direction, "price"));

    List<ProductDTO> productDTOS = new ArrayList<>();
    for (Product product : products) {
      ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
      productDTOS.add(productDTO);
    }
    return productDTOS;
  }

  public List<ProductDTO> proListSortedBySales() {
    List<Object[]> result = productRepository.findProductsOrderBySalesDesc();

    List<ProductDTO> dtoList = new ArrayList<>();
    for (Object[] row : result) {
      Product product = (Product) row[0];

      ProductDTO dto = modelMapper.map(product, ProductDTO.class);
      dtoList.add(dto);
    }

    return dtoList;
  }
  public List<ProductDTO> getDiscountedProducts() {
    return productRepository.findAll().stream()
            .filter(p -> p.getDiscountRate() > 0)
            .sorted(Comparator.comparingInt(Product::getDiscountRate).reversed())
            .map(p -> modelMapper.map(p, ProductDTO.class))
            .limit(8)
            .toList();
  }

  public List<Product> getHitProducts() {
    return productRepository.findProductsOrderBySalesDesc().stream()
            .map(row -> (Product) row[0])
            .limit(8)
            .toList();
  }

  public List<ProductDTO> getRecommendedProducts() {
    return productRepository.findAll().stream()
            .sorted(Comparator.comparingInt(p ->
                    (int) (p.getPrice() * (100 - p.getDiscountRate()) / 100.0)
            ))
            .limit(8)
            .map(p -> modelMapper.map(p, ProductDTO.class))
            .toList();
  }

  public List<ProductDTO> proListBySubCategoryId(String subcategoryId) {
    List<Product> products = productRepository.findBySubCategory_Id(subcategoryId);
    return products.stream()
            .map(product -> modelMapper.map(product, ProductDTO.class))
            .toList();
  }

}
