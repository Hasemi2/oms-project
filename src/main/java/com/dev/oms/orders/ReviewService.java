package com.dev.oms.orders;

import com.dev.oms.errors.ConflictReviewException;
import com.dev.oms.errors.NotFoundException;
import com.dev.oms.errors.OrderStateNotAllowException;
import com.dev.oms.products.Product;
import com.dev.oms.products.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Long createReview(Long orderId, Long userId, ReviewRequest request) {
        checkNotNull(orderId, "productId must be orderId");
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Could not found order for " + orderId));

        if (!order.getState().equals(OrderState.COMPLETED))
            throw new OrderStateNotAllowException("Could not write review for order " +  orderId + " because state(REQUESTED) is not allowed");

        if (order.getReview()!=null)
            throw  new ConflictReviewException("Could not write review for order "+ orderId + "  because have already written");

        Long id = reviewRepository.create(userId, order.getProductSeq(), request.getContent());

        Product product = productRepository
                .findById(order.getProductSeq()).orElseThrow(() -> new NotFoundException("Could not found product for " + order.getProductSeq()));

        product.plusReviewCount();
        productRepository.update(product);
        orderRepository.update(id, orderId);

        return id;

    }

}
