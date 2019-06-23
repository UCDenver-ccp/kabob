;; ---------------------------------------------------------
;; --------- DrugBank Drug Protein Target Relation ---------
;; ---------------------------------------------------------
`{:name          "step-hda_temp-link-drugbank-record-to-drug-instance"
  :description   "This rule generates bio-representations for the drug-target relationships cataloged by DrugBank where the target is a protein"
  :head          ((?/drugbank_drug_record ccp/temp_drug ?/drug))

  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
  PREFIX obo: <http://purl.obolibrary.org/obo/>
  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
  prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
  SELECT distinct ?drugbank_drug_record ?drug
  WHERE {


    ?drugbank_drug_record rdf:type ccp:IAO_EXT_0000426 . # ccp:DrugBank_record
    ?drugbank_drug_record obo:BFO_0000051 ?drugbank_identifier_field_value .
    ?drugbank_identifier_field_value rdf:type ccp:IAO_EXT_0000360 . # ccp:Drugbank_drug_record__drugbank_identifier_field_value
    ?drugbank_identifier_field_value rdf:type ?drugbank_identifier .
    ?drugbank_identifier rdfs:subClassOf ccp:IAO_EXT_0001309 . # CCP:drugbank_identifier
    ?drugbank_identifier obo:IAO_0000219 ?drug .

    }"

  }