package com.example.finance_tracker.Controller;
import com.example.finance_tracker.Entity.Transaction;
import com.example.finance_tracker.Service.TransactionService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.StripeObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.stripe.model.PaymentIntent;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/stripe")
public class StripeWebhookController {

    @Autowired
    TransactionService transactionService;

    @Value("${STRIPE_SECRET_KEY}")
    private String stripeSecretKey;

    @Value("${STRIPE_WEBHOOK_SECRET}")
    private String STRIPE_WEBHOOK_SECRET;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload,
                                                    @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
           // System.out.println("Received Payload: " + payload);
           // System.out.println("Received Stripe-Signature: " + sigHeader);
            // Verify the webhook signature to ensure authenticity
            Event event = Webhook.constructEvent(payload, sigHeader, STRIPE_WEBHOOK_SECRET);


            // Handle the event
            if ("payment_intent.succeeded".equals(event.getType())) {
                // Payment was successful, process transaction data
                processTransaction(event);
                // Print the entire event data
                //System.out.println("Received payment intent Stripe Event: " + event);
            } else {
                // Handle other event types if necessary
                //System.out.println("Received other Stripe Event: " + event);

            }

            return ResponseEntity.ok("Success");

        } catch (Exception e) {
            return ResponseEntity.status(400).body("Webhook Error: " + e.getMessage());
        }
    }

    private void processTransaction(Event event) throws StripeException {
        System.out.println("processTransaction called");
        //System.out.println("Received event type: " + event.getType());
        //System.out.println("Event data: " + event.getData().getObject());
        //System.out.println("Payment Intent ID: " );//+ event.getDataObjectDeserializer().getObject().getId());
        //System.out.println("event : "+ event);

        // Extract Payment Intent data from the event
        //PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
        PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
        System.out.println("PaymentIntent : "+ paymentIntent);

            Transaction transaction = new Transaction();

            // Convert the created timestamp to LocalDateTime
            long createdTimestamp = paymentIntent.getCreated(); // Assuming this is a long (epoch second)
            LocalDateTime transactionDate = Instant.ofEpochSecond(createdTimestamp)
                    .atZone(ZoneId.systemDefault())  // Use system's default time zone
                    .toLocalDateTime();



            // Setting transaction details from the Stripe payment intent
            transaction.setTransactionType(Transaction.TransactionType.valueOf("EXPENSE"));  // Assuming it's an expense
            transaction.setCategoryRef(paymentIntent.getStatementDescriptor());
            transaction.setTransactionDate(transactionDate);
            transaction.setRecurring(false);  // Example of checking recurrence
            transaction.setTransactionAmount(BigDecimal.valueOf(paymentIntent.getAmountReceived()/100.0));  // Stripe returns amounts in cents, so divide by 100
            transaction.setTransactionDescription(paymentIntent.getDescription());

            //transaction.setTags(Collections.singletonList("lunch, work"));  // You can define this manually or extract from metadata
            //transaction.setRecurrencePattern("HOURLY");  // Set recurrence pattern from your logic
            //transaction.setRecurringEndDate("2025-03-09T00:00:00");  // Example end date

            transaction.printTransaction();

            //crate new transaction and save to db
            transactionService.createAutomatedTransaction(transaction,paymentIntent.getCustomer());

            System.out.println("transaction: " + transaction);
            System.out.println("Received payment intent Stripe Event and created transaction: ");



    }
}

//http://hasinthafinancetracker999.ngrok.io/webhook/stripe

//    stripe listen --forward-to http://localhost:8080/api/stripe/webhook
//stripe trigger payment_intent.succeeded
//stripe trigger checkout.session.completed

