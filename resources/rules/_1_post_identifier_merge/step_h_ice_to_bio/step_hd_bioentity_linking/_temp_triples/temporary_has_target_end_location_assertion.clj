;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hd_0_temp-has_target_end_location-assertion"
  :description "This rule temporarily marks the has_target_end_location property so that it can be easily referenced in downstream rules."
  :head ((?/has_target_end_location rdf/type kice/temp_has_target_end_location))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

         select ?has_target_end_location {
            kice:RO_0002339 obo:IAO_0000219 ?has_target_end_location .
            filter (?has_target_end_location != obo:RO_0002339) .
            }"
  }