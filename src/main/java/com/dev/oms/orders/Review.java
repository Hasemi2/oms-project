package com.dev.oms.orders;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class Review {


    private final Long seq;
    private Long userSeq;
    private Long productSeq;
    private String content;
    private LocalDateTime createdAt;

    public Review(Long userSeq, Long productSeq, String content) {
        this(null, userSeq, productSeq, content, null);
    }

    public Review(Long seq, Long productSeq, String content, LocalDateTime createdAt) {
       this.seq = seq;
       this.productSeq = productSeq;
       this.content = content;
       this.createdAt = createdAt;
    }


    public Review(Long seq, Long userSeq, Long productSeq, String content, LocalDateTime createdAt) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.productSeq = productSeq;
        this.content = content;
        this.createdAt = defaultIfNull(createdAt, now());
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSeq() {
        return seq;
    }

    public Long getProductSeq() {
        return productSeq;
    }

    public String getContent() {
        return content;
    }

}
