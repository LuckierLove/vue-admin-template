<script setup>
import {User} from "@element-plus/icons-vue";
</script>

<template>
  <div style="text-align: center; margin: 0 20px">
    <div style="margin-top: 150px; font-weight: bold; font-size: 25px">登录</div>
    <div style="font-size: 14px; color: grey">在进入系统之前请先输入用户名和密码进行登录</div>
  </div>
  <div style="margin: 50px 20px">
    <el-form>
      <el-form-item>
        <el-input type="text" id="username"/>
          <template #prefix>
            <el-icon><User /></el-icon>
          </template>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped>
</style>