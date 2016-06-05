package category;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;

//@Entity
public class Categories {

 //   @GeneratedValue
    private String categoryName;
 //   @GeneratedValue
    private String subCategoryName;

    public Categories() {
        this.categoryName = "foo";
        this.subCategoryName = "bar";
    }
    public Categories(String catname, String subcatname) {
        this.categoryName = catname;
        this.subCategoryName = subcatname;
    }

    public void setCategoryName(String c) {
        this.categoryName = c;
    }
    public void setSubCategoryName(String c) {
        this.subCategoryName = c;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The name of the category", required = true)
    public String getCategoryName() {
        return categoryName;
    }
    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The name of the subcategory", required = true)
    public String getSubCategoryName() {
        return subCategoryName;
    }
}
