package com.example.lotteon.service.product;

import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.repository.product.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingRepository;
    private final ModelMapper modelMapper;

    //public ProductDTO shoppingCart(int id) {
      //  Optional<Product> optProduct = shoppingRepository.findById(id);

        //if(optProduct.isPresent()) {

          //  Product product = optProduct.get();

            //ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            //log.info("productDTO: " + productDTO);
            //return productDTO;
        //}
        //return null;
    //}

    public List<ProductDTO> shoppingCart() {

        List<Product> productList = shoppingRepository.findAll();

        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : productList) {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            productDTOS.add(productDTO);
        }

        return productDTOS;
    }

}
