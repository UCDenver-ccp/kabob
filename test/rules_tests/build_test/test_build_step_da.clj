(ns rules-tests.build_test.test_build_step_da
  (use clojure.test
       kr.sesame.kb
       kr.sesame.sparql
       kr.sesame.rdf
       )
  (:require [kabob.build.run-rules :refer [query-variables]]
            [kr.core.forward-rule :refer [add-reify-fns]]
            [kr.core.sparql :refer [sparql-select-query query sparql-query ask]]
            [kr.core.rdf :refer [register-namespaces synch-ns-mappings add! *graph*]]
            [kr.core.kb :refer [kb open close]]
            [kabob.core.namespace :refer [*namespaces*]]
            [kabob.core.rule :refer [kabob-load-rules-from-classpath]]
            [kabob.build.output-kb :refer [output-kb]]
            [clojure.pprint :refer [pprint]]
            [rules-tests.build-test.ccp-ext-ontology :refer [ccp-ext-ontology-triples]]
            [rules-tests.build-test.test-build-util :refer [concepts object-properties other-identifiers-mentioned-in-records ice-identifiers
                                                            initial-plus-ice-triples run-build-rule run-build-rules
                                                            test-kb build-rules-step-a build-rules-step-b
                                                            build-rules-step-ca build-rules-step-cb build-rules-step-cc build-rules-step-cd
                                                            build-rules-step-da build-rules-step-db build-rules-step-dc]]))



(def base-kb (let [source-kb (test-kb initial-plus-ice-triples)]
               (binding [*graph* "http://ccp-extension.owl"]
                 (dorun (map (partial add! source-kb) ccp-ext-ontology-triples)))
               (run-build-rules source-kb build-rules-step-a)
               (run-build-rules source-kb build-rules-step-b)
               (run-build-rules source-kb build-rules-step-ca)
               (run-build-rules source-kb build-rules-step-cb)
               (run-build-rules source-kb build-rules-step-cc)
               (run-build-rules source-kb build-rules-step-cd)
               source-kb))


