This is a java application developed with a Derby DB. The application allows users to simulate the action of booking a flight,
and the booked flight is then logged in the database.

When running the application, a GUI appears on the screen with seven tabs.

The first tabs contains the necessary functionality to add things to the database. The user has the ability to add a customer
name to the list of customers who can book a flight, add a flight, or add an available date for flights. This is done by typing
the requested information in the adjacent text box before clicking the appropriate button to add data.

The second tab handles the booking of flights. The user is asked to select a customer, a flight, and a date from drop down menus.
If there is available seating on that flight, then the user recieves a confirmation that their flight is booked. Otherwise,
the user receives a message indicating that they have been placed on the waitlist for that flight.

The third tab cancels already made bookings. The user is asked to select a customer and date from drop down menus, and then that
customers flight booking for the selected date is removed from the database.

The fourth tabs allows users to drop flights. Selecting a flight from the drop down menu and clicking the button causes the selected
flight to be removed from the database. Anyone who had a seat booked for the deleted flight will be moved to another flight's booking
(or waitlist) for the same day. Customers who were on the waitlist for the deleted flight are simply removed entirely.

The final three tabs give status updates on information in the database. The flight status allows users to select a flight and a date,
and displays the bookings and waitlist for that flight on that date. The waitlist status tab allows the user to select a date and displays
all customers on a waitlist for a flight on that date. Finally, the customer status tab allows the user to select a customer and displays
all booking and waitlist information across any number of days and flights for that customer.
