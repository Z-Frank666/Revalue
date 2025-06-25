package com.frank.revaluebackend.service.Impl;

import com.frank.revaluebackend.mapper.BuyMapper;
import com.frank.revaluebackend.mapper.MineMapper;
import com.frank.revaluebackend.mapper.UnusedMapper;
import com.frank.revaluebackend.pojo.*;
import com.frank.revaluebackend.service.MineService;
import com.frank.revaluebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.frank.revaluebackend.utils.TimeUtils.trans;

@Service
public class MineServiceImpl implements MineService {

    @Autowired
    private MineMapper mineMapper;

    @Autowired
    private UnusedMapper unusedMapper;

    @Autowired
    private BuyMapper buyMapper;
    @Autowired
    private UserService userService;

    @Override
    public List<CollectionInfo> getCollection(Integer id) {
        List<Collection> collections = mineMapper.getCollect(id);
        List<CollectionInfo> collectionInfos = new ArrayList<>();
        for (Collection collection : collections) {
            CollectionInfo collectionInfo = new CollectionInfo();
            collectionInfo.setId(collection.getId());
            collectionInfo.setUnusedId(collection.getUnusedId());
            collectionInfo.setUsedId(collection.getUserId());
            UnusedItem unusedItem = unusedMapper.getDetail(collection.getUnusedId());
            if (unusedItem != null) {
                collectionInfo.setSortId(unusedItem.getSortId());
                collectionInfo.setDescription(unusedItem.getDescription());
                collectionInfo.setName(unusedItem.getName());
                collectionInfo.setImage(unusedItem.getImage());
                collectionInfo.setCreateTime(unusedItem.getCreateTime());
                collectionInfo.setTag(unusedMapper.getTag(collectionInfo.getSortId()));
                collectionInfo.setPrice(unusedItem.getPrice());
            }
            collectionInfos.add(collectionInfo);
        }
        return collectionInfos;
    }

    @Override
    public void incollectById(Integer id) {
        mineMapper.deleteCollection(id);
    }

    @Override
    public List<UnusedInfo> getunused(Integer id) {
        List<UnusedItem> unusedItems = mineMapper.getunused(id);
        List<UnusedInfo> unusedInfos = new ArrayList<>();
        for (UnusedItem unusedItem : unusedItems) {
            UnusedInfo unusedInfo = new UnusedInfo();

            unusedInfo.setId(unusedItem.getId());
            unusedInfo.setName(unusedItem.getName());
            unusedInfo.setDescription(unusedItem.getDescription());
            unusedInfo.setPrice(unusedItem.getPrice());
            unusedInfo.setImage(unusedItem.getImage());
            unusedInfo.setCreateTime(unusedItem.getCreateTime());
            unusedInfo.setUserId(unusedItem.getUserId());
            unusedInfo.setSortId(unusedItem.getSortId());
            unusedInfo.setPhone(unusedItem.getPhone());
            unusedInfo.setLinkman(unusedItem.getLinkman());
            unusedInfo.setTag(unusedMapper.getTag(unusedInfo.getSortId()));
            unusedInfo.setIsactive(unusedItem.getIsactive());
            unusedInfos.add(unusedInfo);
        }
        return unusedInfos;
    }

    @Override
    public List<Mybuy> getbuy(Integer id) {
        List<BuyItem> buyItems = buyMapper.getbuyByUserId(id);
        List<Mybuy> mybuys = new ArrayList<>();
        for (BuyItem buyItem : buyItems) {
            Mybuy buyInfo = new Mybuy();
            buyInfo.setId(buyItem.getId());
            buyInfo.setTitle(buyItem.getTitle());
            buyInfo.setSortId(buyItem.getSortId());
            buyInfo.setDescription(buyItem.getDescription());
            buyInfo.setImgCnt(buyItem.getImgCnt());
            buyInfo.setTag(buyMapper.getTag(buyItem.getSortId()));
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime pastTime = buyItem.getCreateTime(); // 45分钟前
            String result = trans(pastTime, now); // 返回"45分钟前"
            buyInfo.setTime(result);
            buyInfo.setIsactive(buyItem.getIsactive());
            mybuys.add(buyInfo);
        }
        return mybuys;
    }

    @Override
    public BuyItem getbuyDetail(Integer id) {
        return mineMapper.getbuydetail(id);
    }

    @Override
    public void updatebuy(BuyItem buyItem) {
        mineMapper.updatebuy(buyItem);
    }

    @Override
    public void deletebuy(Integer id) {
        mineMapper.deletebuy(id);
    }

    @Override
    public void hidebuy(Integer id) {
        mineMapper.hidebuy(id);
    }

    @Override
    public void updateUnused(UnusedItem unusedItem) {
        mineMapper.updateunused(unusedItem);
    }

    @Override
    public void deleteUnused(Integer id) {
        mineMapper.deleteunused(id);
    }

    @Override
    public void hideUnused(Integer id) {
        mineMapper.hideunused(id);
    }

    @Override
    public List<PayOrder> getPayorder(Integer id) {
        return mineMapper.getPayorder(id);
    }

    @Override
    public List<SaleOrder> getSaleorder(Integer id) {
        return mineMapper.getSaleorder(id);
    }

    @Override
    public OrderDetail getPayorderDetail(Integer id) {
        PayOrder payOrder = mineMapper.getPayorderDetail(id);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(payOrder.getId());
        orderDetail.setImage(payOrder.getImage());
        orderDetail.setName(payOrder.getName());
        orderDetail.setPrice(payOrder.getPrice());
        orderDetail.setDescription(payOrder.getDescription());
        orderDetail.setCreateTime(payOrder.getCreateTime());
        orderDetail.setBuyerId(payOrder.getBuyerId());
        orderDetail.setItemId(payOrder.getItemId());
        orderDetail.setSellerId(payOrder.getSellerId());
        orderDetail.setBuyerName(userService.getUsername(payOrder.getBuyerId()));
        orderDetail.setSellerName(userService.getUsername(payOrder.getSellerId()));
        return orderDetail;
    }

    @Override
    public OrderDetail getSaleorderDetail(Integer id) {
        SaleOrder saleOrder = mineMapper.getSaleorderDetail(id);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(saleOrder.getId());
        orderDetail.setImage(saleOrder.getImage());
        orderDetail.setName(saleOrder.getName());
        orderDetail.setPrice(saleOrder.getPrice());
        orderDetail.setDescription(saleOrder.getDescription());
        orderDetail.setCreateTime(saleOrder.getCreateTime());
        orderDetail.setBuyerId(saleOrder.getBuyerId());
        orderDetail.setItemId(saleOrder.getItemId());
        orderDetail.setSellerId(saleOrder.getSellerId());
        orderDetail.setBuyerName(userService.getUsername(saleOrder.getBuyerId()));
        orderDetail.setSellerName(userService.getUsername(saleOrder.getSellerId()));
        return orderDetail;
    }

    @Override
    public void reset(ResetParam resetParam) {
        String email = resetParam.getEmail();
        String password = resetParam.getNewPassword();
        mineMapper.reset(email,password);
    }

    @Override
    public UnusedItem getunusedDeail(Integer id) {
        return mineMapper.getunuseddetail(id);
    }
}
