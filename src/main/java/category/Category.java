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

    @Override
    public boolean equals(Object object)
    {
        boolean equals = false;

        if (object != null && object instanceof Category)
        {
            equals = this.categoryName.equals(((Category) object).categoryName);
        }

        return equals;
    }
}
