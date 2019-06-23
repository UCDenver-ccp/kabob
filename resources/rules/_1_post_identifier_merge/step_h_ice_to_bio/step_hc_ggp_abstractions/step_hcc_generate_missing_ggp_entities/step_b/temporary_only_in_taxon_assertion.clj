;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hcc_b_temp-only-in-taxon-assertion"
  :description "This rule temporarily marks the has_gene_template property so that it can be easily referenced in downstream rules."
  :head ((?/only_in_taxon rdf/type kice/temp_only_in_taxon))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

         select ?only_in_taxon {
           kice:RO_0002160 obo:IAO_0000219 ?only_in_taxon .
           filter (?only_in_taxon != obo:RO_0002160) .
         }"
  }