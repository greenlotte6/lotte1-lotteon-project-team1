package com.example.lotteon.controller.order;

import com.example.lotteon.dto.order.OrderDTO;
import com.example.lotteon.dto.order.OrderItemDTO;
import com.example.lotteon.dto.order.OrderSheet;
import com.example.lotteon.dto.order.OrderStatusDTO;
import com.example.lotteon.dto.product.ProductDTO;
import com.example.lotteon.dto.product.ProductOptionsDTO;
import com.example.lotteon.dto.user.MemberDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.service.order.OrderService;
import com.example.lotteon.service.product.CartService;
import com.example.lotteon.service.product.ProductService;
import com.example.lotteon.service.product.options.ProductOptionsService;
import com.example.lotteon.service.user.MemberService;
import com.example.lotteon.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 상품 상세 페이지에서 구매하기 버튼을 눌렀을 때 전송되는 데이터에 대한 컨트롤러.
 */
@Controller
@RequiredArgsConstructor
public class OrderController {

  private final UserService userService;
  private final MemberService memberService;
  private final OrderService orderService;
  private final ProductOptionsService optionsService;
  private final ProductService productService;
  private final ModelMapper mapper;
  private final CartService cartService;

  private int calculatedTotalPrice(ProductDTO product, int amount) {
    int price = product.getPrice();
    int discountRate = product.getDiscountRate();
    int deliveryFee = product.getDeliveryFee();

    return (price - (price * discountRate / 100)) * amount + deliveryFee;
  }

  @PostMapping("/order")
  public String orderSheet(@ModelAttribute("forms") OrderSheet orderSheet,
      HttpSession session) {
    session.setAttribute("orderSheet", orderSheet);//세션에 주문서 임시 저장
    session.setMaxInactiveInterval(1800); //30분 후 세션 만료 => 세션에 임시 저장 된 Order 관련 데이터 삭제
    return "redirect:/order/sheet?src=detail";
  }

  @GetMapping("/order/sheet")
  public String orderSheet(HttpServletResponse response, HttpSession session, Model model,
      @RequestParam("src") String src)
      throws IOException {
    OrderSheet sessionOrderSheet = (OrderSheet) session.getAttribute("orderSheet");

    if (sessionOrderSheet != null) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      UserDetails userDetails = (UserDetails) auth.getPrincipal();

      List<OrderItemDTO> orderItems = sessionOrderSheet.getOrderItems();
      for (OrderItemDTO orderItem : orderItems) {
        ProductDTO product = orderItem.getProduct();
        Product targetEntity = productService.getById(product.getId());
        ProductDTO targetProduct = mapper.map(targetEntity, ProductDTO.class);
        orderItem.setProduct(targetProduct);

        int totalPrice = calculatedTotalPrice(targetProduct, orderItem.getAmount());
        orderItem.setTotalPrice(totalPrice);
      }

      List<ProductOptionsDTO> options = sessionOrderSheet.getOptions(); //TODO Delete options from order sheet or use them
      for (ProductOptionsDTO option : options) {
        ProductOptionsDTO targetOption = optionsService.getById(option.getId());
        option.setId(targetOption.getId());
        option.setOption(targetOption.getOption());
        option.setValue(targetOption.getValue());
      }

      UserDTO user = userService.getUserInfo(userDetails.getUsername());

      model.addAttribute("src", src);
      model.addAttribute("user", user);
      model.addAttribute("orderSheet", sessionOrderSheet);
      session.setAttribute("orderSheet", sessionOrderSheet); //세션에 주문서 임시 저장
      //session.removeAttribute("orderSheets");
    }

    return "/product/proOrder";
  }

  @PostMapping("/order/place")
  public String placeOrder(HttpSession session, OrderDTO order, @RequestParam("src") String src) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) auth.getPrincipal();

    OrderStatusDTO orderStatus = OrderStatusDTO.builder()
        .id(2)
        .build();
    LocalDate orderDate = LocalDate.now();

    OrderSheet sessionOrderSheet = (OrderSheet) session.getAttribute("orderSheet");
    List<OrderItemDTO> orderItems = sessionOrderSheet.getOrderItems();

    MemberDTO member = memberService.getById(userDetails.getUsername());

    String latestOrderNumber = orderService.getLatestOrderNumber();

    //전달 받은 OrderDTO의 member, orderNUmber, orderStatus, orderDate 속성 초기화
    order.setMember(member);
    order.setOrderNumber(latestOrderNumber);
    order.setStatus(orderStatus);
    order.setOrderDate(orderDate);

    // 전달 받은 OrderItemDTO의 order 속성 초기화
    for (OrderItemDTO orderItem : orderItems) {
      orderItem.setOrder(order);
    }

    // 세션 주문서의 OrderDTO 속성 초기화
    sessionOrderSheet.setOrder(order);

    orderService.placeOrder(sessionOrderSheet);

    return "redirect:/order/result?src=" + src;
  }

  @GetMapping("/order/result")
  public String orderResult(HttpSession session, Model model, @RequestParam("src") String src) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    OrderSheet sessionSheet = (OrderSheet) session.getAttribute("orderSheet");
    model.addAttribute("orderSheet", sessionSheet);
    session.removeAttribute("orderSheet");

    if (src.equals("cart")) {
      cartService.deleteByMemberId(userDetails.getUsername());
    }
    return "/product/proOrderRs";
  }
}
