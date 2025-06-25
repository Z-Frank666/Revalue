<template>
  <view class="container">
	 <text class="tip-text">请选择修改密码的方式：</text>
	      
	  <!-- 验证密保改密选项 -->
	  <view class="option-item" @click="navigateToVerify">
		<view class="option-left">
		  <image src="/static/shield.png" class="option-icon"></image>
		  <view class="option-text">
			<text class="option-title">验证密保改密</text>
			<text class="option-desc">发送邮箱邮件进行验证</text>
		  </view>
		</view>
		<image src="/static/arrow.png" class="arrow-icon"></image>
	  </view>

	  <!-- 短信改密选项 -->
	  <view class="option-item" @click="navigateToSms">
		<view class="option-left">
		  <image src="/static/phone.png" class="option-icon"></image>
		  <view class="option-text">
			<text class="option-title">短信改密</text>
			<text class="option-desc">发送一条短信即可快速改密码</text>
		  </view>
		</view>
		<image src="/static/arrow.png" class="arrow-icon"></image>
	  </view>
	
	
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const navigateToVerify = ()=>{
	uni.navigateTo({
		url:'/pages/Mine/verify'
	})
}

const navigateToSms = ()=>{
	uni.navigateTo({
		url:'/pages/Mine/sms'
	})
}
</script>

<style lang='scss'>
.tip-text {
  font-size: 28rpx;
  color: #999999;
  margin-bottom: 40rpx;
  display: block;
}

/* 选项项样式 */
.option-item {
  background-color: #ffffff;
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 30rpx;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.option-left {
  display: flex;
  align-items: center;
}

.option-icon {
  width: 48rpx;
  height: 48rpx;
  margin-right: 20rpx;
}

.option-text {
  display: flex;
  flex-direction: column;
}

.option-title {
  font-size: 32rpx;
  color: #333333;
  margin-bottom: 8rpx;
}

.option-desc {
  font-size: 24rpx;
  color: #999999;
}

.arrow-icon {
  width: 24rpx;
  height: 24rpx;
}
</style>