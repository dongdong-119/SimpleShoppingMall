package jpashop1_practice.practice.domain;


import jpashop1_practice.practice.domain.item.Item;
import jpashop1_practice.practice.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 당시의 가격

    private int count; //주문 수량


    //생성메소드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) throws NotEnoughStockException {
        OrderItem orderItem = new OrderItem();

        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);

        return orderItem;
    }



    //주문 상품 취소 메소드
    public void cancel() throws NotEnoughStockException {

        getItem().addStock(getCount());

    }

    //주문 상품 가격 조회 메소드
    public int getTotalPrice() {
        return getCount() * getOrderPrice();
    }
}
