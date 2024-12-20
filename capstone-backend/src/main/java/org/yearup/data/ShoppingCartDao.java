package org.yearup.data;

import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.util.ArrayList;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    ShoppingCart addProduct(Product product, int userId, int quantity);
    void updateCart(int userId, int productId, int quantity);
    void clear(int userId);

    // add additional method signatures here
}
