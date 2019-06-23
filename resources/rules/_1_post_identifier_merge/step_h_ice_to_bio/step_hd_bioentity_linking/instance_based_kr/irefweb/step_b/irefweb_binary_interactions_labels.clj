;; -----------------------------------------------
;; --------- IRefWeb Binary Interactions ---------
;; -----------------------------------------------
`{:name          "step-hdb_irefweb-binary-interactions-instance-labels"
  :description   "This rule generates bio-representations for all binary protein interactions cataloged by IRefWeb"
  :head          ((?/interaction rdfs/label ?/interaction_label)
                   (?/b1_instance rdfs/label ?/b1_label) ; transfer label to the subclass
                   (?/b2_instance rdfs/label ?/b2_label)) ; transfer label to the subclass

  :body "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
  prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
    PREFIX obo: <http://purl.obolibrary.org/obo/>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
    select  ?interaction ?b1_instance ?b2_instance ?b1_label ?b2_label ?interaction_label {

        ?interaction rdfs:subClassOf ccp:temp_irefweb_binary_interaction .
        ?interaction ccp:temp_has_participant ?b1_instance .
        ?interaction ccp:temp_has_participant ?b2_instance .
        filter (?b1_instance != ?b2_instance)

        ?b1_instance rdf:type ?b1 .
        ?b2_instance rdf:type ?b2 .
        
        optional {?b1 rdfs:label ?label1}
        bind(coalesce(?label1, \"Unnamed interacting protein\") as ?b1_name)

        optional {?b2 rdfs:label ?label2}
        bind(coalesce(?label2, \"Unnamed interacting protein\") as ?b2_name)

        bind(concat(str(?b1_name), \"; interacts with \", str(?b2_name)) as ?b1_label)
        bind(concat(str(?b2_name), \"; interacts with \", str(?b1_name)) as ?b2_label)
        bind(concat(str(?b1_name), \" and \", str(?b2_name), \" interaction\") as ?interaction_label)
        }"

  }