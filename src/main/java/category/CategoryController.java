package category;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CategoryController {

    private final AtomicLong counter = new AtomicLong();
    private ArrayList<Category> categoryList;
    private int numreturned;

public static String asJsonString(final Object obj) {
    try {
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(obj);
        return jsonContent;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}  
    public CategoryController() {
       categoryList  = new ArrayList<Category>();
       numreturned = 0;
       categoryList.add(new Category(counter.incrementAndGet(), "ANIMAL"));
       categoryList.add(new Category(counter.incrementAndGet(), "PERSON"));
       categoryList.add(new Category(counter.incrementAndGet(), "COMPUTER"));
       categoryList.add(new Category(counter.incrementAndGet(), "PLACE"));
       categoryList.add(new Category(counter.incrementAndGet(), "OTHER"));
    }
    
    boolean isExists(ArrayList<Categories> retlist1, Categories cat) {
        
        boolean found = false;
        for (int i = 0; i < retlist1.size(); i++) {
            if (cat.getCategoryName().equals(retlist1.get(i).getCategoryName() )
                && cat.getSubCategoryName().equals(retlist1.get(i).getSubCategoryName())) {
                found = true;
                break;
            }
        }
        return found;
   }

    public boolean isValidCategory(String cat) {
        for(int i = 0; i < categoryList.size(); i++){
          if (categoryList.get(i).getCategoryName().equals(cat))
          {   
             return true;
          }
        }
        return false;
    }

    public ArrayList<Categories> categoryCleanHelper(ArrayList<Categories> catslist)
    {
         ArrayList<Categories> retlist = new ArrayList<Categories>();

         int j = 0;
         numreturned = 0;
         for (int i = 0; i < catslist.size(); i++) {
            // check if the category is valid and that there is not an existing
            // pair already in results 

           if (isValidCategory(catslist.get(i).getCategoryName()) 
                   && (isExists(retlist, catslist.get(i)) == false)) {
                retlist.add(catslist.get(i));
                numreturned += 1;
            }
         }
         return retlist;
    }
 
    public ArrayList<Category> categoryCountHelper(ArrayList<Categories> catslist) {

         ArrayList<Categories> tmp = new ArrayList<Categories>();
         ArrayList<Category> tmp1 = new ArrayList<Category>();
         tmp = categoryCleanHelper(catslist);
         for (int i = 0; i < tmp.size(); i++) {
            boolean found = false;
            for (int j = 0; j < tmp1.size(); j++) {
              if (tmp1.get(j).getCategoryName().equals(tmp.get(i).getCategoryName()))  {
                 found = true;
                 tmp1.get(j).setCount(tmp1.get(j).getCount() + 1);
                 break;
              }
            }
            if (found == false) {
               Category x = new Category(counter.incrementAndGet(), tmp.get(i).getCategoryName());
               x.setCount(x.getCount() + 1);
               tmp1.add(x);
            }
         }
         Collections.sort(tmp1, Collections.reverseOrder());
         return tmp1;
    }


    @RequestMapping(value="/category/cleanclasslist",method=RequestMethod.POST, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "catslist", value = "category list", required = true, dataType = "ArrayList<Categories>", paramType = "body")
      })
    @ApiOperation(value = "CleanCategories", nickname= "Fetch List of Categories and clean them and return cleaned list to user.")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = ArrayList.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    public ArrayList<Categories> categoryCleanClass(@RequestBody ArrayList<Categories> catslist)
    {
         ArrayList<Categories> retlist = new ArrayList<Categories>();
         retlist = categoryCleanHelper(catslist);
         return retlist;
    }


    @RequestMapping(value="/category/clean",method=RequestMethod.POST, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "catlist1", value = "category list", required = true, dataType = "ArrayList",  paramType = "body")
      })
    @ApiOperation(value = "CleanCategoriesStrings", nickname= "Fetch List of Categories as a colon delimited string and clean them and return cleaned list to user.")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = ArrayList.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
     public ArrayList<Categories> categoryClean(@RequestBody ArrayList<String> catlist1) {

        ArrayList<Categories> catlist = new ArrayList<Categories>();
        ArrayList<Categories> retlist = new ArrayList<Categories>();
        
        for (int i = 0; i < catlist1.size(); i++) {
           String[] tmp = catlist1.get(i).split(":");
           catlist.add(new Categories(tmp[0], tmp[1]));
        }
        retlist = categoryCleanHelper(catlist);
        return retlist; 
    }

    @RequestMapping(value="/category/countclasslist",method=RequestMethod.POST, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "catlist1", value = "category list", required = true, dataType = "ArrayList", paramType = "body")
      })
    @ApiOperation(value = "CategoryCount", nickname= "Fetch List of Categories and clean them and return count per Category sorted in descending order.")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = ArrayList.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    public ArrayList<Category> categoryCount(@RequestBody ArrayList<Categories> catlist1) {

        ArrayList<Category> reslist = new ArrayList<Category>();
        
        reslist = categoryCountHelper(catlist1);
        return reslist; 
    }


