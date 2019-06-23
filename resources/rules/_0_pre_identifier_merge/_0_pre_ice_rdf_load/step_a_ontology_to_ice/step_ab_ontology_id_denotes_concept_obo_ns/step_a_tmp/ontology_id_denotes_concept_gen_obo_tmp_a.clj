;; ------------------------------------------------------------------------
;; --------- Ontology Concept Identifier Denotes Ontology Concept ---------
;; ------------------------------------------------------------------------
;; create id denotes concept triple for every ontology concept
`{:name          "step-aba_ontology-id-denotes-concept-gen-obo-tmp-a"
  :description   "temporary assignment of all subclasses of IAO_0000030 to rdf:type ccp:temp_ice_concept"
  :head          ((?/ontology_concept rdf/type ccp/temp_ice_concept))
  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
         prefix franzOption_clauseReorderer: <franz:identity>
                  prefix obo: <http://purl.obolibrary.org/obo/>
                  select distinct ?ontology_concept {
                      ?ontology_concept rdfs:subClassOf* obo:IAO_0000030 .
                  }"
  }
