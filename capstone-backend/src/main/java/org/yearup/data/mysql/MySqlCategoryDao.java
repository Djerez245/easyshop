package org.yearup.data.mysql;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories()
    {
        ArrayList<Category> results = new ArrayList<>();
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM categories;
                    """);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
               Category c = mapRow(rs);
               results.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // get all categories
        return results;
    }

    @Override
    public Category getById(int categoryId)
    {
        Category c = null;
        try(Connection connection = getConnection()){

            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM categories WHERE category_id = ?;
                    """);
            statement.setInt(1, categoryId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                c = mapRow(rs);
            } else {
                System.out.println(categoryId + "not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // get category by id
        return c;
    }


    // TODO: find a way to make this work
    @Override
    public Category create(Category category)
    {

        try(Connection connection = getConnection()){

            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO categories VALUES(null, ?, ?);
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            if (key.next()) {
                int id = key.getInt(1);
                return new Category(id, category.getName(), category.getDescription());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
        try(Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("""
                    UPDATE categories
                    SET name = ? ,description = ?
                    WHERE category_id = ?;
                    """);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, categoryId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int categoryId)
    {
        try(Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("""
                    DELETE FROM categories WHERE category_id = ?;
                    """);

            statement.setInt(1, categoryId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }


}
