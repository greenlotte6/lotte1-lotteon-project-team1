package com.example.lotteon.service.product;

import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.repository.UserRepository;
import com.example.lotteon.repository.product.ProductRepository;
import com.example.lotteon.repository.product.ShoppingCartRepository;
import com.example.lotteon.service.user.LoginUserService;
import jakarta.servlet.http.HttpSession;
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

    private final LoginUserService loginUserService;
    private final ShoppingCartRepository shoppingRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;


    public void addCart(int id) {

        // 로그인 사용자 Id 가져오기
        //String loginUser = loginUserService.getLoginUser();
        //userRepository.findby

      //  Optional<Product> optProduct = shoppingRepository.findById(id);

       // if(optProduct.isPresent()) {
       //     Product product = optProduct.get();

         //   ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
         //   log.info("productDTO: " + productDTO);


     //   }

    }

    // 장바구니 조회
    public ProductDTO getCart(int id) {

        Optional<Product> optPro = productRepository.findById(id);

        if(optPro.isPresent()) {
            Product product = optPro.get();

            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            log.info(productDTO.toString());
            return productDTO;
        }
        return null;

    }

}
