;; -------------------------------------------------------------------------
;; --------- Human Phenotype Ontology Phenotype Concept Assignment ---------
;; -------------------------------------------------------------------------
`{:name          "step-hdb_hp-phenotype-ice-to-bio-instance-labels"
  :description   "This rule creates a subclass of every human phenotype and types it as a human phenotype concept identifier (IAO_EXT_0000208)"
  :head          ((?/phenotype_instance rdfs/label ?/human_phenotype_label)
                   (?/bioentity_instance rdfs/label ?/causal_bioentity_label))
  :body "PREFIX obo: <http://purl.obolibrary.org/obo/>
                  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                  prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
                  PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                  select  ?phenotype_instance ?bioentity_instance ?causal_bioentity_label ?human_phenotype_label {

                     ?phenotype_instance rdfs:subClassOf ccp:temp_human_phenotype .
                     ?phenotype_instance rdf:type ?hp .

                     ?bioentity_instance ccp:temp_causes ?phenotype_instance .
                     ?bioentity_instance rdf:type ?causal_bioentity .

                     optional {?hp rdfs:label ?hpl}
                     bind(coalesce(?hpl, \"Unnamed phenotype\") as ?hp_name)

                     optional {?causal_bioentity rdfs:label ?bl}
                     bind(coalesce(?bl, \"Unnamed causal bioentity\") as ?bioentity_name)

                     bind(concat(str(?bioentity_name), \" contributes to \", str(?hp_name)) as ?causal_bioentity_label)
                     bind(concat(str(?hp_name), \" by \", str(?bioentity_name)) as ?human_phenotype_label)
                     }"

  }