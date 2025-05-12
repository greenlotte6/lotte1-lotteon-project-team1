package com.example.lotteon.controller.order;

import com.example.lotteon.dto.order.OrderSheetRequestForm;
import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.dto.product.ProductOptionsDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.service.product.ProductService;
import com.example.lotteon.service.product.options.ProductOptionsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 상품 상세 페이지에서 구매하기 버튼을 눌렀을 때 전송되는 데이터에 대한 컨트롤러.
 */
@Controller
@RequiredArgsConstructor
public class OrderController {

  private final ProductOptionsService optionsService;
  private final ProductService productService;
  private final ModelMapper mapper;

  @GetMapping("/order")
  public String orderSheet(OrderSheetRequestForm form, Model model) {
    //TODO 나중에 UserDetails에서 user id 가져와서 DB 조회할 것.
    UserDTO user = UserDTO.builder()
        .contact("010-1111-2222")
        .zip("12345")
        .address("부산광역시")
        .addressDetail("수영구")
        .build();

    List<ProductDTO> targetProducts = form.getProducts().stream().map((product) -> {
      Product targetEntity = productService.getById(product.getId());
      return mapper.map(targetEntity, ProductDTO.class);
    }).toList();

    List<ProductOptionsDTO> targetOptions = form.getOptions().stream().map((option) -> {
      return optionsService.getById(option.getId());
    }).toList();

    form.setProducts(targetProducts);
    form.setOptions(targetOptions);

    model.addAttribute("user", user);
    model.addAttribute("form", form);
    return "/product/proOrder";
  }
}
