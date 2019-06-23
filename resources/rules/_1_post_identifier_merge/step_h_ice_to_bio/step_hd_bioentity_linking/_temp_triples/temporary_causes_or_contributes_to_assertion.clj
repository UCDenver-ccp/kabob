;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hd_0_temp-cause_or_contributes_to_condition-assertion"
  :description "This rule temporarily marks the cause_or_contributes_to_condition property so that it can be easily referenced in downstream rules."
  :head ((?/cause_or_contributes_to_condition rdf/type kice/temp_cause_or_contributes_to_condition))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

        select ?cause_or_contributes_to_condition {
         kice:RO_0003302 obo:IAO_0000219 ?cause_or_contributes_to_condition .
         filter (?cause_or_contributes_to_condition != obo:RO_0003302) .
         }"
  }