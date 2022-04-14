package japbook.jpashop.repository;

import japbook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            // merge는 업데이트랑 비슷한것
            // merge는 파라미터로 넘어온 준영속 엔티티의 식별자 값으로 엔티티에서 조회한 후
            // item과 mergeItem은 다르다!(*item은 기존에 아직 트랜잭션으로 처리되기전 데이터 mergeItem은 데이터가 반영된 후 데이터
            Item mergeItem = em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
