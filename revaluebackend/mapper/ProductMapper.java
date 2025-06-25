package com.frank.revaluebackend.mapper;

import com.frank.revaluebackend.pojo.Product;
import com.frank.revaluebackend.pojo.Product_sort;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("select * from product_item where sort_id = #{id}")
    List<Product> getProductBySort(Integer id);

    @Select("select * from product_item")
    List<Product> getAllproducts();

    @Select("select * from product_sort")
    List<Product_sort> getAlltags();
}
