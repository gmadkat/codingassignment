package category;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

 
//import com.wordnik.swagger.annotations.Api;
//import com.wordnik.swagger.annotations.ApiOperation;
//import com.wordnik.swagger.annotations.ApiResponse;
//import com.wordnik.swagger.annotations.ApiResponses;
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

@RestController
public class CategoryController {

    private final AtomicLong counter = new AtomicLong();
    private ArrayList<Category> catlist;
    private Categories[] catlist1;

    public CategoryController() {
       catlist  = new ArrayList<Category>();
       catlist1 = new Categories[10];
       catlist.add(new Category(counter.incrementAndGet(), "ANIMAL"));
       catlist.add(new Category(counter.incrementAndGet(), "PERSON"));
       catlist.add(new Category(counter.incrementAndGet(), "COMPUTER"));
       catlist.add(new Category(counter.incrementAndGet(), "PLACE"));
       catlist.add(new Category(counter.incrementAndGet(), "OTHER"));
    }
    
    boolean notExists(Categories[] retlist1, Categories cat) {
        
        for (int i = 0; i < retlist1.length; i++) {
            if (cat.getCategoryName().equals(retlist1[i].getCategoryName() )
                && cat.getSubCategoryName().equals(retlist1[i].getSubCategoryName()))
                return false;
        }
        return true;
   }

    public boolean isValidCategory(String cat) {
        if (catlist.contains(cat))
           return true;
        return false;
    }
 
    @RequestMapping(value="/category/clean",method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "catslist", value = "category list", required = true, dataType = "array", paramType = "query")
      })
    @ApiOperation(value = "GetCategories", nickname= "Fetch List of Categories and clean them and return cleaned list to user.")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Categories.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    public Categories[] categoryclean(@RequestBody Categories[] catslist)
    {
         System.out.println("catslist " + catslist.length);
         Categories[] retlist = new Categories[catslist.length];
         int j = 0;
         for (int i = 0; i < catslist.length; i++) {
            // check if the category is valid and that there is not an existing
            // pair already in results 

//            if (isValidCategory(catslist[i].getCategoryName()) 
//                   && notExists(retlist, catslist[i])) {
                retlist[j++] = catslist[i];
//            }
         }
         return retlist;
    }

/*
    @RequestMapping(value = "/category/clean", method=RequestMethod.POST)
    @ResponseBody
    public String[] categoryclean(@ModelAttribute("catslist") String[] catslist) {
         System.out.println("catslist " + catslist.length);
        for (int i = 0; i < catslist.length; i++)
         System.out.println("catslist " + catslist[i]);
        ArrayList<Categories> catlist  = new ArrayList<Categories>();
        return catslist; //todo fix
        //return catlist;
    }
*/

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
        for (int i = 0; i < catlist.size(); i++)
         System.out.println("catlist " + catlist.get(i));
        return catlist;
    }

    // method that adds a category the valid category list
    @ApiOperation(value = "addCategory", nickname = "addCategory")
    @RequestMapping(method = RequestMethod.POST, path="/category/add", produces = "application/json")
    @ApiImplicitParams({
       @ApiImplicitParam(name = "name", value = "Category name", required = false, dataType = "string", paramType = "query", defaultValue="NONE")
      })
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = ArrayList.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    public Category categoryadd(@RequestParam(value="name", defaultValue="category1") String name) {
        System.out.println("catadd " + name);
        for (int i = 0; i < catlist.size(); i++)
         System.out.println("catlist " + catlist.get(i).getCategoryName());
        Category cat =  new Category(counter.incrementAndGet(), name); 
        catlist.add(cat);
        for (int i = 0; i < catlist.size(); i++)
         System.out.println("catlist " + catlist.get(i).getCategoryName());
        return cat;
    }

    // method that deletes a category from the valid category list
    @ApiOperation(value = "deleteCategory", nickname = "deleteCategory")
    @RequestMapping(method = RequestMethod.POST, path="/category/delete", produces = "application/json")
    @ApiImplicitParams({
       @ApiImplicitParam(name = "name", value = "Category name", required = false, dataType = "string", paramType = "query", defaultValue="NONE")
      })
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = ArrayList.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    public boolean categorydelete(@RequestParam(value="name") String name) {
        for (int i = 0; i < catlist.size(); i++)
         System.out.println("catlist " + catlist.get(i).getCategoryName());
        Category cat =  new Category(counter.incrementAndGet(), name); 
        boolean b = catlist.contains(cat);
        int index1 = catlist.indexOf(cat);
        catlist.remove(index1);
        for (int i = 0; i < catlist.size(); i++)
         System.out.println("catlist " + catlist.get(i).getCategoryName());
        return b;
    }

}
