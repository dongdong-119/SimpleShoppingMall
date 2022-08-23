package jpashop1_practice.practice.service;

import jpashop1_practice.practice.domain.Address;
import jpashop1_practice.practice.domain.Member;
import jpashop1_practice.practice.domain.Order;
import jpashop1_practice.practice.domain.OrderStatus;
import jpashop1_practice.practice.domain.item.Book;
import jpashop1_practice.practice.exception.NotEnoughStockException;
import jpashop1_practice.practice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;


    @Test
    public void 상품_주문() throws Exception{
        //given

        Member member = createMember();

        Book book = createBook("JAPA", 10000, 10);

        int count = 10;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), count);

        Order order = orderRepository.findOne(orderId);

        //then
        //주문상태
        assertEquals(OrderStatus.ORDER, order.getStatus());
        //주문상품 수 = 1
        assertEquals(1, order.getOrderItems().size());
        //주문한 아이템의 재고
        assertEquals(0, book.getStockQuantity());
        //주문한 상품의 가격
        assertEquals(10000*10, order.getTotalPrice());
    }
    
    @Test
    public void 재고수량초과() throws Exception{
        //given
        Book book = createBook("JPA", 10000, 10);
        Member member = createMember();


        int orderCount = 11;

        Assertions.assertThrows(NotEnoughStockException.class,
                ()->orderService.order(member.getId(), book.getId(), orderCount));

    }


    @Test
    public void 주문_취소() throws Exception{
        //given

        Book book = createBook("JPA", 10000, 10);
        Member member = createMember();

        Long orderId = orderService.order(member.getId(), book.getId(), 8);

        assertEquals(2, book.getStockQuantity());
        Order order = orderRepository.findOne(orderId);

        order.cancel();

        assertEquals(10, book.getStockQuantity());
        //when

        //then

    }



    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("memberA");
        member.setAddress(new Address("경기도", "1거리", "123-123"));


        em.persist(member);

        return member;
    }
}