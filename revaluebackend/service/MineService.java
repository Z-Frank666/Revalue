package com.frank.revaluebackend.service;

import com.frank.revaluebackend.pojo.*;

import java.util.List;

public interface MineService {
    List<CollectionInfo> getCollection(Integer id);

    void incollectById(Integer id);

    List<UnusedInfo> getunused(Integer id);

    List<Mybuy> getbuy(Integer id);

    BuyItem getbuyDetail(Integer id);

    void updatebuy(BuyItem buyItem);

    void deletebuy(Integer id);

    void hidebuy(Integer id);

    void updateUnused(UnusedItem unusedItem);

    void deleteUnused(Integer id);

    void hideUnused(Integer id);

    List<PayOrder> getPayorder(Integer id);

    List<SaleOrder> getSaleorder(Integer id);

    OrderDetail getPayorderDetail(Integer id);

    OrderDetail getSaleorderDetail(Integer id);

    void reset(ResetParam resetParam);

    UnusedItem getunusedDeail(Integer id);
}
