
Connecting to Stripe Digital Wallet simulation
in command prompt,

choco install stripe
stripe login
stripe listen --forward-to localhost:8000/stripe/webhook

//trigger payment events.
stripe trigger payment_intent.succeeded
stripe trigger payment_intent.payment_failed

How to Run Unit Tests

mvn test //all tests
mvn test -Dtest=TransactionControllerTest  //specific test