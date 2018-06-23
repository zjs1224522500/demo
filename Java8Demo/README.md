# Java 8 Demo
### lamda expression
### stream
### interface default method
### Optional
- [Reference Link Runoob.com](http://www.runoob.com/java/java8-optional-class.html)
- [Reference Link Oracle API](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)

#### Implement
- Use **Optional** class to avoid NullPointerException
```java
class Test {
    
    void testOriginal() {
        userList.stream().forEach(user -> {
            // check if object is null
            if(null != user.getAddress()) {
                System.out.println(user.getAddress().getCity());
            }
        });
    }
    
    void testJava8Optional() {
        userList.stream().forEach(user -> {
            Optional.ofNullable(user.getAddress()).ifPresent(address -> {
                System.out.println(address.getCity()); 
            }); 
        });
    }
}
```
- Other implements (**map,filter**)
```java
class OptionalMethodTest {

    @Test
    public void testOfNullable() {
        System.out.println(Optional.ofNullable(createUser()));
        System.out.println(Optional.ofNullable(null));
        System.out.println(Optional.of(createUser()));
        System.out.println(Optional.of(null));
    }

    @Test
    public void testOrElse() {
        User user = null;
        User shunzi = createUser();
        shunzi.setName("shunzi");
        System.out.println(Optional.ofNullable(user).orElse(createUser()));
        System.out.println(Optional.ofNullable(shunzi).orElse(createUser()));

        System.out.println(Optional.ofNullable(user).orElseGet(() -> createUser()));

        Optional.ofNullable(shunzi).orElseThrow(() -> new NullPointerException("user is null"));
        System.out.println("**************************");
        Optional.ofNullable(user).orElseThrow(() -> new NullPointerException("user is null"));
    }

    @Test
    public void testMap() {
        User user = null;
        User shunzi = createUser();

        System.out.println(Optional.ofNullable(shunzi).map(u -> u.getName()).get());

        OptionalMethodTest outer = new OptionalMethodTest();
        OptionalMethodTest.TestFlatMap testFlatMap = outer.new TestFlatMap();
        testFlatMap.setName("shunzi");
        System.out.println(Optional.ofNullable(testFlatMap).flatMap(u -> u.getName()).get());
    }

    @Test
    public void testPresent() {
        User user = null;
        User shunzi = createUser();

        System.out.println(Optional.ofNullable(user).isPresent());
        System.out.println(Optional.ofNullable(shunzi).isPresent());
        Optional.ofNullable(user).ifPresent(u -> System.out.println(u));
        Optional.ofNullable(shunzi).ifPresent(u -> System.out.println(u));
    }

    @Test
    public void testFilter() {
        User user = null;
        User shunzi = createUser();
        Optional<User> user1 = Optional.ofNullable(shunzi).filter( u -> u.getName().length() > 6);
        System.out.println(user1);
        Optional<User> user2 = Optional.ofNullable(shunzi).filter( u -> u.getName().length() < 6);
        System.out.println(user2);
    }

    private static User createUser() {
        System.out.println("execute create user");
        User user = new User();
        user.setUserId("i348910");
        user.setName("elvis");
        return user;
    }

    class TestFlatMap {
        private String name;

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

```