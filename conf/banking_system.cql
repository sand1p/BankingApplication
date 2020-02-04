CREATE KEYSPACE banking_system WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TABLE banking_system.account (
    id uuid,
    balance double,
    creation_date timestamp,
    type text,
    PRIMARY KEY (id)
);

CREATE TABLE banking_system.transaction (
    id uuid,
    date timestamp,
    ammount double,
    recipient_id uuid,
    sender_id uuid
    PRIMARY KEY (id, date)
);

CREATE TABLE banking_system.user (
    id uuid PRIMARY KEY,
    email_id text,
    mobile_number bigint,
    name text,
    password text
);

create accounts (
    userId UUID,
    accountID UUID,
    Primary Key(userId, accountId)
)

INSERT INTO user (id , email_id , mobile_number , name , password ) VALUES (now() )  ;



Transaction BATCH :

 INSERT INTO transaction (id , date , sender_id , recipient_id , ammount ) VALUES ( now(),toTimestamp(now()),cd50ca20-44fd-11ea-88e0-f530e33335cb,b23da521-44e7-11ea-88e0-f530e33335cb, 456.54) ;
 UPDATE account SET balance = 55 WHERE user_id =6ce64240-44f4-11ea-88e0-f530e33335cb  and id =cd50ca20-44fd-11ea-88e0-f530e33335cb IF balance = 513.234;





#create statement(
#    account_id UUID,
#    merchant_id UUID,
#    debit double,
#    credit double,
#    transaction_id UUID)


Queries:
    1. User will create account
    2. User will login to his account
    3. User will transfer amount to other recipient (Debit)
        Transaction will be recorded,
            debit amount from other account:
                balance of the other account should reduce by the amount transferred
            amount will be credited to user's account.
                balance of the customer's account will be increased by the amount transfered
    4. Amount will be credited to user's account (Credit)
        Transaction will be recorded,
            sender will be other_account_id
            recipient will be user_account_id
            amount will be debited from user's account
            amount will be credited to other account
            balance of the cusotmer's account should be increased by the amount credited
            balance of the sender's account should be decreased bt the amount transfered to user's account

  def transaction(sender_id, reciver_id, amount) {
    validate sender_id,
    validate recipient_id,
    validate sender's balance,
    performa transaction

  }


  transaction :
        step 1: balance of sender's account should be decreased by sent ammount ,
                update account set balance = balance - ammount , last_updated = timestamp(now()) where account_id = senders_id;
        step 2: balance of recipient's account should be increased by the ammount.
                update account set balance = balalnce + amount, last_updated = timestamp(now) where account_id = recipient_id
        step 3: transaction table should be updated with transaction information
                    transaction_id UUID, sender_id UUID, recipient_id UUID, date timestamp, ammount double)
                insert into transaction(transaction_id,  sender_id, recipient_id, date, ammount ) values(now(), sender_id, recipient_id, timestamp(now()), ammount)




INSERT INTO account (user_id , id , balance , creation_date , type ) VALUES ( now(),now(), 234234.0,toTimestamp(now()),'savings');

user_id,
type,
balance


