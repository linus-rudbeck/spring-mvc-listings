package se.distansakademin.models;

public class Listing {
    private int id;
    private String title;
    private String description;
    private String imageUrl;

    public Listing(){

    }

    public Listing(String title, String description, String imageUrl, int id){
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
