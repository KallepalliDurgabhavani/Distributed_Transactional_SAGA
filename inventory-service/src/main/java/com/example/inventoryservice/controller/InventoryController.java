package com.example.inventoryservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@RestController
public class InventoryController {

    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    // FIX: quantity is required=true (was required=false — allowed null to slip through).
    // FIX: removed unreachable null checks; @RequestParam required=true enforces presence.
    @PostMapping("/inventory/reserve")
    public ResponseEntity<String> reserveInventory(@RequestParam UUID orderId, @RequestParam Integer quantity) {
        if (quantity > 100) {
            log.warn("Inventory reservation failed for Order: {} - Out of Stock", orderId);
            return ResponseEntity.badRequest().body("Inventory reservation failed: Out of Stock");
        }
        log.info("Inventory reserved for Order: {}, Quantity: {}", orderId, quantity);
        return ResponseEntity.ok("Inventory reserved successfully");
    }

    @PostMapping("/inventory/release")
    public ResponseEntity<String> releaseInventory(@RequestParam UUID orderId) {
        log.info("Releasing inventory for Order: {}", orderId);
        return ResponseEntity.ok("Inventory released successfully");
    }
}

