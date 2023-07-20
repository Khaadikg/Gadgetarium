package com.peaksoft.gadgetariumm5.service;

import com.peaksoft.gadgetariumm5.dto.BasketResponse;
import com.peaksoft.gadgetariumm5.dto.ProductResponse;
import com.peaksoft.gadgetariumm5.exception.NotFoundException;
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
    @Autowired
    private ProductService productService;

    public BasketResponse getAllBasket(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User not found email " + email)
        );
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
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User not found email " + email)
        );
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Product not found by id " + id)
        );
        for (int i = 0; i < user.getBasket().getProductList().size(); i++) {
            if (Objects.equals(id, user.getBasket().getProductList().get(i).getId())) {
                user.getBasket().getProductList().get(i).setBasketList(null);
                product.setInStock(product.getInStock() + 1);
                productRepository.save(user.getBasket().getProductList().get(i));
                user.getBasket().getProductList().remove(user.getBasket().getProductList().get(i));
                remove(product.getId(), user);
            }
        }
    }

    public void remove(Long id, User user) {
        List<ProductAmount> products = user.getBasket().getProductAmountList();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(id)) {
                Product product = productRepository.findById(id).orElseThrow(
                        () -> new NotFoundException("Product not found by id " + id)
                );
                product.setProductAmountList(null);
                products.get(i).setProduct(null);
                productRepository.save(product);
                Long productAmountId = products.get(i).getId();
                products.remove(products.get(i));
                productAmountRepository.deleteById(productAmountId);
            }
        }
    }

    public ProductResponse addToBasket(Long productId, String email) {
        List<ProductAmount> productAmountList = new ArrayList<>();
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("product not found by id = " + productId)
        );
        User user = userRepository.findByEmail(email).orElseThrow(

                () -> new NotFoundException("User not found email " + email));
        if (!user.getBasket().getProductList().contains(product) && product.getInStock() >= 1) {

            ProductAmount productAmount = new ProductAmount();
            productAmountList.add(productAmount);
            user.getBasket().setProductAmountList(productAmountList);
            productAmount.setBasket(user.getBasket());

            List<Product> productList = new ArrayList<>();
            productList.add(product);

            user.getBasket().getProductList().add(product);
            product.getBasketList().add(user.getBasket());
            product.setProductAmountList(productAmountList);

            addToProductAmount(user.getId(), product, user.getBasket(), productAmount, productList);
            basketRepository.save(user.getBasket());
            product.setInStock(product.getInStock() - 1);
            productRepository.save(product);
        } else if (product.getInStock() == 0) {
            throw new NotFoundException("This product out of stock!");
        } else {
            addAmount(product.getId(), user.getBasket());
        }
        return productService.mapToResponse(product);
    }

    public void addAmount(Long productId, Basket basket) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("Product not found by id " + productId)
        );
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
                product.setInStock(product.getInStock() - 1);
                productAmountRepository.save(productAmountList.get(i));
            }
        }
    }

    private void addToProductAmount(Long userId, Product product, Basket basket, ProductAmount productAmount, List<Product> productList) {
        productAmount.setAmount(productAmount.getAmount() + 1);
        productAmount.setTotal(product.getPrice() * productAmount.getAmount());
        productAmount.setDiscount(productAmount.getTotal() / 100 * product.getDiscountProduct());
        productAmount.setGrandTotal(productAmount.getTotal() - productAmount.getDiscount());
        productAmount.setProduct(product);
        productAmount.setProductId(product.getId());
        productAmount.setUserId(userId);
        productAmount.setBasket(basket);
        basketRepository.save(basket);
    }

    public void minus(Long productId, String email) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("Product not found by id " + productId)
        );
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User not found email " + email)
        );
        List<ProductAmount> productAmountList = user.getBasket().getProductAmountList();
        for (int i = 0; i < productAmountList.size(); i++) {
            if (Objects.equals(productId, productAmountList.get(i).getProductId()) && productAmountList.get(i).getAmount() > 1) {
                productAmountList.get(i).setAmount(productAmountList.get(i).getAmount() - 1);
                productAmountList.get(i).setTotal(product.getPrice() * productAmountList.get(i).getAmount() );
                productAmountList.get(i).setDiscount(productAmountList.get(i).getTotal() / 100 * product.getDiscountProduct());
                productAmountList.get(i).setGrandTotal(productAmountList.get(i).getTotal() - productAmountList.get(i).getDiscount());
                product.setInStock(product.getInStock() + 1);
                productRepository.save(product);
                productAmountRepository.save(productAmountList.get(i));
            } else if (Objects.equals(productId, productAmountList.get(i).getProductId()) && productAmountList.get(i).getAmount() == 1) {
                deleteProduct(productId, email);
            }
        }
    }

}
