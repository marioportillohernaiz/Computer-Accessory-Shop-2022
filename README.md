# Object-Oriented-Programming-CW2022

You are to develop a system in Java for a Computer Accessories Shop (CAS) to help the business to
handle many of the shop’s activities in an easy and practical manner. Each product the shop stocks
has a barcode (a unique 6-digit number), brand, colour, connectivity (either wired or wireless), quantity
in stock, original cost and retail price. The shop sells two types of product: keyboards and mice. The
different types of keyboard that they sell are standard, flexible and gaming. The keyboards have the US
layout or the UK layout. The different types of mouse that they sell are standard and gaming. The mice
may have different number of buttons.
The users of the system will need to have unique user ID, unique username, name, address (consisting
of house number, postcode and city). A user can be an admin of the system or a customer. The admin
user has the right to add a new product to the system and view all products with all their attributes.
A customer in addition to the above users’ attributes will need to have a shopping basket. Customers
should be able to add items to their shopping basket and pay for all items in the basket (by choosing a
payment method which can be either PayPal or Credit Card). The Credit Card payments consist of a
6-digit card number and 3-digit security code, and for PayPal payments, an email address is required.
Furthermore, customers need to be able to cancel all items in the shopping basket. A customer should
be also able to view all product attributes except original cost.
The program should include at least the products listed in Appendix B.
Functionality Two types of roles need to be created: "Admin" and "Customer". The system works
with the list of users provided in a file (see Appendix A). Once the user logs into the system by choosing
from the list of available usernames, they should be able to access the below functionalities according
their roles.

• Admin
– View all products with all their attributes sorted ascending by retail price.
– Add a product to the current product list (stock) with these parameters: barcode, brand,
colour, connectivity, quantity in stock, original cost, retail price. If the product being added
is a keyboard then the following parameters need to be included as well: keyboard type,
keyboard layout, and if the product is a mouse then the following parameters need to be
included: mouse type, number of buttons.

• Customer
– View all available products with all their attributes (except original cost) sorted ascending by
retail price.
– Add items to their shopping basket.
– View items in their shopping basket.
– Pay for all items in the basket by choosing from the following payment methods:
* PayPal in which the customers need to enter their PayPal email address.
* Credit Card in which the customers need to enter a 6-digit card number and 3-digit security code.
The pay function must update the stock, make the basket empty, and display a different
message on the screen based on the chosen payment method as follows:
* For PayPal, the message should be "[amount]+ paid using PayPal, and the delivery address is [full address]++".
* For Credit Card, the message should be "amount+ paid using Credit Card, and the delivery address is [full address]++".
+ In the above messages, [amount] must be replaced by the numeric value of the total
payment amount.
++ In the above messages, [full address] must be replaced by the user’s (buyer’s) full address.
– Cancel their shopping basket which empties out the entire content of the basket.
– Search/Filter the list of all available products to purchase by the following fields and view the
results:
* brand, and/or
* mouse’s button quantity

(all product details must be displayed except original cost)

The program should be able to avoid and detect errors. Examples: (i) The customers should be
notified if they want to buy an item that is out of stock. (ii) Products with a same barcode must not
be added into stock more than once.
User Interface You should provide a graphical user interface (GUI) or command-line interface (CLI).
A GUI scores more points than a CLI. A good interface is intuitive, quick and easy to use.


The following Class Diagram represents the diagram for the classes.

![Capture](https://user-images.githubusercontent.com/111706273/235656720-3b68f7b0-e4df-434c-a83b-ae632139aa0e.JPG)

