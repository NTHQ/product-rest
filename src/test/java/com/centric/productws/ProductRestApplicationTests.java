package com.centric.productws;

import com.centric.productws.controller.ProductController;
import com.centric.productws.dao.IProductDao;
import com.centric.productws.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class ProductRestApplicationTests {

    @MockBean
    ProductController productController;
    @Autowired
    private IProductDao productDao;
    @Autowired
    private MockMvc mvc;

    @Test
    public void testFindProductByCategory() throws Exception {
        Product product1 = new Product();
        product1.setId("b6afac37-cf9a-4fd4-8257-f096dbb5d34d");
        product1.setName("Red Shirt");
        product1.setDescription("Red hugo boss shirt");
        product1.setBrand("Hugo Boss");
        List<String> tags1 = new ArrayList<>();
        tags1.add("red");
        tags1.add("shirt");
        tags1.add("slim fit");
        product1.setTags(tags1);
        product1.setCategory("apparel");
        product1.setCreated_at(new Date());

        Product product2 = new Product();
        product2.setId("357cd2c8-6f69-4bea-a6fa-86e40af0d867");
        product2.setName("Blue Shirt");
        product2.setDescription("Blue hugo boss shirt");
        product2.setBrand("Hugo Boss");
        List<String> tags2 = new ArrayList<>();
        tags2.add("blue");
        tags2.add("shirt");
        product2.setTags(tags2);
        product2.setCategory("apparel");
        product2.setCreated_at(new Date());

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        given(productController.findProductByCategory("apparel")).willReturn(products);

        List<Product> result = productController.findProductByCategory("apparel");

        mvc.perform(MockMvcRequestBuilders
                .get("/v1/products/findProductByCategory/")
                .param("category", "apparel")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].category", equalTo("apparel")))
                .andExpect(jsonPath("$[1].category", equalTo("apparel")));
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName("Green Shirt");
        product.setDescription("Green Hugo Boss shirt");
        product.setBrand("Hugo Boss");
        List<String> tags = new ArrayList<>();
        tags.add("green");
        tags.add("shirt");
        product.setTags(tags);
        product.setCategory("apparel");
        Date date = new Date();
        product.setCreated_at(date);

        given(productController.createProduct(product)).willReturn(product);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String requestBody = "{\"name\":\"Green Shirt\",\"description\":\"Green hugo boss shirt\",\"brand\":\"Hugo Boss\",\"tags\":[\"green\",\"shirt\",\"slim fit\"],\"category\":\"apparel\"}";
        mvc.perform(MockMvcRequestBuilders
                .post("/v1/products/createProduct/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
    }
}
