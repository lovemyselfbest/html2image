## 项目用于转换html成png图片

## 项目共提供三个接口

    1，/generate  get方法，从querystring中取得html参数进行转换，图片的长高由html控制
    2，/generate?width=500&height=500  post方法，从请求体中取得html参数进行转换
    3，/load/{guid} 加载图片接口，根据1，2两个接口返回的值进行加载
   
   
   
## 项目配置项：
    
    1，server.port:8080  项目运行的端口，默认为8080
    2，root.path 项目图片存放的图片，需要有读写权限，默认为/data/server/images/
