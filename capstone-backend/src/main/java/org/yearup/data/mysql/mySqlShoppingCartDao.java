package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class mySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public mySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {

        ShoppingCart s = null;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM shopping_cart
                    JOIN shopping_cart
                    ON products.product_id = shopping_cart.product_id;
                    WHERE user_id = ?;
                    """);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("user_id");
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                int categoryId = rs.getInt("category_id");
                String description = rs.getString("description");
                String color = rs.getString("color");
                String image = rs.getString("image_url");
                int stock = rs.getInt("stock");
                boolean featured = rs.getBoolean("featured");
                int quantity = rs.getInt("quantity");
                Product p = new Product(productId,name,price, categoryId,description,color,stock,featured,image);
                ShoppingCartItem si = new ShoppingCartItem();
                si.setProduct(p);
                s = new ShoppingCart();
                s.add(si);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return s;
    }

    @Override
    public ShoppingCart addProduct(Product product, int userId) {

        ShoppingCart shoppingCart = getByUserId(userId);

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO shopping_cart(user_id, product_id) VALUES(?, ?)
                    """);
            statement.setInt(1, userId);
            statement.setInt(2, product.getProductId());
            statement.executeUpdate();
            ShoppingCartItem item = new ShoppingCartItem();
            shoppingCart.add(item);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return shoppingCart;
    }

    // figure out a way to set these parameters
    @Override
    public void updateCart(int productId) {
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("""
                    UPDATE shopping_cart
                    SET product_id = ?, quantity = ?
                    WHERE user_id = ?
                    """);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void clear(int userId) {

        try(Connection connection = getConnection()){
          PreparedStatement statement = connection.prepareStatement("""
                  DELETE FROM shopping_cart WHERE user_id = ?;
                  """);
          statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
