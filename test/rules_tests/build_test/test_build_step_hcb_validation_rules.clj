(ns rules-tests.build_test.test_build_step_hcb_validation_rules
  (use clojure.test
       kr.sesame.kb
       kr.sesame.sparql
       kr.sesame.rdf
       )
  (:require [kabob.build.run-rules :refer [query-variables]]
            [kr.core.forward-rule :refer [add-reify-fns]]
            [kr.core.sparql :refer [sparql-select-query query sparql-query ask]]
            [kr.core.rdf :refer [register-namespaces synch-ns-mappings add! load-rdf *graph*]]
            [kr.core.kb :refer [kb open close]]
            [kabob.core.namespace :refer [*namespaces*]]
            [kabob.core.rule :refer [kabob-load-rules-from-classpath]]
            [kabob.build.output-kb :refer [output-kb]]
            [clojure.pprint :refer [pprint]]
            [rules-tests.build-test.ccp-ext-ontology :refer [ccp-ext-ontology-triples]]
            [rules-tests.build-test.test-build-util :refer [initial-plus-ice-triples run-build-rule run-build-rules
                                                            test-kb build-rules-step-a build-rules-step-b
                                                            build-rules-step-ca build-rules-step-cb build-rules-step-cc build-rules-step-cd
                                                            build-rules-step-da build-rules-step-db build-rules-step-dc
                                                            build-rules-step-fa
                                                            build-rules-step-fb
                                                            build-rules-step-ga build-rules-step-gb build-rules-step-gca
                                                            build-rules-step-gcb build-rules-step-gcc
                                                            build-rules-step-ha build-rules-step-hb
                                                            build-rules-step-hca build-rules-step-hcb
                                                            validation-rules-list validation-rules-restriction
                                                            expected-subpropertyof-links expected-inverseof-links
                                                            expected-subclassof-links expected-disjointwith-links
                                                            expected-restrictions expected-restrictions-in-lists
                                                            expected-equivalent-class-links expected-rdfs-domain-links
                                                            expected-rdfs-range-links]]
            [test-with-files.core :refer [with-tmp-dir tmp-dir]]
            [kabob.build.id-sets.generate :refer [generate-all-id-sets]]
            [clojure.java.io :as io])
  (:import [java.io File]
           [java.util.Arrays]))




(defn get-only-files
  "given a directory path, return only the files that are in the directory"
  [path]
  (filter #(.isFile %) (.listFiles (io/as-file path))))

(def base-kb (let [source-kb (test-kb initial-plus-ice-triples)]
               (binding [*graph* "http://ccp-extension.owl"]
                 (dorun (map (partial add! source-kb) ccp-ext-ontology-triples)))
               (run-build-rules source-kb build-rules-step-a)
               (run-build-rules source-kb build-rules-step-b)
               (run-build-rules source-kb build-rules-step-ca)
               (run-build-rules source-kb build-rules-step-cb)
               (run-build-rules source-kb build-rules-step-cc)
               (run-build-rules source-kb build-rules-step-cd)
               (run-build-rules source-kb build-rules-step-da)
               (run-build-rules source-kb build-rules-step-db)
               (run-build-rules source-kb build-rules-step-dc)

               (with-tmp-dir
                 ;; generate identifier set ntriple files and load into the source-kb
                 (generate-all-id-sets source-kb (str tmp-dir "/"))
                 (prn (str "PRINTING FILE LIST: " (count (get-only-files tmp-dir))))
                 (dorun (map (fn [f] (prn (str "FILE TO LOAD:" f))
                               (load-rdf source-kb (java.util.zip.GZIPInputStream.
                                                     (clojure.java.io/input-stream
                                                       f)) :ntriple))
                             (get-only-files tmp-dir))))
               (run-build-rules source-kb build-rules-step-fa)
               (run-build-rules source-kb build-rules-step-fb)
               (run-build-rules source-kb build-rules-step-ga)
               (run-build-rules source-kb build-rules-step-gb)
               (run-build-rules source-kb build-rules-step-gca)
               (run-build-rules source-kb build-rules-step-gcb)
               (run-build-rules source-kb build-rules-step-gcc)
               (run-build-rules source-kb build-rules-step-ha)
               (run-build-rules source-kb build-rules-step-hb)
               (run-build-rules source-kb build-rules-step-hca)
               source-kb))


;; validate list structures
(deftest step-hcc-test-with-validation-rules-lists
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rules source-kb build-rules-step-hcb)

    (run-build-rule source-kb target-kb validation-rules-list 0)
    ;; add 4 for the rule metadata
    (is (= 4 (count (query target-kb '((?/s ?/p ?/o))))))

    (run-build-rule source-kb target-kb validation-rules-list 1)
    ;; add 4 for the rule metadata
    (is (= 8 (count (query target-kb '((?/s ?/p ?/o))))))

    (run-build-rule source-kb target-kb validation-rules-list 2)
    ;; add 4 for the rule metadata
    (is (= 12 (count (query target-kb '((?/s ?/p ?/o))))))

    (run-build-rule source-kb target-kb validation-rules-list 3)
    ;; add 4 for the rule metadata
    (is (= 16 (count (query target-kb '((?/s ?/p ?/o))))))

    (run-build-rule source-kb target-kb validation-rules-list 4)
    ;; add 4 for the rule metadata
    (is (= 20 (count (query target-kb '((?/s ?/p ?/o))))))

    (run-build-rule source-kb target-kb validation-rules-list 5)
    ;; add 4 for the rule metadata
    (is (= 24 (count (query target-kb '((?/s ?/p ?/o))))))

    (run-build-rule source-kb source-kb validation-rules-list 6)
    (run-build-rule source-kb target-kb validation-rules-list 7)
    ;; add 4 for the rule metadata
    (is (= 28 (count (query target-kb '((?/s ?/p ?/o))))))))


;; validate restriction structures
(deftest step-hcc-test-with-validation-rules-restrictions
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rules source-kb build-rules-step-hcb)

    (run-build-rule source-kb target-kb validation-rules-restriction 0)
    ;; add 4 for the rule metadata
    (is (= 4 (count (query target-kb '((?/s ?/p ?/o))))))

    (run-build-rule source-kb target-kb validation-rules-restriction 1)
    ;; add 4 for the rule metadata
    (is (= 8 (count (query target-kb '((?/s ?/p ?/o))))))

    (run-build-rule source-kb target-kb validation-rules-restriction 2)
    ;; add 4 for the rule metadata
    (is (= 12 (count (query target-kb '((?/s ?/p ?/o))))))

    (run-build-rule source-kb target-kb validation-rules-restriction 3)
    ;; add 4 for the rule metadata
    (is (= 16 (count (query target-kb '((?/s ?/p ?/o))))))

    (run-build-rule source-kb target-kb validation-rules-restriction 4)
    ;; add 4 for the rule metadata
    (is (= 20 (count (query target-kb '((?/s ?/p ?/o))))))))



