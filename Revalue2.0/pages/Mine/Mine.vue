<template>
	<view>
		<!-- 用户信息区 -->
		<view class="user-info">
		  <image class="avatar" :src="user.avatar || '/static/mine.png'"></image>
		  <text class="username">{{user.nickname}}</text>
		</view>
		
		<!-- 功能列表 -->
		<view class="function-list">
		  <view class="function-item" v-for="item in functionItems" :key="item.id" @click="handleFunctionClick(item.id)">
			<image class="item-icon" :src="item.icon"></image>
			<text class="item-title">{{item.title}}</text>
			<image class="item-arrow" src="/static/arrow.png"></image>
			<view v-if="item.badge" class="item-badge"></view>
		  </view>
		</view>
	</view>
</template>

<script setup>
import { ref,onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { queryUserApi } from '../../api/user/user.js'
const router = useRouter()

onMounted(() => {
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    if (loginUser && loginUser.id) {
        user.value.id = loginUser.id;
    }
	getInfo()
	console.log(user.value.id)
})

const getInfo = async()=>{
	const res = await queryUserApi(user.value.id)
	if (res.code) {
		user.value = res.data;
		console.log(user.value);
	}
}

const user = ref({
	id:'',
	nickname:'',
	name:'',
	avatar:'',
	phone:'',
	email:'',
	balance:'',
})

// 功能列表数据
const functionItems = ref([
  { id: 1, title: '我的闲置', icon: '/static/star.png' },
  { id: 2, title: '我的求购', icon: '/static/shopcar.png' },
  { id: 3, title: '我的收藏', icon: '/static/love.png' },
  { id: 4, title: '购买订单', icon: '/static/pay.png' },
  { id: 5, title: '出售订单', icon: '/static/sale.png'},
  { id: 6, title: '个人信息' ,icon: '/static/profile.png'},
  { id: 7, title: '修改密码', icon: '/static/edit.png' },
  { id: 8, title: '退出账号', icon: '/static/logout.png' }
])

const handleFunctionClick = async (id) => {
  if(id==1){
	router.push('/pages/Mine/myunused')  
  }
  if(id==2){
	 router.push('/pages/Mine/mybuy') 
  }
  if(id==3){
	router.push('/pages/Mine/mycollection')  
  }
  if(id==4){
	router.push('/pages/Mine/payorder')  
  }
  if(id==5){
	router.push('/pages/Mine/saleorder')  
  }
  if(id==6){
	  router.push('/pages/Mine/profile')
  }
  if(id==7){
	  router.push('/pages/Mine/reset')
  }
  if(id==8){
	  uni.showModal({
	          title: '提示',
	          content: '确定要退出登录吗？',
	          success: (res) => {
	              if (res.confirm) {
	                  // 清除本地存储的用户信息
	                  localStorage.removeItem('loginUser')
	                  // 跳转到登录页
	                  uni.reLaunch({
	                      url: '/pages/Login/Login'
	                  })
	              }
	          }
	      })
  }
  // ...原有逻辑
}

</script>

<style lang="scss">
/* 用户信息区 */
.user-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 30px 0;
  background-color: #fff;
}

.user-info .avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
}

.user-info .username {
  margin-top: 15px;
  font-size: 18px;
  font-weight: bold;
}

/* 功能列表 */
.function-list {
  margin-top: 10px;
  background-color: #fff;
}

.function-item {
  height: 50px;
  display: flex;
  align-items: center;
  padding: 0 15px;
  position: relative;
  border-bottom: 1px solid #f0f0f0;
}

.function-item .item-icon {
  width: 20px;
  height: 20px;
  margin-right: 10px;
}

.function-item .item-title {
  flex: 1;
  font-size: 16px;
}

.function-item .item-arrow {
  width: 16px;
  height: 16px;
}

.function-item .item-badge {
  position: absolute;
  right: 35px;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: #999;
}     
</style>
