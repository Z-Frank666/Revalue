package com.frank.revaluebackend.service;

import com.frank.revaluebackend.pojo.*;

import java.util.List;

public interface UnusedService {
    List<UnusedItem> getUnusedBySort(Integer id);

    List<UnusedItem> getAllunused();

    List<Unused_sort> getAlltags();

    Integer addUnused(UnusedItem unusedItem);

    void addUnusedImg(UnusedImg unusedImg);

    List<Showimg> getShowImg();

    List<String> getDetailimgs(Integer id);

    UnusedItem getdetail(Integer id);

    Collection collect(Collection collection);

    void incollect(Collection collection);

    Boolean checkcollect(Collection collection);

    String adddeal(Integer userId,Integer itemId);

    List<UnusedItem> unusedsearch(String keyword);
}
