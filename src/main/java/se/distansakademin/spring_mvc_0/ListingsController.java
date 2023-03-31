package se.distansakademin.spring_mvc_0;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.distansakademin.database.Database;
import se.distansakademin.models.Listing;


import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
// @RequestMapping("/listings")
public class ListingsController {


    // GET request for retrieving all listings
    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    // GET request for retrieving all listings
    @GetMapping("/listings")
    public String getAllListings(Model model) {
        Database database = new Database();

        List<Listing> listings = database.getListings();
        model.addAttribute("listings", listings);

        return "listings";
    }

    // GET request for retrieving a single listing by ID
    @GetMapping("/listings/{id}")
    public String getListingById(@PathVariable int id, Model model) {
        Database database = new Database();

        Listing listing = database.getListingById(id);
        model.addAttribute("listing", listing);

        return "listing";
    }

    // GET request for displaying a form to create a new listing
    @GetMapping("/listings/new")
    public String showCreateListingForm(Model model) {
        Listing listing = new Listing();
        model.addAttribute("listing", listing);

        return "create_listing";
    }

    // POST request for creating a new listing
    @PostMapping("/listings/new")
    public String createListing(@ModelAttribute("listing") Listing listing, @RequestParam("listing-image") MultipartFile file) throws IOException {

        String imageUrl = uploadToS3(file);

        listing.setImageUrl(imageUrl);

        Database database = new Database();
        database.insertListing(listing);

        return "redirect:/listings";
    }


    // GET request for displaying a form to update an existing listing
    @GetMapping("/listings/{id}/edit")
    public String showUpdateListingForm(@PathVariable int id, Model model) {
        Database database = new Database();

        Listing listing = database.getListingById(id);
        model.addAttribute("listing", listing);

        return "update_listing";
    }

    // POST request for updating an existing listing
    @PostMapping("/listings/{id}/edit")
    public String updateListing(@PathVariable int id, @ModelAttribute("listing") Listing listing, @RequestParam("listing-image") MultipartFile file) throws IOException {
        String imageUrl = uploadToS3(file);

        listing.setImageUrl(imageUrl);

        Database database = new Database();
        database.updateListing(id, listing);

        return "redirect:/listings";
    }

    // POST request for deleting a listing
    @PostMapping("/listings/{id}/delete")
    public String deleteListing(@PathVariable int id) {
        Database database = new Database();

        database.deleteListingById(id);

        return "redirect:/listings";
    }

    private String uploadToS3(MultipartFile file) throws IOException {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_NORTH_1)
                .build();

        String bucketName = "bucket-5745172304";
        String objectKey = "listings/" + UUID.randomUUID() + ".jpg";

        s3Client.putObject(bucketName, objectKey, file.getInputStream(), new ObjectMetadata());

        s3Client.setObjectAcl(bucketName, objectKey, CannedAccessControlList.PublicRead);

        String imageUrl = s3Client.getUrl(bucketName, objectKey).toString();

        return imageUrl;
    }
}
