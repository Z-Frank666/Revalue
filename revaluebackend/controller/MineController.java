package com.frank.revaluebackend.controller;


import com.frank.revaluebackend.pojo.*;
import com.frank.revaluebackend.service.MineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mine")
@CrossOrigin
public class MineController {

    @Autowired
    private MineService mineService;

    @GetMapping("/getcollection/{id}")
    public Result getcollection(@PathVariable Integer id) {
        log.info("我的收藏,{}",id);
        List<CollectionInfo> collectionInfoList = mineService.getCollection(id);
        return Result.success(collectionInfoList);
    }

    @PutMapping("/incollect/{id}")
    public Result incollect(@PathVariable Integer id) {
        log.info("根据id取消收藏{}",id);
        mineService.incollectById(id);
        return Result.success();
    }

    @GetMapping("/getunused/{id}")
    public Result getunused(@PathVariable Integer id) {
        log.info("我的闲置,{}",id);
        List<UnusedInfo> unusedInfos = mineService.getunused(id);
        return Result.success(unusedInfos);
    }

    @GetMapping("/getbuy/{id}")
    public Result getbuy(@PathVariable Integer id) {
        log.info("我的求购,{}",id);
        List<Mybuy> mybuys = mineService.getbuy(id);
        return Result.success(mybuys);
    }

    @GetMapping("/getbuydetail/{id}")
    public Result getbuydetail(@PathVariable Integer id) {
        log.info("获取求购表单{}",id);
        BuyItem buyItem = mineService.getbuyDetail(id);
        return Result.success(buyItem);
    }

    @GetMapping("/getunuseddetail/{id}")
    public Result getunuseddetail(@PathVariable Integer id) {
        log.info("获取闲置表单{}",id);
        UnusedItem unusedItem = mineService.getunusedDeail(id);
        return Result.success(unusedItem);
    }

    @PostMapping("/updatebuy")
    public Result updatebuy(@RequestBody BuyItem buyItem) {
        log.info("修改求购表单{}",buyItem);
        mineService.updatebuy(buyItem);
        return Result.success();
    }

//    @PostMapping("/updateunused")
//    public Result updateunused(@RequestBody UnusedItem unusedItem) {
//        log.info("修改闲置表单{}",unusedItem);
//        mineService.updateUnused(unusedItem);
//        return Result.success();
//    }

    @PostMapping("/deletebuy/{id}")
    public Result deletebuy(@PathVariable Integer id) {
        log.info("删除求购表单{}",id);
        mineService.deletebuy(id);
        return Result.success();
    }

    @PostMapping("/hidebuy/{id}")
    public Result hidebuy(@PathVariable Integer id) {
        log.info("下架/上架求购表单{}",id);
        mineService.hidebuy(id);
        return Result.success();
    }

    @PostMapping("/updateunused")
    public Result updateUnused(@RequestBody UnusedItem unusedItem) {
        log.info("修改闲置表单{}", unusedItem);
        mineService.updateUnused(unusedItem);
        return Result.success();
    }

    @PostMapping("/deleteunused/{id}")
    public Result deleteUnused(@PathVariable Integer id) {
        log.info("删除闲置表单{}", id);
        mineService.deleteUnused(id);
        return Result.success();
    }

    @PostMapping("/hideunused/{id}")  // 修正了${id}为{id}的路径变量语法
    public Result hideUnused(@PathVariable Integer id) {
        log.info("下架/上架闲置表单{}", id);
        mineService.hideUnused(id);
        return Result.success();
    }

    @GetMapping("/payorder/{id}")
    public Result payorder(@PathVariable Integer id) {
        log.info("获取购买订单列表{}", id);
        List<PayOrder> payOrderList = mineService.getPayorder(id);
        return Result.success(payOrderList);
    }

    @GetMapping("/saleorder/{id}")
    public Result saleorder(@PathVariable Integer id) {
        log.info("获取购买订单列表{}", id);
        List<SaleOrder> saleOrderList = mineService.getSaleorder(id);
        return Result.success(saleOrderList);
    }

    @GetMapping("/payorderdetail/{id}")
    public Result payorderdetail(@PathVariable Integer id) {
        log.info("获取购买订单详情{}", id);
        OrderDetail orderDetail = mineService.getPayorderDetail(id);
        return Result.success(orderDetail);
    }

    @GetMapping("/saleorderdetail/{id}")
    public Result saleorderdetail(@PathVariable Integer id) {
        log.info("获取出售订单详情{}", id);
        OrderDetail orderDetail = mineService.getSaleorderDetail(id);
        return Result.success(orderDetail);
    }

    @PostMapping("/reset")
    public Result reset(@RequestBody ResetParam resetParam) {
        log.info("修改密码{}", resetParam);
        mineService.reset(resetParam);
        return Result.success("修改密码成功！");
    }
}
