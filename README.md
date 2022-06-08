## mybatis 分页插件
   通过AOP将对象中的page分页参数进行解析，重填SQL达到分页的效果
     
## 使用方式
    0、此jar未上传公共私服，请下载本地仓库进行引用，项目已配置本地上传组件，使用gradle的upload上传本地
    
**引用方式：implementation 'com.cas:mybatis-pagination:0.0.8-SNAPSHOT'**

    1、配置文件开启
    在application.yml 中配置开启分页插件

```yaml
cas:
  mybatis-pagination:
    ## 是否开启分页插件
    enable: true
```

    2、对象继承com.cas.bean.PageWrapper
```java
public class PageWrapper {

    /**
     * 总数
     */
    private int total;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 第几页
     */
    private int pageNum;
}
```
    填写pageSize 和 pageNum字段
    
    3、触发SQL查看效果
    
## 如果帮助到您，请给我点一个start，谢谢
![](.README_images/1a93b674.png)