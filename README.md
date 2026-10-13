# 职芽 JobSprout

基于 Spring Boot / Spring Cloud 的学生在线求职平台毕业设计骨架，包含后端多模块、数据库脚本和 Vue 3 前端。

## 环境要求

- JDK 17、Maven 3.9+
- MySQL 8.0、Redis 7.x
- Nacos 2.3.x
- Node.js 18+

## 启动步骤

1. 启动 Nacos 2.3.x：Linux/macOS 执行 `sh startup.sh -m standalone`，Windows 执行 `startup.cmd -m standalone`，控制台默认地址为 http://127.0.0.1:8848/nacos，账号密码通常为 nacos/nacos。
2. 创建 MySQL 数据库并执行 [job_sprout.sql](docs/sql/job_sprout.sql)。脚本默认连接配置为 root/123456，可在各服务的 `application.yml` 中修改。
3. 编译后端：在 `job-sprout-backend` 目录执行 `mvn clean package`。
4. 按顺序启动服务：认证 8081、用户 8082、职位 8083、简历 8084、消息 8085、管理 8086，最后启动网关 8080。每个服务启动类位于对应模块的 `src/main/java`。
5. 启动前端：在 `job-sprout-web` 执行 `npm install` 和 `npm run dev`，访问 http://localhost:5173。

## 服务地址

| 服务 | 端口 | 地址 |
|---|---:|---|
| Gateway | 8080 | http://localhost:8080 |
| Auth | 8081 | http://localhost:8081 |
| User | 8082 | http://localhost:8082 |
| Job | 8083 | http://localhost:8083 |
| Resume | 8084 | http://localhost:8084 |
| Message | 8085 | http://localhost:8085 |
| Admin | 8086 | http://localhost:8086 |
| Web | 5173 | http://localhost:5173 |

各业务服务提供 `GET /api/{service}/health` 示例接口；认证服务提供 `POST /api/auth/login` 和 `POST /api/auth/register`。网关根据 `/api/auth/**`、`/api/user/**`、`/api/job/**`、`/api/resume/**`、`/api/message/**`、`/api/admin/**` 路径转发到 Nacos 中的服务。

## 目录

`job-sprout-backend` 是 Maven 父工程，包含 common、gateway、auth、user、job、resume、message、admin 八个模块；`job-sprout-web` 是 Vue 3 + Vite 前端。
