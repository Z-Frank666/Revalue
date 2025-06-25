package com.frank.revaluebackend.controller;


import com.frank.revaluebackend.pojo.Product;
import com.frank.revaluebackend.pojo.Product_sort;
import com.frank.revaluebackend.pojo.Result;
import com.frank.revaluebackend.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据分类id查询详细信息");
        List<Product> products = productService.getProductBySort(id);
        return Result.success(products);
    }

    @GetMapping("/all")
    public Result getAll(){
        log.info("查询全部信息");
        List<Product> products = productService.getAllproducts();
        return Result.success(products);
    }

    @GetMapping("/tags")
    public Result getAllTag(){
        log.info("查询全部信息");
        List<Product_sort> productSorts = productService.getAlltags();
        return Result.success(productSorts);
    }

}
