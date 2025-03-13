<script setup>
import { ref } from 'vue';
import request from '@/utils/request';
import { ElMessage } from "element-plus";

const formData = ref({
  name: '',
  code:'',
  instructor:'',
  tableData: [
  ],
  pageNum: 1,
  pageSize: 5,
  total: 0,
});

const dialogFormVisible = ref(false);

const addForm = ref({
  // name:'',
  // code:'',
  // description:'',
  // lessons:'',
  // instructor:'',
})

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

const handleAdd = () => {
  addForm.value = {};
  dialogFormVisible.value = true;
}

const handleEdit = (row) => {
  addForm.value = JSON.parse(JSON.stringify(row));
  dialogFormVisible.value = true;
}

const save = () => {

  request.request({
    url: addForm.value.id ? '/course/update' : '/course/add',
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
      <el-input style="width: 260px; margin-right: 10px" v-model="formData.name" placeholder="请输入课程名称查询" prefix-icon="Search" />
      <el-input style="width: 260px; margin-right: 10px" v-model="formData.code" placeholder="请输入课程代号查询" prefix-icon="Search" />
      <el-input style="width: 260px; margin-right: 10px" v-model="formData.instructor" placeholder="请输入任课老师查询" prefix-icon="Search" />
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div style="margin-bottom: 10px" class="card">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-table :data="formData.tableData" style="width: 100%">
        <el-table-column prop="id" label="序号" width="80px"/>
        <el-table-column prop="name" label="课程名称"/>
        <el-table-column prop="code" label="课程代号"/>
        <el-table-column prop="description" label="课程描述"/>
        <el-table-column prop="lessons" label="课时"/>
        <el-table-column prop="instructor" label="任课老师"/>
        <el-table-column>
          <template #default="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
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

    <el-dialog v-model="dialogFormVisible" title="新增课程">
      <el-form v-model="addForm" label-position="right" label-width="100px" style="padding-right: 30px">
        <el-form-item label="课程名称">
          <el-input placeholder="请输入课程名称" v-model="addForm.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程代号">
          <el-input placeholder="请输入课程代号" v-model="addForm.code" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程描述">
          <el-input placeholder="请输入课程描述" v-model="addForm.description" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课时">
          <el-input placeholder="请输入课时" v-model="addForm.lessons" autocomplete="off" />
        </el-form-item>
        <el-form-item label="任课老师">
          <el-input placeholder="请输入任课老师" v-model="addForm.instructor" autocomplete="off" />
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