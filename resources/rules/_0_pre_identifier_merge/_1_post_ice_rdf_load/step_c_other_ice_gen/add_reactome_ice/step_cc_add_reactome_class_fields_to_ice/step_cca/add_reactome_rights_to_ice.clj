`{:description "This rule finds any right field described in Reactome; these describe outputs of biochemical reactions.",
 :name "step_cca-add_reactome_rights_to_ice",
  :reify ([?/this_output_record {:ln (:sha-1 ?/output_record ?/bcr_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]),
 :head ((?/bcr_record obo/BFO_0000051 ?/this_output_record)
        (?/this_output_record rdfs/subClassOf ?/output_record)
        (?/this_output_record rdf/type ccp/IAO_EXT_0001550) ;; right field
        ),
 :body "#add_reactome_rights_to_ice.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?bcr_record ?output_record WHERE {
 ?bcr bp:right ?output .
 ?bcr ccp:ekws_temp_biopax_connector_relation ?bcr_record .
 ?output ccp:ekws_temp_biopax_connector_relation ?output_record .
# Look for inputs and outputs that are the same thing; these are participants
OPTIONAL {
 ?output_record ccp:ekws_temp_biochemical_reaction_participant_relation ?left_thing_record .
 ?bcr_record ccp:ekws_temp_biochemical_reaction_participant_relation ?output_record .
 ?bcr_record ccp:ekws_temp_biochemical_reaction_participant_relation ?left_thing_record .
} 
filter (!bound (?left_thing_record)) . # not a participant
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
