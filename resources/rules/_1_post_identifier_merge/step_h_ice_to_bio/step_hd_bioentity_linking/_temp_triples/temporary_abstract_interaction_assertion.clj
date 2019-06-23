;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hd_0_temp-abstract_interaction-assertion"
  :description "This rule temporarily marks the abstract_interaction property so that it can be easily referenced in downstream rules."
  :head ((?/abstract_interaction rdf/type kice/temp_abstract_interaction))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

         select ?abstract_interaction {
             kice:INO_0000002 obo:IAO_0000219 ?abstract_interaction .
             filter (?abstract_interaction != obo:INO_0000002) .
             }"
  }