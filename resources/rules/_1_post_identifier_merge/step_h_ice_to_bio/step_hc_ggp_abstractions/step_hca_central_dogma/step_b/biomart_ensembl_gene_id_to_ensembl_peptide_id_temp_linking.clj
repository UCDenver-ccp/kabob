;; -----------------------------------------------------------------
;; --------- Ensembl Gene ID NCBI Gene ID Exact Match --------
;; -----------------------------------------------------------------
`{:name "step-hcab_biomart_ensembl_gene_to_ensembl_protein_temp_linking"
  :description "This rule links Ensembl transcripts with Ensembl proteins using has_gene_template"
  :head ((?/protein ccp/temp_possible_hgt_restriction ?/gene))
  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT distinct ?gene ?protein
  WHERE {

         ?ensembl_protein_identifier_field_value rdf:type ccp:IAO_EXT_0001996 . # CCP:ensembl_protein_id_field_value
    ?ensembl_protein_identifier_field_value rdf:type ?ensembl_protein_identifier .
         ?ensembl_protein_identifier rdfs:subClassOf ccp:IAO_EXT_0001991 . # ccp:ensembl_protein_identifier
    ?ensembl_protein_identifier obo:IAO_0000219 ?protein .

         ?record obo:BFO_0000051 ?ensembl_protein_identifier_field_value .
         ?record rdf:type ccp:IAO_EXT_0001995 . # CCP:BioMart_central_dogma_identifier_mapping_record
    ?record obo:BFO_0000051 ?ensembl_gene_id_field_value .
         ?ensembl_gene_identifier rdfs:subClassOf ccp:IAO_EXT_0001955 . # CCP:Ensembl_gene_identifier

    ?ensembl_gene_identifier obo:IAO_0000219 ?gene .
         ?ensembl_gene_id_field_value rdf:type ?ensembl_gene_identifier .
         ?ensembl_gene_id_field_value rdf:type ccp:IAO_EXT_0001961 . # CCP:Ensembl_gene_id_field_value

    }"
}