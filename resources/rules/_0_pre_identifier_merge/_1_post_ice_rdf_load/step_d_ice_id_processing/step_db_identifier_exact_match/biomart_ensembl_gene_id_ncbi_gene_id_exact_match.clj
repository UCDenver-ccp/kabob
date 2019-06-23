;; -----------------------------------------------------------------
;; --------- Ensembl Gene ID NCBI Gene ID Exact Match --------
;; -----------------------------------------------------------------
`{:name "step-db_biomart-ensembl-gene-id-ncbi-gene-id-exact-match"
  :description "This rule asserts an exact match between Ensembl gene identifiers and NCBI gene identifiers via BioMart identifier mapping records"
  :head ((?/ensembl_gene_identifier skos/exactMatch ?/ncbi_gene_identifier))
  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
    SELECT distinct ?ensembl_gene_identifier ?ncbi_gene_identifier
    WHERE {  ?record rdf:type ccp:IAO_EXT_0001992 . # CCP:BioMart_gene_identifier_mapping_record
            ?record obo:BFO_0000051 ?ensembl_gene_id_field_value .
            ?ensembl_gene_id_field_value rdf:type ccp:IAO_EXT_0001961 . # CCP:Ensembl_gene_id_field_value
            ?ensembl_gene_id_field_value rdf:type ?ensembl_gene_identifier .
            ?ensembl_gene_identifier rdfs:subClassOf ccp:IAO_EXT_0001955 . # CCP:Ensembl_gene_identifier
            ?record obo:BFO_0000051 ?ncbi_gene_identifier_field_value .
            ?ncbi_gene_identifier_field_value rdf:type ccp:IAO_EXT_0001963 . # CCP:NCBI_gene_id_field_value
            ?ncbi_gene_identifier_field_value rdf:type ?ncbi_gene_identifier .
            ?ncbi_gene_identifier rdfs:subClassOf ccp:IAO_EXT_0000084 . # ccp:NCBI_gene_identifier
    }"
}