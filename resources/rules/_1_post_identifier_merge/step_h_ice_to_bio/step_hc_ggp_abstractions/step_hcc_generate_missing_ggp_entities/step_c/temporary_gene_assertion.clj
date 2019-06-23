;; --------------------------------------------------
;; --------- Missing gene entity generation ---------
;; --------------------------------------------------
`{:name "step-hcc_temp-gene-assertion"
  :description "This rule identifies subclasses of gene and makes them explicit subclasses with gene root so that downstream queries can avoid using *'s"
  :head ((?/g rdf/type kice/temp_gene))
  :reify ()
  :body "prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
  PREFIX obo: <http://purl.obolibrary.org/obo/>
  PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    select distinct ?g  {
       ?gene rdf:type kice:temp_gene_root .
       ?g rdfs:subClassOf* ?gene .
    }"
  }

