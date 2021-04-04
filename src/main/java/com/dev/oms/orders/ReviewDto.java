package com.dev.oms.orders;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ReviewDto {
    private Long seq;
    private Long productId;
    private String content;
    private LocalDateTime createAt;

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public ReviewDto(Review source) {
        copyProperties(source, this);
        this.productId = source.getProductSeq();
    }
}
