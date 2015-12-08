package se.kth.eit.trackit.model;

/**
 * Product category model class.
 */
public class ProductCategory {
    private String id, name;

    private int imageId;

    public ProductCategory(String id, String name, int imageId) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
