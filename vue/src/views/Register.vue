<script setup>
import { ref } from "vue";
import request from '@/utils/request';
import router from '@/router';
import { ElMessage } from "element-plus";



const formData = ref({
  username:'',
  password:'',
})

const rules = {
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 5, max: 16, message: '长度必须为5到16个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 16, message: '长度必须为6到16个字符', trigger: 'blur' }
  ],
}

const register = () => {
  request.post('/register', formData.value).then(res => {
    if (res.code === '200') {
      ElMessage.success('注册成功');
      router.push('/login');
    } else {
      ElMessage.error(res.msg);
    }
  })
}

</script>

<template>
  <div class="register-container">
    <div class="register-box" style="width: 350px">
      <div style="font-weight: bold; font-size: 24px; text-align: center; margin-bottom: 30px">注 册</div>
      <el-form :model="formData" :rules="rules">
        <el-form-item prop="username">
          <el-input prefix-icon="User" v-model="formData.username" placeholder="请输入账号"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input show-password prefix-icon="Lock" v-model="formData.password" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%" @click="register">注册</el-button>
        </el-form-item>
      </el-form>
      <div style="font-size: 14px; text-align: left">
        <a href="/login">登录</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
.register-container {
  min-height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url("@/assets/imgs/register_bg.jpg");
  background-size: cover;
}
.register-box{
  background-color: rgba(255, 255, 255, 0.85);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 30px;
  border-radius: 5px;
}
</style>