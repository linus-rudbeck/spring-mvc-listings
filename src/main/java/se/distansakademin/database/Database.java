package se.distansakademin.database;
import se.distansakademin.models.Listing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;


public class Database {

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    protected Connection conn;

    public boolean isConnected = false;

    public Database() {
        setDevCredentials();

        try {
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            isConnected = (conn != null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDevCredentials(){
        dbUrl = "jdbc:mysql://localhost/listings_db";
        dbUsername = "root";
        dbPassword = "";
    }

    public void setLiveCredentials(){
        dbUrl = "NOT_IN_USE";
        dbUsername = "NOT_IN_USE";
        dbPassword = "NOT_IN_USE";
    }

    public boolean insertListing(Listing listing){
        String query = "INSERT INTO listings (title, description, image_url) VALUES (?, ?, ?);";

        boolean success = false;

        try{
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, listing.getTitle());
            stmt.setString(2, listing.getDescription());
            stmt.setString(3, listing.getImageUrl());

            success = stmt.execute();

            stmt.close();


        }catch(SQLException e){
            e.printStackTrace();
        }

        return success;
    }

    public List<Listing> getListings(){
        List<Listing> listings = new ArrayList<>();

        try{
            String query = "SELECT * FROM listings;";

            Statement stmt = conn.createStatement();

            ResultSet result = stmt.executeQuery(query);

            while (result.next()){
                String title = result.getString("title");
                String description = result.getString("description");
                String imageUrl = result.getString("image_url");
                int id = result.getInt("listing_id");

                Listing listing = new Listing(title, description, imageUrl, id);

                listings.add(listing);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return listings;
    }

    public Listing getListingById(int id){
        Listing listing = null;

        try{
            String query = "SELECT * FROM listings WHERE listing_id = ?;";

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            stmt.execute();

            ResultSet result = stmt.getResultSet();

            if (result.next()){
                String title = result.getString("title");
                String description = result.getString("description");
                String imageUrl = result.getString("image_url");

                listing = new Listing(title, description, imageUrl, id);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return listing;
    }

    public boolean updateListing(int id, Listing listing){
        String query = "UPDATE listings SET title = ?, description = ?, image_url = ? WHERE listing_id = ?;";

        boolean success = false;

        try{
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, listing.getTitle());
            stmt.setString(2, listing.getDescription());
            stmt.setString(3, listing.getImageUrl());
            stmt.setInt(4, id);

            success = stmt.execute();

            stmt.close();


        }catch(SQLException e){
            e.printStackTrace();
        }

        return success;
    }

    public boolean deleteListingById(int id){
        String query = "DELETE FROM listings WHERE listing_id = ?;";

        boolean success = false;

        try{
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            success = stmt.execute();

            stmt.close();


        }catch(SQLException e){
            e.printStackTrace();
        }

        return success;
    }
}
