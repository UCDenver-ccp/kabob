;; -----------------------------------------------------------------
;; --------- RNACentral ID; MirBase ID Exact Match --------
;; -----------------------------------------------------------------
`{:name "step-db_rnacentral-id-mirbase-id-exact-match"
  :description "This rule asserts an exact match between RNACentral identifiers and MirBase identifiers via RNACentral identifier mapping records"
  :head ((?/rnacentral_identifier skos/exactMatch ?/mirbase_identifier))
  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
    SELECT distinct ?rnacentral_identifier ?mirbase_identifier
WHERE {  ?record rdf:type ccp:IAO_EXT_0001978 . # CCP:RNACentral_identifier_mapping_record
?record obo:BFO_0000051 ?rnacentral_id_field_value .
?rnacentral_id_field_value rdf:type ccp:IAO_EXT_0001972 . # CCP:rnacentral_id_field_value
?rnacentral_id_field_value rdf:type ?rnacentral_identifier .
?rnacentral_identifier rdfs:subClassOf ccp:IAO_EXT_0000189 . # CCP:rnacentral_identifier
?record obo:BFO_0000051 ?external_identifier_field_value .
?external_identifier_field_value rdf:type ccp:IAO_EXT_0001973 . # CCP:external_id_field_value
?external_identifier_field_value rdf:type ?mirbase_identifier .
?mirbase_identifier rdfs:subClassOf ccp:IAO_EXT_0001397 . # ccp:mirbase_identifier
}"
}