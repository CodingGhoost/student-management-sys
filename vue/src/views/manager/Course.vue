<script setup>
import { ref } from 'vue';
import request from '@/utils/request';

const formData = ref({
  name: '',
  tableData: [
  ],
  pageNum: 1,
  pageSize: 5,
  total: 0,
});

const load = () => {
  request.get("course/selectPage", {
    params: {
      pageNum: formData.value.pageNum,
      pageSize: formData.value.pageSize,
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
  load();
}

load();
</script>

<template>
  <div style="margin-bottom: 10px" class="card">
    <el-input style="width: 260px" v-model="formData.name" placeholder="请输入课程名称查询" prefix-icon="Search" />
    <el-button type="primary" style="margin-left: 10px" @click="load">查询</el-button>
    <el-button type="info" @click="reset">重置</el-button>
  </div>

  <div style="margin-bottom: 10px" class="card">
    <el-button type="primary">新增</el-button>
    <el-table :data="formData.tableData" style="width: 100%">
      <el-table-column prop="id" label="序号" width="80px"/>
      <el-table-column prop="name" label="课程名称"/>
      <el-table-column prop="code" label="课程代号"/>
      <el-table-column prop="description" label="课程描述"/>
      <el-table-column prop="lessons" label="课时"/>
      <el-table-column prop="instructor" label="任课老师"/>
      <el-table-column>
        <template #default="scope">
          <el-button type="primary">编辑</el-button>
          <el-button type="danger">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <div class="card">
    <el-pagination v-model:current-page="formData.pageNum" v-model:page-size="formData.pageSize"
                   @current-change="handleCurrentChange"
                   background layout="prev, pager, next" :total="formData.total"/>
  </div>
</template>

<style scoped>

</style>