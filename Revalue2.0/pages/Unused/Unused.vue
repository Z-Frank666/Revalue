<template>
	<view>
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
		
		<view class="waterfall-container">
		   <!-- 两列瀑布流 -->
		   <view class="waterfall-column" v-for="(column, index) in columns" :key="index">
		     <view 
		       class="product-card" 
		       v-for="item in column" 
		       :key="item.id"
			   @click="navigatorTodetail(item.id)"
		     >
		       <image class="product-image" :src=item.image mode="aspectFill" v-if="item.isactive==true"/>
		       <view class="product-info" v-if="item.isactive==true">
		         <text class="product-title">{{item.name}}</text>
		         <view class="price-section">
		           <text class="price">¥{{item.price}}</text>
		           <text class="original-price" v-if="item.originalPrice">¥{{item.originalPrice}}</text>
		         </view>
		         <text class="shop-name">{{item.description}}</text>
		       </view>
		     </view>
		   </view>
		 </view>  
	</view>
</template>

<script setup>
import { ref,onMounted } from 'vue'
import { getShowImg, getallTags, getallUnused, queryUnusedApi, unusedSearch } from '../../api/unused/unused.js'
// import usePullToRefresh from '@/composables/usePullToRefresh'

// const { data, loading } = useFetchData()

// // 使用组合函数
// usePullToRefresh(async () => {
//   await refreshData()
// })

// async function refreshData() {
//   // 你的数据刷新逻辑
//   await InitAll();
// }


// 标签数据
const tabs = ref([
])

// 当前选中标签（默认衣服）
const activeTab = ref(1) // 

// 切换标签方法
const switchTab = async(tabId) => {
  activeTab.value = tabId
  const res = await queryUnusedApi(tabId)
  if(res.code&&tabId!=1){
  	  unuseds.value = res.data
  	  console.log(res.data)
  }else if(tabId == 1){
	  await InitShowImg();
  	  await InitAll();
  }
  //清空columns
  columns.value = [[], []]
  // 简单分配算法：交替分配到两列
  unuseds.value.forEach((item, index) => {
    columns.value[index % 2].push(item)
  })
}

const keyword = ref('')

const handleSearch = async()=>{
	console.log(keyword.value)
	const res = await unusedSearch(keyword.value)
	if(res.code){
		unuseds.value = res.data
		//清空columns
		columns.value = [[], []]
		unuseds.value.forEach((item, index) => {
		  columns.value[index % 2].push(item)
		})
	}
}

const handleClear = async()=>{
	keyword.value = ''
	await InitAll();
	//清空columns
	columns.value = [[], []]
	unuseds.value.forEach((item, index) => {
	  columns.value[index % 2].push(item)
	})
}

const unuseds = ref([])


// 将商品分配到两列
const columns = ref([[], []])

onMounted(async() => {
	
  await InitShowImg();
  await InitTags();
  await InitAll();
  console.log(unuseds.value)
  // 简单分配算法：交替分配到两列
  unuseds.value.forEach((item, index) => {
    columns.value[index % 2].push(item)
  })
  
  // 更高级的实现可以计算图片高度后动态分配到较矮的列[3,4](@ref)
})

const InitShowImg = async()=>{
	const res = await getShowImg();
	if(res.code){
		console.log(res.data)
	}
}

const InitTags = async()=>{
	const res = await getallTags();
	if(res.code){
		console.log(res.data);
		tabs.value = res.data;
	}
}

const InitAll = async()=>{
	const res = await getallUnused();
	if(res.code){
		console.log(res.data);
		unuseds.value = res.data;
	}
}

const navigatorTodetail = (id)=>{
	uni.navigateTo({
		url: `/pages/Unused/detail?id=${id}`
	});
}

</script>

<style lang="scss">
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
