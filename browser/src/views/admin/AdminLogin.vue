<script setup lang="ts">
import { reactive, ref } from 'vue'
import { requrest } from '@/utils/request'
import router from '@/router'
import { ElMessage } from 'element-plus'

const loginForm = reactive({
  username: 'admin',
  password: '123456'
})

const loading = ref(false)

const loginRules = reactive({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ min: 6, message: '密码必须在6位以上', trigger: 'blur' }]
})

let loginFormRef = ref()

const handleLogin = async () => {
  let valid: boolean = false
  try {
    valid = await loginFormRef.value?.validate()
  } catch (error) {
    console.error('表单未通过验证')
  }

  if (valid) {
    loading.value = true
    let res = await requrest.post('/employee/login', loginForm)
    let R = res.data
    if (String(R.code) === '1') {
      localStorage.setItem('userInfo', JSON.stringify(R.data))
      router.replace('/admin')
    } else {
      ElMessage.error(R.msg)
      loading.value = false
    }
  }
}
</script>

<template>
  <div style="height: 100vh">
    <div class="login" id="login-app">
      <div class="login-box">
        <img src="/images/login/login-l.jpg" alt="" />
        <div class="login-form">
          <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules">
            <div class="login-form-title">
              <img src="/images/login/logo.png" style="width: 139px; height: 42px" alt="" />
            </div>
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                type="text"
                auto-complete="off"
                placeholder="账号"
                maxlength="20"
                prefix-icon="user"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="密码"
                maxlength="20"
                prefix-icon="lock"
                @keyup.enter="handleLogin"
              />
            </el-form-item>
            <el-form-item style="width: 100%">
              <el-button
                :loading="loading"
                class="login-btn"
                type="primary"
                style="width: 100%"
                @click.prevent="handleLogin"
              >
                <span v-if="!loading">登录</span>
                <span v-else>登录中...</span>
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: #333;
  .login-box {
    width: 1000px;
    height: 474.38px;
    border-radius: 8px;
    display: flex;
    img {
      width: 60%;
      height: auto;
    }
    .title {
      margin: 0px auto 30px auto;
      text-align: center;
      color: #707070;
    }
    .login-form {
      background: #ffffff;
      width: 40%;
      border-radius: 0px 8px 8px 0px;
      display: flex;
      justify-content: center;
      align-items: center;
      .el-form {
        width: 214px;
        height: 307px;
      }
      .el-form-item {
        margin-bottom: 30px;
      }
      .input-icon {
        height: 32px;
        width: 18px;
        margin-left: -2px;
      }
      .el-input__inner {
        border: 0;
        border-bottom: 1px solid #e9e9e8;
        border-radius: 0;
        font-size: 14px;
        font-weight: 400;
        color: #333333;
        height: 32px;
        line-height: 32px;
      }
      .el-input__prefix {
        left: 0;
      }
      .el-input--prefix .el-input__inner {
        padding-left: 26px;
      }
      .el-input__inner::placeholder {
        color: #aeb5c4;
      }
      .el-form-item--medium .el-form-item__content {
        line-height: 32px;
      }
      .el-input--medium .el-input__icon {
        line-height: 32px;
      }
    }
  }
}

.login-box .login-form .login-form .login-form .el-form-item.is-error .el-input__inner {
  border: 0 !important;
  border-bottom: 1px solid #fd7065 !important;
  background: #fff !important;
}

.login-btn {
  border-radius: 17px;
  padding: 11px 20px !important;
  margin-top: 10px;
  font-weight: 500;
  font-size: 14px;
  border: 0;
  background-color: #ffc200;
}

.login-form-title {
  height: 36px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 40px;
  .title-label {
    font-weight: 500;
    font-size: 20px;
    color: #333333;
    margin-left: 10px;
  }
}
</style>
