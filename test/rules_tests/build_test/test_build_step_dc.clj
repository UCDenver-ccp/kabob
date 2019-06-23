(ns rules-tests.build_test.test_build_step_dc
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



(deftest step-dc-uniprot-to-refseq-identifier-exact-match
  (let [source-kb base-kb
        target-kb (test-kb '())]
    (run-build-rules source-kb build-rules-step-da)
    (run-build-rules source-kb build-rules-step-db)
    (run-build-rule source-kb target-kb build-rules-step-dc 0)

    (is (ask target-kb '((kice/REFSEQ_NP_006752 skos/exactMatch kice/UNIPROT_P62258))))

    ;; the following is not there b/c the refseq is for an isoform of P37173
    ;;(is (ask target-kb '((kice/REFSEQ_NP_001020018 skos/exactMatch kice/UNIPROT_P37173))))

    ;; there are 4 metadata triples for each rule run, so 4 metadata triples and 2 rule output triples expected here
    (is (= 5 (count (query target-kb '((?/s ?/p ?/o))))))

    ;(let [log-kb (output-kb "/tmp/triples.nt")]
    ;  (run-build-rule source-kb log-kb build-rules-step-dc 0)
    ;  (close log-kb))


    ;(prn (str "--------------------------------"))
    ;(doall (map #(prn (str %)) (sparql-query source-kb
    ;                                         "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    ;                                          prefix ccp: <http://ccp.ucdenver.edu/obo/ext/>
    ;                                          prefix obo: <http://purl.obolibrary.org/obo/>
    ;                                          prefix owl: <http://www.w3.org/2002/07/owl#>
    ;                                          prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
    ;                                          prefix skos: <http://www.w3.org/2004/02/skos/core#>
    ;                                          select * {
    ;                                            ?record rdf:type ccp:IAO_EXT_0000692 . # CCP:ncbi_gene_refseq_uniprotkb_collaboration_record
    ;                                            ?record obo:BFO_0000051 ?refseq_protein_identifier_field_value .
    ;                                            ?refseq_protein_identifier_field_value rdf:type ccp:IAO_EXT_0000927 . # CCP:ncbi_gene_refseq_uniprotkb_collaboration_record_refseq_protein_identifier_field_value
    ;                                            ?refseq_protein_identifier_field_value rdf:type ?refseq_identifier .
    ;                                            ?refseq_identifier rdfs:subClassOf ccp:IAO_EXT_0001638 . # CCP:refseq_protein_identifier
    ;                                            ?record obo:BFO_0000051 ?uniprot_identifier_field_value .
    ;                                            ?uniprot_identifier_field_value rdf:type ccp:IAO_EXT_0000928 . # CCP:ncbi_gene_refseq_uniprotkb_collaboration_record_uniprot_identifier_field_value
    ;                                            ?uniprot_identifier_field_value rdf:type ?uniprot_identifier .
    ;                                            ?uniprot_identifier rdfs:subClassOf ccp:IAO_EXT_0000184 . # CCP:uniprot_identifier
    ;
    ;                                        # only include matches if the refseq identifier is not already in a skos:exactMatch relation
    ;                                                     # b/c if it is, then it is already directly linked to a uniprot isoform id
    ;                                                     minus {?refseq_identifier skos:exactMatch ?other_id}
    ;                                                     minus {?other_id skos:exactMatch ?refseq_identifier}
    ;                                         }"
    ;                                         )))
    ;
    ;    (prn (str "--------------------------------"))

    ))


