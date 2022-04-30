package hellojpa;

import javax.persistence.*;
import java.util.Date;

/*
* 기본 생성자 필수
* final클래스, enum, interface, inner 클래스 사용 불가!
* 저장할 필드에 final 사용 X
* */
@Entity
public class Member {

    //javax.persistence의 @Id 어노테이션으로 JPA에게 PK를 알려줌.
    @Id
    private Long id;

    // DDL 생성기능 : DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다
    @Column(name ="name",nullable = true, columnDefinition = "varchar(100) default 'ddd'") // columnDefinition : 데이터베이스 컬럼 정보를 직접 줄 수 있다.
    private String username;

    @Column(unique =true, length = 10) // unique는 제약조건 이름을 설정할 수 없어서 실제로 쓰지 않고, @Table(uniqueConstraints = )으로 설정한다
    private Integer age;

    @Enumerated(EnumType.STRING)  // EnumType은 기본값이 ORDINAL(순서를 DB에 저장하는 방식)이므로, 반드시 STRING TYPE으로 지정해준다.
    private RoleType roleType;

    // 날짜 타입 매핑. LocalDate, LocalDateTime 타입을 쓰면  이 설정이 필요없다. (최신 하이버네이트 지원)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob //Large Object를 사용하려면 @Lob을 사용 (CLOB, BLOB ...) // 매핑하는 필드 타입이 문자면 CLOB, 나머지는 BLOB로 매핑
    private String description;

    @Transient // 이 필드를 컬럼에 매핑하지 않음. DB에 저장 X, 조회X. 메모리 상에서 임시로 어떤 값을 보관하고 싶을 때 사용
    private int temp;

    public Member() {}  //@Entity를 붙인 엔티티객체는 기본 생성자가 필요함. (리플렉션 같은 기술을 쓰기 위해)


}
