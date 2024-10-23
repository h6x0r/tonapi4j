# ☕ Tonapi4j

[![Based on TON][ton-svg]][ton]
[![License: MIT][license-svg]][license]
![GitHub last commit](https://img.shields.io/github/last-commit/h6x0r/tonapi4j)

![Image](https://telegra.ph//file/f88bcf9051073973edbd6.jpg)

Java SDK for [tonapi.io].\
Information about the API can be found in the  [documentation](https://docs.tonconsole.com/tonapi/api-v2).\
To use the API **you need an API key**, you can get it here [tonconsole.com](https://tonconsole.com/).

<blockquote>
For creating wallets, transferring TON, Jetton, NFTs, and other operations, recommend using <a href="https://github.com/neodix42/ton4j">ton4j</a> in combination with the library. For more information, refer to the library documentation.
</blockquote>

## Maven [![Maven Central][maven-central-svg]][maven-central]

```xml

<dependency>
    <groupId>io.github.h6x0r</groupId>
    <artifactId>tonapi4j</artifactId>
    <version>1.0.2</version>
</dependency>
```

## Usage

```java

import org.ton.tonapi.async.AsyncTonapi;
import org.ton.tonapi.sync.Tonapi;

// Specify your API_KEY
String API_KEY = "YOUR_TON_API_KEY";
// Specify the account ID (wallet_address)
String ACCOUNT_ID = "UQB8ANV_ynITQr1qHXADHDKYUAQ9VFcCRDZB7h4aPuPKuFtm";

// Asynchronous

// Create a new AsyncTonapi client with the provided API key, testnet and maxRetries count
AsyncTonapi asyncTonapi = new AsyncTonapi(API_KEY, false, 10);

// Retrieve account information asynchronously
CompletableFuture<Account> future = asyncTonapi.getAccounts().getInfo(ACCOUNT_ID);
Account response = future.get();

// Synchronous

// Create a new Tonapi client with the provided API key, testnet and maxRetries count
Tonapi tonapi = new Tonapi(API_KEY, false, 10);

// Retrieve account information synchronously
Account response = tonapi.getAccounts().getInfo(ACCOUNT_ID);


```

* **More examples** can be found in the [test](https://github.com/h6x0r/tonapi4j/tree/master/src/test/java/org/ton)
  package.

## Support ton-java development

If you want to speed up ton-java development and thus change its priority in my backlog, you are welcome to donate some
toncoins:

```UQB8ANV_ynITQr1qHXADHDKYUAQ9VFcCRDZB7h4aPuPKuFtm```

## Contribution

Your contribution is welcome! If you have ideas for improvement or have identified a bug, please create an issue or
submit a pull request.

## License

This repository is distributed under the [MIT License](https://github.com/tonkeeper/pytonapi/blob/main/LICENSE). Feel
free to use, modify, and distribute the code in accordance
with the terms of the license.

[![Star History Chart](https://api.star-history.com/svg?repos=h6x0r/tonapi4j&type=Date)](https://star-history.com/#h6x0r/tonapi4j&Date)

[maven-central-svg]: https://img.shields.io/maven-central/v/io.github.h6x0r/tonapi4j?color=green

[maven-central]: https://mvnrepository.com/artifact/io.github.h6x0r/tonapi4j

[license-svg]: https://img.shields.io/badge/License-MIT-white.svg

[license]: https://opensource.org/licenses/MIT

[tonapi.io]: https://tonapi.io

[ton-svg]: https://img.shields.io/badge/Based%20on-TON-blue

[ton]: https://ton.org