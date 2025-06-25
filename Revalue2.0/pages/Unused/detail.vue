<template>
  <view class="detail-container">
	<!-- 轮播图区域 -->
	<swiper class="swiper" indicator-dots autoplay circular>
	  <swiper-item v-for="(item, index) in imgs" :key="index">
		<image :src="item" mode="aspectFill" class="swiper-img"></image>
	  </swiper-item>
	</swiper>
	<!-- 商品信息区域 -->
	<view class="goods-info">
	  <view class="price-section">
		<text class="price">￥{{detail.price}}</text>
	  </view>
	  <text class="title">{{detail.name}}</text>
	  
	  <view class="info-item">
		<uni-section class="section" title="商品简介" sub-title="" type="line" titleColor="orange" titleFontSize="18px"></uni-section>
		<text class="desc">{{detail.description}}</text>
	  </view>
	  <view class="info-item">
		<uni-section class="section" title="商品位置" sub-title="" type="line" titleColor="orange" titleFontSize="18px"></uni-section>
		<text class="value">西北风大学</text>
	  </view>
	  
	  <view class="info-item">
		  <uni-section class="section" title="交易流程" sub-title="" type="line" titleColor="orange" titleFontSize="18px"></uni-section>
		  <uni-steps :options="list" :active="active" active-color="#FF7670" style="width: 100%;" />
	  </view>
	  
	  <view class="info-item">
	  	<uni-section class="section" title="交易方式" sub-title="" type="line" titleColor="orange" titleFontSize="18px"></uni-section>
		<text class="value">自行协商，自提或送货上门约定交易点当面验货交易</text>	
	  </view>

	  <view class="info-item">
		<uni-section class="section" title="发布时间" sub-title="" type="line" titleColor="orange" titleFontSize="18px"></uni-section>
		<text class="value">{{detail.createTime}}</text>
	  </view>
	  <view class="info-item" style="margin-bottom: 80px;">
	  	<uni-section class="section" title="联系方式" sub-title="" type="line" titleColor="orange" titleFontSize="18px"></uni-section>
	  	<text class="value">{{detail.phone}}</text>
	  </view>
	</view>
	<view class="navigation" style="margin-top: 40px;">
		<view class="left">
			<view class="item">
				<uni-icons type="home" size="25"></uni-icons>
				<view class="text-button">首页</view>
			</view>
			<view class="item" @click="collectchange">
				<uni-icons v-if="isCollect" type="star-filled" size="25"></uni-icons>
				<uni-icons v-else type="star" size="25"></uni-icons>
			    <text class="text-button" v-if="isCollect">取消收藏</text>
			    <text class="text-button" v-else>收藏</text>
			</view>
			<view class="item">
				<uni-icons type="info" size="25"></uni-icons>
				<view class="text-button">举报</view>
			</view>
		</view>
		<view class="right">
			<view class="phoneCall">电话咨询</view>
			<view class="quickDeal" @click="quickDeal">立即交易</view>
		</view>
	</view>
  </view>
</template>

<script setup>
import { ref,onMounted } from 'vue'
import { onLoad } from '@dcloudio/uni-app'  // 必须引入
import { checkcollect, getDetail, getImgs, incollect,collect, quickdeal } from '../../api/unused/unused.js'

// 接收参数
const itemId = ref(null)

const imgs = ref([])

const detail = ref({
	id:itemId.value,
	name:'',
	description:'',
	price:'',
	linkman:'',
	phone:'',
	createTime:''
})

const list = ref([
	{
		title: '发布信息'
	}, {
		title: '电话/微信沟通'
	}, {
		title: '当面交易'
	}, {
		title: '交易完成'
	}
])

onMounted(async() => {
	await InitImgs(itemId.value);
	await Initdetail(itemId.value);
	collectionTemp.value.unusedId = itemId.value
    console.log(imgs.value)
	const loginUser = JSON.parse(localStorage.getItem('loginUser'));
	if (loginUser && loginUser.id) {
	    collectionTemp.value.userId = loginUser.id;
	}
	const res = await checkcollect(collectionTemp.value)
	if(res.code){
		isCollect.value = res.data
		console.log(res.data)
	}
})

