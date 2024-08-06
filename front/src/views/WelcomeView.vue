<script setup>
import welcomeImage from '@/assets/welcomeImage.jpg'
</script>

<template>
    <div class="common-layout" style="width: 100vw;height: 100vh;overflow: hidden;display: flex">
          <div style="flex: 1;">
            <el-image :src="welcomeImage" style="width: 100%; height: 100%;" fit="cover"/>
          </div>
      <div id="welcome-title">
        <div style="font-size: 30px;font-weight: bold">玩家管理系统</div>
        <div style="margin-top: 10px">基于Vue3+SpringBoot开发，用于快速搭建前后端分离项目的框架。</div>
      </div>

      <div id="right-card">
        <router-view v-slot="{ Component }">
          <component :is="Component" style="height: 100%"/>
        </router-view>
      </div>
    </div>
</template>

<style scoped>
  #welcome-title{
    position: absolute;
    bottom: 30px;
    left: 30px;
    text-shadow: 0 0 10px black;
    color: white;
  }

  #right-card{
    z-index: 1;
    background-color: var(--el-bg-color);
    width: 24%;
  }
</style>