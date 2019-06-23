`{:description "This rule finds any physical entity field described in Reactome; it's not to be confused with the physical entity class of the same name in Reactome.",
 :name "step_cca-add_reactome_physical_entities_to_ice_2",
 :reify ([?/this_physical_entity_record {:ln (:sha-1 ?/physical_entity_record ?/stoichiometry_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]),
 :head ((?/stoichiometry_record obo/BFO_0000051 ?/this_physical_entity_record)
        (?/this_physical_entity_record rdfs/subClassOf ?/physical_entity_record)
        (?/this_physical_entity_record rdf/type ccp/IAO_EXT_0001539) ;; physical entity field
        ),
 :body "#add_reactome_physical_entities_to_ice_2.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?stoichiometry_record ?physical_entity_record WHERE {
 ?stoichiometry bp:physicalEntity ?physical_entity .
 ?stoichiometry ccp:ekws_temp_biopax_connector_relation ?stoichiometry_record .
 ?physical_entity ccp:ekws_temp_biopax_connector_relation ?physical_entity_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
