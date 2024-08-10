<template>
  <div style="margin: 0 20px; text-align: center">
    <div style="margin-top: 100px">
      <div style="font-size: 25px; font-weight: bold">注册</div>
      <div style="font-size: 14px; color: grey">欢迎注册本系统，请在下方填写相关信息</div>
    </div>

    <div style="margin-top: 50px">
      <el-form :model="form" :rules="rules" ref="formRef" @validate="onValidate">
        <el-form-item prop="username">
          <el-input v-model="form.username" type="text" placeholder="用户名" :maxlength="8">
            <template #prefix>
              <el-icon>
                <User/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="密码" :maxlength="16">
            <template #prefix>
              <el-icon>
                <Unlock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="password_repeat">
          <el-input v-model="form.password_repeat" type="password" placeholder="重复密码" :maxlength="16">
            <template #prefix>
              <el-icon>
                <Lock/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="email">
          <el-input v-model="form.email" type="email" placeholder="电子邮箱地址">
            <template #prefix>
              <el-icon>
                <Message/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-row style="margin-left: -5px; margin-right: -5px">
          <el-col :span="16">
            <el-form-item prop="code">
              <el-input v-model="form.code" type="number" placeholder="验证码"
                        style="padding-right: 5px; padding-left: 5px">
                <template #prefix>
                  <el-icon>
                    <EditPen/>
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-button type="success" style="padding-left: 5px; padding-right: 5px; width: 100%" @click="validateEmail"
                       :disabled="!isEmailValid || coldTime > 0" plain>
              {{ coldTime > 0 ? '请稍后' + coldTime + '秒' : '获取验证码' }}
            </el-button>
          </el-col>
        </el-row>
      </el-form>

      <div style="margin-top: 50px">
        <el-button type="warning" style="width: 270px" @click="register" plain>立即注册</el-button>
      </div>

      <div style="margin-top: 10px">
        <span style="font-size: 14px; line-height: 15px; color: grey">已有帐号？</span>
        <el-link type="primary" style="translate: 0 -2px" @click="router.push('/')">立即登录</el-link>
      </div>
    </div>

  </div>
</template>

<script setup>
import {EditPen, Lock, Message, Unlock, User} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import router from "@/router/index.js";
import {ElMessage} from "element-plus";
import axios from "axios";

const form = reactive({
  username: '',
  password: '',
  password_repeat: '',
  email: '',
  code: ''
})

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
    callback(new Error('用户名不能包含特殊字符，只能是中文/英文'))
  } else {
    callback()
  }
}

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    {
      validator: validateUsername,
      trigger: ['blur', 'change']
    },
    {
      min: 2,
      max: 8,
      message: '用户名的长度必须在2-8个字符之间',
      trigger: ['blur', 'change']
    }
  ],
  password: [
    {
      required: true,
      message: '请输入密码',
      trigger: 'blur'
    },
    {
      min: 6,
      max: 16,
      message: '密码的长度必须在6-16个字符之间',
      trigger: ['blur', 'change']
    }
  ],
  password_repeat: [
    {validator: validatePassword, trigger: ['blur', 'change']},
  ],
  email: [
    {required: true, message: '请输入邮件地址', trigger: 'blur'},
    {type: 'email', message: '请输入合法的电子邮件地址', trigger: ['blur', 'change']}
  ],
  code: [
    {required: true, message: '请输入获取的验证码', trigger: 'blur'},
  ]
}

const formRef = ref()
const isEmailValid = ref(false)
const coldTime = ref(0)

const validateEmail = () => {
  coldTime.value = 60
  axios.get(`/api/user/ask-code?email=${form.email}&type=register`)
      .then(() => {
        ElMessage.success(`验证码已发送到邮箱: ${form.email}，请注意查收`)
        const handle = setInterval(() => {
          coldTime.value--
          if (coldTime.value === 0) {
            clearInterval(handle)
          }
        }, 1000)
      })
      .catch((error) => {
        ElMessage.warning(error.message)
        coldTime.value = 0
      })
}

const onValidate = (prop, isValid) => {
  if (prop === 'email') isEmailValid.value = isValid
}

const register = () => {
  formRef.value.validate((isValid) => {
    if (isValid) {
      axios.post('/api/user/register', {
        username: form.username,
        password: form.password,
        email: form.email,
        code: form.code
      }).then(({data}) => {
        if(data.code === 200){
          ElMessage.success("欢迎加入我们!")
          router.push("/")
        }else{
          ElMessage.warning(data.message)
        }
      }).catch((error) => {
        ElMessage.warning(error.message)
      })
    } else {
      ElMessage.warning('请完整填写注册表单内容！')
    }
  })
}
</script>

<style scoped>

</style>