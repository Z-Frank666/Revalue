<template>
  <view>
    <!-- 搜索栏 -->
    <view class="search-bar">
      <input 
        placeholder="搜索" 
        v-model="searchText" 
        confirm-type="search"
        @confirm="handleSearch"
      />
    </view>
    <!-- 兴趣标签 -->
	<scroll-view class="tag-scroll" scroll-x :show-scrollbar="false">
	  <view 
		v-for="tab in tabs" 
		:key="tab.id"
		class="tag" 
		:class="{ active: activeTag === tab.id }"  
		@click="setActiveTag(tab.id)"             
	  >{{ tab.name }}</view>
	</scroll-view>
	
	<!-- 分类宫格 -->
	<view class="category-grid">
	  <view class="category-item" v-for="item in categories" :key="item.name">
		<image :src="item.icon" />
		<text>{{item.name}}</text>
	  </view>
	</view>
	
	 <view class="waterfall-container">
	    <!-- 两列瀑布流 -->
	    <view class="waterfall-column" v-for="(column, index) in columns" :key="index">
	      <view 
	        class="product-card" 
	        v-for="item in column" 
	        :key="item.id"
	      >
	        <image class="product-image" :src="item.image" mode="aspectFill" />
	        <view class="product-info">
	          <text class="product-title">{{item.title}}</text>
	          <view class="price-section">
	            <text class="price">¥{{item.price}}</text>
	            <text class="original-price" v-if="item.originalPrice">¥{{item.originalPrice}}</text>
	          </view>
	          <text class="shop-name">{{item.shop}}</text>
	        </view>
	      </view>
	    </view>
	  </view>
	
  </view>
</template>

<script setup>
import { ref,onMounted } from 'vue'
import { getallProduct, getallTags, queryProductApi } from '../../api/product/product.js'


// 标签数据
const tabs = ref([

])

// 当前选中标签（改为存储id）
const activeTag = ref(1) // 默认选中"推荐"标签(id=1)

// 切换标签方法（改为接收id参数）
const setActiveTag = async(tabId) => {
  activeTag.value = tabId
  const res = await queryProductApi(tabId)
  if(res.code&&tabId!=1){
	  products.value = res.data
	  console.log(res.data)
  }else if(tabId == 1){
	  await InitRecommend();
  }
  //清空columns
  columns.value = [[], []]
  // 简单分配算法：交替分配到两列
  products.value.forEach((item, index) => {
    columns.value[index % 2].push(item)
  })
}

const searchText = ref('')
const categories = ref([
  { name: '手机', icon: '/static/iphone.png' },
  { name: '平板', icon: '/static/ipad.png' },
  { name: '笔记本', icon: '/static/matebook.png' },
  { name: '首饰配饰', icon: '/static/glass.png' },
  { name: '摄影摄像', icon: '/static/camera.png' },
  { name: '图书小说', icon: '/static/book.png' },
  { name: '耳机',icon: '/static/airpods.png'},
  { name: '潮鞋潮服',icon: '/static/converse.png'},
  { name: '乐器',icon: '/static/yueqi.png'},
  { name: '运动器材',icon: '/static/yonex.png'}
])

const handleSearch = () => {
  // 搜索逻辑
}


// 商品数据（封装在script中）
const products = ref([

])

// 将商品分配到两列
const columns = ref([[], []])

onMounted(async() => {
	
  await InitTags();
  await InitRecommend();
  console.log(products.value)
  // 简单分配算法：交替分配到两列
  products.value.forEach((item, index) => {
    columns.value[index % 2].push(item)
  })
  
  // 更高级的实现可以计算图片高度后动态分配到较矮的列[3,4](@ref)
})

const InitTags = async()=>{
	const res = await getallTags();
	if(res.code){
		console.log(res.data);
		tabs.value = res.data;
	}
}

const InitRecommend = async()=>{
	const res = await getallProduct();
	if(res.code){
		console.log(res.data);
		products.value = res.data;
	}
}

</script>

<style lang="scss">
.search-bar {
  position: relative;
  margin: 5px 20px 15px;
  
  input {
    background: #F5F5F5;
    border-radius: 20px;
    height: 40px;
    padding-left: 45px;
    font-size: 14px;
  }
  
  &::before {
    content: '';
    position: absolute;
    left: 15px;
    top: 10px;
    width: 20px;
    height: 20px;
    background: url('@/static/search-icon.png') no-repeat;
    background-size: contain;
  }
}

.tag-scroll {
  white-space: nowrap;
  padding: 0 20px 15px;
  
  .tag {
    display: inline-block;
    padding: 6px 15px;
    background: #F5F5F5;
    border-radius: 15px;
    margin-right: 10px;
    font-size: 13px;
    cursor: pointer;
    
    &.active {
      background: #2c2c2c;
      color: white;
    }
  }
}

.category-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 15px;
  padding: 15px 20px;
  border-bottom: 10px solid #F5F5F5;
  
  .category-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    
    image {
      width: 40px;
      height: 40px;
      margin-bottom: 5px;
    }
    
    text {
      font-size: 12px;
      color: #333;
      text-align: center;
    }
  }
}

.waterfall-container {
  display: flex;
  padding: 10px;
  background-color: #f5f5f5;
  min-height: 100vh;
  
  .waterfall-column {
    flex: 1;
    padding: 0 5px;
    
    .product-card {
      background: white;
      border-radius: 8px;
      overflow: hidden;
      margin-bottom: 10px;
      box-shadow: 0 1px 3px rgba(0,0,0,0.1);
      
      .product-image {
        width: 100%;
        height: 180px;
      }
      
      .product-info {
        padding: 10px;
        
        .product-title {
          font-size: 14px;
          line-height: 1.4;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
          overflow: hidden;
        }
        
        .price-section {
          margin-top: 8px;
          display: flex;
          align-items: center;
          
          .price {
            font-size: 16px;
            font-weight: bold;
            color: #ff5500;
            margin-right: 8px;
          }
          
          .original-price {
            font-size: 12px;
            color: #999;
            text-decoration: line-through;
          }
        }
        
        .shop-name {
          font-size: 12px;
          color: #666;
          margin-top: 5px;
        }
      }
    }
  }
}
</style>