mvn clean install

mvn spring-boot:run

## Request and response from postman. This project in mongo

Get:
http://localhost:8088/payment/status

-- Add credit card -- post method
http://localhost:8088/payment/addcard

{
    "cardNumber": "1111111111111111",
    "cardHolder": "nupur sharma",
    "cvv": "999",
    "expiryDate": "2025-12-31",
    "balance": 10000.00,
    "createdBy": "admin",
    "updatedBy": "admin"
}



URL: POST 
http://localhost:8088/payment/initiate
Request Body: ->raw

{
    "cardNumber": "1234123412341234",
    "cvv": "345",
    "amount": 1000.00
}


#POST:
http://localhost:8088/payment/verify

Request Body:

{
    "transactionId": "66fb5a86218c02f14096403e",
    "otp": "264224"
}

#update card put method
http://localhost:8088/payment/updatecard

{
    "cardNumber": "3333333333333333",
    "cardHolder": "xyz sharma",
    "expiryDate": "2022-10-01",
    "cvv": "123"
}

#DELETE card  delete method
http://localhost:8088/payment/deletecard/3333333333333333

Success-- (204 No Content):


# return all cards
Method: GET
URL: http://localhost:8088/payment/allcards



# mongo db docker
docker run -d --name mongo -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=admin123 -p 27017:27017 mongo
docker exec -it  mongo bash   #run mongo

use admin;
db.createUser({
  user: "testUser",
  pwd: "testUser",
  roles: [{ role: "dbAdmin", db: "test" },
  { role: "readWrite", db: "admin" } ]
})


# Mongo
use test;
db.auth("testUser", passwordPrompt()) ;

db.credit_cards.drop()
db.transactions.drop()
db.otp_requests.drop()

db.credit_cards.find();
db.transactions.find();
db.otp_requests.find();

db.credit_cards.insertMany([
    {
        "cardNumber": "1234567812345678",
        "cardHolder": "Chandra Shekhar",
        "cvv": "123",
        "expiryDate": ISODate("2025-12-31T00:00:00Z"),   // MongoDB stores dates in ISO format
        "balance": NumberDecimal("5000.00")
    },
    {
        "cardNumber": "1234123412341234",
        "cardHolder": "Nitu Prabha",
        "cvv": "345",
        "expiryDate": ISODate("2026-12-31T00:00:00Z"),
        "balance": NumberDecimal("5000.00")
    }
]);

db.transactions.insertMany([
    {
        "creditCard": ObjectId("66fb13c1218c02f140964037"),  
        "amount": NumberDecimal("100.00"),
        "otp": "123456",
        "status": "pending",
        "createdAt": new Date()  
    },
    {
        "creditCard": ObjectId("66fb13c1218c02f140964038"),  
        "amount": NumberDecimal("100.00"),
        "otp": "654321",
        "status": "approved",
        "createdAt": new Date()
    }
]);

db.credit_cards.find({"cardNumber": "1234567812345678"}, {_id: 1});


db.otp_requests.insertMany([
    {
        "creditCard": ObjectId("66fb13c1218c02f140964037"),  
        "otp": "123456",
        "expiresAt": new Date(new Date().getTime() + 5 * 60 * 1000)  // Adds 5 minutes to the current time
    },
    {
        "card_id": ObjectId("66fb13c1218c02f140964038"),  
        "otp": "654321",
        "expiresAt": new Date(new Date().getTime() + 1 * 60 * 1000)  // Adds 1 minute to the current time
    }
]);


# postgres 16

docker run -d --name postgres16 -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres postgres:16 postgres
docker exec -it postgres16 bash
psql -U postgres

create database payment;
\c payment



# postgres

CREATE TABLE credit_cards (
    id SERIAL PRIMARY KEY,
    card_number VARCHAR(16) NOT NULL,
    card_holder VARCHAR(100) NOT NULL,
    cvv VARCHAR(3) NOT NULL,
    expiry_date DATE NOT NULL,
    balance DECIMAL(15, 2) NOT NULL
);

INSERT INTO credit_cards (card_number, card_holder, cvv, expiry_date, balance)
VALUES ('1234567812345678', 'chandra shekhar', '123', '2025-12-31', 5000.00);
INSERT INTO credit_cards (card_number, card_holder, cvv, expiry_date, balance)
VALUES ('1234123412341234', 'Nitu Prabha', '123', '2025-12-31', 5000.00);


CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    card_id INT REFERENCES credit_cards(id),
    amount DECIMAL(15, 2) NOT NULL,
    otp VARCHAR(6),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO transactions (card_id, amount, otp, status, created_at)
VALUES (1, 100.00, '654321', 'pending', CURRENT_TIMESTAMP);
INSERT INTO transactions (card_id, amount, otp, status, created_at)
VALUES (2, 100.00, '654321', 'pending', CURRENT_TIMESTAMP);


CREATE TABLE otp_requests (
    id SERIAL PRIMARY KEY,
    card_id INT REFERENCES credit_cards(id),
    otp VARCHAR(6) NOT NULL,
    expires_at TIMESTAMP NOT NULL
);

INSERT INTO otp_requests (card_id, otp, expires_at)
VALUES (1, '123456', CURRENT_TIMESTAMP + INTERVAL '5 minutes');
INSERT INTO otp_requests (card_id, otp, expires_at)
VALUES (2, '654321', CURRENT_TIMESTAMP + INTERVAL '1 minutes');


