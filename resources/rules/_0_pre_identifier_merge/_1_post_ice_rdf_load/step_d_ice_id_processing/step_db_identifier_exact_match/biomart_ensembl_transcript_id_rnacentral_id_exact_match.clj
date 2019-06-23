;; -----------------------------------------------------------------
;; --------- Ensembl Gene ID NCBI Gene ID Exact Match --------
;; -----------------------------------------------------------------
`{:name "step-db_biomart-ensembl-trancript-id-rnacentral-id-exact-match"
  :description "This rule asserts an exact match between Ensembl transcript identifiers and RNACentral identifiers via BioMart identifier mapping records"
  :head ((?/ensembl_transcript_identifier skos/exactMatch ?/rnacentral_identifier))
  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
  PREFIX obo: <http://purl.obolibrary.org/obo/>
  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
  PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
  SELECT distinct ?ensembl_transcript_identifier ?rnacentral_identifier
  WHERE {  ?record rdf:type ccp:IAO_EXT_0001994 . # CCP:BioMart_transcript_identifier_mapping_record
           ?record obo:BFO_0000051 ?ensembl_transcript_id_field_value .
           ?ensembl_transcript_id_field_value rdf:type ccp:IAO_EXT_0001962 . # CCP:ensembl_transcript_id_field_value
           ?ensembl_transcript_id_field_value rdf:type ?ensembl_transcript_identifier .
           ?ensembl_transcript_identifier rdfs:subClassOf ccp:IAO_EXT_0001956 . # CCP:ensembl_transcript_identifier
           ?record obo:BFO_0000051 ?rnacentral_identifier_field_value .
           ?rnacentral_identifier_field_value rdf:type ccp:IAO_EXT_0001998 . # CCP:rnacentral_id_field_value
           ?rnacentral_identifier_field_value rdf:type ?rnacentral_identifier .
           ?rnacentral_identifier rdfs:subClassOf ccp:IAO_EXT_0000189 . # ccp:rnacentral_identifier
        }"
}