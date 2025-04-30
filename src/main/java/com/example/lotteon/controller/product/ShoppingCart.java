package com.example.lotteon.controller.product;

import com.example.lotteon.dto.product.SessionCartDTO;
import com.example.lotteon.entity.product.Cart;
import com.example.lotteon.entity.product.CartId;
import com.example.lotteon.entity.product.Product;
import com.example.lotteon.repository.product.ProductRepository;
import com.example.lotteon.service.product.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShoppingCart {

    private final ShoppingCartService shoppingCartService;
    private final ProductRepository productRepository;

    @PostMapping("/product/cart/add")
    public String addCart(@RequestParam("id") int productId,
                          @RequestParam("quantity") int quantity,
                          HttpSession session) {

        String memberId = (String) session.getAttribute("loginUserId");

        if (memberId == null) {
            List<SessionCartDTO> temporaryCart = (List<SessionCartDTO>) session.getAttribute("temporaryCart");

            if (temporaryCart == null) {
                temporaryCart = new ArrayList<>();
            }

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

            int price = product.getPrice();
            int discountRate = product.getDiscountRate();
            int shippingFee = 3000;
            int point = (price / 1000) * quantity;
            double discountedPrice = (price * quantity) * (1 - (discountRate / 100.0));
            int totalPrice = (int) discountedPrice + shippingFee;

            boolean productExists = false;
            for (SessionCartDTO cartItem : temporaryCart) {
                if (cartItem.getProductId() == productId) {
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    double updatedDiscountedPrice = (cartItem.getPrice() * cartItem.getQuantity()) * (1 - (cartItem.getDiscountRate() / 100.0));
                    int updatedTotalPrice = (int) updatedDiscountedPrice + cartItem.getDil();
                    cartItem.setTotalPrice(updatedTotalPrice);
                    cartItem.setPoint((cartItem.getPrice() / 1000) * cartItem.getQuantity());
                    productExists = true;
                    break;
                }
            }

            if (!productExists) {
                SessionCartDTO newCartItem = new SessionCartDTO(
                        product.getId(), product.getName(), price, discountRate,
                        point, shippingFee, quantity, totalPrice
                );
                temporaryCart.add(newCartItem);
            }

            session.setAttribute("temporaryCart", temporaryCart);
            return "redirect:/product/cart/view";
        }

        shoppingCartService.addCart(productId, memberId, quantity);
        return "redirect:/product/cart/view";
    }

    @GetMapping("/product/cart/view")
    public String viewCart(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("loginUserId");
        List<SessionCartDTO> cartItems = new ArrayList<>();

        if (memberId != null) {
            List<Cart> dbCartItems = shoppingCartService.getCartItems(memberId);
            for (Cart cart : dbCartItems) {
                SessionCartDTO dto = new SessionCartDTO(
                        cart.getProduct().getId(), cart.getProduct().getName(),
                        cart.getPrice(), cart.getProduct().getDiscountRate(),
                        cart.getProduct().getPoint(), cart.getDil(),
                        cart.getQuantity(), cart.getTotalPrice()
                );
                cartItems.add(dto);
            }
        } else {
            cartItems = (List<SessionCartDTO>) session.getAttribute("temporaryCart");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }
        }

        model.addAttribute("cart", cartItems);
        applyCartSummary(cartItems, model);
        return "/product/shoppingCart";
    }

    @PostMapping("/cart/deleteSelected")
    public String deleteSelectedCartItems(@RequestParam("productIds") List<Integer> productIds,
                                          HttpSession session) {
        String memberId = (String) session.getAttribute("loginUserId");

        List<CartId> compositeIds = productIds.stream()
                .map(pid -> new CartId(memberId, pid))
                .toList();

        shoppingCartService.deleteByCompositeIds(compositeIds);
        return "redirect:/product/cart/view";
    }

    // ✔ 공통 요약정보 계산 메서드
    public static void applyCartSummary(List<SessionCartDTO> cartItems, Model model) {
        int productCount = cartItems.size();
        int totalProductPrice = 0;
        int totalDiscount = 0;
        int totalShippingFee = 0;
        int totalOrderPrice;
        int totalPoint = 0;

        for (SessionCartDTO cart : cartItems) {
            totalProductPrice += cart.getPrice() * cart.getQuantity();
            totalDiscount += (cart.getPrice() * cart.getQuantity() * cart.getDiscountRate()) / 100;
            totalShippingFee += cart.getDil();
            totalPoint += cart.getPoint();
        }

        totalOrderPrice = (totalProductPrice - totalDiscount) + totalShippingFee;

        model.addAttribute("productCount", productCount);
        model.addAttribute("totalProductPrice", totalProductPrice);
        model.addAttribute("totalDiscount", totalDiscount);
        model.addAttribute("totalShippingFee", totalShippingFee);
        model.addAttribute("totalOrderPrice", totalOrderPrice);
        model.addAttribute("totalPoint", totalPoint);
    }
}
