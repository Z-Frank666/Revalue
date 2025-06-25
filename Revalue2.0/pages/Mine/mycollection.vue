<template>
  <view class="container">

    <!-- 商品列表 -->
    <view class="item-list" 
        v-for="item in collectionList" 
        :key="item.id">
		<view class="header">
		  <text class="main-title">{{item.name}}</text>
		  <text class="tag">{{item.tag}}</text>
		</view>
      <!-- 笔记本电脑 -->
      <view class="item-card">
        <image class="item-image" :src="item.image" mode="aspectFill"></image>
        <view class="item-info">
          <text class="item-title">{{item.name}}</text>
          <text class="item-price">¥{{item.price}}</text>
          <view class="meta-info">
            <text class="publish-time">发布时间{{item.createTime}}</text>
          </view>
          <view class="action-buttons">
            <button class="btn view-btn" @click="viewDetail(item.unusedId)">查看</button>
            <button class="btn cancel-btn" @click="cancelCollect(item.id)">取消收藏</button>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>
	
<script setup>
import { ref,onMounted } from 'vue'
import { incollectById, myCollections } from '../../api/mine/mine.js';

const currentUserId = ref()

onMounted(async() => {
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    if (loginUser && loginUser.id) {
        currentUserId.value = loginUser.id;
    }
	console.log(currentUserId.value)
	await getCollections(currentUserId.value)
})

const collectionList = ref([])

const getCollections = async(userId)=>{
	const res = await myCollections(userId)
	if(res.code){
		collectionList.value = res.data
	}
}


const viewDetail = (id) => {
  uni.navigateTo({
    url: `/pages/Unused/detail?id=${id}`
  })
}


const cancelCollect = async(id) => {
  uni.showModal({
    title: '确认取消',
    content: '确定要取消收藏该商品吗？',
    success(res) {
      if (res.confirm) {
		const res = incollectById(id)
      }
    }
  })
  uni.showToast({
    title: '已取消收藏',
    icon: 'success'
  })
  await getCollections(currentUserId.value)
}
</script>

<style lang="scss">
.container {
  padding: 20rpx;
  background-color: #fff;
}

.header {
  display: flex;
  align-items: center;
  margin-bottom: 30rpx;
  
  .main-title {
    font-size: 36rpx;
    font-weight: bold;
    margin-right: 20rpx;
  }
  
  .tag {
    font-size: 24rpx;
    color: #999;
    background-color: #f5f5f5;
    padding: 6rpx 12rpx;
    border-radius: 8rpx;
  }
}

.item-list {
	border-radius: 12px;
	padding: 15px;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  .item-card {
    display: flex;
    margin-bottom: 30rpx;
    border-bottom: 1rpx solid #eee;
    padding-bottom: 30rpx;
    
    .item-image {
      width: 220rpx;
      height: 220rpx;
      border-radius: 12rpx;
      margin-right: 20rpx;
    }
    
    .item-info {
      flex: 1;
      
      .item-title {
        font-size: 32rpx;
        font-weight: bold;
        margin-bottom: 10rpx;
        display: block;
      }
      
      .item-price {
        font-size: 36rpx;
        color: #f44;
        margin-bottom: 20rpx;
        display: block;
      }
      
      .meta-info {
        display: flex;
        justify-content: space-between;
        margin-bottom: 30rpx;
        
        .publish-time {
          font-size: 24rpx;
          color: #999;
        }
        
        .quantity {
          font-size: 24rpx;
          color: #999;
        }
      }
      
      .action-buttons {
        display: flex;
        justify-content: flex-end;
        
        .btn {
          height: 60rpx;
          line-height: 60rpx;
          font-size: 28rpx;
          margin-left: 20rpx;
          border-radius: 30rpx;
          padding: 0 30rpx;
          
          &.view-btn {
            background-color: #4a90e2;
            color: white;
          }
          
          &.cancel-btn {
            background-color: #f5f5f5;
            color: #666;
          }
        }
      }
    }
  }
}
</style>