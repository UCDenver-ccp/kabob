;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hd_0_temp-drug_role-assertion"
  :description "This rule temporarily marks the drug_role property so that it can be easily referenced in downstream rules."
  :head ((?/drug_role rdf/type kice/temp_drug_role))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

         select ?drug_role {
             kice:CHEBI_23888 obo:IAO_0000219 ?drug_role .
             filter (?drug_role != obo:CHEBI_23888) .
             }"
  }