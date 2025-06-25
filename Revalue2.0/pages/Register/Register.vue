<template>
  <view class="content">
    <view class="header">
      <text class="logo">Revalue.</text>
    </view>
    <view class="signup-container">
      <text class="title" style="font-family: 'SimSun';">注 册</text>
      
	<view class="input-group">
	  <text class="input-label" style="font-family: 'SimSun';">姓名</text>
	  <input 
		class="input-field" 
		type="text" 
		style="font-family: 'SimSun';" 
		placeholder="请输入姓名"
		v-model="formData.name"
		@blur="validateName"
	  />
	  <text class="error-text" v-if="errors.name">{{ errors.name }}</text>
	</view>

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
<!-- 		<image 
		  class="eye-icon" 
		  :src="showPassword ? '/static/eye-open.png' : '/static/eye-close.png'" 
		  @click="togglePassword" 
		/> -->
	  </view>
	  <text class="error-text" v-if="errors.password">{{ errors.password }}</text>
	</view>

	<view class="input-group">
	  <text class="input-label" style="font-family: 'SimSun';">确认密码</text>
	  <view class="password-input">
		<input 
		  class="input-field" 
		  :type="showPassword ? 'text' : 'password'" 
		  style="font-family: 'SimSun';"
		  placeholder="请再次输入密码"
		  v-model="formData.confirmPassword"
		  @blur="validateConfirmPassword"
		/>
<!-- 		<image 
		  class="eye-icon" 
		  :src="showPassword ? '/static/eye-open.png' : '/static/eye-close.png'" 
		  @click="togglePassword" 
		/> -->
	  </view>
	  <text class="error-text" v-if="errors.confirmPassword">{{ errors.confirmPassword }}</text>
	</view>

	<button 
	  class="signup-btn" 
	  style="font-family: 'SimSun';"
	  @click="handleSubmit"
	>
	  注     册
	</button>
    
      <view class="signin-prompt">
        <text>已有账户？</text>
        <text class="signin-link" @click="navigateToLogin">登录</text>
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
import { registerApi } from '../../api/user/user.js'

const router = useRouter()

const navigateToLogin = () => {
  router.push('/pages/Login/Login')
}

// 表单数据ref对象
const formData = ref({
  name: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const result = ref({
	message:'',
	type:''
})
// 错误信息ref对象
const errors = ref({
  name: '',
  email: '',
  password: '',
  confirmPassword: ''
})

// 密码可见性控制
const showPassword = ref(false)
const togglePassword = () => {
  showPassword.value = !showPassword.value
}

const validateName = () => {
  if (!formData.value.name.trim()) {
    errors.value.name = '请输入姓名'
  } else if (formData.value.name.trim().length < 2) {
    errors.value.name = '姓名至少需要2个字符'
  } else {
    errors.value.name = ''
  }
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

const validateConfirmPassword = () =>{
	if (!formData.value.confirmPassword.trim()) {
	  errors.value.confirmPassword = '请再次输入密码'
	} else if (formData.value.password!=formData.value.confirmPassword) {
	  errors.value.confirmPassword = '两次密码输入不一致！'
	} else {
	  errors.value.confirmPassword = ''
	}
}

const successPopup = ref(null)

// 注册提交
const handleSubmit = async () => {
   const res = await registerApi(formData.value)
   if(res.code){
	   result.value.message="注册成功！"
	   result.value.type="success"
	   successPopup.value.open()
	   setTimeout(() => {
		 uni.navigateTo({
		   url: '/pages/Login/Login'
		 })
	   }, 1000) // 延迟1.5秒跳转，确保弹窗显示完整
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
  background: {
    size: cover;
    position: center;
    repeat: no-repeat;
  }
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
}

.header {
  padding: 40px 0 20px;
  text-align: center;
  height: 150px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.logo {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

.signup-container {
  flex: 1;
  background-color: white;
  border-top-left-radius: 30px;
  border-top-right-radius: 30px;
  padding: 30px 25px;
  margin-top: 20px;
  box-shadow: 0 -5px 15px rgba(0, 0, 0, 0.05);
}

.title {
  display: block;
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 25px;
  color: #333;
  text-align: center;
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
  border-radius: 10px;
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

.signup-btn {
  width: 100%;
  height: 50px;
  background-color: #8B6B4A; // 棕色按钮
  color: white;
  border: none;
  border-radius: 10px;
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

.social-signup {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-bottom: 30px;
}

.social-btn {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 1px solid #eee;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
  
  &.google {
    border-color: #DB4437;
  }
  
  &.facebook {
    border-color: #4267B2;
  }
}

.social-icon {
  width: 24px;
  height: 24px;
}

.signin-prompt {
  margin-top: 10px;
  text-align: center;
  font-size: 14px;
  color: #666;
}

.signin-link {
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