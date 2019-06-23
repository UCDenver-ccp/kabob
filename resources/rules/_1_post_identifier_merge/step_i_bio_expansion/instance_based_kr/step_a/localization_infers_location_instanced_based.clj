;; ------------------------------------------------
;; --------- Localization Infers Location ---------
;; ------------------------------------------------
`{:name        "step-i_localization-infers-location-instance-based"
  :description "This rule asserts that a protein is located in a cellular component if a localization process results in the localization of a protein and has target end location of a cellular component"
  :head        (
                 (?/location_instance rdfs/subClassOf ccp/temp_location)
                 (?/bioentity_instance ccp/temp_located_in ?/location_instance)

                 (?/location_instance rdf/type ?/location)
                 (?/bioentity_instance rdf/type ?/bioentity)
                 (?/bioentity_instance ?/located_in ?/location_instance))

  :reify       ([?/location_instance {:ln (:sha-1 ?/location "inferred-location")
                                :ns "kbio" :prefix "B_"}]
                 [?/bioentity_instance {:ln (:sha-1 ?/bioentity ?/location "inferred-location")
                                  :ns "kbio" :prefix "B_"}])

  :body
               "prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
               PREFIX obo: <http://purl.obolibrary.org/obo/>
               SELECT ?bioentity ?location ?located_in

               WHERE {

               ?has_target_end_location rdf:type kice:temp_has_target_end_location .
               ?localization_process rdf:type kice:temp_localization_process .
               ?located_in rdf:type kice:temp_located_in .
               ?transports_or_maintains_localization_of rdf:type kice:temp_transports_or_maintains_localization_of .

                      ?localization_instance rdf:type ?localization_process .
                      ?localization_instance ?transports_or_maintains_localization_of ?bioentity_instance .
                      ?localization_instance ?has_target_end_location ?location_instance .
                      ?bioentity_instance rdf:type ?bioentity .
                      ?location_instance rdf:type ?location .


                      }"

  :options     {:magic-prefixes [["franzOption_clauseReorderer" "franz:identity"]]}
  }