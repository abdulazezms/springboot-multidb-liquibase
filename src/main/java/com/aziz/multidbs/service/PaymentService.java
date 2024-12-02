package com.aziz.multidbs.service;

import com.aziz.multidbs.domain.orders.Order;
import com.aziz.multidbs.domain.payments.Payment;
import com.aziz.multidbs.domain.users.User;
import com.aziz.multidbs.dto.PaymentDto;
import com.aziz.multidbs.exception.ResourceNotFoundException;
import com.aziz.multidbs.repository.orders.OrderRepository;
import com.aziz.multidbs.repository.payments.PaymentRepository;
import com.aziz.multidbs.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    /*
        To coordinate transactions across multiple databases within the scope of a single @Transactional annotation,
        you would need to integrate a JTA transaction manager that can manage distributed transactions.
    */
    public Payment checkout(PaymentDto paymentDto) {
        log.debug("Checkout started");
        boolean isPaymentSuccessful = paymentDto.isPaymentSuccessful();

        User user = userRepository.findById(paymentDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: %s not found"));
        log.debug("User retrieved successfully from users database");

        Order order = orderRepository.findById(paymentDto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order with ID: %s not found"));
        log.debug("Order retrieved successfully from orders database");

        Payment payment = new Payment();
        payment.setUserId(user.getId());
        payment.setOrderId(order.getId());
        payment.setSuccessful(isPaymentSuccessful);
        Payment persisted = paymentRepository.save(payment);
        log.debug("Payment saved successfully in the payments database");

        return persisted;
    }
}
