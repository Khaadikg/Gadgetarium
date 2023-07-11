package com.peaksoft.gadgetariumm5.service;

import com.peaksoft.gadgetariumm5.dto.BasketResponse;
import com.peaksoft.gadgetariumm5.model.entity.Basket;
import com.peaksoft.gadgetariumm5.model.entity.Product;
import com.peaksoft.gadgetariumm5.model.entity.ProductAmount;
import com.peaksoft.gadgetariumm5.model.entity.User;
import com.peaksoft.gadgetariumm5.repository.BasketRepository;
import com.peaksoft.gadgetariumm5.repository.ProductAmountRepository;
import com.peaksoft.gadgetariumm5.repository.ProductRepository;
import com.peaksoft.gadgetariumm5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductAmountRepository productAmountRepository;

    public BasketResponse getAllBasket(String email) {
        User user = userRepository.findByEmail(email).get();
        Basket basket = user.getBasket();
        getProductAmount(basket);
        return mapToResponse(basket);

    }

    private void getProductAmount(Basket basket) {
        int amount = 0;
        double grandDiscount = 0d;
        double total = 0d;
        double grandTotal = 0d;
        for (int i = 0; i < basket.getProductAmountList().size(); i++) {
            amount += basket.getProductAmountList().get(i).getAmount();
            total += basket.getProductAmountList().get(i).getTotal();
            grandDiscount += basket.getProductAmountList().get(i).getDiscount();
            grandTotal += basket.getProductAmountList().get(i).getGrandTotal();
            basket.setAmount(amount);
            basket.setDiscount(grandDiscount);
            basket.setTotal(total);
            basket.setGrandTotal(grandTotal);
            basketRepository.save(basket);
        }
    }

    public BasketResponse mapToResponse(Basket basket) {
        BasketResponse basketResponse = new BasketResponse();
        basketResponse.setId(basket.getId());
        basketResponse.setAmount(basket.getAmount());
        basketResponse.setDiscount(basket.getDiscount());
        basketResponse.setTotal(basket.getTotal());
        basketResponse.setGrandTotal(basket.getGrandTotal());
        basketResponse.setProductList(basket.getProductList());
        basketResponse.setProductAmountList(basket.getProductAmountList());
        return basketResponse;
    }

    public void deleteProduct(Long id, String email) {
        User user = userRepository.getUserByUsername(email);
        for (int i = 0; i < user.getBasket().getProductList().size(); i++) {
            if (Objects.equals(id, user.getBasket().getProductList().get(i).getId())) {
                user.getBasket().getProductList().get(i).setBasketList(null);
                productRepository.save(user.getBasket().getProductList().get(i));
                user.getBasket().getProductList().remove(user.getBasket().getProductList().get(i));

            } else {
                System.out.println("not");
            }
        }
    }
    public void remove(Long id, User user){
        for (int i = 0; i < user.getBasket().getProductAmountList().size(); i++) {
            if (user.getBasket().getProductAmountList().get(i).getId() == id){
                user.getBasket().getProductAmountList().remove(user.getBasket().getProductAmountList().get(i));
            }
        }
    }

//    public void addTest(Long prd, String email){
//        Product product = productRepository.getById(prd);
//        User user = userRepository.findByEmail(email).get();
//
//        Optional<Basket> basketOptional = basketRepository.findByUserId(user.getId(),prd);
//
//        if (basketOptional.isPresent()){
//            basketOptional.get().amountPlus();
//            basketRepository.save(basketOptional.get());
//        }else {
//            Basket basket = new Basket();
//            basket.setProduct(product);
//            basket.setUser(user);
//            basketRepository.save(basket);
//        }
//    }


    //    @PostConstruct
//    void ho(){
//        addTest(1L,"uson@gmail.com");
//    }
    public void addToBasket(Long productId, String email) {
        Product product = productRepository.getById(productId);
        User user = userRepository.findByEmail(email).get();
        if (user.getBasket().getProductAmountList().size() < 1) {
            ProductAmount productAmount = new ProductAmount();
            List<ProductAmount> productAmountList = new ArrayList<>();
            user.getBasket().setProductAmountList(productAmountList);
            productAmount.setBasket(user.getBasket());
            productAmountList.add(productAmount);
            List<Product> productList = new ArrayList<>();
            productList.add(product);

            List<Basket> basketList = new ArrayList<>();
            basketList.add(user.getBasket());

            user.getBasket().setProductList(productList);
            product.setBasketList(basketList);

            addToProductAmount(user.getId(), product, user.getBasket(), productAmount, productList);
            basketRepository.save(user.getBasket());
            userRepository.save(user);
            productRepository.save(product);
        } else {
            addAmount(product.getId(), user.getBasket());
        }
    }

    public void addAmount(Long productId, Basket basket) {
        for (int i = 0; i < basket.getProductAmountList().size(); i++) {
            if (basket.getProductAmountList().get(i).getProductId() == productId) {
                basket.getProductAmountList().get(i).setAmount(basket.getProductAmountList().get(i).getAmount() + 1);
                basket.getProductAmountList().get(i).setTotal(basket.getProductAmountList().get(i).getTotal() * basket.getProductAmountList().get(i).getAmount());
                basket.getProductAmountList().get(i).setDiscount(basket.getProductAmountList().get(i).getDiscount() + basket.getProductAmountList().get(i).getDiscount());
                basket.getProductAmountList().get(i).setGrandTotal(basket.getProductAmountList().get(i).getTotal() - basket.getProductAmountList().get(i).getDiscount());
                productAmountRepository.save(basket.getProductAmountList().get(i));
            }
        }
    }

    private void addToProductAmount(Long userId, Product product, Basket basket, ProductAmount productAmount, List<Product> productList) {
        productAmount.setAmount(productAmount.getAmount() + 1);
        productAmount.setTotal(product.getPrice() * productAmount.getAmount());
        productAmount.setDiscount(productAmount.getTotal() / 100 * product.getDiscount());
        productAmount.setGrandTotal(productAmount.getTotal() - productAmount.getDiscount());
        productAmount.setProductList(productList);
        productAmount.setProductId(product.getId());
        productAmount.setUserId(userId);
        productAmount.setBasket(basket);
        basketRepository.save(basket);
//            }
//        }
    }

    public void productAmount(Long productId, String email) {
        User user = userRepository.getUserByUsername(email);
        for (int i = 0; i < user.getBasket().getProductAmountList().size(); i++) {
            if (Objects.equals(productId, user.getBasket().getProductAmountList().get(i).getProductId())) {
                user.getBasket().getProductAmountList().get(i).setAmount(user.getBasket().getProductAmountList().get(i).getAmount() + 1);
            }
        }
    }public void productAmount2(Long productId , String email) {
        User user = userRepository.getUserByUsername(email);
        for (int i = 0; i < user.getBasket().getProductAmountList().size(); i++) {
            if (Objects.equals(productId, user.getBasket().getProductAmountList().get(i).getProductId())) {
                user.getBasket().getProductAmountList().get(i).setAmount(user.getBasket().getProductAmountList().get(i).getAmount() - 1);
            }
        }
    }

}
