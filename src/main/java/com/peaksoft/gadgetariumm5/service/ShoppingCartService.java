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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final BasketRepository basketRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;
    private final ProductAmountRepository productAmountRepository;

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
        User user = userRepository.findByEmail(email).get();
        Product product = productRepository.findById(id).get();
        for (int i = 0; i < user.getBasket().getProductList().size(); i++) {
            if (Objects.equals(id, user.getBasket().getProductList().get(i).getId())) {
                user.getBasket().getProductList().get(i).setBasketList(null);
                productRepository.save(user.getBasket().getProductList().get(i));
                user.getBasket().getProductList().remove(user.getBasket().getProductList().get(i));
                remove(product.getId(),user);
            } else {
                System.out.println("not");
            }
        }
    }

    public void remove(Long id, User user) {
        List<ProductAmount> products = user.getBasket().getProductAmountList();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(id)) {
                Product product = productRepository.findById(id).get();
                product.setProductAmountList(null);
                products.get(i).setProduct(null);
                productRepository.save(product);
                Long productAmountId = products.get(i).getId();
                products.remove(products.get(i));
                productAmountRepository.deleteById(productAmountId);
            }
        }
    }

    public void addToBasket(Long productId, String email) {
        List<ProductAmount> productAmountList = new ArrayList<>();
        Product product = productRepository.getById(productId);
        User user = userRepository.findByEmail(email).get();
        if (!user.getBasket().getProductList().contains(product)) {

            ProductAmount productAmount = new ProductAmount();
            productAmountList.add(productAmount);
            user.getBasket().setProductAmountList(productAmountList);
            productAmount.setBasket(user.getBasket());

            List<Product> productList = new ArrayList<>();
            productList.add(product);

            user.getBasket().getProductList().add(product); // add product
            product.getBasketList().add(user.getBasket());
            product.setProductAmountList(productAmountList);

            addToProductAmount(user.getId(), product, user.getBasket(), productAmount, productList);
            basketRepository.save(user.getBasket());
        } else {
            addAmount(product.getId(), user.getBasket());
            System.out.println("xxxx");
        }
    }

    public void addAmount(Long productId, Basket basket) {
        List<ProductAmount> productAmountList = basket.getProductAmountList();
        for (int i = 0; i < productAmountList.size(); i++) {
            if (Objects.equals(productAmountList.get(i).getProductId(), productId)) {
                productAmountList.get(i).setAmount(productAmountList.get(i).getAmount() + 1);
                productAmountList.get(i).setTotal(productAmountList.get(i)
                        .getTotal() * productAmountList.get(i).getAmount());
                productAmountList.get(i).setDiscount(productAmountList.get(i)
                        .getDiscount() + productAmountList.get(i).getDiscount());
                productAmountList.get(i).setGrandTotal(productAmountList.get(i)
                        .getTotal() - productAmountList.get(i).getDiscount());
                productAmountRepository.save(productAmountList.get(i));
            }
        }
    }

    private void addToProductAmount(Long userId, Product product, Basket basket, ProductAmount productAmount, List<Product> productList) {
        productAmount.setAmount(productAmount.getAmount() + 1);
        productAmount.setTotal(product.getPrice() * productAmount.getAmount());
        productAmount.setDiscount(productAmount.getTotal() / 100 * product.getDiscount());
        productAmount.setGrandTotal(productAmount.getTotal() - productAmount.getDiscount());
        productAmount.setProduct(product);
        productAmount.setProductId(product.getId());
        productAmount.setUserId(userId);
        productAmount.setBasket(basket);
        basketRepository.save(basket);
    }

    public void minus(Long productId, String email) {
        Product product = productRepository.findById(productId).get();
        User user = userRepository.findByEmail(email).get();
        for (int i = 0; i < user.getBasket().getProductAmountList().size(); i++) {
            if (Objects.equals(productId, user.getBasket().getProductAmountList().get(i).getProductId())) {
                user.getBasket().getProductAmountList().get(i).setAmount(user.getBasket().getProductAmountList()
                        .get(i).getAmount() - 1);
                user.getBasket().getProductAmountList().get(i).setTotal(user.getBasket().getProductAmountList().get(i)
                        .getTotal() - product.getPrice());
                user.getBasket().getProductAmountList().get(i).setDiscount(user.getBasket().getProductAmountList()
                        .get(i).getTotal()/100 * product.getDiscount());
                user.getBasket().getProductAmountList().get(i).setGrandTotal(user.getBasket().getProductAmountList()
                        .get(i).getTotal() - user.getBasket().getProductAmountList().get(i).getDiscount());
                productAmountRepository.save(user.getBasket().getProductAmountList().get(i));
            }
        }
    }

}
