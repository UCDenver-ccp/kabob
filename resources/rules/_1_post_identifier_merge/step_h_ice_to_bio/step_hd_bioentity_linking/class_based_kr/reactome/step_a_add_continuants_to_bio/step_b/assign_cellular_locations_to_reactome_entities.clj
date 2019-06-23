`{:description "This rule assigns cellular locations to entities whose records specify a location.",
 :name "step-hdb-reactome_assign-cellular-locations-to-reactome-entities",
 :reify ([?/trans_restriction {:ln (:restriction), :ns "kbio" :prefix "RS_"}]
         [?/target_restriction {:ln (:restriction), :ns "kbio" :prefix "RS_"}]
         [?/cellular_component_sc {:ln (:sha-1 ?/cellular_component), :ns "kbio" :prefix "RS_"}]
         [?/localization_sc {:ln (:sha-1 "GO_0051179" ?/target_restriction ?/trans_restriction), :ns "kbio" :prefix "B_"}]),
 :head ((?/cellular_component_sc rdfs/subClassOf ?/cellular_component)
        (?/trans_restriction owl/someValuesFrom ?/bio_entity)
        (?/trans_restriction rdf/type owl/Restriction)
        (?/trans_restriction owl/onProperty ?/transports_or_maintains_localization_of)
        (?/target_restriction owl/someValuesFrom ?/cellular_component_sc)
        (?/target_restriction rdf/type owl/Restriction)
        (?/target_restriction owl/onProperty ?/has_target_end_location)
        (?/localization_sc rdfs/subClassOf ?/target_restriction)
        (?/localization_sc rdfs/subClassOf ?/trans_restriction)
        (?/localization_sc rdfs/subClassOf ?/localization_process)),
 :body "PREFIX franzOption_memoryLimit: <franz:85g>
PREFIX franzOption_memoryExhaustionWarningPercentage: <franz:95>
PREFIX franzOption_logQuery: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?bio_entity ?cellular_component ?transports_or_maintains_localization_of ?has_target_end_location ?localization_process
WHERE {
      ?entity_record_with_cellular_localization_field rdfs:subClassOf* ccp:IAO_EXT_0001954 .  # reactome record for localized entity
      ?entity_record rdf:type ?entity_record_with_cellular_localization_field .
      ?entity_record obo:BFO_0000051 ?location_record .
      ?location_record rdf:type ccp:IAO_EXT_0001521 . # cellular location
      ?location_record obo:BFO_0000051 ?gocc_xref_record .
      ?gocc_xref_record rdf:type ccp:IAO_EXT_0001588 .
      ?gocc_xref_record obo:BFO_0000051 ?gocc_xref_id_field .
      ?gocc_xref_id_field rdf:type ?gocc_id .
      ?gocc_id rdfs:subClassOf* ccp:IAO_EXT_0000102 . # gene ontology concept identifier
      ?gocc_id obo:IAO_0000219 ?cellular_component .
      filter (contains (str (?cellular_component), \"http://ccp.ucdenver.edu/kabob/bio\")) .

      ?entity_record obo:BFO_0000051 ?react_xref_record .
      ?react_xref_record rdf:type ccp:IAO_EXT_0001588 . #xref field
      ?react_xref_record obo:BFO_0000051 ?react_id_field .
      ?react_id_field rdf:type ?reactome_id .
      ?reactome_id rdfs:subClassOf ccp:IAO_EXT_0001643 .
      ?reactome_id obo:IAO_0000219 ?bio_entity .

      ?localization_process rdf:type kice:temp_localization_process .
  ?transports_or_maintains_localization_of rdf:type kice:temp_transports_or_maintains_localization_of .
  ?has_target_end_location rdf:type kice:temp_has_target_end_location .
  }",
 :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}
