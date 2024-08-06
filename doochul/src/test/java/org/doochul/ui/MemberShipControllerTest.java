package org.doochul.ui;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.doochul.application.MemberShipService;
import org.doochul.domain.membership.MemberShip;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.doochul.domain.oauth.token.Jwt;
import org.doochul.domain.product.Product;
import org.doochul.domain.product.ProductType;
import org.doochul.domain.user.User;
import org.doochul.support.JwtSupporter;
import org.doochul.ui.dto.MemberShipReadResponse;
import org.doochul.ui.dto.ProductCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberShipController.class)
class MemberShipControllerTest {

    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberShipService memberShipService;

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("멤버쉽을 저장하면 201을 반환한다.")
    @Test
    void createMemberShip() throws Exception {
        //given
        final Long userId = 1L;
        final Long productId = 1L;
        final Jwt accessToken = JwtSupporter.generateToken(userId);

        given(jwtProvider.getPayload(accessToken.token())).willReturn(userId);
        given(memberShipService.createMemberShip(userId, productId)).willReturn(1L);

        //when & then
        mockMvc.perform(
                        post("/memberShip/" + productId)
                                .header(AUTHORIZATION, "Bearer " + accessToken.token())
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/memberShips/" + productId));
    }

    @DisplayName("멤버쉽을 조회하면 200을 반환한다.")
    @Test
    void readMemberShip() throws Exception {
        //given
        final Long userId = 1L;
        final Long productId = 1L;
        final Jwt accessToken = JwtSupporter.generateToken(userId);
        final String userName = "테스트 유저 1";
        final String teacherName = "테스트 선생님 1";
        final String productName = "롤 강의 1";
        final String type = ProductType.LOL.name();
        final Integer count = 10;
        final MemberShipReadResponse readResponse = new MemberShipReadResponse(userName, teacherName, productName,
                type, count);

        given(jwtProvider.getPayload(accessToken.token())).willReturn(userId);
        given(memberShipService.findMemberShip(userId, productId)).willReturn(readResponse);

        //when & then
        mockMvc.perform(
                        get("/memberShip/" + productId)
                                .header(AUTHORIZATION, "Bearer " + accessToken.token())
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(readResponse))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(userName))
                .andExpect(jsonPath("$.teacherName").value(teacherName))
                .andExpect(jsonPath("$.productName").value(productName))
                .andExpect(jsonPath("$.productType").value(type))
                .andExpect(jsonPath("$.count").value(count));
    }

    @DisplayName("멤버쉽을 전체 조회하면 200을 반환한다.")
    @Test
    void readMemberShips() throws Exception {
        //given
        final Long userId = 1L;
        final Long socialId = 3411L;
        final String socialType = "kakao";
        final String name = "카카오 유저 1";
        final User user = User.of(userId, socialId, socialType, name);

        final Long productId = 1L;
        final String productName = "안녕";
        final ProductType type = ProductType.LOL;
        final Integer count = 10;

        final String productName2 = "안녕2";
        final ProductType type2 = ProductType.TFT;
        final Integer count2 = 20;

        final ProductCreateRequest productCreateRequest = new ProductCreateRequest(productName, type, count);
        final ProductCreateRequest productCreateRequest2 = new ProductCreateRequest(productName2, type2, count2);

        final Product product = Product.of(productId, user, productCreateRequest);
        final Product product2 = Product.of(productId, user, productCreateRequest2);

        final MemberShip memberShip = MemberShip.of(1L, user, product, product.getCount());
        final MemberShip memberShip2 = MemberShip.of(1L, user, product2, product2.getCount());

        final List<MemberShip> memberShips = List.of(memberShip, memberShip2);

        final Jwt accessToken = JwtSupporter.generateToken(userId);

        given(jwtProvider.getPayload(accessToken.token())).willReturn(userId);
        given(memberShipService.findMemberShips(userId)).willReturn(MemberShipReadResponse.from(memberShips));

                //when & then
                mockMvc.perform(
                                get("/memberShips")
                                        .header(AUTHORIZATION, "Bearer " + accessToken.token())
                                        .contentType(APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].userName").value(name))
                        .andExpect(jsonPath("$[0].teacherName").value(name))
                        .andExpect(jsonPath("$[0].productName").value(productName))
                        .andExpect(jsonPath("$[0].productType").value(type.name()))
                        .andExpect(jsonPath("$[0].count").value(count))
                        .andExpect(jsonPath("$[1].userName").value(name))
                        .andExpect(jsonPath("$[1].teacherName").value(name))
                        .andExpect(jsonPath("$[1].productName").value(productName2))
                        .andExpect(jsonPath("$[1].productType").value(type2.name()))
                        .andExpect(jsonPath("$[1].count").value(count2));
    }
}
