package hellojpa;

import javax.persistence.*;

/*
 * 권장하는 식별자 전략
 * 기본 키 제약조건 : null 아님, 유일한 값, 변하면 안된다.
 * 미래까지 이 조건을 만족하는 자연키(실제 비즈니스 정보)는 찾기 어렵다. 대리키(UUID,시퀀스 오브젝트,Auto Increment)를 사용하자.
 * 가령, 주민번호도 기본 키로 적절하지 않다.
 * 권장 : Long형 + 대체키 + 키 생성전략 사용
 * */
/*
 * 기본 생성자 필수
 * final클래스, enum, interface, inner 클래스 사용 불가!
 * 저장할 필드에 final 사용 X
 * */
@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50)
public class Member {

    /*
     * IDENTITY 전략에서는, em.persist() 호출 시점에 바로 insert 쿼리가 날아간다.
     * 영속성 컨텍스트에는 key값으로 id값을 들고 있어야 하는데, 쿼리를 보내지 않고서는 db가 정한 id를 알 수 없으므로.
     * 따로 select 쿼리는 안날아간다. JDBC 드라이버에 insert 시점에 리턴받는 식으로 짜여져 있으므로, JPA는 그것을 활용
     * 여러번 네트워크를 타긴 하지만, 한 트랜잭션 안에서 이루어지므로 큰 성능 손실은 없다.
     * 결국 시퀀스를 지원하지 않는 DB는 IDENTITY가 최선.
     * */
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR")
    @Column(name = "member_id")
    private Long id;


    @Column(name = "username")
    private String username;

//    @Column(name = "team_id")
//    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member() {
    }  //@Entity를 붙인 엔티티객체는 기본 생성자가 필요함. (리플렉션 같은 기술을 쓰기 위해)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
