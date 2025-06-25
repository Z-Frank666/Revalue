package com.frank.revaluebackend.mapper;

import com.frank.revaluebackend.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UnusedMapper {

    @Select("select * from unused_item where sort_id = #{id}")
    List<UnusedItem> getUnusedBySort(Integer id);

    @Select("select * from unused_item")
    List<UnusedItem> getAllunused();

    @Select("select * from unused_sort")
    List<Unused_sort> getAlltags();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into unused_item(name,description, price,linkman,phone,sort_id,user_id,create_time) " +
            "values (#{name},#{description},#{price},#{linkman},#{phone},#{sortId},#{userId},#{createTime})")
    void addUnused(UnusedItem unusedItem);

    @Select("select MAX(id) from unused_item")
    Integer getRecentId();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into unused_img(image,item_id) " +
            "values (#{image},#{itemId})")
    void addUnusedImg(UnusedImg unusedImg);

    List<Showimg> getShowImgs();

    void updateImg(List<Showimg> showImgs);

    @Select("select image from unused_img where item_id = #{id}")
    List<String> getDetailimgs(Integer id);

    @Select("select * from unused_item where id = #{id}")
    UnusedItem getDetail(Integer id);

    @Insert("insert into collection(unused_id,user_id) values (#{unusedId},#{userId})")
    void collect(Collection collection);

    @Select("select * from collection where unused_id = #{unusedId} and user_id = #{userId}")
    Collection getCollection(Integer unusedId, Integer userId);

    @Delete("delete from collection where id = #{id}")
    void incollect(Collection collection);

    @Select("select count(id) from collection where unused_id = #{unusedId} and user_id = #{userId}")
    Integer checkcollect(Collection collection);

    @Select("select name from unused_sort where id = #{id}")
    String getTag(Integer id);

    @Insert("insert into pay_order(name,item_id,buyer_id,seller_id,price,create_time,image,description) values (#{name},#{itemId},#{buyerId},#{sellerId},#{price},#{createTime},#{image},#{description})")
    void addPayorder(PayOrder payOrder);

    @Insert("insert into sale_order(name,item_id,buyer_id,seller_id,price,create_time,image,description) values (#{name},#{itemId},#{buyerId},#{sellerId},#{price},#{createTime},#{image},#{description})")
    void addSaleorder(SaleOrder saleOrder);

    List<UnusedItem> unusedSearch(String keyword);
}
