;; --------------------------------------------------
;; --------- Missing gene entity generation ---------
;; --------------------------------------------------
`{:name "step-hcc_temp-missing-gene-gen-protein-no-template"
  :description "This rule identifies subclasses of protein that are not involved in a gene template restriction."
  :head ((?/protein_missing_gene rdf/type kice/protein_no_template))
  :reify ()
  :body "prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
  PREFIX obo: <http://purl.obolibrary.org/obo/>
  PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    select distinct ?protein_missing_gene  {

        # the below subClassOf should be subClassOf* but we have previously linked all
        # subclasses directly to the root protein class so we don't need the * here.
        ?protein rdf:type kice:temp_protein_root .
        ?protein_missing_gene rdfs:subClassOf ?protein .

        ## exclude proteins that already have a has_gene_template restriction (likely imported via pr.owl)
        minus {
               ?has_gene_template rdf:type kice:temp_has_gene_template .
               ?has_gene_template_r owl:onProperty ?has_gene_template .
               ?protein_missing_gene rdfs:subClassOf ?has_gene_template_r .
               }
        }"
  }

