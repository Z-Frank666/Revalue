<template>
  <view class="container">
    <!-- 状态栏 -->
	<uni-search-bar 
	      placeholder="请输入关键词"
	      cancelButton="none"
	      :radius="100"
	      v-model="keyword"
	      @confirm="handleSearch"
	      @clear="handleClear"
	      style="margin-top: 5px;"
	    >
	</uni-search-bar>
	
	<view class="tabs-container">
	  <scroll-view class="tabs-scroll" scroll-x :show-scrollbar="false">
	    <view 
	      class="tab-item" 
	      v-for="tab in tabs" 
	      :key="tab.id"
	      :class="{ active: activeTab === tab.id }"
	      @click="switchTab(tab.id)"
	    >
	      <text>{{ tab.name }}</text>
	    </view>
	  </scroll-view>
	</view>

    <!-- 求购消息列表 -->
    <scroll-view class="content" scroll-y>
      <view class="item-card" v-for="(item, index) in sortedItems" :key="index" @click="navigatorTodetail(item.id)">
        <view class="user-info" v-if="item.isactive==true">
          <image class="avatar" :src="item.avatar" mode="aspectFill"></image>
          <view class="user-detail">
            <text class="nickname">{{ item.nickname }}</text>
            <text class="identity">{{ item.identity }}</text>
          </view>
        </view>
        
        <view class="item-content" v-if="item.isactive==true">
          <text class="item-title">{{ item.title }}</text>
          <text class="item-desc">{{ item.description }}</text>
          <view class="tags">
            <text class="tag">#{{ item.tag }}</text>
            <text class="time">{{ item.time }}</text>
            <text class="view-count" v-if="item.viewCount">{{ item.viewCount }}人围观</text>
          </view>
        </view>
        <view class="item-images" v-if="item.isactive ==true">
          <text class="image-count" v-if="item.imgCnt">{{ item.imgCnt }}张图片</text>
        </view>
      </view>
    </scroll-view>
  </view>
</template>

<script setup>
import { ref,onMounted,computed } from 'vue'
import {buySearch, getallTags,showAll,showBySort } from '../../api/buy/buy.js'

const keyword = ref('')

const handleSearch = async()=>{
	console.log(keyword.value)
	const res = await buySearch(keyword.value)
	if(res.code){
		itemList.value = res.data
	}
}

const handleClear = async()=>{
	keyword.value = ''
	await Initbuy();
}

const tabs = ref([
])

const sortedItems = computed(() => {
  return [...itemList.value].sort((a, b) => b.id - a.id)
})

// 当前选中标签（默认）
const activeTab = ref(1) // 

const navigatorTodetail = (id)=>{
	uni.navigateTo({
		url: `/pages/Buy/detail?id=${id}`
	});
}

// 切换标签方法
const switchTab = async(tabId) => {
  activeTab.value = tabId
  const res = await showBySort(tabId);
  if(res.code&&tabId!=1){
	  itemList.value = res.data;
	  console.log(res.data)
  }else if(tabId==1){
	  Initbuy();
  }
}

const currentUserId = ref(0)

onMounted(async() => {
	
  await InitTags();
  await Initbuy();
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if (loginUser && loginUser.id) {
    currentUserId.value = loginUser.id;
  }
  console.log(currentUserId.value)
  console.log(itemList.value)
})

const InitTags = async()=>{
	const res = await getallTags();
	if(res.code){
		console.log(res.data);
		tabs.value = res.data;
	}
}

const Initbuy = async()=>{
	const res = await showAll();
	if(res.code){
		console.log(res.data);
		itemList.value = res.data
	}
}


