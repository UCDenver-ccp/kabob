;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hd_0_temp-inheres_in-assertion"
  :description "This rule temporarily marks the inheres_in property so that it can be easily referenced in downstream rules."
  :head ((?/inheres_in rdf/type kice/temp_inheres_in))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

        select ?inheres_in {
           kice:RO_0000052 obo:IAO_0000219 ?inheres_in .
           filter (?inheres_in != obo:RO_0000052) .
           }"
  }