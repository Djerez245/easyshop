# Easy Shop 

## Overview

---
with this project I started out with a project that was unfinished and had 
a few bugs. The project started out with two projects one for the back end and 
onr for the front end. in this project I had to adjust or create controller classes
and fill int the code for most of the dao methods.

### Catergories and Products

---
with the categories and products I started out with a bunch of empty methods for the dao and the 
controller. I started of by creating the SQL database in MySQL and looking at the table so 
I know what columns I am working with. Then I created prepared statements for
all the queries that I am going to need for my for dao and made sure they worked 
by executing them in MySQL. Then I created the objects to return the methods proper 
return types. Everything I did with category classes I did with my products classes

Here are some examples...

![categoryDaoExample](capstone-backend/images/categoryDAO.png)

![categoryDaoExample](/images/categoryDAO2.png)

With my controller methods I had to make sure that the mapping and authorization was
correct so all my test would pass. I did this by ensuring I used the correct annotations

![controller](/images/categoryController.png)

Some things doing this that I learned that I never thought of and I thought was a great idea
was using the map row method so I can just call that method instead of getting all the data and 
creating a new category every time.

This is the method that I was talking about...

![mapRow](/images/categoryDAOmaprow.png)

## Shopping cart 
With my shopping cart things where a bit different because the classes that already existed
were missing some methods I needed so I had to take a moment and plan out my next moves
I started by creating all the methods that I needed in my interface and then implemented them into 
the dao. Once I did that everything was pretty similar to the category and product classes.
The challenging part of the shopping cart was that I had to create a product object then convert the 
product to a shopping cart object and then add that object to the shopping cart. In order to achieve this
I had to join my shopping cart table with my products table in my SQL query.

here is an example...

![shoppingcart](/images/shoppingcartExample.png)

I also had to make sure that only a user can access the shopping cart so I had
to add the @Preauthorize(hasRole'user') annotation. 

## Profiles 

---
With the these classes I already had the method to add a profile. I had to add the 
the methods to update and delete a user. There was no controller class so I had to create one.

## Bugs 

---

### Bug #1
In the products dao class there was a bug in the SQL query that wasn't handling the maximum
price. I added a line to the query that handled that. I also set the integers for the max price.

### Bug #2
There was a bug that when a user goes to update a product. there would be duplicates.
I looked into the products controller class and I saw that in the update method was calling the 
add method from the dao class instead of the update method.

### Bug #3
There was a bug I noticed in the front-end that when you adjust the price instead of setting maximum
price it was setting the price for minimum price twice so I went into the front-end code and fixed that.
