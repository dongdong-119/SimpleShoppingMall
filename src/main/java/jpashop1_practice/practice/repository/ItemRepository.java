package jpashop1_practice.practice.repository;


import jpashop1_practice.practice.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;


    //상품 추가
    public void save(Item item){

        if (item.getId() == null) {
            em.persist(item);
        } else{
            em.merge(item);
        }
    }


    //상품 단건 검색
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }


    //상품 전체 검색
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }




}
