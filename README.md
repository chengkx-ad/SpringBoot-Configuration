SpringBoot

### 一、springboot入门

### 二、spring、springMVC、springBoot的关系

### 三、约定优于配置

### 四、依赖管理和自动配置

#### 1、依赖管理

修改默认版本号  或者在properties里配置mysql.version

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.49</version>
</dependency>
```

#### 2、场景启动器

#### 3、自动配置

#### 4、如何修改默认配置？

（1）写在mainapp的启动注解中

```java
@SpringBootApplication(scanBasePackages = "com.chengkx")
```

（2）

（3）自定义配置

使用value注解进行配置

```xml
my.website=http://www.baidu.com
```

```java
@Value("${my.website}")
private String website;
```

5、自动配置遵守按需加载原则

引入了哪个starter场景启动器，就会自动加载关联的jar包

springboot所有的自动配置功能都在spring-boot-autoconfigure包下

在 SpringBoot 的 自 动 配 置 包 , 一 般 是 XxxAutoConfiguration.java, 对 应XxxxProperties.java,

### 五、容器功能

#### 1、@Controller、@Service、@Repository、@Component

在spring中的这几个注解依然可以正常使用，通过这些注解给容器注入对象

#### 2、@Configuration

在springboot中如何注入一个bean/组件？和原本的spring有什么区别？

 注：在springboot中依然可以使用spring中的bean注入方式

标识一个配置类，（类似）等价于传统的beans.xml文件，这个配置类可以有多个，具体看实际需求

被标识的类，也会被作为组件注入容器中

```java
@Configuration
public class BeanConfig {
    
    //若不加(name = "monster1")那么该bean的id就是方法名，但是也可以通过name指定
    @Bean
    //@Bean(name = "monster1")
    public Monster monster01(){
        return new Monster(100, "小成", "醉拳", 25);
    }

}
```

#### 3、springboot新增的一个值**proxyBeanMethods**（调用组件的情况下区分单例和多例）

(1) Full(proxyBeanMethods = true)、【保证每个@Bean 方法被调用多少次返回的组件都是单实例的, 是代理方式

(2) Lite(proxyBeanMethods = false)【每个@Bean 方法被调用多少次返回的组件都是新创建的, 是非代理方式

(3) 特别说明: proxyBeanMethods 是在 调用@Bean 方法 才生效，因此，需要先获取BeanConfig 组件，再调用方法而不是直接通过 SpringBoot 主程序得到的容器来获取 bean, 注意观察直接通过ioc.getBean() 获取 Bean, proxyBeanMethods 值并没有生效

(4) 如何选择: 组件依赖必须使用 Full 模式默认。如果不需要组件依赖使用 Lite 模

(5) Lite 模 也称为轻量级模式，因为不检测依赖关系，运行速度快

```java
@Configuration(proxyBeanMethods = true)
```

#### 4、@import

将配置类通过.class文件注入容器

```java
//源码
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Import {
    Class<?>[] value();
}
```

```java
@Import(Monster.class)  //导入
```

#### 5、条件装配--@Conditional

满足一定条件，进行组装

#### 6、@ImportResource

可以将beans.xml文件导入到config配置类中，来获取xml文件中的组件

放在类上，将beans.xml中的组件全部注入容器中

```java
@Configuration
@ImportResource("classpath:beans.xml")
public class BeanConfig {
}
```

```xml
<bean id="monster02" class="com.chengkx.bean.Monster">
    <property name="name" value="成凯璇"></property>
    <property name="id" value="001"></property>
    <property name="age" value="25"></property>
    <property name="skill" value="写代码"></property>
