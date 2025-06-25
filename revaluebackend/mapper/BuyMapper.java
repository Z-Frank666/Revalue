package com.frank.revaluebackend.mapper;

import com.frank.revaluebackend.pojo.BuyImg;
import com.frank.revaluebackend.pojo.BuyItem;
import com.frank.revaluebackend.pojo.BuySort;
import com.frank.revaluebackend.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BuyMapper {

    @Select("select * from buy_sort")
    List<BuySort> getAlltags();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into buy_item(user_id,title,description,sort_id,create_time,QQ,weixin,phone,name)values " +
            "(#{userId},#{title},#{description},#{sortId},#{createTime},#{QQ},#{weixin},#{phone},#{name})")
    void addBuy(BuyItem buyItem);

    @Select("SELECT * FROM buy_item WHERE id = (SELECT MAX(id) FROM buy_item)")
    BuyItem getRecent();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into buy_img(image,item_id) " +
            "values (#{image},#{itemId})")
    void addBuyImg(BuyImg buyImg);

    @Select("select count(id) from buy_img where item_id = #{id}")
    Integer getImgCnt(Integer id);

    @Update("update buy_item set img_cnt = #{cnt} where id = #{id}")
    void updateImgCnt(Integer cnt,Integer id);

    @Select("select * from buy_item")
    List<BuyItem> getAllbuy();

    @Select("select * from buy_item where sort_id = #{id}")
    List<BuyItem> getUnusedBySort(Integer id);

    @Select("select name from buy_sort where id = #{id}")
    String getTag(Integer id);

    @Select("select * from buy_item where id = #{id}")
    BuyItem getItemById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into comment(user_id,item_id,content,create_time,likes) " +
            "values (#{userId},#{itemId},#{content},#{createTime},#{likes})")
    void addComment(Comment comment);

    @Select("select * from comment where item_id = #{id}")
    List<Comment> showComment(Integer id);

    @Select("select image from buy_img where item_id = #{id}")
    List<String> getImgList(Integer id);

    @Select("select * from buy_item where user_id = #{id}")
    List<BuyItem> getbuyByUserId(Integer id);

    List<BuyItem> buySearch(String keyword);
}
