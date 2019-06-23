;; -----------------------------------------------------------------
;; --------- Ensembl Gene ID NCBI Gene ID Exact Match --------
;; -----------------------------------------------------------------
`{:name "step-hcab_rnacentral_to_gene_temp_linking"
  :description "This rule links RNACentral RNAs with the genes that encode them using has_gene_template"
  :head ((?/rna ccp/temp_possible_hgt_restriction ?/gene))
  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX obo_pr: <http://purl.obolibrary.org/obo/pr#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
    SELECT distinct ?gene ?rna
WHERE {
?record rdf:type ccp:IAO_EXT_0001978 .
?record obo:BFO_0000051 ?rnacentral_id_field_value .
  ?rnacentral_id_field_value rdf:type ccp:IAO_EXT_0001972 . # CCP:rnacentral_id_field_value
    ?rnacentral_id_field_value rdf:type ?rnacentral_identifier .
  ?rnacentral_identifier rdfs:subClassOf ccp:IAO_EXT_0000189 . # CCP:rnacentral_identifier
?rnacentral_identifier obo:IAO_0000219 ?rna .

?record obo:BFO_0000051 ?external_identifier_field_value .
  ?external_identifier_field_value rdf:type ccp:IAO_EXT_0001973 . # CCP:external_id_field_value
    ?external_identifier_field_value rdf:type ?external_identifier .
  ?external_identifier rdfs:subClassOf* ccp:IAO_EXT_0000182 . # ccp:genomic_identifier
  ?external_identifier obo:IAO_0000219 ?gene .

}"
}