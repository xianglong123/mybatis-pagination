## mybatis 分页插件
   通过AOP将对象中的page分页参数进行解析，重填SQL达到分页的效果
     
## 使用方式
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