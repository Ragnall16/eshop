package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    Map<String, String> paymentData;
    String status;

    public Payment(String id, String method, Map<String, String> paymentData) {
        if (paymentData == null) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.setMethod(method);
        this.paymentData = paymentData;
        this.validateData();
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void setMethod(String method) {
        if (PaymentMethod.contains(method)) {
            this.method = method;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void validateData() {
        boolean isValid = false;
        switch (PaymentMethod.valueOf(method)) {
            case VOUCHER:
                isValid = validateVoucherMethod();
                break;
            case BANK_TRANSFER:
                isValid = validateBankMethod();
                break;
            default:
                break;
        }

        this.status = isValid ? PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue();
    }

    private boolean validateVoucherMethod() {
        String voucherCode = paymentData.get("voucherCode");
        return voucherCode != null && checkVoucherCode(voucherCode);
    }

    private boolean checkVoucherCode(String voucherCode) {
        if (voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }
        String code = voucherCode.substring(5);
        int numericCharCount = 0;
        for (char character : code.toCharArray()) {
            if (Character.isDigit(character)) {
                numericCharCount++;
            }
        }
        return numericCharCount == 8;
    }

    private boolean validateBankMethod() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        return bankName != null && !bankName.isEmpty() && referenceCode != null && !referenceCode.isEmpty();
    }
}
