<template>
  <view class="order-page">
    <!-- 顶部搜索栏 -->
    <view class="search-bar">
      <uni-search-bar placeholder="搜索我的订单" radius="100"></uni-search-bar>
      <view class="filter-btn">
<!--        <text>筛选</text> -->
<!--        <uni-icons type="arrowdown" size="16"></uni-icons> -->
      </view>
<!--      <uni-badge text="5" type="error"></uni-badge> -->
    </view>

    <!-- 订单状态选项卡 -->
    <scroll-view scroll-x class="tab-bar">
      <text v-for="tab in tabs" :key="tab" 
            :class="{active: activeTab === tab}">
        {{tab}}
      </text>
    </scroll-view>

    <!-- 订单列表 -->
    <scroll-view scroll-y class="order-list">
      <view v-for="order in payorders" :key="order.id" class="order-item">
        <!-- 店铺信息 -->
        <view class="shop-header">
          <text class="shop-name">Revalue平台闲置</text>
          <text class="status">完成</text>
        </view>
        
        <!-- 商品信息 -->
        <view class="product-info">
          <image :src="order.image" mode="aspectFill"></image>
          <view class="detail">
            <view class="title-line">
              <text class="title">{{order.name}}</text>
              <text class="price">￥{{order.price}}</text>
            </view>
            <text class="desc">{{order.description}}</text>
            <view class="tags">
              <text v-for="tag in tags" >{{tag}}</text>
            </view>
          </view>
        </view>

        <!-- 订单操作 -->
        <view class="action-buttons">
          <button v-for="action in actions" 
                  :key="action" 
                  :class="action === '再次购买' ? 'buy-again' : ''"
				  @click="checkorder(order.id)">
            {{action}}
          </button>
        </view>
      </view>
    </scroll-view>
	<uni-popup ref="popup" type="right" :mask-click="false">
	    <view class="order-detail">
	      <!-- 顶部标题栏 -->
	      <view class="header">
	        <text class="title">订单详情</text>
	        <uni-icons type="close" size="24" @click="close"></uni-icons>
	      </view>
	      
	      <!-- 订单状态 -->
	      <view class="status-bar">
	        <text class="status">已完成</text>
<!-- 	        <uni-badge text="5" type="error"></uni-badge> -->
	      </view>
	      
	      <!-- 店铺信息 -->
	      <view class="shop-info">
	        <text class="name">Revalue平台闲置</text>
	      </view>
	      
	      <!-- 商品信息 -->
	      <view class="product-card">
	        <image :src="orderDetail.image" mode="aspectFill"></image>
	        <view class="info">
	          <text class="title">{{orderDetail.name}}</text>
<!-- 	          <text class="author">陈守孔</text> -->
<!-- 	          <text class="price">到手￥79.8</text> -->
	          <text class="spec" style="margin-left: 80px;">数量×1</text>
	          <view class="tags">
	            <text>官方正版</text>
	            <text>7天价保</text>
	            <text>现货包邮</text>
	          </view>
	        </view>
	      </view>
	      
	      <!-- 订单信息 -->
	      <view class="order-info">
	        <view class="row">
	          <text>实付款</text>
	          <text class="highlight">合计￥{{orderDetail.price}}</text>
	        </view>
	        <view class="row">
	          <text>订单编号</text>
	          <text>3133942962{{orderDetail.id}} | 复制</text>
	        </view>
	        <view class="row">
	          <text>支付方式</text>
	          <text>微信支付</text>
	        </view>
	        <view class="row">
	          <text>下单时间</text>
	          <text>{{orderDetail.createTime}}</text>
	        </view>
	        <view class="row">
	          <text>配送方式</text>
	          <text>卖家自行配送或约定取货地点</text>
	        </view>
	      </view>
	      
	      <!-- 收货信息 -->
<!-- 	      <view class="address-info">
	        <text class="section-title">收货信息</text>
	        <text class="name">张俊鑫 182****1714</text>
	        <text class="address">湖北武汉市洪山区珞南街道雄楚大道</text>
	        <text class="detail">武汉理工大学南湖（校区）智园12舍409</text>
	      </view> -->
	      
	      <!-- 操作按钮 -->
<!-- 	      <view class="action-buttons">
	        <button v-for="action in actions" 
	                :key="action" 
	                :class="{'primary-btn': action === '评价晒单'}">
	          {{action}}
	        </button>
	      </view> -->
	    </view>
	  </uni-popup>
  </view>
</template>

<script setup>
import { ref,onMounted } from 'vue'
import { getpayorder, payorderDetail } from '../../api/mine/mine.js'

const activeTab = ref('已完成')
const tabs = ['待收货', '待服务', '已完成', '待评价', '已取消']

const popup = ref(null)

const tags = ref(['完整版', '7天价保'])

// const completedOrders = ref([
//   {
//     id: 1,
//     shop: '天地教育图书旗舰店',
//     image: '/static/book1.jpg',
//     title: '机工算法与数据结构考研试题精析 第4版',
//     desc: '陈守孔 编 结构考研试题精析 第4版...',
//     price: '79.80',
//     tags: ['官方正版', '7天价保', '现货包邮']
//   },
//   {
//     id: 2,
//     shop: '永泰天成旗舰店',
//     image: '/static/charger.jpg',
//     title: '蜜玛适用华为66W充电器',
//     desc: 'type-c超级快充头Mate7...',
//     price: '39.45',
//     tags: ['全国联保', '冰芯不发热']
//   },
//   {
//     id: 3,
//     shop: '墨趣书简图书专营店',
//     image: '/static/book2.jpg',
//     title: '平凡的世界 全三册',
//     desc: '【新华书店直发】平凡的世界全三册路遥著...',
//     price: '75.00',
//     tags: ['完整版', '7天价保']
//   }
// ])

