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
