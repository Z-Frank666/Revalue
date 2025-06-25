<template>
	<view>
  <!-- 表单区域 -->
    <view class="form">
      <!-- 类型选择 -->
      <view class="form-item">
        <text class="label">类型：</text>
        <radio-group class="radio-group" @change="onTypeChange">
          <label class="radio-label">
            <radio value='1' color="#ff5500" :checked="selectType === '1'"/>
            <text>闲置</text>
          </label>
          <label class="radio-label">
            <radio value='2' color="#ff5500" :checked="selectType === '2'"/>
            <text>求购</text>
          </label>
        </radio-group>
      </view>

	<view class="unusedContainer" v-if="selectType=='1'">
      <!-- 名称输入 -->
      <view class="form-item">
        <text class="label">名称：</text>
        <input 
          class="input" 
          v-model="unusedform.name" 
          placeholder="请输入名称"
          placeholder-class="placeholder"
        />
      </view>
	  <view class="form-item">
		  <text class="label">分类：</text>
	      <!-- uni-data-select 实现 -->
	      <uni-data-select
	        v-model="unusedform.sortId"
	        :localdata="categories"
	        :clear="false"
	        placeholder="请选择分类"
	        @change="handleCategoryChange"
	      ></uni-data-select>
		</view>
		
      <!-- 简介输入 -->
      <view class="form-item">
        <text class="label">简介：</text>
        <textarea 
          class="textarea" 
          v-model="unusedform.description" 
          placeholder="请输入简介"
          placeholder-class="placeholder"
        />
      </view>

      <!-- 价格输入 -->
      <view class="form-item">
        <text class="label">价格：</text>
        <input 
          class="input" 
          v-model="unusedform.price" 
          placeholder="请输入价格"
          placeholder-class="placeholder"
          type="number"
        />
      </view>

      <!-- 联系人输入 -->
      <view class="form-item">
        <text class="label">联系人：</text>
        <input 
          class="input" 
          v-model="unusedform.linkman" 
          placeholder="请输入联系人"
          placeholder-class="placeholder"
        />
      </view>

      <!-- 联系电话输入 -->
      <view class="form-item">
        <text class="label">联系电话：</text>
        <view class="phone-input">
          <input 
            class="input" 
            v-model="unusedform.phone" 
            placeholder="请输入联系电话"
            placeholder-class="placeholder"
            type="number"
          />
          <view class="phone-dot"></view>
        </view>
      </view>

      <!-- 图片上传 -->
      <view class="form-item">
        <text class="label">图片：</text>
            <view class="example-body">
        		<uni-file-picker 
				limit="9" 
				title="最多选择9张图片" 
				:image-styles="imageStyles"
				:auto-upload="false"
				@select="handleSelect"
				@delete="handleDelete"
				></uni-file-picker>
        	</view>
      </view>
	</view>
	
	<view class="buyContainer" v-if="selectType=='2'">
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
		        <text class="placeholder" style="margin-left: 140px;">手机/微信/QQ</text>
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
	  <!-- 图片上传 -->
	  <view class="form-item">
	    <text class="label">图片：</text>
	        <view class="example-body">
	    		<uni-file-picker 
	  				limit="9" 
	  				title="最多选择9张图片" 
	  				:image-styles="imageStyles"
	  				:auto-upload="false"
	  				@select="buySelect"
	  				@delete="buyDelete"
	  				></uni-file-picker>
	    	</view>
	  </view>
	</view>

      <!-- 发布按钮 -->
      <button class="submit-btn" @click="submitForm">发布</button>
    </view>
  </view>
</template>

<script setup>
import { ref ,onMounted} from 'vue'
import { saveImage, saveUnused } from '../../api/unused/unused.js'
import { getImgCnt, saveBuy, saveBuyImage } from '../../api/buy/buy.js'

const showPicker = ref(false)

const selectType = ref('1')

const unusedform = ref({
  id:'',
  name: '',
  description: '',
  price: '',
  linkman: '',
  phone: '',
  sortId:1,
  userId:0,
})

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

