package br.edu.ifpb.padroes.service.order;

import br.edu.ifpb.padroes.domain.Order;
import br.edu.ifpb.padroes.service.log.FileLogHandler;
import br.edu.ifpb.padroes.service.log.LogService;
import br.edu.ifpb.padroes.service.payment.PaymentService;
import br.edu.ifpb.padroes.service.mail.EmailNotification;
import br.edu.ifpb.padroes.service.payment.PaymentStrategy;

public class OrderManager {

    public OrderManager(Order order) {
        this.orderContext = new OrderContext(order);
    }

    private OrderContext orderContext;

    private EmailNotification emailNotification = new EmailNotification();

    private PaymentService paymentService = new PaymentService();

    private LogService logServiceImpl = new LogService(new FileLogHandler());

    public void payOrder(PaymentStrategy paymentStrategy) {
        try {
            paymentService.setPaymentStrategy(paymentStrategy);
            paymentService.doPayment();
            orderContext.getOrderState().paymentApproved();
            emailNotification.sendMailNotification(String.format("Order %d completed successfully!", orderContext.getOrder().getId()));
            logServiceImpl.info("Payment finished!");
        } catch (Exception e) {
            logServiceImpl.error("Payment refused!");
            orderContext.getOrderState().paymentRefused();
            emailNotification.sendMailNotification(String.format("Order %d refused", orderContext.getOrder().getId()));
        }
    }

    public void cancelOrder() {
        orderContext.getOrderState().cancelOrder();
        emailNotification.sendMailNotification(String.format("Your order %d has been canceled", orderContext.getOrder().getId()));
        logServiceImpl.debug(String.format("Your order %d has been canceled", orderContext.getOrder().getId()));
    }

}
