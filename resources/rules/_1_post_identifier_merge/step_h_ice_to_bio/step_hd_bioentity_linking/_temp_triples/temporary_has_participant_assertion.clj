;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hd_0_temp-has_participant-assertion"
  :description "This rule temporarily marks the has_participant property so that it can be easily referenced in downstream rules."
  :head ((?/has_participant rdf/type kice/temp_has_participant))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

         select ?has_participant {
             kice:RO_0000057 obo:IAO_0000219 ?has_participant .
             filter (?has_participant != obo:RO_0000057) . # obo:has_participant
         }"
  }