package category;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author Gowri Visweswaran
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class CategoryTest {

    private MockMvc mockMvc;

    private Categories categories;
    private Category category;


    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CategoryController()).build();
        this.categories = new Categories("PEOPLE", "subcat1");
        this.category = new Category(111, "PEOPLE");
    }

    @Test
    public void validCategory() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/category/list").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
	       .andExpect(content().string(equalTo("[{\"id\":1,\"categoryName\":\"ANIMAL\",\"count\":0},{\"id\":2,\"categoryName\":\"PERSON\",\"count\":0},{\"id\":3,\"categoryName\":\"COMPUTER\",\"count\":0},{\"id\":4,\"categoryName\":\"PLACE\",\"count\":0},{\"id\":5,\"categoryName\":\"OTHER\",\"count\":0}]")));
    }
}
