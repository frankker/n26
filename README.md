# N26 Coding Challenge

- Project is buildable with Maven.

- Transactions with time discrepancies are accepted. 
  Both transactions with timestamps in the past or in the future are recorded.  

- Data is stored in memory transiently.

- ArrayList is used to store the transaction objects.

- Thread safe with concurrent requests has been taken into account by :
    1.) Synchronizing the arraylist with Collections.synchronizedList(new ArrayList()) 
    2.) Manually synchronize on the returned list whenever iteration is performed

- The amortized time complexity for all functions is O(1).

-----------------------------------------------------------------------------------------------------------------------
Special notes:

1.) As it is stated in the question that the data should be stored in-memory without using database and there is no request to clean up old data, I believe the data should be kept in-memory without any house-keeping. In that case, the stored data can be treated as event source as well. Of course, this shouldn't be the case in real-life situation, data stored in-memory should be house-kept from time to time. Unless there is an indication in the question that the data will have to be cleaned up from time to time, the memory will of course grow continuously. The implementation of the solution is specially built based on the requirements of the coding challenge question. 

2.) Both the complexity of getStatistics() and addTransaction is in fact O(1). 
Because the question mentioned that the system should be able to handle time discrepancy. For example, accept a 'special' transaction with timestamp earlier than any of the transactions stored in memory. Of course O(n) will happen if this rare situation happens, but technically it will not happened unless there is some issue for the transaction API call, eg wrong timestamp stated in the POST /transaction body. With the algorithm, the API is able to handle very rare edge cases

3.) The code is broken down into 'Controller', 'Service', and 'Model' in a modular way.
Controller : Handle HTTP requests and responses
Service : Handle business logic
Model : Data structure
I agree that Interface implementation can be skipped in my code to make it more straight forward. But it is used with a reason, it makes service dependency injection to be more flexible if implementation is changed in future.
The code is straight forward and easy to follow actually.

4.) I agree that response entities can be included in the 'Controller' instead in the 'Service' layer. After some thoughts, I decided to include it in the 'Service' instead of the 'Controller' layer, because I think business logic should be handled in 'Service' layer. However, it is still debatable.

