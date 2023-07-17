package com.peaksoft.gadgetariumm5.service;

import com.peaksoft.gadgetariumm5.dto.*;
import com.peaksoft.gadgetariumm5.dto.mapToResponse.MapToResponse;
import com.peaksoft.gadgetariumm5.exception.NotFoundException;
import com.peaksoft.gadgetariumm5.model.entity.Card;
import com.peaksoft.gadgetariumm5.model.entity.Order;
import com.peaksoft.gadgetariumm5.model.entity.User;
import com.peaksoft.gadgetariumm5.model.enums.Delivery;
import com.peaksoft.gadgetariumm5.model.enums.Payment;
import com.peaksoft.gadgetariumm5.model.enums.Role;
import com.peaksoft.gadgetariumm5.repository.CardRepository;
import com.peaksoft.gadgetariumm5.repository.OrderRepository;
import com.peaksoft.gadgetariumm5.repository.UserRepository;
import com.sun.mail.imap.protocol.INTERNALDATE;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    private final MapToResponse mapToOrder = new MapToResponse();


    public List<OrderResponse> getAllOrders() {
        User user = getAuthUser();
        List<OrderResponse> orderResponses = new ArrayList<>();
        if (user.getRole().equals(Role.ADMIN)) {
            for (Order order : orderRepository.findAll()) {
                orderResponses.add(mapToOrder.mapToOrderResponse(order));
                return orderResponses;
            }

        } else if (user.getRole().equals(Role.USER)) {
            for (Order order : user.getOrders()) {
                orderResponses.add(mapToOrder.mapToOrderResponse(order));
            }
        }
        return orderResponses;
    }

    public OrderResponse addOrder(OrderRequest orderRequest) {
        User user = getAuthUser();
        List<Order> orderList = new ArrayList<>();
        Order order = mapToOrder.mapToEntity(orderRequest);
        if (order.getDelivery().equals(Delivery.COURIER_DELIVERY)) {
            order.setAddress(orderRequest.getAddress());
        } else {
            order.setAddress("");
        }
        order.setUser(user);
        orderList.add(order);
        user.setOrders(orderList);

        orderRepository.save(order);
        userRepository.save(user);
        return mapToOrder.mapToOrderResponse(order);

    }

    public PaymentResponse paymentMethod(Long orderId, PaymentRequest paymentRequest) {
        User user = getAuthUser();
        Order order = findByOrderId(orderId);

        if (!user.getOrders().contains(order)) {
            throw new NotFoundException("You dont have such order!");
        }

        PaymentResponse paymentResponse;

        List<Card> cards = new ArrayList<>();
        Card card = new Card();
        if (paymentRequest.getPaymentMethod()
                .equalsIgnoreCase(String.valueOf(Payment.BY_CARD_ONLINE))) {
            card = Card.builder()
                    .cardNumber(paymentRequest.getCardNumber())
                    .month(paymentRequest.getMonth())
                    .year(paymentRequest.getYear())
                    .cvc(passwordEncoder.encode(paymentRequest.getCvc()))
                    .build();
            card.setUser(user);
            cards.add(card);
            user.setCards(cards);
            order.setPayment(Payment.valueOf((paymentRequest.getPaymentMethod())));
            userRepository.save(user);
            orderRepository.save(order);
            paymentResponse = mapToOrder.mapToCardResponse(card, String.valueOf(Payment.BY_CARD_ONLINE));

        } else if (paymentRequest.getPaymentMethod()
                .equalsIgnoreCase(String.valueOf(Payment.BY_CARD_UPON_RECEIPT))) {
            order.setPayment(Payment.valueOf((paymentRequest.getPaymentMethod())));
            orderRepository.save(order);
            paymentResponse = mapToOrder.mapToCardResponse(card, String.valueOf(order.getPayment()));
        } else {

            order.setPayment(Payment.valueOf(paymentRequest.getPaymentMethod()));

            orderRepository.saveAndFlush(order);
            paymentResponse = mapToOrder.mapToCardResponse(card, String.valueOf(order.getPayment()));
        }
        return paymentResponse;
    }

    public OrderOverviewResponse orderOverview(Long orderId) {
        User user = getAuthUser();
        Order order = findByOrderId(orderId);
        List<Order> orderList = new ArrayList<>();
        // order.setTotal(user.getBasket().getGrandTotal());
        orderList.add(order);
        user.setOrders(orderList);
        order.setUser(user);
        orderRepository.saveAndFlush(order);
        return mapToOrder.mapToOrderOverview(order);

    }

    public OrderFinishResponse orderFinishResponse(Long orderId) {
        User user = getAuthUser();
        Order order = null;
        for (int i = 0; i < user.getOrders().size(); i++) {
            if (user.getOrders().get(i).getId().equals(orderId)) {
                order = user.getOrders().get(i);
            }
        }
        assert order != null;
        return mapToOrder.mapFinishResponse(order);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order findByOrderId(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("There is no order by id = " + orderId));
    }

    public User getAuthUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new NotFoundException("You didn't  sign in!");
        }
        return user;
    }

}