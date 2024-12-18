package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class mySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public mySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {

        ShoppingCart s = null;

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM shopping_cart WHERE user_id = ?;
                    """);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){

                userId = rs.getInt("user_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                s = new ShoppingCart();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return s;
    }

    @Override
    public ArrayList<Product> showCart() {
        ArrayList<ShoppingCart> results = new ArrayList<>();

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM products
                    JOIN shopping_cart
                    ON products.product_id = shopping_cart.product_id;
                    """);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int category_id = rs.getInt("category_id");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                ShoppingCart s = new ShoppingCart();
                results.add(s);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Product addProduct(int productId) {
        return null;
    }

    @Override
    public Product updateCart(int productId) {
        return null;
    }

}
