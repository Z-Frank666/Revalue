package com.frank.revaluebackend.mapper;

import com.frank.revaluebackend.pojo.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MineMapper {

    @Select("select * from collection where user_id = #{id}")
    List<Collection> getCollect(Integer id);

    @Delete("delete from collection where id = #{id}")
    void deleteCollection(Integer id);

    @Select("select * from unused_item where user_id = #{id}")
    List<UnusedItem> getunused(Integer id);

    @Select("select * from buy_item where id = #{id}")
    BuyItem getbuydetail(Integer id);

    @Update("update buy_item set " +
            "title = #{title}, " +
            "description = #{description}, " +
            "sort_id = #{sortId}, " +
            "QQ = #{QQ}, " +
            "weixin = #{weixin}, " +
            "phone = #{phone}, " +
            "name = #{name} " +
            "where id = #{id}")
    void updatebuy(BuyItem buyItem);

    @Delete("delete from buy_item where id = #{id}")
    void deletebuy(Integer id);

    @Update("UPDATE buy_item SET isactive = CASE WHEN isactive = 1 THEN 0 ELSE 1 END WHERE id = #{id}")
    void hidebuy(Integer id);


    @Update("UPDATE unused_item SET " +
            "name = #{name}, " +
            "description = #{description}, " +
            "price = #{price}, " +
            "linkman = #{linkman}, " +
            "phone = #{phone}, " +
            "sort_id = #{sortId} " + // 注意这里没有逗号
            "WHERE id = #{id}")
    void updateunused(UnusedItem item);

    @Delete("delete from unused_item where id = #{id}")
    void deleteunused(Integer id);

    @Update("UPDATE unused_item SET isactive = CASE WHEN isactive = 1 THEN 0 ELSE 1 END WHERE id = #{id}")
    void hideunused(Integer id);

    @Select("select * from pay_order where buyer_id = #{id}")
    List<PayOrder> getPayorder(Integer id);

    @Select("select * from sale_order where seller_id = #{id}")
    List<SaleOrder> getSaleorder(Integer id);

    @Select("select * from pay_order where id = #{id}")
    PayOrder getPayorderDetail(Integer id);

    @Select("select * from sale_order where seller_id = #{id}")
    SaleOrder getSaleorderDetail(Integer id);

    @Update("update user set password = #{password} where email = #{email}")
    void reset(String email, String password);

    @Select("select * from unused_item where id = #{id}")
    UnusedItem getunuseddetail(Integer id);
}
