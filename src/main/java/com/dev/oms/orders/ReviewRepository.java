package com.dev.oms.orders;


import java.util.Optional;

public interface ReviewRepository {
    Long create(Long userId, Long productSeq, String content);
    Optional<Review> findById(Long seq);
}
