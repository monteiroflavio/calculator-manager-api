(defproject clojure-sandbox "0.1.0-SNAPSHOT"
  :main calculator-manager-api.core
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure               "1.11.1"]
                 [buddy/buddy-auth                  "3.0.323"]
                 [crypto-password                   "0.3.0"]
                 [http-kit/http-kit                 "2.7.0"]
                 [compojure                         "1.7.0"]
                 [com.github.seancorfield/honeysql  "2.4.1066"]
                 [com.github.seancorfield/next.jdbc "1.3.834"]
                 [org.postgresql/postgresql         "42.2.10"] 
                 [prismatic/schema                  "1.4.1"]
                 [ring/ring-json                    "0.5.1"]]
  :repl-options {:init-ns clojure-sandbox.core})
