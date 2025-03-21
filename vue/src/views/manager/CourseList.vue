<script setup>
import { ref } from 'vue';
import request from '@/utils/request';
import {ElMessage, ElMessageBox} from "element-plus";

const formData = ref({
  name: '',
  code:'',
  instructor:'',
  tableData: [
  ],
  pageNum: 1,
  pageSize: 5,
  total: 0,
  student: JSON.parse(localStorage.getItem("user") || "{}"),
});

const load = () => {
  request.get("course/selectPage", {
    params: {
      pageNum: formData.value.pageNum,
      pageSize: formData.value.pageSize,
      name: formData.value.name,
      code: formData.value.code,
      instructor: formData.value.instructor,
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
  formData.value.code = '';
  formData.value.instructor = '';
  load();
}

const handleEnroll = (row) => {
  request.post('studentCourse/add',
      { studentId: formData.value.student.id, courseName: row.name, courseCode: row.code, courseId: row.id})
      .then(res => {
        if (res.code === '200') {
          ElMessage.success('操作成功');

        } else {
          ElMessage.error(res.msg);
        }
      })
}

load();
</script>

<template>
  <div>
    <div style="margin-bottom: 10px" class="card">
      <el-input style="width: 260px; margin-right: 10px" v-model="formData.name" placeholder="请输入课程名称查询" prefix-icon="Search" />
      <el-input style="width: 260px; margin-right: 10px" v-model="formData.code" placeholder="请输入课程代号查询" prefix-icon="Search" />
      <el-input style="width: 260px; margin-right: 10px" v-model="formData.instructor" placeholder="请输入任课老师查询" prefix-icon="Search" />
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div style="margin-bottom: 10px" class="card">
      <el-table :data="formData.tableData" style="width: 100%">
        <el-table-column prop="id" label="序号" width="80px"/>
        <el-table-column prop="name" label="课程名称"/>
        <el-table-column prop="code" label="课程代号"/>
        <el-table-column prop="description" label="课程描述"/>
        <el-table-column prop="lessons" label="课时"/>
        <el-table-column prop="instructor" label="任课老师"/>
        <el-table-column>
          <template #default="scope">
            <el-button type="primary" @click="handleEnroll(scope.row)">选课</el-button>
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