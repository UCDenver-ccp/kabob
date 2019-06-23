;; --------------------------------------------------------------------
;; --------- PharmGKB abstract bioentity interaction Relation ---------
;; --------------------------------------------------------------------
`{:name "step-hdb_pharmgkb-abstract-interaction-relation-instance-labels"
  :description "This rule assigns an abstract interaction relation between pharmgkb entities"
  :head ((?/interaction rdfs/label ?/interaction_label) ; transfer label to the subclass
          (?/b1_instance rdfs/label ?/bioentity1_label) ; transfer label to the subclass
          (?/b2_instance rdfs/label ?/bioentity2_label)) ; transfer label to the subclass

  :body
     "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
     prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
      PREFIX obo: <http://purl.obolibrary.org/obo/>
      PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
      PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
      select ?interaction ?b1_instance ?b2_instance ?bioentity1_label ?bioentity2_label ?interaction_label {

          ?interaction rdfs:subClassOf ccp:temp_pharmgkb_interaction .
          ?interaction ccp:temp_has_participant ?b1_instance .
          ?interaction ccp:temp_has_participant ?b2_instance .
          filter (?b1_instance != ?b2_instance)

          ?b1_instance rdf:type ?b1 .
          ?b2_instance rdf:type ?b2 .
          
          optional {?b1 rdfs:label ?label1}
          bind(coalesce(?label1, \"Unnamed interacting bioentity\") as ?bioentity1_name)

          optional {?b2 rdfs:label ?label2}
          bind(coalesce(?label2, \"Unnamed interacting bioentity\") as ?bioentity2_name)

          bind(concat(str(?bioentity1_name), \"; interacts with \", str(?bioentity2_name)) as ?bioentity1_label)
          bind(concat(str(?bioentity2_name), \"; interacts with \", str(?bioentity1_name)) as ?bioentity2_label)
          bind(concat(str(?bioentity1_name), \" and \", str(?bioentity2_name), \" interaction\") as ?interaction_label)

          }"


  }