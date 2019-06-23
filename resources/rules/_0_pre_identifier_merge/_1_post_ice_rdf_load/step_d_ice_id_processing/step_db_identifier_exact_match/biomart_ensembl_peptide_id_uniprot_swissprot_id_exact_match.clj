;; -----------------------------------------------------------------
;; --------- Ensembl Gene ID NCBI Gene ID Exact Match --------
;; -----------------------------------------------------------------
`{:name "step-db_biomart-ensembl-peptide-id-uniprot-swissprot-id-exact-match"
  :description "This rule asserts an exact match between Ensembl peptide identifiers and UniProt SwissProt identifiers via BioMart identifier mapping records"
  :head ((?/ensembl_peptide_identifier skos/exactMatch ?/uniprot_swissprot_identifier))
  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
  PREFIX obo: <http://purl.obolibrary.org/obo/>
  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
  PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
  SELECT distinct ?ensembl_peptide_identifier ?uniprot_swissprot_identifier
  WHERE {  ?record rdf:type ccp:IAO_EXT_0001993 . # CCP:BioMart_protein_identifier_mapping_record
           ?record obo:BFO_0000051 ?ensembl_peptide_id_field_value .
           ?ensembl_peptide_id_field_value rdf:type ccp:IAO_EXT_0001996 . # CCP:ensembl_peptide_id_field_value
           ?ensembl_peptide_id_field_value rdf:type ?ensembl_peptide_identifier .
           ?ensembl_peptide_identifier rdfs:subClassOf ccp:IAO_EXT_0001991 . # CCP:ensembl_peptide_identifier
           ?record obo:BFO_0000051 ?uniprot_swissprot_identifier_field_value .
           ?uniprot_swissprot_identifier_field_value rdf:type ccp:IAO_EXT_0001965 . # CCP:Uniprot_swissprot_id_field_value
           ?uniprot_swissprot_identifier_field_value rdf:type ?uniprot_swissprot_identifier .
           ?uniprot_swissprot_identifier rdfs:subClassOf ccp:IAO_EXT_0000184 . # ccp:uniprot_swissprot_identifier
        }"
}