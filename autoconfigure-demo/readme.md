本实例演示springboot的自定义自动配置
引入的module包 
greeting
greeting-autoconfigure

对于spring版本低于4.x的 可能无法使用springboot的自动配置
需要扩展该功能时，解决思路如下：
条件判断：
    通过BeanFactoryPostProcessor进行判断
配置加载：
    编写Java Config类
    引入配置类，通过component-scan或者通过XML文件import
    参考示例引入 module包
  greeting
  greeting-autoconfigure-backport