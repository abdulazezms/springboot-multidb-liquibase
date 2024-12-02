package com.aziz.multidbs.repository.payments;

import com.aziz.multidbs.domain.payments.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}