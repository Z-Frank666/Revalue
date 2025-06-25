<template>
 <view class="goods-container">
    <view 
      class="goods-item" 
      v-for="item in unusedList" 
      :key="item.id"
    >
      <view class="goods-header">
        <text class="goods-title">{{ item.name }}</text>
        <text class="goods-tag" v-if="item.tag">{{ item.tag }}</text>
      </view>
      
      <view class="goods-body">
        <!-- 新增图片展示区域 -->
        <image 
          class="goods-image"
          :src="item.image"
          mode="aspectFill"
        ></image>
        
        <view class="goods-info">
          <view class="price-section">
            <view class="price-row">
              <text class="goods-price">￥{{ item.price.toFixed(2) }}</text>
              <text class="goods-quantity">x1</text>
            </view>
            <text class="goods-subtitle">{{ item.description }}</text>
          </view>
          
          <view class="spec-section">
            <text class="goods-spec">{{ item.spec }}</text>
            <text class="delivery-time">发货时间:付款后30天内发货</text>
          </view>
        </view>
      </view>
      
      <!-- 操作按钮 -->
      <view class="action-buttons">
        <button class="btn view-btn" @click="viewDetail(item.id)">查看</button>
        <button
          class="btn offline-btn" 
          @click="handleOffline(item.id)"
        >
          {{ item.isactive==true ? '下架' : '上架' }}
        </button>
        <button class="btn edit-btn" @click="editItem(item.id)">编辑</button>
        <button class="btn delete-btn" @click="deleteItem(item.id)">删除</button>
      </view>
    </view>
	<uni-popup ref="editPopup" type="dialog">
		<uni-popup-dialog mode="input" title="编辑闲置" @confirm="updateUnusedId">
			<view class="unusedContainer">
				<view class="form-item">
				  <text class="label">名称：</text>
				  <input 
				    class="input" 
				    v-model="unusedform.name" 
				    placeholder="请输入标题"
				    placeholder-class="placeholder"
				  />
				</view>
				<view class="form-item">
					<text class="label">分类：</text>
				    <uni-data-select
				      v-model="unusedform.sortId"
				      :localdata="unusedcategories"
				      :clear="false"
				      placeholder="请选择分类"
				      @change="UnusedCategoryChange"
				    ></uni-data-select>
				</view>
				<view class="form-item">
				  <text class="label">简介：</text>
				  <textarea 
				    class="textarea" 
				    v-model="unusedform.description" 
				    placeholder="请输入简介"
				    placeholder-class="placeholder"
				  />
				</view>
				<view class="form-item">
				<text class="label">价格：</text>
				  <input 
				    class="input" 
				    v-model="unusedform.price" 
				    placeholder="请输入价格"
				    placeholder-class="placeholder"
				  />
				</view>
				<view class="form-item">
				<text class="label">联系人：</text>
				  <input 
				    class="input" 
				    v-model="unusedform.linkman" 
				    placeholder="请输入联系人"
				    placeholder-class="placeholder"
				  />
				</view>
				<view class="form-item">
				<text class="label">联系人：</text>
				  <input 
				    class="input" 
				    v-model="unusedform.phone" 
				    placeholder="请输入联系电话"
				    placeholder-class="placeholder"
				  />
				</view>
			</view>
		</uni-popup-dialog>
	</uni-popup>
  </view>
</template>

<script setup>
import { ref,onMounted } from 'vue'
import { deleteUnused, hideUnused, myUnuseds, queryUnusedDetail, updateUnused } from '../../api/mine/mine.js'

const editPopup = ref(null)

const currentUserId = ref()

const unusedList = ref([])

const goodsList = ref([
  {
    id: 1,
    title: '2020冬装新款小清新宽松软糯毛衣外套',
    tag: '【冬日限定】现货原创K',
    price: 348.58,
    subtitle: '制服女2020冬装新款...',
    quantity: 1,
    spec: '灰色:M',
    delivery: '发货时间付款后30天内发货'
  },
  {
    id: 2,
    title: '【葡萄藤】现货小清新学院风制服格裙百褶裙',
    price: 135.00,
    subtitle: '院风制服格裙百褶裙女...',
    quantity: 1,
    spec: '45cm；S',
    delivery: '发货时间付款后30天内发货'
  }
])

const unusedform = ref({
	id:'',
	name:'',
	sortId:'',
	description:'',
	price:'',
	linkman:'',
	phone:'',
	userId:0,
})

const unusedcategories = ref([
  { value: 1, text: "选择分类"},
  { value: 2, text: "家居用品" },
  { value: 3, text: "服饰鞋包" },
  { value: 4, text: "数码产品" },
  { value: 5, text: "书籍资料" },
  { value: 6, text: "厨房用具" },
  { value: 7, text: "运动器材" },
  { value: 8, text: "美妆个护" },
  { value: 9, text: "儿童用品" }
])

const UnusedCategoryChange = (value) => {
  
  // 这里可以添加分类筛选逻辑
  const option = unusedcategories.value.find(item => item.value === value)
  unusedform.value.sortId = option.value
  console.log(unusedform.value.sortId);
}


onMounted(async() => {
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    if (loginUser && loginUser.id) {
        currentUserId.value = loginUser.id;
		unusedform.userId = loginUser.id;
    }
	console.log(currentUserId.value)
	const res = await myUnuseds(currentUserId.value)
	if(res.code){
		unusedList.value = res.data
	}
})

