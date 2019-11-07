# nuid.ethereum

Cross-platform Ethereum API.

## Notes

`nuid.ethereum` primarily exists to abstract over platform-specific differences and provide a common interface to the provided functionality across host platforms. `nuid.ethereum` delegates most invocations to a host implemention (e.g. `web3j` on the `jvm`, and `web3js`, etc. in `node` and the browser). This library inherits both dependencies' capability to interact with a remote service such as [Infura](https://infura.io/).

While top-level functionality (e.g. `nuid.ethereum/client`, `nuid.ethereum.transaction/send`, `nuid.ethereum.transaction/get`, etc.) are consistent across platforms, there remain host idiosyncrasies in how nonces and transactions are managed and signed.

## Requirements

[`jvm`](https://www.java.com/en/download/), [`node + npm`](https://nodejs.org/en/download/), [`clj`](https://clojure.org/guides/getting_started), [`shadow-cljs`](https://shadow-cljs.github.io/docs/UsersGuide.html#_installation)

## From Clojure and ClojureScript

### tools.deps:

`{nuid/ethereum {:git/url "https://github.com/nuid/ethereum" :sha "..."}}`

### usage:

```
$ clj # or shadow-cljs node-repl
=> (require '[clojure.core.async :as async]) ;; or [cljs.core...]
=> (require '[nuid.ethereum.transaction :as tx])
=> (require '[nuid.ethereum.address :as addr])
=> (require '[nuid.ethereum :as eth])
=> (require '[nuid.bytes :as bytes])
=> (require '[nuid.hex :as hex])
=> (def http-provider "...") ;; e.g. https://rinkeby.infura.io/...
=> (def private-key "...")
=> (def config #:ethereum{:http-provider http-provider
                          :private-key private-key})
=> (def client (eth/client config))
=> (def resp (atom nil))
=> (def data (str "0x" (hex/encode "blockchain! 🤡")))
=> (async/take! (eth/send-transaction client {:data data})
                (partial reset! resp))
;; finalizing ...
=> (async/take! (eth/get-transaction-by-hash client @resp)
                (partial reset! resp))
=> (bytes/str (hex/decode (tx/get-input @resp))) ;; => "blockchain! 🤡"
```

## Note

This library is a reduced API for interacting with Ethereum via a consistent interface across platforms (`clj` and `cljs`). Convenience from other languages is not a high priority, since using the host libraries directly will likely be preferable.

## Licensing

Apache v2.0 or MIT

## Contributing

### formatting:

```
$ clojure -A:cljfmt            # check
$ clojure -A:cljfmt:cljfmt/fix # fix
```

### dependencies:

```
## check
$ npm outdated
$ clojure -A:depot

## update
$ npm upgrade -s
$ clojure -A:depot:depot/update
```
