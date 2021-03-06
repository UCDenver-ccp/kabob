;; ------------------------------------------------
;; --------- Localization Infers Location ---------
;; ------------------------------------------------
`{:name        "step-i_localization-infers-location"
  :description "This rule asserts that a protein is located in a cellular component if a localization process results in the localization of a protein and has target end location of a cellular component"
  :head        ((?/location_sc rdfs/subClassOf ccp/temp_location)
                 (?/location_sc rdfs/subClassOf ?/parent_location)
                 (?/bioentity_sc rdfs/subClassOf ?/parent_bioentity)
                 ;; create a located in restriction
                 (?/located_in_restriction rdf/type owl/Restriction)
                 (?/located_in_restriction owl/onProperty ?/located_in)
                 (?/located_in_restriction owl/someValuesFrom ?/location_sc)
                 (?/bioentity_sc rdfs/subClassOf ?/located_in_restriction))

  :reify       ([?/location_sc {:ln (:sha-1 ?/parent_location "inferred-location")
                                :ns "kbio" :prefix "B_"}]
                 [?/located_in_restriction {:ln (:restriction)
                                            :ns "kbio" :prefix "RS_"}]
                 [?/bioentity_sc {:ln (:sha-1 ?/parent_bioentity ?/parent_location "inferred-location")
                                  :ns "kbio" :prefix "B_"}])

  :body
               "prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
               PREFIX obo: <http://purl.obolibrary.org/obo/>
               SELECT ?parent_bioentity ?parent_location ?located_in

               WHERE {
                      ?localization_process rdf:type kice:temp_localization_process .
                      ?localization_process_sc rdfs:subClassOf ?localization_process .
                      ?localization_process_sc rdfs:subClassOf ?trans_main_loc_restriction .
                      ?trans_main_loc_restriction rdf:type owl:Restriction .
                      ?trans_main_loc_restriction owl:onProperty ?transports_or_maintains_localization_of .
                      ?transports_or_maintains_localization_of rdf:type kice:temp_transports_or_maintains_localization_of .
                      ?trans_main_loc_restriction owl:someValuesFrom ?bioentity .
                      ?localization_process_sc rdfs:subClassOf ?target_end_restriction .
                      ?target_end_restriction rdf:type owl:Restriction .
                      ?target_end_restriction owl:onProperty ?has_target_end_location .
                      ?has_target_end_location rdf:type kice:temp_has_target_end_location .
                      ?target_end_restriction owl:someValuesFrom ?location .

                      ?bioentity rdfs:subClassOf ?parent_bioentity .
                      ?location rdfs:subClassOf ?parent_location .

                      ?located_in rdf:type kice:temp_located_in .
                      }"

  :options     {:magic-prefixes [["franzOption_clauseReorderer" "franz:identity"]]}
  }