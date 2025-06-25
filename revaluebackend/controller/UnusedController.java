package com.frank.revaluebackend.controller;


import com.frank.revaluebackend.pojo.*;
import com.frank.revaluebackend.service.UnusedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/unused")
@CrossOrigin
public class UnusedController {

    @Autowired
    private UnusedService unusedService;

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据分类id查询详细信息");
        List<UnusedItem> unusedItems = unusedService.getUnusedBySort(id);
        return Result.success(unusedItems);
    }

    @GetMapping("/all")
    public Result getAll(){
        log.info("查询全部信息");
        List<UnusedItem> unusedItems = unusedService.getAllunused();
        return Result.success(unusedItems);
    }

    @GetMapping("/tags")
    public Result getAllTag(){
        log.info("查询全部信息");
        List<Unused_sort> unused_sorts = unusedService.getAlltags();
        return Result.success(unused_sorts);
    }

    @PutMapping("/add")
    public Result update(@RequestBody UnusedItem unusedItem){
        log.info("新增信息, {}", unusedItem);
        Integer id = unusedService.addUnused(unusedItem);
        return Result.success(id);
    }

    @PutMapping("/addimg")
    public Result addImg(@RequestBody UnusedImg unusedImg){
        log.info("新增信息, {}", unusedImg);
        unusedService.addUnusedImg(unusedImg);
        return Result.success();
    }

    @GetMapping("/showImg")
    public Result showImg(){
        log.info("获取展示图片");
        List<Showimg> showimgs = unusedService.getShowImg();
        return Result.success(showimgs);
    }

    @GetMapping("/detail/imgs/{id}")
    public Result detailImgs(@PathVariable Integer id){
        log.info("获取详情页图片,{}",id);
        List<String> imgs = unusedService.getDetailimgs(id);
        return Result.success(imgs);
    }

    @GetMapping("/detail/message/{id}")
    public Result detailMessage(@PathVariable Integer id){
        log.info("获取详情页信息,{}",id);
        UnusedItem unusedItem = unusedService.getdetail(id);
        return Result.success(unusedItem);
    }

    @PutMapping("/detail/collect")
    public Result detailCollect(@RequestBody Collection collection){
        log.info("收藏{}", collection);
        Collection res =  unusedService.collect(collection);
        return Result.success(res);
    }

    @PostMapping("/detail/incollect")
    public Result detailInCollect(@RequestBody Collection collection){
        log.info("取消收藏{}", collection);
        unusedService.incollect(collection);
        return Result.success();
    }

    @PostMapping("/detail/checkcollect")
    public Result detailCheckCollect(@RequestBody Collection collection){
        log.info("check{}", collection);
        Boolean check = unusedService.checkcollect(collection);
        return Result.success(check);
    }

    @PutMapping("/detail/deal/{userId}/{itemId}")
    public Result detailDeal(@PathVariable Integer userId, @PathVariable Integer itemId){
        log.info("快速交易{}",userId);
        String msg = unusedService.adddeal(userId,itemId);
        return Result.success(msg);
    }

    @PostMapping("/search/{keyword}")
    public Result unusedsearch(@PathVariable String keyword){
        log.info("根据关键词搜索求购信息{}",keyword);
        List<UnusedItem> unusedItems = unusedService.unusedsearch(keyword);
        return Result.success(unusedItems);
    }
}
