package category;

public class Categories {

    private String categoryName;
    private String subCategoryName;

    public Categories() {
        System.out.println("ctor ");
        this.categoryName = "foo";
        this.subCategoryName = "bar";
    }
    public Categories(String catname, String subcatname) {
        System.out.println("ctor1 " + catname + subcatname);
        this.categoryName = catname;
        this.subCategoryName = subcatname;
    }

    public void setCategoryName(String c) {
        System.out.println("setter = " + c);
        this.categoryName = c;
    }
    public void setSubCategoryName(String c) {
        System.out.println("setter = " + c);
        this.subCategoryName = c;
    }
    public String getCategoryName() {
        System.out.println("getter = " + categoryName);
        return categoryName;
    }
    public String getSubCategoryName() {
        return subCategoryName;
    }
}
