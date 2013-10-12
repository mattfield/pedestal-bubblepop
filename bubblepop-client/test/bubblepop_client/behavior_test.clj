(ns bubblepop-client.behavior-test
  (:require [io.pedestal.app :as app]
            [io.pedestal.app.protocols :as p]
            [io.pedestal.app.tree :as tree]
            [io.pedestal.app.messages :as msg]
            [io.pedestal.app.render :as render]
            [io.pedestal.app.util.test :as test])
  (:use clojure.test
        bubblepop-client.behavior
        [io.pedestal.app.query :only [q]]))

;; Testing the inc transform function

(deftest test-inc-transform
  (is (= (inc-transform nil {msg/type :inc msg/topic [:my-counter]})
         1))
  (is (= (inc-transform 0 {msg/type :inc msg/topic [:my-counter]})
         1))
  (is (= (inc-transform 1 {msg/type :inc msg/topic [:my-counter]})
         2))
  (is (= (inc-transform 1 nil)
         2)))

;; Extract current value of data model from app

(defn- data-model [app]
  (-> app :state deref :data-model))

;; Build an application, send a message to a transform and check the transform
;; state

(deftest test-app-state
  (let [app (app/build example-app)]
    (is (test/run-sync! app [{msg/type :inc msg/topic [:my-counter]}]
                        :begin :default))
    (is (= (data-model app)
           {:my-counter 1})))
  (let [app (app/build example-app)]
    (is (test/run-sync! app [{msg/type :inc msg/topic [:my-counter]}
                             {msg/type :inc msg/topic [:my-counter]}
                             {msg/type :inc msg/topic [:my-counter]}]
                        :begin :default))
    (is (= (data-model app)
           {:my-counter 3}))))

;; Use io.pedestal.app.query to query the current application model

