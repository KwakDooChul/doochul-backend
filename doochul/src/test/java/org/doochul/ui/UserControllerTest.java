package org.doochul.ui;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.doochul.application.UserService;
import org.doochul.domain.oauth.jwt.JwtProvider;
import org.doochul.domain.oauth.token.Jwt;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.support.JwtSupporter;
import org.doochul.ui.dto.UserInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private JwtProvider jwtProvider;

    @DisplayName("유저의 정보를 조회한다.")
    @Test
    void readUserInfo() throws Exception {
        //given
        final Long userId = 1L;
        final Long socialId = 1L;
        final String socialType = "kakao";
        final String name = "테스트 유저 1";
        final User user = User.of(userId, socialId, socialType, name);
        final Jwt accessToken = JwtSupporter.generateToken(userId);

        given(jwtProvider.getPayload(accessToken.token())).willReturn(userId);

        userRepository.save(user);

        //when
        UserInfoResponse userInfo = userService.findUserInfo(userId);

        //then
        mockMvc.perform(
                get("/user")
                        .header(AUTHORIZATION, "Bearer " + accessToken.token())
                        .contentType(MediaType.APPLICATION_JSON)
                 )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userInfo.name()))
                .andExpect(jsonPath("$.socialId").value(userInfo.socialId()))
                .andExpect(jsonPath("$.socialType").value(userInfo.socialType()))
                .andExpect(jsonPath("$.identity").value(userInfo.identity().name()));
    }
}
