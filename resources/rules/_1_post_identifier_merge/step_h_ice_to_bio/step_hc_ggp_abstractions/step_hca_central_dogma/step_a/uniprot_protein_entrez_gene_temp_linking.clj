;; -------------------------------------------------------
;; --------- Uniprot Protein Entrez Gene Mapping ---------
;; -------------------------------------------------------
`{:name "step-hcaa_uniprot-protein-entrez-gene-temp-linking"
  :description "This rule maps uniprot proteins to entrez genes"
  :head ((?/protein ccp/temp_possible_hgt_restriction ?/gene))
  :body "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
  PREFIX obo: <http://purl.obolibrary.org/obo/>
  PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
  SELECT ?protein ?gene
  WHERE {
         ?record rdf:type ccp:IAO_EXT_0000235 .  # CCP:UniProt_identifier_mapping_record
         ?record obo:BFO_0000051 ?uniprot_identifier_field_value .
         ?uniprot_identifier_field_value rdf:type ccp:IAO_EXT_0000239 .  # CCP:UniProt_identifier_mapping_record__UniProt_accession_identifier_field_value
         ?uniprot_identifier_field_value rdf:type ?uniprot_identifier .
         ?uniprot_identifier obo:IAO_0000219 ?protein .
         ?record obo:BFO_0000051 ?ncbi_gene_identifier_field_value .
         ?ncbi_gene_identifier_field_value rdf:type ccp:IAO_EXT_0000242 .  # CCP:UniProt_identifier_mapping_record__Entrez_gene_identifier_field_value
         ?ncbi_gene_identifier_field_value rdf:type ?ncbi_gene_identifier .
         ?ncbi_gene_identifier obo:IAO_0000219 ?gene .
         }"
  }

