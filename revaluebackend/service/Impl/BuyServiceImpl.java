package com.frank.revaluebackend.service.Impl;

import com.frank.revaluebackend.mapper.BuyMapper;
import com.frank.revaluebackend.mapper.UserMapper;
import com.frank.revaluebackend.pojo.*;
import com.frank.revaluebackend.service.BuyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.frank.revaluebackend.utils.TimeUtils.trans;

@Slf4j
@Service
public class BuyServiceImpl implements BuyService {

    @Autowired
    private BuyMapper buyMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<BuySort> getAlltags() {
        return buyMapper.getAlltags();
    }

    @Override
    public BuyItem addbuy(BuyItem buyItem) {
        buyItem.setCreateTime(LocalDateTime.now());
        buyMapper.addBuy(buyItem);
        return buyMapper.getRecent();
    }

    @Override
    public void addBuyImg(BuyImg buyImg) {
        buyMapper.addBuyImg(buyImg);
    }

    @Override
    public Integer getImgCnt(Integer id) {
        Integer cnt = buyMapper.getImgCnt(id);
        buyMapper.updateImgCnt(cnt,id);
        return cnt;
    }

    @Override
    public List<BuyItem> getAllBuy() {
        return buyMapper.getAllbuy();
    }

    @Override
    public List<BuyItem> getBuyBySort(Integer id) {
        return buyMapper.getUnusedBySort(id);
    }

    @Override
    public List<BuyInfo> getShowAll() {
        List<BuyItem> buyItems = buyMapper.getAllbuy();

        Integer count = buyItems.size();
        List<BuyInfo> buyInfos = new ArrayList<>(count);
        for (Integer i = 0; i < count; i++) {
            BuyInfo temp = new BuyInfo();
            temp.setId(buyItems.get(i).getId());
            temp.setAvatar(userMapper.getAvatar(buyItems.get(i).getUserId()));
            temp.setNickname(userMapper.getNickname(buyItems.get(i).getUserId()));
            temp.setDescription(buyItems.get(i).getDescription());
            temp.setTitle(buyItems.get(i).getTitle());
            Integer cnt = buyMapper.getImgCnt(buyItems.get(i).getId());
            log.info("cnt:",cnt);
            buyMapper.updateImgCnt(cnt,buyItems.get(i).getId());
            temp.setSortId(buyItems.get(i).getSortId());
            temp.setImgCnt(buyItems.get(i).getImgCnt());
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime pastTime = buyItems.get(i).getCreateTime(); // 45分钟前
            String result = trans(pastTime, now); // 返回"45分钟前"
            temp.setTime(result);
            temp.setTag(buyMapper.getTag(buyItems.get(i).getSortId()));
            temp.setIsactive(buyItems.get(i).getIsactive());
            buyInfos.add(temp);
        }
        return buyInfos;
    }

    @Override
    public List<BuyInfo> getShowBySort(Integer id) {
        List<BuyInfo> buyInfos = getShowAll();
        List<BuyInfo> ans = new ArrayList<>();
        for (BuyInfo buyInfo : buyInfos) {
            if (buyInfo.getSortId().equals(id)) {
                ans.add(buyInfo);
            }
        }
        return ans;
    }

    @Override
    public BuyInfo getDetalById(Integer id) {
        List<BuyInfo> buyInfos = getShowAll();
        for (BuyInfo buyInfo : buyInfos) {
            if (buyInfo.getId().equals(id)) {
                return buyInfo;
            }
        }
        return null;
    }

    @Override
    public BuyItem getItemById(Integer id) {
        return buyMapper.getItemById(id);
    }

    @Override
    public Comment addComment(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        comment.setLikes(0);
        comment.setTime("刚刚");
        comment.setAvatar(userMapper.getAvatar(comment.getUserId()));
        comment.setNickname(userMapper.getNickname(comment.getUserId()));
        buyMapper.addComment(comment);
        return comment;
    }

    @Override
    public List<Comment> showComment(Integer id) {
        List<Comment> commentList = buyMapper.showComment(id);
        for (Comment comment : commentList) {
            comment.setTime(trans(comment.getCreateTime(),LocalDateTime.now()));
            comment.setAvatar(userMapper.getAvatar(comment.getUserId()));
            comment.setNickname(userMapper.getNickname(comment.getUserId()));
        }
        return commentList;
    }

    @Override
    public List<String> getImgList(Integer id) {
        return buyMapper.getImgList(id);
    }

    @Override
    public List<BuyInfo> buysearch(String keyword) {
        List<BuyItem> buyItemList = buyMapper.buySearch(keyword);
        List<BuyInfo> buyInfos = new ArrayList<>();
        for (BuyItem buyItem : buyItemList) {
            BuyInfo buyInfo = new BuyInfo();
            buyInfo.setId(buyItem.getId());
            buyInfo.setAvatar(userMapper.getAvatar(buyItem.getUserId()));
            buyInfo.setNickname(userMapper.getNickname(buyItem.getUserId()));
            buyInfo.setDescription(buyItem.getDescription());
            buyInfo.setTitle(buyItem.getTitle());
            buyInfo.setSortId(buyItem.getSortId());
            buyInfo.setImgCnt(buyItem.getImgCnt());
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime pastTime = buyItem.getCreateTime(); // 45分钟前
            String result = trans(pastTime, now); // 返回"45分钟前"
            buyInfo.setTime(result);
            buyInfo.setTag(buyMapper.getTag(buyItem.getSortId()));
            buyInfo.setIsactive(buyItem.getIsactive());
            buyInfos.add(buyInfo);
        }
        return buyInfos;
    }
}
