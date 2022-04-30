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

            Member member1 = new Member();
            member1.setUsername("A");

            Member member2 = new Member();
            member2.setUsername("B");

            Member member3 = new Member();
            member3.setUsername("C");

            Member member4 = new Member();
            member4.setUsername("D");
            System.out.println("==================");

            //allocationSize가 50인데, 처음 호출하면 SEQ값이 1이라, 한 번 더 next value를 호출하므로 처음 두 번 시퀀스 next value 호출
                                // 실제 SEQ 값  | SEQ테이블 값
            em.persist(member1); //     1       |       1
            em.persist(member2); //     2       |      51
            em.persist(member3); //     3       |      51
            em.persist(member4); //     4       |      51       //51개까지 메모리에서 시퀀스를 세다가, 다 쓰면 50개 더 호출

            System.out.println("member1.getId() = " + member1.getId());
            System.out.println("member2.getId() = " + member2.getId());
            System.out.println("member3.getId() = " + member3.getId());
            System.out.println("member4.getId() = " + member4.getId());

            System.out.println("====================");


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();


    }
}
