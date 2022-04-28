package com.zayshop.service;

import com.zayshop.dto.Mail;
import com.zayshop.dto.OrderDTO;
import com.zayshop.dto.OrderDetailDTO;
import com.zayshop.dto.RestResponse;
import com.zayshop.entity.Account;
import com.zayshop.entity.Order;
import com.zayshop.entity.Orderdetail;
import com.zayshop.entity.Product;
import com.zayshop.repository.AccountRepository;
import com.zayshop.repository.OrderDetailRepository;
import com.zayshop.repository.OrderRepository;
import com.zayshop.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    EmailSenderService emailSenderService;


    public List<OrderDTO> getOrder(String status) {
        List<Order> listOrder = orderRepository.searchOrderByStatus(status);
        List<OrderDTO> listDTO = listOrder.stream().map(item -> OrderDTO.builder()
                .id(item.getId())
                .address(item.getAddress())
                .orderDate(item.getOrderDate())
                .nameReceived(item.getNameReceived())
                .username(item.getUsername().getUsername())
                .phoneNumber(item.getPhoneNumber())
                .status(item.getStatus())
                .build()).collect(Collectors.toList());
        return listDTO;
    }

    public void handlingOrder(OrderDTO dto) throws Exception {
        log.info("start handlingOrder");
        Optional<Order> optional = orderRepository.findById(dto.getId());
        if (optional.isEmpty()) throw new Exception("Not found Order");
        Order entity = new Order();
        BeanUtils.copyProperties(dto, entity);
        entity.setUsername(optional.get().getUsername());
        orderRepository.save(entity);
        if (entity.getStatus().equalsIgnoreCase("delivering")){
            Mail mail = new Mail();
            mail.setMailSub("XÁC NHẬN ĐƠN HÀNG");
            mail.setMailTo(entity.getUsername().getEmail());
            mail.setMailContent("<h4>Đơn hàng của bạn đã được người bán xác nhận.</h4>" +
                    "<h4>Đơn hàng đang trên đường vận chuyển. Bạn vui lòng để ý điện thoại</h4>" +
                    "<h4>Xin cảm ơn!</h4>");
            try {
                emailSenderService.sendEmail(mail);
            } catch (Exception e) {
                throw  new Exception("send email fail");
            }
        }
        log.info("end handlingOrder");
    }

    public List<OrderDetailDTO> getOrderDetailById(Integer id) throws Exception {
        Optional<Order> optional = orderRepository.findById(id);
        if (optional.isEmpty()) throw new Exception("Not found Order");
        List<Orderdetail> orderdetails = detailRepository.findAllByOrderID(optional.get());
        List<OrderDetailDTO> listDto = orderdetails.stream().map(
                item -> OrderDetailDTO.builder()
                        .id(item.getId())
                        .orderId(item.getOrderID().getId())
                        .productId(item.getProductID().getId())
                        .price(item.getPrice())
                        .productName(item.getProductID().getName())
                        .quantity(item.getQuantity())
                        .build()
        ).collect(Collectors.toList());
        return listDto;
    }

    public List<OrderDTO> findAllOrderByUsername(String username) throws Exception {
        Optional<Account> account = accountRepository.findById(username);
        if (account.isEmpty()) throw new Exception("Account not found");
        List<Order> orders = orderRepository.findAllByUsername(account.get());
        List<OrderDTO> listDTO = orders.stream().map(
                item -> OrderDTO.builder()
                        .id(item.getId())
                        .username(item.getUsername().getUsername())
                        .orderDate(item.getOrderDate())
                        .nameReceived(item.getNameReceived())
                        .phoneNumber(item.getPhoneNumber())
                        .address(item.getAddress())
                        .status(item.getStatus())
                        .build()
        ).collect(Collectors.toList());
        return listDTO;
    }

    public List<OrderDetailDTO> findAllOrderDetailByOrderId(Integer id) throws Exception {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) throw new Exception("Order Not Found");
        List<Orderdetail> orderdetailList = detailRepository.findAllByOrderID(order.get());
        List<OrderDetailDTO> detailDTOS = orderdetailList.stream().map(
                item -> OrderDetailDTO.builder()
                        .id(item.getId())
                        .orderId(item.getOrderID().getId())
                        .productId(item.getProductID().getId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .productName(item.getProductID().getName())
                        .build()
        ).collect(Collectors.toList());
        return detailDTOS;
    }

    public Order addOrder(OrderDTO dto) {
        Order entity = new Order();
        BeanUtils.copyProperties(dto, entity);
        Optional<Account> account = accountRepository.findById(dto.getUsername());
        entity.setUsername(account.get());
        entity.setOrderDate(LocalDate.now());
        entity.setStatus("pending");
        return orderRepository.save(entity);
    }

    public void addOrderDetail(OrderDetailDTO dto) {
        Optional<Order> order = orderRepository.findById(dto.getOrderId());
        Optional<Product> product = productRepository.findById(dto.getProductId());
        Orderdetail orderdetail = new Orderdetail();
        BeanUtils.copyProperties(dto, orderdetail);
        orderdetail.setOrderID(order.get());
        orderdetail.setProductID(product.get());
        detailRepository.save(orderdetail);
    }

    public OrderDTO updateStatus(OrderDTO dto) throws Exception {
        Optional<Order> optional = orderRepository.findById(dto.getId());
        if (optional.isEmpty()) throw new Exception("Not found Order");
        optional.get().setStatus(dto.getStatus());
        Order order = orderRepository.save(optional.get());
        BeanUtils.copyProperties(order, dto);
        dto.setUsername(order.getUsername().getUsername());
        return dto;
    }

    public void sendEmail(Integer id) throws Exception {
        log.info("id " + id);
        Optional<Order> optional = orderRepository.findById(id);
        if (optional.isEmpty()) throw new Exception("Not found Order");
        List<OrderDetailDTO> detailDTOS = findAllOrderDetailByOrderId(id);
        log.info("size " + detailDTOS.size());
        Double amount = 0.0;
        String itemContent = "";
        for (OrderDetailDTO item :
                detailDTOS) {
            amount += item.getPrice();
            itemContent +=   "<tr style=\"border: 1px solid black\">\n" +
                    "        <td style=\"border: 1px solid black\">"+ item.getProductName()+" </td>\n" +
                    "        <td style=\"border: 1px solid black\">"+ item.getPrice()+ "</td>\n" +
                    "        <td style=\"border: 1px solid black\">"+ item.getQuantity() + "</td>\n" +
                    "        <td style=\"border: 1px solid black\">" + (item.getPrice() * item.getQuantity()) +"</td>\n" +
                    "    </tr>\n";
        }

        Mail mail = new Mail();
        mail.setMailSub("ĐẶT HÀNG THÀNH CÔNG");
        mail.setMailTo(optional.get().getUsername().getEmail());
        String headContent = "<h4> Bạn đã đặt hàng thành công vui lòng chờ xác nhận từ người bán. </h4> <br><br>" +
                "<h5>Chi tiết đơn hàng của bạn:</h5>" +
                "<table style=\"border: 1px solid black\">\n" +
                "    <thead style=\"border: 1px solid black\">\n" +
                "    <tr style=\"border: 1px solid black\">\n" +
                "        <th style=\"border: 1px solid black\">Product Name</th>\n" +
                "        <th style=\"border: 1px solid black\">Price</th>\n" +
                "        <th style=\"border: 1px solid black\">Quantity</th>\n" +
                "        <th style=\"border: 1px solid black\">Thành tiền</th>\n" +
                "    </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n";
        String endContent = "</tbody>\n" +
                "</table>\n" +
                "        <span style=\"font-weight: bold\">Tổng số sản phẩm: "+ detailDTOS.size() +"  </span> <br> <br>\n" +
                "        <span style=\"font-weight: bold\">Thành tiền: "+ amount+" </span>";
        mail.setMailContent(headContent + itemContent + endContent);
log.info("amount " + amount);
        try{
            emailSenderService.sendEmail(mail);
        }catch (Exception e){
            throw  new Exception("send email fail: " + e);
        }
    }
}
