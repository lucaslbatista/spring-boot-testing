# Spring boot testing

## Tips
- @WebMvcTest annotation doesn't load the other layer components like service layer or repository layer.
- We don't add @Transactional to methods in service class because by default all methods in JpaRepository are.
- The @DataJpaTest annotation doesn't load other Spring beans (@Components, @Controller, @Service and annotated beans)
  into ApplicationContext.
- For test controllers (Unit test) we use @WebMvcTest annotation
- For test controllers (Integration test) we use @SpringBootTest annotation
- [Assertj optionals examples](https://github.com/assertj/assertj-examples/blob/main/assertions-examples/src/test/java/org/assertj/examples/OptionalAssertionsExamples.java)
### Tests syntax

``` java
@Test
public void given_when_then() {
    //given - precondition or setup
    
    //when - action or the behaviour we're testing
    
    //then - verify thee output
}
```
