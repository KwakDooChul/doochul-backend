package org.doochul.ui;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.doochul.application.ProductService;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.doochul.domain.oauth.token.Token;
import org.doochul.domain.product.ProductType;
import org.doochul.support.JwtSupporter;
import org.doochul.ui.dto.ProductRegisterRequest;
import org.doochul.ui.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtProvider jwtProvider;

    @DisplayName("Product를 정상적으로 저장했을때 201을 반환한다.")
    @Test
    void addProduct() throws Exception {
        //given
        final Long userId = 1L;
        final Long productId = 1L;
        final Token accessToken = JwtSupporter.generateToken(userId);
        final ProductRegisterRequest productRegisterRequest = new ProductRegisterRequest("안녕", ProductType.LOL, 10);

        given(jwtProvider.getPayload(accessToken.getToken())).willReturn(userId);
        given(productService.save(userId, productRegisterRequest))
                .willReturn(productId);

        //when
        //then
        mockMvc.perform(
                        post("/product")
                                .header(AUTHORIZATION, "Bearer " + accessToken.getToken())
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productRegisterRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/product/" + productId));
    }

    @DisplayName("Product가 정상적으로 조회되면 200을 반환한다.")
    @Test
    void readProduct() throws Exception {
        //given
        final Long userId = 1L;
        final Long productId = 1L;
        final String name = "테스트 PT 명";
        final ProductType type = ProductType.LOL;
        final String teacher = "테스트 PT썜 이름";
        final int count = 10;

        final Token accessToken = JwtSupporter.generateToken(userId);
        final ProductResponse productResponse = new ProductResponse(productId, name, type, teacher, count);

        given(jwtProvider.getPayload(accessToken.getToken())).willReturn(userId);
        given(productService.findProduct(productId)).willReturn(productResponse);

        //when
        //then
        mockMvc.perform(
                        get("/product/" + productId)
                                .header(AUTHORIZATION, "Bearer " + accessToken.getToken())
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.type").value(type.toString()))
                .andExpect(jsonPath("$.teacher").value(teacher))
                .andExpect(jsonPath("$.count").value(count));
    }

    @DisplayName("Product들이 정상적으로 조회되면 200을 반환한다.")
    @Test
    void readProducts() throws Exception {
        // given
        final Long userId = 1L;
        final Long productId1 = 1L;
        final Long productId2 = 2L;
        final String name1 = "테스트 PT 명1";
        final String name2 = "테스트 PT 명2";
        final ProductType type1 = ProductType.LOL;
        final ProductType type2 = ProductType.TFT;
        final String teacher1 = "테스트 PT썜 이름1";
        final String teacher2 = "테스트 PT썜 이름2";
        final int count1 = 10;
        final int count2 = 20;

        final Token accessToken = JwtSupporter.generateToken(userId);
        final ProductResponse productResponse1 = new ProductResponse(productId1, name1, type1, teacher1, count1);
        final ProductResponse productResponse2 = new ProductResponse(productId2, name2, type2, teacher2, count2);
        final List<ProductResponse> products = List.of(productResponse1, productResponse2);

        given(jwtProvider.getPayload(accessToken.getToken())).willReturn(userId);
        given(productService.findProducts()).willReturn(products);

        // when
        // then
        mockMvc.perform(
                        get("/products")
                                .header(AUTHORIZATION, "Bearer " + accessToken.getToken())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(productId1))
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].type").value(type1.toString()))
                .andExpect(jsonPath("$[0].teacher").value(teacher1))
                .andExpect(jsonPath("$[0].count").value(count1))
                .andExpect(jsonPath("$[1].id").value(productId2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].type").value(type2.toString()))
                .andExpect(jsonPath("$[1].teacher").value(teacher2))
                .andExpect(jsonPath("$[1].count").value(count2));
    }

    @DisplayName("Product를 삭제하면 204를 반환한다.")
    @Test
    void deleteProduct() throws Exception {
        // given
        final Long userId = 1L;
        final Long productId = 1L;

        final Token accessToken = JwtSupporter.generateToken(userId);

        given(jwtProvider.getPayload(accessToken.getToken())).willReturn(userId);
        doNothing().when(productService).deleteProduct(productId);

        // when
        // then
        mockMvc.perform(
                        delete("/product/{productId}", productId)
                                .header(AUTHORIZATION, "Bearer " + accessToken.getToken())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }
}
