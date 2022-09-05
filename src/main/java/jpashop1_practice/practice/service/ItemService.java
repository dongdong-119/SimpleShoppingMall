package jpashop1_practice.practice.service;


import jpashop1_practice.practice.domain.item.Item;
import jpashop1_practice.practice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    // 추가 메소드
    @Transactional
    public void saveItem(Item item){

        itemRepository.save(item);

    }

    // 삭제 메소드
    @Transactional
    public void deleteItem(Item item){

        itemRepository.delete(item);

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }


    public Item findOne(Long itemId){

        return itemRepository.findOne(itemId);

    }
}
