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

const save = () => {
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
        <el-table-column prop="avatar" label="头像"/>
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

    <el-dialog v-model="dialogFormVisible" title="学生信息录入">
      <el-form v-model="addForm" label-position="right" label-width="100px" style="padding-right: 30px">
        <el-form-item label="学生号">
          <el-input placeholder="请输入学生号" v-model="addForm.username" autocomplete="off" />
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
          <el-select  style="width: 100%;" v-model="formData.sex">
            <el-option value="male" label="男"></el-option>
            <el-option value="female" label="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
<!--            date picker -->
        </el-form-item>
        <el-form-item>
<!--          avatar upload -->
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