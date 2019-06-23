`{:description "This rule finds any biochemical reaction described in Reactome with the same left and right participants with no stoichiometric coefficients given for either; we define these later as participants in the reaction, rather than inputs or outputs.",
 :name "step_cca-add_reactome_same_lefts_and_rights_to_ice_2",
 :reify ([?/this_participant_record {:ln (:sha-1 "output" ?/participant_record ?/bcr_record), :ns "http://ccp.ucdenver.edu/kabob/ice/" :prefix "R_"}]
         ),
 :head ((?/bcr_record obo/BFO_0000051 ?/this_participant_record)
        (?/this_participant_record rdfs/subClassOf ?/participant_record)
        (?/this_participant_record rdf/type ccp/IAO_EXT_0001941) ;; participant field
        ),
 :body "#add_reactome_same_lefts_and_rights_to_ice_2.clj
PREFIX franzOption_chunkProcessingAllowed: <franz:yes>
PREFIX franzOption_clauseReorderer: <franz:identity>
PREFIX obo: <http://purl.obolibrary.org/obo/>
PREFIX ccp: <http://ccp.ucdenver.edu/obo/ext/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX bp: <http://www.biopax.org/release/biopax-level3.owl#>
PREFIX kice: <http://ccp.ucdenver.edu/kabob/ice/>
PREFIX kbio: <http://ccp.ucdenver.edu/kabob/bio/>
SELECT DISTINCT ?bcr_record ?left_record ?participant_record { 
 ?bcr bp:left ?left .
 ?bcr bp:right ?right .
OPTIONAL {
 ?bcr bp:participantStoichiometry ?stoi_1 .
 ?bcr bp:participantStoichiometry ?stoi_2 .
 ?stoi_1 bp:physicalEntity ?left .
 ?stoi_2 bp:physicalEntity ?right .
}
 filter (!bound (?stoi_1) && !bound (?stoi_2)) .
 filter (?left = ?right) .
 ?bcr ccp:ekws_temp_biopax_connector_relation ?bcr_record .
 ?left ccp:ekws_temp_biopax_connector_relation ?left_thing_record .
 ?right ccp:ekws_temp_biopax_connector_relation ?participant_record .
}",
  :options {:magic-prefixes [["franzOption_logQuery" "franz:yes"] ["franzOption_clauseReorderer" "franz:identity"]]}}




         

         
