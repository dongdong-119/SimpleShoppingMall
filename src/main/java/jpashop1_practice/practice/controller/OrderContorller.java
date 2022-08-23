package jpashop1_practice.practice.controller;

import jpashop1_practice.practice.domain.Member;
import jpashop1_practice.practice.domain.Order;
import jpashop1_practice.practice.domain.OrderSearch;
import jpashop1_practice.practice.domain.item.Item;
import jpashop1_practice.practice.exception.NotEnoughStockException;
import jpashop1_practice.practice.service.ItemService;
import jpashop1_practice.practice.service.MemberService;
import jpashop1_practice.practice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderContorller {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;


    @GetMapping("/order")
    public String createOrder(Model model){

        List<Member> members = memberService.findAll();
        List<Item> items = itemService.findItems();


        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }


    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) throws NotEnoughStockException {

        orderService.order(memberId, itemId, count);

        return "redirect:/orders";
    }


    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch,
                            Model model){

        List<Order> orders = orderService.findOrders(orderSearch);

        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId")Long orderId) throws NotEnoughStockException {

        orderService.cancelOrder(orderId);

        return "redirect:/orders";

    }


}
