package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // xml의 <persistence-unit name> 값을 넣어줌.
        //EntityManagerFactory엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
        //EntityManager 는 쓰레드간에 공유X (사용하고 버려야 한다).
        /*JPA의 모든 데이터 변경은 트랜잭션 안에서 실행*/
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);  //JPA가 알아서 team의 PK값을 꺼내서 member를 insert할 때 fk로 사용한다.
            em.persist(member);

            //db에서 가져오는 쿼리를 보고싶을 때
            em.flush(); //쓰기 지연 저장소에 있는 sql을 db에 전부 날려보냄
            em.clear(); // em 초기화
            //위 2줄의 코드로 영속성컨텍스트가 완전히 비어있음을 보장하므로, 아래 코드로 인한 쿼리를 확실하게 볼 수 있음.
            Member findMember = em.find(Member.class, member.getId());

            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();


    }
}
