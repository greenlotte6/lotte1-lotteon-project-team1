package com.example.lotteon.service.product;

import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.dto.product.ProductOptionsDTO;
import com.example.lotteon.dto.seller.SellerDTO;
import com.example.lotteon.dto.seller.SellerIdDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.entity.product.ProductOptions;
import com.example.lotteon.repository.product.ProductRepository;
import com.example.lotteon.repository.seller.SellerRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final SellerRepository sellerRepository;
  private final ProductRepository repo;
  private final ModelMapper modelMapper;

  public List<ProductDTO> proList() {
    List<Product> products = repo.findAll();
    List<ProductDTO> productDTOS = new ArrayList<>();
    for (Product product : products) {
      ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
      productDTOS.add(productDTO);
    }
    return productDTOS;
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
  }

  public int getLatestIdAndIncrement() {
    return repo.getLatestIdAndIncrement();
  }

  public void register(ProductDTO product) {
    UserDTO user = UserDTO.builder()
        .id("seller1")
        .build();
    SellerIdDTO sellerId = SellerIdDTO.builder()
        .businessNumber("112-12-12345")
        .user(user)
        .build();
    SellerDTO sellerDTO = SellerDTO.builder()
        .sellerId(sellerId)
        .build();

    product.setSeller(sellerDTO);
    Product entity = modelMapper.map(product, Product.class);
    repo.save(entity);
  }

  public List<ProductOptions> getOptions(int productId) {
    return repo.findOptionsByProductId(productId);
  }

  public ProductOptionsDTO getOption(int productId) {
    ProductOptions option = repo.findOptionByProductId(productId);
    return modelMapper.map(option, ProductOptionsDTO.class);
  }

}
