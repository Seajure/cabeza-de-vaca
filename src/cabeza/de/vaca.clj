(ns cabeza.de.vaca
  (:import [javax.jmdns JmDNS ServiceListener ServiceInfo]))

(def jmdns (JmDNS/create))

(def listeners (atom {}))

(defn add-listener [type callback]
  (let [listener (reify ServiceListener
                        (serviceAdded [this event]
                                      (callback event)))]
    (swap! listeners assoc type listener)
    (.addServiceListener jmdns type listener)
    listener))

(defn register [type name port text]
  (.registerService jmdns (ServiceInfo/create type name port text)))
