package com.example.lotteon.repository.jpa.delivery;

import com.example.lotteon.dto.order.DeliveryWrapper;
import com.example.lotteon.entity.order.Delivery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryCustomRepository {

  List<DeliveryWrapper> findAllDeliveries();

  Page<DeliveryWrapper> findByDeliveryNumber(String deliveryNumber, Pageable pageable);

  Page<DeliveryWrapper> findByDeliveryNumber(String currentSellerId, String deliveryNumber,
      Pageable pageable);

  Page<DeliveryWrapper> findByRecipientNameAsPage(String recipientName, Pageable pageable);

  Page<DeliveryWrapper> findByRecipientNameAsPage(String currentSellerId, String recipientName,
      Pageable pageable);

  Page<DeliveryWrapper> findByOrderNumber(String orderNumber, Pageable pageable);

  Page<DeliveryWrapper> findByOrderNumber(String currentSellerId, String orderNumber,
      Pageable pageable);


  Page<DeliveryWrapper> findAllAsPage(String currentSellerId, Pageable pageable);

  Page<DeliveryWrapper> findAllAsPage(Pageable pageable);

  Delivery findById(String deliveryNumber);

  Delivery findById(String currentSellerId, String deliveryNumber);

}
