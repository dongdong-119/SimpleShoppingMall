package jpashop1_practice.practice.domain.item;


import jpashop1_practice.practice.domain.Category;
import jpashop1_practice.practice.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.InheritanceType.*;

@Entity
@Getter @Setter
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();



    //==비지니스 로직==//

    /*
    상품 재고 추가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }


    /*
    상품 재고 제거
     */
    public void removeStock(int quantity) throws NotEnoughStockException {

        int tempQuantity = this.stockQuantity - quantity;

        if (tempQuantity < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }

        this.stockQuantity = tempQuantity;
    }

}
