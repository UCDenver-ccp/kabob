;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hd_0_temp-bio_interaction-assertion"
  :description "This rule temporarily marks the bio_interaction property so that it can be easily referenced in downstream rules."
  :head ((?/bio_interaction rdf/type kice/temp_bio_interaction))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

         select ?bio_interaction {
             kice:INO_0000002 obo:IAO_0000219 ?bio_interaction .
             filter (?bio_interaction != obo:INO_0000002) .
             }"
  }