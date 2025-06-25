<template>
  <view class="container">
    <!-- 商品信息卡片 -->
    <view class="goods-card">
      <!-- 用户信息栏 -->
      <view class="user-info">
        <image class="avatar" :src="detail.avatar" mode="aspectFill"></image>
        <view class="user-detail">
          <text class="username">{{detail.nickname}}</text>
        </view>
        <button class="report-btn" @click="handleReport">举报</button>
      </view>

      <!-- 商品内容 -->
      <view class="goods-content">
        <text class="goods-title">{{detail.title}}</text>
        <text class="goods-desc">{{detail.description}}</text>
        
        <!-- 商品图片 -->
	  <uni-grid :column="3">
		<uni-grid-item v-for="(img, index) in imgList" :key="index">
		  <image class="grid-image" :src="img" mode="aspectFill"/>
		</uni-grid-item>
	  </uni-grid>
        
        <view class="goods-tags">
          <text class="tag">#{{detail.tag}}#</text>
          <text class="post-time">{{detail.time}}发布</text>
        </view>
      </view>
	  
	      <!-- 互动按钮 -->
	  <view class="action-buttons">
		<button class="contact-btn" @click="showContactPopup">点击查看联系方式</button>
	  </view>
	  
	      <!-- 底部弹出层 -->
	  <uni-popup ref="contactPopup" type="bottom" :mask-click="false">
		<view class="contact-popup">
		  <view class="contact-item">
			<text class="contact-label">联系人：</text>
			<text class="contact-value">{{buyItem.name}}</text>
		  </view>
		  <view class="contact-item">
			<text class="contact-label">手机号：</text>
			<text class="contact-value">{{buyItem.phone}}</text>
			<button class="copy-btn" @click="copyPhone" v-if="buyItem.phone">复制</button>
		  </view>
		  <view class="contact-item">
			<text class="contact-label">微信：</text>
			<text class="contact-value">{{buyItem.weixin}}</text>
			<button class="copy-btn" @click="copyWechat" v-if="buyItem.weixin">复制</button>
		  </view>
		  <view class="contact-item">
			<text class="contact-label">QQ：</text>
			<text class="contact-value">{{buyItem.QQ}}</text>
			<button class="copy-btn" @click="copyQQ" v-if="buyItem.QQ">复制</button>
		  </view>
		  <view class="contact-tip">
			联系我时，请告知是在Revalue上看到的
		  </view>
		  <button class="close-btn" @click="closePopup">关闭</button>
		</view>
	  </uni-popup>

    </view>

    <!-- 评论区 -->
    <view class="comment-section">
      <view class="comment-header">
        <text>评论留言</text>
        <view class="sort-options">
          <text :class="{active: sortBy === 'likes'}" @click="sortBy = 'likes'">按赞数</text>
          <text :class="{active: sortBy === 'time'}" @click="sortBy = 'time'">按时间</text>
        </view>
      </view>

	  <view class="comment-list">
		<!-- 动态渲染评论列表 -->
		<view 
		  class="comment-item"
		  :class="{ 'author-comment': true }"
		  v-for="(comment, index) in commentList"
		  :key="index"
		>
		  <image class="avatar" :src="comment.avatar" mode="aspectFill"></image>
		  <view class="comment-content">
			<view class="comment-meta">
			  <text class="username">{{ comment.nickname }}</text>
			  <text class="badge" v-if="comment.userId == buyItem.userId">作者</text>
			  <text class="comment-time">{{ comment.time }}</text>
			</view>
			<view class="content-container">
				<text class="comment-text">{{ comment.content }}</text>
				<!-- 新增点赞区域 -->
				<view class="like-area" @click="toggleLike(comment.id)">
				  <uni-icons 
					:type="isLiked ? 'heart-filled' : 'heart'" 
					size="16" 
					:color="isLiked ? '#f44' : '#666'">
				  </uni-icons>
				  <text class="like-count">{{ comment.likes }}</text>
				</view>
			</view>
		  </view>
		</view>

	  </view>
      
    </view>

    <!-- 底部导航 -->
    <view class="tab-bar">
      <view class="tab-item" @click="navigateTo('home')">
        <uni-icons type="home" size="24"></uni-icons>
        <text>首页</text>
      </view>
	<view class="comment-input">
	   <input 
	     v-model="comment.content"
		 placeholder="友善评论，传递温暖" 
		 placeholder-class="placeholder"
		 @confirm="submitComment"
	   />
	   <uni-icons type="paperplane" size="20" @click="submitComment"></uni-icons>
	 </view>
      <view class="tab-item" @click="navigateTo('messages')">
        <uni-icons type="chat" size="24"></uni-icons>
        <text>消息</text>
        <view class="badge" v-if="unreadCount > 0">{{ unreadCount }}</view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { onLoad } from '@dcloudio/uni-app'  // 必须引入
