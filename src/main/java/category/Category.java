package category;

public class Category {

    private final long id;
    private final String categoryName;

    public Category(long id, String catname) {
        this.id = id;
        this.categoryName = catname;
    }

    public long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
