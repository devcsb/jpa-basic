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
            member.changeTeam(team);  // 연관관계 편의 메서드. 1 : N에서 1 쪽에 넣을지, N쪽에 넣을지 고민해서 결정.
            em.persist(member);

            team.addMember(member); // 연관관계 편의 메서드. 1 : N에서 1 쪽에 넣을지, N쪽에 넣을지 고민해서 결정.

            //영속성 컨텍스트 비워서 아래 코드의 SQL을 보기 위함
//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId());  //1차 캐시에 있음
            List<Member> members = findTeam.getMembers();

            System.out.println("==================");
            System.out.println("members = " +  findTeam);
            System.out.println("==================");

//            Member findMember = em.find(Member.class, member.getId());
//            List<Member> members = findMember.getTeam().getMembers(); //이 코드 실행하면 db에 값이 초기화되는 문제.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();


    }
}
