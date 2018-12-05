# jnu_forum
<a>该系统为Java分享学习网站。前端使用Html+CSS+JS实现，后端使用Java语言开发，使用Spring+SpringMVC+SpringBoot+MyBatis+PageHelper+MySQL+Maven实现，开发工具为Eclipse。</a>

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
<img src="http://pj9qy0grp.bkt.clouddn.com/home.png"/>
2、注册
<img src="http://pj9qy0grp.bkt.clouddn.com/register.png"/>
3、登录
<img src="http://pj9qy0grp.bkt.clouddn.com/login.png"/>
4、话题浏览页面
<img src="http://pj9qy0grp.bkt.clouddn.com/homepage.png"/>
5、话题详情页面
<img src="http://pj9qy0grp.bkt.clouddn.com/topicdetail.png"/>
6、个人简介页面
<img src="http://pj9qy0grp.bkt.clouddn.com/profile.png"/>
7、发表话题页面
<img src="http://pj9qy0grp.bkt.clouddn.com/posttopic.png"/>
8、站内信页面
<img src="http://pj9qy0grp.bkt.clouddn.com/message.png"/>
9、照片墙页面
<img src="http://pj9qy0grp.bkt.clouddn.com/picwall.png"/>

备注：因项目中七牛云过期了，上传的所有照片都失效了，所以项目中有照片的都被和谐了~
个人博客地址：https://xzping.github.io
