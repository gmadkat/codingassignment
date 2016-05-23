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

    @RequestMapping(value = "/category/clean2", method=RequestMethod.POST)
    @ResponseBody
    public String categoryclean2(HttpServletRequest request) {

        System.out.println("POST: " + request.getParameterMap().size()); // prints POST: 0
        Map m=request.getParameterMap();
        Set s = m.entrySet();
        Iterator it = s.iterator();
 
            while(it.hasNext()){
 
                Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();
 
                String key             = entry.getKey();
                String[] value         = entry.getValue();
        System.out.println("POST: " + key  + " value = " + value[0]);
            }
 
        return "";

    }

    //@ModelAttribute("Categories")
    @RequestMapping(value = "/category/clean1", method=RequestMethod.POST)
    @ResponseBody
    public Categories categoryclean1(@ModelAttribute("catslist") Categories catslist) {
         System.out.println("catslist " + catslist);
        return catslist; //todo fix
    }

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

    @RequestMapping("/category/list")
    public ArrayList<Category> categorylist() {
        for (int i = 0; i < catlist.size(); i++)
         System.out.println("catlist " + catlist.get(i));
        return catlist;
    }

    @RequestMapping("/category/add")
    public Category categoryadd(@RequestParam(value="name", defaultValue="category1") String name) {
        Category cat =  new Category(counter.incrementAndGet(), name); 
        catlist.add(cat);
        return cat;
    }
    @RequestMapping("/category/delete")
    public String categorydelete(@RequestParam(value="name", defaultValue="category1") String name) {
        return name; //todo delete this
    }

}
