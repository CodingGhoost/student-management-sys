<script setup>
import { ref } from 'vue';
import request from '@/utils/request';
import {ElMessage, ElMessageBox} from "element-plus";

const formData = ref({
  username: '',
  name: '',
  tableData: [
  ],
  pageNum: 1,
  pageSize: 10,
  total: 0,
});

const dialogFormVisible = ref(false);

const addForm = ref({});

const load = () => {
  request.get("/student/selectPage", {
    params: {
      pageNum: formData.value.pageNum,
      pageSize: formData.value.pageSize,
      username: formData.value.username,
      name: formData.value.name,
    }
  }).then(res => {
    formData.value.tableData = res.data?.list || [];
    formData.value.total = res.data?.total || 0;
  })
}

const handleCurrentChange = () => {
  load();
}

const reset = () => {
  formData.value.name = '';
  formData.value.username = '';
  load();
}

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

const handleAdd = () => {
  addForm.value = {};
  dialogFormVisible.value = true;
}

const handleEdit = (row) => {
  addForm.value = JSON.parse(JSON.stringify(row));
  dialogFormVisible.value = true;
}

const handleDelete = (id) => {
  ElMessageBox.confirm('您确定要删除吗？', '删除确认', {type: 'warning'}).then(res =>{
    request.delete('/student/delete/' + id).then(res => {
      if (res.code === '200') {
        load();
        ElMessage.success("操作成功");
      } else {
        ElMessage.error(res.msg);
      }
    })
  }).catch(res => {
    // Delete canceled
  })
}

const handleImgUpload = (res) => {
  addForm.value.avatar = res.data;
}


const save = () => {
  console.log(addForm.value);
  request.request({
    url: addForm.value.id ? '/student/update' : '/student/add',
    method: addForm.value.id ? 'PUT' : "POST",
    data: addForm.value,

  }).then(res => {
    if (res.code === '200') {
      load();
      ElMessage.success("操作成功");
    } else {
      ElMessage.error(res.msg);
    }
  })
  dialogFormVisible.value = false;
}

load();
</script>

<template>
  <div>
    <div style="margin-bottom: 10px" class="card">
      <el-input style="width: 260px; margin-right: 10px" v-model="formData.username" placeholder="请输入账号查询" prefix-icon="Search" />
      <el-input style="width: 260px; margin-right: 10px" v-model="formData.name" placeholder="请输入名称查询" prefix-icon="Search" />
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div style="margin-bottom: 10px" class="card">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-table :data="formData.tableData" style="width: 100%">
        <el-table-column prop="id" label="序号" width="80px"/>
        <el-table-column prop="username" label="学生账号"/>
        <el-table-column prop="name" label="学生名称"/>
        <el-table-column prop="phone" label="手机号"/>
        <el-table-column prop="email" label="邮箱"/>
        <el-table-column prop="sex" label="性别"/>
        <el-table-column prop="birthDate" label="生日"/>
        <el-table-column prop="avatar" label="头像">
          <template #default="scope">
            <el-image v-if="scope.row.avatar" :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]" style="width:40px; height: 40px; border-radius: 5px;"></el-image>
          </template>
        </el-table-column>
        <el-table-column width="180px">
          <template #default="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination v-model:current-page="formData.pageNum" v-model:page-size="formData.pageSize"
                     @current-change="handleCurrentChange"
                     background layout="prev, pager, next" :total="formData.total"/>
    </div>

    <el-dialog width="35%" v-model="dialogFormVisible" title="学生信息录入" destroy-on-close>
      <el-form :rules="rules" v-model="addForm" label-position="right" label-width="100px" style="padding-right: 30px">
        <el-form-item label="学生账号" prop="username">
          <el-input placeholder="请输入学生号" v-model="addForm.username" autocomplete="off" />
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
          <el-upload action="http://localhost:9090/files/upload" :on-success="handleImgUpload" list-type="picture">
            <el-button type="primary" >上传头像</el-button>
          </el-upload>
        </el-form-item>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">保 存</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>

</style>