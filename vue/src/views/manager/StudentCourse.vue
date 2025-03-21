<script setup>
import { ref } from 'vue';
import request from '@/utils/request';
import {ElMessage, ElMessageBox} from "element-plus";

const formData = ref({
  courseName: '',
  courseCode:'',
  tableData: [
  ],
  pageNum: 1,
  pageSize: 5,
  total: 0,
  user: JSON.parse(localStorage.getItem("user") || "{}"),
});

const load = () => {
  let params = {
    pageNum: formData.value.pageNum,
    pageSize: formData.value.pageSize,
    courseName: formData.value.courseName,
    courseCode: formData.value.courseCode,
  }
  if (formData.value.user.role === 'STUDENT') {
    params.studentId = formData.value.user.id;
  }

  request.get("/studentCourse/selectPage", {
    params: params
  }).then(res => {
    formData.value.tableData = res.data?.list || [];
    formData.value.total = res.data?.total || 0;
  })
}

const handleCurrentChange = () => {
  load();
}

const reset = () => {
  formData.value.courseName = '';
  formData.value.courseCode = '';
  load();
}

const handleDelete = (id) => {
  ElMessageBox.confirm('您确定要删除吗？', '删除确认', {type: 'warning'}).then(res =>{
    request.delete('/studentCourse/delete/' + id).then(res => {
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

const addGrade = (row) => {
  //弹窗
}

load();
</script>

<template>
  <div>
    <div style="margin-bottom: 10px" class="card">
      <el-input style="width: 260px; margin-right: 10px" v-model="formData.courseName" placeholder="请输入课程名称查询" prefix-icon="Search" />
      <el-input style="width: 260px; margin-right: 10px" v-model="formData.courseCode" placeholder="请输入课程代号查询" prefix-icon="Search" />
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div style="margin-bottom: 10px" class="card">
      <el-table :data="formData.tableData" style="width: 100%">
        <el-table-column prop="id" label="序号" width="80px"/>
        <el-table-column prop="courseName" label="课程名称"/>
        <el-table-column prop="courseCode" label="课程代号"/>
        <el-table-column prop="studentName" label="学生名称"/>
        <el-table-column>
          <template #default="scope">
            <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
            <el-button type="primary" @click="addGrade(scope.row)" v-if="formData.user.role === 'ADMIN'">打分</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination v-model:current-page="formData.pageNum" v-model:page-size="formData.pageSize"
                     @current-change="handleCurrentChange"
                     background layout="prev, pager, next" :total="formData.total"/>
    </div>

  </div>
</template>

<style scoped>

</style>