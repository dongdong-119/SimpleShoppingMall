package jpashop1_practice.practice.service;


import jpashop1_practice.practice.domain.*;
import jpashop1_practice.practice.domain.item.Item;
import jpashop1_practice.practice.exception.NotEnoughStockException;
import jpashop1_practice.practice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jpashop1_practice.practice.domain.DeliveryStatus.READY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderRepository orderRepository;


    /*
    * 주문 메소드
    * 현재는 한 상품만 주문할 수 있도록(한 상품으로만 Order 만들 수 있도록 설계) 설계
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) throws NotEnoughStockException {

        //엔티티 조회
        Member member = memberService.findOne(memberId);
        Item item = itemService.findOne(itemId);


        //배송정보 설정
        Delivery delivery = new Delivery();
        delivery.setStatus(READY);
        delivery.setAddress(member.getAddress());

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);


        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);


        orderRepository.save(order);

        return order.getId();
    }


    /**
     * 주문 취소 메소드
     *
     */
    @Transactional
    public void cancelOrder(Long orderId) throws NotEnoughStockException {

        Order order = orderRepository.findOne(orderId);

        order.cancel();
    }



    /*
     * 주문검색
     */
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByCriteria(orderSearch);
    }
}
