package com.frank.revaluebackend.service;

import com.frank.revaluebackend.pojo.Product;
import com.frank.revaluebackend.pojo.Product_sort;

import java.util.List;

public interface ProductService {
    List<Product> getProductBySort(Integer id);

    List<Product> getAllproducts();

    List<Product_sort> getAlltags();
}
