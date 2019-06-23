`{:description "This rule finds any cellular location field described in Reactome.",
 :name "add_reactome_cellular_locations_to_ice",
 :reify ([?/this_cellular_location_record {:ln (:sha-1 ?/cellular_location_record ?/continuant_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]),
  :head ((?/continuant_record obo/BFO_0000051 ?/this_cellular_location_record)
        (?/this_cellular_location_record rdfs/subClassOf ?/cellular_location_record)
        (?/this_cellular_location_record rdf/type ccp/IAO_EXT_0001521) ;; cellular location field
        ),
 :body "#add_reactome_cellular_locations_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?continuant_record ?cellular_location_record WHERE {
 ?continuant bp:cellularLocation ?cellular_location .
 ?continuant ccp:ekws_temp_biopax_connector_relation ?continuant_record .
 ?cellular_location ccp:ekws_temp_biopax_connector_relation ?cellular_location_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