;;;
;;; rules/pre_identifier_merge/post_ice_rdf_load/step_c_ice_id_typing/refseq_protein_identifier_typing
;;; Test that refseq protein identifiers get appropriately typed (or subclassed in this case).
;;;
(deftest step-da-refseq-protein
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rule source-kb target-kb build-rules-step-da 2)

    (is (ask target-kb '((kice/REFSEQ_NP_001020018 rdfs/subClassOf ccp/IAO_EXT_0001638)))) ;; ccp:refseq_protein_identifier
    (is (ask target-kb '((kice/REFSEQ_NP_003233 rdfs/subClassOf ccp/IAO_EXT_0001638)))) ;; ccp:refseq_protein_identifier
    (is (ask target-kb '((kice/REFSEQ_NP_006752 rdfs/subClassOf ccp/IAO_EXT_0001638)))) ;; ccp:refseq_protein_identifier

    ;; there are 4 metadata triples for each rule run, so 4 metadata triples and 3 rule output triples expected here
    (is (= 7 (count (query target-kb '((?/s ?/p ?/o))))))

    (let [log-kb (output-kb "/tmp/triples.nt")]
      (run-build-rule source-kb log-kb build-rules-step-da 2)
      (close log-kb))
    ))

;;;
;;; rules/pre_identifier_merge/post_ice_rdf_load/step_c_ice_id_typing/refseq_genomic_identifier_typing
;;; Test that refseq genomic identifiers get appropriately typed (or subclassed in this case).
;;;

(deftest step-da-refseq-genomic
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rule source-kb target-kb build-rules-step-da 0)

    ;; there are 4 metadata triples for each rule run, so 4 metadata triples and 0 rule output triples expected here
    (is (= 4 (count (query target-kb '((?/s ?/p ?/o))))))))


;;;
;;; rules/pre_identifier_merge/post_ice_rdf_load/step_c_ice_id_typing/refseq_mrna_identifier_typing
;;; Test that refseq genomic identifiers get appropriately typed (or subclassed in this case).
;;;
(deftest step-da-refseq-mrna
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rule source-kb target-kb build-rules-step-da 1)

    ;; there are 4 metadata triples for each rule run, so 4 metadata triples and 0 rule output triples expected here
    (is (= 4 (count (query target-kb '((?/s ?/p ?/o))))))))


;;;
;;; rules/pre_identifier_merge/post_ice_rdf_load/step_c_ice_id_typing/refseq_rna_identifier_typing
;;; Test that refseq genomic identifiers get appropriately typed (or subclassed in this case).
;;;
(deftest step-da-refseq-rna
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rule source-kb target-kb build-rules-step-da 3)

    ;; there are 4 metadata triples for each rule run, so 4 metadata triples and 0 rule output triples expected here
    (is (= 4 (count (query target-kb '((?/s ?/p ?/o))))))))


;;;
;;; rules/pre_identifier_merge/post_ice_rdf_load/step_d_ice_id_exact_match/uniprot_protein_alternative_identifier_exact_match
;;; Test the generation of skos:exactMatch links between HGNC symbols and NCBI gene identifiers based on the HGNC data source
;;;
(deftest step-da-obsolete-uniprot-identifier-typing
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rule source-kb target-kb build-rules-step-da 4)

    (is (ask target-kb '((kice/UNIPROT_B4DTV5 rdf/type ccp/IAO_EXT_0001711)))) ;; ccp:obsolete_identifier
    (is (ask target-kb '((kice/UNIPROT_Q15580 rdf/type ccp/IAO_EXT_0001711)))) ;; ccp:obsolete_identifier
    (is (ask target-kb '((kice/UNIPROT_Q6DKT6 rdf/type ccp/IAO_EXT_0001711)))) ;; ccp:obsolete_identifier
    (is (ask target-kb '((kice/UNIPROT_Q99474 rdf/type ccp/IAO_EXT_0001711)))) ;; ccp:obsolete_identifier

    ;; there are 4 metadata triples for each rule run, so 4 metadata triples and 4 rule output triples expected here
    (is (= 8 (count (query target-kb '((?/s ?/p ?/o))))))
    ))



;;;
;;; rules/pre_identifier_merge/post_ice_rdf_load/step_c_ice_id_typing/refseq_protein_identifier_typing
;;; Test that refseq protein identifiers get appropriately typed (or subclassed in this case).
;;;
(deftest step-da-refseq-protein
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rule source-kb target-kb build-rules-step-da 2)

    (is (ask target-kb '((kice/REFSEQ_NP_001020018 rdfs/subClassOf ccp/IAO_EXT_0001638)))) ;; ccp:refseq_protein_identifier
    (is (ask target-kb '((kice/REFSEQ_NP_003233 rdfs/subClassOf ccp/IAO_EXT_0001638)))) ;; ccp:refseq_protein_identifier
    (is (ask target-kb '((kice/REFSEQ_NP_006752 rdfs/subClassOf ccp/IAO_EXT_0001638)))) ;; ccp:refseq_protein_identifier

    ;; there are 4 metadata triples for each rule run, so 4 metadata triples and 3 rule output triples expected here
    (is (= 7 (count (query target-kb '((?/s ?/p ?/o))))))))


;;;
;;; rules/pre_identifier_merge/post_ice_rdf_load/step_c_ice_id_typing/refseq_genomic_identifier_typing
;;; Test that refseq genomic identifiers get appropriately typed (or subclassed in this case).
;;;
(deftest step-da-refseq-genomic
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rule source-kb target-kb build-rules-step-da 0)

    ;; there are 4 metadata triples for each rule run, so 4 metadata triples and 0 rule output triples expected here
    (is (= 4 (count (query target-kb '((?/s ?/p ?/o))))))))


;;;
;;; rules/pre_identifier_merge/post_ice_rdf_load/step_c_ice_id_typing/refseq_mrna_identifier_typing
;;; Test that refseq genomic identifiers get appropriately typed (or subclassed in this case).
;;;
(deftest step-da-refseq-mrna
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rule source-kb target-kb build-rules-step-da 1)

    ;; there are 4 metadata triples for each rule run, so 4 metadata triples and 0 rule output triples expected here
    (is (= 4 (count (query target-kb '((?/s ?/p ?/o))))))))


;;;
;;; rules/pre_identifier_merge/post_ice_rdf_load/step_c_ice_id_typing/refseq_rna_identifier_typing
;;; Test that refseq genomic identifiers get appropriately typed (or subclassed in this case).
;;;
(deftest step-da-refseq-rna
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rule source-kb target-kb build-rules-step-da 3)

    ;; there are 4 metadata triples for each rule run, so 4 metadata triples and 0 rule output triples expected here
    (is (= 4 (count (query target-kb '((?/s ?/p ?/o))))))))


;;;
;;; rules/pre_identifier_merge/post_ice_rdf_load/step_d_ice_id_exact_match/uniprot_protein_alternative_identifier_exact_match
;;; Test the generation of skos:exactMatch links between HGNC symbols and NCBI gene identifiers based on the HGNC data source
;;;
(deftest step-da-obsolete-uniprot-identifier-typing
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rule source-kb target-kb build-rules-step-da 4)

    (is (ask target-kb '((kice/UNIPROT_B4DTV5 rdfs/subClassOf ccp/IAO_EXT_0001711)))) ;; ccp:obsolete_identifier
    (is (ask target-kb '((kice/UNIPROT_Q15580 rdfs/subClassOf ccp/IAO_EXT_0001711)))) ;; ccp:obsolete_identifier
    (is (ask target-kb '((kice/UNIPROT_Q6DKT6 rdfs/subClassOf ccp/IAO_EXT_0001711)))) ;; ccp:obsolete_identifier
    (is (ask target-kb '((kice/UNIPROT_Q99474 rdfs/subClassOf ccp/IAO_EXT_0001711)))) ;; ccp:obsolete_identifier

    ;; there are 4 metadata triples for each rule run, so 4 metadata triples and 4 rule output triples expected here
    (is (= 8 (count (query target-kb '((?/s ?/p ?/o))))))
    ))


(deftest step-da-bio-identifier-typing
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rule source-kb target-kb build-rules-step-da 5)

    ;; concepts have already been associated with IAO_EXT_0000342 so they are not returned by this rule
    (is (= (count (distinct (concat ice-identifiers other-identifiers-mentioned-in-records)))
           (count (query target-kb '((?/s rdfs/subClassOf ccp/IAO_EXT_0000342))))))

    (let [log-kb (output-kb "/tmp/triples.nt")]
      (run-build-rule source-kb log-kb build-rules-step-da 5)
      (close log-kb))
    ))