</bean>
```

#### 7、配置绑定

配置绑定--绑定的是什么？---**在application.properties中配置K-V类型的属性值，利用前缀、@Resource与某个javabean绑定**

类的注解配置

```java
@Component
@ConfigurationProperties(prefix = "furn01")
public class Furn {
```

controller中的配置

resource注解会将application.properties中的配置信息加载进来

```java
@Resource
private Furn furn;
```

```java
@RequestMapping(value = "/furn")
@ResponseBody
public Furn furn(){
    return furn;
}
```

最终，访问/furn，furn的相关信息（json）会输出在页面

### 六、Lombok

简化代码------getter、setter、tostring等，异常处理，IO流关闭操作

常用注解：

@Data   一般作用在javabean类上，为该类的所有属性提供getter和setter方法、此外，还提供了tostring、hashcode等等

@Setter  针对属性，提供setter方法

@Getter  针对属性， 提供getter方法

@Log4j  作用在类上，为类提供一个属性名为log的log4j日志对象

@NoArgsConstructor  作用在类上，提供无参构造

@AllArgsConstructor   作用在类上，提供全参构造

@Cleanup  关闭流

@Builder  为某个类增加构造者模式

@Synchronized   加个同步锁

@SneakyThrows  等同于try'/catch 捕获异常

@NonNull  为参数增加该注解后，若该参数为null，会有空指针异常

@Value  基本等同于data，区别在于会把所有的属性自动定义为private final修饰的  不会生成setter方法

```java
@Component
@ConfigurationProperties(prefix = "furn01")

// 自动生成tostring  默认情况下会自动显示的声明一个无参构造器
//@ToString

/**
 * 等价于使用了@code @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
 * 注意： 不会生成全参构造器
 */
@Data

/**
 * 这两个要一起用，如果使用了@All，系统会不再默认生成无参构造器，所以必须配合使用@No
 * 只要有别的构造器生成，就会覆盖默认的无参构造器，所以必须手动生成
 */
@NoArgsConstructor
@AllArgsConstructor

/**
 * 一些细节：
 * 第一点：要想在日志中输出furn结果，得配置Setter方法，但是配好setter方法后，只能显示在后端日志中
 * 第二点：前端页面中显示json数据时，需要getter方法才能显示，所以必须配置getter
 * 但是目前我直接配置了@Data，所以不需要另外配置了
 */

public class Furn {

    private Integer id;
    private String name;
    private String skill;

}
```

```java
@RestController
//这里restcontroller包含了controller和requestbody两个注解
public class MonsterController {
    @Autowired
    private Monster monster;

