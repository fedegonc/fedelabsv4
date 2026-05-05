package com.fedelabsv4.controller;

import com.fedelabsv4.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    
    @Autowired
    private StripeService stripeService;
    
    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody Map<String, Object> data) {
        try {
            Long amount = Long.valueOf(data.get("amount").toString());
            String currency = data.get("currency").toString();
            
            Map<String, String> response = stripeService.createPaymentIntentResponse(amount, currency);
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/create-checkout-session")
    public ResponseEntity<?> createCheckoutSession(@RequestBody Map<String, Object> data) {
        try {
            String productName = data.get("productName").toString();
            Long amount = Long.valueOf(data.get("amount").toString());
            String currency = data.get("currency").toString();
            String successUrl = data.get("successUrl").toString();
            String cancelUrl = data.get("cancelUrl").toString();
            
            Session session = stripeService.createCheckoutSession(
                productName, amount, currency, successUrl, cancelUrl
            );
            
            Map<String, String> response = new HashMap<>();
            response.put("sessionId", session.getId());
            response.put("url", session.getUrl());
            
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, 
                                                @RequestHeader("Stripe-Signature") String sigHeader) {
        return ResponseEntity.ok("Webhook received");
    }
}
