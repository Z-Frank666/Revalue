<template>
<view class="profile-container">
    <!-- 个人信息表单 -->
    <view class="form">
		
      <!-- 头像 -->
      <view class="form-item" @click="changeAvatar">
        <text class="label">头像</text>
        <view class="right-content">
          <image :src="form.avatar || '/static/default-avatar.png'" class="avatar"></image>
          <image src="/static/arrow-right.png" class="arrow"></image>
        </view>
      </view>

      <!-- 昵称 -->
      <view class="form-item" @click="editField('nickname')">
        <text class="label">昵称</text>
        <view class="right-content">
          <text class="value">{{ form.nickname || '未设置' }}</text>
          <image src="/static/arrow-right.png" class="arrow"></image>
        </view>
      </view>

	<!-- 替换原有的性别选择器部分 -->
	<view class="form-item" @click="showGenderSelect">
	  <text class="label">性别</text>
	  <view class="right-content">
		<text class="value">{{ form.gender === 1 ? '男' : form.gender === 2 ? '女' : '未设置' }}</text>
		<image src="/static/arrow-right.png" class="arrow"></image>
	  </view>
	</view>
	  
		<!-- 简介 -->
		<view class="form-item" @click="editField('description')">
		  <text class="label">简介</text>
		  <view class="right-content">
			<text class="value">{{ form.description || '未设置' }}</text>
			<image src="/static/arrow-right.png" class="arrow"></image>
		  </view>
		</view>

      <!-- 生日 -->
      <view class="form-item" @click="showBirthdayPicker">
        <text class="label">生日</text>
        <view class="right-content">
          <text class="value">{{ form.birthday || '未设置' }}</text>
          <image src="/static/arrow-right.png" class="arrow"></image>
        </view>
      </view>
	  
	        <!-- 电话 -->
		<view class="form-item" @click="editField('phone')">
		  <text class="label">电话</text>
		  <view class="right-content">
			<text class="value">{{ form.phone || '未设置' }}</text>
			<image src="/static/arrow-right.png" class="arrow"></image>
		  </view>
		</view>
  
		<!-- 邮箱 -->
		<view class="form-item" @click="editField('email')">
		  <text class="label">邮箱</text>
		  <view class="right-content">
			<text class="value">{{ form.email || '未设置' }}</text>
			<image src="/static/arrow-right.png" class="arrow"></image>
		  </view>
		</view>
  
		<!-- 地址 -->
		<view class="form-item" @click="editField('address')">
		  <text class="label">地址</text>
		  <view class="right-content">
			<text class="value">{{ form.address || '未设置' }}</text>
			<image src="/static/arrow-right.png" class="arrow"></image>
		  </view>
		</view>
  
		<!-- 余额 -->
		<view class="form-item" @click="editField('balance')">
		  <text class="label">余额</text>
		  <view class="right-content">
			<text class="value">¥{{ form.balance || '0.00' }}</text>
		  </view>
		</view>
		
		<!-- 在profile-container的底部添加 -->
		<view class="footer-button">
		  <button 
		    type="primary" 
		    class="confirm-btn" 
		    @click="saveProfile"
		  >确认修改</button>
		</view>
	  
    </view>


    <!-- 生日选择器 -->
    <uni-datetime-picker
      ref="birthdayPicker"
      type="date"
      v-model="form.birthday"
      :start="minDate"
      :end="maxDate"
    />
	
	<!-- 编辑弹窗 -->
	<uni-popup ref="editPopup" type="dialog">
	  <uni-popup-dialog
		mode="input"
		:title="`编辑${currentField}`"
		:value="form[currentField]"
		@confirm="confirmEdit"
	  ></uni-popup-dialog>
	</uni-popup>
  </view>
</template>

<script setup>
import { ref, computed,onMounted } from 'vue'
import { queryUserApi, updateUserApi } from '../../api/user/user.js'

onMounted(() => {
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    if (loginUser && loginUser.id) {
        form.value.id = loginUser.id;
    }
	getInfo()
	console.log(form.value)
})

const getInfo = async()=>{
	const res = await queryUserApi(form.value.id)
	if (res.code) {
		form.value = res.data;
		console.log(form.value);
	}
}

// 表单数据
const form = ref({
  id:'',
  avatar: '',
  nickname: '',
  name:'',
  description: '',
  gender: '', // 1-男 2-女
  birthday: '',
  phone:'',
  email:'',
  address:'',
  balance:''
})