    @RequestMapping(value = "/monster")
    public Monster monster(){
        return monster;
    }
}
```

### 七、spring initailizr

创建spring boot、maven项目的另外一些方法

创建的pom.xml会爆红，注意版本问题，设置与当前spring boot版本一致，刷新即可

### 八、yaml

1. **形式为** key: value**；注意**: **后面有空格** 

2. **区分大小写** 
3. 使用缩进表示层级关系** 
4. **缩进不允许使用** tab**，只允许空格** [**有些地方也识别** tab , **推荐使用空格**] 
5. **缩进的空格数不重要，只要相同层级的元素左对齐即可**
6. **字符串无需加引号** 
7. yml **中**, **注释使用** #

数据类型：字面量  （单个的  不可再分的数据类型）

​					对象 （键值对的集合 ：map  hash  set  object）

​					数组  （array   list   queue）

```yaml
# 配置一个bean对象的属性
monster:
  id: 001
  name: 成凯璇
  age: 25
  is-married: false
  birth: 1999/04/30
  #对象
  car: {name: 梅赛德斯, price: 300000}  # 行内
  #数组
  skill: [羽毛球, 跑步]
  #list 也属于数组  所以和数组写法一样
  hobby:
    - 抽烟
    - 喝酒
  # 对象 map set
  wife: {wife01: 玉面狐狸, wife02: 铁扇公主}
  salaries:
    - 10000
    - 20000
  cars:
    group01:
      - {name: 宝马, price: 300000}
      - {name: 法拉利, price: 700000}
    group02:
      - {name: 奔驰, price: 400000}
      - {name: 小米su7, price: 188888}
```

### 九、静态资源访问--WEB

类路径（resource目录下）下的文件：/static  /public /resources  /META-INF/resources/  （源码里写好的）这些静态资源可以直接被访问   例如 css  js  图片等等

#### 1、路径冲突问题

上面说的直接访问   Url为：localhost：8080/1.jpg，但是有可能@RequestMapping映射路径也是/1.jpg，这样就会有路径冲突，此时controller会拦截，优先显示controller的内容

解决：

```yaml
spring:
  mvc:
    static-path-pattern: /ckx/**
```

修改后，访问资源的路径就多加了/ckx，controller内的映射路径不变

#### 2、修改默认的静态资源路径，类似于/static，可以直接访问静态资源

```yaml
# 与静态资源相关的配置
spring:
  # 配置静态资源的访问路径
  mvc:
    static-path-pattern: /ckx/**
  # 增加默认访问路径
  # 这样配置以后，原本的四个默认路径就被覆盖了，所以需要什么静态资源路径都得自己配置，除非只用默认的
  web:
    resources:
      static-locations: [classpath:/chengk/]
```

### 十、REST风格请求处理

#### 1、问题

理解：@RequestMapping和@Requestbody

使用@RequestMapping时，会使用视图解析器处理return回来的字符串，去找对应的xxx.html资源，**如果找不到资源**（**优先视图解析器**），也会去controller里面找有没有配置对应的/xxx路径，如果有就会跳转过去

使用@Requestbody时，会直接用该注解处理return回来的字符串，直接显示在前端页面，不会用视图解析器处理

这里涉及到一个思考题？为什么这样写会在前端页面返回一个字符串，而不是某个资源文件

注意@RestController，相当于@Controller和@Requestbody，加上@Requestbody，会用这个注解处理数据，直接返回字符串，而不用视图解析器进行处理。

```java
@RestController
public class AnimalController {

    @GetMapping("/animal")
    public String getAnimal() {
        return "GET-获取动物";
    }
```

#### 2、springboot配置视图解析器

注意：如果配置了static-path-pattern: /ckx/**，那么下面的前缀就变为#prefix: /ckx/，但是一般用默认的，因为约定大于配置

```yaml
mvc:
  #static-path-pattern: /ckx/**
  # 配置打开REST过滤器 默认是关闭的
  hiddenmethod:
    filter:
      enabled: true
  view:
    #prefix: /ckx/
    suffix: .html
    prefix: /
```

### 十一、接收参数相关的注解

#### 1、@PathVariable

```java
// 测试@PathVariable
    /**
     * 此时要传的参数为id和name，可以分开一个一个传
     * 也可以使用map，map会把所有的param全部接收，方便遍历
     * 记住：在传递参数这里，map只能定义为<String, String>,无论传递的是什么类型，都被当作字符串对待，
     * 如果想用Integer，拿出来单独进行转换
     * @param id
     * @param name
     * @param map
     * @return
     */
    @GetMapping("/path1/{id}/{name}")
    public String pathVariable(@PathVariable("id") Integer id,
                               @PathVariable("name") String name,
                               @PathVariable Map<String, String> map
                               ){
        System.out.println("id--" + id);
        System.out.println("name--" + name);
        System.out.println("map--" + map);
        // 输出结果
//        id--1
//        name--ckx
//        map--{name=ckx, id=001}
        return "success";
    }
```

#### 2、@RequestHeader

```java
//获取请求头信息@RequestHeader
//不区分大小写
@GetMapping("/path2")
public String getHeader(@RequestHeader("host") String host,
                        @RequestHeader Map<String, String> header
                        ){
    System.out.println("host--" + host);
    System.out.println("map--" + header);
    return "success";
}
```

#### 3、@RequestParam

```java
// 获取参数@RequestParam
// 注意：如果在表单中有一个对象多个值，例如这里的hobby，如果用map接收，它只能接收到第一个参数的值，也就是只有GP，没有DNF
@GetMapping("/path3")
public String getParam(@RequestParam("name") String name,
                       @RequestParam("age") Integer age,
                       @RequestParam("hobby") List<String> list,
                       @RequestParam Map<String, String> map
                       ){
    System.out.println("list--" + list);
    System.out.println("map--" + map);
    return "Success";
}
```

#### 4、@CookieValue

```java
// 测试@CookieValue
@GetMapping("/path4")
public String getCookie(@CookieValue(value = "cookie_key", required = false) String cookieValue,
                        @CookieValue(value = "username", required = false)Cookie cookie){
    System.out.println(cookieValue);
    System.out.println(cookie);
    return "Success";
}
```

#### 5、@RequestBody

```java
// 测试@RequestBody
@PostMapping("/path5")
public String getJson(@RequestBody String content){
    System.out.println(content);
    return "Success";
}
```

#### 6、@RequestAttribute

```java
@Controller
public class RequestAttributeController {

    /**
     * 这里注意请求转发，此时这个controller类用的是@Controller注解，也配置了视图解析器，所以可以转发到path7资源
     * 但是如果使用的是@RestController注解，即便配置了视图解析器，也无法进行请求转发，会直接将forward:/path7以json形式显示在前端页面
     * @param request
     * @return
     */
    @GetMapping("/path6")
    public String setValue(HttpServletRequest request){
        request.setAttribute("name", "小成");
        return "forward:/path7";
    }

    @GetMapping("/path7")
    @ResponseBody
    public String getValue(@RequestAttribute("name") String username,
                           HttpServletRequest request){
        System.out.println("username--" + username);
        Object o = request.getAttribute("name");
        System.out.println("o" + o);
        return "success";
    }
}
```

#### 7、复杂参数

#### 8、自定义对象参数
