;; -----------------------------------------------------------------------
;; --------- Gene Ontology Cellular Component Concept Assignment ---------
;; -----------------------------------------------------------------------
`{:name "step-hdb_goa-cellular-component-ice-to-bio-instance-labels"
  :description "This rule creates a subclass of every cellular component and types it as a gene ontology cellular component concept identifier (IAO_EXT_0000200)"
  :head ((?/loc_process_instance rdfs/label ?/localization_process_label)
          (?/localized_bioentity_instance rdfs/label ?/localized_bioentity_label)
          (?/cc_instance rdfs/label ?/cellular_component_label))
  :body "PREFIX obo: <http://purl.obolibrary.org/obo/>
                  PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                  prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
                  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                  PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                  select  ?loc_process_instance ?localized_bioentity_instance ?cc_instance ?localized_bioentity_label ?cellular_component_label ?localization_process_label {

                          ?loc_process_instance rdfs:subClassOf ccp:temp_localization .
                          ?loc_process_instance ccp:temp_transports ?localized_bioentity_instance .
                          ?localized_bioentity_instance rdf:type ?localized_bioentity .
                          ?loc_process_instance ccp:temp_end_location ?cc_instance .
                          ?cc_instance rdf:type ?cc .

                          optional {?cc rdfs:label ?ccl}
                          bind(coalesce(?ccl, \"Unnamed cellular component\") as ?cc_name)

                          optional {?localized_bioentity rdfs:label ?bl}
                          bind(coalesce(?bl, \"Unnamed localized bioentity\") as ?bioentity_name)

                          bind(concat(str(?bioentity_name), \"; in \", str(?cc_name)) as ?localized_bioentity_label)
                          bind(concat(str(?cc_name), \"; contains \", str(?bioentity_name)) as ?cellular_component_label)
                          bind(concat(\"Localization of \", str(?bioentity_name), \" to \", str(?cc_name)) as ?localization_process_label)

                          }"
}