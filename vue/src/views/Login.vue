<script setup>
import { ref } from "vue";
import request from '@/utils/request';
import router from '@/router';
import { ElMessage } from "element-plus";



  const formData = ref({
    role:'1',
    username:'',
    password:'',
  })

  const rules = {
    username: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { min: 5, max: 16, message: '长度必须为5到16个字符', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, max: 16, message: '长度必须为6到16个字符', trigger: 'blur' }
    ],
  }

  const login = () => {
    request.post('/login', formData.value).then(res => {
      if (res.code === '200') {
        localStorage.setItem('user', JSON.stringify(res.data));
        ElMessage.success('登录成功');
        router.push('/home');
      } else {
        ElMessage.error(res.msg);
      }
    })
  }

</script>

<template>
  <div class="login-container">
    <div class="login-box" style="width: 350px">
      <div style="font-weight: bold; font-size: 24px; text-align: center; margin-bottom: 30px">登 录</div>
      <el-form :model="formData" :rules="rules">
        <el-form-item>
          <el-radio-group v-model="formData.role">
            <el-radio label="ADMIN" >管理员</el-radio>
            <el-radio label="STUDENT">学生</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="username">
          <el-input prefix-icon="User" v-model="formData.username" placeholder="请输入账号"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input show-password prefix-icon="Lock" v-model="formData.password" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
      <div style="font-size: 14px; text-align: left">
        <a href="/register">注册</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  min-height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url("@/assets/imgs/login_bg.jpg");
  background-size: cover;
}
.login-box{
  background-color: rgba(255, 255, 255, 0.85);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 30px;
  border-radius: 5px;
}
</style>