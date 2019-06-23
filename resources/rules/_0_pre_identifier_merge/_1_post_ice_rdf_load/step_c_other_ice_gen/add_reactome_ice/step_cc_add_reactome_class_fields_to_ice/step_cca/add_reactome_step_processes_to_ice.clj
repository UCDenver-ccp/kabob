`{:description "This rule finds any step process field (the most basic component of a pathway step) described in Reactome.",
 :name "step_cca-add_reactome_step_processes_to_ice",
 :reify ([?/this_step_process_record {:ln (:sha-1 ?/step_process_record ?/pathway_step_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]),
  :head ((?/pathway_step_record obo/BFO_0000051 ?/this_step_process_record)
        (?/this_step_process_record rdfs/subClassOf ?/step_process_record)
        (?/this_step_process_record rdf/type ccp/IAO_EXT_0001548) ;; step process field
        ),
 :body "#add_reactome_step_processes_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?pathway_step_record ?step_process_record WHERE {
 ?pathway_step bp:stepProcess ?step_process .
 ?pathway_step ccp:ekws_temp_biopax_connector_relation ?pathway_step_record .
 ?step_process ccp:ekws_temp_biopax_connector_relation ?step_process_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