const viewDetail = (id) => {
  uni.navigateTo({
    url: `/pages/Unused/detail?id=${id}`
  })
}

const handleOffline = async(id) => {
  const unused = unusedList.value.find(item => item.id === id);
  if(unused.isactive){
  	  uni.showModal({
  		title: '确认下架',
  		content: '确定要下架该闲置吗？',
  		success(res) {
  		  if (res.confirm) {
  			const result = hideUnused(id)
  			if(res.code)
  			uni.showToast({ title: '已下架', icon: 'success' })
  		  }
  		}
  	  })
  	  const res = await myUnuseds(currentUserId.value)
  	  if(res.code){
  	  	unusedList.value = res.data
  	  }
  }else{
  	  uni.showModal({
  	  		title: '确认上架',
  	  		content: '确定要上架该闲置吗？',
  	  		success(res) {
  	  		  if (res.confirm) {
  				const result = hideUnused(id)
  				if(res.code)
  	  			uni.showToast({ title: '已上架', icon: 'success' })
  	  		  }
  	  		}
  	  })
  	  const res = await myUnuseds(currentUserId.value)
  	  if(res.code){
  	  	unusedList.value = res.data
  	  }
  }
}

const editItem = async(id) => {
	const res = await queryUnusedDetail(id)
	if(res.code){
		unusedform.value = res.data
	}
    editPopup.value.open()
}

const updateUnusedId = async()=>{
	const res = await updateUnused(unusedform.value)
	console.log("提交修改")
	if(res.code){
		uni.showToast({ title: '编辑成功', icon: 'success' })
	}
}

const deleteItem = (id) => {
  uni.showModal({
    title: '确认删除',
    content: '删除后不可恢复，确定删除吗？',
    success(res) {
      if (res.confirm) {
  		const result = deleteUnused(id)
  		if(result.code)
        uni.showToast({ title: '已删除', icon: 'success' })
      }
    }
  })
}
</script>

<style lang="scss">
.goods-container {
  padding: 20rpx;
  background-color: #fff;
}

.goods-item {
  margin-bottom: 30rpx;
  border-radius: 12rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
  overflow: hidden;
  
  .goods-header {
    padding: 24rpx;
    border-bottom: 1rpx solid #f5f5f5;
    
    .goods-title {
      font-size: 32rpx;
      font-weight: bold;
      display: block;
      margin-bottom: 8rpx;
      color: #333;
    }
    
    .goods-tag {
      font-size: 24rpx;
      color: #ff6b6b;
      background-color: #fff5f5;
      padding: 4rpx 12rpx;
      border-radius: 6rpx;
    }
  }
  
  .goods-body {
  display: flex; /* 启用flex布局 */
  padding: 24rpx;
  
  .goods-image {
    width: 180rpx;
    height: 180rpx;
    border-radius: 8rpx;
    margin-right: 20rpx;
    flex-shrink: 0; /* 防止图片被压缩 */
  }
  
  .goods-info {
    flex: 1; /* 剩余空间全部给信息区域 */
    min-width: 0; /* 解决flex布局内容溢出问题 */
    
    /* 原有样式保持不变 */
    .price-section {
      display: flex;
      flex-direction: column;
      margin-bottom: 20rpx;
      
      .price-row {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 8rpx;
        
        .goods-price {
          font-size: 36rpx;
          color: #ff6b6b;
          font-weight: bold;
        }
        
        .goods-quantity {
          font-size: 26rpx;
          color: #999;
        }
      }
      
      .goods-subtitle {
        font-size: 26rpx;
        color: #666;
        line-height: 1.4;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2; /* 限制两行显示 */
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
    
    .spec-section {
      display: flex;
      justify-content: space-between;
      
      .goods-spec {
        font-size: 26rpx;
        color: #333;
      }
      
      .delivery-time {
		margin-top: 35px;
        font-size: 24rpx;
        color: #999;
      }
    }
  }
}
  
  .action-buttons {
    display: flex;
    border-top: 1rpx solid #f5f5f5;
    
    .btn {
      flex: 1;
      height: 80rpx;
      line-height: 80rpx;
      font-size: 28rpx;
      border-radius: 0;
      border: none;
      background: none;
      
      &::after {
        border: none;
      }
      
      &.view-btn {
        color: #4a90e2;
      }
      
      &.offline-btn {
        color: #f0ad4e;
      }
      
      &.edit-btn {
        color: #5cb85c;
      }
      
      &.delete-btn {
        color: #d9534f;
      }
    }
  }
}
/* 表单区域 */
.form-item {
  width: 300px;
  display: flex;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.label {
  margin-left: 5px;
  width: 80px;
  font-size: 15px;
}

.input {
  flex: 1;
  font-size: 15px;
  // height: 40px;
  // width: 40px;
}

.textarea {
  flex: 1;
  font-size: 15px;
  // height: 50px;
}

.item-arrow{
	position: absolute;
	right: 10px;
	margin-top: 10px;
	width: 16px;
	height: 16px;
}

.placeholder {
  color: #999;
  font-size: 15px;
}
.example-body{
	// height: 250px;
}
.contact-form {
  width: 100%;
  background-color: #fff;
  border-radius: 12rpx;
  box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.05);
  overflow: hidden;
}
</style>