;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-i_temp-transports-or-maintains-localization-of-assertion"
  :description "This rule temporarily marks the 'transports or maintains localization of' property so that it can be easily referenced in downstream rules."
  :head ((?/transports_or_maintains_localization_of rdf/type kice/temp_transports_or_maintains_localization_of))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

        select ?transports_or_maintains_localization_of {
            kice:RO_0002313 obo:IAO_0000219 ?transports_or_maintains_localization_of .
            filter (?transports_or_maintains_localization_of != obo:RO_0002313) .
        }"
  }