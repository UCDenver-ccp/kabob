;; ----------------------------------------------------------------------------------
;; --------- Gene Ontology Annotation Biological Process Concept Assignment ---------
;; ----------------------------------------------------------------------------------
`{:name          "step-hdb_goa-biological-process-ice-to-bio-instance-labels"
  :description   "This rule creates a subclass of every biological process and types it as a gene ontology biological process concept identifier  (IAO_EXT_0000103)"
  :head          ((?/bp_instance rdfs/label ?/biological_process_label)
                   (?/participating_bioentity_instance rdfs/label ?/participating_bioentity_label))
  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
                prefix franzOption_clauseReorderer: <franz:identity>
                PREFIX obo: <http://purl.obolibrary.org/obo/>
                PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                select  ?bp_instance ?participating_bioentity_instance ?participating_bioentity_label ?biological_process_label {

                            ?bp_instance rdfs:subClassOf ccp:temp_biological_process .
                            ?bp_instance rdf:type ?bp .
                            ?bp_instance ccp:temp_has_participant ?participating_bioentity_instance .
                            ?participating_bioentity_instance rdf:type ?participating_bioentity .

                    optional {?bp rdfs:label ?bpl}
                    bind(coalesce(?bpl, \"Unnamed biological process\") as ?bp_name)

                    optional {?participating_bioentity rdfs:label ?bl}
                    bind(coalesce(?bl, \"Unnamed biological process participant\") as ?bioentity_name)

                    bind(concat(str(?bioentity_name), \"; participates in \", str(?bp_name)) as ?participating_bioentity_label)
                    bind(concat(str(?bp_name), \" by \", str(?bioentity_name)) as ?biological_process_label)

                    }"
}