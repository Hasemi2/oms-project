package com.dev.oms.orders;

import com.dev.oms.errors.NotFoundException;
import com.dev.oms.security.JwtAuthentication;
import com.dev.oms.utils.ApiUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dev.oms.utils.ApiUtils.success;


@RestController
@RequestMapping("api/orders")
public class ReviewRestController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    public ReviewRestController(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("{id}/review")
    public ApiUtils.ApiResult<ReviewDto> review(@PathVariable("id") Long orderId,
                                             @Valid @RequestBody ReviewRequest request,
                                             @AuthenticationPrincipal JwtAuthentication authentication
                                             ) {

        Long id = reviewService.createReview(orderId, authentication.id, request);
        ReviewDto response =  reviewRepository.findById(id)
                .map(ReviewDto::new)
                .orElseThrow(() -> new NotFoundException("Could not found review for " + id));
        return success(response);
    }
}