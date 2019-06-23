;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hd_0_temp-has-gene-template-assertion"
  :description "This rule temporarily marks the has_gene_template property so that it can be easily referenced in downstream rules."
  :head ((?/has_gene_template rdf/type kice/temp_has_gene_template))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

         select ?has_gene_template {
           ?has_gene_template_id obo:IAO_0000219 <http://purl.obolibrary.org/obo/pr#has_gene_template> .
           ?has_gene_template_id obo:IAO_0000219 ?has_gene_template .
           # ensure it's a kabob bioentity (not an obo bioentity)
           filter (contains (str(?has_gene_template), 'http://ccp.ucdenver.edu/kabob/bio/'))
           }"
  }