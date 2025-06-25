<template>
  <view class="container">
    
    <!-- 内容区域 -->
    <view class="content">
      <text class="tip-text">你会收到一条带验证码的短信，请输入验证码：</text>
      
      <!-- 验证码输入框 -->
      <view class="input-container">
        <image src="/static/search.png" class="search-icon"></image>
        <input 
          v-model="verificationCode" 
          placeholder="请输入验证码" 
          type="number" 
          maxlength="6"
          class="code-input"
          placeholder-class="placeholder"
        />
        <text class="countdown" @click="resendCode" v-if="countdown > 0">{{countdown}}s</text>
        <text class="resend" @click="resendCode" v-else>重新发送</text>
      </view>
      
      <!-- 下一步按钮 -->
      <button 
        class="next-btn" 
        :class="{disabled: !verificationCode}"
        @click="handleNext"
      >
        下一步
      </button>
    </view>
	  <uni-popup ref="msgpopup" type="center">
	    <view class="sms-popup">
	      <text class="title">验证码已发送</text>
	      <text class="sms-content">【系统】验证码：{{ code }}，1分钟内有效</text>
	    </view>
	  </uni-popup>
  </view>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const msgpopup = ref(null)
const verificationCode = ref('')
const countdown = ref(60)
let timer = null

const code = ref(Math.floor(100000 + Math.random() * 900000))
const inputCode = ref('')

const showCodePopup = () => {
  popup.value.open()
  // 实际项目这里调用发送短信API
  console.log('发送验证码:', code.value)
}

const verifyCode = () => {
  if(inputCode.value === code.value.toString()) {
    uni.showToast({ title: '验证成功' })
    popup.value.close()
  } else {
    uni.showToast({ icon: 'error', title: '验证码错误' })
  }
}

// 倒计时逻辑
const startCountdown = () => {
  timer = setInterval(() => {
    if (countdown.value > 0) {
      countdown.value--
	  if(countdown.value == 50){
		    msgpopup.value.open()
	  }
    } else {
      clearInterval(timer)
    }
  }, 1000)
}

// 重新发送验证码
const resendCode = () => {
  if (countdown.value > 0) return
  
  countdown.value = 60
  startCountdown()
  uni.showToast({ title: '验证码已发送', icon: 'none' })
}

// 下一步操作
const handleNext = () => {
  
  if (!verificationCode.value) {
    uni.showToast({ title: '请输入验证码', icon: 'none' })
    return
  }else if(verificationCode.value!=code.value){
	  uni.showToast({ title: '验证码错误，请重新输入', icon: 'none' })
  }else{
	  uni.navigateTo({ url: '/pages/Resetpassword/Resetpassword' })
  }

}

// 返回上一页
const goBack = () => {
  uni.navigateBack()
}

onMounted(() => {
  startCountdown()
})

onUnmounted(() => {
  clearInterval(timer)
})
</script>

<style lang="scss">
.container {
  background-color: #f5f5f5;
  min-height: 100vh;
  padding-top: var(--status-bar-height);
}

.content {
  padding: 40rpx 30rpx;
}

.tip-text {
  font-size: 32rpx;
  color: #333;
  margin-bottom: 60rpx;
  display: block;
}

.input-container {
  position: relative;
  background-color: #fff;
  border-radius: 12rpx;
  height: 100rpx;
  display: flex;
  align-items: center;
  padding: 0 30rpx;
  margin-bottom: 60rpx;
  
  .search-icon {
    width: 36rpx;
    height: 36rpx;
    margin-right: 20rpx;
  }
  
  .code-input {
    flex: 1;
    height: 100%;
    font-size: 32rpx;
  }
  
  .countdown, .resend {
    font-size: 28rpx;
    color: #999;
    margin-left: 20rpx;
  }
  
  .resend {
    color: #007aff;
  }
}

.next-btn {
  background-color: #ff3b30;
  color: #fff;
  border-radius: 12rpx;
  height: 96rpx;
  line-height: 96rpx;
  font-size: 32rpx;
  margin-top: 40rpx;
  
  &.disabled {
    opacity: 0.6;
  }
}

.placeholder {
  color: #999;
  font-size: 32rpx;
}

.sms-popup {
  // position: relative;
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 头部对齐关键属性 */
  width: 80vw;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
}
.title {
  font-size: 18px;
  margin-bottom: 10px;
}
.sms-content {
  color: #666;
  margin-bottom: 15px;
}
</style>