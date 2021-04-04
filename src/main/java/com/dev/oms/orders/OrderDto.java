package com.dev.oms.orders;


import java.time.LocalDateTime;
import static org.springframework.beans.BeanUtils.copyProperties;

public class OrderDto {

    private Long seq;

    private Long productId;

    private ReviewDto review;

    private OrderState state;

    private String requestMessage;

    private String rejectMessage;

    private LocalDateTime completedAt;

    private LocalDateTime rejectedAt;

    private LocalDateTime createAt;

    public OrderDto(Order source) {
        copyProperties(source, this);
        this.requestMessage = source.getRequestMsg();
        this.rejectMessage = source.getRejectMsg();
        this.productId = source.getProductSeq();
        if(source.getReview() != null) this.review = new ReviewDto(source.getReview());

    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setReview(ReviewDto review) {
        this.review = review;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}




