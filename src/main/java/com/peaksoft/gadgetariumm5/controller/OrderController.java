package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.*;
import com.peaksoft.gadgetariumm5.service.EmailService;
import com.peaksoft.gadgetariumm5.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Ordering.", description = "We can order")
@PreAuthorize("hasAuthority('USER')")

public class OrderController {
    private final OrderService orderService;
    private final EmailService emailService;

    @GetMapping("getAll")
    @Operation(summary = "Get all orders.", description = "Only the admin can see all orders," +
            "the user can only see his own orders.")
    @PreAuthorize("hasAuthority('ADMIN,USER')")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/add")
    @Operation(summary = "Add order.", description = "User can the order.")
    public OrderResponse addOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.addOrder(orderRequest);
    }

    @PostMapping("/payment")
    @Operation(summary = "Order payment .", description = "User can order payment.")
    public PaymentResponse payment(@RequestParam Long orderId,
                                   @RequestBody PaymentRequest paymentRequest) {
        return orderService.paymentMethod(orderId, paymentRequest);
    }

    @PostMapping("/orderOverview")
    @Operation(summary = " Order overview.", description = "User can order overview.")
    public OrderOverviewResponse orderOverview(@RequestParam Long orderId) {
        return orderService.orderOverview(orderId);

    }

    @PostMapping("/finish")
    @Operation(summary = " Order checkout.", description = "User can order checkout.")
    public OrderFinishResponse orderFinish(@RequestParam Long orderId) {
        System.out.println("HEllo java");
        emailService.sendOrderMassage(orderId);
        System.out.println("hahahah");
        return orderService.orderFinishResponse(orderId);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete order.", description = "Admin can delete the order.")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable("id") Long orderId) {
        orderService.deleteOrder(orderId);
        return "Successfully deleted order with id : " + orderId;
    }
}

