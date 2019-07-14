技术选型
SpringBoot
JWT
Filter
Redis + Redisson

JWT（token）存储在Redis中，类似 JSessionId-Session的关系，用户登录后每次请求在Header中携带jwt

提供统一的异常拦截机制
异常 采用断言

==================================================================

1. 运行项目，访问localhost:8080 demo中没有存储用户信息，随意输入用户名密码，用户名相同则被踢出
2. 访问 localhost:8080/index 弹出用户信息, 代表当前用户有效
3. 另一个浏览器登录相同用户名，回到第一个浏览器刷新页面，提示被踢出
4. application.properties中选择开启哪种过滤器模式，queue-filter.enabled=true 指的是开启队列踢出， 屏蔽该项则默认比较时间戳踢出。