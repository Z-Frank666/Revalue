<template>
  <view class="container">
    <!-- 表单区域 -->
    <view class="form-container">
      <uni-forms ref="form" :model="formData">
        
        <!-- 电话号码 -->
        <uni-forms-item label="邮箱" name="phone">
          <uni-easyinput 
            v-model="formData.email" 
            placeholder="请输入邮箱"
            :inputBorder="false"
			@blur="validateEmail"
          />
        </uni-forms-item>
		<text class="error-text" v-if="errors.email">{{ errors.email }}</text>
        
        <!-- 新密码 -->
        <uni-forms-item label="新密码" name="newPassword">
          <uni-easyinput 
            v-model="formData.newPassword" 
            placeholder="请输入新密码"
            type="password"
            :inputBorder="false"
			@blur="validatePassword"
          />
        </uni-forms-item>
		<text class="error-text" v-if="errors.password">{{ errors.password }}</text>
        
        <!-- 确认密码 -->
        <uni-forms-item label="确定密码" name="confirmPassword">
          <uni-easyinput 
            v-model="formData.confirmPassword" 
            placeholder="请再次输入密码"
            type="password"
            :inputBorder="false"
			@blur="validateConfirmPassword"
          />
        </uni-forms-item>
		<text class="error-text" v-if="errors.confirmPassword">{{ errors.confirmPassword }}</text>
        
        <!-- 验证码 -->
        <uni-forms-item label="验证码" name="captcha">
          <view class="captcha-container">
            <uni-easyinput 
              v-model="formData.captcha" 
              placeholder="请输入验证码"
              :inputBorder="false"
              class="captcha-input"
            />
            <view class="captcha-image" @click="refreshCaptcha">
              {{ captchaCode }}
            </view>
          </view>
        </uni-forms-item>
      </uni-forms>
      
      <!-- 提交按钮 -->
      <button class="submit-btn" @click="handleSubmit">确定修改</button>
    </view>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { resetpassword } from '../../api/mine/mine.js'

// 表单数据
const formData = ref({
  emial: '',
  newPassword: '',
  confirmPassword: '',
  captcha: ''
})

// 验证码
const captchaCode = ref('8539')

// 刷新验证码
const refreshCaptcha = () => {
  captchaCode.value = Math.floor(1000 + Math.random() * 9000)
}

const errors = ref({
  email: '',
  password: '',
  confirmPassword: ''
})

const validateEmail = () => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!formData.value.email.trim()) {
    errors.value.email = '请输入邮箱'
  } else if (!emailRegex.test(formData.value.email)) {
    errors.value.email = '请输入有效的邮箱地址'
  } else {
    errors.value.email = ''
  }
}

const validatePassword = ()=>{
	if (!formData.value.newPassword.trim()) {
	  errors.value.password = '请输入密码'
	} else if (formData.value.newPassword.trim().length < 6) {
	  errors.value.password = '密码至少需要6个字符'
	} else {
	  errors.value.password = ''
	}
} 

const validateConfirmPassword = () =>{
	if (!formData.value.confirmPassword.trim()) {
	  errors.value.confirmPassword = '请再次输入密码'
	} else if (formData.value.newPassword!=formData.value.confirmPassword) {
	  errors.value.confirmPassword = '两次密码输入不一致！'
	} else {
	  errors.value.confirmPassword = ''
	}
}


// 提交表单
const handleSubmit = async() => {
  console.log('表单提交:', formData.value)
  if(formData.value.captcha == captchaCode.value){
	  const msg = ref('')
	  const res = await resetpassword(formData.value)
	  if(res.code){
		 msg.value = res.data 
	  }
	  uni.showToast({
		title: msg.value,
		icon: 'success'
	  })
	  uni.navigateTo({ url: '/pages/Login/Login' })
  }else{
	  refreshCaptcha()
	  uni.showToast({
		title: '验证码错误！',
		icon: 'none'
	  })
  }
  
}
</script>

<style lang="scss">
.container {
  padding: 40rpx;
  background-color: #fff;
  min-height: 100vh;
}

.form-container {
  .uni-forms-item {
    margin-bottom: 30rpx;
    // /deep/ .uni-forms-item__label {
    //   font-size: 32rpx;
    //   color: #333;
    // }
  }
}

.captcha-container {
  display: flex;
  align-items: center;
  .captcha-input {
    flex: 1;
  }
  .captcha-image {
    width: 150rpx;
    height: 70rpx;
    margin-left: 20rpx;
    background: #f5f5f5;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 36rpx;
    color: #333;
    border-radius: 8rpx;
  }
}

.submit-btn {
  margin-top: 60rpx;
  background-color: #e74c3c;
  color: white;
  border-radius: 50rpx;
  font-size: 32rpx;
  height: 90rpx;
  line-height: 90rpx;
}

.error-text {
  color: #ff4d4f;
  font-size: 12px;
  margin-top: 5px;
  font-family: 'SimSun';
}
</style>