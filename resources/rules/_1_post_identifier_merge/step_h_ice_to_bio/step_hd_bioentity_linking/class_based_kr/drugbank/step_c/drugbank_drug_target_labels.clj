;; ---------------------------------------------------------
;; --------- DrugBank Drug Protein Target Relation ---------
;; ---------------------------------------------------------
`{:name        "step-hdb_drugbank-drug-to-protein-target-relation-drug-labels"
  :description "This rule generates bio-representations for the drug-target relationships cataloged by DrugBank where the target is a protein"
  :head        ((?/drug_sc rdfs/label ?/drug_label)
                 (?/interaction rdfs/label ?/interaction_label)
                 (?/target_sc rdfs/label ?/target_label))
  :body        "prefix franzOption_chunkProcessingAllowed: <franz:yes>
  prefix franzOption_clauseReorderer: <franz:identity>
  PREFIX obo: <http://purl.obolibrary.org/obo/>
  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
  prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
  select  ?interaction ?target_sc ?drug_sc ?drug_label ?target_label ?interaction_label {

      ?interaction rdfs:subClassOf ccp:temp_drugbank_interaction .
      ?interaction ccp:temp_drug_participant ?drug_sc .
      ?drug_sc rdfs:subClassOf ?drug .
      ?interaction ccp:temp_target_participant ?target_sc .
      ?target_sc rdfs:subClassOf ?target .


  optional {?target rdfs:label ?pl}
  bind(coalesce(?pl, \"Unnamed drug target\") as ?target_name)

  optional {?drug rdfs:label ?dl}
  bind(coalesce(?dl, \"Unnamed drug\") as ?drug_name)

  bind(concat(str(?target_name), \"; interacts with \", str(?drug_name)) as ?target_label)
  bind(concat(str(?drug_name), \"; interacts with \", str(?target_name)) as ?drug_label)
  bind(concat(str(?drug_name), \" and \", str(?target_name), \" interaction\") as ?interaction_label)
  }"
  }