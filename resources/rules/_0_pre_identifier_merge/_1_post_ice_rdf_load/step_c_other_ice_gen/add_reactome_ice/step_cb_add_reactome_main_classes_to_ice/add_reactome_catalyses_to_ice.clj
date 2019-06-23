`{:description "This rule finds any catalysis record described in Reactome, as well as its GO MF field; there is no Reactome ID for catalyses, but every one has a GO-MF term.",
 :name "step_cb-add_reactome_catalyses_to_ice",
 :reify ([?/catal_record {:ns "http://ccp.ucdenver.edu/kabob/ice/", :ln (:sha-1 "Reactome catalysis record" ?/catal), :prefix "R_"}]
         [?/this_xref_record {:ns "http://ccp.ucdenver.edu/kabob/ice/", :ln (:sha-1 ?/xref_record ?/catal_record), :prefix "R_"}]
         ),
 :head ((?/record_set obo/BFO_0000051 ?/catal_record)
        (?/catal_record rdf/type ccp/IAO_EXT_0001574) ;; catalysis record
        (?/catal ccp/ekws_temp_biopax_connector_relation ?/catal_record)
        ),
 :body "PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?record_set ?catal ?xref_record ?xref_id_field ?go_mf_ice WHERE {
 ?record_set rdfs:label \"Reactome BioPAX record set, Homo sapiens\"@en .
 ?record_set rdf:type ccp:IAO_EXT_0000012 .
 ?record_set obo:IAO_0000142 ?download .
 ?download rdfs:label \"http://www.reactome.org/pages/download-data/biopax/Homo_sapiens.owl\"@en .
 ?catal rdf:type bp:Catalysis .
 }",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