import { addComment, getImgList, queryDetail,queryItem, showcomment } from '../../api/buy/buy.js'

// 接收参数
const itemId = ref(null)

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

const contactPopup = ref(null)

const showContactPopup = () => {
  contactPopup.value.open()
}

const closePopup = () => {
  contactPopup.value.close()
}

const copyPhone = () => {
  uni.setClipboardData({
    data: buyItem.value.phone,
    success: () => {
      uni.showToast({ title: '手机号已复制', icon: 'none' })
    }
  })
}

const copyWechat = () => {
  uni.setClipboardData({
    data: buyItem.value.weixin,
    success: () => {
      uni.showToast({ title: '微信号已复制', icon: 'none' })
    }
  })
}

const copyQQ = () => {
  uni.setClipboardData({
    data: buyItem.value.QQ,
    success: () => {
      uni.showToast({ title: 'QQ号已复制', icon: 'none' })
    }
  })
}

const currentUserId = ref(0)

onMounted(async() => {
	
  await InitDetail()
  await InitItem()
  await InitImgs()
  await InitComments()
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if (loginUser && loginUser.id) {
    currentUserId.value = loginUser.id;
  }
  console.log(currentUserId.value)
})

const detail = ref({})

const buyItem = ref({})

const comment = ref({
	id:'',
	userId:'',
	avatar:'',
	nickname:'',
	itemId:'',
	content:'',
	time:'',
	likes:0
})

const commentList = ref([])

const InitDetail = async()=>{
	const res = await queryDetail(itemId.value)
	if(res.code){
		detail.value = res.data
		console.log(res.data)
	}
}

const InitItem = async()=>{
	const res = await queryItem(itemId.value)
	if(res.code){
		buyItem.value = res.data
		console.log(res.data)
	}
}

const InitComments = async()=>{
	const res = await showcomment(itemId.value)
	if(res.code){
		commentList.value = res.data
		console.log(res.data)
	}
}

const submitComment = async()=>{
	comment.value.itemId = itemId.value
	comment.value.userId = currentUserId.value
	const res = await addComment(comment.value)
	if(res.code){
		comment.value = res.data
		console.log(res.data)
	}
	await InitComments()
	uni.showToast({
		title:"发表评论成功！",
		icon:"success"
	})
	comment.value.content=''
}

// 点赞状态管理
const likerecord = ref([	
])

// 点赞/取消点赞
const toggleLike = (id) => {
  isLiked.value = !isLiked.value
  
}

const imgList = ref([])

const InitImgs = async()=>{
	const res = await getImgList(itemId.value)
	if(res.code){
		imgList.value = res.data
	}
}

</script>

<style lang="scss">
.container {
  padding-bottom: 100px;
}

.goods-card {
  background: #fff;
  margin: 15px;
  border-radius: 12px;
  padding: 15px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  
  .avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
  }
  
  .user-detail {
    flex: 1;
    
    .username {
      font-weight: bold;
      font-size: 16px;
      display: block;
    }
    
    .user-tag {
      font-size: 12px;
      color: #999;
    }
  }
  
  .report-btn {
    font-size: 12px;
    color: #999;
    background: none;
    border: none;
    padding: 0;
  }
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
  align-items: center;
  // border-top: 1px solid #eee;
  padding-top: 10px;

  .contact-btn {
	  /* 背景与文字 */
	  // background-color: #f5f5f5;
	  color: royalblue;
	  
	  /* 尺寸与间距 */
	  padding: 0px 30px;
	  margin-left: 85px;
	  
	  /* 边框与形状 */
	  border-radius: 20px;
	  border: none; /* 去除默认边框 */
	  
	  /* 文字样式 */
	  font-size: 14px;
	  font-weight: normal; /* 非加粗 */
	  text-align: center;
	  white-space: nowrap; /* 防止文字换行 */
	  
	  /* 交互效果 */
	  transition: all 0.3s; /* 添加悬停动画 */
	}

	/* 悬停状态增强交互性 */
	.contact-btn:active {
	  background-color: #e0e0e0;
	  transform: scale(0.98);
  }
}

