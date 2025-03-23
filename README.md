
Connecting to Stripe Digital Wallet simulation
in command prompt,

//Install Stripe on windows
choco install stripe
stripe login

//Start Stripe webhook - open cmd and run following
stripe listen --forward-to http://localhost:8000/api/stripe/webhook

//trigger payment events - use another cmd
stripe trigger payment_intent.succeeded
stripe trigger payment_intent.payment_failed

How to Run Unit Tests
mvn test //all tests
mvn test -Dtest=TransactionControllerTest  //specific test
