<template>
  <view class="content">
    <image class="logo" src="/static/logo.png"></image>
  </view>
</template>

<script setup>
import { onMounted } from 'vue'

// 生命周期钩子
onMounted(() => {
	// 页面加载完成后3秒跳转到登录页
	setTimeout(() => {
		uni.redirectTo({
			url:"/pages/Login/Login"
		})
	}, 3000);
})

</script>

<style lang="scss">
.content{
	height: 100vh;
	width: 100vw;
	.logo{
		height: 100%;
		width: 100%;
	}
}
</style>