const InitImgs = async(id)=>{
	const res = await getImgs(id);
	if(res.code){
		imgs.value = res.data;
		console.log(res.data)
	}
}

const Initdetail = async(id)=>{
	const res = await getDetail(id);
	if(res.code){
		detail.value = res.data
		console.log(res.data)
	}
}

onLoad((options) => {
  // 参数验证
  if (options?.id) {
    itemId.value = options.id
    console.log('接收到的ID:', itemId.value)
    
    // 这里可以添加根据ID加载数据的逻辑
    // loadData(itemId.value)
  } else {
    console.warn('未接收到ID参数')
    uni.showToast({
      title: '参数错误',
      icon: 'error'
    })
  }
})

const isCollect = ref(false);

const collectionTemp = ref(
{
	id:1,
	unusedId:1,
	userId:1
})

const collectchange = async()=>{
	console.log(isCollect.value)
	if(isCollect.value == false){
		const res = await collect(collectionTemp.value)
		if(res.code){
			collectionTemp.value = res.data
			uni.showToast({
			  title: '收藏成功',
			  icon: 'success'
			})
		}	
	}else{
		const res = await incollect(collectionTemp.value)
		if(res.code){
			collectionTemp.value = res.data
			uni.showToast({
			  title: '已取消收藏',
			  icon: 'success'
			})
		}
	}
	isCollect.value = !isCollect.value
}

const quickDeal = async()=>{
	const res = await quickdeal(collectionTemp.value.userId,itemId.value)
	if(res.code){
		console.log(res.data)
	}
	uni.showToast({
		title: res.data,
		icon: 'none'
	})
}


</script>

<style lang="scss">
.swiper {
  width: 100%;
  height: 375px;
  
  &-img {
    width: 100%;
    height: 100%;
  }
}

.goods-info {
  padding: 20px;
  
  .price-section {
    display: flex;
    align-items: flex-end;
    margin-bottom: 10px;
    
    .price {
      font-size: 24px;
      color: #f44;
      font-weight: bold;
    }
    
    .original-price {
      font-size: 14px;
      color: #999;
      text-decoration: line-through;
      margin-left: 10px;
    }
  }
  
  .title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
    display: block;
  }
  
  .desc {
    font-size: 14px;
    color: #666;
    line-height: 1.5;
    margin-bottom: 15px;
    display: block;
  }
  
  .info-item {
    display: block;
    margin-bottom: 10px;
    
    .section {
      
    }
    
    .value {
      flex: 1;
      color: #333;
    }
  }
}

.navigation {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 60px;
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 10px;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
  z-index: 100;
}

/* 左侧按钮组 */
.left {
  display: flex;
  flex: 1;
}

.item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 0 12px;
}

.uni-icons {
  font-size: 20px;
  color: #666;
}

.text-button {
  font-size: 12px;
  color: #666;
  margin-top: 4px;
}

/* 右侧操作按钮 */
.right {
  display: flex;
  align-items: center;
}

.phoneCall {
  height: 36px;
  line-height: 36px;
  padding: 0 15px;
  border: 1px solid #ddd;
  border-radius: 18px;
  font-size: 14px;
  background-color: #ff0000;
  color: #fff;
  margin-right: 10px;
}

.quickDeal {
  height: 36px;
  line-height: 36px;
  padding: 0 20px;
  background-color: #ff5500;
  color: #fff;
  border-radius: 18px;
  font-size: 14px;
  font-weight: 500;
}

/* 交互效果 */
.item:active .uni-icons,
.item:active .text-button {
  color: #f44;
}

.phoneCall:active {
  background-color: #ff7670;
}

.quickDeal:active {
  background-color: #e33;
}
</style>