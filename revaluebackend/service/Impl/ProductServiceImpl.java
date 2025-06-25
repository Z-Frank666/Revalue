package com.frank.revaluebackend.service.Impl;

import com.frank.revaluebackend.mapper.ProductMapper;
import com.frank.revaluebackend.pojo.Product;
import com.frank.revaluebackend.pojo.Product_sort;
import com.frank.revaluebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getProductBySort(Integer id) {
        return productMapper.getProductBySort(id);
    }

    @Override
    public List<Product> getAllproducts() {
        return productMapper.getAllproducts();
    }

    @Override
    public List<Product_sort> getAlltags() {
        return productMapper.getAlltags();
    }
}
