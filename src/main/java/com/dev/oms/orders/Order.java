package com.dev.oms.orders;

import java.time.LocalDateTime;



public class Order {

    private final Long seq;

    private Long userSeq;

    private Long productSeq;

    private Review review;

    private OrderState state;

    private String requestMsg;

    private String rejectMsg;

    private LocalDateTime completedAt;

    private LocalDateTime rejectedAt;

    private LocalDateTime createAt;

    public Long getSeq() {
        return seq;
    }

    public Long getUserSeq() {
        return userSeq;
    }

    public Long getProductSeq() {
        return productSeq;
    }

    public Review getReview() {
        if(this.review.getSeq() != 0) {
            return review;
        } else {
           return null;
        }
    }

    public void setStatus(OrderState state) {
        this.state = state;
    }

    public void rejectProcess(String message) {
        setStatus(OrderState.REJECTED);
        this.rejectMsg = message;
        this.rejectedAt = LocalDateTime.now();
    }

    public void setCompleteProcess() {
        setStatus(OrderState.COMPLETED);
        this.completedAt = LocalDateTime.now();
    }

    public void reject(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }

    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public OrderState getState() {
        return state;
    }

    public String getRequestMsg() {
        return requestMsg;
    }

    public String getRejectMsg() {
        return rejectMsg;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public Order(Long seq, Long userSeq, Long productSeq, Review review, OrderState state, String requestMsg, String rejectMsg, LocalDateTime completedAt, LocalDateTime rejectedAt, LocalDateTime createAt) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.productSeq = productSeq;
        this.review = review;
        this.state = state;
        this.requestMsg = requestMsg;
        this.rejectMsg = rejectMsg;
        this.completedAt = completedAt;
        this.rejectedAt = rejectedAt;
        this.createAt = createAt;
    }


    static public class Builder {
        private Long seq;
        private Long userSeq;
        private Long productSeq;
        private Review review;
        private OrderState state;
        private String requestMsg;
        private String rejectMsg;
        private LocalDateTime completedAt;
        private LocalDateTime rejectedAt;
        private LocalDateTime createAt;

        public Builder() {/*empty*/}

        public Builder(Order order) {
            this.seq = order.seq;
            this.userSeq = order.userSeq;
            this.productSeq = order.productSeq;
            this.review = order.review;
            this.state = order.state;
            this.requestMsg = order.requestMsg;
            this.rejectMsg = order.rejectMsg;
            this.completedAt = order.completedAt;
            this.rejectedAt = order.rejectedAt;
            this.createAt = order.createAt;
        }

        public Builder seq(Long seq) {
            this.seq = seq;
            return this;
        }

        public Builder userSeq(Long userSeq) {
            this.userSeq = userSeq;
            return this;
        }

        public Builder productSeq(Long productSeq) {
            this.productSeq = productSeq;
            return this;
        }

        public Builder review(Review review) {
            this.review = review;
            return this;
        }

        public Builder state(OrderState state) {
            this.state = state;
            return this;
        }

        public Builder requestMsg(String requestMsg) {
            this.requestMsg = requestMsg;
            return this;
        }

        public Builder rejectMsg(String rejectMsg) {
            this.rejectMsg = rejectMsg;
            return this;
        }

        public Builder createAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }


        public Builder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }


        public Builder rejectedAt(LocalDateTime rejectedAt) {
            this.rejectedAt = rejectedAt;
            return this;
        }

        public Order build() {
            return new Order(
                    seq,
                    userSeq,
                    productSeq,
                    review,
                    state,
                    requestMsg,
                    rejectMsg,
                    completedAt,
                    rejectedAt,
                    createAt
            );
        }
    }
}
