package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.*;
import com.peaksoft.gadgetariumm5.service.EmailService;
import com.peaksoft.gadgetariumm5.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final EmailService emailService;

    @GetMapping("getAll")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/add")
    public OrderResponse addOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.addOrder(orderRequest);
    }

    @PostMapping("/payment")
    public PaymentResponse payment(@RequestParam Long orderId,
                                   @RequestBody PaymentRequest paymentRequest) {
        return orderService.paymentMethod(orderId, paymentRequest);
    }

    @PostMapping("/orderOverview")
    public OrderOverviewResponse orderOverview(@RequestParam Long orderId) {
        return orderService.orderOverview(orderId);

    }

    @PostMapping("/finish")
    public OrderFinishResponse orderFinish(@RequestParam Long orderId) {
        emailService.sendOrderMassage(orderId);
        return orderService.orderFinishResponse(orderId);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long orderId) {
        orderService.deleteOrder(orderId);
        return "Successfully deleted order with id : " + orderId;
    }
}

