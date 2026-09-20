package service;

import model.Payment;

public class PaymentService {

    private static final PaymentService instance = new PaymentService();

    private Payment currentPayment;

    private PaymentService() {

    }

    public static PaymentService getInstance() {
        return instance;
    }

    public boolean processPayment(Payment payment) {

        payment.setPaymentSuccessful(true);

        currentPayment = payment;

        return true;
    }

    public Payment getCurrentPayment() {
        return currentPayment;
    }

}