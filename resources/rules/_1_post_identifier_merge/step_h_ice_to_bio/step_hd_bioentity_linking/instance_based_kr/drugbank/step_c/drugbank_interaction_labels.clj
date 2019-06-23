;; ---------------------------------------------------------
;; --------- DrugBank Drug Protein Target Relation ---------
;; ---------------------------------------------------------
`{:name        "step-hdc_drugbank-drug-to-protein-target-relation-drug-instance-labels"
  :description "This rule generates bio-representations for the drug-target relationships cataloged by DrugBank where the target is a protein"
  :head        ((?/drug_instance rdfs/label ?/drug_label)
                 (?/interaction rdfs/label ?/interaction_label)
                 (?/target_instance rdfs/label ?/target_label))
  :body        "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
  PREFIX obo: <http://purl.obolibrary.org/obo/>
  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
  prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
  select  ?drug_instance ?drug_label ?target_instance ?target_label ?interaction ?interaction_label {

    ?interaction rdfs:subClassOf ccp:temp_drugbank_interaction .
    ?interaction ccp:temp_drug_participant ?drug_instance .
    ?drug_instance rdf:type ?drug .
    optional {?drug rdfs:label ?dl}
    bind(coalesce(?dl, \"Unnamed drug\") as ?drug_name)
    ?interaction ccp:temp_target_participant ?target_instance .
    ?target_instance rdf:type ?target .
    optional {?target rdfs:label ?pl}
    bind(coalesce(?pl, \"Unnamed drug target\") as ?target_name)

    bind(concat(str(?target_name), \"; interacts with \", str(?drug_name)) as ?target_label)
    bind(concat(str(?drug_name), \"; interacts with \", str(?target_name)) as ?drug_label)
    bind(concat(str(?drug_name), \" and \", str(?target_name), \" interaction\") as ?interaction_label)
    }"
}