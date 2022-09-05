package jpashop1_practice.practice.domain;


import jpashop1_practice.practice.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
                joinColumns = @JoinColumn(name = "category_id"),
                inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();



    // 상품과 카테고리 편의메소드(카테고리에 만들면됨)
    // 상품 저장 시 카테고리 지정
    public void addItem(Item item){
        getItems().add(item);

        for (Item item1 : items) {
            item1.getCategories().add(this);
        }
    }

}
