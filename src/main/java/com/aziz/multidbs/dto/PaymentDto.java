package com.aziz.multidbs.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentDto {
    private String userId;
    private String orderId;
    private boolean isPaymentSuccessful;
}
