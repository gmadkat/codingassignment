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

    @RequestMapping(value="/category/clean",method=RequestMethod.POST)
    public Categories[] categoryclean(@RequestBody Categories[] catslist)
    {
         System.out.println("catslist " + catslist.length);
        return catslist;
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
    @RequestMapping("/category/list")
    public ArrayList<Category> categorylist() {
        for (int i = 0; i < catlist.size(); i++)
         System.out.println("catlist " + catlist.get(i));
        return catlist;
    }

    // method that adds a category the valid category list
    @RequestMapping("/category/add")
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
    @RequestMapping(value = "/category/delete", method=RequestMethod.POST)
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