const categories = ref([
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

onMounted(() => {
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    if (loginUser && loginUser.id) {
        buyform.value.userId = loginUser.id;
		unusedform.value.userId = loginUser.id;
    }
	console.log(buyform.value.userId)
})

// 分类变化处理
const handleCategoryChange = (value) => {
  // 这里可以添加分类筛选逻辑
  const option = categories.value.find(item => item.value === value)
  unusedform.value.sortId = option.value
  console.log(unusedform.value.sortId);
}

const BuyCategoryChange = (value) => {
  
  // 这里可以添加分类筛选逻辑
  const option = buycategories.value.find(item => item.value === value)
  buyform.value.sortId = option.value
  console.log(buyform.value.sortId);
}

const selectedImg = ref([])

const buyImg = ref([])

const imageStyles = ref({
	width:120,
	height:120,
	border:{
		width:2,
		style:'dashed',
		radius:'2px'
	}
})
			
const onTypeChange = (e) => {
  selectType.value = e.detail.value
  console.log(selectType.value)
}

const handleSelect = (e) => {
  // 手动处理多文件上传
  e.tempFiles.forEach(file => {
    uni.uploadFile({
      url: 'http://localhost:8080/api/upload',
      filePath: file.path,
      name: 'file',
      success: (res) => {
        const data = JSON.parse(res.data)
        console.log('上传成功', data)
		selectedImg.value.push({
			localPath:file.path,
			serverPath:data.data
		});
		console.log(selectedImg.value)
      }
    })
  })
}

const handleDelete = (file) => {
  uni.showModal({
    title: '确认删除',
    content: '确定要删除这张图片吗？',
    success: (res) => {
      if (res.confirm) {
        // 实际删除逻辑（组件已自动处理UI删除）
        // console.log('已删除文件:', file)
		const index = selectedImg.value.findIndex(item => 
			  item.localPath === file.tempFilePath // 根据后端返回的唯一路径标识
			);
		if (index !== -1) {
		  // 从数组中删除对应元素
		  selectedImg.value.splice(index, 1);
		  console.log('已删除文件:', file);
		  uni.showToast({ title: '删除成功', icon: 'success' });
		  console.log(selectedImg.value)
		}
      }
    }
  })
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

const recentId = ref()

const submitImg = ref({
	image:'',
	itemId:0,
})

const buysubmitImg = ref({
	image:'',
	itemId:0,
})

const submitForm = async() => {
  if(selectType.value=='1'){
	console.log('提交表单:', unusedform.value)
	const res = await saveUnused(unusedform.value)
	if(res.code){
		recentId.value = res.data
		console.log(res.data)
	}
	HandlesubmitImg()
  }else if(selectType.value=='2'){
	console.log('提交表单:', buyform.value) 
	const res  = await saveBuy(buyform.value)
	if(res.code){
		buyform.value = res.data
		console.log(res.data)
	}
	HandlesubmitImg2()
	InitImgCnt(buyform.value.id)
  }
  // buyform.value = null
  uni.showToast({ title: '发布成功', icon: 'success' });
}

const HandlesubmitImg = async()=>{
	selectedImg.value.forEach(async(img, index) => {
	  console.log(`处理第${index}张图片:`, img)
	  // 这里添加URL处理逻辑，例如：
	  console.log(img.serverPath)
	  submitImg.value.image = img.serverPath;
	  submitImg.value.itemId = recentId.value;
	  console.log(submitImg.value)
	  const res = await saveImage(submitImg.value);
	  if(res.code){
		  console.log(`添加第${index}张图片成功`)
	  }
	})
}

const HandlesubmitImg2 = async()=>{
	buyImg.value.forEach(async(img, index) => {
	  console.log(`处理第${index}张图片:`, img)
	  // 这里添加URL处理逻辑，例如：
	  console.log(img.serverPath)
	  buysubmitImg.value.image = img.serverPath;
	  buysubmitImg.value.itemId = buyform.value.id;
	  console.log(buysubmitImg.value)
	  const res = await saveBuyImage(buysubmitImg.value);
	  if(res.code){
		  console.log(`添加第${index}张图片成功`)
	  }
	})
}

const InitImgCnt = async(id)=>{
	const res = await getImgCnt(id) 
	if(res.code){
		console.log("发布图片数量为：",res.data)
	}
}
</script>

<style lang="scss">
/* 表单区域 */
.form-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.label {
  margin-left: 5px;
  width: 90px;
  font-size: 15px;
}

.input {
  flex: 1;
  font-size: 15px;
  height: 40px;
}

.textarea {
  flex: 1;
  font-size: 15px;
  height: 50px;
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

/* 类型选择 */
.radio-group {
  display: flex;
}

.radio-label {
  display: flex;
  align-items: center;
  margin-right: 30px;
}

.radio-label text {
  margin-left: 5px;
}

/* 联系电话特殊样式 */
.phone-input {
  flex: 1;
  display: flex;
  align-items: center;
}

.phone-dot {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: #eee;
  margin-left: 10px;
}

.example-body{
	height: 250px;
}

/* 发布按钮 */
.submit-btn {
  margin-top: 30px;
  background-color: #ff5500;
  color: #fff;
  border-radius: 25px;
  height: 45px;
  line-height: 45px;
  font-size: 16px;
}   
 
.contact-form {
  width: 100%;
  background-color: #fff;
  border-radius: 12rpx;
  box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

.form-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24rpx 30rpx;
  border-bottom: 1rpx solid #f5f5f5;

  .title {
    font-size: 32rpx;
    font-weight: bold;
    color: #333;
  }

  .history-switch {
    display: flex;
    align-items: center;
    
    text {
      font-size: 26rpx;
      color: #2979FF;
      margin-right: 15rpx;
    }
  }
}

.form-content {
  padding: 0 30rpx;
}


</style>
