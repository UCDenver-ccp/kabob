;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hcc_b_temp-gene-root-assertion"
  :description "This rule temporarily marks the has_gene_template property so that it can be easily referenced in downstream rules."
  :head ((?/gene rdf/type kice/temp_gene_root))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

        select ?gene {
            kice:SO_0000704 obo:IAO_0000219 ?gene .
            filter (?gene != obo:SO_0000704) .
            }"
  }