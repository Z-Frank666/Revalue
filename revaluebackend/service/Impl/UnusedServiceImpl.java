package com.frank.revaluebackend.service.Impl;

import com.frank.revaluebackend.mapper.UnusedMapper;
import com.frank.revaluebackend.pojo.*;
import com.frank.revaluebackend.service.UnusedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UnusedServiceImpl implements UnusedService {

    @Autowired
    private UnusedMapper unusedMapper;

    @Override
    public List<UnusedItem> getUnusedBySort(Integer id) {
        return unusedMapper.getUnusedBySort(id);
    }

    @Override
    public List<UnusedItem> getAllunused() {
        return unusedMapper.getAllunused();
    }

    @Override
    public List<Unused_sort> getAlltags() {
        return unusedMapper.getAlltags();
    }

    @Override
    public Integer addUnused(UnusedItem unusedItem) {
        unusedItem.setCreateTime(LocalDateTime.now());
        unusedMapper.addUnused(unusedItem);
        return unusedMapper.getRecentId();
    }

    @Override
    public void addUnusedImg(UnusedImg unusedImg) {
        unusedMapper.addUnusedImg(unusedImg);
    }

    @Override
    public List<Showimg> getShowImg() {
        List<Showimg> showimgs = unusedMapper.getShowImgs();
        unusedMapper.updateImg(showimgs);
        return showimgs;
    }

    @Override
    public List<String> getDetailimgs(Integer id) {
        return unusedMapper.getDetailimgs(id);
    }

    @Override
    public UnusedItem getdetail(Integer id) {
        return unusedMapper.getDetail(id);
    }

    @Override
    public Collection collect(Collection collection) {
        unusedMapper.collect(collection);
        Collection collection1 = unusedMapper.getCollection(collection.getUnusedId(), collection.getUserId());
        return collection1;
    }

    @Override
    public void incollect(Collection collection) {
        unusedMapper.incollect(collection);
    }

    @Override
    public Boolean checkcollect(Collection collection) {
        if(unusedMapper.checkcollect(collection)>0){
            return true;
        }
        return false;
    }

    @Override
    public String adddeal(Integer userId,Integer itemId) {
        UnusedItem unusedItem = unusedMapper.getDetail(itemId);
        PayOrder payOrder = new PayOrder();
        payOrder.setName(unusedItem.getName());
        payOrder.setDescription(unusedItem.getDescription());
        payOrder.setItemId(itemId);
        payOrder.setBuyerId(userId);
        payOrder.setSellerId(unusedItem.getUserId());
        payOrder.setPrice(unusedItem.getPrice());
        payOrder.setImage(unusedItem.getImage());
        payOrder.setCreateTime(LocalDateTime.now());

        SaleOrder saleOrder = new SaleOrder();
        saleOrder.setName(unusedItem.getName());
        saleOrder.setDescription(unusedItem.getDescription());
        saleOrder.setItemId(itemId);
        saleOrder.setBuyerId(userId);
        saleOrder.setSellerId(unusedItem.getUserId());
        saleOrder.setPrice(unusedItem.getPrice());
        saleOrder.setImage(unusedItem.getImage());
        saleOrder.setCreateTime(LocalDateTime.now());
        String msg = "";
        if(payOrder.getBuyerId()!=payOrder.getSellerId()){
            unusedMapper.addPayorder(payOrder);
            unusedMapper.addSaleorder(saleOrder);
            msg = "交易成功！";
        }else{
            msg = "无法购买自己的闲置！";
        }
        return msg;
    }

    @Override
    public List<UnusedItem> unusedsearch(String keyword) {
        return unusedMapper.unusedSearch(keyword);
    }
}
