package org.ton.sync;

import org.ton.tonapi.sync.Tonapi;

public class TonapiTestBase {

  private static final String API_KEY = "AGAGTULGDPRBFZQAAAAKCH4HZ5VKKEHZ2LDK2OX3WKDUIGE3OMVI5M4P7VDNQ5AMFH6BSFY";
  protected Tonapi tonapi = new Tonapi(API_KEY, false, 10);
  protected Tonapi testnetTonapi = new Tonapi(API_KEY, true, 10);
}
