`{:description "This rule finds any conversion direction field described in Reactome; these are all left to right in the current 2018 releases.",
 :name "add_reactome_conversion_directions_to_ice",
 :reify ([?/conversion_direction_field {:ns "http://ccp.ucdenver.edu/kabob/ice/", :ln (:sha-1 "Reactome conversion direction field" ?/conv_dir), :prefix "F_"}])
 :head ((?/reactome_thing_record obo/BFO_0000051 ?/conversion_direction_field)
        (?/conversion_direction_field rdf/type ccp/IAO_EXT_0001546) ;; conversion direction field
        (?/conversion_direction_field rdfs/label ?/conv_dir)
        ),
 :body "#add_reactome_conversion_directions_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?reactome_thing_record ?conv_dir WHERE {
 ?reactome_thing bp:conversionDirection ?conv_dir .
 ?reactome_thing ccp:ekws_temp_biopax_connector_relation ?reactome_thing_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
