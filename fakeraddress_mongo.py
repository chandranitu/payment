# pip install pymongo faker
# pip install faker

import time
import signal
import sys
from faker import Faker
from pymongo import MongoClient
from pymongo.errors import ConnectionFailure

# Initialize Faker and MongoDB client
fake = Faker()
client = MongoClient('localhost', 27017)  # Adjust the host and port as needed
db = client['test']  # Adjust the database name as needed
collection = db['credit_cards']  # Adjust the collection name as needed

# Function to handle interrupt signal
def signal_handler(sig, frame):
    print('Interrupt received, stopping the script...')
    sys.exit(0)

# Register the interrupt signal handler
signal.signal(signal.SIGINT, signal_handler)

# Function to generate fake data
#card_type could be maestro, mastercard, visa16, visa13, visa19, amex, discover, diners, jcb15, jcb16.
def generate_fake_data():
    return {
        'cardNumber': fake.credit_card_number(card_type='amex'),
        'cardHolder': fake.name(),
        'cvv': fake.address(),
        'expiryDate': fake.date_time(),
        'balance': fake.text(),
        'updatedAt': fake.date_time()
    }

# Main loop to generate and insert data
while True:
    try:
        # Generate fake data
        data = generate_fake_data()
        print(f"Inserting data: {data}")
        
        # Insert the data into MongoDB
        collection.insert_one(data)
        
        # Sleep for 5 seconds
        time.sleep(5)
    except ConnectionFailure:
        print("Failed to connect to MongoDB, please check your connection.")
        sys.exit(1)
    except Exception as e:
        print(f"An error occurred: {e}")



