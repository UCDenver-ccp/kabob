;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hd_0_temp-direct_interaction-assertion"
  :description "This rule temporarily marks the direct_interaction property so that it can be easily referenced in downstream rules."
  :head ((?/direct_interaction rdf/type kice/temp_direct_interaction))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

         select ?direct_interaction {
              kice:MI_0407 obo:IAO_0000219 ?direct_interaction .
              filter (?direct_interaction != obo:MI_0407) .
              }"
  }