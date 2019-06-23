;; --------------------------------------------------------------------
;; --------- PharmGKB abstract bioentity interaction Relation ---------
;; --------------------------------------------------------------------
`{:name "step-hd_pharmgkb-abstract-interaction-relation-instance-based-kr"
  :description "This rule assigns an abstract interaction relation between pharmgkb entities"
  :head (
          (?/interaction rdfs/subClassOf ccp/temp_pharmgkb_interaction) ;interaction
          (?/interaction ccp/temp_has_participant ?/bioentity1_instance)
          (?/interaction ccp/temp_has_participant ?/bioentity2_instance)


          (?/interaction rdf/type ?/abstract_interaction) ;interaction

          ;; create subclasses of the proteins
          (?/bioentity1_instance rdf/type ?/bioentity1)

          (?/bioentity2_instance rdf/type ?/bioentity2)

          (?/interaction ?/has_participant ?/bioentity1_instance)
          (?/interaction ?/has_participant ?/bioentity2_instance)

          (?/record obo/IAO_0000219 ?/interaction))

  :reify         ([?/interaction {:ln (:sha-1 ?/bioentity1 ?/bioentity2 "pharmgkb-relation")
                                  :ns "kbio" :prefix "B_"}]
                   [?/bioentity1_instance {:ln (:sha-1 ?/interaction ?/bioentity1)
                                     :ns "kbio" :prefix "B_"}]
                   [?/bioentity2_instance {:ln (:sha-1 ?/interaction ?/bioentity2)
                                     :ns "kbio" :prefix "B_"}])

  :body
     "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
     prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
      PREFIX obo: <http://purl.obolibrary.org/obo/>
      PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
      PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
      SELECT ?bioentity1 ?bioentity2 ?has_participant ?abstract_interaction ?abstract_interaction_label ?record
      WHERE {

      ?has_participant rdf:type kice:temp_has_participant .
      ?abstract_interaction rdf:type kice:temp_abstract_interaction .

      ?record rdf:type ccp:IAO_EXT_0000823 . # ccp:PharmGKB_relation_record
               ?record obo:BFO_0000051 ?entity1_identifier_field_value . # BFO:has_part
               ?entity1_identifier_field_value rdf:type ccp:IAO_EXT_0001036 . # ccp:PharmGKB_relation_record__entity_1_identifier_field_value
               ?entity1_identifier_field_value rdf:type ?entity1_id .
               ?entity1_id obo:IAO_0000219 ?bioentity1 . # IAO:denotes

               ?record obo:BFO_0000051 ?entity2_identifier_field_value . # BFO:has_part
               ?entity2_identifier_field_value rdf:type ccp:IAO_EXT_0001041 . # ccp:PharmGKB_relation_record__entity_2_identifier_field_value
               ?entity2_identifier_field_value rdf:type ?entity2_id .
               ?entity2_id obo:IAO_0000219 ?bioentity2 . # IAO:denotes

          }

         }"


  }