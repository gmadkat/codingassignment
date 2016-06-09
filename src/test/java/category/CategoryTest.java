package category;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Gowri Visweswaran
 * Purpose: To unit test the category classes
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CategoryTest {

    private MockMvc mockMvc;

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
       MediaType.APPLICATION_JSON.getSubtype(),
       Charset.forName("utf8"));

    protected HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired 
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testValidCategory() throws Exception {
        this.mockMvc.perform(get("/category/list")
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
	       .andExpect(content().string("[{\"id\":1,\"categoryName\":\"ANIMAL\",\"count\":0},{\"id\":2,\"categoryName\":\"PERSON\",\"count\":0},{\"id\":3,\"categoryName\":\"COMPUTER\",\"count\":0},{\"id\":4,\"categoryName\":\"PLACE\",\"count\":0},{\"id\":5,\"categoryName\":\"OTHER\",\"count\":0}]"));
    }

    @Test
    public void testAddCategory() throws Exception {
        this.mockMvc.perform(post("/category/add?name=TESTCAT")
               .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        this.mockMvc.perform(get("/category/list").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
	       .andExpect(content().string("[{\"id\":1,\"categoryName\":\"ANIMAL\",\"count\":0},{\"id\":2,\"categoryName\":\"PERSON\",\"count\":0},{\"id\":3,\"categoryName\":\"COMPUTER\",\"count\":0},{\"id\":4,\"categoryName\":\"PLACE\",\"count\":0},{\"id\":5,\"categoryName\":\"OTHER\",\"count\":0},{\"id\":6,\"categoryName\":\"TESTCAT\",\"count\":0}]"));
    }
    
    @Test
    public void testDeleteCategory() throws Exception {
        this.mockMvc.perform(delete("/category/delete?name=TESTCAT")
               .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
               .andExpect(content().string("true"));
        this.mockMvc.perform(get("/category/list")
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
	       .andExpect(content().string("[{\"id\":1,\"categoryName\":\"ANIMAL\",\"count\":0},{\"id\":2,\"categoryName\":\"PERSON\",\"count\":0},{\"id\":3,\"categoryName\":\"COMPUTER\",\"count\":0},{\"id\":4,\"categoryName\":\"PLACE\",\"count\":0},{\"id\":5,\"categoryName\":\"OTHER\",\"count\":0}]"));
  }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        System.out.println(this.mappingJackson2HttpMessageConverter.getSupportedMediaTypes());
        if (this.mappingJackson2HttpMessageConverter.canWrite(Categories.class, contentType))
          this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


   @Test
    public void testCountCategory() throws Exception {
        ArrayList<Categories> catlist = new ArrayList<Categories>();
        catlist.add(new Categories("ANIMAL", "cat"));
        catlist.add(new Categories("ANIMAL", "dog"));
        catlist.add(new Categories("ANIMAL", "mouse"));
        catlist.add(new Categories("PERSON", "Maya"));
        catlist.add(new Categories("PERSON", "Neel"));
        String catJson = this.json(catlist);
        this.mockMvc.perform(post("/category/countclasslist")
                .content(catJson)
                .contentType(contentType))
                .andExpect(status().isOk())
	        .andExpect(jsonPath("$[0].categoryName").value("ANIMAL"))
	        .andExpect(jsonPath("$[0].count").value(3));
    }


   @Test
    public void testCleanCategoryList1() throws Exception {
        ArrayList<Categories> catlist = new ArrayList<Categories>();
        catlist.add(new Categories("ANIMAL", "bbbbb"));
        catlist.add(new Categories("PERSON", "aaaaa"));
        String catJson = this.json(catlist);
        this.mockMvc.perform(post("/category/cleanclasslist")
                .content(catJson)
                .contentType(contentType))
                .andExpect(status().isOk())
	        .andExpect(jsonPath("$[0].categoryName").value("ANIMAL"));
   }

   @Test
    public void testCleanCategoryList2() throws Exception {
        ArrayList<Categories> catlist = new ArrayList<Categories>();
        catlist.add(new Categories("ANIMAL", "bbbbb"));
        catlist.add(new Categories("ANIMAL", "bbbbb"));
        catlist.add(new Categories("PERSON", "aaaaa"));
        catlist.add(new Categories("PERSON", "aaaaa"));
        catlist.add(new Categories("INVALID", "aaaaa"));
        String catJson = this.json(catlist);
        ArrayList<Categories> catlistclean = new ArrayList<Categories>();
        catlist.add(new Categories("ANIMAL", "bbbbb"));
        catlist.add(new Categories("PERSON", "aaaaa"));
        String catJsonclean = this.json(catlist);
        this.mockMvc.perform(post("/category/cleanclasslist")
                .content(catJson)
                .contentType(contentType))
                .andExpect(status().isOk())
	        .andExpect(jsonPath("$", hasSize(2)));
    }
}
