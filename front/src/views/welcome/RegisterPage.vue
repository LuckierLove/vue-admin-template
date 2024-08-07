<script setup>

import {EditPen, Lock, Message, Unlock, User} from "@element-plus/icons-vue";
</script>

<template>
  <div style="margin: 0 20px; text-align: center">
    <div style="margin-top: 100px">
      <div style="font-size: 25px; font-weight: bold">注册</div>
      <div style="font-size: 14px; color: grey">欢迎注册本系统，请在下方填写相关信息</div>
    </div>

    <div style="margin-top: 50px">
      <el-form>
        <el-form-item>
          <el-input type="text" placeholder="用户名" id="username">
            <template #prefix>
              <el-icon>
                <User/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-input type="password" placeholder="密码" id="password">
            <template #prefix>
              <el-icon>
                <Unlock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-input type="password" placeholder="重复密码" id="repeat-password">
            <template #prefix>
              <el-icon>
                <Lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-input type="email" placeholder="电子邮箱地址" id="email">
            <template #prefix>
              <el-icon>
                <Message/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-row style="margin-left: -5px; margin-right: -5px">
          <el-col :span="16">
              <el-form-item>
                <el-input type="number" placeholder="验证码" id="verifiedCode" style="padding-right: 5px; padding-left: 5px">
                  <template #prefix>
                    <el-icon><EditPen/></el-icon>
                  </template>
                </el-input>
              </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-button type="success" id="getVerifiedCode" style="padding-left: 5px; padding-right: 5px; width: 100%" plain>获取验证码</el-button>
          </el-col>
        </el-row>
      </el-form>

      <div style="margin-top: 50px">
        <el-button type="warning" style="width: 270px" plain>立即注册</el-button>
      </div>

      <div style="margin-top: 10px">
        <span style="font-size: 14px; line-height: 15px; color: grey">已有帐号？</span><el-link type="primary" style="translate: 0 -2px">立即登录</el-link>
      </div>
    </div>

  </div>
</template>

<style scoped>

</style>