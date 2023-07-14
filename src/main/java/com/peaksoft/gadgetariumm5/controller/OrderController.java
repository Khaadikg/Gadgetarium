package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.*;
import com.peaksoft.gadgetariumm5.model.entity.Order;
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
    private  final EmailService emailService;

    @GetMapping("getAll")
    public List<Order> getAllOrders(@RequestParam String email) {
        return orderService.getAllOrders(email);
    }

    @PostMapping("/add")
    public OrderResponse addOrder(@RequestParam String email, @RequestBody OrderRequest orderRequest) {
        return orderService.addOrder(email, orderRequest);
    }

    @PostMapping("/payment")
    public PaymentResponse payment(@RequestParam Long orderId,
                                   @RequestParam String email,
                                   @RequestBody PaymentRequest paymentRequest) {
        return orderService.paymentMethod(orderId, email, paymentRequest);
    }

    @PostMapping("/orderOverview")
    public OrderOverviewResponse orderOverview(@RequestParam Long orderId, @RequestParam String email) {
        return orderService.orderOverview(orderId, email);

    }

    @PostMapping("/finish")
    public OrderFinishResponse orderFinish(@RequestParam Long orderId, @RequestParam String email) {
        emailService.sendOrderMassage(email,orderId);
        return orderService.orderFinishResponse(orderId,email);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long orderId ) {
        orderService.deleteOrder(orderId);
        return "Successfully deleted order with id : "+orderId;
    }
}

