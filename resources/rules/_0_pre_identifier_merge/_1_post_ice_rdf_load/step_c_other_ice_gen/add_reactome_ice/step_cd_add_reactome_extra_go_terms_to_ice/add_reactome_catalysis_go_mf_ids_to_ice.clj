`{:description "This rule finds any catalysis record described in Reactome with a GO MF id field.  All of them should have this field.",
 :name "add_reactome_catalysis_go_mf_ids_to_ice",
 :reify ([?/this_xref_record {:ns "http://ccp.ucdenver.edu/kabob/ice/", :ln (:sha-1 ?/xref_record ?/reactome_catal_record), :prefix "R_"}]),
          
 :head ((?/reactome_catal_record obo/BFO_0000051 ?/this_xref_record)
         (?/this_xref_record rdfs/subClassOf ?/xref_record)
        (?/xref_id_field rdf/type ?/go_mf_ice) ;; 
        ),
 :body "#add_reactome_catalysis_go_mf_ids_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?reactome_catal_record ?xref_record ?go_mf_ice ?xref_id_field WHERE {
 ?reactome_catal rdf:type bp:Catalysis .
 ?reactome_catal bp:xref ?xref .
 ?reactome_catal ccp:ekws_temp_biopax_connector_relation ?reactome_catal_record .
 ?xref ccp:ekws_temp_biopax_connector_relation ?xref_record .
 ?xref_record obo:BFO_0000051 ?xref_id_field .
 ?xref_id_field rdf:type ccp:IAO_EXT_0001520 .
 ?xref_id_field rdfs:label ?go_mf_id .
 bind (strafter (str (?go_mf_id), \":\") as ?clean_go_id).
bind (uri (concat (str (\"http://ccp.ucdenver.edu/kabob/ice/GO_\"), ?clean_go_id)) AS ?go_mf_ice).
 ?xref_record obo:BFO_0000051 ?xref_db_field .
 ?xref_db_field rdf:type ccp:IAO_EXT_0001519 .
 ?xref_db_field rdfs:label \"GENE ONTOLOGY\"@en .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
