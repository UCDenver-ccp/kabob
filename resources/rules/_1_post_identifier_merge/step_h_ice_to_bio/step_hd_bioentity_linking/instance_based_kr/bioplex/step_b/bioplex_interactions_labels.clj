;; -----------------------------------------------
;; --------- IRefWeb Binary Interactions ---------
;; -----------------------------------------------
`{:name          "step-hd_bioplex-interaction-labels"
  :description   "This rule generates bio-representations the (biophysical) interactions cataloged by BioPlex"
  :head          ((?/interaction rdfs/label ?/interaction_label)
                   (?/bioentity1_instance rdfs/label ?/b1_label)
                   (?/bioentity2_instance rdfs/label ?/b2_label))

  :body "PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
  prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
    PREFIX obo: <http://purl.obolibrary.org/obo/>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
    select  ?interaction ?bioentity1_instance ?bioentity2_instance ?b1_label ?b2_label ?interaction_label {
    ?interaction rdfs:subClassOf ccp:temp_bioplex_interaction .
    ?interaction ccp:temp_has_participant ?bioentity1_instance .
    ?bioentity1_instance rdf:type ?b1 .
    ?interaction ccp:temp_has_participant ?bioentity2_instance .
    ?bioentity2_instance rdf:type ?b2 .
    filter (?bioentity1_instance != ?bioentity2_instance)

    optional {?b1 rdfs:label ?label1}
    bind(coalesce(?label1, \"Unnamed protein\") as ?b1_name)

    optional {?b2 rdfs:label ?label2}
    bind(coalesce(?label2, \"Unnamed protein\") as ?b2_name)
    bind(concat(str(?b1_name), \"; interacts with \", str(?b2_name)) as ?b1_label)
    bind(concat(str(?b2_name), \"; interacts with \", str(?b1_name)) as ?b2_label)
    bind(concat(str(?b1_name), \" and \", str(?b2_name), \" interaction\") as ?interaction_label)
    }"
  }