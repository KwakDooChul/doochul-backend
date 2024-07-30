package org.doochul.common.exception;

public enum ErrorCodes {

    TOKEN_NULL_EXCEPTION("토큰이 비어있으면 안됩니다.", 1000L),
    TOKEN_BEARER_TYPE_EXCEPTION("토큰에 BEARER이 빠져있습니다.", 1001L),

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
