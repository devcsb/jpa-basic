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



            System.out.println("===========");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();


    }
}
