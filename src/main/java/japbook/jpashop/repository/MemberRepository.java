package japbook.jpashop.repository;

import japbook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext // JPA 엔티티 매니저를 주입 받을 수 있다.
    private EntityManager em;

    public void save(Member membmer) {
        em.persist(membmer);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAlList() {
        // SQL 은 테이블을 대상으로 하지만 JPQL 은 Member 엔티티 객체에 대한 조회를 함
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 파라미터 바인딩을 통한 특정 조건을 통한 질의
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
