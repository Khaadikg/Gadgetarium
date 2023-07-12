package com.peaksoft.gadgetariumm5.service;

import com.peaksoft.gadgetariumm5.dto.DiscountRequest;
import com.peaksoft.gadgetariumm5.dto.DiscountResponse;
import com.peaksoft.gadgetariumm5.model.entity.Discount;
import com.peaksoft.gadgetariumm5.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;
    public List<DiscountResponse> getAllDiscounts(){
        List<Discount> discounts = discountRepository.findAll();
        List<DiscountResponse> responses = new ArrayList<>();
        for (Discount discount : discounts){
            responses.add(mapToResponse(discount));
        }
        return responses;
    }
    public DiscountResponse create(DiscountRequest discountRequest){
        Discount discount = mapToEntity(discountRequest);
        discountRepository.save(discount);
        return mapToResponse(discount);
    }
    public DiscountResponse update(Long id,DiscountRequest discountRequest){
        Discount discount = new Discount();
        discount.setDateOfStart(discountRequest.getDateOfStart());
        discount.setDateOfFinish(discountRequest.getDateOfFinish());
        discount.setPercentage(discountRequest.getPercentage());
        discountRepository.save(discount);
        return mapToResponse(discount);
    }
    public DiscountResponse getById(Long id){
        Discount discount = discountRepository.findById(id).get();
        return mapToResponse(discount);
    }

    public Discount mapToEntity(DiscountRequest request){
        Discount discount = new Discount();
        discount.setDateOfStart(request.getDateOfStart());
        discount.setDateOfFinish(request.getDateOfFinish());
        discount.setPercentage(request.getPercentage());
        return discount;
    }
    public DiscountResponse mapToResponse(Discount discount){
        DiscountResponse discountResponse = new DiscountResponse();
        discountResponse.setId(discount.getId());
        discountResponse.setDateOfStart(discount.getDateOfStart());
        discountResponse.setDateOfFinish(discount.getDateOfFinish());
        discountResponse.setPercentage(discount.getPercentage());
        return discountResponse;
    }
}
