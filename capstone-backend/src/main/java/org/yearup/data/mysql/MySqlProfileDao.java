package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.models.Profile;
import org.yearup.data.ProfileDao;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao
{
    @Autowired
    public MySqlProfileDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public Profile create(Profile profile)
    {
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFirstName());
            ps.setString(3, profile.getLastName());
            ps.setString(4, profile.getPhone());
            ps.setString(5, profile.getEmail());
            ps.setString(6, profile.getAddress());
            ps.setString(7, profile.getCity());
            ps.setString(8, profile.getState());
            ps.setString(9, profile.getZip());

            ps.executeUpdate();

            return profile;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Profile getById(int userId) {

        Profile p = null;
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM profile WHERE user_id = ?;
                    """);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
               userId = rs.getInt("user_id");
               String firstName = rs.getString("first_name");
               String lastName = rs.getString("last_name");
               String phone = rs.getString("phone");
               String email = rs.getString("email");
               String address = rs.getString("address");
               String city = rs.getString("city");
               String state = rs.getString("state");
               String zip = rs.getString("zip");
               p = new Profile();
               p.setUserId(userId);
               p.setFirstName(firstName);
               p.setLastName(lastName);
               p.setPhone(phone);
               p.setEmail(email);
               p.setAddress(address);
               p.setCity(city);
               p.setState(state);
               p.setZip(zip);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    @Override
    public void update(Profile profile) {

    }

}
