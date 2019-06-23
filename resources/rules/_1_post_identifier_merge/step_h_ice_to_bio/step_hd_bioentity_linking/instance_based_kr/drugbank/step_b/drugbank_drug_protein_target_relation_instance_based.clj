;; ---------------------------------------------------------
;; --------- DrugBank Drug Protein Target Relation ---------
;; ---------------------------------------------------------
`{:name          "step-hd_drugbank-drug-to-protein-target-relation-instance-based-kr"
  :description   "This rule generates bio-representations for the drug-target relationships cataloged by DrugBank where the target is a protein"
  :head          (
                   (?/interaction rdfs/subClassOf ccp/temp_drugbank_interaction)
                   (?/interaction ccp/temp_drug_participant ?/drug_instance)
                   (?/interaction ccp/temp_target_participant ?/target_instance)

                   ;; create an interaction as instances of both direct binding and binding
                   (?/interaction rdf/type ?/direct_interaction) ; MI:direct interaction
                   (?/interaction rdf/type ?/binding) ; GO:binding

                   ;; create an instance of the protein target that will participate in the interaction
                   (?/target_instance rdf/type ?/target_protein)

                   (?/interaction ?/has_participant ?/target_instance)

                   ;; create an instance of the drug that will participate in the interaction
                   (?/drug_instance rdf/type ?/drug)

                   (?/interaction ?/has_participant ?/drug_instance)

                   (?/drugbank_drug_record obo/IAO_0000219 ?/interaction))


  :reify         ([?/interaction {:ln (:sha-1 "interaction" ?/target_protein ?/drug)
                                  :ns "kbio" :prefix "I_"}]
                   [?/target_instance {:ln (:sha-1 ?/target_protein ?/drug "target")
                                 :ns "kbio" :prefix "B_"}]
                   [?/drug_instance {:ln (:sha-1 ?/target_protein ?/drug "drug")
                               :ns "kbio" :prefix "B_"}])

  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
  PREFIX obo: <http://purl.obolibrary.org/obo/>
  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
  prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
  SELECT ?target_protein ?drug ?binding ?has_participant ?realizes ?inheres_in ?drug_role ?drugbank_drug_record ?direct_interaction
  WHERE {

    ?drug_role rdf:type kice:temp_drug_role .
  ?inheres_in rdf:type kice:temp_inheres_in .
  ?realizes rdf:type kice:temp_realizes .
  ?has_participant rdf:type kice:temp_has_participant .
  ?binding rdf:type kice:temp_binding .
  ?direct_interaction rdf:type kice:temp_direct_interaction .

    ?drugbank_drug_record ccp:temp_drug ?drug .

    # retrieve the target bioentity participant identifier
    ?drugbank_drug_record obo:BFO_0000051 ?target_record_as_field_value .
    ?target_record_as_field_value rdf:type ccp:IAO_EXT_0000410 . # ccp:Drugbank_drug_record__targets_field_value
    ?target_record_as_field_value obo:BFO_0000051 ?polypeptide_record_as_field_value .
    ?polypeptide_record_as_field_value rdf:type ccp:IAO_EXT_0000447 . # ccp:Drugbank_target_record__polypeptides_field_value

    # the vast majority of targets contain a polypeptide record which links to a protein bio-entity
    # of 12,704 target records, all but 225 have an associated polypeptide record we should look into these, they are currently excluded (https://trello.com/c/JulbRanl)
    ?polypeptide_record_as_field_value obo:BFO_0000051 ?identifier_field_value .
    ?identifier_field_value rdf:type ccp:IAO_EXT_0000527 . # CCP:Drugbank_polypeptide_record__identifier_field_value
    ?identifier_field_value rdf:type ?target_identifier .
    ?target_identifier rdfs:subClassOf* ccp:IAO_EXT_0000188 . # CCP:protein_identifier
    ?target_identifier obo:IAO_0000219 ?target_protein .

     }"

  }