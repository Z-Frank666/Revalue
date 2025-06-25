# Revalue 二手闲置平台
## 项目概述
Revalue 是一个基于uniapp（前端）和Spring Boot（后端）的二手交易平台，支持用户注册登录、商品发布/浏览、个人中心管理等核心功能，适配多端（微信小程序/Android/iOS）。

## 技术架构
### 前端（Revalue2.0）
- 框架：uniapp（Vue.js生态，跨平台开发）
- 网络请求： common/http.js 封装通用请求方法
- 页面路由： pages.json 配置所有页面路径（ Buy / Home / Login 等）
- 静态资源： static/ 目录包含图标（ buy.png / mine.png ）、背景图（ login-bg.jpg ）等
### 后端（revaluebackend）
- 框架：Spring Boot 2.x（快速开发）
- 持久层：MyBatis（ mapper/ 目录定义数据库操作接口）
- 数据模型： pojo/ 目录包含所有实体类（ User / Product / UnusedInfo 等）
- 核心功能：
  - 用户模块： UserController.java （注册/登录/密码重置）
  - 商品模块： ProductController.java （商品发布/查询）
  - 闲置模块： UnusedController.java （闲置物品管理）
  - 文件上传： UploadController.java 集成阿里云OSS（ utils/AliyunOSSOperator.java ）
  - 鉴权： utils/JwtUtils.java 生成/验证JWT令牌
## 目录结构
```
Revalue/
├── Revalue2.0/          # 前端代码
│   ├── api/             # 接口请求封装（按功能分模块）
│   ├── pages/           # 业务页面（Buy/Home/Login等）
│   ├── static/          # 静态资源（图标/图片）
│   ├── App.vue          # 应用入口文件
│   └── pages.json       # 页面路由配置
├── revaluebackend/      # 后端代码
│   ├── controller/      # HTTP请求处理层
│   ├── service/         # 业务逻辑层
│   ├── mapper/          # MyBatis数据库接口
│   ├── pojo/            # 数据实体类
│   └── RevalueBackendApplication.java  # 启动类
└── README.md            # 当前文档
```
## 运行环境
### 前端
- Node.js ≥ 14.0.0
- HBuilderX（推荐）或VS Code（需安装uniapp插件）
### 后端
- JDK ≥ 1.8
- MySQL ≥ 5.7
- Maven ≥ 3.6
- 阿里云OSS（需配置 utils/AliyunOSSProperties.java ）
