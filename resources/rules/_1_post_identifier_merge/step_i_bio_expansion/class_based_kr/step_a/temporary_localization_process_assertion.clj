;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-i_temp-localization-process-assertion"
  :description "This rule temporarily marks the localization process class so that it can be easily referenced in downstream rules."
  :head ((?/localization_process rdf/type kice/temp_localization_process))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

        select ?localization_process {
          kice:GO_0051179 obo:IAO_0000219 ?localization_process .
          filter (?localization_process != obo:GO_0051179) .
        }"
  }