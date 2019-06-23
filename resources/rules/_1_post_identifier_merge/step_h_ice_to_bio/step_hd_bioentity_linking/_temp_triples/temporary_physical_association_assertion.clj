;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hd_0_temp-physical_association-assertion"
  :description "This rule temporarily marks the physical_association property so that it can be easily referenced in downstream rules."
  :head ((?/physical_association rdf/type kice/temp_physical_association))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

         select ?physical_association {
              kice:MI_0915 obo:IAO_0000219 ?physical_association .
              filter (?physical_association != obo:MI_0915) . # obo:physical_association
         }"
  }