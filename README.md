# Starting with Spring Dependency Injection

## Prerequisites
- Java 17
- enable -ea in your IDE (or use it when launching java) to use assertion.
- Steps in intellij: open "run/build configuration", go to "modify options" -> "add VM options", set "-ea" in this space.
- (To be sure it is working fine just modify an assertion in main() and check it throw an Exception).

## Structure
*Controllers* = object offering you the entry point to the application (access to the logic)

*Services* = business logic your app implements (object implementing the use cases)

Usually the controllers define the endpoints in web applications.

*Repository* = objects allow you the access to data source

*Proxy* = allow you to connect with other applications (used to call other services)

*Model* = object that describe the data

## ComponentScan
Spring is not searching automatically for components, you need to add annotations @ComponentScan in config to tell it:
```
    @ComponentScan(basePackages = {"controllers", "services"})
    public class AppConfig {
    }
```
if you have just one package, the {} are not needed.

## Dependency Injection
We want the controller to refer automatically to the service, without initialize the service itself.
If we don´t do something we are going to have NullPointerException as productService is not initialized.

@Autowired annotation automatically take the service instance from the context.
As ProductService is a @Component, so the instance in the context exists
-> Spring automatically inject the dependency of productService instance.

Take care: this only work if both the classes are beans in the content (declared with @Bean or with Component)-

Options to use @Autowired:
- **Field injection**: *Not best practice.*
As in  [ProductController](src/main/java/controllers/ProductController.java).
  @Autowired in the field declaration implies the service is not final, as it is provided in a second moment.
  This does not guarantee the immutability of the value.
  For this reason generally is not recommended.
- **Setter injection**: *Not best practice.* Also in this case you can´t make to field final, 
so there are the same problem of the case above.
- **Constructor injection**: *This is the recommended way.*
You can fix this problem using Autowired in the constructor, spring inject the dependency in the constuctor paramenter,
as in [ProductWithImmutableController](src/main/java/controllers/ProductWithImmutableController.java).
In last versions of spring (> spring 5), @Autowired in constructor is implicit. This only works if you have only one constructor,
as in [ProductWithImplicitAutowiredController](src/main/java/controllers/ProductWithImplicitAutowiredController.java).

Another advantage of construction injection is that provide you an easier to do unit tests. 

We can do construction dependency injection also on bean method in configuration class.
In this case you don´t need service and controller to ba a component, as you define them in conf class.
We have an example with [ProductNotAComponentController](src/main/java/controllers/ProductNotAComponentController.java) 
and the use here: 
```
public class AppConfig {

    @Bean
    //@Autowires implicit here
    ProductNotAComponentController productNotAComponentController(ProductNotAComponentService productNotAComponentService){
        return new ProductNotAComponentController(productNotAComponentService);
    }

    @Bean
    //@Autowires implicit here
    ProductNotAComponentController productNotAComponentControllerSecondOption(){
        return new ProductNotAComponentController(productNotAComponentService());
        //you can take directly the instance from the context, it is not a method call
    }

    @Bean
    ProductNotAComponentService productNotAComponentService(){
        return new ProductNotAComponentService();
    }
}
```
In the context we will have only ONE instance of ProductNotAComponentService. 
Both the controllers are referring to the same instance.

## Qualifier
If you have more than one implementation of an interface, you need to say which one to use.
You can do that giving a name at each bean instance:
```
public class AppConfig {
    @Bean
    @Qualifier("first")
    ProductServiceInterface productController() {
        return new ProductServiceImpl();
    }

    @Bean
    @Qualifier("second")
    ProductServiceInterface secondProductController() {
        return new SecondProductServiceImpl();
    }
}
```
Que @Qualifier allow you to do that.
In this case for example the instance of ProductServiceInterface that use ProductServiceImpl is called "first".
In this case for example the instance of ProductServiceInterface that use SecondProductServiceImpl is called "second".

You can automatically inject the correct one using th @Qualifier and the @Autowired together, as here 
(where we are going to use first implementation):
```
@Component
public class ProductUsingServiceInterfaceController {
    @Autowired
    @Qualifier("first")
    private ProductServiceInterface productService;
}
```