<script setup>

import {EditPen, Lock, Message, Unlock} from "@element-plus/icons-vue";
</script>

<template>
  <div style="margin: 0 20px">
      <div style="margin-top: 20px">
        <el-steps align-center>
          <el-step title="验证邮箱"/>
          <el-step title="重设密码"/>
        </el-steps>
      </div>

    <transition>
      <div v-if="false">
        <div style="margin-top: 100px; text-align: center">
          <div style="font-size: 25px; font-weight: bold">重置密码</div>
          <div style="font-size: 14px; color: grey">请输入需要重置密码的电子邮件地址</div>
        </div>

        <div style="margin-top: 50px">
          <el-form>
            <el-form-item>
              <el-input type="email" placeholder="电子邮箱地址">
                <template #prefix>
                  <el-icon>
                    <Message/>
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-row style="margin-right: -5px; margin-left: -5px">
              <el-col :span="16" style="padding-right: 5px;padding-left: 5px">
                <el-form-item>
                  <el-input type="number" placeholder="验证码">
                    <template #prefix>
                      <el-icon>
                        <EditPen/>
                      </el-icon>
                    </template>
                  </el-input>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-button type="success" style="padding-right: 5px;padding-left: 5px; width: 100%" plain>获取验证码</el-button>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <div style="text-align: center; margin-top: 50px">
          <el-button style="width: 270px" type="danger" plain>开始重置密码</el-button>
        </div>
      </div>
    </transition>

    <transition>
      <div v-if="true">
        <div style="margin-top: 100px; text-align: center">
          <div style="font-size: 25px; font-weight: bold">重置密码</div>
          <div style="font-size: 14px; color: grey">请填写您的新密码，务必牢记，防止丢失</div>
        </div>

        <div style="margin-top: 50px">
          <el-form>
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
          </el-form>
        </div>

        <div style="margin-top: 70px; text-align: center">
          <el-button type="danger" style="width: 270px" plain>立即重置密码</el-button>
        </div>
      </div>
    </transition>
  </div>
</template>

<style scoped>

</style>