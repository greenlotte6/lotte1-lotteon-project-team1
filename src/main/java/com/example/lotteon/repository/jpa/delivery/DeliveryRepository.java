package com.example.lotteon.repository.jpa.delivery;

import com.example.lotteon.entity.order.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer>,
    DeliveryCustomRepository {

}
