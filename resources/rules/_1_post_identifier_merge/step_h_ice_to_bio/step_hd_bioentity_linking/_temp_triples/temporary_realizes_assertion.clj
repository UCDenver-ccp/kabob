;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hd_0_temp-realizes-assertion"
  :description "This rule temporarily marks the realizes property so that it can be easily referenced in downstream rules."
  :head ((?/realizes rdf/type kice/temp_realizes))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

         select ?realizes {
            kice:BFO_0000055 obo:IAO_0000219 ?realizes .
            filter (?realizes != obo:BFO_0000055) .
            }"
  }