;; -------------------------------------------------------------
;; --------- Protein Entity Direct SubClass Assertion ----------
;; -------------------------------------------------------------
`{:name "step-hcc_b_temp-protein-coding-gene-assertion"
  :description "This rule temporarily marks the has_gene_template property so that it can be easily referenced in downstream rules."
  :head ((?/protein_coding_gene_bioentity rdf/type kice/temp_protein_coding_gene))
  :body "prefix obo: <http://purl.obolibrary.org/obo/>
         prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
         PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
         PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
         PREFIX franzOption_clauseReorderer: <franz:identity>

        select ?protein_coding_gene_bioentity {
           kice:SO_0001217 obo:IAO_0000219 ?protein_coding_gene_bioentity . # OBO:denotes
           filter (?protein_coding_gene_bioentity != obo:SO_0001217) # OBO:gene
        }"
  }