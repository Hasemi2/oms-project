package com.dev.oms.orders;

import com.dev.oms.configures.web.Pageable;
import com.dev.oms.errors.NotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.dev.oms.utils.ApiUtils.ApiResult;
import static com.dev.oms.utils.ApiUtils.success;

@RestController
@RequestMapping("api/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ApiResult<List<OrderDto>> findAll(Pageable pageable) {
        List<Order> response = orderService.findAll(pageable.getOffset(), pageable.getSize());
        return success(response.stream().map(OrderDto::new).collect(Collectors.toList()));
    }

    @GetMapping(path = "{id}")
    public ApiResult<OrderDto> findById(@PathVariable Long id) {
        OrderDto response =  orderService.findById(id)
                .map(OrderDto::new)
                .orElseThrow(() -> new NotFoundException("Could not found order for " + id));

        return success(response);
    }

    @PatchMapping(path = "{id}/accept")
    public ApiResult<Boolean> accept(@PathVariable Long id) {
        boolean response = orderService.accept(id);
        return success(response);
    }

    @PatchMapping(path = "{id}/reject")
    public ApiResult<Boolean> reject(@PathVariable Long id, @Valid @RequestBody OrderRejectRequest request) {
        boolean response = orderService.reject(id ,request);
        return success(response);
    }

    @PatchMapping(path = "{id}/shipping")
    public ApiResult<Boolean> shipping(@PathVariable Long id) {
        boolean response = orderService.shipping(id);
        return success(response);
    }

    @PatchMapping(path = "{id}/complete")
    public ApiResult<Boolean> complete(@PathVariable Long id) {
        boolean response = orderService.complete(id);
        return success(response);
    }
}