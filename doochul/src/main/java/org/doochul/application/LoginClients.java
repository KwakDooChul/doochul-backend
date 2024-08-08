package org.doochul.application;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.doochul.application.client.LoginClient;
import org.doochul.domain.oauth.SocialType;
import org.doochul.ui.dto.UserInfo;

public class LoginClients {

    private final Map<SocialType, LoginClient> clients;

    public LoginClients(final Set<LoginClient> clients) {
        final EnumMap<SocialType, LoginClient> mapping = new EnumMap<>(SocialType.class);
        clients.forEach(client -> mapping.put(client.getSocialType(), client));
        this.clients = mapping;
    }

    public UserInfo findUserInfo(final SocialType socialType, final String code) {
        final LoginClient client = getClient(socialType);
        final String accessToken = client.requestToken(code);
        return client.findUserInfo(accessToken);
    }

    private LoginClient getClient(final SocialType socialType) {
        return Optional.ofNullable(clients.get(socialType))
                .orElseThrow(() -> new IllegalArgumentException("해당 OAuth2 제공자는 지원되지 않습니다."));
    }
}
