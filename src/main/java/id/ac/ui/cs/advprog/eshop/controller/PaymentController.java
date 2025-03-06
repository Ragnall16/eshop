package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/detail/{paymentId}")
    public String getPaymentDetail(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);

        model.addAttribute("payment", payment);
        return "PaymentDetail";
    }

    @GetMapping("/admin/list")
    public String listPayments(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "PaymentList";
    }

    @GetMapping("/admin/detail/{paymentId}")
    public String getAdminPaymentDetail(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);

        model.addAttribute("payment", payment);

        List<String> allowedStatuses = Arrays.asList(PaymentStatus.SUCCESS.getValue(), PaymentStatus.REJECTED.getValue());
        model.addAttribute("allowedStatuses", allowedStatuses);
        return "AdminPaymentDetail";
    }

    @PostMapping("/admin/set-status/{paymentId}")
    public String setPaymentStatus(@PathVariable String paymentId, @RequestParam String status) {
        Payment payment = paymentService.getPayment(paymentId);

        return "redirect:/payment/admin/detail/" + paymentId;
    }
}