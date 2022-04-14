package japbook.jpashop.service;

import japbook.jpashop.domain.item.Item;
import japbook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // readOnly면 저장이 안되니까 오버라이딩
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        // findItem은 영속상태
        Item findItem = itemRepository.findOne(itemId);
//        findItem.change(param.getName(), param.getPrice(), param.getStockQuantity()); // 메소드화 하자
        findItem.setPrice(price);  //==> dirty checking
        findItem.setName(name);  //==> dirty checking
        findItem.setStockQuantity(stockQuantity);  //==> dirty checking
        // 더이상 처리할 필요없이 @Transactional을 통해 flush를 하게 된다.
//        return findItem;
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public  Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
