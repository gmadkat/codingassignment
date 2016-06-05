package category;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class Category implements Comparable {

    private final long id;
    private final String categoryName;
    private int count;

    public Category() {
        this.id = 0;
        this.count = 0;
        this.categoryName = "";
    }
    public Category(long id, String catname) {
        this.id = id;
        this.count = 0;
        this.categoryName = catname;
    }

    public long getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int ct) {
       this.count = ct;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The name of the user", required = true)
    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public int compareTo(Object o)
    {
       return Integer.compare(this.count, ((Category )o).count);
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

    @Override
    public String toString(){
     String s = "Category name: "+ categoryName+"\ncount: "+count;
     return s;
    }
}
