package com.example.HopeConnect.Services;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeCheckoutService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public String createCheckoutSession(Long amount) throws Exception {
        Stripe.apiKey = stripeApiKey;

        // إعداد الجلسة
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("https://yourdomain.com/success") // رابط بعد النجاح
                        .setCancelUrl("https://yourdomain.com/cancel")   // رابط بعد الإلغاء
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("usd")
                                                        .setUnitAmount(amount) // المبلغ بالـ سنت
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("Donation to HopeConnect")
                                                                        .build()
                                                        )
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

        Session session = Session.create(params);

        // رجع رابط الدفع
        return session.getUrl();
    }
}
