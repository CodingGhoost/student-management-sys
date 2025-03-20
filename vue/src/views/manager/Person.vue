<script setup>
import {ref} from 'vue';
import request from '@/utils/request';
import router from '@/router';
import {ElMessage} from 'element-plus'

const addForm = ref(
    JSON.parse(localStorage.getItem('user') || "{}")
);

const handleImgUpload = (res) => {
  addForm.value.avatar = res.data;
}

const update = () => {
  request.put('student/update', addForm.value).then(res => {
    const oldPassword =  JSON.parse(localStorage.getItem('user')).password;
    if (res.code === '200') {
      if (!(addForm.value.password === oldPassword)) {
        ElMessage.success('操作成功，请用新密码重新登录');
        router.push('/login');
      } else {
        localStorage.setItem('user', JSON.stringify(addForm.value));
        ElMessage.success('操作成功');
      }
    } else {
      ElMessage.error(res.msg);
    }

  })
}

</script>

<template>
  <div>
    <el-card style="width: 50%; padding: 40px;">
      <el-form v-model="addForm" label-position="right" label-width="100px" style="padding-right: 30px">
        <el-form-item label="学生头像">
          <el-upload class="avatar-uploader" action="http://localhost:9090/files/upload" :on-success="handleImgUpload" list-type="picture">
            <img v-if="addForm.avatar" :src="addForm.avatar" class="avatar">
          </el-upload>
        </el-form-item>
        <el-form-item label="学生账号" prop="username">
          <el-input placeholder="请输入学生号" v-model="addForm.username" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="学生密码" prop="password">
          <el-input show-password placeholder="请输入密码" v-model="addForm.password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input placeholder="请输入学生姓名" v-model="addForm.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input placeholder="请输入手机号" v-model="addForm.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input placeholder="请输入邮箱" v-model="addForm.email" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="addForm.sex">
            <el-radio label="male">男</el-radio>
            <el-radio label="female">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出生日期">
          <el-date-picker style="width: 100%;" format="YYYY-MM-DD" value-format="YYYY-MM-DD" v-model="addForm.birthDate">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="update">保 存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
}
</style>