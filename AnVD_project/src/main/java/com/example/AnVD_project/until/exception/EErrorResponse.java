package com.example.AnVD_project.until.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Getter
public enum EErrorResponse implements AbstractError{
    PROCESS_LOCKED(423, "プロセスはブロックされています", HttpStatus.LOCKED),
    BODY_MISSING_DATA(400, "リクエストボディが不足しています", HttpStatus.BAD_REQUEST),
    THREAD_ERROR(400, "スレッドエラー", HttpStatus.BAD_REQUEST),
    SQL_ERROR(400, "SQLエラー", HttpStatus.BAD_REQUEST),
    DUPLICATE_CODE_ERROR(400, "データが重複しています", HttpStatus.BAD_REQUEST),
    NO_CONTENT(204, "内容なし", HttpStatus.NO_CONTENT),
    INTERNAL_SERVER_ERROR(500, "サーバー内部エラー", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTHENTICATION_ERROR(401, "権限がありません。", HttpStatus.UNAUTHORIZED),
    DATA_NOT_FOUND_ERROR(400, "データがありません", HttpStatus.BAD_REQUEST),
    DATA_INVALID_ERROR(400, "データが無効です。", HttpStatus.BAD_REQUEST),
    DUPLICATE_DATA_ERROR(400, "データが重複しています", HttpStatus.BAD_REQUEST),
    PRODUCT_DUPLICATE_ERROR(400, "選択された行の商品コードが重複しています！", HttpStatus.BAD_REQUEST),
    PRODUCT_DUMMY_CODE_INVALID(400, "商品コードが既に存在しています。", HttpStatus.BAD_REQUEST),
    GROUP_DUMMY_CODE_FORMAT_INVALID(400, "グループコードの形式が正しくありません。", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND_ERROR(400, "商品が見つかりません", HttpStatus.BAD_REQUEST),
    PRODUCT_DUMMY_CODE_REACHED_MAX(400, "商品コードの最大数に達しました！", HttpStatus.BAD_REQUEST),
    GROUP_DUMMY_CODE_REACHED_MAX(400, "グループコードの最大数に達しました！", HttpStatus.BAD_REQUEST),
    PRODUCT_GROUP_DUMMY_CODE_REACHED_MAX(400, "商品グループコードの最大数に達しました！", HttpStatus.BAD_REQUEST),
    GROUP_DUPLICATE_ERROR(400, "商品グループコードが重複しています", HttpStatus.BAD_REQUEST),
    INGREDIENT_CODE_PRODUCT_DUPLICATE_ERROR(400, "商品を編集するときに　原料コードは　重複された!", HttpStatus.BAD_REQUEST),
    TEMPLATE_NOT_FOUND_ERROR(400, "テンプレートファイルが見つかりません", HttpStatus.BAD_REQUEST),
    ERROR_WHEN_GENERATING_PDF(400, "PDFを生成する際にエラーが発生しました", HttpStatus.BAD_REQUEST),
    VALIDATION_PICKING_GROUP_ERROR(400, "該当原料コードが含まれていないため、登録できません。", HttpStatus.BAD_REQUEST),
    VALIDATION_GROUP_ERROR(400, "このグループには、現在の原料を使用していない商品がございます。", HttpStatus.BAD_REQUEST),
    VALIDATION_GROUP_PICKING_ERROR(400, "このグループにはすでに明細が存在するため、原料を変更できません。", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    EErrorResponse(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public Long getEpochTime() {
        return epochTime;
    }

    @Override
    public OffsetDateTime getDateTime() {
        return dateTime;
    }
}
