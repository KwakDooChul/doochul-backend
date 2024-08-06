package org.doochul.common.exception;

public enum ErrorCodes {

    // 토큰 관련
    TOKEN_NULL_EXCEPTION("토큰이 비어있으면 안됩니다.", 1000L),
    TOKEN_BEARER_TYPE_EXCEPTION("토큰에 BEARER이 빠져있습니다.", 1001L),

    // 유저 관련
    USER_NOT_FOUND("올바르지 않은 유저 입니다.", 2000L),

    // PT 관련
    PRODUCT_NOT_FOUND("올바르지 않은 PT 입니다.", 3000L),
    PRODUCT_VERIFY_OWNER("본인의 PT가 아닙니다.", 3001L),

    // ProductType 관련
    PRODUCT_TYPE_NOT_FOUND("올바르지 않은 타입입니다.", 4000L),
   
    // 멤버쉽 관련
    MEMBERSHIP_NOT_FOUND("올바르지 않은 멤버쉽입니다.", 4000L),

    BAD_REQUEST("BAD_REQUEST", 9404L),
    BAD_REQUEST_JSON_PARSE_ERROR("[BAD_REQUEST] JSON_PARSE_ERROR - 올바른 JSON 형식이 아님", 9405L),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", 9999L);

    public final String message;
    public final Long code;

    ErrorCodes(String message, Long code) {
        this.message = message;
        this.code = code;
    }
}
