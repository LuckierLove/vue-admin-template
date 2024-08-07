import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'welcome',
            component: () => import('@/views/WelcomeView.vue'),
            children: [
                {
                    path: '',
                    name: 'welcome-login',
                    component: () => import('@/views/welcome/LoginPage.vue')
                },
                {
                    path: 'register',
                    name: 'welcome-register',
                    component: () => import('@/views/welcome/RegisterPage.vue')
                },
                {
                    path: 'forget',
                    name: 'welcome-forget',
                    component: () => import('@/views/welcome/ForgetPage.vue')
                }
            ]
        },
        {
            path: '/dashboard',
            name: 'dashboard',
            component: () => import('@/views/DashboardView.vue'),
            children: [
                {
                    path: 'playerList',
                    name: 'dashboard-playerList',
                    component: () => import('@/views/dashboard/PlayerList.vue')
                },
                {
                    path: 'ban',
                    name: 'dashboard-ban',
                    component: () => import('@/views/dashboard/Ban.vue')
                },
                {
                    path: 'whiteList',
                    name: 'dashboard-whiteList',
                    component: () => import('@/views/dashboard/WhiteList.vue')
                },
                {
                    path: '',
                    name: 'dashboard-index',
                    component: () => import('@/views/dashboard/IndexPage.vue')
                }
            ]
        }
    ]
})

export default router