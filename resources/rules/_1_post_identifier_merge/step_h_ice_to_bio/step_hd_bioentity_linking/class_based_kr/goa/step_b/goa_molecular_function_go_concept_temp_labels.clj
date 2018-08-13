;; ----------------------------------------------------------------------------------
;; --------- Gene Ontology Annotation Molecular Function Concept Assignment ---------
;; ----------------------------------------------------------------------------------
`{:name          "step-hdb0_goa-molecular-function-go-concept-temp-labels"
  :description   "This rule creates a subclass of every molecular function and types it as a gene ontology molecular function concept identifier  (IAO_EXT_0000103)"
  :head          ((?/mf_sc rdfs/label ?/molecular_function_label)
                   (?/participating_bioentity_sc rdfs/label ?/participating_bioentity_label))

  :body "prefix franzOption_chunkProcessingAllowed: <franz:yes>
                prefix franzOption_clauseReorderer: <franz:identity>
                PREFIX obo: <http://purl.obolibrary.org/obo/>
                PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
                prefix kice: <http://ccp.ucdenver.edu/kabob/ice/>
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                select  ?mf_sc ?participating_bioentity_sc ?participating_bioentity_label ?molecular_function_label {

                ?mf_sc rdfs:subClassOf ccp:temp_molecular_function .
                ?mf_sc rdfs:subClassOf ?mf .
                filter (?mf != ccp:temp_molecular_function)


                ?mf_sc ccp:temp_has_participant ?participating_bioentity_sc .
                ?participating_bioentity_sc rdfs:subClassOf ?participating_bioentity .
                minus {?mf rdf:type owl:Restriction}

                optional {?mf rdfs:label ?mfl}
                bind(coalesce(?mfl, "Unnamed molecular function") as ?mf_name)

                optional {?participating_bioentity rdfs:label ?bl}
                bind(coalesce(?bl, "Unnamed molecular function participant") as ?bioentity_name)

                bind(concat(str(?bioentity_name), \"; participates in \", str(?mf_name)) as ?participating_bioentity_label)
                bind(concat(str(?mf_name), \" by \", str(?bioentity_name)) as ?molecular_function_label)

                }"

  :options       {:magic-prefixes [["franzOption_clauseReorderer" "franz:identity"]]}
  }