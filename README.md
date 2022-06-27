# Spring boot testing

## Tips

- We don't add @Transactional to methods in service class because by default all thods in JapRepository are.
- The @DataJpaTest annotation doesn't load other Spring beans (@Components, @Controller, @Service and annotated beans)
  into ApplicationContext.
-

### Tests syntax

``` java
@Test
public void given_when_then() {
    //given - precondition or setup
    
    //when - action or the behaviour we're testing
    
    //then - verify thee output
}
```