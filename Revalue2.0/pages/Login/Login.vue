<template>
  <view class="content">
    <view class="header">
      <text class="logo">Revalue.</text>
    </view>
    <view class="login-container">
      <text class="greeting" style="font-family: 'SimSun';">欢	迎！</text>
      <text class="subtitle" style="font-family: 'SimSun';">用你的账户登录</text>
      
	<view class="input-group">
	  <text class="input-label" style="font-family: 'SimSun';">邮箱</text>
	  <input 
		class="input-field" 
		type="email" 
		style="font-family: 'SimSun';" 
		placeholder="请输入邮箱"
		v-model="formData.email"
		@blur="validateEmail"
	  />
	  <text class="error-text" v-if="errors.email">{{ errors.email }}</text>
	</view>
      
	  <view class="input-group">
	    <text class="input-label" style="font-family: 'SimSun';">密码</text>
	    <view class="password-input">
	  	<input 
	  	  class="input-field" 
	  	  :type="showPassword ? 'text' : 'password'" 
	  	  style="font-family: 'SimSun';"
	  	  placeholder="请输入密码"
	  	  v-model="formData.password"
	  	  @blur="validatePassword"
	  	/>
<!-- 	  	<image 
	  	  class="eye-icon" 
	  	  :src="showPassword ? '/static/eye-open.png' : '/static/eye-close.png'" 
	  	  @click="togglePassword" 
	  	/> -->
	    </view>
	    <text class="error-text" v-if="errors.password">{{ errors.password }}</text>
		<text class="forgot-password" style="font-family: 'SimSun';" @click="reset">忘记密码?</text>
	  </view>
      
      <button class="signin-btn" style="font-family: 'SimSun';" @click="handleSubmit">登		录</button>
      
      <view class="divider">
        <text class="divider-text" style="font-family: 'SimSun';">或者用以下方式登录</text>
      </view>
      
      <view class="social-login">
        <button class="social-btn google">
          <image src="/static/QQ.png" mode="scaleToFill" class="social-icon"></image>
        </button>
        <button class="social-btn facebook">
          <image src="/static/weixin.png" mode="scaleToFill" class="social-icon"></image>
        </button>
      </view>
      
      <view class="signup-prompt">
        <text style="font-family: 'SimSun';">未创建账户? </text>
        <text class="signup-link" style="font-family: 'SimSun';"  @click="navigateToRegister">注册</text>
      </view>
    </view>
	<uni-popup ref="successPopup" type="message">
	  <uni-popup-message 
	    :type=result.type 
	    :message=result.message
	    :duration="1000"
	  />
	</uni-popup>
  </view>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { loginApi } from '../../api/user/user.js'

const router = useRouter()

const navigateToRegister = () => {
  router.push('/pages/Register/Register') // 或使用replace()不保留历史记录
}

// 表单数据ref对象
const formData = ref({
  email: '',
  password: '',
})

// 错误信息ref对象
const errors = ref({
  email: '',
  password: '',
})

const result = ref({
	message:'',
	type:''
})

// 密码可见性控制
const showPassword = ref(false)
const togglePassword = () => {
  showPassword.value = !showPassword.value
}

const reset = ()=>{
	uni.navigateTo({ url: '/pages/Resetpassword/Resetpassword' })
}

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
	if (!formData.value.password.trim()) {
	  errors.value.password = '请输入密码'
	} else if (formData.value.password.trim().length < 6) {
	  errors.value.password = '密码至少需要6个字符'
	} else {
	  errors.value.password = ''
	}
} 

const handleSubmit = async () => {
   const res = await loginApi(formData.value)
   if(res.code){
	  localStorage.setItem('loginUser', JSON.stringify(res.data));
	  router.push('/pages/Home/Home')
   }else{
	   result.value.message=res.msg
	   result.value.type="error"
	   successPopup.value.open()
   }
}
</script>

<style lang="scss">
.content {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #f5f5f0; // 浅米色背景
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
}

.header {
  padding: 40px 0 30px;
  text-align: center;
}

.logo {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

.login-container {
  flex: 1;
  background-color: white;
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
  padding: 30px 25px;
  box-shadow: 0 -5px 15px rgba(0, 0, 0, 0.05);
}

.greeting {
  display: block;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
}

.subtitle {
  display: block;
  font-size: 14px;
  color: #666;
  margin-bottom: 30px;
}

.input-group {
  margin-bottom: 20px;
}

.input-label {
  display: block;
  font-size: 14px;
  color: #555;
  margin-bottom: 8px;
}

.input-field {
  width: 95%;
  height: 50px;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 0 15px;
  font-size: 16px;
  background-color: #fafafa;
}

.password-input {
  position: relative;
  
  .eye-icon {
    position: absolute;
    right: 15px;
    top: 50%;
    transform: translateY(-50%);
    width: 24px;
    height: 24px;
  }
}

.forgot-password {
  display: block;
  text-align: right;
  font-size: 12px;
  color: #888;
  margin-top: 8px;
  text-decoration: underline;
}

.signin-btn {
  width: 100%;
  height: 50px;
  background-color: #333;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: bold;
  margin-top: 15px;
}

.divider {
  display: flex;
  align-items: center;
  margin: 25px 0;
  color: #999;
  
  &::before, &::after {
    content: "";
    flex: 1;
    height: 1px;
    background-color: #eee;
  }
}

.divider-text {
  padding: 0 15px;
  font-size: 12px;
}

.social-login {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-bottom: 30px;
}

.social-btn {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  border: 1px solid #eee;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
}

.social-icon {
  width: 24px;
  height: 24px;
}

.signup-prompt {
  text-align: center;
  font-size: 14px;
  color: #666;
}

.signup-link {
  color: #8B6B4A; // 棕色文字
  font-weight: bold;
  text-decoration: underline;
}

.error-text {
  color: #ff4d4f;
  font-size: 12px;
  margin-top: 5px;
  font-family: 'SimSun';
}
</style>