.comment-section {
  min-height: 600px;
  background: #fff;
  margin: 15px;
  border-radius: 12px;
  padding: 15px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  
  .comment-header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 15px;
    font-size: 16px;
    font-weight: bold;
    
    .sort-options {
      color: #999;
      font-size: 14px;
      font-weight: normal;
      
      text {
        margin-left: 15px;
        
        &.active {
          color: #4a90e2;
        }
      }
    }
  }
  
  .author-comment {
    display: flex;
    padding: 10px 0;
    border-bottom: 1px solid #eee;
    
    .avatar {
      width: 40px;
      height: 40px;
      border-radius: 50%;
      margin-right: 10px;
    }
    
    .comment-content {
      flex: 1;
      
      .comment-meta {
        display: flex;
        align-items: center;
        margin-bottom: 5px;
        
        .username {
          font-weight: bold;
          margin-right: 5px;
        }
        
        .badge {
          background: #ffe411;
          color: #333;
          font-size: 10px;
          padding: 2px 5px;
          border-radius: 3px;
          margin-right: 10px;
        }
        
        .comment-time {
          color: #999;
          font-size: 12px;
        }
      }
      
      .comment-text {
        font-size: 14px;
      }
    }
  }
  
  .comment-input {
    display: flex;
    align-items: center;
    margin-top: 15px;
    background: #f5f5f5;
    border-radius: 20px;
    padding: 8px 15px;
    
    input {
      flex: 1;
      font-size: 14px;
    }
    
    .placeholder {
      color: #999;
    }
    
    .uni-icons {
      color: #4a90e2;
      margin-left: 10px;
    }
  }
}

.tab-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 60px;
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-around;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
  
  .comment-input{
	  width: 250px;
	  height: 30px;
	  display: flex;
	  align-items: center;
	  margin-top: 10px;
	  background: #f5f5f5;
	  border-radius: 20px;
	  padding: 8px 15px;
	  
	  input {
	    flex: 1;
	    font-size: 14px;
	  }
	  
	  .placeholder {
	    color: #999;
	  }
	  
	  .uni-icons {
	    color: #4a90e2;
	    margin-left: 10px;
	  }
  }
  
  .tab-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-size: 12px;
    color: #666;
    position: relative;
    
    .uni-icons {
      margin-bottom: 3px;
    }
    
    .badge {
      position: absolute;
      top: -5px;
      right: -5px;
      background: #f44;
      color: white;
      font-size: 10px;
      width: 16px;
      height: 16px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
}

.contact-popup {
  background: #fff;
  padding: 20px;
  border-radius: 12px 12px 0 0;
  
  .contact-item {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
    
    .contact-label {
      font-weight: bold;
      font-size: 16px;
      color: #333;
      margin-right: 5px;
    }
    
    .contact-value {
      font-size: 16px;
      color: #333;
      flex: 1;
    }
    
    .copy-btn {
      background: #4a90e2;
      color: white;
      border-radius: 4px;
      padding: 2px 8px;
      font-size: 12px;
      margin-left: 10px;
    }
  }
  
  .contact-tip {
    font-size: 12px;
    color: #999;
    margin-bottom: 20px;
    line-height: 1.5;
  }
  
  .close-btn {
    background: #f5f5f5;
    color: #333;
    border-radius: 20px;
    padding: 8px 0;
    font-size: 14px;
    margin-top: 10px;
  }
}
.content-container{
	display: flex;
	justify-content: space-between;
	.like-area {
		position: relative;
		display: flex;
		// align-items: center;
		
		.like-count {
		  font-size: 12px;
		  color: #666;
		  margin-left: 4px;
		}
	  }
}
</style>