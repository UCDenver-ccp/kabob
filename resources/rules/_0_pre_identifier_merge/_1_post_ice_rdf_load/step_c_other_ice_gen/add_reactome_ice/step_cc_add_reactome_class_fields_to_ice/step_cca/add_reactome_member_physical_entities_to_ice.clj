`{:description "This rule finds any member physical entity field described in Reactome; these fields define groups of molecules with shared functions.",
  :name "step_cca-add_reactome_member_physical_entities_to_ice",
  :reify ([?/this_member_physical_entity_record {:ln (:sha-1 ?/member_physical_entity_record ?/continuant_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]) 
  :head ((?/continuant_record obo/BFO_0000051 ?/this_member_physical_entity_record)
         (?/this_member_physical_entity_record rdfs/subClassOf ?/member_physical_entity_record)
         (?/this_member_physical_entity_record rdf/type ccp/IAO_EXT_0001528) ;; member physical entity field
        ),
  :body "#add_reactome_member_physical_entities_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?continuant_record ?member_physical_entity_record WHERE {
 ?continuant bp:memberPhysicalEntity ?member_physical_entity .
 ?continuant ccp:ekws_temp_biopax_connector_relation ?continuant_record .
 ?member_physical_entity ccp:ekws_temp_biopax_connector_relation ?member_physical_entity_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
