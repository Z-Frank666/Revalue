<template>
	<view class="container">
	  <view class="goods-card"
		v-for="item in buyList"
		:key="item.id"
	    >
		<!-- 商品内容 -->
		<view class="goods-content">
		  <text class="goods-title">{{item.title}}</text>
		  <text class="goods-desc">{{item.description}}</text>
		  
		<uni-grid :column="3">
				<uni-grid-item v-for="(img, index) in imgList.get(item.id)" :key="index">
				  <image class="grid-image" :src="img" mode="aspectFill"/>
				</uni-grid-item>
		</uni-grid>
		  
		  <view class="goods-tags">
		    <text class="tag">#{{item.tag}}#</text>
		    <text class="post-time">{{item.time}}发布</text>
		  </view>
		</view>
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
		  <uni-popup-dialog mode="input" title="编辑求购" @confirm="updateBuyId">
		  <view class="buyContainer">
		  	<view class="form-item">
		  	  <text class="label">标题：</text>
		  	  <input 
		  	    class="input" 
		  	    v-model="buyform.title" 
		  	    placeholder="请输入标题"
		  	    placeholder-class="placeholder"
		  	  />
		  	</view>
		  	<view class="form-item">
		  			  <text class="label">分类：</text>
		  	    <!-- uni-data-select 实现 -->
		  	    <uni-data-select
		  	      v-model="buyform.sortId"
		  	      :localdata="buycategories"
		  	      :clear="false"
		  	      placeholder="请选择分类"
		  	      @change="BuyCategoryChange"
		  	    ></uni-data-select>
		  	</view>
		  	<view class="form-item">
		  	  <text class="label">简介：</text>
		  	  <textarea 
		  	    class="textarea" 
		  	    v-model="buyform.description" 
		  	    placeholder="请输入简介"
		  	    placeholder-class="placeholder"
		  	  />
		  	</view>
		  	<view class="form-item"  v-if="showPicker==false">
		  	  <text class="label">联系方式：</text>
		  	  <view class="selector-dropdown" @click="showPicker = true">
		  	        <text class="placeholder">手机/微信/QQ</text>
		  	        <image class="item-arrow" src="/static/arrow-down.png"></image>
		  		</view>
		  	</view>
		  	<view class="contact-form" v-if="showPicker==true">
		  	    <!-- 顶部标题栏 -->
		  	    <view class="form-item">
		  	      <text class="label">联系方式：</text>
		  		  <image class="item-arrow" src="/static/arrow-up-bold.png" @click="showPicker=false"></image>
		  	    </view>
		  	<!-- 表单内容 -->
		  	<view class="form-content">
		  	  <!-- 联系人 -->
		  	  <view class="form-item">
		  		<text class="label">联系人</text>
		  		<input 
		  		  v-model="buyform.name" 
		  		  placeholder="您的姓名" 
		  		  placeholder-class="placeholder"
		  		  class="input-field"
		  		/>
		  	  </view>
		  	  
		  	  <!-- 手机 -->
		  	  <view class="form-item">
		  		<text class="label">手机</text>
		  		<input 
		  		  v-model="buyform.phone" 
		  		  placeholder="手机号" 
		  		  placeholder-class="placeholder"
		  		  class="input-field"
		  		/>
		  	  </view>
		  	  
		  	  <!-- QQ -->
		  	  <view class="form-item">
		  		<text class="label">QQ</text>
		  		<input 
		  		  v-model="buyform.QQ" 
		  		  placeholder="6~15位QQ号码" 
		  		  placeholder-class="placeholder"
		  		  class="input-field"
		  		/>
		  	  </view>
		  	  
		  	  <!-- 微信 -->
		  	  <view class="form-item">
		  		<text class="label">微信</text>
		  		<input 
		  		  v-model="buyform.weixin" 
		  		  placeholder="4~20位微信号" 
		  		  placeholder-class="placeholder"
		  		  class="input-field"
		  		/>
		  	  </view>
		  	</view>
		    </view>
		  </view>
		  </uni-popup-dialog>
	  </uni-popup>
	</view>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { deleteBuy, hidebuy, myBuys, queryBuyDetail, updateBuy } from '../../api/mine/mine'
import { getImgList } from '../../api/buy/buy.js'
const imgList = ref(new Map())

const buyList = ref([])

const currentUserId = ref()

const editPopup = ref(null)

// const isActive = ref(true)

const showPicker = ref(false)

onMounted(async() => {
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    if (loginUser && loginUser.id) {
        currentUserId.value = loginUser.id;
    }
	console.log(currentUserId.value)
	const res = await myBuys(currentUserId.value)
	if(res.code){
		buyList.value = res.data
	}
	console.log(buyList.value)
	await InitImgs()
})

const InitImgs = async () => {
  for (const item of buyList.value) {
    const res = await getImgList(item.id)
    if (res.code) {
      imgList.value.set(item.id, res.data)
    }
  }
}

