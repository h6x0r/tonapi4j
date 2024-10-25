package org.ton.tonapi.sync;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.ton.tonapi.sync.methods.AccountsMethod;
import org.ton.tonapi.sync.methods.BlockchainMethod;
import org.ton.tonapi.sync.methods.DnsMethod;
import org.ton.tonapi.sync.methods.EmulateMethod;
import org.ton.tonapi.sync.methods.EventsMethod;
import org.ton.tonapi.sync.methods.InscriptionsMethod;
import org.ton.tonapi.sync.methods.JettonsMethod;
import org.ton.tonapi.sync.methods.LiteserverMethod;
import org.ton.tonapi.sync.methods.MultisigMethod;
import org.ton.tonapi.sync.methods.NftMethod;
import org.ton.tonapi.sync.methods.RatesMethod;
import org.ton.tonapi.sync.methods.SSEMethod;
import org.ton.tonapi.sync.methods.StakingMethod;
import org.ton.tonapi.sync.methods.StorageMethod;
import org.ton.tonapi.sync.methods.TonconnectMethod;
import org.ton.tonapi.sync.methods.TracesMethod;
import org.ton.tonapi.sync.methods.UtilitiesMethod;
import org.ton.tonapi.sync.methods.WalletMethod;

@Slf4j
public class Tonapi extends TonapiClientBase {

  public Tonapi(String apiKey,
      Boolean isTestnet,
      Integer maxRetries) {
    super(apiKey, isTestnet, maxRetries, null, null, null);
  }

  public Tonapi(String apiKey,
      Boolean isTestnet,
      Integer maxRetries,
      String baseUrl,
      Map<String, String> headers,
      Float timeout) {
    super(apiKey, isTestnet, maxRetries, baseUrl, headers, timeout);
  }

  public BlockchainMethod getBlockchain() {
    return new BlockchainMethod(this);
  }

  public AccountsMethod getAccounts() {
    return new AccountsMethod(this);
  }

  public JettonsMethod getJettons() {
    return new JettonsMethod(this);
  }

  public LiteserverMethod getLiteserver() {
    return new LiteserverMethod(this);
  }

  public MultisigMethod getMultisig() {
    return new MultisigMethod(this);
  }

  public DnsMethod getDns() {
    return new DnsMethod(this);
  }

  public EmulateMethod getEmulate() {
    return new EmulateMethod(this);
  }

  public EventsMethod getEvents() {
    return new EventsMethod(this);
  }

  public InscriptionsMethod getInscriptions() {
    return new InscriptionsMethod(this);
  }

  public NftMethod getNft() {
    return new NftMethod(this);
  }

  public RatesMethod getRates() {
    return new RatesMethod(this);
  }

  public SSEMethod getSse() {
    return new SSEMethod(this);
  }

  public StakingMethod getStaking() {
    return new StakingMethod(this);
  }

  public StorageMethod getStorage() {
    return new StorageMethod(this);
  }

  public TonconnectMethod getTonconnect() {
    return new TonconnectMethod(this);
  }

  public TracesMethod getTraces() {
    return new TracesMethod(this);
  }

  public UtilitiesMethod getUtilities() {
    return new UtilitiesMethod(this);
  }

  public WalletMethod getWallet() {
    return new WalletMethod(this);
  }
}
