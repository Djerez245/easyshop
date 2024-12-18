package org.yearup.data;

import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;

import java.util.ArrayList;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    ArrayList<Product> showCart();
    Product addProduct(int productId);
    Product updateCart(int productId);

    // add additional method signatures here
}
