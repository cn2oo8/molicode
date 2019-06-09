import Main from '@/views/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: 'Login - 登录'
    },
    component: () => import('@/views/login.vue')
};

export const page404 = {
    path: '/*',
    name: 'error-404',
    meta: {
        title: '404-页面不存在'
    },
    component: () => import('@/views/error-page/404.vue')
};

export const page403 = {
    path: '/403',
    meta: {
        title: '403-权限不足'
    },
    name: 'error-403',
    component: () => import('@//views/error-page/403.vue')
};


/**
 * add by david 20180501
 */
export const myPage = {
    path: '/myPage',
    meta: {
        title: 'myPage_self edit'
    },
    name: 'myPage',
    component: () => import('@//views/error-page/myPage.vue')
};

export const page500 = {
    path: '/500',
    meta: {
        title: '500-服务端错误'
    },
    name: 'error-500',
    component: () => import('@/views/error-page/500.vue')
};


export const locking = {
    path: '/locking',
    name: 'locking',
    component: () => import('@/views/main-components/lockscreen/components/locking-page.vue')
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    redirect: '/home',
    component: Main,
    children: [
        {path: 'home', title: {i18n: 'home'}, name: 'home_index', component: () => import('@/views/home/home.vue')},
        {
            path: 'ownspace',
            title: '个人中心',
            name: 'ownspace_index',
            component: () => import('@/views/own-space/own-space.vue')
        },
        {
            path: 'order/:order_id',
            title: '订单详情',
            name: 'order-info',
            component: () => import('@/views/advanced-router/component/order-info.vue')
        }, // 用于展示动态路由
        {
            path: 'shopping',
            title: '购物详情',
            name: 'shopping',
            component: () => import('@/views/advanced-router/component/shopping-info.vue')
        }, // 用于展示带参路由
        {path: 'message', title: '消息中心', name: 'message_index', component: () => import('@/views/message/message.vue')}
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [
    {
        path: '/auto-code',
        icon: 'android-desktop',
        name: 'auto-code',
        title: '自动代码工具',
        component: Main,
        children: [
            {
                path: 'global-config',
                icon: 'ios-world',
                name: 'global-config',
                title: '全局配置',
                component: () => import('@//views/auto-code/globalConfiguration.vue')
            },
            {
                path: 'configuration',
                icon: 'ios-list',
                name: 'configuration',
                title: '项目配置',
                component: () => import('@//views/auto-code/configuration.vue')
            },
            {
                path: 'autoCode',
                icon: 'android-bicycle',
                name: 'autoCode',
                title: '代码生成',
                component: () => import('@//views/auto-code/autoCode.vue')
            }
        ]
    },
    {
        path: '/project-process',
        icon: 'android-desktop',
        name: 'project-process',
        title: '工程处理',
        component: Main,
        children: [
            {
                path: 'replace',
                icon: 'ios-copy',
                name: 'replace',
                title: '全文替换工具',
                component: () => import('@//views/auto-code/replace.vue')
            },
            {
                path: 'smartSegment',
                icon: 'ios-copy',
                name: 'smartSegment',
                title: 'smartSegment',
                component: () => import('@//views/auto-code/smartSegment.vue')
            }
        ]
    },
    {
        path: '/moli-tool',
        icon: 'wrench',
        name: 'tools',
        title: '系统工具',
        component: Main,
        children: [
            {
                path: 'browser',
                icon: 'social-chrome',
                name: 'browser',
                title: '魔力浏览器',
                component: () => import('@//views/moli-tool/browser.vue')
            },
            {
                path: 'browser-setting',
                icon: 'settings',
                name: 'browser-setting',
                title: '浏览器配置项',
                component: () => import('@//views/moli-tool/browser-setting.vue')
            }
        ]
    },
    {
        path: '/help',
        icon: 'ios-help',
        name: 'component',
        title: '帮助中心',
        component: Main,
        children: [
            {
                path: 'autoCodeHelp',
                icon: 'ios-help-outline',
                name: 'autoCodeHelp',
                title: '自动代码帮助中心',
                component: () => import('@//views/help/molicode/autoCodeHelp.vue')
            },
            {
                path: 'tableModelHelp',
                icon: 'ios-help-outline',
                name: 'tableModelHelp',
                title: '表模型帮助中心',
                component: () => import('@//views/help/molicode/tableModelHelp.vue')
            }
        ]
    }
];

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    loginRouter,
    otherRouter,
    locking,
    ...appRouter,
    page500,
    page403,
    page404,
    myPage
];
