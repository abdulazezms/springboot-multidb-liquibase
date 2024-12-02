package com.aziz.multidbs.repository.orders;

import com.aziz.multidbs.domain.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}