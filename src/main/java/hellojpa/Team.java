package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "team_id")  // 일대다 방식 사용시 @JoinColumn을 꼭 사용해야 함. 그렇지 않으면 조인 테이블 방식을 사용함
    private List<Member> members = new ArrayList<>(); //초기화해두면 add할 때 null pointer가 안뜨므로. 관례적으로 이렇게 해놓는다.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

}
