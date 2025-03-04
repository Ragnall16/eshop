package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    Map<String, String> paymentData;
    String status;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = "SUCCESS"; // Placeholder sebelum ada subfitur voucher / bank data

        if (method == null || method.trim().isEmpty() || !method.equals("VOUCHER")) {
            throw new IllegalArgumentException();
        }
        if (paymentData == null) {
            throw new IllegalArgumentException();
        }
    }

    public void setStatus(String status) {
        String[] statusList = {"SUCCESS", "REJECTED"};
        if (Arrays.stream(statusList).noneMatch(item ->(item.equals(status)))) {
            throw new IllegalArgumentException();
        } else {
            this.status = status;
        }
    }
}