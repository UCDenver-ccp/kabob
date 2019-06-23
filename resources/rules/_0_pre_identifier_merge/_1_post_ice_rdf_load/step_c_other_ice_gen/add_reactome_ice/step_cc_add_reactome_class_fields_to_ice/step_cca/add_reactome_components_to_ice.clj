`{:description "This rule finds any component field of a complex described in Reactome.",
 :name "step_cca-add_reactome_components_to_ice",
 :reify ([?/this_component_record {:ln (:sha-1 ?/component_record ?/complex_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]),
  :head ((?/complex_record obo/BFO_0000051 ?/this_component_record)
        (?/this_component_record rdfs/subClassOf ?/component_record)
        (?/this_component_record rdf/type ccp/IAO_EXT_0001544) ;; component field
        ),
 :body "#add_reactome_components_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?complex_record ?component_record WHERE {
 ?complex bp:component ?component .
 ?complex ccp:ekws_temp_biopax_connector_relation ?complex_record .
 ?component ccp:ekws_temp_biopax_connector_relation ?component_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
