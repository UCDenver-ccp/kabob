`{:description "This rule finds any participant stoichiometry field of a biochemical reaction described in Reactome.",
 :name "step_cca-add_reactome_participant_stoichiometries_to_ice",
 :reify ([?/this_participant_stoi_record {:ln (:sha-1 ?/participant_stoi_record ?/bcr_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]),
 :head ((?/bcr_record obo/BFO_0000051 ?/this_participant_stoi_record)
        (?/this_participant_stoi_record rdfs/subClassOf ?/participant_stoi_record)
        (?/this_participant_stoi_record rdf/type ccp/IAO_EXT_0001944) ;; participant stoichiometry field
        ),
 :body "#add_reactome_participant_stoichiometries_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?bcr_record ?participant_stoi_record WHERE {
 ?bcr bp:participantStoichiometry ?participant_stoi .
 ?bcr ccp:ekws_temp_biopax_connector_relation ?bcr_record .
 ?participant_stoi ccp:ekws_temp_biopax_connector_relation ?participant_stoi_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
