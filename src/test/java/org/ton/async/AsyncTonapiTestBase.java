package org.ton.async;

import org.ton.tonapi.async.AsyncTonapi;

public class AsyncTonapiTestBase {

  private static final String API_KEY = "AGAGTULGN4LNNFYAAAAD6THPXLO4G7TX7TICGHYUYW7MD7BSLFDJCYGO4VCYG3SVNFUENWI";
  protected AsyncTonapi tonapi = new AsyncTonapi(API_KEY, false, 10);
  protected AsyncTonapi testnetTonapi = new AsyncTonapi(API_KEY, true, 10);

}
