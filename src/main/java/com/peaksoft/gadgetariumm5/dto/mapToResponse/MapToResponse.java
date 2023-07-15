package com.peaksoft.gadgetariumm5.dto.mapToResponse;

import com.peaksoft.gadgetariumm5.dto.*;
import com.peaksoft.gadgetariumm5.model.entity.Card;
import com.peaksoft.gadgetariumm5.model.entity.Order;
import com.peaksoft.gadgetariumm5.model.enums.Delivery;
import com.peaksoft.gadgetariumm5.model.enums.Status;

import java.time.LocalDate;
import java.util.Random;

public class MapToResponse {

    public Order mapToEntity(OrderRequest orderRequest) {
        Random random = new Random();

        return Order.builder()
                .delivery(Delivery.valueOf(orderRequest.getDeliveryOptions()))
                .fistName(orderRequest.getFirstName())
                .lastName(orderRequest.getLastName())
                .email(orderRequest.getEmail())
                .phoneNumber(orderRequest.getPhoneNumber())
                .status(Status.PENDING)
                .created(LocalDate.now().atStartOfDay())
                .applicationNumber(String.valueOf(random.nextInt(1000000, 9000000)))


                .build();
    }

    public OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .delivery(order.getDelivery())
                .fistName(order.getFistName())
                .lastName(order.getLastName())
                .email(order.getEmail()).
                address(order.getAddress())
                .build();
    }

    public PaymentResponse mapToCardResponse(Card card, String payment) {
        return PaymentResponse.builder()
                .id(card.getId())
                .payment(payment)
                .cardNumber(card.getCardNumber())
                .month(card.getMonth())
                .year(card.getYear())
                .build();

    }

    public OrderOverviewResponse mapToOrderOverview(Order order) {
        return OrderOverviewResponse.builder()
                .total(order.getTotal())
                .delivery(String.valueOf(order.getDelivery()))
                .payment(String.valueOf(order.getPayment())).build();
    }

    public OrderFinishResponse mapFinishResponse(Order order) {
        return OrderFinishResponse.builder()
                .applicationNumber(order.getApplicationNumber())
                .status(order.getStatus().toString())
                .localDate(order.getCreated().toLocalDate())
                .email(order.getEmail()).build();
    }

}
