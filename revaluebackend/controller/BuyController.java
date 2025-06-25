package com.frank.revaluebackend.controller;


import com.frank.revaluebackend.pojo.*;
import com.frank.revaluebackend.service.BuyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/buy")
@CrossOrigin
public class BuyController {

    @Autowired
    private BuyService buyService;

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据分类id查询详细信息");
        List<BuyItem> buyItems = buyService.getBuyBySort(id);
        return Result.success(buyItems);
    }

    @GetMapping("/all")
    public Result getAll(){
        log.info("查询全部信息");
        List<BuyItem> buyItems = buyService.getAllBuy();
        return Result.success(buyItems);
    }

    @PostMapping("/showall")
    public Result showAll(){
        log.info("查询全部信息");
        List<BuyInfo> buyInfos = buyService.getShowAll();
        return Result.success(buyInfos);
    }

    @PostMapping("/showbysort/{id}")
    public Result showBySort(@PathVariable Integer id){
        log.info("根据分类id查询详细信息");
        List<BuyInfo> buyInfos = buyService.getShowBySort(id);
        return Result.success(buyInfos);
    }

    @GetMapping("/tags")
    public Result getAllTag(){
        log.info("查询全部信息");
        List<BuySort> buySorts = buyService.getAlltags();
        return Result.success(buySorts);
    }

    @PutMapping("/add")
    public Result update(@RequestBody BuyItem buyItem){
        log.info("新增信息, {}", buyItem);
        BuyItem buyItem1 = buyService.addbuy(buyItem);
        return Result.success(buyItem1);
    }

    @PutMapping("/addimg")
    public Result addImg(@RequestBody BuyImg buyImg){
        log.info("新增信息, {}", buyImg);
        buyService.addBuyImg(buyImg);
        return Result.success();
    }

    @GetMapping("/imgcnt/{id}")
    public Result getImgcnt(@PathVariable Integer id){
        log.info("图片数量设置,{}",id);
        Integer cnt = buyService.getImgCnt(id);
        return Result.success(cnt);
    }

    @GetMapping("/detail/{id}")
    public Result getDetail(@PathVariable Integer id){
        log.info("获取详情页{}",id);
        BuyInfo buyInfo = buyService.getDetalById(id);
        return Result.success(buyInfo);
    }

    @GetMapping("/item/{id}")
    public Result getItem(@PathVariable Integer id){
        log.info("获取求购信息{}",id);
        BuyItem buyItem = buyService.getItemById(id);
        return Result.success(buyItem);
    }

    @PutMapping("/comment/add")
    public Result addComment(@RequestBody Comment comment){
        log.info("添加评论{}",comment);
        Comment res = buyService.addComment(comment);
        return Result.success(res);
    }

    @GetMapping("/comment/show/{id}")
    public Result showComment(@PathVariable Integer id){
        log.info("展示指定求购下的评论{}",id);
        List<Comment> comments = buyService.showComment(id);
        return Result.success(comments);
    }

    @GetMapping("/detail/imglist/{id}")
    public Result getDetailImg(@PathVariable Integer id){
        log.info("获取指定求购的图片{}",id);
        List<String> imglist = buyService.getImgList(id);
        return Result.success(imglist);
    }

    @PostMapping("/search/{keyword}")
    public Result buysearch(@PathVariable String keyword){
        log.info("根据关键词搜索求购信息{}",keyword);
        List<BuyInfo> buyInfos = buyService.buysearch(keyword);
        return Result.success(buyInfos);
    }
}
