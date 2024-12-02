package com.aziz.multidbs.service;

import com.aziz.multidbs.domain.users.User;
import com.aziz.multidbs.domain.orders.Order;
import com.aziz.multidbs.domain.payments.Payment;
import com.aziz.multidbs.dto.PaymentDto;
import com.aziz.multidbs.repository.users.UserRepository;
import com.aziz.multidbs.repository.orders.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@Slf4j
public class PaymentServiceTest {

    @Container
    static PostgreSQLContainer<?> databaseServer =
            new PostgreSQLContainer<>("postgres:17-alpine")
                    .withCopyFileToContainer(
                            MountableFile.forClasspathResource(
                                    "init.sql"), "/docker-entrypoint-initdb.d/"
                    ).withAccessToHost(true)
                    .withExposedPorts(5432);

    @BeforeAll
    static void setUp() {
        System.setProperty("DB_HOST_PORT", "localhost:" + databaseServer.getMappedPort(5432));
    }

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testCheckout() {
        log.debug("Testing checkout");
        final String name = "Aziz";

        log.debug("Inserting user used for the test");
        User user = new User();
        user.setName(name);
        user = userRepository.saveAndFlush(user);

        log.debug("Inserting order used for the test");
        Order order = new Order();
        order.setUserId(user.getId());
        order = orderRepository.saveAndFlush(order);

        final boolean isPaymentSuccessful = true;
        PaymentDto paymentDto = PaymentDto
                .builder()
                .userId(user.getId())
                .orderId(order.getId())
                .isPaymentSuccessful(isPaymentSuccessful)
                .build();

        Payment payment = paymentService.checkout(paymentDto);

        assertThat(payment).isNotNull();
        assertThat(payment.getId()).isNotNull();
        assertThat(payment.getUserId()).isEqualTo(user.getId());
        assertThat(payment.getOrderId()).isEqualTo(order.getId());
        assertThat(payment.isSuccessful()).isEqualTo(isPaymentSuccessful);
    }

}
