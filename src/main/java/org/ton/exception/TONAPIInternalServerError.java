package org.ton.exception;

public class TONAPIInternalServerError extends TONAPIServerError {

  public TONAPIInternalServerError(String message) throws TONAPIMempoolNotEnabledError {
    super(message);
    if (message != null && message.contains("mempool is not enabled")) {
      throw new TONAPIMempoolNotEnabledError(
          "Mempool functionality is not enabled on your plan. Upgrade your plan on https://tonconsole.com.");
    }
  }
}
