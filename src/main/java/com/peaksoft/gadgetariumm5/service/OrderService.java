package com.peaksoft.gadgetariumm5.service;

import com.peaksoft.gadgetariumm5.dto.*;
import com.peaksoft.gadgetariumm5.exception.NotFoundException;
import com.peaksoft.gadgetariumm5.model.entity.Card;
import com.peaksoft.gadgetariumm5.model.entity.Order;
import com.peaksoft.gadgetariumm5.model.entity.User;
import com.peaksoft.gadgetariumm5.model.enums.Delivery;
import com.peaksoft.gadgetariumm5.model.enums.Payment;
import com.peaksoft.gadgetariumm5.model.enums.Role;
import com.peaksoft.gadgetariumm5.model.enums.Status;
import com.peaksoft.gadgetariumm5.repository.CardRepository;
import com.peaksoft.gadgetariumm5.repository.OrderRepository;
import com.peaksoft.gadgetariumm5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Order> getAllOrders(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("There is no user by email = " + email));
        if (user.getRole().equals(Role.ADMIN)) {
            return orderRepository.findAll();
        } else if (user.getRole().equals(Role.USER)) {
            return user.getOrders();
        } else {
            // TODO: 14.07.2023 Don't return null, you have to throw modification Exception 
            return null;
        }
    }

    public OrderResponse addOrder(String email, OrderRequest orderRequest) {
        Random random = new Random();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("There is no user by email = " + email));
        
        List<Order> orderList = new ArrayList<>();
        
        Order.builder()
                // TODO: 14.07.2023 Map to Builder 
                .build();
        
        Order order = new Order();
        order.setId(order.getId());
        order.setDelivery(Delivery.valueOf(orderRequest.getDeliveryOptions()));
        order.setFistName(orderRequest.getFirstName());
        order.setLastName(orderRequest.getLastName());
        order.setEmail(orderRequest.getEmail());
        order.setPhoneNumber(order.getPhoneNumber());
        if (order.getDelivery().equals(Delivery.DELIVERY)) {
            order.setAddress(orderRequest.getAddress());
        } else {
            order.setAddress(null);
        }
        order.setApplicationNumber(String.valueOf(random.nextInt(1000000, 9000000)));
        order.setStatus(Status.IN_PROCESSING);
        order.setCreated(LocalDate.now().atStartOfDay());
        order.setUser(user);
        orderList.add(order);
        user.setOrders(orderList);
        userRepository.save(user);
        orderRepository.save(order);
        return mapToOrderResponse(order);

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

    public PaymentResponse paymentMethod(Long orderId, String email, PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("There is no order by id = " + orderId));
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("There is no user by email = " + email));
        Card card = new Card();
        
        Card.builder()
                // TODO: 14.07.2023 Map to Builder 
                .build();
        
        if (paymentRequest.getPaymentMethod()
                .equalsIgnoreCase(String.valueOf(Payment.BY_CARD_ONLINE))) {
            
            card.setCardNumber(paymentRequest.getCardNumber());
            card.setMonth(paymentRequest.getMonth());
            card.setYear(paymentRequest.getYear());
            card.setCvc(passwordEncoder.encode(paymentRequest.getCvc()));
            card.setUser(user);
            List<Card> cards = new ArrayList<>();
            cards.add(card);
            user.setCards(cards);
            cardRepository.save(card);
            order.setPayment(Payment.BY_CARD_ONLINE);
            userRepository.save(user);
            orderRepository.save(order);
            return mapToCardResponse(card, String.valueOf(Payment.BY_CARD_ONLINE));
        } else if (paymentRequest.getPaymentMethod()
                        .equalsIgnoreCase(String.valueOf(Payment.BY_CARD_UPON_RECEIPT))) {
            return mapToCardResponse(card, String.valueOf(Payment.BY_CARD_UPON_RECEIPT));
        }
        return mapToCardResponse(card, String.valueOf(Payment.IN_CASH_UPON_RECEIPT));
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

    public OrderOverviewResponse orderOverview(Long orderId, String email) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("There is no order by id = " + orderId));
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("There is no user by email = " + email));
        List<Order> orderList = new ArrayList<>();
        // TODO: 14.07.2023 User only Builder pattern 
        order.setTotal(user.getBasket().getGrandTotal());
        orderList.add(order);
        user.setOrders(orderList);
        order.setUser(user);
        orderRepository.save(order);
        return mapToOrderOverview(order);

    }
    
    private User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("There is no user by email = " + email));
    }
    // TODO: 14.07.2023 create method for findById on orderRepository 
    

    public OrderResponse updateOrderDelivery(Long orderId, String email, OrderRequest orderRequest) {
        User user = findByEmail(email);
        // TODO: 14.07.2023 use private findByEmail method 
        Order order1 = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("There is no order by id = " + orderId));
        List<Order> orderList = new ArrayList<>();

        // TODO: 14.07.2023 Use Builder pattern 
        order1.setId(order1.getId());
        order1.setDelivery(Delivery.valueOf(orderRequest.getDeliveryOptions()));
        order1.setFistName(orderRequest.getFirstName());
        order1.setLastName(orderRequest.getLastName());
        order1.setEmail(orderRequest.getEmail());
        order1.setPhoneNumber(orderRequest.getPhoneNumber());
        if (order1.getDelivery().equals(Delivery.DELIVERY)) {
            order1.setAddress(orderRequest.getAddress());
        } else {
            order1.setAddress("");
        }
        order1.setApplicationNumber(order1.getApplicationNumber());
        order1.setStatus(Status.IN_PROCESSING);
        order1.setCreated(LocalDate.now().atStartOfDay());
        order1.setUser(user);
        orderList.add(order1);
        user.setOrders(orderList);
        userRepository.save(user);
        orderRepository.saveAndFlush(order1);
        return mapToOrderResponse(order1);
    }
    public PaymentResponse updateOrderPayment(Long orderId,String email,PaymentRequest paymentRequest){
        // TODO: 14.07.2023 Use private findByEmail method 
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("There is no order by id = " + orderId));
        // TODO: 14.07.2023 The same situation 
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("There is no user by email = " + email));
        Card card = new Card();
        // TODO: 14.07.2023 Use Builder pattern 
        if (paymentRequest.getPaymentMethod().equalsIgnoreCase(String.valueOf(Payment.BY_CARD_ONLINE))) {
            card.setCardNumber(paymentRequest.getCardNumber());
            card.setMonth(paymentRequest.getMonth());
            card.setYear(paymentRequest.getYear());
            card.setCvc(passwordEncoder.encode(paymentRequest.getCvc()));
            card.setUser(user);
            List<Card> cards = new ArrayList<>();
            cards.add(card);
            user.setCards(cards);
            cardRepository.saveAndFlush(card);
            order.setPayment(Payment.BY_CARD_ONLINE);
            userRepository.saveAndFlush(user);
            orderRepository.saveAndFlush(order);
            return mapToCardResponse(card, String.valueOf(Payment.BY_CARD_ONLINE));
        } else if (paymentRequest.getPaymentMethod().equalsIgnoreCase(String.valueOf(Payment.BY_CARD_UPON_RECEIPT))) {
            return mapToCardResponse(card, String.valueOf(Payment.BY_CARD_UPON_RECEIPT));
        }
        return mapToCardResponse(card, String.valueOf(Payment.IN_CASH_UPON_RECEIPT));
    }
    

    public OrderOverviewResponse mapToOrderOverview(Order order) {
        return OrderOverviewResponse.builder()
                .total(order.getTotal())
                .delivery(String.valueOf(order.getDelivery()))
                .payment(String.valueOf(order.getPayment())).build();
    }

    public OrderFinishResponse orderFinishResponse(Long orderId, String email) {
        // TODO: 14.07.2023 Use private findByEmail method
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("There is no user by email = " + email));
        // TODO: 14.07.2023 Same thing
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("There is no order by id = " + orderId));
        // TODO: 14.07.2023 Use Builder pattern
        order.setCreated(order.getCreated());
        order.setEmail(order.getEmail());
        List<Order> orderList = new ArrayList<>();
        user.setOrders(orderList);
        order.setUser(user);
        userRepository.save(user);
        orderRepository.save(order);
        return mapFinishResponse(order);
    }

    public OrderFinishResponse mapFinishResponse(Order order) {
        return OrderFinishResponse.builder()
                .applicationNumber(order.getApplicationNumber())
                .status(order.getStatus().toString())
                .localDate(order.getCreated().toLocalDate())
                .email(order.getEmail()).build();
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}