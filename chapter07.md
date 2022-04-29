# 토비의 스프링 3.1

## 객체 생성, 객체 해제
### C++, Java 객체 해제
```c++
class Member {
private:
    int id;
    String name;
    
public:
    void getId() {
        cout << id;
    }

    void GetName() {
        cout << name;
    }
 
};

int main {
    Member* member = new Member();
    member->GetName();
    delete member;

    return 0;
}
```

```java
public class Member {
    private int id;
    private String name;

    // getter, setter //
    public static void main(String[] args) {
        Member member = new Member();
        member.getName();
    }
}
```

### 의존관계가 있을때
```c++
class Member {
private:
    int id;
    String name;
    Role* role;
    
public:
    void getId() {
        cout << id;
    }

    void GetName() {
        cout << name;
    }
 
};

int main {
    Member* member = new Member();
    member->GetName();
    delete member; // 메모리 누수

    return 0;
}
```

```java
public class Member {
    private int id;
    private String name;
    private Role role;

    // getter, setter //
    public static void main(String[] args) {
        Member member = new Member();
        member.getName();

        // 아무 문제 없다.
    }
}
```

### 객체 생성
```java
public class Member {
    private int id;
    private String name;
    private Role role;

    public Membre(int id, String name, Role role) {
        // 대충 구현..
    }

    // getter, setter //
    public static void main(String[] args) {
        Member member = new Member(id, name, role);
        member.getName();

        // 아무 문제 없다.
    }
}
```



## DI

"확장은 항상 미래에 일어나는 일이다. 지금 당장 기능이 동작하는 데 아무런 문제가 없으면 된다고 생각하면 오늘을 위한 설계밖에 나오지 않는다. DI는 확장을 위해 필요한 것이므로 항상 미래에 일어날 변화를 예상하고 고민해야 적합한 설계가 가능해진다. DI란 결국 미래를 프로그래밍하는 것이다." - 618p

## DI와 인터페이스 프로그래밍

"DI를 적용할 때는 가능한 한 인터페이스를 사용하게 해야 한다."

1. 다형성
2. 인터페이스 분리 원칙

```java
public interface SmartPhone {
    void 전화();
}
```

```java
class IPhone implements SmartPhone {

    @Ovverride
    public void 전화() {

    }

}

class Galaxy implements SmartPhone {

    @Ovverride
    public void 전화() {
        
    }

    public void GOS() {
        
    }
}
```
