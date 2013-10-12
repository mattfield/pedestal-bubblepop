(ns bubblepop-client.html-templates
  (:use [io.pedestal.app.templates :only [tfn dtfn tnodes]]))

(defmacro bubblepop-client-templates
  []
  {:bubblepop-client-page (dtfn (tnodes "bubblepop-client.html" "hello") #{:id})})
