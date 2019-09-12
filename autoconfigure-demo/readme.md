本实例演示springboot的自定义自动配置<br/>
引入的module包 
greeting
greeting-autoconfigure <br/>
对于spring版本低于4.x的可能无法使用springboot的自动配置需要扩展该功能时，<br/>
解决思路如下：<br/>
条件判断：<br/>
    通过BeanFactoryPostProcessor进行判断<br/>
配置加载：<br/>
    编写Java Config类<br/>
    引入配置类，通过component-scan或者通过XML文件import<br/>
    参考示例引入 module包<br/>
  greeting<br/>
  greeting-autoconfigure-backport<br/>
