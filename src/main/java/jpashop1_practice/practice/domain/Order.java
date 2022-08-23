package jpashop1_practice.practice.domain;


import jpashop1_practice.practice.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static jpashop1_practice.practice.domain.DeliveryStatus.*;
import static jpashop1_practice.practice.domain.OrderStatus.CANCEL;
import static jpashop1_practice.practice.domain.OrderStatus.ORDER;
import static lombok.AccessLevel.*;

@Entity
@Getter @Setter
@Table(name = "orders")
@NoArgsConstructor(access = PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    //==생성 메소드==//
    //Order 생성 메소드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){

        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;

    }

    //==비지니스 메소드==//
    //1. 주문취소
    public void cancel() throws NotEnoughStockException {

        if (this.delivery.getStatus() == COMP){
            throw new IllegalStateException("주문완료된 상품은 취소할 수 없습니다.");
        }

        this.setStatus(CANCEL);

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }



    //==조회로직==//
    //1. 주문에 담긴 주문상품의 전체 가격 조회 메소드
    public int getTotalPrice(){
        int total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getTotalPrice();
        }
        return total;
    }



    //== 연관관계 편의 메소드=//
    public void setMember(Member member){
        member.getOrders().add(this);
        this.member = member;
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        delivery.setOrder(this);
        this.delivery = delivery;
    }






}
