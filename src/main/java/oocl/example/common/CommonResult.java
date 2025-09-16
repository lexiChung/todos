package oocl.example.common;

import lombok.Data;

@Data
public class CommonResult<T> {
  public static String CODE_200 = "200";

  private String code;
  private String message;
  private T result;

  public CommonResult() {
  }

  public CommonResult(String code, String message, T result) {
    this.code = code;
    this.message = message;
    this.result = result;
  }

  public static <T> CommonResult<T> ok(T result) {
    return new CommonResult<>(CODE_200, null, result);
  }

  public static <T> CommonResult<T> ok() {
    return new CommonResult<>(CODE_200, null, null);
  }
}
