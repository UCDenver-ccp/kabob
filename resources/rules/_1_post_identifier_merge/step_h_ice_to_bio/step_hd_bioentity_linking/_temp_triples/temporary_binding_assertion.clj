;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hd_0_temp-binding-assertion"
  :description "This rule temporarily marks the binding property so that it can be easily referenced in downstream rules."
  :head ((?/binding rdf/type kice/temp_binding))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

         select ?binding {
             kice:GO_0005488 obo:IAO_0000219 ?binding .
             filter (?binding != obo:GO_0005488) .
           }"
  }