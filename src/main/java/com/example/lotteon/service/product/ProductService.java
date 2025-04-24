package com.example.lotteon.service.product;

import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDTO> proList() {
       List<Product> products = productRepository.findAll();
       List<ProductDTO> productDTOS = new ArrayList<>();
       for (Product product : products) {
           ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
           productDTOS.add(productDTO);
       }

       return productDTOS;
    }
}
