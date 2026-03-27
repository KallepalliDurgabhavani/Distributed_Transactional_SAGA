package com.example.paymentservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

@RestController
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    // FIX: amount uses BigDecimal (not Double) for precise monetary arithmetic.
    // FIX: amount is required=true — omitting it no longer bypasses the fraud check.
    @PostMapping("/payment")
    public ResponseEntity<String> processPayment(@RequestParam UUID orderId, @RequestParam BigDecimal amount) {
        log.info("Processing payment for Order: {}, Amount: {}", orderId, amount);

        if (amount.compareTo(BigDecimal.valueOf(1000)) > 0) {
            log.warn("Payment failed: Insufficient funds for Order: {}", orderId);
            return ResponseEntity.badRequest().body("Payment Failed: Insufficient funds for Order: " + orderId);
        }
        return ResponseEntity.ok("Payment Processed Successfully for Order: " + orderId);
    }

    @PostMapping("/payment/cancel")
    public ResponseEntity<String> cancelPayment(@RequestParam UUID orderId) {
        log.info("Cancelling payment for Order: {}", orderId);
        return ResponseEntity.ok("Payment Cancelled Successfully for Order: " + orderId);
    }
}
