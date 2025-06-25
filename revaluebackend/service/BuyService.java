package com.frank.revaluebackend.service;

import com.frank.revaluebackend.pojo.*;

import java.util.List;

public interface BuyService {
    List<BuySort> getAlltags();

    BuyItem addbuy(BuyItem buyItem);

    void addBuyImg(BuyImg buyImg);

    Integer getImgCnt(Integer id);

    List<BuyItem> getAllBuy();

    List<BuyItem> getBuyBySort(Integer id);

    List<BuyInfo> getShowAll();

    List<BuyInfo> getShowBySort(Integer id);

    BuyInfo getDetalById(Integer id);

    BuyItem getItemById(Integer id);

    Comment addComment(Comment comment);

    List<Comment> showComment(Integer id);

    List<String> getImgList(Integer id);

    List<BuyInfo> buysearch(String keyword);
}
