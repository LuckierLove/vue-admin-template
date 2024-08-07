<script setup>
import {Lock, User} from "@element-plus/icons-vue";
</script>

<template>
  <div style="text-align: center; margin: 0 20px">
    <div style="margin-top: 150px">
      <div style="font-weight: bold; font-size: 25px">登录</div>
      <div style="font-size: 14px; color: grey">在进入系统之前请先输入用户名和密码进行登录</div>
    </div>

    <div style="margin-top: 50px">
      <el-form>
        <el-form-item>
          <el-input type="text" id="username" placeholder="用户名/邮箱">
            <template #prefix>
              <el-icon>
                <User/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-input type="password" id="password" placeholder="密码">
            <template #prefix>
              <el-icon>
                <Lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-row style="margin-top: 5px">

          <el-col :span="12" style="text-align: left">
            <el-form-item>
              <el-checkbox label="记住我"/>
            </el-form-item>
          </el-col>

          <el-col :span="12" style="text-align: right">
            <el-link>忘记密码</el-link>
          </el-col>
        </el-row>
      </el-form>

      <div style="margin-top: 50px">
        <el-button type="primary" style="width: 270px" plain>立即登录</el-button>
      </div>

      <div style="margin: 10px 0">
        <el-divider content-position="center">没有账号</el-divider>
      </div>

      <div>
        <el-button style="width: 270px" type="warning" plain>注册账号</el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>