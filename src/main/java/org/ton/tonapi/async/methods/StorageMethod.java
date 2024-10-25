package org.ton.tonapi.async.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.concurrent.CompletableFuture;
import org.ton.exception.TONAPIError;
import org.ton.schema.storage.StorageProviders;
import org.ton.tonapi.async.AsyncTonapiClientBase;

public class StorageMethod extends AsyncTonapiClientBase {

  public StorageMethod(AsyncTonapiClientBase client) {
    super(client);
  }

  /**
   * Get TON storage providers deployed to the blockchain.
   *
   * @return CompletableFuture of StorageProviders object containing storage provider information
   * @throws TONAPIError if the request fails
   */
  public CompletableFuture<StorageProviders> getProviders() throws TONAPIError {
    String method = "v2/storage/providers";
    return this.get(method, null, null, new TypeReference<StorageProviders>() {
    });
  }
}