//  example body usage: ["ANIMAL:aaa","PERSON:bob","PERSON:bob","INVALID:cat"]
//  curl -X POST --data '["ANIMAL:aaa","PERSON:bbb","OTHER:ccc","ANIMAL:aaaa","ANIMAL:sssss"]' -H "Content-Type:application/json"  http://localhost:8080/category/clean
    @RequestMapping(value="/category/count",method=RequestMethod.POST, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "catlist1", value = "category list", required = true, dataType = "ArrayList", paramType = "body")
      })
    @ApiOperation(value = "CategoryCount", nickname= "Fetch List of Categories as a colon delimited string and clean them and return count per Category sorted in descending order.", response = ArrayList.class)
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = ArrayList.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    public ArrayList<Category> categoryCountString(@RequestBody ArrayList<String> catlist1) {

        ArrayList<Categories> catlist = new ArrayList<Categories>();
        ArrayList<Category> reslist = new ArrayList<Category>();
        
        for (int i = 0; i < catlist1.size(); i++) {
           String[] tmp = catlist1.get(i).split(":");
           catlist.add(new Categories(tmp[0], tmp[1]));
        }
        reslist = categoryCountHelper(catlist);
        return reslist; 
    }


    // method that lists the valid categories
    @ApiOperation(value = "getCategoryList", nickname = "getCategoryList")
    @RequestMapping(method = RequestMethod.GET, path="/category/list", produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam()
      })
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = ArrayList.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    public ArrayList<Category> categorylist() {
       String[] test = new String[1];
        test[0] = new String("ANIMAL:aaa");
        return categoryList;
    }

    // method that adds a category the valid category list
    @ApiOperation(value = "addCategory", nickname = "addCategory")
    @RequestMapping(method = RequestMethod.POST, path="/category/add", produces = "application/json")
    @ApiImplicitParams({
       @ApiImplicitParam(name = "name", value = "Category name", required = false, dataType = "string", paramType = "query", defaultValue="NONE")
      })
    @ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Success", response = Category.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    public Category categoryadd(@RequestParam(value="name", defaultValue="category1") String name) {
        Category cat =  new Category(counter.incrementAndGet(), name); 
        categoryList.add(cat);
        return cat;
    }

    // method that deletes a category from the valid category list
    @ApiOperation(value = "deleteCategory", nickname = "deleteCategory")
    @RequestMapping(method = RequestMethod.POST, path="/category/delete", produces = "application/json")
    @ApiImplicitParams({
       @ApiImplicitParam(name = "name", value = "Category name", required = false, dataType = "string", paramType = "query", defaultValue="NONE")
      })
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    public boolean categorydelete(@RequestParam(value="name") String name) {
        Category cat =  new Category(counter.incrementAndGet(), name); 
        boolean b = categoryList.contains(cat);
        int index1 = categoryList.indexOf(cat);
        if (index1 >= 0)
           categoryList.remove(index1);
        else b = false;
        return b;
    }
}
