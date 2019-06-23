;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-ha_a_temp-protein-root-assertion"
  :description "This rule temporarily marks the kabob protein class so that it can be easily referenced in downstream rules."
  :head ((?/protein rdf/type kice/temp_protein_root))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

         select ?protein {
            kice:CHEBI_36080 obo:IAO_0000219 ?protein .
            filter (?protein != obo:CHEBI_36080) .
         }"
  }