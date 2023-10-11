(ns calculator-manager-api.adapters.config)

(def config
  {:secret            (or (System/getenv "SECRET")            "secret")
   :port              (or (System/getenv "PORT")              "8080")
   :postgres-db       (or (System/getenv "POSTGRES_DB")       "postgres")
   :postgres-username (or (System/getenv "POSTGRES_USERNAME") "postgres")
   :postgres-host     (or (System/getenv "POSTGRES_ADDRESS")  "postgres-db")
   :postgres-password (or (System/getenv "POSTGRES_PASSWORD") "postgres")
   :initial-credit    (or (System/getenv "INITIAL_CREDIT")    1000)})