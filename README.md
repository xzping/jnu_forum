# jnu_forum
<a>该系统为Java分享学习网站。前端使用Html+CSS+JS实现，后端使用Java语言开发，使用SpringBoot+MyBatis+MySQL+Maven实现，开发工具为Eclipse。</a>

# 功能
1、登录和注册<br>
2、（分类）浏览话题<br>
3、发表话题<br>
4、上传照片<br>
5、评论<br>
6、站内信通知<br>
7、用户积分排行榜<br>

# 主要功能实现
1、登录注册：使用SpringSecurity4框架，即使用已经包装好的接口来实现，简单易用。<br>
2、上传照片：照片是存储在第三方服务器，即七牛云。<br>
3、站内信通知：通过异步队列来实现的站内信通知，其中选择Redis来作为队列。<br>
4、排行榜：排行榜是通过Redis的有序集合来实现的，可以实现快速排序。<br>

# 页面展示
1、首页
<img src="http://ox6xu9hb7.bkt.clouddn.com/%E9%A6%96%E9%A1%B5.jpeg"/>
2、注册
<img src="http://ox6xu9hb7.bkt.clouddn.com/%E6%B3%A8%E5%86%8C.jpeg"/>
3、登录
<img src="http://ox6xu9hb7.bkt.clouddn.com/%E7%99%BB%E5%BD%95.jpeg"/>
4、话题浏览页面
<img src="http://ox6xu9hb7.bkt.clouddn.com/%E8%AF%9D%E9%A2%98%E6%B5%8F%E8%A7%88%E9%A1%B5%E9%9D%A2.jpeg"/>
5、话题详情页面
<img src="http://ox6xu9hb7.bkt.clouddn.com/%E8%AF%9D%E9%A2%98%E8%AF%A6%E6%83%85%E9%A1%B5%E9%9D%A2.jpeg"/>
6、个人简介页面
<img src="http://ox6xu9hb7.bkt.clouddn.com/%E4%B8%AA%E4%BA%BA%E7%AE%80%E4%BB%8B%E9%A1%B5%E9%9D%A2.jpeg"/>
7、发表话题页面
<img src="http://ox6xu9hb7.bkt.clouddn.com/%E5%8F%91%E8%A1%A8%E8%AF%9D%E9%A2%98%E9%A1%B5%E9%9D%A2.jpeg"/>
8、站内信页面
<img src="http://ox6xu9hb7.bkt.clouddn.com/%E7%AB%99%E5%86%85%E4%BF%A1%E9%A1%B5%E9%9D%A2.jpeg"/>
9、照片墙页面
<img src="http://ox6xu9hb7.bkt.clouddn.com/%E7%85%A7%E7%89%87%E5%A2%99.jpeg"/>
