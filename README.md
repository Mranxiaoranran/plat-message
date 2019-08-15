# plat-message
基于netty实现即时通讯

一 如何使用

  修改配置文件
  
      配置文件的位置
      
          resources  
               ---  application.properties
               
       
       配置文件需要改什么
       
           1 修改消息服务器ip地址       该地址为部署服务器的ip地址
                                        windows 通过  ipconfig 获取ipv4地址 
                                        Linux   通过  ifconfig 获取ipv4地址
                                        
           2  修改消息服务器端口号     消息服务器使用的端口号
           
           
           3   修改redis ip 地址 端口号 
           
 二 如何启动
 
      1  开发环境启动
    
               找到 MessageApplication类，启动run方法 
            
      2   生产环境部署
      
          执行 mvn clean package  将项目打包为 jar 
          
          服务器上执行  java -jar 即可
                                                                   