const currentUserId = ref()

const payorders = ref([])

onMounted(async() => {
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    if (loginUser && loginUser.id) {
        currentUserId.value = loginUser.id;
    }
	console.log(currentUserId.value)
	await InitPayorder()
})

const InitPayorder = async()=>{
	const res = await getpayorder(currentUserId.value)
	if(res.code){
		payorders.value = res.data
	}
}

const orderDetail = ref({
	id:'',
	name:'',
	itemId:'',
	buyerId:'',
	buyerName:'',
	sellerId:'',
	sellerName:'',
	price:'',
	createTime:'',
	image:'',
	description:''
})

const checkorder = async(id)=>{
	const res = await payorderDetail(id)
	if(res.code){
		orderDetail.value = res.data
	}
	popup.value.open()
}

const close = ()=>{
	popup.value.close()
}

const actions = [ '售后', '查看订单']
</script>

<style lang="scss">
.order-page {
  background: #f5f5f5;
  min-height: 100vh;
  
  .search-bar {
    display: flex;
    align-items: center;
    padding: 20rpx;
    background: #fff;
    
    .filter-btn {
      margin: 0 20rpx;
    }
  }
  
  .tab-bar {
    white-space: nowrap;
    background: #fff;
    padding: 20rpx 0;
    
    text {
      display: inline-block;
      padding: 0 30rpx;
      color: #666;
      
      &.active {
        color: #e93b3b;
        font-weight: bold;
      }
    }
  }
  
  .order-item {
    margin: 20rpx;
    background: #fff;
    border-radius: 10rpx;
    overflow: hidden;
    
    .shop-header {
      display: flex;
      justify-content: space-between;
      padding: 20rpx;
      border-bottom: 1rpx solid #eee;
      
      .shop-name {
        font-weight: bold;
      }
      
      .status {
        color: #999;
      }
    }
    
    .product-info {
      display: flex;
      padding: 20rpx;
      
      image {
        width: 160rpx;
        height: 160rpx;
        margin-right: 20rpx;
      }
      
      .title-line {
        display: flex;
        justify-content: space-between;
        
        .price {
          color: #e93b3b;
          font-weight: bold;
        }
      }
      
      .tags {
        margin-top: 10rpx;
        
        text {
          display: inline-block;
          padding: 4rpx 10rpx;
          background: #f8f8f8;
          border-radius: 4rpx;
          margin-right: 10rpx;
          font-size: 24rpx;
          color: #666;
        }
      }
    }
    
    .action-buttons {
	  margin-left: 150px;
      display: flex;
      justify-content: flex-end;
      padding: 20rpx;
      border-top: 1rpx solid #eee;
      
      button {
        margin-left: 20rpx;
        padding: 0 30rpx;
        height: 60rpx;
        line-height: 60rpx;
        font-size: 28rpx;
        background: #fff;
        border: 1rpx solid #ddd;
        
        &.buy-again {
          color: #e93b3b;
          border-color: #e93b3b;
        }
      }
    }
  }
}
.order-detail {
  width: 85vw;
  height: 100vh;
  background: #fff;
  padding: 30rpx;
  
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 20rpx;
    border-bottom: 1rpx solid #eee;
    
    .title {
      font-size: 36rpx;
      font-weight: bold;
    }
  }
  
  .status-bar {
    display: flex;
    align-items: center;
    margin: 20rpx 0;
    
    .status {
      font-size: 32rpx;
      margin-right: 20rpx;
    }
  }
  
  .product-card {
    display: flex;
    margin: 30rpx 0;
    padding-bottom: 30rpx;
    border-bottom: 1rpx solid #eee;
    
    image {
      width: 180rpx;
      height: 240rpx;
      margin-right: 20rpx;
      border-radius: 8rpx;
    }
    
    .info {
      flex: 1;
      
      .title {
        font-size: 32rpx;
        line-height: 1.4;
      }
      
      .author {
        color: #666;
        margin: 10rpx 0;
      }
      
      .price {
        color: #e93b3b;
        font-size: 36rpx;
        font-weight: bold;
      }
      
      .spec {
        color: #999;
        margin: 10rpx 0;
      }
      
      .tags {
        margin-top: 20rpx;
        
        text {
          display: inline-block;
          padding: 6rpx 12rpx;
          background: #f5f5f5;
          border-radius: 6rpx;
          margin-right: 10rpx;
          font-size: 24rpx;
          color: #666;
        }
      }
    }
  }
  
  .row {
    display: flex;
    justify-content: space-between;
    padding: 20rpx 0;
    border-bottom: 1rpx dashed #eee;
    
    .highlight {
      color: #e93b3b;
    }
  }
  
  .address-info {
    margin-top: 30rpx;
    padding: 20rpx;
    background: #f9f9f9;
    border-radius: 8rpx;
    
    .section-title {
      font-weight: bold;
      margin-bottom: 10rpx;
    }
    
    .name {
      margin: 10rpx 0;
    }
    
    .address, .detail {
      color: #666;
      line-height: 1.6;
    }
  }
  
  .action-buttons {
    display: flex;
    flex-wrap: wrap;
    margin-top: 40rpx;
    
    button {
      flex: 1;
      min-width: 30%;
      margin: 10rpx;
      padding: 0 20rpx;
      height: 70rpx;
      line-height: 70rpx;
      font-size: 28rpx;
      background: #fff;
      border: 1rpx solid #ddd;
      
      &.primary-btn {
        color: #e93b3b;
        border-color: #e93b3b;
      }
    }
  }
}
</style>