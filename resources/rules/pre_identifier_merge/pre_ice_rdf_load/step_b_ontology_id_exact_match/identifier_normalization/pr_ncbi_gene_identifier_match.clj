;; ---------------------------------------------------
;; --------- owl:equivalentClass id mappings ---------
;; ---------------------------------------------------
`{:name "pr-ncbi-gene-identifier-match"
  :description "The Protein Ontology defines classes for some concepts, e.g. genes, which are also defined elsewhere in
                kabob. It is important to ensure that these concepts can be connected if they are defined by multiple
                resources. Making identifiers equivalent should facilitate this."
  :head ((?/id skos/exactMatch ?/pr_id)
          (?/id obo/IAO_0000219 ?/ontology_concept) ; IAO:denotes
          (?/id rdfs/subClassOf ccp/IAO_EXT_0000088) ; CCP:ontology_concept_identifier
          (?/id rdfs/subClassOf ccp/IAO_EXT_0000084)) ; CCP:NCBI_gene_identifier
  :reify ([?/id {:ln (:regex "NCBIGene:" "NCBI_GENE_" ?/concept_id)
                 :ns "ccp" :prefix "" :suffix ""}])
  :sparql-string "prefix franzOption_clauseReorderer: <franz:identity>
                  prefix franzOption_chunkProcessingAllowed: <franz:yes>
                  prefix ccp: <http://ccp.ucdenver.edu/obo/ext/>
                  prefix obo: <http://purl.obolibrary.org/obo/>
                  prefix oboInOwl: <http://www.geneontology.org/formats/oboInOwl#>
                  select ?ontology_concept ?concept_id ?pr_id {
                      ?ontology_concept oboInOwl:id ?concept_id .
                      # include only concepts with the NCBI namespace
                      filter (contains (str(?ontology_concept), 'http://purl.obolibrary.org/obo/NCBIGene_'))
                      minus {?ontology_concept owl:deprecated true} .
                      # return the id generated by processing pr.owl and equate it to the expected id form, e.g. NCBI_GENE_NNN
                      ?pr_id obo:IAO_0000219 ?ontology_concept .
                  }"
  }