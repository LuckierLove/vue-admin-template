<script setup>

import {House, List, Lock, Ticket, Unlock, User} from "@element-plus/icons-vue";
</script>

<template>
  <div style="height: 100vh; width: 100vw; margin: 0">
    <el-container style="width: 100%; height: 100%;">
      <el-aside style="width: 10%">
        <el-scrollbar>
        <div>
          <el-menu
              :router="true"
              :unique-opened="true"
              default-active="/dashboard"
          >
              <el-menu-item index="/dashboard">
                <template #title>
                  <el-icon><House /></el-icon>首页
                </template>
              </el-menu-item>

            <el-sub-menu index="2">
              <template #title>
                <el-icon>
                  <User/>
                </el-icon>
                玩家相关
              </template>
              <el-menu-item index="/dashboard/playerList">
                <el-icon>
                  <List/>
                </el-icon>
                玩家列表
              </el-menu-item>
              <el-menu-item index="/dashboard/ban">
                <el-icon>
                  <Lock/>
                </el-icon>
                封禁管理
              </el-menu-item>
              <el-menu-item index="/dashboard/whiteList">
                <el-icon>
                  <Ticket/>
                </el-icon>
                白名单管理
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </div>
        </el-scrollbar>
      </el-aside>
      <el-main style="width: 90%; background-color: #fafafa">
        <div style="margin: 30px 10px">
          <router-view v-slot="{Component}">
            <transition name="el-fade-in-linear">
              <component :is="Component"/>
            </transition>
          </router-view>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>

</style>