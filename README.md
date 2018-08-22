# logback-elk
使用logback将日志写入kafka，使用elk进行消费展示
详细说明见博客:
http://www.xiaoting.link/articles/2018/08/13/1534164756332.html

### 1.1 更新说明:
1、添加基础日志工具类与spring Interceptor 工具类，默认拦截请求参数(post body流数据无法获取也不建议获取)