const itemList = ref([
  {
	id:'',
    avatar: '/static/avatars/1.jpg',
    nickname: '初中生',
    title: '出卡西欧计算器fx991cm',
    description: '',
    tag:'',
    time: '刚刚',
    imgCnt: 3
  },
  {
	id:'',
    avatar: '/static/avatars/2.jpg',
    nickname: 'boaster',
    title: '迈欧健身',
    description: '出一张迈欧健身卡，大概到今年11月-12月',
    sortId:2,
    time: '刚刚',
    imgCnt: 3
  },
  {
	id:'',
    avatar: '/static/avatars/3.jpg',
    nickname: '小雪人',
    title: '优时颜洗面奶拼单',
    description: '李佳琦61834.75/支',
    sortId:2,
    time: '刚刚',
    imgCnt: 2
  },
  {
	id:'',
    avatar: '/static/avatars/4.jpg',
    nickname: '求求求求求求求',
    title: '出课外书出二手课外书',
    description: '东野圭吾《[时生](@replace=10001)》《[恶意](@replace=10002)》两本7块，赠送幻夜和白夜行（因为目测盗版老舍的茶馆，刘震云的《[一句顶一万句](@replace=10003)》，鲁迅的《[故事新编](@replace=10004)》，余华《[第七天](@replace=10005)》和《[我们生活在巨大的差距里](@replace=10006)》，莫言《[红高粱](@replace=10007)》和《天堂蒜苔[](@replace=10008)》，一本5块，3本12,5本18，七本21，赠送《[芙蓉镇](@replace=10009)》《[牵风记](@replace=10010)》《[他的国](@replace=10011)》《[罪与罚](@replace=10012)》8块赠送《[华为传](@replace=10013)》《[房思琪的初恋乐园](@replace=10014)》3块《[人类简史](@replace=10015)》5块《[红星照耀中国](@replace=10016)》5块，赠送我dang简史，明天青年节，再多送《论dang的青年工作[](@replace=10017)》咨询自提送货请加v，南湖南院',
    sortId:2,
    time: '30分钟前',
    imgCnt: 1
  }
])

</script>

<style lang="scss">
// .container {
//   background-color: #fff;
//   height: 100vh;
//   display: flex;
//   flex-direction: column;
// }

.tabs-container {
  background: white;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
  
  .tabs-scroll {
    white-space: nowrap;
    
    .tab-item {
      display: inline-block;
      padding: 0 16px;
      
      text {
        font-size: 15px;
        color: #333;
        position: relative;
      }
      
      &.active {
        text {
          color: #1890ff; /* 蓝色 */
          font-weight: 500;
          
          &::after {
            content: '';
            position: absolute;
            bottom: -8px;
            left: 0;
            right: 0;
            height: 3px;
            background: #1890ff;
            border-radius: 2px;
          }
        }
      }
      
      & + .tab-item {
        margin-left: 8px;
      }
    }
    
    /* 隐藏滚动条 */
    &::-webkit-scrollbar {
      display: none;
    }
  }
}  

// .nav-bar {
//   white-space: nowrap;
//   padding: 20rpx 0;
//   border-bottom: 1rpx solid #f0f0f0;
//   .nav-item {
//     display: inline-block;
//     padding: 0 30rpx;
//     font-size: 32rpx;
//     color: #666;
//     &.active {
//       color: #ff5500;
//       font-weight: bold;
//       position: relative;
//       &::after {
//         content: '';
//         position: absolute;
//         bottom: -10rpx;
//         left: 50%;
//         transform: translateX(-50%);
//         width: 80rpx;
//         height: 6rpx;
//         background-color: #ff5500;
//         border-radius: 3rpx;
//       }
//     }
//   }
// }

.content {
  flex: 1;
  padding: 20rpx 30rpx;
}

.item-card {
  padding: 30rpx 0;
  border-bottom: 1rpx solid #f5f5f5;
  .user-info {
    display: flex;
    align-items: center;
    margin-bottom: 20rpx;
    .avatar {
      width: 80rpx;
      height: 80rpx;
      border-radius: 50%;
      margin-right: 20rpx;
    }
    .user-detail {
      display: flex;
      flex-direction: column;
      .nickname {
        font-size: 32rpx;
        font-weight: bold;
      }
      .identity {
        font-size: 24rpx;
        color: #999;
      }
    }
  }
  .item-content {
    margin-bottom: 20rpx;
    .item-title {
      font-size: 36rpx;
      font-weight: bold;
      display: block;
      margin-bottom: 10rpx;
    }
    .item-desc {
      font-size: 30rpx;
      color: #333;
      display: block;
      margin-bottom: 15rpx;
      line-height: 1.5;
    }
    .tags {
      display: flex;
      align-items: center;
      .tag {
        font-size: 24rpx;
        color: #ff5500;
        margin-right: 20rpx;
      }
      .time, .view-count {
        font-size: 24rpx;
        color: #999;
        margin-right: 20rpx;
      }
    }
  }
  .item-images {
    .image-count {
      font-size: 24rpx;
      color: #999;
    }
  }
}
</style>