// 性别选项数据格式调整
const genders = ref([
  { text: '男', value: '1' },
  { text: '女', value: '2' }
])

// 计算属性：性别显示文本
const genderText = computed(() => {
  return form.value.gender ? 
    genders.value.find(item => item.value === form.value.gender)?.text : '未设置'
})

// 显示性别选择器
const showGenderSelect = () => {
  uni.showActionSheet({
    itemList: genders.value.map(item => item.text),
    success: (res) => {
      form.value.gender = genders.value[res.tapIndex].value
    }
  })
}

// 性别变更处理
const onGenderChange = (e) => {
  console.log('性别已选择:', e.detail.value)
}

const birthdayPicker = ref(null)

// 最小/最大日期限制
const minDate = ref('1900-01-01')
const maxDate = ref(new Date().toISOString().split('T')[0])

// 当前编辑字段
const currentField = ref('')
const editPopup = ref(null)

// 编辑字段
const editField = (field) => {
  currentField.value = field
  // if(field = 'balance'){
  // 	  currentField.value = '充值金额'
  // }
  editPopup.value.open()
}

// 确认编辑
const confirmEdit = (value) => {
  form.value[currentField.value] = value
  editPopup.value.close()
}



const saveProfile = async () => {
	const res = await updateUserApi(form.value)
	console.log(res)
	console.log(form.value)
	if (res.code==1) {
		uni.showToast({
			title: '修改成功',
			icon: 'success',
			duration: 1000
		      })
	} else {
		uni.showToast({
			title: res.msg || '修改失败',
			icon: 'none',
			duration: 1000
		  })
	}
	
}

// 修改头像
const changeAvatar = () => {
uni.chooseImage({
    count: 1,
    success: (res) => {
      const tempFilePath = res.tempFilePaths[0];
      form.value.avatar = tempFilePath; // 先显示本地预览
      
      // 开始上传到服务器
      uni.uploadFile({
		url: 'http://localhost:8080/api/upload',
		filePath: tempFilePath,
		name: 'file',
        success: (uploadRes) => {
          try {
            const res = JSON.parse(uploadRes.data);
            if(res.data) {
			  console.log(res.data)
              form.value.avatar = res.data; // 更新为服务器返回的永久地址
              uni.showToast({ title: '上传成功', icon: 'success' });
            }
          } catch(e) {
            console.error('解析响应失败:', e);
          }
        },
        fail: (err) => {
          uni.showToast({ title: '上传失败', icon: 'none' });
          console.error('上传错误:', err);
        }
      });
    },
    fail: (err) => {
      console.error('选择图片失败:', err);
    }
  });
}

// 显示生日选择器
const showBirthdayPicker = () => {
  birthdayPicker.value.show()
}
</script>

<style lang="scss">
.profile-container {
  background-color: #f5f5f5;
  min-height: 100vh;
}

.form {
  background-color: #fff;
  padding: 0 30rpx;
  
  .form-item {
    height: 100rpx;
    display: flex;
    align-items: center;
    justify-content: space-between;
    border-bottom: 1rpx solid #f0f0f0;
    
    .label {
      font-size: 32rpx;
      color: #333;
    }
    
    .right-content {
      display: flex;
      align-items: center;
      
      .value {
        font-size: 30rpx;
        color: #999;
        margin-right: 20rpx;
      }
      
      .avatar {
        width: 80rpx;
        height: 80rpx;
        border-radius: 50%;
        margin-right: 20rpx;
      }
      
      .arrow {
        width: 36rpx;
        height: 36rpx;
      }
    }
    
    &:last-child {
      border-bottom: none;
    }
  }
}

.picker-container {
  background-color: #fff;
  padding: 30rpx;
  
  .picker-item {
    height: 80rpx;
    line-height: 80rpx;
    text-align: center;
    font-size: 32rpx;
  }
  
  .picker-confirm {
    margin-top: 30rpx;
    background-color: #1E90FF;
    color: #fff;
    border-radius: 8rpx;
  }
}

.footer-button {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 30rpx;
  // background-color: #fff;
  // box-shadow: 0 -2rpx 10rpx rgba(0,0,0,0.1);
  
  .confirm-btn {
    width: 100%;
    height: 90rpx;
    line-height: 90rpx;
    font-size: 32rpx;
    border-radius: 45rpx;
    background-color: #1E90FF;
    color: #fff;
    
    &:active {
      opacity: 0.8;
    }
  }
}
</style>