const buycategories = ref([
  { value: 1, text: "选择分类" },
  { value: 2, text: "数码家电" },
  { value: 3, text: "服饰鞋包" },
  { value: 4, text: "家具家居" },
  { value: 5, text: "图书文具" },
  { value: 6, text: "运动户外" },
  { value: 7, text: "虚拟服务" },
  { value: 8, text: "闲置乐器" }
])

const buyform = ref({
	id:'',
	userId:0,
	name:'',
	title:'',
	phone:'',
	description:'',
	sortId:1,
	createTime:'',
	imgCnt:0,
	QQ:'',
	weixin:''
})

const BuyCategoryChange = (value) => {
  
  // 这里可以添加分类筛选逻辑
  const option = buycategories.value.find(item => item.value === value)
  buyform.value.sortId = option.value
  console.log(buyform.value.sortId);
}


const buySelect = (e) => {
  // 手动处理多文件上传
  e.tempFiles.forEach(file => {
    uni.uploadFile({
      url: 'http://localhost:8080/api/upload',
      filePath: file.path,
      name: 'file',
      success: (res) => {
        const data = JSON.parse(res.data)
        console.log('上传成功', data)
		buyImg.value.push({
			localPath:file.path,
			serverPath:data.data
		});
		console.log(buyImg.value)
      }
    })
  })
}

const buyImg = ref([])

const buyDelete = (file) => {
  uni.showModal({
    title: '确认删除',
    content: '确定要删除这张图片吗？',
    success: (res) => {
      if (res.confirm) {
        // 实际删除逻辑（组件已自动处理UI删除）
        // console.log('已删除文件:', file)
		const index = buyImg.value.findIndex(item => 
			  item.localPath === file.tempFilePath // 根据后端返回的唯一路径标识
			);
		if (index !== -1) {
		  // 从数组中删除对应元素
		  buyImg.value.splice(index, 1);
		  console.log('已删除文件:', file);
		  uni.showToast({ title: '删除成功', icon: 'success' });
		  console.log(buyImg.value)
		}
      }
    }
  })
}

const imageStyles = ref({
	width:60,
	height:60,
	border:{
		width:2,
		style:'dashed',
		radius:'2px'
	}
})


const viewDetail = (id) => {
  uni.navigateTo({
    url: `/pages/Buy/detail?id=${id}`
  })
}

const handleOffline = async(id) => {
  const buy = buyList.value.find(item => item.id === id);
  if(buy.isactive){
	  uni.showModal({
		title: '确认下架',
		content: '确定要下架该求购吗？',
		success(res) {
		  if (res.confirm) {
			const result = hidebuy(id)
			if(res.code)
			uni.showToast({ title: '已下架', icon: 'success' })
		  }
		}
	  })
	  const res = await myBuys(currentUserId.value)
	  if(res.code){
	  	buyList.value = res.data
	  }
  }else{
	  uni.showModal({
	  		title: '确认上架',
	  		content: '确定要上架该求购吗？',
	  		success(res) {
	  		  if (res.confirm) {
				const result = hidebuy(id)
				if(res.code)
	  			uni.showToast({ title: '已上架', icon: 'success' })
	  		  }
	  		}
	  })
	  const res = await myBuys(currentUserId.value)
	  if(res.code){
	  	buyList.value = res.data
	  }
  }
}

const editItem = async(id) => {
	const res = await queryBuyDetail(id)
	if(res.code){
		buyform.value = res.data
	}
    editPopup.value.open()
}

const updateBuyId = async()=>{
	const res = await updateBuy(buyform.value)
	if(res.code){
		uni.showToast({ title: '编辑成功', icon: 'success' })
	}
}

const deleteItem = async(id) => {
  uni.showModal({
    title: '确认删除',
    content: '删除后不可恢复，确定删除吗？',
    success(res) {
      if (res.confirm) {
		const result = deleteBuy(id)
		if(result.code)
        uni.showToast({ title: '已删除', icon: 'success' })
      }
    }
  })
}
</script>

<style lang="scss">
.goods-card {
  background: #fff;
  margin: 15px;
  border-radius: 12px;
  padding: 15px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}
.goods-content {
  margin-bottom: 15px;
  
  .goods-title {
    font-weight: bold;
    font-size: 18px;
    display: block;
    margin-bottom: 8px;
  }
  
  .goods-desc {
    font-size: 15px;
    line-height: 1.5;
    display: block;
    margin-bottom: 10px;
  }
  
  .goods-swiper {
    height: 300px;
    margin: 10px 0;
    
    .goods-img {
      width: 100%;
      height: 100%;
      border-radius: 8px;
    }
  }
  
  .grid-image {
    width: 100%;
    height: 120px;
    border-radius: 4px;
  }
  
  .goods-tags {
    display: flex;
    align-items: center;
    
    .tag {
      color: #4a90e2;
      font-size: 12px;
      margin-right: 10px;
    }
    
    .post-time {
      color: #999;
      font-size: 12px;
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