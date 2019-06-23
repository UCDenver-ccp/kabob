;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-i_temp-located-in-assertion"
  :description "This rule temporarily marks the located_in property so that it can be easily referenced in downstream rules."
  :head ((?/located_in rdf/type kice/temp_located_in))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

        select ?located_in {
         kice:RO_0001025 obo:IAO_0000219 ?located_in .
         filter (?located_in != obo:RO_0001025) .
         }"
  }