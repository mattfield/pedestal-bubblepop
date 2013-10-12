(ns bubblepop-client.simulated.start
  (:require [io.pedestal.app.render.push.handlers.automatic :as d]
            [bubblepop-client.start :as start]
            [bubblepop-client.rendering :as rendering]
            [goog.Uri]
            ;; This needs to be included somewhere in order for the
            ;; tools to work.
            [io.pedestal.app-tools.tooling :as tooling]
            [io.pedestal.app.protocols :as p]
            [bubblepop-client.simulated.services :as services]
            [io.pedestal.app :as app]))

(defn param [name]
  (let [uri (goog.Uri. (.toString  (.-location js/document)))]
    (.getParameterValue uri name)))

(defn ^:export main []
  (let [app (start/create-app d/data-renderer-config)
        services (services/->MockServices (:app app))]
    (app/consume-effects (:app app) services/services-fn)
    (p/start services)
    app))
