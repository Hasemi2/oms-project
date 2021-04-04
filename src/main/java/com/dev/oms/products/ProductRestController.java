package com.dev.oms.products;

import com.dev.oms.errors.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dev.oms.utils.ApiUtils.ApiResult;
import static com.dev.oms.utils.ApiUtils.success;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "{id}")
    public ApiResult<ProductDto> findById(@PathVariable Long id) {
        ProductDto response =  productService.findById(id)
                .map(ProductDto::new)
                .orElseThrow(() -> new NotFoundException("Could not found product for " + id));

        return success(response);
    }

    @GetMapping
    public ApiResult<List<ProductDto>> findAll() {
        List<ProductDto> responseList = productService.findAll().stream()
                .map(ProductDto::new)
                .collect(toList());

        return success(responseList);
    }

}